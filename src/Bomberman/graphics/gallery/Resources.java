package Bomberman.graphics.gallery;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resources {
    public static BufferedImage gameIcon;
    public static BufferedImage menuBG;
    public static BufferedImage onePlayer1;
    public static BufferedImage onePlayer2;
    public static BufferedImage twoPlayer1;
    public static BufferedImage twoPlayer2;
    public static BufferedImage offline1;
    public static BufferedImage offline2;
    public static BufferedImage online1;
    public static BufferedImage online2;
    public static BufferedImage back1;
    public static BufferedImage back2;
    public static BufferedImage sound1;
    public static BufferedImage sound2;
    public static BufferedImage sound3;
    public static BufferedImage background2;
    public static BufferedImage player1win;
    public static BufferedImage player2win;
    public static BufferedImage draw;
    public static BufferedImage youLose;
    public static BufferedImage chest;
    public static BufferedImage greyBrick;
    public static BufferedImage luigi;
    public static BufferedImage minotaur;
    public static BufferedImage redGhost;
    public static BufferedImage blueGhost;
    public static BufferedImage bombs;
    public static BufferedImage explosions;
    public static BufferedImage electric;
    public static BufferedImage sonic;
    public static BufferedImage sparkleOrb;
    public static BufferedImage dead;

    public static final int pWidth = 32;
    public static final int pHeight = 32;
    public static final int tWidth = 40;
    public static final int tHeight = 40;

    public static BufferedImage[] bomb = new BufferedImage[5];
    public static BufferedImage[] explosion = new BufferedImage[5];
    public static BufferedImage[] power1 = new BufferedImage[5];
    public static BufferedImage[] power2 = new BufferedImage[5];
    public static BufferedImage[] power3 = new BufferedImage[5];
    public static BufferedImage[] portal = new BufferedImage[4];
    public BufferedImage[] Down = new BufferedImage[3];
    public BufferedImage[] Up = new BufferedImage[3];
    public BufferedImage[] Left = new BufferedImage[3];
    public BufferedImage[] Right = new BufferedImage[3];

    public static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static void init() {
        gameIcon = loadImage("resources/gameIcon.png");
        BufferedImage tiles = loadImage("resources/tiles.png");
        BufferedImage buttons = loadImage("resources/buttons.png");
        BufferedImage portals = loadImage("resources/doors.png");
        player1win = loadImage("resources/player1win.png");
        player2win = loadImage("resources/player2win.png");
        youLose = loadImage("resources/youlose.png");
        draw = loadImage("resources/draw.png");
        menuBG = loadImage("resources/menuBackground.png");
        background2 = loadImage("resources/floor.png");
        bombs = loadImage("resources/bomb.png");
        luigi = loadImage("resources/luigi.png");
        minotaur = loadImage("resources/minotaur.png");
        redGhost = loadImage("resources/ghost1.png");
        blueGhost = loadImage("resources/ghost2.png");
        explosions = loadImage("resources/explosion.png");
        electric = loadImage("resources/power1.png");
        sonic = loadImage("resources/power2.png");
        sparkleOrb = loadImage("resources/power3.png");
        dead = loadImage("resources/skull.png");

        onePlayer1 = buttons.getSubimage(0, 0, 200, 80);
        onePlayer2 = buttons.getSubimage(0, 80, 200, 80);
        twoPlayer1 = buttons.getSubimage(0, 160, 200, 80);
        twoPlayer2 = buttons.getSubimage(0, 240, 200, 80);
        offline1 = buttons.getSubimage(200, 0, 200, 80);
        offline2 = buttons.getSubimage(200, 80, 200, 80);
        online1 = buttons.getSubimage(200, 160, 200, 80);
        online2 = buttons.getSubimage(200, 240, 200, 80);
        back1 = buttons.getSubimage(0, 320, 120, 40);
        back2 = buttons.getSubimage(0, 360, 120, 40);
        sound1 = buttons.getSubimage(0, 400, 120, 40);
        sound2 = buttons.getSubimage(0, 440, 120, 40);
        sound3 = buttons.getSubimage(0, 480, 120, 40);

        chest = tiles.getSubimage(tWidth * 6, 0, tWidth, tHeight);
        greyBrick = tiles.getSubimage(tWidth * 4, 0, tWidth, tHeight);

        for (int i = 0; i < 5; ++i) {
            bomb[i] = bombs.getSubimage(tWidth * i, 0, tWidth, tHeight);
            explosion[i] = explosions.getSubimage(tWidth * i, 0, tWidth, tHeight);
            power1[i] = electric.getSubimage(tWidth * i, 0, tWidth, tHeight);
            power2[i] = sonic.getSubimage(tWidth * i, 0, tWidth, tHeight);
            power3[i] = sparkleOrb.getSubimage(tWidth * i, 0, tWidth, tHeight);
            if (i < 4) {
                portal[i] = portals.getSubimage(0, tHeight * i, tWidth, tHeight);
            }
        }
    }
}
