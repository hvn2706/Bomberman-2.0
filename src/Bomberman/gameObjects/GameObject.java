package Bomberman.gameObjects;

import Bomberman.Game;
import Bomberman.graphics.gallery.Resources;
import Bomberman.map.Map;

import java.awt.*;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected final int resetX;
    protected final int resetY;
    protected Game game;
    protected Map gameMap;
    protected Rectangle hitBox;

    public GameObject(Game game, Map gameMap, int x, int y) {
        this.x = x;
        this.y = y;
        resetX = x;
        resetY = y;
        this.game = game;
        this.gameMap = gameMap;
        hitBox = new Rectangle(0, 0, Resources.tWidth, Resources.tHeight);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getResetX() {
        return resetX;
    }

    public int getResetY() {
        return resetY;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public abstract void update();

    public abstract void render(Graphics g);
}
