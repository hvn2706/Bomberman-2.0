package Bomberman.scenes;

import Bomberman.Game;
import Bomberman.gameObjects.creatures.Player;
import Bomberman.graphics.SceneButton;
import Bomberman.graphics.SoundButton;
import Bomberman.graphics.Status;
import Bomberman.graphics.gallery.Luigi;
import Bomberman.graphics.gallery.Minotaur;
import Bomberman.graphics.gallery.Resources;
import Bomberman.map.Map;

import java.awt.*;

public class GameScene2 extends MyScene { // 2 player
    private final Player luigi;
    private final Player minotaur;
    private final SceneButton back;
    private final SoundButton soundButton;
    private final Status strLuigi;
    private final Status strMinotaur;
    private final Map gameMap;
    private long timer = 0;

    public GameScene2(Game game) {
        super(game);
        gameMap = new Map("resources/map1.txt");
        minotaur = new Player(game, gameMap, 1230, 778, new Minotaur());
        luigi = new Player(game, gameMap, 71, 71, new Luigi());
        back = new SceneButton(game, Resources.back1, Resources.back2, 1374, 10);
        soundButton = new SoundButton(game, 1374, 778);
        strLuigi = new Status(luigi, 1370, 150);
        strMinotaur = new Status(minotaur, 1370, 300);
    }

    public Map getGameMap() {
        return gameMap;
    }

    public void reset() {
        gameMap.resetMap();
        gameMap.setPortal(null);
        luigi.reset();
        minotaur.reset();
        timer = 0;
    }

    @Override
    public void update() {
        gameMap.update();
        luigi.update();
        minotaur.update();
        back.update(game.getMenuScene());
        soundButton.update();

        if (!luigi.alive || !minotaur.alive) {
            long lastTime = System.nanoTime();
            timer += System.nanoTime() - lastTime;
            if (luigi.alive) {
                game.getResultScene().setResult(Resources.player1win);
            } else if (minotaur.alive) {
                game.getResultScene().setResult(Resources.player2win);
            } else {
                game.getResultScene().setResult(Resources.draw);
            }

            if (timer >= 10000) {
                MyScene.setCurrentScene(game.getResultScene());
            }
        }
    }

    @Override
    public void render(Graphics g) {
        gameMap.render(g);
        strLuigi.render(g);
        strMinotaur.render(g);
        luigi.render(g);
        minotaur.render(g);
        back.render(g);
        soundButton.render(g);
    }
}
