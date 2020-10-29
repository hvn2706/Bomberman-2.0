package Bomberman.scenes;

import Bomberman.Game;

import java.awt.*;

public abstract class MyScene {
    private static MyScene currentScene = null;
    protected Game game;

    public MyScene(Game game) {
        this.game = game;
    }

    public static void setCurrentScene(MyScene currentScene) {
        MyScene.currentScene = currentScene;
    }

    public static MyScene getCurrentScene() {
        return currentScene;
    }

    public abstract void update();

    public abstract void render(Graphics g);
}
