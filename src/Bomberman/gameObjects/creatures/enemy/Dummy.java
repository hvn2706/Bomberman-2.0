package Bomberman.gameObjects.creatures.enemy;

import Bomberman.Game;
import Bomberman.graphics.gallery.Resources;
import Bomberman.map.Map;

import java.util.Random;

public class Dummy extends Enemy {
    private final Random rand = new Random();

    public Dummy(Game game, Map gameMap, int x, int y, Resources asset) {
        super(game, gameMap, x, y, asset);
        speed = 1;
    }

    @Override
    public void makeChoice() {
        if (stand) {
            choice = rand.nextInt(4) + 1;
        }
    }
}
