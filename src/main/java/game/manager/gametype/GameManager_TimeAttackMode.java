package game.manager.gametype;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import game.GameUI;
import game.container.BlockGenerator;
import game.manager.BoardManager;
import game.manager.DualModeUtils.GameEndForDualUI;
import game.manager.DualModeUtils.UserNumber;
import game.manager.GameInfoManager;
import game.manager.GameManager;
import game.manager.InGameUIManager;
import game.model.BlockController;
import scoreBoard.NoItemScoreBoard.ScoreInputUI;
import start.StartUI;

import javax.swing.*;

public class GameManager_TimeAttackMode extends GameManager {

//#region Gimmick
    public static float timeLimit;
    private static float maxTimeLimit;
    private static Timer additionalTimer;
    
    public static void AdditionalTimer(float maxSecond) {

        maxTimeLimit = maxSecond;
        timeLimit = maxTimeLimit;

        additionalTimer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                
				timeLimit -= 0.1f;
                System.out.println(timeLimit);
                if(timeLimit <= 0f)
                {
                    GameManager_TimeAttackMode.TimeLimit();
                }
			}
		});

        additionalTimer.start();
    }

    private static void TimeLimit()
    {
        GameManager_TimeAttackMode.getInstance(0).gameOver();
        //GameManager_TimeAttackMode.getInstance(1).gameOver(); //하나만 끝나도 괜찮다.
        additionalTimer.stop();
    }
//#endregion

//#region Singleton

    private GameManager_TimeAttackMode(int index) {
        this.index = index;
    }

    private static GameManager_TimeAttackMode instance = new GameManager_TimeAttackMode(0);
    private static GameManager_TimeAttackMode instance2 = new GameManager_TimeAttackMode(1);

    private static ArrayList<GameManager_TimeAttackMode> gameManagerList =
        new ArrayList<GameManager_TimeAttackMode>(Arrays.asList(instance, instance2));

    public static GameManager_TimeAttackMode getInstance(int i) {
        return gameManagerList.get(i);
    }

//#endregion


//#region GameFramework

public Step curStep = Step.GameReady;

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
    BlockGenerator.getInstance(index).addBlock();
    curBlock = BlockGenerator.getInstance(index).createBlock(index);

    BlockController nextBlock = BlockGenerator.getInstance(index).blockQueue.peek();
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

        isBlockMovable = BoardManager.getInstance(index).checkBlockMovable(curBlock);
        if(!isBlockMovable)
        {
            return Step.EraseAnimation;
        }
        
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
    BlockController nextBlock = BlockGenerator.getInstance(index).blockQueue.peek();
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
        if(UserNumber.getInstance().user==2){
            additionalTimer.stop();
            instance.onGameEnd();
            instance2.onGameEnd();
            new GameEndForDualUI(instance.score, instance2.score);
        }
        else{
            additionalTimer.stop();
            instance.onGameEnd();
            new ScoreInputUI(score,GameInfoManager.getInstance().difficultyToString(difficulty));
        }
        GameUI.getInstance().setVisible(false);
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
    BlockGenerator.getInstance(index).initBlockQueue();
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
        if(index==0) {
            if (keySetting.getDownBlock() == e.getKeyCode()) {
                blockMove();
            } else if (keySetting.getRight() == e.getKeyCode()) {
                BoardManager.getInstance(index).translateBlock(curBlock, 0, 1);
            } else if (keySetting.getLeft() == e.getKeyCode()) {
                BoardManager.getInstance(index).translateBlock(curBlock, 0, -1);
            } else if (keySetting.getTurnBlock() == e.getKeyCode()) {
                BoardManager.getInstance(index).eraseBlock(curBlock);
                curBlock.rotate();
                if (!BoardManager.getInstance(index).checkDrawable(curBlock.shape, curBlock.posRow, curBlock.posCol)) {
                    curBlock.rotate();
                    curBlock.rotate();
                    curBlock.rotate();
                }
                BoardManager.getInstance(index).setBlockPos(curBlock, curBlock.posRow, curBlock.posCol);
            } else if (keySetting.getOneTimeDown() == e.getKeyCode()) {
                while (BoardManager.getInstance(index).checkBlockMovable(curBlock)) {
                    BoardManager.getInstance(index).translateBlock(curBlock, 1, 0);
                    onBlockMove();
                    InGameUIManager.getInstance().drawScore(index);
                }
                timer.restart();
                InGameUIManager.getInstance().drawBoard(index);
            }
        }
        else{
            if (keySetting.getDownBlock2P() == e.getKeyCode()) {
                blockMove();
            } else if (keySetting.getRight2P() == e.getKeyCode()) {
                BoardManager.getInstance(index).translateBlock(curBlock, 0, 1);
            } else if (keySetting.getLeft2P() == e.getKeyCode()) {
                BoardManager.getInstance(index).translateBlock(curBlock, 0, -1);
            } else if (keySetting.getTurnBlock2P() == e.getKeyCode()) {
                BoardManager.getInstance(index).eraseBlock(curBlock);
                curBlock.rotate();
                if (!BoardManager.getInstance(index).checkDrawable(curBlock.shape, curBlock.posRow, curBlock.posCol)) {
                    curBlock.rotate();
                    curBlock.rotate();
                    curBlock.rotate();
                }
                BoardManager.getInstance(index).setBlockPos(curBlock, curBlock.posRow, curBlock.posCol);
            } else if (keySetting.getOneTimeDown2P() == e.getKeyCode()) {
                while (BoardManager.getInstance(index).checkBlockMovable(curBlock)) {
                    BoardManager.getInstance(index).translateBlock(curBlock, 1, 0);
                    onBlockMove();
                    InGameUIManager.getInstance().drawScore(index);
                }
                timer.restart();
                InGameUIManager.getInstance().drawBoard(index);
            }
        }

        InGameUIManager.getInstance().drawScore(index);
        InGameUIManager.getInstance().drawBoard(index);
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
            if(timer.isRunning()){
                additionalTimer.stop();
                stopGameFramework();
            }
            else{
                additionalTimer.restart();
                restartGameFramework();
            }
        }
        
        if(keySetting.getEscape() == e.getKeyCode()) {
            additionalTimer.stop();
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
