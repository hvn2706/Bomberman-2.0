package Bomberman.scenes;

import Bomberman.Game;
import Bomberman.gameObjects.creatures.Player;
import Bomberman.graphics.MyButton;
import Bomberman.graphics.Status;
import Bomberman.graphics.gallery.Luigi;
import Bomberman.graphics.gallery.Minotaur;
import Bomberman.graphics.gallery.Resources;

import java.awt.*;

public class GameScene2 extends MyScene { // 2 player
    private final Player luigi;
    private final Player minotaur;
    private final MyButton back;
    private final Status strLuigi;
    private final Status strMinotaur;

    public GameScene2(Game game) {
        super(game);
        minotaur = new Player(game, 1230, 778, new Minotaur());
        luigi = new Player(game,71, 71, new Luigi());
        back = new MyButton(game, Resources.back1, Resources.back2, 1374, 10);
        strLuigi = new Status(luigi, 1370, 150);
        strMinotaur = new Status(minotaur, 1370, 300);
    }

    public Player getLuigi() {
        return luigi;
    }

    public Player getMinotaur() {
        return minotaur;
    }

    @Override
    public void update() {
        luigi.update();
        minotaur.update();
        back.update(game.getMenuScene());
    }

    @Override
    public void render(Graphics g) {
        strLuigi.render(g);
        strMinotaur.render(g);
        luigi.render(g);
        minotaur.render(g);
        back.render(g);
    }
}
