package Bomberman.gameObjects;

import Bomberman.Game;
import Bomberman.sounds.Playlist;
import Bomberman.gameObjects.creatures.Player;
import Bomberman.graphics.Animation;
import Bomberman.graphics.gallery.Resources;
import Bomberman.map.Map;

import java.awt.*;

public class Bomb extends GameObject {
    private final Animation wiggle = new Animation(Resources.bomb, 90000000);
    private long lastTime;
    private boolean isBombed;
    private final Player owner;
    private final int bombLength;
    private final int coorX;
    private final int coorY;
    private final char fire = 'f';
    private int check = 0;
    private int lUp = 0;
    private int lDown = 0;
    private int lLeft = 0;
    private int lRight = 0;

    public Bomb(Game game, Map gameMap, int x, int y, int bombLength, Player owner) {
        super(game, gameMap, x, y);
        this.bombLength = bombLength;
        this.owner = owner;
        isBombed = false;
        lastTime = System.currentTimeMillis();
        coorX = y / Resources.tHeight;
        coorY = x / Resources.tWidth;
        gameMap.setGameCoor(coorX, coorY, 'b');
        owner.plusCntBomb();
        //fixedDir();
    }

    private void fixedDir() {
        char[][] tmp_gameMap = gameMap.getGameMap();
        for (int i = 1; i <= bombLength; ++i) { // up
            int nowX = coorX - i;
            if (nowX < Map.MAP_HEIGHT && nowX >= 0) {
                if (tmp_gameMap[nowX][coorY] == '0' || tmp_gameMap[nowX][coorY] == 'b') {
                    lUp++;
                } else if (tmp_gameMap[nowX][coorY] == '1') {
                    lUp++;
                    break;
                } else if (tmp_gameMap[nowX][coorY] == '2') {
                    break;
                }
            }
        }

        for (int i = 1; i <= bombLength; ++i) { // down
            int nowX = coorX + i;
            if (nowX < Map.MAP_HEIGHT && nowX >= 0) {
                if (tmp_gameMap[nowX][coorY] == '0' || tmp_gameMap[nowX][coorY] == 'b') {
                    lDown++;
                } else if (tmp_gameMap[nowX][coorY] == '1') {
                    lDown++;
                    break;
                } else if (tmp_gameMap[nowX][coorY] == '2') {
                    break;
                }
            }
        }

        for (int i = 1; i <= bombLength; ++i) { // left
            int nowY = coorY - i;
            if (nowY < Map.MAP_WIDTH && nowY >= 0) {
                if (tmp_gameMap[coorX][nowY] == '0' || tmp_gameMap[coorX][nowY] == 'b') {
                    lLeft++;
                } else if (tmp_gameMap[coorX][nowY] == '1') {
                    lLeft++;
                    break;
                } else if (tmp_gameMap[coorX][nowY] == '2') {
                    break;
                }
            }
        }

        for (int i = 1; i <= bombLength; ++i) { // right
            int nowY = coorY + i;
            if (nowY < Map.MAP_WIDTH && nowY >= 0) {
                if (tmp_gameMap[coorX][nowY] == '0' || tmp_gameMap[coorX][nowY] == 'b') {
                    lRight++;
                } else if (tmp_gameMap[coorX][nowY] == '1') {
                    lRight++;
                    break;
                } else if (tmp_gameMap[coorX][nowY] == '2') {
                    break;
                }
            }
        }
    }

    public void setBombed(boolean bombed) {
        isBombed = bombed;
    }

    private void explode() {
        isBombed = false;
        char[][] tmp_gameMap = gameMap.getGameMap();
        char[][] tmp_bombMap =  gameMap.getBombMap();
        char[][] tmp_powerMap = gameMap.getPowerMap();
        tmp_bombMap[coorX][coorY] = fire;
        for (int i = 1; i <= lUp; ++i) { // up
            int nowX = coorX - i;
            tmp_bombMap[nowX][coorY] = fire;
            if (tmp_gameMap[nowX][coorY] == '0') {
                tmp_powerMap[nowX][coorY] = '0';
            }
            tmp_gameMap[nowX][coorY] = '0';
        }

        for (int i = 1; i <= lDown; ++i) { // down
            int nowX = coorX + i;
            tmp_bombMap[nowX][coorY] = fire;
            if (tmp_gameMap[nowX][coorY] == '0') {
                tmp_powerMap[nowX][coorY] = '0';
            }
            tmp_gameMap[nowX][coorY] = '0';
        }

        for (int i = 1; i <= lLeft; ++i) { // left
            int nowY = coorY - i;
            tmp_bombMap[coorX][nowY] = fire;
            if (tmp_gameMap[coorX][nowY] == '0') {
                tmp_powerMap[coorX][nowY] = '0';
            }
            tmp_gameMap[coorX][nowY] = '0';
        }

        for (int i = 1; i <= lRight; ++i) { // right
            int nowY = coorY + i;
            tmp_bombMap[coorX][nowY] = fire;
            if (tmp_gameMap[coorX][nowY] == '0') {
                tmp_powerMap[coorX][nowY] = '0';
            }
            tmp_gameMap[coorX][nowY] = '0';
        }
    }

    private void finish() {
        char[][] tmp_bombMap = gameMap.getBombMap();
        gameMap.setBombCoor(coorX, coorY, '0');
        gameMap.setGameCoor(coorX, coorY, '0');
        for (int i = 1; i <= lUp; ++i) { // up
            int nowX = coorX - i;
            tmp_bombMap[nowX][coorY] = '0';
        }

        for (int i = 1; i <= lDown; ++i) { // down
            int nowX = coorX + i;
            tmp_bombMap[nowX][coorY] = '0';
        }

        for (int i = 1; i <= lLeft; ++i) { // left
            int nowY = coorY - i;
            tmp_bombMap[coorX][nowY] = '0';
        }

        for (int i = 1; i <= lRight; ++i) { // right
            int nowY = coorY + i;
            tmp_bombMap[coorX][nowY] = '0';
        }
        owner.subCntBomb();
    }

    @Override
    public void update() {
        long timer = System.currentTimeMillis() - this.lastTime;
        wiggle.update();

        if ((timer > 2000 && timer < 2500 || gameMap.getBombCoor(coorX, coorY) == fire) && check == 0) {
            fixedDir();
            explode();
            Playlist.explosion.play();
            this.lastTime = System.currentTimeMillis();
            timer = System.currentTimeMillis() - this.lastTime;
            check = 1;
        }

        if (timer > 500 && check == 1) {
            finish();
            check = -1;
        }
    }

    @Override
    public void render(Graphics g) {
        if (isBombed) {
            g.drawImage(wiggle.getCurrentFrame(), x, y, Resources.tWidth, Resources.tHeight, null);
        }
    }
}
