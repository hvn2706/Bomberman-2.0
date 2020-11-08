package Bomberman.graphics;

import Bomberman.Game;
import Bomberman.graphics.gallery.Resources;
import Bomberman.sounds.Playlist;
import Bomberman.sounds.SoundEffect;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SoundButton {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private boolean state;
    private final Game game;
    private BufferedImage current;
    private int click;

    public SoundButton(Game game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
        width = Resources.sound1.getWidth();
        height = Resources.sound1.getHeight();
        state = false;
        if (SoundEffect.isOn) {
            current = Resources.sound2;
        } else {
            current = Resources.sound1;
        }
        click = 1;
    }

    public void update() {
        if ((game.getMouseMN().getMx() >= x && game.getMouseMN().getMy() >= y) &&
            (game.getMouseMN().getMx() <= x + width && game.getMouseMN().getMy() <= y + height)) {
            state = true;

            if (game.getMouseMN().isLeft() && click == 1) {
                SoundEffect.isOn = !SoundEffect.isOn;
                click = 0;
            }
        } else {
            state = false;
            click = 1;
        }

        if (SoundEffect.isOn) {
            current = Resources.sound2;
            Playlist.backgroundMusic.playBackground();
        } else {
            current = Resources.sound1;
            Playlist.backgroundMusic.stopBackground();
        }
    }

    public void render(Graphics g) {
        if (!state) {
            g.drawImage(current, x, y, null);
        } else {
            g.drawImage(Resources.sound3, x, y, null);
        }
    }
}
