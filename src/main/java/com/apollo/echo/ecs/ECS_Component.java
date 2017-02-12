package com.apollo.echo.ecs;

/**
 * Created by Ben on 2/17/14.
 */
public abstract class ECS_Component {
    private String UID = "NULL";
    private boolean enabled = true;

    public ECS_Component() {
        this.UID = this.getClass().getName();
    }

    public final String getUniqueID() {
        return UID;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public final boolean isEnabled() {
        return enabled;
    }
}
