package Bomberman.graphics;

import java.awt.image.BufferedImage;

public class Animation {
    private final BufferedImage[] frames;
    private long lastTime, timer;
    private final long frameSpeed;
    private int index;

    public Animation(BufferedImage[] frames, long frameSpeed) {
        this.frames = frames;
        index = 0;
        timer = 0;
        this.frameSpeed = frameSpeed;
        lastTime = System.nanoTime();
    }

    public void update() {
        timer += System.nanoTime() - lastTime;
        lastTime = System.nanoTime();

        if (timer > frameSpeed) {
            index++;
            timer = 0;
            if (index >= frames.length) {
                index = 0;
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }
}
