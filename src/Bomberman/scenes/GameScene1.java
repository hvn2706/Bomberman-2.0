package Bomberman.scenes;

import Bomberman.Game;
import Bomberman.gameObjects.creatures.Player;
import Bomberman.gameObjects.creatures.enemy.Dummy;
import Bomberman.gameObjects.creatures.enemy.Enemy;
import Bomberman.graphics.MyButton;
import Bomberman.graphics.Status;
import Bomberman.graphics.gallery.Luigi;
import Bomberman.graphics.gallery.RedGhost;
import Bomberman.graphics.gallery.Resources;

import java.awt.*;

public class GameScene1 extends MyScene { // 1 player
    private final Player luigi;
    private final Enemy redGhost1;
    private final Enemy redGhost2;
    private final MyButton back;
    private final Status strLuigi;

    public GameScene1(Game game) {
        super(game);
        luigi = new Player(game,71, 71, new Luigi());
        redGhost1 = new Dummy(game, 778, 266, new RedGhost());
        redGhost2 = new Dummy(game, 1226, 74, new RedGhost());
        back = new MyButton(game, Resources.back1, Resources.back2, 1374, 10);
        strLuigi = new Status(luigi, 1370, 150);
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

    @Override
    public void update() {
        luigi.update();
        redGhost1.update();
        redGhost2.update();
        back.update(game.getMenuScene());
    }

    @Override
    public void render(Graphics g) {
        strLuigi.render(g);
        luigi.render(g);
        redGhost1.render(g);
        redGhost2.render(g);
        back.render(g);
    }
}
