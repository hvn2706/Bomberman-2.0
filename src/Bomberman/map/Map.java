package Bomberman.map;

import Bomberman.gameObjects.Portal;
import Bomberman.graphics.Animation;
import Bomberman.graphics.gallery.Resources;

import java.awt.*;
import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Map {
    private final String path;
    private final char[][] gameMap;
    private final char[][] bombMap;
    private final char[][] powerMap;
    private final int chance;
    private Portal portal;
    public static final int MAP_WIDTH = 21;
    public static final int MAP_HEIGHT = 14;

    private final Animation explosion = new Animation(Resources.explosion, 90000000);
    private final Animation power1 = new Animation(Resources.power1, 90000000);
    private final Animation power2 = new Animation(Resources.power2, 90000000);
    private final Animation power3 = new Animation(Resources.power3, 90000000);

    public Map(String path, int chance) {
        this.path = path;
        this.chance = chance;
        gameMap = new char[100][100];
        bombMap = new char[100][100];
        powerMap = new char[100][100];
        loadMap(path);
    }

    public void setPortal(Portal portal) {
        this.portal = portal;
    }

    public char[][] getGameMap() {
        return gameMap;
    }

    public char[][] getPowerMap() {
        return powerMap;
    }

    public char[][] getBombMap() {
        return bombMap;
    }

    public void setBombCoor(int x, int y, char c) {
        bombMap[x][y] = c;
    }

    public char getBombCoor(int x, int y) {
        return bombMap[x][y];
    }

    public void setPowerCoor(int x, int y, char c) {
        powerMap[x][y] = c;
    }

    public char getGameCoor(int x, int y) {
        return gameMap[x][y];
    }

    public void setGameCoor(int x, int y, char c) {
        gameMap[x][y] = c;
    }

    public void update() {
        explosion.update();
        power1.update();
        power2.update();
        power3.update();
    }

    public void render(Graphics g) {
        g.drawImage(Resources.background2, 0, 0, null);
        if (portal != null) {
            portal.render(g);
        }
        for (int i = 0; i < MAP_HEIGHT; ++i) {
            for (int j = 0; j < MAP_WIDTH; ++j) {
                if (powerMap[i][j] == '1') {
                    g.drawImage(power1.getCurrentFrame(), j * Resources.tHeight,
                            i * Resources.tWidth, null);
                } else if (powerMap[i][j] == '2') {
                    g.drawImage(power2.getCurrentFrame(), j * Resources.tHeight,
                            i * Resources.tWidth, null);
                } else if (powerMap[i][j] == '3') {
                    g.drawImage(power3.getCurrentFrame(), j * Resources.tHeight,
                            i * Resources.tWidth, null);
                }

                if (bombMap[i][j] == 'f') {
                    g.drawImage(explosion.getCurrentFrame(), j * Resources.tHeight,
                            i * Resources.tWidth, null);
                } else {
                    if (gameMap[i][j] == '2') {
                        g.drawImage(Resources.greyBrick, j * Resources.tHeight,
                                i * Resources.tWidth, null);
                    } else if (gameMap[i][j] == '1') {
                        g.drawImage(Resources.chest, j * Resources.tHeight,
                                i * Resources.tWidth, null);
                    }
                }
            }
        }
    }

    private void loadMap(String path) {
        for (int i = 0; i < MAP_HEIGHT; ++i) {
            for (int j = 0; j < MAP_WIDTH; ++j) {
                bombMap[i][j] = '0';
                powerMap[i][j] = '0';
            }
        }
        try {
            File inp = new File(path);
            Scanner sc = new Scanner(inp);
            String tmp;
            Random rand = new Random();

            for (int i = 0; i < MAP_HEIGHT; ++i) {
                tmp = sc.nextLine();
                for (int j = 0; j < MAP_WIDTH; ++j) {
                    gameMap[i][j] = tmp.charAt(j);
                    if (gameMap[i][j] == '1') {
                        int rand_int = rand.nextInt(100);
                        if (rand_int < chance) {
                            rand_int = rand.nextInt(5);
                            if (rand_int == 0 || rand_int == 1) {
                                powerMap[i][j] = '1';
                            } else if (rand_int == 2 || rand_int == 3) {
                                powerMap[i][j] = '2';
                            } else {
                                powerMap[i][j] = '3';
                            }
                        }
                    }
                }
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetMap() {
        loadMap(path);
    }
}