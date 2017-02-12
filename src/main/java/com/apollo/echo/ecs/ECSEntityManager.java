package com.apollo.echo.ecs;
/**
 * Created by Ben on 2/17/14.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public final class ECSEntityManager {
    public ArrayList<ECSEntity> entities = new ArrayList<ECSEntity>();
    private HashMap<String, ArrayList<ECSEntity>> groupMap = new HashMap<String, ArrayList<ECSEntity>>();
    private HashMap<String, ECSEntity> uniqueMap = new HashMap<String, ECSEntity>();

    private ECSWorld world;

    public ECSEntityManager(ECSWorld world) {
        this.world = world;
    }

    public ECSEntity createEntity() {
        return new ECSEntity();
    }

    // Figures out which systems are interested in the entity
    public void addEntity(ECSEntity e) {
        entities.add(e);
        world.getSystemManager().insertEntity(e);
    }

    public void removeEntity(ECSEntity e) {
        ECSSystemManager manager = world.getSystemManager();
        Collection<ECSSystem> systems = manager.getSystemMap().values();
        for (ECSSystem s : systems) {
            s.getEntities().remove(e);
        }
        entities.remove(e);
        if (!e.getGroupID().equals("NULL")) {
            groupMap.get(e.getGroupID()).remove(e);
            if (groupMap.get(e.getGroupID()).isEmpty()) groupMap.remove(e.getGroupID());
        }
        if (!e.getUniqueID().equals("NULL")) uniqueMap.remove(e.getUniqueID());
    }

    public ArrayList<ECSEntity> getGroup(String UID) {
        boolean groupExists = groupMap.containsKey(UID);
        if (groupExists) return groupMap.get(UID);
        else System.out.println(UID + " could not be retrieved");
        return null;
    }

    public ECSEntity getUniqueEntity(String UID) {
        boolean entityExists = uniqueMap.containsKey(UID);
        if (entityExists) return uniqueMap.get(UID);
        else System.out.println(UID + " could not be retrieved");
        return null;
    }

    public void assignGroupID(String UID, ECSEntity e) {
        boolean groupExists = groupMap.containsKey(UID);
        if (groupExists) {
            //Check if entity has already been assigned to group
            boolean entityAssigned = groupMap.get(UID).contains(e);
            if (!entityAssigned) {
                e.setGroupID(UID);
                groupMap.get(UID).add(e);
            } else System.out.println("Entity already assigned to " + UID);
        } else if (!groupExists) {
            ArrayList<ECSEntity> group = new ArrayList<ECSEntity>();
            groupMap.put(UID, group);
            e.setGroupID(UID);
            groupMap.get(UID).add(e);
        } else System.out.println("Could not assign entity to group " + UID);
    }

    public void assignUniqueID(String UID, ECSEntity e) {
        boolean uniqueAssigned = uniqueMap.containsKey(UID);
        if (!uniqueAssigned) {
            e.setUniqueID(UID);
            uniqueMap.put(UID, e);
        } else System.out.println("Could not assigned entity to unique " + UID);
    }

    public ECSWorld getWorld() {
        return world;
    }
}
