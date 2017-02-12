package com.apollo.echo.ecs;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderComponent extends ECSComponent {
    public int sizeX, sizeY;
    public Color c;
    public BufferedImage image;

    public RenderComponent(int x, int y, Color c, BufferedImage i) {
        this.sizeX = x;
        this.sizeY = y;
        this.c = c;
        this.image = i;
    }
}