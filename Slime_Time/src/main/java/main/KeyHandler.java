package main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent> {
    GameApplication ga;
    public static boolean upPressed, downPressed, leftPressed, rightPressed, cPressed, pPressed;
    public boolean flag;
    public boolean checkDrawTime = false;
    public KeyHandler(GameApplication ga, boolean flag) {
        this.ga = ga;
        this.flag = flag;
    }


    // Adjusts Variables According to Key Events
    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W:
                upPressed = flag;
                break;
            case A:
                leftPressed = flag;
                break;
            case S:
                downPressed = flag;
                break;
            case D:
                rightPressed = flag;
                break;
            case P:
                if (!flag) {
                    if (pPressed) {
                        pPressed = false;
                        if (ga.gameState != ga.pauseState) {
                            ga.gameState = ga.pauseState;
                        }
                        else {
                            ga.gameState = ga.playState;
                        }
                    }
                }
                else {
                    pPressed = true;
                }
                break;
            case T:
                if (!checkDrawTime) {
                    checkDrawTime = true;
                }
                else {
                    checkDrawTime = false;
                }
                break;
            case C:
                if (!flag) {
                    if (cPressed) {
                        cPressed = false;
                        if (ga.gameState != ga.characterState) {
                            ga.gameState = ga.characterState;
                        }
                        else {
                            ga.gameState = ga.playState;
                        }
                    }
                }
                else {
                    cPressed = true;
                }
                break;
        }
    }
}

