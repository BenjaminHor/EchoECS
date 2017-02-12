package com.apollo.echo.ecs;
/**
 * Created by Ben on 2/17/14.
 */

import java.util.ArrayList;

public abstract class ECSSystem {
    private String UID = "NULL"; // Maybe a default UID?
    private SystemType systemType = SystemType.PROCESS; // PROCESS by default
    private ArrayList<ECSEntity> entities = new ArrayList<ECSEntity>();
    private ArrayList<String> relevantComponents = new ArrayList<String>();
    private ArrayList<String> irrelevantComponents = new ArrayList<String>();
    private ECSSystemManager systemManager;
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
    public boolean checkEntity(ECSEntity e) {
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

    protected void registerRelevantComponents(Class... className) {
        for (int i = 0; i < className.length; i++) {
            if (!relevantComponents.contains(className[i])) relevantComponents.add(className[i].getName());
        }
    }

    public ArrayList<ECSEntity> getEntities() {
        return entities;
    }

    public String getUniqueID() {
        return UID;
    }

    public void setSystemManager(ECSSystemManager s) {
        this.systemManager = s;
    }

    public ECSSystemManager getSystemManager() {
        return systemManager;
    }

    public void addEntity(ECSEntity e) {
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
