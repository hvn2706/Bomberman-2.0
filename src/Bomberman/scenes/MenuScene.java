package Bomberman.scenes;

import Bomberman.Game;
import Bomberman.graphics.SceneButton;
import Bomberman.graphics.SoundButton;
import Bomberman.graphics.gallery.Resources;

import java.awt.*;

public class MenuScene extends MyScene {
    private final SceneButton onePlayer;
    private final SceneButton twoPlayer;
    private final SoundButton soundButton;

    public MenuScene(Game game) {
        super(game);
        onePlayer = new SceneButton(game, Resources.onePlayer1, Resources.onePlayer2, 400, 160);
        twoPlayer = new SceneButton(game, Resources.twoPlayer1, Resources.twoPlayer2, 400, 260);
        soundButton = new SoundButton(game, 860, 486);
    }

    @Override
    public void update() {
        soundButton.update();
        onePlayer.update(game.getGameScene1());
        twoPlayer.update(game.getOptionScene());
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Resources.menuBG, 0, 0, null);
        soundButton.render(g);
        twoPlayer.render(g);
        onePlayer.render(g);
    }
}
