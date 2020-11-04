package Bomberman.graphics.gallery;

public class BlueGhost extends Resources{
    public BlueGhost() {
        for (int i = 0; i < 3; ++i) {
            Down[i] = blueGhost.getSubimage(i * pWidth, 0, pWidth, pHeight);

            Up[i] = blueGhost.getSubimage(i * pWidth, pHeight, pWidth, pHeight);

            Left[i] = blueGhost.getSubimage(i * pWidth, pHeight * 2, pWidth, pHeight);

            Right[i] = blueGhost.getSubimage(i * pWidth, pHeight * 3, pWidth, pHeight);
        }
    }
}
