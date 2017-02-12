package com.apollo.echo.ecs;

import java.awt.*;
import java.util.Random;

public class RenderSystem extends ECSSystem {
    private Graphics2D g;

    public RenderSystem() {
        setSystemType(SystemType.RENDER);
        setUniqueID(this.getClass().getName());

        registerRelevantComponents(RenderComponent.class);
    }

    Random rand = new Random();
    int ticks = 0;

    public void process() {
        g = Main.fetchGraphics();
        ticks++;

        VelocityComponent v;
        RenderComponent r;
        for (ECSEntity e : getEntities()) {
            v = (VelocityComponent) e.getComponent(VelocityComponent.class);
            r = (RenderComponent) e.getComponent(RenderComponent.class);

            if (r.isEnabled()) {
                g.drawImage(r.image, v.x, v.y, r.sizeX, r.sizeY, null);
                //g.setColor(r.c);
                //g.fill3DRect(v.x, v.y, r.sizeX, r.sizeY, true);
            }
        }
    }
}