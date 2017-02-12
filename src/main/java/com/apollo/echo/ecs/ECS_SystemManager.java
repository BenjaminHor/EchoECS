package com.apollo.echo.ecs;
/**
 * Created by Ben on 2/17/14.
 */

import java.util.ArrayList;
import java.util.HashMap;

public final class ECS_SystemManager {
    private ArrayList<ECS_System> processSystems = new ArrayList<ECS_System>();
    private ArrayList<ECS_System> renderSystems = new ArrayList<ECS_System>();
    private HashMap<String, ECS_System> systemMap = new HashMap<String, ECS_System>();
    private ECS_World world;

    public ECS_SystemManager(ECS_World world) {
        this.world = world;
    }

    public void process() {
        for (ECS_System s : processSystems) {
            if (s.isEnabled()) s.process();
        }
    }

    public void render() {
        for (ECS_System s : renderSystems) {
            if (s.isEnabled()) s.process();
        }
    }

    //Placing entity into appropriate processSystems
    public void insertEntity(ECS_Entity e) {
        for (ECS_System s : systemMap.values()) {
            if (s.checkEntity(e)) {
                s.addEntity(e);
            }
        }
    }

    public void registerSystem(ECS_System system) {
        boolean systemExists = systemMap.containsKey(system.getUniqueID());
        if (!systemExists && !system.getUniqueID().equals("NULL")) {
            if (system.getSystemType() == ECS_System.SystemType.PROCESS) processSystems.add(system);
            else if (system.getSystemType() == ECS_System.SystemType.RENDER) renderSystems.add(system);
            systemMap.put(system.getUniqueID(), system);
            system.setSystemManager(this);
        } else System.out.println(system.getUniqueID() + " could not be registered");
    }

    public void removeSystem(String UID) {
        boolean systemExists = systemMap.containsKey(UID);
        if (systemExists) {
            ECS_System temp = systemMap.get(UID);
            if (temp.getSystemType() == ECS_System.SystemType.PROCESS) processSystems.remove(temp);
            else if (temp.getSystemType() == ECS_System.SystemType.RENDER) renderSystems.remove(temp);
            systemMap.remove(UID);
        } else System.out.println(UID + " could not be removed");
    }

    public ECS_System getSystem(String UID) {
        boolean systemExists = systemMap.containsKey(UID);
        if (systemExists) {
            ECS_System temp = systemMap.get(UID);
            if (temp.getSystemType() == ECS_System.SystemType.PROCESS) return temp;
            else if (temp.getSystemType() == ECS_System.SystemType.RENDER) return temp;
        } else System.out.println(UID + " could not be retrieved");
        return null;
    }

    public ECS_World getWorld() {
        return world;
    }

    public ArrayList<ECS_System> getProcessSystems() {
        return processSystems;
    }

    public ArrayList<ECS_System> getRenderSystems() {
        return renderSystems;
    }

    public HashMap<String, ECS_System> getSystemMap() {
        return systemMap;
    }
}
