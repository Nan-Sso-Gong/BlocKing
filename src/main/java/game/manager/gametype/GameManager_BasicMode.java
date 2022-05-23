package game.manager.gametype;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import game.GameUI;
import game.container.BlockGenerator;
import game.manager.BoardManager;
import game.manager.DualModeUtils.UserNumber;
import game.manager.GameInfoManager;
import game.manager.GameManager;
import game.manager.InGameUIManager;
import game.model.BlockController;
import scoreBoard.NoItemScoreBoard.ScoreInputUI;
import start.StartUI;

public class GameManager_BasicMode extends GameManager {
    
//#region Singleton

    private GameManager_BasicMode(int index) {
        this.index = index;
    }

    private static GameManager_BasicMode instance = new GameManager_BasicMode(0);
    private static GameManager_BasicMode instance2 = new GameManager_BasicMode(1);

    private static ArrayList<GameManager_BasicMode> gameManagerList =
        new ArrayList<GameManager_BasicMode>(Arrays.asList(instance, instance2));

    public static GameManager_BasicMode getInstance(int i) {
        return gameManagerList.get(i);
    }

//#endregion


//#region GameFramework

protected Step curStep = Step.GameReady;

public enum Step {

    GameReady,

    //while !gameOver
    CreateNewBlock,
    BlockMove,
    EraseAnimation,
    EraseLine,
    SendAttackBoard,
    SetGameBalance,
    CheckGameOver,

