package Bomberman;

import Bomberman.graphics.gallery.Resources;
import Bomberman.graphics.Display;
import Bomberman.input.KeyManager;
import Bomberman.input.MouseManager;
import Bomberman.map.Map;
import Bomberman.scenes.*;
import Bomberman.sounds.Playlist;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    public final Display window;
    public int width;
    public int height;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    private final GameScene1 gameScene1;
    private final GameScene2 gameScene2;
    private final MenuScene menuScene;
    private final ResultScene resultScene;
    private final Map gameMap;

    private final KeyManager keyMN = new KeyManager();
    private final MouseManager mouseMN = new MouseManager();

    public Game(String title, int width, int height) {
        Resources.init();
        Playlist.init();
        Playlist.backgroundMusic.playBackground();
        gameMap = new Map("resources/map1.txt");
        this.width = width;
        this.height = height;
        window = new Display(title, width, height);
        window.getWindow().addKeyListener(keyMN);
        window.getWindow().addMouseListener(mouseMN);
        window.getWindow().addMouseMotionListener(mouseMN);
        window.getCanvas().addMouseListener(mouseMN);
        window.getCanvas().addMouseMotionListener(mouseMN);
        gameScene1 = new GameScene1(this);
        gameScene2 = new GameScene2(this);
        menuScene = new MenuScene(this);
        resultScene = new ResultScene(this);
        MyScene.setCurrentScene(menuScene);
    }

    public KeyManager getKeyMN() {
        return keyMN;
    }

    public MouseManager getMouseMN() {
        return mouseMN;
    }

    private void update() {
        if (MyScene.getCurrentScene() != null) {
            gameMap.update();
            MyScene.getCurrentScene().update();
        } else {
            System.out.println("no scene found");
        }
    }

    private void drawScreen() {
        bs = window.getCanvas().getBufferStrategy();
        if (bs == null) {
            window.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        //draw

        if (MyScene.getCurrentScene() != null) {
            gameMap.render(g);
            MyScene.getCurrentScene().render(g);
        } else {
            System.out.println("No scene initialized!");
        }

        //end drawing
        bs.show();
        g.dispose();
    }

    public GameScene1 getGameScene1() {
        return gameScene1;
    }

    public GameScene2 getGameScene2() {
        return gameScene2;
    }

    public MenuScene getMenuScene() {
        return menuScene;
    }

    public ResultScene getResultScene() {
        return resultScene;
    }

    public Map getGameMap() {
        return gameMap;
    }

    @Override
    public void run() {
        int fps = 60;
        double perFrame = 1000000000 / (double) fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / perFrame;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                update();
                drawScreen();
                ticks++;
                delta--;
            }

            if (timer >= 1000000000) {
                System.out.println("fps:" + ticks);
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
