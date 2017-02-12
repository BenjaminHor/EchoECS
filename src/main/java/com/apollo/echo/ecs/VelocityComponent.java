package com.apollo.echo.ecs;

public class VelocityComponent extends ECSComponent {
    public int x, y, dx, dy;

    public VelocityComponent(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }
}