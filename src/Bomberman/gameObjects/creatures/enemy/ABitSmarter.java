package Bomberman.gameObjects.creatures.enemy;

import Bomberman.Game;
import Bomberman.gameObjects.creatures.Player;
import Bomberman.graphics.gallery.Resources;
import Bomberman.map.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class ABitSmarter extends Enemy {
    private final PriorityQueue<MyPair> pq;
    private final Point[][] parent;
    private final ArrayList<Point> path;
    private final Random rand = new Random();
    private final boolean[][] check;
    private final int[][] dist;
    private char[][] map;
    private Player player;
    private int coorX;
    private int coorY;
    private long lastTime;
    private long timer;

    public ABitSmarter(Game game, Map gameMap, int x, int y, Resources asset) {
        super(game, gameMap, x, y, asset);
        pq = new PriorityQueue<>(300, new Cmp());
        path = new ArrayList<>();
        dist = new int[100][100];
        parent = new Point[100][100];
        check = new boolean[100][100];
        lastTime = System.nanoTime();
    }

    public void dijkstra() {
        for(int i = 0; i < Map.MAP_HEIGHT; ++i) {
            for(int j = 0; j < Map.MAP_WIDTH; ++j) {
                dist[i][j] = 1000000000;
                check[i][j] = false;
                parent[i][j] = new Point(-1, -1);
            }
        }
        dist[coorX][coorY] = 0;
        pq.clear();
        pq.add(new MyPair(coorX, coorY, 0));

        while (!pq.isEmpty()) {
            int vx = pq.peek().first;
            int vy = pq.peek().second;
            int d = pq.peek().w;

            pq.poll();

            if (check[vx][vy]) {
                continue;
            }
            else {
                check[vx][vy] = true;
            }

            if(map[vx - 1][vy] == '0') {
                if (dist[vx - 1][vy] > 1 + d) {
                    dist[vx - 1][vy] = 1 + d;
                    parent[vx - 1][vy] = new Point(vx, vy);
                }
                pq.add(new MyPair(vx - 1, vy, dist[vx - 1][vy]));
            }

            if(map[vx + 1][vy] == '0') {
                if (dist[vx + 1][vy] > 1 + d) {
                    dist[vx + 1][vy] = 1 + d;
                    parent[vx + 1][vy] = new Point(vx, vy);
                }
                pq.add(new MyPair(vx + 1, vy, dist[vx + 1][vy]));
            }

            if(map[vx][vy - 1] == '0') {
                if (dist[vx][vy - 1] > 1 + d) {
                    dist[vx][vy - 1] = 1 + d;
                    parent[vx][vy - 1] = new Point(vx, vy);
                }
                pq.add(new MyPair(vx, vy - 1, dist[vx][vy - 1]));
            }

            if(map[vx][vy + 1] == '0') {
                if (dist[vx][vy + 1] > 1 + d) {
                    dist[vx][vy + 1] = 1 + d;
                    parent[vx][vy + 1] = new Point(vx, vy);
                }
                pq.add(new MyPair(vx, vy + 1, dist[vx][vy + 1]));
            }
        }
    }

    void tracking(int vx, int vy) {
        path.add(0, new Point(vx, vy));
        if (parent[vx][vy].x == -1) return;
        tracking(parent[vx][vy].x, parent[vx][vy].y);
    }

    public void findPath() {
        map = gameMap.getGameMap();
        path.clear();
        for (int i = 0; i < Map.MAP_HEIGHT; ++i) {
            for (int j = 0; j < Map.MAP_WIDTH; ++j) {
                check[i][j] = false;
            }
        }

        int targetY = (player.getX() + player.getHitBox().x + player.getHitBox().width / 2) / Resources.tWidth;
        int targetX = (player.getY() + player.getHitBox().y + player.getHitBox().height / 2) / Resources.tWidth;
        coorY = (this.x + hitBox.x + hitBox.width / 2) / Resources.tWidth;
        coorX = (this.y + hitBox.y + hitBox.height / 2) / Resources.tHeight;
        dijkstra();
        tracking(targetX, targetY);
    }

    @Override
    public void makeChoice() {
        timer += System.nanoTime() - lastTime;
        lastTime = System.nanoTime();
        if (timer > 1500000000) {
            timer = 0;
            if (speed == 1.0f) {
                speed = 3.0f;
            } else {
                speed = 1.0f;
            }
        }

        player = game.getGameScene1().getLuigi();

        if (choice == 1) {
            if ((y + hitBox.y) % Resources.tHeight >= Resources.tHeight / 2) {
                return;
            }
        } else if (choice == 2) {
            if ((y + hitBox.y + hitBox.height) % Resources.tHeight <= Resources.tHeight / 2) {
                return;
            }
        } else if (choice == 3) {
            if ((x + hitBox.x) % Resources.tWidth >= Resources.tWidth / 2) {
                return;
            }
        } else if (choice == 4) {
            if ((x + hitBox.x + hitBox.width) % Resources.tWidth <= Resources.tWidth / 2) {
                return;
            }
        }

        findPath();
        if (path.size() <= 1) {
            if (stand) {
                choice = rand.nextInt(4) + 1;
            }
            return;
        }
        Point next = path.get(1);
        if (next.x == coorX - 1) { // up
            choice = 1;
        } else if (next.x == coorX + 1) { // down
            choice = 2;
        } else if (next.y == coorY - 1) { // left
            choice = 3;
        } else if (next.y == coorY + 1) { // right
            choice = 4;
        }
    }
}

class MyPair {
    public int first;
    public int second;
    public int w;

    public MyPair(int first, int second, int w) {
        this.first = first;
        this.second = second;
        this.w = w;
    }
}

class Cmp implements Comparator<MyPair> {
    @Override
    public int compare(MyPair o1, MyPair o2) {
        return Integer.compare(o1.w, o2.w);
    }
}