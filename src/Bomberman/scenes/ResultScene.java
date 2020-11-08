package Bomberman.scenes;

import Bomberman.Game;
import Bomberman.graphics.SceneButton;
import Bomberman.graphics.gallery.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ResultScene extends MyScene {
    private final SceneButton back;
    BufferedImage result;

    public ResultScene(Game game) {
        super(game);
        back = new SceneButton(game, Resources.back1, Resources.back2, 702, 640);
    }

    public void setResult(BufferedImage result) {
        this.result = result;
    }

    @Override
    public void update() {
        back.update(game.getMenuScene());
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(result, 0, 0, null);
        back.render(g);
    }
}
