package Bomberman.scenes;

import Bomberman.Game;
import Bomberman.graphics.MyButton;
import Bomberman.graphics.gallery.Resources;

import java.awt.*;

public class MenuScene extends MyScene {
    private final MyButton onePlayer;
    private final MyButton twoPlayer;

    public MenuScene(Game game) {
        super(game);
        onePlayer = new MyButton(game, Resources.onePlayer1, Resources.onePlayer2, 640, 256);
        twoPlayer = new MyButton(game, Resources.twoPlayer1, Resources.twoPlayer2, 640, 448);
    }

    @Override
    public void update() {
        onePlayer.update(game.getGameScene1());
        twoPlayer.update(game.getGameScene2());
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Resources.menuBG, 0, 0, null);
        twoPlayer.render(g);
        onePlayer.render(g);
    }
}
