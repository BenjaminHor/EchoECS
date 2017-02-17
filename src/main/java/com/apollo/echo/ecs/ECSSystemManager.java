package com.apollo.echo.ecs;
/**
 * Created by Ben on 2/17/14.
 */

import java.util.ArrayList;
import java.util.HashMap;

public final class ECSSystemManager {
    private ArrayList<ECSSystem> processSystems = new ArrayList<ECSSystem>();
    private ArrayList<ECSSystem> renderSystems = new ArrayList<ECSSystem>();
    private HashMap<String, ECSSystem> systemMap = new HashMap<String, ECSSystem>();
    private ECSWorld world;

    public ECSSystemManager(ECSWorld world) {
        this.world = world;
    }

    public void process() {
        for (ECSSystem s : processSystems) {
            if (s.isEnabled()) s.process();
        }
    }

    public void render() {
        for (ECSSystem s : renderSystems) {
            if (s.isEnabled()) s.process();
        }
    }

    //Placing entity into appropriate processSystems
    public void insertEntity(ECSEntity e) {
        for (ECSSystem s : systemMap.values()) {
            s.addEntity(e);
        }
    }

    public void registerSystem(ECSSystem system) {
        boolean systemExists = systemMap.containsKey(system.getUniqueID());
        if (!systemExists && !system.getUniqueID().equals("NULL")) {
            if (system.getSystemType() == ECSSystem.SystemType.PROCESS) processSystems.add(system);
            else if (system.getSystemType() == ECSSystem.SystemType.RENDER) renderSystems.add(system);
            systemMap.put(system.getUniqueID(), system);
            system.setSystemManager(this);
        } else System.out.println(system.getUniqueID() + " could not be registered");
    }

    public void removeSystem(String UID) {
        boolean systemExists = systemMap.containsKey(UID);
        if (systemExists) {
            ECSSystem temp = systemMap.get(UID);
            if (temp.getSystemType() == ECSSystem.SystemType.PROCESS) processSystems.remove(temp);
            else if (temp.getSystemType() == ECSSystem.SystemType.RENDER) renderSystems.remove(temp);
            systemMap.remove(UID);
        } else System.out.println(UID + " could not be removed");
    }

    public ECSSystem getSystem(String UID) {
        boolean systemExists = systemMap.containsKey(UID);
        if (systemExists) {
            ECSSystem temp = systemMap.get(UID);
            if (temp.getSystemType() == ECSSystem.SystemType.PROCESS) return temp;
            else if (temp.getSystemType() == ECSSystem.SystemType.RENDER) return temp;
        } else System.out.println(UID + " could not be retrieved");
        return null;
    }

    public ECSWorld getWorld() {
        return world;
    }

    public ArrayList<ECSSystem> getProcessSystems() {
        return processSystems;
    }

    public ArrayList<ECSSystem> getRenderSystems() {
        return renderSystems;
    }

    public HashMap<String, ECSSystem> getSystemMap() {
        return systemMap;
    }
}
