package Bomberman.graphics.gallery;

public class Minotaur extends Resources {
    public Minotaur() {
        for (int i = 0; i < 3; ++i) {
            Down[i] = minotaur.getSubimage(i * pWidth, 0, pWidth, pHeight);

            Up[i] = minotaur.getSubimage(i * pWidth, pHeight, pWidth, pHeight);

            Left[i] = minotaur.getSubimage(i * pWidth, pHeight * 2, pWidth, pHeight);

            Right[i] = minotaur.getSubimage(i * pWidth, pHeight * 3, pWidth, pHeight);
        }
    }
}
