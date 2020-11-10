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
    private final long lastTime;
    private boolean isBombed;
    private final Player owner;
    private final int bombLength;
    private final int coorX;
    private final int coorY;
    private final char fire = 'f';
    private int check = 0;

    public Bomb(Game game, Map gameMap, int x, int y, int bombLength, Player owner) {
        super(game, gameMap, x, y);
        this.bombLength = bombLength;
        this.owner = owner;
        isBombed = false;
        lastTime = System.nanoTime();
        coorX = y / Resources.tHeight;
        coorY = x / Resources.tWidth;
        gameMap.setGameCoor(coorX, coorY, 'b');
        owner.plusCntBomb();
    }

    public void setBombed(boolean bombed) {
        isBombed = bombed;
    }

    public void explode() {
        isBombed = false;
        char[][] tmp_gameMap = gameMap.getGameMap();
        char[][] tmp_bombMap =  gameMap.getBombMap();
        tmp_bombMap[coorX][coorY] = fire;
        for (int i = 0; i <= bombLength; ++i) { // up
            int nowX = coorX - i;
            if (nowX < Map.MAP_HEIGHT && nowX >= 0) {
                if (tmp_gameMap[nowX][coorY] == '0' || tmp_gameMap[nowX][coorY] == 'b') {
                    tmp_bombMap[nowX][coorY] = fire;
                } else if (tmp_gameMap[nowX][coorY] == '1') {
                    tmp_bombMap[nowX][coorY] = fire;
                    break;
                } else if (tmp_gameMap[nowX][coorY] == '2') {
                    break;
                }
            }
        }

        for (int i = 0; i <= bombLength; ++i) { // down
            int nowX = coorX + i;
            if (nowX < Map.MAP_HEIGHT && nowX >= 0) {
                if (tmp_gameMap[nowX][coorY] == '0' || tmp_gameMap[nowX][coorY] == 'b') {
                    tmp_bombMap[nowX][coorY] = fire;
                } else if (tmp_gameMap[nowX][coorY] == '1') {
                    tmp_bombMap[nowX][coorY] = fire;
                    break;
                } else if (tmp_gameMap[nowX][coorY] == '2') {
                    break;
                }
            }
        }

        for (int i = 0; i <= bombLength; ++i) { // left
            int nowY = coorY - i;
            if (nowY < Map.MAP_WIDTH && nowY >= 0) {
                if (tmp_gameMap[coorX][nowY] == '0' || tmp_gameMap[coorX][nowY] == 'b') {
                    tmp_bombMap[coorX][nowY] = fire;
                } else if (tmp_gameMap[coorX][nowY] == '1') {
                    tmp_bombMap[coorX][nowY] = fire;
                    break;
                } else if (tmp_gameMap[coorX][nowY] == '2') {
                    break;
                }
            }
        }

        for (int i = 0; i <= bombLength; ++i) { // right
            int nowY = coorY + i;
            if (nowY < Map.MAP_WIDTH && nowY >= 0) {
                if (tmp_gameMap[coorX][nowY] == '0' || tmp_gameMap[coorX][nowY] == 'b') {
                    tmp_bombMap[coorX][nowY] = fire;
                } else if (tmp_gameMap[coorX][nowY] == '1') {
                    tmp_bombMap[coorX][nowY] = fire;
                    break;
                } else if (tmp_gameMap[coorX][nowY] == '2') {
                    break;
                }
            }
        }
    }

    public void finish() {
        char[][] tmp = gameMap.getGameMap();
        char[][] tmp_ = gameMap.getBombMap();
        char[][] tmp__ = gameMap.getPowerMap();
        gameMap.setBombCoor(coorX, coorY, '0');
        gameMap.setGameCoor(coorX, coorY, '0');
        for (int i = 0; i < Map.MAP_HEIGHT; ++i) {
            for (int j = 0; j < Map.MAP_WIDTH; ++j) {
                if (tmp_[i][j] == fire) {
                    if (tmp[i][j] == '0') {
                        tmp__[i][j] = '0';
                    }
                    tmp[i][j] = '0';
                    tmp_[i][j] = '0';
                }
            }
        }
        owner.subCntBomb();
    }

    @Override
    public void update() {
        long timer = System.nanoTime() - lastTime;
        wiggle.update();

        if (timer > 2000000000.0 && timer < 2500000000.0) {
            if (check == 0) {
                Playlist.explosion.play();
                check = 1;
            }
            explode();
        }

        if (timer > 2500000000.0 && check == 1) {
            check = -1;
            finish();
        }
    }

    @Override
    public void render(Graphics g) {
        if (isBombed) {
            g.drawImage(wiggle.getCurrentFrame(), x, y, Resources.tWidth, Resources.tHeight, null);
        }
    }
}
