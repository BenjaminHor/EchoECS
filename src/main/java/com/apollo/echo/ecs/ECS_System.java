package com.apollo.echo.ecs;
/**
 * Created by Ben on 2/17/14.
 */

import java.util.ArrayList;

public abstract class ECS_System {
    private String UID = "NULL"; // Maybe a default UID?
    private SystemType systemType = SystemType.PROCESS; // PROCESS by default
    private ArrayList<ECS_Entity> entities = new ArrayList<ECS_Entity>();
    private ArrayList<String> relevantComponents = new ArrayList<String>();
    private ArrayList<String> irrelevantComponents = new ArrayList<String>();
    private ECS_SystemManager systemManager;
    private boolean enabled = true;

    public enum SystemType {
        PROCESS, RENDER;
    }

    protected void setUniqueID(String UID) {
        this.UID = UID;
    }

    protected void setSystemType(SystemType t) {
        systemType = t;
    }

    public SystemType getSystemType() {
        return systemType;
    }

    public abstract void process();

    /*
     * Process an entity if it contains the relevant components to the system
     * but none of the rejected components
     */
    public boolean checkEntity(ECS_Entity e) {
        boolean valid = false;
        for (String s : irrelevantComponents) {
            valid = e.hasComponent(s);
            if (valid) return !valid;
        }
        for (String s : relevantComponents) {
            valid = e.hasComponent(s);
            if (!valid) return valid;
        }
        return valid;
    }

    protected void registerRelevantComponents(String... UID) {
        for (int i = 0; i < UID.length; i++) {
            if (!relevantComponents.contains(UID[i])) relevantComponents.add(UID[i]);
        }
    }

    protected void registerIrrelevantComponents(String... UID) {
        for (int i = 0; i < UID.length; i++) {
            if (!irrelevantComponents.contains(UID[i])) irrelevantComponents.add(UID[i]);
        }
    }

    public ArrayList<ECS_Entity> getEntities() {
        return entities;
    }

    public String getUniqueID() {
        return UID;
    }

    public void setSystemManager(ECS_SystemManager s) {
        this.systemManager = s;
    }

    public ECS_SystemManager getSystemManager() {
        return systemManager;
    }

    public void addEntity(ECS_Entity e) {
        if (!entities.contains(e)) {
            entities.add(e);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean trigger) {
        enabled = trigger;
    }
}
