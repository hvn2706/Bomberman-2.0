package Bomberman.scenes;

import Bomberman.Game;
import Bomberman.gameObjects.creatures.Player;
import Bomberman.gameObjects.creatures.enemy.ABitSmarter;
import Bomberman.gameObjects.creatures.enemy.Dummy;
import Bomberman.gameObjects.creatures.enemy.Enemy;
import Bomberman.graphics.MyButton;
import Bomberman.graphics.Status;
import Bomberman.graphics.gallery.BlueGhost;
import Bomberman.graphics.gallery.Luigi;
import Bomberman.graphics.gallery.RedGhost;
import Bomberman.graphics.gallery.Resources;

import java.awt.*;
import java.util.ArrayList;

public class GameScene1 extends MyScene { // 1 player
    private final Player luigi;
    private final Enemy redGhost1;
    private final Enemy redGhost2;
    private final Enemy blueGhost1;
    private final MyButton back;
    private final Status strLuigi;
    private final ArrayList<Enemy> enemies;

    public GameScene1(Game game) {
        super(game);
        luigi = new Player(game,71, 71, new Luigi());
        redGhost1 = new Dummy(game, 522, 266, new RedGhost());
        redGhost2 = new Dummy(game, 1226, 74, new RedGhost());
        blueGhost1 = new ABitSmarter(game, 778, 266, new BlueGhost());
        back = new MyButton(game, Resources.back1, Resources.back2, 1374, 10);
        strLuigi = new Status(luigi, 1370, 150);
        enemies = new ArrayList<>();
        enemies.add(redGhost1);
        enemies.add(redGhost2);
        enemies.add(blueGhost1);
    }

    public Player getLuigi() {
        return luigi;
    }

    public Enemy getRedGhost1() {
        return redGhost1;
    }

    public Enemy getRedGhost2() {
        return redGhost2;
    }

    public Enemy getBlueGhost1() {
        return blueGhost1;
    }

    @Override
    public void update() {
        luigi.hitMeBabe(enemies);
        luigi.update();
        blueGhost1.update();
        redGhost1.update();
        redGhost2.update();
        back.update(game.getMenuScene());
    }

    @Override
    public void render(Graphics g) {
        strLuigi.render(g);
        luigi.render(g);
        blueGhost1.render(g);
        redGhost1.render(g);
        redGhost2.render(g);
        back.render(g);
    }
}
