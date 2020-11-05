package Bomberman.gameObjects.creatures.enemy;

import Bomberman.Game;
import Bomberman.graphics.gallery.Resources;

import java.util.Random;

public class Dummy extends Enemy {
    private final Random rand = new Random();

    public Dummy(Game game, int x, int y, Resources asset) {
        super(game, x, y, asset);
        speed = 2.5f;
    }

    @Override
    public void makeChoice() {
        if (stand) {
            choice = rand.nextInt(4) + 1;
        }
    }
}
