package Bomberman.gameObjects;

import Bomberman.Game;
import Bomberman.graphics.Animation;
import Bomberman.graphics.gallery.Resources;
import Bomberman.map.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Portal extends GameObject {
    private final Animation door;
    public Portal(Game game, int x, int y) {
        super(game, x, y);
        door = new Animation(Resources.portal, 60000000);
    }

    public void init() {
        char[][] map = game.getGameMap().getGameMap();
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
        game.getGameMap().setPowerCoor(tmp.x, tmp.y, '0');
    }

    @Override
    public void update() {
        if (game.getGameScene1().open() && door.getCurrentFrame() != Resources.portal[3]) {
            door.update();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(door.getCurrentFrame(), x, y, null);
    }
}
