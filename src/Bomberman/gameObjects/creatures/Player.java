package Bomberman.gameObjects.creatures;

import Bomberman.Game;
import Bomberman.graphics.gallery.Minotaur;
import Bomberman.map.Map;
import Bomberman.sounds.Playlist;
import Bomberman.gameObjects.Bomb;
import Bomberman.graphics.gallery.Luigi;
import Bomberman.graphics.gallery.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends Creature {
    private final ArrayList<Bomb> bombs = new ArrayList<>();
    private int bagCapacity;
    private int cntBomb;
    private int bombLength;
    private final String name;
    public boolean input = false;

    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;
    public boolean toggle = false;

    private static int cnt = 0;
    private final int stable;

    public Player(Game game, Map gameMap, int x, int y, Resources asset) {
        super(game, gameMap, x, y, asset);
        current = asset.Down[0];
        cnt++;
        stable = cnt;
        hitBox.x = 6;
        hitBox.y = 9;
        hitBox.width = 20;
        hitBox.height = 20;
        cntBomb = 0;
        bagCapacity = 1;
        bombLength = 1;
        if (asset instanceof Luigi) {
            name = "Luigi";
        } else if (asset instanceof Minotaur) {
            name = "Minotaur";
        } else {
            name = "Nigga";
        }
    }

    @Override
    public void update() {
        if (alive) {
            yMove = 0;
            xMove = 0;
            toggleUpdate();
            up = false;
            down = false;
            left = false;
            right = false;
            toggle = false;
            checkAlive();
            aDown.update();
            aUp.update();
            aLeft.update();
            aRight.update();
            powerUpdate();
            if (stable == 1 || stable == 3 || input) {
                if (!game.getKeyMN().keys[KeyEvent.VK_SPACE]) {
                    if (game.getKeyMN().keys[KeyEvent.VK_W]) {
                        up = true;
                    }
                    if (game.getKeyMN().keys[KeyEvent.VK_S]) {
                        down = true;
                    }
                    if (game.getKeyMN().keys[KeyEvent.VK_A]) {
                        left = true;
                    }
                    if (game.getKeyMN().keys[KeyEvent.VK_D]) {
                        right = true;
                    }
                } else if (game.getKeyMN().keys[KeyEvent.VK_SPACE]) {
                    toggle = true;
                }
            } else if (stable == 2) {
                if (!game.getKeyMN().keys[KeyEvent.VK_P]) {
                    if (game.getKeyMN().keys[KeyEvent.VK_UP]) {
                        up = true;
                    }
                    if (game.getKeyMN().keys[KeyEvent.VK_DOWN]) {
                        down = true;
                    }
                    if (game.getKeyMN().keys[KeyEvent.VK_LEFT]) {
                        left = true;
                    }
                    if (game.getKeyMN().keys[KeyEvent.VK_RIGHT]) {
                        right = true;
                    }
                } else if (game.getKeyMN().keys[KeyEvent.VK_P]) {
                    toggle = true;
                }
            }
        }

        for (Bomb bomb : bombs) {
            bomb.update();
        }
        move();
    }

    private void toggleUpdate() {
        if (up) {
            current = asset.Up[0];
            yMove -= speed;
        }
        if (down) {
            current = asset.Down[0];
            yMove += speed;
        }
        if (left) {
            current = asset.Left[0];
            xMove -= speed;
        }
        if (right) {
            current = asset.Right[0];
            xMove += speed;
        }
        if (toggle) {
            if (cntBomb >= 0) {
                int xBomb = 0;
                int yBomb = 0;

                if ((x + hitBox.x + hitBox.width) % Resources.tWidth > Resources.tWidth / 2) {
                    yBomb = (x + hitBox.x + hitBox.width) / Resources.tWidth;
                } else if ((x + hitBox.x + hitBox.width) % Resources.tWidth <= Resources.tWidth / 2) {
                    yBomb = (x + hitBox.x) / Resources.tWidth;
                }

                if ((y + hitBox.y + hitBox.height) % Resources.tHeight > Resources.tWidth / 2) {
                    xBomb = (y + hitBox.y + hitBox.height) / Resources.tHeight;
                } else if ((y + hitBox.y + hitBox.height) % Resources.tHeight <= Resources.tWidth / 2) {
                    xBomb = (y + hitBox.y) / Resources.tHeight;
                }

                char gameCoor = gameMap.getGameCoor(xBomb, yBomb);

                if (cntBomb < bagCapacity && gameCoor == '0' &&
                        (xBomb != game.getGameScene1().getPortal().getY() / Resources.tWidth ||
                                yBomb != game.getGameScene1().getPortal().getX() / Resources.tWidth)) {
                    Playlist.setBomb.play();
                    Bomb bomb = new Bomb(game, gameMap, yBomb * Resources.tWidth,
                            xBomb * Resources.tHeight, bombLength, this);
                    bombs.add(bomb);
                    bomb.setBombed(true);
                }
            }
        }
    }

    private void powerUpdate() {
        char[][] tmp = gameMap.getPowerMap();

        if (tmp[(y + Resources.tWidth / 2) / Resources.tWidth][(x + Resources.pHeight / 2) / Resources.tWidth] == '1') {
            Playlist.eatPower.play();
            bombLength++;
            tmp[(y + Resources.tWidth / 2) / Resources.tWidth][(x + Resources.pHeight / 2) / Resources.tWidth] = '0';
        } else if (tmp[(y + Resources.tWidth / 2) / Resources.tWidth][(x + Resources.pHeight / 2) / Resources.tWidth] == '2') {
            Playlist.eatPower.play();
            speed ++;
            tmp[(y + Resources.tWidth / 2) / Resources.tWidth][(x + Resources.pHeight / 2) / Resources.tWidth] = '0';
        } else if (tmp[(y + Resources.tWidth / 2) / Resources.tWidth][(x + Resources.pHeight / 2) / Resources.tWidth] == '3') {
            Playlist.eatPower.play();
            bagCapacity++;
            tmp[(y + Resources.tWidth / 2) / Resources.tWidth][(x + Resources.pHeight / 2) / Resources.tWidth] = '0';
        }
    }

    public void plusCntBomb() {
        this.cntBomb += 1;
    }

    public void subCntBomb() {
        this.cntBomb -= 1;
    }

    public String getName() {
        return name;
    }

    public int getBagCapacity() {
        return bagCapacity;
    }

    public int getBombLength() {
        return bombLength;
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public void render(Graphics g) {
        if (alive) {
            g.drawImage(getFrame(), x, y, Resources.pWidth, Resources.pHeight, null);
            //g.setColor(Color.blue); // debugging purpose
            //g.fillRect(x + hitBox.x, y + hitBox.y, hitBox.width, hitBox.height); // debugging purpose
            for (Bomb bomb : bombs) {
                bomb.render(g);
            }
        } else {
            xMove = 0;
            yMove = 0;
            deadTimer = System.nanoTime();
            deadNow += System.nanoTime() - deadTimer;
            if (deadNow < 20000) {
                g.drawImage(Resources.dead, x, y, null);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        bombs.clear();
        cntBomb = 0;
        bombLength = 1;
        bagCapacity = 1;
        speed = DEFAULT_SPEED;
    }
}
