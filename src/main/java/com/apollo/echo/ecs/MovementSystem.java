package com.apollo.echo.ecs;

public class MovementSystem extends ECSSystem {
    public MovementSystem() {
        setSystemType(SystemType.PROCESS);

        registerRelevantComponents(VelocityComponent.class);
    }

    public void process() {
        VelocityComponent v;

        for (ECSEntity e : getEntities()) {
            v = (VelocityComponent) e.getComponent(VelocityComponent.class);
            if (v.isEnabled()) {
                v.x += v.dx;
                if (v.x > 500 - 50 || v.x < 0) v.dx *= -1;
            }
        }
    }
}