package main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent> {
    private GameApplication ga;
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
            case W -> upPressed = flag;
            case A -> leftPressed = flag;
            case S -> downPressed = flag;
            case D -> rightPressed = flag;
            case P -> {
                if (!flag) {
                    if (pPressed) {
                        pPressed = false;
                        if (ga.gameState != ga.PAUSE_STATE) {
                            ga.gameState = ga.PAUSE_STATE;
                        } else {
                            ga.gameState = ga.PLAY_STATE;
                        }
                    }
                } else {
                    pPressed = true;
                }
            }
            case T ->
                checkDrawTime = !checkDrawTime;

            case C -> {
                if (!flag) {
                    if (cPressed) {
                        cPressed = false;
                        if (ga.gameState != ga.CHARACTER_STATE) {
                            ga.gameState = ga.CHARACTER_STATE;
                        } else {
                            ga.gameState = ga.PLAY_STATE;
                        }
                    }
                } else {
                    cPressed = true;
                }
            }
            case U -> {
                if (!flag) {
                    if (ga.ui.showUpgradeScreen) {
                        ga.upgradeBootsButton.setVisible(false);
                        ga.upgradeMeleeButton.setVisible(false);
                        ga.upgradeArmorButton.setVisible(false);
                        ga.upgradeProjectileButton.setVisible(false);
                        ga.ui.showUpgradeScreen = false;
                        ga.gameState = ga.PLAY_STATE;
                    }
                    else {
                        ga.upgradeBootsButton.setVisible(true);
                        ga.upgradeMeleeButton.setVisible(true);
                        ga.upgradeArmorButton.setVisible(true);
                        ga.upgradeProjectileButton.setVisible(true);
                        ga.ui.showUpgradeScreen = true;
                        ga.gameState = ga.PAUSE_STATE;
                    }
                }
            }
            case F3 -> {
                if (!flag && ga.showExtraHUD) {
                    ga.showExtraHUD = false;
                }
                else {
                    ga.showExtraHUD = true;
                }
            }
            case ESCAPE -> {
                pPressed = true;

                cPressed = false;
                if (ga.gameState != ga.CHARACTER_STATE) {
                    ga.gameState = ga.CHARACTER_STATE;
                } else {
                    ga.gameState = ga.PLAY_STATE;
                }

                ga.upgradeBootsButton.setVisible(false);
                ga.upgradeMeleeButton.setVisible(false);
                ga.upgradeArmorButton.setVisible(false);
                ga.upgradeProjectileButton.setVisible(false);
                ga.ui.showUpgradeScreen = false;
                ga.gameState = ga.PLAY_STATE;
            }
        }
    }
}

