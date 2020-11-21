package Bomberman.gameObjects;

import Bomberman.Game;
import Bomberman.gameObjects.creatures.Player;
import Bomberman.graphics.Animation;
import Bomberman.graphics.gallery.Resources;
import Bomberman.map.Map;
import Bomberman.scenes.MyScene;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Portal extends GameObject {
    private final Animation door;
    public Portal(Game game, Map gameMap, int x, int y) {
        super(game, gameMap, x, y);
        door = new Animation(Resources.portal, 60000000);
    }

    public void init() {
        door.reset();
        char[][] map = gameMap.getGameMap();
        ArrayList<Point> chests = new ArrayList<>();
        for (int i = 0; i < Map.MAP_HEIGHT; ++i) {
            for (int j = 0; j < Map.MAP_WIDTH; ++j) {
                if (map[i][j] == '1') {
                    chests.add(new Point(i, j));
                }
            }
        }

        Random rand = new Random();
        int index = rand.nextInt(chests.size());
        Point tmp = chests.get(index);
        x = tmp.y * Resources.tWidth;
        y = tmp.x * Resources.tHeight;
        gameMap.setPowerCoor(tmp.x, tmp.y, '0');
    }

    @Override
    public void update() {
        if (game.getGameScene1().open() && door.getCurrentFrame() != Resources.portal[3]) {
            door.update();
        }

        if (game.getGameScene1().open()) {
            Player player = game.getGameScene1().getLuigi();
            int playerY = (player.getX() + player.getHitBox().x + player.getHitBox().width / 2) / Resources.tWidth;
            int playerX = (player.getY() + player.getHitBox().y + player.getHitBox().height / 2) / Resources.tWidth;
            if (playerX == y / Resources.tHeight && playerY == x / Resources.tWidth) {
                MyScene.setCurrentScene(game.getResultScene());
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(door.getCurrentFrame(), x, y, null);
    }
}
