package game.manager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import game.manager.DualModeUtils.UserNumber;
import game.model.BlockController;
import game.manager.gametype.GameManager_TimeAttackMode;
import game.manager.gametype.GameManager_ItemMode;
import game.manager.gametype.GameManager_BasicMode;

import javax.swing.*;

public class BoardManager {

//#region Singleton
    
    private static BoardManager instance = new BoardManager();
    private static BoardManager instance2 = new BoardManager();

    private static ArrayList<BoardManager> boardManagerList =
        new ArrayList<BoardManager>(Arrays.asList(instance, instance2));

    public static BoardManager getInstance(int i) {
        return boardManagerList.get(i);
    }

    /*
    public static BoardManager getInstance() {
        return instance;
    }
    */
    
//#endregion

//#region Board 

    //board에 문자를 입력하여 현태 상태 표시
    // 'X' : 테두리
    // 'O' :
    public char[][] board;
    public char[][] board2;
    public char[][] board3;
    public char[][] attackBoard;
    public char[][] boardColor;
    public char[][] attackBoardColor;
    public char[][] nextBlock;
    public char[][] nextBlockColor;
    int m =5; int n=6;

    public BoardManager() {
        board = new char [][] {
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, //1행
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, //10행
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, //20행
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}, 
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}
        };

        board2 = new char [][] { //lineclear되기 전 board의 상태를 저장
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, //1행
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, //10행
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'}, //20행
                {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}
        };

        attackBoard = new char [][] {
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, //1행,
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}  //10행
        };



        boardColor = new char [][] {
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'}, //1행
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'}, //10행
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'}, //20행
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
            {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
        };

        attackBoardColor = new char [][] {
                {'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},//1행
                {'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
                {'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
                {'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
                {'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
                {'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
                {'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
                {'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
                {'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L'},
                {'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L'} //10행

        };

        nextBlock = new char [][]{
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        nextBlockColor = new char[][]{
                {'B', 'B', 'B', 'B'},
                {'B', 'B', 'B', 'B'}
        };
    }

//#endregion

//#region Board Controll

    //#region 블럭 이동 관련

    public BlockController setBlockPos(BlockController curBlock, int targetRow, int targetCol) {
        for(int i=0; i<curBlock.height(); i++) {
            for(int j=0; j<curBlock.width(); j++) {
                if(curBlock.getShape(i, j) == 'O') {
                    board[targetRow+i][targetCol+j] = 'O';
                    boardColor[targetRow+i][targetCol+j] = curBlock.getColor();
                }
                else if(curBlock.getShape(i, j) != ' ') {
                    board[targetRow+i][targetCol+j] = curBlock.getShape(i,j);
                    boardColor[targetRow+i][targetCol+j] = 'W';
                }
				    
            }
        }
        
        curBlock.posRow = targetRow;
        curBlock.posCol = targetCol;

        return curBlock;
    }


    public void eraseBlock(BlockController curBlock) {
        for(int i=0; i<curBlock.height(); i++) {
            for(int j=0; j<curBlock.width(); j++) {
                if(curBlock.getShape(i, j) != ' ')
                    board[curBlock.posRow+i][curBlock.posCol+j] = ' ';
            }
        }
    }

    public void translateBlock(BlockController curBlock, int row, int col) {
        eraseBlock(curBlock);
        if(checkDrawable(curBlock.shape, curBlock.posRow+row, curBlock.posCol+col))
            setBlockPos(curBlock, curBlock.posRow+row, curBlock.posCol+col);
        else
            setBlockPos(curBlock, curBlock.posRow, curBlock.posCol);
    }
    //#endregion

    //#region 행 삭제 관련
    public int eraseFullLine(int boardIndex) {
        if(boardIndex == 0){
            boardIndex = 1;
        }
        else if(boardIndex == 1){
            boardIndex = 0;
        }
        int clear[] = new int[22];
        int clearsum = 0;
        int lineCount = 0;
        for(int i=0; i<20; i++) {
            boolean isFull = true;
            for(int j = 1; j<11; j++ ) {
                if(board[i][j] == ' ') {
                    isFull = false;
                }
            }
            if(isFull) {
                clear[i] = 1;
            }
        }
        for(int i=0; i<20; i++) {
            clearsum += clear[i];
        }

        for(int i=0; i<20; i++) {
            boolean isFull = true;
            for(int j = 1; j<11; j++ ) {
                if(board[i][j] == ' ') {
                    isFull = false;
                } 
            }
            if(isFull) {
                for(int j=i; j>0; j--)
                {
                    board[j] = board[j-1].clone();
                    boardColor[j] = boardColor[j-1].clone();
                }
                if(UserNumber.getInstance().user==2 && clearsum >=2) {
                    for(int j=0; j<9; j++)
                    {
                        attackBoard[j] = attackBoard[j+1].clone();
                    }
                    for(int j=0; j<10; j++)
                    {
                        attackBoard[9][j] = board2[i][j+1];
                    }
                    InGameUIManager.getInstance().drawAttackBoard(boardIndex);

                }

                lineCount++;
            }
        }

        return lineCount;
    }

    public void attackEvent(int boardIndex){


        if(boardIndex == 0){
            boardIndex = 1;
        }
        else if(boardIndex == 1){
            boardIndex = 0;
        }

        int attacklinecount = 0;
        for(int i=0; i<10; i++) {
            int attackcount =0;
            for (int j = 0; j < 10; j++) {
                if (BoardManager.getInstance(boardIndex).attackBoard[i][j] == ' ') {
                    attackcount++;
                }
            }
            if (attackcount < 10) {
                attacklinecount++;
            }
        }

        for(int i=0; i<20-attacklinecount; i++)
        {
            board[i] = board[i+attacklinecount].clone();
            boardColor[i] = boardColor[i+attacklinecount].clone();
        }
        for(int i=20-attacklinecount; i<20; i++)
        {
            board[i][0] = 'X';
            for(int j=1; j<11; j++)
            {

                board[i][j] = BoardManager.getInstance(boardIndex).attackBoard[i-10][j-1];
                boardColor[i][j] = 'L';
            }
            board[i][11] = 'X';
        }

        for(int i=0; i<20; i++) {
            for(int j = 1; j<11; j++ ) {
                board2[i][j] = board[i][j];
            }
        }

        BoardManager.getInstance(boardIndex).eraseAttackBoard();
        if(boardIndex == 0){
            boardIndex = 1;
        }
        else if(boardIndex == 1){
            boardIndex = 0;
        }
        InGameUIManager.getInstance().drawAttackBoard(boardIndex);

    }

    public void initBoard(){
        for(int i=0; i<20; i++){
            eraseSelectRow(i);
        }
    }

    public void eraseSelectRow(int row){
        char[] emptyLine = {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'};

        board[row] = emptyLine.clone();
    }

    public void eraseAttackBoard(){
        char[] emptyAttackLine = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        for(int i=0; i<10; i++){
            attackBoard[i] = emptyAttackLine.clone();
        }
    }

    public void eraseHalfBoard(){
        for(int i =0; i<10; i++){
            eraseSelectRow(i);
        }
    }

    public void eraseUnderBlock(BlockController curBlock){
        for(int i = 0; i<curBlock.width(); i++){
            board[curBlock.posRow + curBlock.height()][curBlock.posCol + i] = ' ';
        }
    }
    //#endregion

    //#region animation
    public void eraseEvent(ArrayList<Integer> additionalIndex, int boardIndex){
        int clear[] = new int[22];
        int clearsum = 0;
        m=5; n=6;
        Arrays.fill(clear,0);

        for(int i=0; i<20; i++) {
            boolean isFull = true;
            for(int j = 1; j<11; j++ ) {
                if(board[i][j] == ' ') {
                    isFull = false;
                } 
            }
            //줄삭제 아이템 활성화 : L이 있는 줄 제거
            for(int j = 1; j<11; j++ ) {
                if(board[i][j] == 'L') {
                    isFull = true;
                }
            }
            if(isFull) {
                clear[i] = 1;
            }
        }

        for(int i=0; i<additionalIndex.size(); i++) {
            int index = additionalIndex.get(i);
            clear[index] = 1;            
        }
        
        for(int i=0; i<20; i++) {
            clearsum += clear[i];
        }

        if(clearsum > 0) {

            Timer timers = new Timer(1,
            
            new ActionListener() {
                int phase = 1;
/*
                
*/
                public void actionPerformed (ActionEvent e)
                {
                    if(GameManager_BasicMode.getInstance(boardIndex).curSpeed > 2500|| GameManager_ItemMode.getInstance(boardIndex).curSpeed>2500||
                            GameManager_TimeAttackMode.getInstance(boardIndex).curSpeed>2500)
                    {
                        System.out.println("hello");
                        phase += 10;
                    }

                    switch (phase){
                        case 1:  for(int i=0; i<22; i++) {
                            if(clear[i]==1) {
                                board[i][1] = '┏';
                                for(int j=2; j<11; j++)
                                {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 2:  for(int i=0; i<22; i++) {
                            if(clear[i]==1) {
                                board[i][1] = ' ';
                                board[i][2] = '─';
                                for(int j=3; j<11; j++)
                                {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 3:  for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                board[i][1] = ' ';
                                board[i][2] = ' ';
                                board[i][3] = '─';
                                for (int j = 4; j < 11; j++) {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 4:  for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 4; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][4] = '─';
                                for (int j = 5; j < 11; j++) {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 5:  for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 5; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][5] = '─';
                                for (int j = 6; j < 11; j++) {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 6:  for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 6; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][6] = '─';
                                for (int j = 7; j < 11; j++) {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 7:  for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 7; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][7] = '─';
                                for (int j = 8; j < 11; j++) {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 8:  for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 8; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][8] = '─';
                                for (int j = 9; j < 11; j++) {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 9:  for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 9; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][9] = '─';
                                board[i][10] = ' ';
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 10: for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 10; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][10] = '┓';
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 11: for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 10; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][10] = '┛';
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 12: for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 9; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][9] = '_';
                                board[i][10] = ' ';
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 13:  for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 8; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][8] = '_';
                                for (int j = 9; j < 11; j++) {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 14:  for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 7; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][7] = '_';
                                for (int j = 8; j < 11; j++) {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 15:  for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 6; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][6] = '_';
                                for (int j = 7; j < 11; j++) {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 16:  for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 5; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][5] = '_';
                                for (int j = 6; j < 11; j++) {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 17:  for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                for (int j = 1; j < 4; j++) {
                                    board[i][j] = ' ';
                                }
                                board[i][4] = '_';
                                for (int j = 5; j < 11; j++) {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 18:  for(int i=0; i<22; i++) {
                            if (clear[i] == 1) {
                                board[i][1] = ' ';
                                board[i][2] = ' ';
                                board[i][3] = '_';
                                for (int j = 4; j < 11; j++) {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 19:  for(int i=0; i<22; i++) {
                            if(clear[i]==1) {
                                board[i][1] = ' ';
                                board[i][2] = '_';
                                for(int j=3; j<11; j++)
                                {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        case 20:  for(int i=0; i<22; i++) {
                            if(clear[i]==1) {
                                board[i][1] = '┗';
                                for(int j=2; j<11; j++)
                                {
                                    board[i][j] = ' ';
                                }
                            }
                        }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            phase++;
                            break;
                        default : for(int i=0; i<22; i++) {
                            if(clear[i]==1) {
                                board[i][m] = '*';
                                board[i][n] = '*';
                            }
                        }
                            if(m>0) {
                                m--; n++;
                            }
                            InGameUIManager.getInstance().drawBoard(boardIndex);
                            //System.out.println("블록 제거중" + m);
                            if(m ==0){
                                ((Timer)e.getSource()).stop();
                                for(int i=0; i<22; i++) {
                                    if(clear[i]==1) {
                                        board[i][0] = 'X';
                                        for(int j=1; j<11; j++)
                                        {
                                            board[i][j] = 'O';
                                        }
                                        board[i][11] = 'X';
                                    }
                                }
                                //System.out.println("블록 제거 완료" + m);
                            }
                            break;
                    }
                }
                
            });

            timers.start();
        }

        if(UserNumber.getInstance().user==2) {
            if(clearsum == 0){
                for(int i=0; i<20; i++) {
                    for(int j = 1; j<11; j++ ) {
                        board2[i][j] = board[i][j];
                    }
                }
            }
        }

    }
//#endregion

//#endregion

//#region checkDrawable
    
    public boolean checkDrawable(char[][] targetShape, int targetRow, int targetCol) {

        int height = targetShape.length;
        int width = 0;

        if(height > 0)
            width = targetShape[0].length;

        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                if(targetShape[i][j] != ' ' && board[targetRow+i][targetCol+j] != ' ')
                    return false;
            }
        }

        return true;
    }

    public boolean checkBlockMovable(BlockController curBlock) {
        int[] indexOfBottom = new int[curBlock.width()];

        for(int i=0; i<curBlock.width(); i++) {
            for(int j=0; j<curBlock.height(); j++) {
                if(curBlock.shape[j][i] != ' ')
                    indexOfBottom[i] = j;
            }
        }

        for(int i=0; i<curBlock.width(); i++) { 
            if(board[curBlock.posRow + indexOfBottom[i] + 1][curBlock.posCol + i] != ' ') {
                return false;
            }
        }

        return true;
    }

//    public boolean checkLeftSide(BlockController curBlock) {
//        int[] indexOfLeft = new int[curBlock.height()];
//
//        for(int i=0; i<curBlock.height(); i++) {
//            for(int j=0; j<curBlock.width(); j++) {
//                if(curBlock.shape[i][j] == 'O') {
//                    indexOfLeft[i] = j;
//                    break;
//                }
//            }
//        }
//
//        for(int i=0; i<curBlock.height(); i++) {
//            if(board[curBlock.posRow + i][curBlock.posCol + indexOfLeft[i] - 1] != ' ') {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    public boolean checkRightSide(BlockController curBlock) {
//        int[] indexOfRight = new int[curBlock.height()];
//
//        for(int i=0; i<curBlock.height(); i++) {
//            for(int j=0; j<curBlock.width(); j++) {
//                if(curBlock.shape[i][j] == 'O') {
//                    indexOfRight[i] = j;
//                }
//            }
//        }
//
//        for(int i=0; i<curBlock.height(); i++) {
//            if(board[curBlock.posRow + i][curBlock.posCol + indexOfRight[i] + 1] != ' ') {
//                return false;
//            }
//        }
//
//        return true;
//    }
//#endregion

//#region nextBlock Board
public BlockController setNextBlockColor(BlockController nextBlock) {
    for (int i = 0; i < nextBlock.height(); i++) {
        for (int j = 0; j < nextBlock.width(); j++) {
            if(nextBlock.getShape(i,j) == 'O') {
                nextBlockColor[i][j] = nextBlock.getColor();
            }
            else{
                nextBlockColor[i][j] = 'W';
            }
        }
    }
     return nextBlock;
}
//#endregion

//#region Debug
    public void printBoard() {
        for(int i=0; i<22; i++)
        {
            for(int j=0; j<12; j++)
            {
                char curText = board[i][j];
                if(curText == 0)
                    System.out.print(" ");
                else
                    System.out.print(curText);
            }
            System.out.print("\n");
        }
    }

    public void useWeight(BlockController curBlock) {
        int weightBlockWidth=4;

        for (int i = 0; i < weightBlockWidth; i++) {
            board[curBlock.posRow+1][curBlock.posCol+i]=' ';
        }
    }

    public boolean checkWeightMovable(BlockController curBlock) {
        int row = curBlock.posRow+1;
        if(row<19){
            return true;
        }
        else{
            return false;
        }
    }


}

