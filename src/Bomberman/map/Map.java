package Bomberman.map;

import Bomberman.graphics.Animation;
import Bomberman.graphics.gallery.Resources;

import java.awt.*;
import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Map {
    private final String path;
    private char[][] gameMap;
    private char[][] bombMap;
    private char[][] powerMap;
    public static final int MAP_WIDTH = 21;
    public static final int MAP_HEIGHT = 14;

    private final Animation explosion = new Animation(Resources.explosion, 90000000);
    private final Animation power1 = new Animation(Resources.power1, 90000000);
    private final Animation power2 = new Animation(Resources.power2, 90000000);
    private final Animation power3 = new Animation(Resources.power3, 90000000);

    public Map(String path) {
        this.path = path;
        gameMap = new char[100][100];
        bombMap = new char[100][100];
        powerMap = new char[100][100];
    }

    public char[][] getPowerMap() {
        return powerMap;
    }

    public char[][] getBombMap() {
        return bombMap;
    }

    public char getBombCoor(int x, int y) {
        return bombMap[x][y];
    }

    public void setBombCoor(int x, int y, char c) {
        bombMap[x][y] = c;
    }

    public char[][] getGameMap() {
        return gameMap;
    }

    public char getGameCoor(int x, int y) {
        return gameMap[x][y];
    }

    public void setGameCoor(int x, int y, char c) {
        gameMap[x][y] = c;
    }

    public int getMAP_WIDTH() {
        return MAP_WIDTH;
    }

    public int getMAP_HEIGHT() {
        return MAP_HEIGHT;
    }

    public void update() {
        explosion.update();
        power1.update();
        power2.update();
        power3.update();
    }

    public void render(Graphics g) {
        g.drawImage(Resources.background2, 0, 0, null);
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
                }
                else {
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
                        if (rand_int < 24) {
                            if (rand_int % 3 == 0) {
                                powerMap[i][j] = '1';
                            } else if (rand_int % 3 == 1) {
                                powerMap[i][j] = '2';
                            } else {
                                powerMap[i][j] = '3';
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetMap() {
        loadMap(path);
    }
}
