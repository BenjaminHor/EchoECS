package com.apollo.echo.ecs;
/**
 * Created by Ben on 2/17/14.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public final class ECS_EntityManager {
    public ArrayList<ECS_Entity> entities = new ArrayList<ECS_Entity>();
    private HashMap<String, ArrayList<ECS_Entity>> groupMap = new HashMap<String, ArrayList<ECS_Entity>>();
    private HashMap<String, ECS_Entity> uniqueMap = new HashMap<String, ECS_Entity>();

    private ECS_World world;

    public ECS_EntityManager(ECS_World world) {
        this.world = world;
    }

    public ECS_Entity createEntity() {
        return new ECS_Entity();
    }

    // Figures out which systems are interested in the entity
    public void addEntity(ECS_Entity e) {
        entities.add(e);
        world.getSystemManager().insertEntity(e);
    }

    public void removeEntity(ECS_Entity e) {
        ECS_SystemManager manager = world.getSystemManager();
        Collection<ECS_System> systems = manager.getSystemMap().values();
        for (ECS_System s : systems) {
            s.getEntities().remove(e);
        }
        entities.remove(e);
        if (!e.getGroupID().equals("NULL")) {
            groupMap.get(e.getGroupID()).remove(e);
            if (groupMap.get(e.getGroupID()).isEmpty()) groupMap.remove(e.getGroupID());
        }
        if (!e.getUniqueID().equals("NULL")) uniqueMap.remove(e.getUniqueID());
    }

    public ArrayList<ECS_Entity> getGroup(String UID) {
        boolean groupExists = groupMap.containsKey(UID);
        if (groupExists) return groupMap.get(UID);
        else System.out.println(UID + " could not be retrieved");
        return null;
    }

    public ECS_Entity getUniqueEntity(String UID) {
        boolean entityExists = uniqueMap.containsKey(UID);
        if (entityExists) return uniqueMap.get(UID);
        else System.out.println(UID + " could not be retrieved");
        return null;
    }

    public void assignGroupID(String UID, ECS_Entity e) {
        boolean groupExists = groupMap.containsKey(UID);
        if (groupExists) {
            //Check if entity has already been assigned to group
            boolean entityAssigned = groupMap.get(UID).contains(e);
            if (!entityAssigned) {
                e.setGroupID(UID);
                groupMap.get(UID).add(e);
            } else System.out.println("Entity already assigned to " + UID);
        } else if (!groupExists) {
            ArrayList<ECS_Entity> group = new ArrayList<ECS_Entity>();
            groupMap.put(UID, group);
            e.setGroupID(UID);
            groupMap.get(UID).add(e);
        } else System.out.println("Could not assign entity to group " + UID);
    }

    public void assignUniqueID(String UID, ECS_Entity e) {
        boolean uniqueAssigned = uniqueMap.containsKey(UID);
        if (!uniqueAssigned) {
            e.setUniqueID(UID);
            uniqueMap.put(UID, e);
        } else System.out.println("Could not assigned entity to unique " + UID);
    }

    public ECS_World getWorld() {
        return world;
    }
}
