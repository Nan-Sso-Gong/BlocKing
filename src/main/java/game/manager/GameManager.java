package game.manager;

import game.model.BlockController;

public abstract class GameManager {

    public abstract void startGameFramework();

    public abstract void stopGameFramework();

    protected abstract void gameFramework();

    protected abstract void oneFrame();

    protected abstract void gameOver();

    public abstract BlockController getCurBlock();
}