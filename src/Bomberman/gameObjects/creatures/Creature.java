package Bomberman.gameObjects.creatures;

import Bomberman.Game;
import Bomberman.gameObjects.GameObject;
import Bomberman.gameObjects.creatures.enemy.Enemy;
import Bomberman.graphics.Animation;
import Bomberman.graphics.gallery.Resources;
import Bomberman.map.Map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Creature extends GameObject {
    protected int xMove;
    protected int yMove;
    protected boolean stand;

    protected final Resources asset;
    protected final Animation aDown;
    protected final Animation aUp;
    protected final Animation aLeft;
    protected final Animation aRight;
    protected BufferedImage current;

    public boolean alive;
    protected final float DEFAULT_SPEED = 4.0f;
    protected float speed = DEFAULT_SPEED;
    protected final long frameSpeed = 60000000;
    protected long deadTimer;
    protected long deadNow = 0;

    public Creature(Game game, Map gameMap, int x, int y, Resources asset) {
        super(game, gameMap, x, y);
        alive = true;
        this.asset = asset;
        aDown  = new Animation(asset.Down, frameSpeed);
        aUp    = new Animation(asset.Up, frameSpeed);
        aLeft  = new Animation(asset.Left, frameSpeed);
        aRight = new Animation(asset.Right, frameSpeed);
    }

    private boolean intersect(Rectangle r1, Rectangle r2) {
        if (r1.x > r2.x + r2.width || r2.x > r1.x + r1.width) {
            return false;
        }

        if (r1.y > r2.y + r2.height || r2.y > r1.y + r1.height) {
            return false;
        }

        return true;
    }

    public void hitMeBabe(ArrayList<Enemy> enemies) {
        for (Enemy en : enemies) {
            Rectangle r1 = new Rectangle(this.hitBox);
            r1.x += this.x;
            r1.y += this.y;
            Rectangle r2 = new Rectangle(en.hitBox);
            r2.x += en.getX();
            r2.y += en.getY();
            if (intersect(r1, r2) && en.alive) {
                alive = false;
                break;
            }
        }
    }

    public void checkAlive() {
        char[][] tmp = gameMap.getBombMap();
        if (tmp[(y + hitBox.y) / Resources.tHeight][(x + hitBox.x) / Resources.tWidth] == 'f' ||
            tmp[(y + hitBox.y) / Resources.tHeight][(x + hitBox.x + hitBox.width) / Resources.tWidth] == 'f' ||
            tmp[(y + hitBox.y + hitBox.height) / Resources.tHeight][(x + hitBox.x + hitBox.width) / Resources.tWidth] == 'f' ||
            tmp[(y + hitBox.y + hitBox.height) / Resources.tHeight][(x + hitBox.x) / Resources.tWidth] == 'f') {
            alive = false;
        }
    }

    public BufferedImage getFrame() {
        if (yMove > 0) {
            return aDown.getCurrentFrame();
        } else if (yMove < 0) {
            return aUp.getCurrentFrame();
        } else if (xMove < 0) {
            return aLeft.getCurrentFrame();
        } else if (xMove > 0) {
            return aRight.getCurrentFrame();
        }
        return current;
    }

    public void move() {
        int yy1 = (y + hitBox.y) / Resources.tHeight;
        int yy2 = (y + hitBox.y + hitBox.height) / Resources.tHeight;
        char tmp1;
        char tmp2;
        stand = false;
        int xx;

        if (xMove > 0) { // right
            xx = (x + xMove + hitBox.x + hitBox.width) / Resources.tWidth;
            if (this instanceof Enemy) {
                xx = (x + xMove + hitBox.x + hitBox.width + 16) / Resources.tWidth;
            }
            tmp1 = gameMap.getGameCoor(yy1, xx);
            tmp2 = gameMap.getGameCoor(yy2, xx);
            if ((x + hitBox.x + hitBox.width) / Resources.tWidth == xx) {
                x += xMove;
            } else if (tmp1 == '0' && tmp2 == '0' || tmp1 == 'f' || tmp2 == 'f') {
                x += xMove;
            } else {
                if (this instanceof Player) {
                    x = xx * Resources.tWidth - hitBox.x - hitBox.width - 1;
                }
                stand = true;
            }
        } else if (xMove < 0) { // left
            xx = (x + xMove + hitBox.x) / Resources.tWidth;
            if (this instanceof Enemy) {
                xx = (x + xMove + hitBox.x - 16) / Resources.tWidth;
            }
            tmp1 = gameMap.getGameCoor(yy1, xx);
            tmp2 = gameMap.getGameCoor(yy2, xx);
            if ((x + hitBox.x)/ Resources.tWidth == xx) {
                x += xMove;
            } else if (tmp1 == '0' && tmp2 == '0' || tmp1 == 'f' || tmp2 == 'f') {
                x += xMove;
            } else {
                if (this instanceof Player) {
                    x = (xx + 1) * Resources.tWidth - hitBox.x;
                }
                stand = true;
            }
        }

        int xx1 = (x + hitBox.x) / Resources.tWidth;
        int xx2 = (x + hitBox.x + hitBox.width) / Resources.tWidth;
        int yy;

        if (yMove < 0) { // up
            yy = (y + yMove + hitBox.y) / Resources.tHeight;
            if (this instanceof Enemy) {
                yy = (y + yMove + hitBox.y - 16) / Resources.tHeight;
            }
            tmp1 = gameMap.getGameCoor(yy, xx1);
            tmp2 = gameMap.getGameCoor(yy, xx2);
            if ((y + hitBox.y)/ Resources.tHeight == yy) {
                y += yMove;
            } else if (tmp1 == '0' && tmp2 == '0' || tmp1 == 'f' || tmp2 == 'f') {
                y += yMove;
            } else {
                if (this instanceof Player) {
                    y = (yy + 1) * Resources.tHeight - hitBox.y;
                }
                stand = true;
            }
        } else if (yMove > 0) { // down
            yy = (y + yMove + hitBox.y + hitBox.height) / Resources.tHeight;
            if (this instanceof Enemy) {
                yy = (y + yMove + hitBox.y + hitBox.height + 16) / Resources.tHeight;
            }
            tmp1 = gameMap.getGameCoor(yy, xx1);
            tmp2 = gameMap.getGameCoor(yy, xx2);
            if ((y + hitBox.y + hitBox.height)/ Resources.tHeight == yy) {
                y += yMove;
            } else if (tmp1 == '0' && tmp2 == '0' || tmp1 == 'f' || tmp2 == 'f') {
                y += yMove;
            } else {
                if (this instanceof Player) {
                    y = yy * Resources.tHeight - hitBox.y - hitBox.height - 1;
                }
                stand = true;
            }
        }

        if (xMove == 0 && yMove == 0) {
            stand = true;
        }
    }

    public void reset() {
        x = resetX;
        y = resetY;
        alive = true;
        deadTimer = 0;
        deadNow = 0;
    }
}
