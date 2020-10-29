package Bomberman.graphics.gallery;

public class Luigi extends Resources {
    public Luigi() {
        for (int i = 0; i < 3; ++i) {
            Down[i] = luigi.getSubimage(i * pWidth, 0, pWidth, pHeight);

            Up[i] = luigi.getSubimage(i * pWidth, pHeight, pWidth, pHeight);

            Left[i] = luigi.getSubimage(i * pWidth, pHeight * 2, pWidth, pHeight);

            Right[i] = luigi.getSubimage(i * pWidth, pHeight * 3, pWidth, pHeight);
        }
    }
}
