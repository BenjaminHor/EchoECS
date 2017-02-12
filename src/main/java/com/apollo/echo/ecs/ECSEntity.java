package com.apollo.echo.ecs;
/**
 * Created by Ben on 2/17/14.
 */

import java.util.HashMap;
import java.util.Map;

public final class ECSEntity {
    private Map<String, ECSComponent> componentMap = new HashMap<String, ECSComponent>();
    private String groupID = "NULL";
    private String uniqueID = "NULL";
    private boolean removed = false;

    public void setGroupID(String UID) {
        groupID = UID;
    }

    public void setUniqueID(String UID) {
        uniqueID = UID;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void remove() {
        if (!removed) removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void addComponent(ECSComponent c) {
        boolean compExists = componentMap.containsKey(c.getUniqueID());
        if (!compExists) {
            componentMap.put(c.getUniqueID(), c);
        } else System.out.println(c.getUniqueID() + " could not be added");
    }

    public void removeComponent(Class className) {
        String id = className.getName();
        boolean compExists = componentMap.containsKey(id);
        if (compExists) {
            componentMap.remove(id);
        } else System.out.println(id + " could not be removed");
    }

    public ECSComponent getComponent(Class className) {
        String id = className.getName();
        boolean compExists = componentMap.containsKey(id);
        if (compExists) return componentMap.get(id);
        else {
            System.out.println(id + " could not be retrieved");
            return null;
        }
    }

    public boolean hasComponent(String UID) {
        return componentMap.containsKey(UID);
    }
}
