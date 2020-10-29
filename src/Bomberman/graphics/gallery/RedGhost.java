package Bomberman.graphics.gallery;

public class RedGhost extends Resources {
    public RedGhost() {
        for (int i = 0; i < 3; ++i) {
            Down[i] = redGhost.getSubimage(i * pWidth, 0, pWidth, pHeight);

            Up[i] = redGhost.getSubimage(i * pWidth, pHeight, pWidth, pHeight);

            Left[i] = redGhost.getSubimage(i * pWidth, pHeight * 2, pWidth, pHeight);

            Right[i] = redGhost.getSubimage(i * pWidth, pHeight * 3, pWidth, pHeight);
        }
    }
}