    GameOver
}

@Override
protected void gameFramework() { //전체적인 게임의 동작 흐름

    switch(curStep) {
        case GameReady:
            curStep = gameReady();
            break;

        case CreateNewBlock:
            curStep = createNewBlock();
            break;

        case BlockMove:
            curStep = blockMove();
            if(curStep != Step.BlockMove) gameFramework();
            break;

        case EraseAnimation:
            curStep = eraseAnimation();
            break;

        case EraseLine:
            curStep = eraseLine();
            gameFramework();
            break;

        case SendAttackBoard:
            curStep = sendAttackBoard();
            gameFramework();
            break;

        case SetGameBalance:
            curStep = setGameBalance();
            gameFramework();
            break;

        case CheckGameOver:
            curStep = checkGameOver();
            gameFramework();
            break;

        case GameOver :
            gameOver();
            break;
    }
}

private Step gameReady() {
    //게임을 준비합니다.
    initKeyListener();
    initGameStatus();
    initBlockGenerator();
    initBoardManage();
    isPlaying = true;
    
    

    return Step.CreateNewBlock;
}

public Step createNewBlock() {
    BlockGenerator.getInstance().addBlock();
    curBlock = BlockGenerator.getInstance().createBlock(index);

    BlockController nextBlock = BlockGenerator.getInstance().blockQueue.peek();
    BoardManager.getInstance(index).setNextBlockColor(nextBlock);
    InGameUIManager.getInstance().drawNextBlockInfo(nextBlock, index);
    onBlockCreate();

    return Step.BlockMove;
}

public Step blockMove() {
    isBlockMovable = BoardManager.getInstance(index).checkBlockMovable(curBlock);
    if(isBlockMovable) {
        BoardManager.getInstance(index).translateBlock(curBlock, 1, 0);
        onBlockMove();
        return Step.BlockMove;
    }
    else
        return Step.EraseAnimation;
}

private Step eraseAnimation() {
    
    BoardManager.getInstance(index).eraseEvent(targetLineIndexList, index);
    return Step.EraseLine;
}





private Step eraseLine() {


    int curLineCount = BoardManager.getInstance(index).eraseFullLine(index);
    onLineErase(curLineCount);

    return Step.SendAttackBoard;
}

private Step sendAttackBoard() {

    if(UserNumber.getInstance().user==2) {
        BoardManager.getInstance(index).attackEvent(index);
    }
    return Step.SetGameBalance;
}


private Step setGameBalance() {
    curSpeed = basicSpeed + addSpeed * (lineCount + blockCount);
    timeScale = maxSpeed / curSpeed;
    setTimeScale(timeScale);

    return Step.CheckGameOver;
}

public Step checkGameOver() {
    BlockController nextBlock = BlockGenerator.getInstance().blockQueue.peek();
    for(int i=0; i<nextBlock.height(); i++) {
        for(int j=0; j<nextBlock.width(); j++) {
            if(nextBlock.getShape(i, j) != ' ') {
                if(BoardManager.getInstance(index).board[i][j + 5] != ' ')
                    return Step.GameOver;
            }
        }
    }
    return Step.CreateNewBlock;
}

@Override
protected void gameOver() {
    onGameEnd();
    new ScoreInputUI(score,GameInfoManager.getInstance().difficultyToString(difficulty));

}

//#endregion

//#region init

public void initKeyListener() {
    interaction_play = new Interaction_Play();
    interaction_utils = new Interaction_Utils();
    //GameUI.getInstance().pane[index].addKeyListener(interaction_play);
    //GameUI.getInstance().pane[index].addKeyListener(interaction_utils);

    if(UserNumber.getInstance().user==2) {
        GameUI.getInstance().pane[0].addKeyListener(interaction_play);
        GameUI.getInstance().pane[0].addKeyListener(interaction_utils);
        GameUI.getInstance().pane[1].addKeyListener(interaction_play);
        GameUI.getInstance().pane[1].addKeyListener(interaction_utils);
    }
    else{
        GameUI.getInstance().pane[0].addKeyListener(interaction_play);
        GameUI.getInstance().pane[0].addKeyListener(interaction_utils);
    }
}

@Override
protected void initGameStatus() {
    blockCount = 0;
    lineCount = 0;
    score = 0;
    curSpeed = basicSpeed;
    
    curBlock = null;
    
    difficulty = GameInfoManager.getInstance().difficulty; 
    addSpeed = GameInfoManager.getInstance().difficultiesMap.get(difficulty).getAddSpeed();
}

protected void initBoardManage() {
    BoardManager.getInstance(index).initBoard();
}

protected void initBlockGenerator() {
    BlockGenerator.getInstance().initBlockQueue();
}

//#endregion

//#region Events
private int onBlockMove() {
    score += curSpeed;
    InGameUIManager.getInstance().drawScore(index);
    return 0;
}

public int onLineErase(int count) {
    score += curSpeed * count * 10;

    if(count > 2) {
        score += 10000;
    }

    lineCount += count;
    InGameUIManager.getInstance().drawScore(index);

    return 0;
}

private int onBlockCreate() {
    score += curSpeed;
    blockCount++;
    InGameUIManager.getInstance().drawScore(index);

    return 0;
}

public void onGameEnd() {
    stopGameFramework();
    
    isPlaying = false;
    curStep = Step.GameReady;
}

//#endregion


//#region Interaction

public class Interaction_Play implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
            
    }


    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("e.getKeyCode() = " + e.getKeyCode());
        if(index==0) {
            if (keySetting.getDownBlock() == e.getKeyCode()) {
                blockMove();
                InGameUIManager.getInstance().drawBoard(index);
                System.out.println("input down");
            } else if (keySetting.getRight() == e.getKeyCode()) {
                BoardManager.getInstance(index).translateBlock(curBlock, 0, 1);
                InGameUIManager.getInstance().drawBoard(index);
                System.out.println("input right");
            } else if (keySetting.getLeft() == e.getKeyCode()) {
                BoardManager.getInstance(index).translateBlock(curBlock, 0, -1);
                InGameUIManager.getInstance().drawBoard(index);
                System.out.println("input left");
            } else if (keySetting.getTurnBlock() == e.getKeyCode()) {
                BoardManager.getInstance(index).eraseBlock(curBlock);
                curBlock.rotate();
                if (!BoardManager.getInstance(index).checkDrawable(curBlock.shape, curBlock.posRow, curBlock.posCol)) {
                    curBlock.rotate();
                    curBlock.rotate();
                    curBlock.rotate();
                }
                BoardManager.getInstance(index).setBlockPos(curBlock, curBlock.posRow, curBlock.posCol);
                InGameUIManager.getInstance().drawBoard(index);
                System.out.println("input up");
            } else if (keySetting.getOneTimeDown() == e.getKeyCode()) {
                while (BoardManager.getInstance(index).checkBlockMovable(curBlock)) {
                    BoardManager.getInstance(index).translateBlock(curBlock, 1, 0);
                }
                timer.restart();
                InGameUIManager.getInstance().drawBoard(index);
            }
        }
        else{
            if (keySetting.getDownBlock2P() == e.getKeyCode()) {
                blockMove();
                InGameUIManager.getInstance().drawBoard(index);
                System.out.println("input down");
            } else if (keySetting.getRight2P() == e.getKeyCode()) {
                BoardManager.getInstance(index).translateBlock(curBlock, 0, 1);
                InGameUIManager.getInstance().drawBoard(index);
                System.out.println("input right");
            } else if (keySetting.getLeft2P() == e.getKeyCode()) {
                BoardManager.getInstance(index).translateBlock(curBlock, 0, -1);
                InGameUIManager.getInstance().drawBoard(index);
                System.out.println("input left");
            } else if (keySetting.getTurnBlock2P() == e.getKeyCode()) {
                BoardManager.getInstance(index).eraseBlock(curBlock);
                curBlock.rotate();
                if (!BoardManager.getInstance(index).checkDrawable(curBlock.shape, curBlock.posRow, curBlock.posCol)) {
                    curBlock.rotate();
                    curBlock.rotate();
                    curBlock.rotate();
                }
                BoardManager.getInstance(index).setBlockPos(curBlock, curBlock.posRow, curBlock.posCol);
                InGameUIManager.getInstance().drawBoard(index);
                System.out.println("input up");
            } else if (keySetting.getOneTimeDown2P() == e.getKeyCode()) {
                while (BoardManager.getInstance(index).checkBlockMovable(curBlock)) {
                    BoardManager.getInstance(index).translateBlock(curBlock, 1, 0);
                }
                timer.restart();
                InGameUIManager.getInstance().drawBoard(index);
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}

public class Interaction_Utils implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(keySetting.getStop() == e.getKeyCode()) {
            BoardManager.getInstance(index).printBoard();
            if(timer.isRunning())
                stopGameFramework();
            else
                restartGameFramework();
        }
        
        if(keySetting.getEscape() == e.getKeyCode()) {
            onGameEnd();
            new StartUI();
            GameUI.getInstance().dispose();
        }
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}

//#endregion

}
