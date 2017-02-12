package com.apollo.echo.ecs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Main extends Canvas {
    private static final long serialVersionUID = 1L;
    private static JFrame window = new JFrame();

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "True");
        Main main = new Main();

        window.add(main);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
        window.pack();
        window.setLocationRelativeTo(null);
        window.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_Q) {
                    gameWorld.getSystem(RenderSystem.class.getName()).setEnabled(false);
                }
                if (arg0.getKeyCode() == KeyEvent.VK_W) {
                    gameWorld.getSystem(RenderSystem.class.getName()).setEnabled(true);
                }
                if (arg0.getKeyCode() == KeyEvent.VK_E) {
                    gameWorld.getSystem(MovementSystem.class.getName()).setEnabled(false);
                }
                if (arg0.getKeyCode() == KeyEvent.VK_R) {
                    gameWorld.getSystem(MovementSystem.class.getName()).setEnabled(true);
                }
            }

            public void keyReleased(KeyEvent arg0) {
                // TODO Auto-generated method stub

            }

            public void keyTyped(KeyEvent arg0) {
                // TODO Auto-generated method stub

            }

        });

        main.start();
    }

    private BufferStrategy bufferStrategy;
    private static Graphics2D graphicsContext;
    private static ECS_World gameWorld;
    BufferedImage image;

    public Main() {
        setPreferredSize(new Dimension(500, 500));
        setIgnoreRepaint(true);
        try {
            image = ImageIO.read(getClass().getClassLoader().getResource("SpriteSheet.png"));
            image = image.getSubimage(0, 32 * 2, 32, 32);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // TEST RUN
        gameWorld = new ECS_World();

        gameWorld.registerSystem(new MovementSystem());
        gameWorld.registerSystem(new RenderSystem());
    }

    public void start() {
        createBufferStrategy(2);
        bufferStrategy = getBufferStrategy();
        run();
    }

    private final double NANO_TO_MS = 1000000.0;
    private static int rate = 60;

    public void run() {
        double currTime;
        double prevTime = System.nanoTime();
        double FPSTIMER = System.nanoTime();
        double delta = 1000000000.0 / 60;
        int processes = 0, frames = 0;

        while (true) {
            currTime = System.nanoTime();
            if (currTime >= prevTime) {
                process();
                processes++;
                prevTime += delta;
                if (currTime < prevTime && processes % (60 / rate) == 0) {
                    render();
                    frames++;
                }
            } else {
                try {
                    Thread.sleep((long) ((prevTime - currTime) / NANO_TO_MS));
                } catch (Exception e) {
                }
            }

            if (System.nanoTime() - FPSTIMER > 1000000000.0) {
                //System.out.println("Process: " + processes + " FPS: " + frames);
                window.setTitle("Process: " + processes + " FPS: " + frames + " POLYGONS: " + polygons);
                processes = frames = 0;
                FPSTIMER += 1000000000.0;
            }
        }
    }

    private Random r = new Random();
    private int ticks = 0;
    private int polygons = 0;

    public void process() {
        gameWorld.process();
        ticks++;

        //if(ticks % (60 / 2) == 0){
        ECS_Entity e = gameWorld.createEntity();
        e.addComponent(new VelocityComponent(200, r.nextInt(500 - 50), r.nextInt(3) + 1, 6));
        e.addComponent(new RenderComponent(50, 50, new Color(r.nextInt(150), r.nextInt(1), r.nextInt(255)), image));
        gameWorld.addEntity(e);
        //}
    }

    public void render() {
        graphicsContext = (Graphics2D) bufferStrategy.getDrawGraphics();
        graphicsContext.setColor(Color.GRAY);
        graphicsContext.fillRect(0, 0, 500, 500);

        // Render here
        gameWorld.render();

        bufferStrategy.show();
        graphicsContext.dispose();
    }

    public static Graphics2D fetchGraphics() {
        return graphicsContext;
    }
}