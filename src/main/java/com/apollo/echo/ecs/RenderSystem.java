package com.apollo.echo.ecs;

import java.awt.*;
import java.util.Random;

public class RenderSystem extends ECS_System {
    private final String velocityID = VelocityComponent.class.getName();
    private final String renderID = RenderComponent.class.getName();
    private Graphics2D g;

    public RenderSystem() {
        setSystemType(SystemType.RENDER);
        setUniqueID(this.getClass().getName());

        registerRelevantComponents(renderID);
    }

    Random rand = new Random();
    int ticks = 0;

    public void process() {
        g = Main.fetchGraphics();
        ticks++;

        VelocityComponent v;
        RenderComponent r;
        for (ECS_Entity e : getEntities()) {
            v = (VelocityComponent) e.getComponent(velocityID);
            r = (RenderComponent) e.getComponent(renderID);

            if (r.isEnabled()) {
                g.drawImage(r.image, v.x, v.y, r.sizeX, r.sizeY, null);
                //g.setColor(r.c);
                //g.fill3DRect(v.x, v.y, r.sizeX, r.sizeY, true);
            }
        }
    }
}