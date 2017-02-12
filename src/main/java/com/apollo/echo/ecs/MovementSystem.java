package com.apollo.echo.ecs;

public class MovementSystem extends ECS_System {
    private final String velocityID = VelocityComponent.class.getName();

    public MovementSystem() {
        setSystemType(SystemType.PROCESS);
        setUniqueID(this.getClass().getName());

        registerRelevantComponents(velocityID);
    }

    public void process() {
        VelocityComponent v;

        for (ECS_Entity e : getEntities()) {
            v = (VelocityComponent) e.getComponent(velocityID);
            if (v.isEnabled()) {
                v.x += v.dx;
                if (v.x > 500 - 50 || v.x < 0) v.dx *= -1;
            }
        }
    }
}