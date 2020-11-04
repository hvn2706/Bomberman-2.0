package Bomberman.gameObjects.creatures.enemy;

import Bomberman.Game;
import Bomberman.graphics.gallery.Resources;

import java.util.Random;

public class Dummy extends Enemy {
    private Random rand = new Random();

    public Dummy(Game game, int x, int y, Resources asset) {
        super(game, x, y, asset);
    }

    @Override
    public void makeChoice() {
        if (stand) {
            choice = rand.nextInt(4) + 1;
        }
    }
}
