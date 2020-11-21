package Bomberman;

import Bomberman.graphics.gallery.Resources;
import Bomberman.graphics.Display;
import Bomberman.input.KeyManager;
import Bomberman.input.MouseManager;
import Bomberman.network.Client;
import Bomberman.network.Server;
import Bomberman.scenes.*;
import Bomberman.sounds.Playlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;

public class Game {
    public final Display window;
    public int width;
    public int height;

    private boolean running = true;

    private final GameScene1 gameScene1;
    private final GameScene2 gameScene2;
    private final GameScene3 gameScene3;
    private final MenuScene menuScene;
    private final OptionScene optionScene;
    private final ResultScene resultScene;

    private final KeyManager keyMN = new KeyManager();
    private final MouseManager mouseMN = new MouseManager();

    private final Font font;
    private int displayFPS;

    public Server server;
    public static boolean serverRunning = false;

    public Game(String title, int width, int height) {
        Resources.init();
        Playlist.init();
        Playlist.backgroundMusic.playBackground();
        this.width = width;
        this.height = height;
        window = new Display(title, width, height);
        window.getWindow().addKeyListener(keyMN);
        window.getWindow().addMouseListener(mouseMN);
        window.getWindow().addMouseMotionListener(mouseMN);
        window.getCanvas().addMouseListener(mouseMN);
        window.getCanvas().addMouseMotionListener(mouseMN);
        window.getWindow().addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                running = false;
                Playlist.close();
                System.out.println("close");
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        gameScene1 = new GameScene1(this);
        gameScene2 = new GameScene2(this);

        if (JOptionPane.showConfirmDialog(window.getWindow(), "Do you want to run the server") == 0) {
            server = new Server();
            server.start();
            serverRunning = true;
        } else {
            serverRunning = false;
        }
        Client.runClient();

        gameScene3 = new GameScene3(this);
        menuScene = new MenuScene(this);
        optionScene = new OptionScene(this);
        resultScene = new ResultScene(this);
        MyScene.setCurrentScene(menuScene);
        font = new Font(Font.MONOSPACED, Font.BOLD, 14);
    }

    public KeyManager getKeyMN() {
        return keyMN;
    }

    public MouseManager getMouseMN() {
        return mouseMN;
    }

    public GameScene1 getGameScene1() {
        return gameScene1;
    }

    public GameScene2 getGameScene2() {
        return gameScene2;
    }

    public GameScene3 getGameScene3() {
        return gameScene3;
    }

    public MenuScene getMenuScene() {
        return menuScene;
    }

    public OptionScene getOptionScene() {
        return optionScene;
    }

    public ResultScene getResultScene() {
        return resultScene;
    }

    private void update() {
        if (MyScene.getCurrentScene() != null) {
            MyScene.getCurrentScene().update();
        } else {
            System.out.println("no scene found");
        }
    }

    private void drawScreen() {
        BufferStrategy bs = window.getCanvas().getBufferStrategy();
        if (bs == null) {
            window.getCanvas().createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        //draw

        if (MyScene.getCurrentScene() != null) {
            MyScene.getCurrentScene().render(g);
        } else {
            System.out.println("No scene initialized!");
        }
        g.setFont(font);
        g.drawString("FPS: " + displayFPS, 862, 532);

        //end drawing
        bs.show();
        g.dispose();
    }

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
                displayFPS = ticks;
                ticks = 0;
                timer = 0;
            }
            try {
                Thread.sleep(10); // reduce the high cpu usage by half.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        running = false;
    }
}
