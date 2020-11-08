package Bomberman.graphics;

import Bomberman.Game;
import Bomberman.scenes.MyScene;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SceneButton {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final BufferedImage state1;
    private final BufferedImage state2;
    private boolean state;
    private final Game game;

    public SceneButton(Game game, BufferedImage state1, BufferedImage state2, int x, int y) {
        this.state1 = state1;
        this.state2 = state2;
        this.x = x;
        this.y = y;
        this.width = state1.getWidth();
        this.height = state1.getHeight();
        state = false;
        this.game = game;
    }

    public void update(MyScene scene) {
        if ((game.getMouseMN().getMx() >= x && game.getMouseMN().getMy() >= y) &&
            (game.getMouseMN().getMx() <= x + width && game.getMouseMN().getMy() <= y + height)) {
            state = true;
            if (game.getMouseMN().isLeft()) {
                if (scene != game.getMenuScene()) {
                    game.getGameMap().resetMap();
                    game.getGameScene1().getPortal().setX(-1);
                    game.getGameScene1().getPortal().setY(-1);
                }

                if (scene == game.getGameScene1()) {
                    game.getGameScene1().getLuigi().reset();
                    game.getGameScene1().getBlueGhost1().reset();
                    game.getGameScene1().getBlueGhost2().reset();
                    game.getGameScene1().getRedGhost1().reset();
                    game.getGameScene1().getRedGhost2().reset();
                    game.getGameScene1().getPortal().init();
                    game.getGameScene1().setTimer(0);
                } else if (scene == game.getGameScene2()) {
                    game.getGameScene2().getLuigi().reset();
                    game.getGameScene2().getMinotaur().reset();
                    game.getGameScene2().setTimer(0);
                }
                MyScene.setCurrentScene(scene);
            }
        } else {
            state = false;
        }
    }

    public void render(Graphics g) {
        if (!state) {
            g.drawImage(state1, x, y, null);
        } else {
            g.drawImage(state2, x, y, null);
        }
    }
}
