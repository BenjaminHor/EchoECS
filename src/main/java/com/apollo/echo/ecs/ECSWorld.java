package com.apollo.echo.ecs;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ben on 2/7/14.
 */
public final class ECSWorld {
    private ECSSystemManager systemManager;
    private ECSEntityManager entityManager;

    public ECSWorld() {
        systemManager = new ECSSystemManager(this);
        entityManager = new ECSEntityManager(this);
    }

    public void process() {
        systemManager.process();
    }

    public void render() {
        systemManager.render();
    }

    // Entity Manager wrapper functions

    public ECSEntity createEntity() {
        return entityManager.createEntity();
    }

    public void addEntity(ECSEntity e) {
        entityManager.addEntity(e);
    }

    public void removeEntity(ECSEntity e) {
        entityManager.removeEntity(e);
    }

    public ArrayList<ECSEntity> getGroup(String UID) {
        return entityManager.getGroup(UID);
    }

    public ECSEntity getUniqueEntity(String UID) {
        return entityManager.getUniqueEntity(UID);
    }

    public void assignGroupID(String UID, ECSEntity e) {
        // Combine entity manager register and assign functions together
        entityManager.assignGroupID(UID, e);
    }

    public void assignUniqueID(String UID, ECSEntity e) {
        entityManager.assignUniqueID(UID, e);
    }

    // System Manager wrapper functions

    public void registerSystem(ECSSystem system) {
        systemManager.registerSystem(system);
    }

    public void removeSystem(String UID) {
        systemManager.removeSystem(UID);
    }

    public ECSSystem getSystem(String UID) {
        return systemManager.getSystem(UID);
    }

    public ArrayList<ECSSystem> getProcessSystems() {
        return systemManager.getProcessSystems();
    }

    public ArrayList<ECSSystem> getRenderSystems() {
        return systemManager.getRenderSystems();
    }

    public HashMap<String, ECSSystem> getSystemMap() {
        return systemManager.getSystemMap();
    }

    public ECSSystemManager getSystemManager() {
        return systemManager;
    }

    public ECSEntityManager getEntityManager() {
        return entityManager;
    }
}
