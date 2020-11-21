package Bomberman.scenes;

import Bomberman.Game;
import Bomberman.gameObjects.creatures.Player;
import Bomberman.graphics.SceneButton;
import Bomberman.graphics.SoundButton;
import Bomberman.graphics.Status;
import Bomberman.graphics.gallery.Luigi;
import Bomberman.graphics.gallery.Minotaur;
import Bomberman.graphics.gallery.Resources;
import Bomberman.map.Map;
import Bomberman.network.Client;
import Bomberman.network.Server;
import Bomberman.network.ServerConnection;

import java.awt.*;

public class GameScene3 extends MyScene {
    private final Player luigi;
    private final Player minotaur;
    private final Player myPlayer;
    private final Player opPlayer;
    private final SceneButton back;
    private final SoundButton soundButton;
    private final Status strLuigi;
    private final Status strMinotaur;
    private final Map gameMap;
    private final Font font;
    private long timer = 0;
    private boolean mapIsUpdated = false;

    public GameScene3(Game game) {
        super(game);
        gameMap = new Map("resources/map1.txt", 18);
        minotaur = new Player(game, gameMap, 764, 484, new Minotaur());
        luigi = new Player(game, gameMap, 44, 44, new Luigi());
        back = new SceneButton(game, Resources.back1, Resources.back2, 860, 6);
        soundButton = new SoundButton(game, 860, 486);
        strLuigi = new Status(luigi, 856, 94);
        strMinotaur = new Status(minotaur, 856, 188);
        font = new Font(Font.MONOSPACED, Font.BOLD, 25);
        if (Game.serverRunning) {
            luigi.input = true;
            minotaur.input = false;
            myPlayer = luigi;
            opPlayer = minotaur;
        } else {
            luigi.input = false;
            minotaur.input = true;
            myPlayer = minotaur;
            opPlayer = luigi;
        }
    }

    public Map getGameMap() {
        return gameMap;
    }

    public void reset() {
        gameMap.resetMap();
        gameMap.setPortal(null);
        luigi.reset();
        minotaur.reset();
        timer = 0;
    }

    private void sendDataToServer() {
        String msg = "";
        if (Game.serverRunning) {
            char[][] tmpMap = gameMap.getPowerMap();
            for (int i = 0; i < Map.MAP_HEIGHT; ++i) {
                for (int j = 0; j < Map.MAP_WIDTH; ++j) {
                    msg += tmpMap[i][j];
                }
                msg += " ";
            }
        }
        msg += myPlayer.up + " " + myPlayer.down + " "
                   + myPlayer.left + " " + myPlayer.right + " " + myPlayer.toggle + " "
                   + myPlayer.getX() + " " + myPlayer.getY();
        Client.out.println(msg);
    }

    private void updateFromServer() {
        if (ServerConnection.serverResponse != null) {
            String[] msg = ServerConnection.serverResponse.split(" ");
            int startIndex = 0;
            if (!Game.serverRunning) {
                startIndex = Map.MAP_HEIGHT;
                if (!mapIsUpdated) {
                    mapIsUpdated = true;
                    for (int i = 0; i < Map.MAP_HEIGHT; ++i) {
                        for (int j = 0; j < Map.MAP_WIDTH; ++j) {
                            gameMap.setPowerCoor(i, j, msg[i + 1].charAt(j));
                        }
                    }
                }
            }
            opPlayer.up = false;
            opPlayer.down = false;
            opPlayer.left = false;
            opPlayer.right = false;
            opPlayer.toggle = false;
            if (msg[startIndex + 1].trim().equals("true")) {
                opPlayer.up = true;
            }
            if (msg[startIndex + 2].trim().equals("true")) {
                opPlayer.down = true;
            }
            if (msg[startIndex + 3].trim().equals("true")) {
                opPlayer.left = true;
            }
            if (msg[startIndex + 4].trim().equals("true")) {
                opPlayer.right = true;
            }
            if (msg[startIndex + 5].trim().equals("true")) {
                opPlayer.toggle = true;
            }
            opPlayer.setX(Integer.parseInt(msg[startIndex + 6]));
            opPlayer.setY(Integer.parseInt(msg[startIndex + 7]));
        }
    }

    @Override
    public void update() {
        back.update(game.getOptionScene());
        soundButton.update();

        if (!Client.serverRunning) {
            return;
        }

        if (Game.serverRunning && Server.clients.size() < 2) {
            return;
        }

        sendDataToServer();
        updateFromServer();
        gameMap.update();
        luigi.update();
        minotaur.update();

        if (!luigi.alive || !minotaur.alive) {
            long lastTime = System.nanoTime();
            timer += System.nanoTime() - lastTime;
            if (luigi.alive) {
                game.getResultScene().setResult(Resources.player1win);
            } else if (minotaur.alive) {
                game.getResultScene().setResult(Resources.player2win);
            } else {
                game.getResultScene().setResult(Resources.draw);
            }

            if (timer >= 10000) {
                MyScene.setCurrentScene(game.getResultScene());
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (!Client.serverRunning) {
            g.setFont(font);
            g.drawString("No server found...", 15, 30);
            back.render(g);
            soundButton.render(g);
            return;
        }

        if (Game.serverRunning && Server.clients.size() < 2) {
            g.setFont(font);
            g.drawString("Waiting for other player...", 15, 30);
            back.render(g);
            soundButton.render(g);
            return;
        }
        gameMap.render(g);
        back.render(g);
        soundButton.render(g);
        strLuigi.render(g);
        strMinotaur.render(g);
        luigi.render(g);
        minotaur.render(g);
    }
}
