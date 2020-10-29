package Bomberman.graphics.gallery;

public class Werewolf extends Resources {
    public Werewolf() {
        for (int i = 0; i < 3; ++i) {
            Down[i] = werewolf.getSubimage(i * pWidth, 0, pWidth, pHeight);

            Up[i] = werewolf.getSubimage(i * pWidth, pHeight, pWidth, pHeight);

            Left[i] = werewolf.getSubimage(i * pWidth, pHeight * 2, pWidth, pHeight);

            Right[i] = werewolf.getSubimage(i * pWidth, pHeight * 3, pWidth, pHeight);
        }
    }
}
