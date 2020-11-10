package Bomberman.scenes;

import Bomberman.Game;
import Bomberman.gameObjects.Portal;
import Bomberman.gameObjects.creatures.Creature;
import Bomberman.gameObjects.creatures.Player;
import Bomberman.gameObjects.creatures.enemy.ABitSmarter;
import Bomberman.gameObjects.creatures.enemy.Dummy;
import Bomberman.gameObjects.creatures.enemy.Enemy;
import Bomberman.graphics.SceneButton;
import Bomberman.graphics.SoundButton;
import Bomberman.graphics.Status;
import Bomberman.graphics.gallery.BlueGhost;
import Bomberman.graphics.gallery.Luigi;
import Bomberman.graphics.gallery.RedGhost;
import Bomberman.graphics.gallery.Resources;
import Bomberman.map.Map;

import java.awt.*;
import java.util.ArrayList;

public class GameScene1 extends MyScene { // 1 player
    private final Player luigi;
    private final Enemy redGhost1;
    private final Enemy redGhost2;
    private final Enemy blueGhost1;
    private final Enemy blueGhost2;
    private final SceneButton back;
    private final SoundButton soundButton;
    private final Status strLuigi;
    private final ArrayList<Enemy> enemies;
    private final Portal portal;
    private int death;
    private long timer = 0;
    private final Map gameMap;

    public GameScene1(Game game) {
        super(game);
        this.gameMap = new Map("resources/map1.txt");
        luigi = new Player(game, gameMap, 71, 71, new Luigi());
        redGhost1 = new Dummy(game, gameMap, 522, 266, new RedGhost());
        redGhost2 = new Dummy(game, gameMap, 1226, 74, new RedGhost());
        blueGhost1 = new ABitSmarter(game, gameMap, 778, 266, new BlueGhost());
        blueGhost2 = new ABitSmarter(game, gameMap, 74, 778, new BlueGhost());
        back = new SceneButton(game, Resources.back1, Resources.back2, 1374, 10);
        soundButton = new SoundButton(game, 1374, 778);
        portal = new Portal(game, gameMap, -1, -1);
        portal.init();
        strLuigi = new Status(luigi, 1370, 150);
        enemies = new ArrayList<>();
        enemies.add(redGhost1);
        enemies.add(redGhost2);
        enemies.add(blueGhost1);
        enemies.add(blueGhost2);
    }

    public Map getGameMap() {
        return gameMap;
    }

    public Player getLuigi() {
        return luigi;
    }

    public Portal getPortal() {
        return portal;
    }

    public boolean open() {
        return death == enemies.size();
    }

    public void reset() {
        gameMap.resetMap();
        luigi.reset();
        blueGhost1.reset();
        blueGhost2.reset();
        redGhost1.reset();
        redGhost2.reset();
        portal.init();
        gameMap.setPortal(portal);
        timer = 0;
    }

    @Override
    public void update() {
        gameMap.update();
        luigi.hitMeBabe(enemies);
        death = 0;
        for (Creature en : enemies) {
            if (!en.alive) {
                death++;
            }
        }
        portal.update();
        luigi.update();
        blueGhost1.update();
        blueGhost2.update();
        redGhost1.update();
        redGhost2.update();
        soundButton.update();
        back.update(game.getMenuScene());

        if (luigi.alive && open()) {
            game.getResultScene().setResult(Resources.player1win);
        } else {
            game.getResultScene().setResult(Resources.youLose);
            if (!luigi.alive) {
                long lastTime = System.nanoTime();
                timer += System.nanoTime() - lastTime;
                if (timer >= 10000) {
                    MyScene.setCurrentScene(game.getResultScene());
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        gameMap.render(g);
        strLuigi.render(g);
        luigi.render(g);
        blueGhost1.render(g);
        blueGhost2.render(g);
        redGhost1.render(g);
        redGhost2.render(g);
        back.render(g);
        soundButton.render(g);
    }
}
