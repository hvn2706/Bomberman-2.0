package Bomberman.gameObjects.creatures.enemy;

import Bomberman.Game;
import Bomberman.gameObjects.creatures.Creature;
import Bomberman.graphics.gallery.Resources;

import java.awt.*;

public abstract class Enemy extends Creature {
    protected int choice; // = 1 = up, = 2 = down, = 3 = left, = 4 = right

    public Enemy(Game game, int x, int y, Resources asset) {
        super(game, x, y, asset);
        current = asset.Down[0];
        hitBox.x = 10;
        hitBox.y = 18;
        hitBox.width = 30;
        hitBox.height = 32;
    }

    public abstract void makeChoice();

    @Override
    public void update() {
        if (alive) {
            xMove = 0;
            yMove = 0;
            checkAlive();
            aDown.update();
            aUp.update();
            aLeft.update();
            aRight.update();
            makeChoice();

            if (choice == 1) { // up
                yMove -= speed;
            } else if (choice == 2) { // down
                yMove += speed;
            } else if (choice == 3) { // left
                xMove -= speed;
            } else { // right
                xMove += speed;
            }
        }
        move();
    }

    @Override
    public void render(Graphics g) {
        if (alive) {
            g.drawImage(getFrame(), x, y, Resources.pWidth, Resources.pHeight, null);
            //g.setColor(Color.blue); // debugging purpose
            //g.fillRect(x + hitBox.x, y + hitBox.y, hitBox.width, hitBox.height); // debugging purpose
        } else {
            xMove = 0;
            yMove = 0;
            deadTimer = System.nanoTime();
            deadNow += System.nanoTime() - deadTimer;
            if (deadNow < 25000) {
                g.drawImage(Resources.dead, x, y, null);
            }
        }
    }
}
