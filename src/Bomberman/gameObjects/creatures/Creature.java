package Bomberman.gameObjects.creatures;

import Bomberman.Game;
import Bomberman.gameObjects.GameObject;
import Bomberman.graphics.Animation;
import Bomberman.graphics.gallery.Resources;

import java.awt.image.BufferedImage;

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

    protected boolean Alive;
    protected final float DEFAULT_SPEED = 5.0f;
    protected float speed = DEFAULT_SPEED;

    public Creature(Game game, int x, int y, Resources asset) {
        super(game, x, y);
        Alive = true;
        this.asset = asset;
        aDown  = new Animation(asset.Down, 60000000);
        aUp    = new Animation(asset.Up, 60000000);
        aLeft  = new Animation(asset.Left, 60000000);
        aRight = new Animation(asset.Right, 60000000);
    }

    public void checkAlive() {
        char[][] tmp = game.getGameMap().getBombMap();
        if (tmp[(y + hitBox.y) / Resources.tHeight][(x + hitBox.x) / Resources.tWidth] == 'f' ||
            tmp[(y + hitBox.y) / Resources.tHeight][(x + hitBox.x + hitBox.width) / Resources.tWidth] == 'f' ||
            tmp[(y + hitBox.y + hitBox.height) / Resources.tHeight][(x + hitBox.x + hitBox.width) / Resources.tWidth] == 'f' ||
            tmp[(y + hitBox.y + hitBox.height) / Resources.tHeight][(x + hitBox.x) / Resources.tWidth] == 'f') {
            Alive = false;
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
            tmp1 = game.getGameMap().getGameCoor(yy1, xx);
            tmp2 = game.getGameMap().getGameCoor(yy2, xx);
            if ((x + hitBox.x + hitBox.width) / Resources.tWidth == xx) {
                x += xMove;
            } else if (tmp1 == '0' && tmp2 == '0' || tmp1 == 'f' || tmp2 == 'f') {
                x += xMove;
            } else {
                x = xx * Resources.tWidth - hitBox.x - hitBox.width - 1;
                stand = true;
            }
        } else if (xMove < 0) { // left
            xx = (x + xMove + hitBox.x) / Resources.tWidth;
            tmp1 = game.getGameMap().getGameCoor(yy1, xx);
            tmp2 = game.getGameMap().getGameCoor(yy2, xx);
            if ((x + hitBox.x)/ Resources.tWidth == xx) {
                x += xMove;
            } else if (tmp1 == '0' && tmp2 == '0' || tmp1 == 'f' || tmp2 == 'f') {
                x += xMove;
            } else {
                x = (xx + 1) * Resources.tWidth - hitBox.x;
                stand = true;
            }
        }

        int xx1 = (x + hitBox.x) / Resources.tWidth;
        int xx2 = (x + hitBox.x + hitBox.width) / Resources.tWidth;
        int yy;

        if (yMove < 0) { // up
            yy = (y + yMove + hitBox.y) / Resources.tHeight;
            tmp1 = game.getGameMap().getGameCoor(yy, xx1);
            tmp2 = game.getGameMap().getGameCoor(yy, xx2);
            if ((y + hitBox.y)/ Resources.tHeight == yy) {
                y += yMove;
            } else if (tmp1 == '0' && tmp2 == '0' || tmp1 == 'f' || tmp2 == 'f') {
                y += yMove;
            } else {
                y = (yy + 1) * Resources.tHeight - hitBox.y;
                stand = true;
            }
        } else if (yMove > 0) { // down
            yy = (y + yMove + hitBox.y + hitBox.height) / Resources.tHeight;
            tmp1 = game.getGameMap().getGameCoor(yy, xx1);
            tmp2 = game.getGameMap().getGameCoor(yy, xx2);
            if ((y + hitBox.y + hitBox.height)/ Resources.tHeight == yy) {
                y += yMove;
            } else if (tmp1 == '0' && tmp2 == '0' || tmp1 == 'f' || tmp2 == 'f') {
                y += yMove;
            } else {
                y = yy * Resources.tHeight - hitBox.y - hitBox.height - 1;
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
        Alive = true;
    }
}
