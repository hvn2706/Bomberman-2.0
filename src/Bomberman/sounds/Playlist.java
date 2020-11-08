package Bomberman.sounds;

public class Playlist {
    public static SoundEffect backgroundMusic;
    public static SoundEffect setBomb;
    public static SoundEffect eatPower;
    public static SoundEffect explosion;

    public static void init() {
        setBomb = new SoundEffect("resources/setbomb.wav");
        eatPower = new SoundEffect("resources/pop.wav");
        explosion = new SoundEffect("resources/explosion.wav");
        backgroundMusic = new SoundEffect("resources/STRLGHT - Clutch.wav");
    }
}
