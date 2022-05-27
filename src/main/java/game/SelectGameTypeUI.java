package game;



import game.manager.GameInfoManager;
import scoreBoard.NoItemScoreBoard.ScoreBoardUI;
import scoreBoard.scoreBoradItem.ItemScoreBoardUI;
import setting.DuplicateKeySettingException;
import setting.SaveAndLoad;
import setting.ScreenSize;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SelectGameTypeUI extends JFrame {
    private JPanel gameSelectPanel;
    ButtonGroup gameModeGroup = new ButtonGroup();
    ButtonGroup difficultyGroup = new ButtonGroup();
    private GameInfoManager gameInfoManager = GameInfoManager.getInstance();

    private JRadioButton[] gameModeBtns = new JRadioButton[2];
    private JRadioButton[] difficultyBtns = new JRadioButton[3];
    private ScreenSize screenSize =ScreenSize.getInstance();
    ImageIcon titleImg1 = new ImageIcon(getClass().getClassLoader().getResource("title1.png"));
    ImageIcon titleImg2 = new ImageIcon(getClass().getClassLoader().getResource("title2.png"));
    ImageIcon titleImg3 = new ImageIcon(getClass().getClassLoader().getResource("title3.png"));

    public SelectGameTypeUI() {
        //JFrame setting
        super("software-tetris");//제목
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 메모리까지 종료
        this.setSize(screenSize.getWidth(),screenSize.getHeight());
        this.setVisible(true);

        //board display setting
        gameSelectPanel = new JPanel();
        gameSelectPanel.setBackground(Color.BLACK);

        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5)
        );
        gameSelectPanel.setBorder(border);

        this.getContentPane().add(gameSelectPanel,BorderLayout.CENTER);
        setTitle();
        selectMode();
        selectDifficulty();
        //종료 시 현재 setting 및 scoreBoard 저장
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                try {
                    SaveAndLoad.SaveSetting();
                } catch (DuplicateKeySettingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void setTitle() {
        JButton titleBtn;
        if(screenSize.getWidth() == 800){
            titleBtn = new JButton(titleImg1);
        }
        else if(screenSize.getWidth() == 1024){
            titleBtn = new JButton(titleImg2);
        }
        else{
            titleBtn = new JButton(titleImg3);
        }
        titleBtn.setBackground(Color.BLACK);

        gameSelectPanel.add(titleBtn);
    }

    public void selectMode(){
        JPanel radioPanel = new JPanel();
        GridLayout gridLayout=new GridLayout( 2,1);
        radioPanel.setLayout(gridLayout);
        radioPanel.setBackground(Color.BLACK);
        radioPanel.setBorder(BorderFactory.createEmptyBorder(screenSize.getHeight()/4,0,0,0));

        gameModeBtns[0] = new JRadioButton("Basic-Mode");
        gameModeBtns[1] = new JRadioButton("Item-Mode");

        gameModeBtns[0].setSelected(true);

        for (int i = 0; i < 2; i++) {
            gameModeGroup.add(gameModeBtns[i]);
        }

        for (int i = 0; i < 2; i++) {
            radioPanel.add(gameModeBtns[i]);
        }

        gameSelectPanel.add(radioPanel, "Center");
    }



    public void selectDifficulty() {
        JPanel radioPanel = new JPanel();
        GridLayout gridLayout=new GridLayout( 3,1);
        radioPanel.setLayout(gridLayout);
        radioPanel.setBackground(Color.BLACK);
        radioPanel.setBorder(BorderFactory.createEmptyBorder(screenSize.getHeight()/4,0,0,0));

        difficultyBtns[0] = new JRadioButton("EASY");
        difficultyBtns[1] = new JRadioButton("NORMAL");
        difficultyBtns[2] = new JRadioButton("HARD");

        difficultyBtns[0].setSelected(true);

        for (int i = 0; i < 3; i++) {
            difficultyGroup.add(difficultyBtns[i]);
        }

        for (int i = 0; i < 3; i++) {
            radioPanel.add(difficultyBtns[i]);
        }

        gameSelectPanel.add(radioPanel, "Center");

        JPanel checkPanel = new JPanel();
        JButton btn = new JButton("완료");
        checkPanel.add(btn);
        checkPanel.setBorder((BorderFactory.createEmptyBorder(screenSize.getHeight() / 4, 0, 0, 0)));
        checkPanel.setBackground(Color.black);
        gameSelectPanel.add(checkPanel, "South");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameModeBtns[0].isSelected()) {
                    //일반모드
                    gameInfoManager.mode = GameInfoManager.GameMode.BasicMode;
                    if (difficultyBtns[0].isSelected()) {
                        gameInfoManager.difficulty = GameInfoManager.GameDifficulty.Easy;
                    } else if (difficultyBtns[1].isSelected()) {
                        gameInfoManager.difficulty = GameInfoManager.GameDifficulty.Normal;
                    } else if (difficultyBtns[2].isSelected()) {
                        gameInfoManager.difficulty = GameInfoManager.GameDifficulty.Hard;
                    }

                } else if (gameModeBtns[1].isSelected()) {
                    gameInfoManager.mode = GameInfoManager.GameMode.ItemMode;
                    if (difficultyBtns[0].isSelected()) {
                        gameInfoManager.difficulty = GameInfoManager.GameDifficulty.Easy;
                    } else if (difficultyBtns[1].isSelected()) {
                        gameInfoManager.difficulty = GameInfoManager.GameDifficulty.Normal;
                    } else if (difficultyBtns[2].isSelected()) {
                        gameInfoManager.difficulty = GameInfoManager.GameDifficulty.Hard;
                    }
                }
                new GameUI();
                dispose();

            }
        });


    }
}