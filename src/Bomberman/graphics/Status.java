package Bomberman.graphics;

import Bomberman.gameObjects.creatures.Player;

import java.awt.*;

public class Status {
    private final Player player;
    private String str;
    private final Font font;
    private int x;
    private int y;

    public Status(Player player, int x, int y) {
        this.player = player;
        this.x = x;
        this.y = y;
        str = "";
        font = new Font(Font.MONOSPACED, Font.BOLD, 20);
    }

    public void render(Graphics g) {
        g.setFont(font);
        str = player.getName() + ":";
        g.drawString(str, x, y);
        str = "  Bombs: x" + player.getBagCapacity();
        g.drawString(str, x, y += g.getFontMetrics(font).getHeight());
        str = "  Areas: x" + player.getBombLength();
        g.drawString(str, x, y += g.getFontMetrics(font).getHeight());
        str = "  Speed: x" + player.getSpeed();
        g.drawString(str, x, y += g.getFontMetrics(font).getHeight());
        y -= g.getFontMetrics(font).getHeight() * 3;
    }
}
