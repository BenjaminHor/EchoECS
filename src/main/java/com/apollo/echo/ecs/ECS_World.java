package com.apollo.echo.ecs;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ben on 2/7/14.
 */
public final class ECS_World {
    private ECS_SystemManager systemManager;
    private ECS_EntityManager entityManager;

    public ECS_World() {
        systemManager = new ECS_SystemManager(this);
        entityManager = new ECS_EntityManager(this);
    }

    public void process() {
        systemManager.process();
    }

    public void render() {
        systemManager.render();
    }

    // Entity Manager wrapper functions

    public ECS_Entity createEntity() {
        return entityManager.createEntity();
    }

    public void addEntity(ECS_Entity e) {
        entityManager.addEntity(e);
    }

    public void removeEntity(ECS_Entity e) {
        entityManager.removeEntity(e);
    }

    public ArrayList<ECS_Entity> getGroup(String UID) {
        return entityManager.getGroup(UID);
    }

    public ECS_Entity getUniqueEntity(String UID) {
        return entityManager.getUniqueEntity(UID);
    }

    public void assignGroupID(String UID, ECS_Entity e) {
        // Combine entity manager register and assign functions together
        entityManager.assignGroupID(UID, e);
    }

    public void assignUniqueID(String UID, ECS_Entity e) {
        entityManager.assignUniqueID(UID, e);
    }

    // System Manager wrapper functions

    public void registerSystem(ECS_System system) {
        systemManager.registerSystem(system);
    }

    public void removeSystem(String UID) {
        systemManager.removeSystem(UID);
    }

    public ECS_System getSystem(String UID) {
        return systemManager.getSystem(UID);
    }

    public ArrayList<ECS_System> getProcessSystems() {
        return systemManager.getProcessSystems();
    }

    public ArrayList<ECS_System> getRenderSystems() {
        return systemManager.getRenderSystems();
    }

    public HashMap<String, ECS_System> getSystemMap() {
        return systemManager.getSystemMap();
    }

    public ECS_SystemManager getSystemManager() {
        return systemManager;
    }

    public ECS_EntityManager getEntityManager() {
        return entityManager;
    }
}
