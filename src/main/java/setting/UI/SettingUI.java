package setting.UI;

import game.GameUI;
import scoreBoard.NoItemScoreBoard.ScoreList;
import setting.*;
import start.StartUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.YES_NO_OPTION;
import static start.IsButtonClicked.btnClicked;

public class SettingUI extends JFrame {

    private JPanel mainPanel;
    private ScreenSize screenSize = ScreenSize.getInstance();
    private JButton[] buttons = new JButton[6];
    private ScoreList scoreList = ScoreList.getInstance();
    private KeySetting keySetting = KeySetting.getInstance();
    private ColorBlind colorBlind = ColorBlind.getInstance();

    ImageIcon titleImg1 = new ImageIcon(getClass().getClassLoader().getResource("title1.png"));
    ImageIcon titleImg2 = new ImageIcon(getClass().getClassLoader().getResource("title2.png"));
    ImageIcon titleImg3 = new ImageIcon(getClass().getClassLoader().getResource("title3.png"));

    private final String settingList[] = {"화면사이즈 조절", "게임 조작 키 설정", "스코어보드 초기화", "색맹모드", "모든 설정 기본으로 돌리기","시작메뉴"};  //스코어 보드는 테스트용 -> 실제는 게임 시작화면에 있어야함

    public enum Scene {
        StartUI,
        GameUI
    }

    public Scene prevScene = Scene.StartUI;

    public SettingUI(){
        super("software-tetris");//제목
        init();
    }

    public SettingUI(Scene scene) {
        super("software-tetris");//제목
        prevScene = scene;
        init();
    }

    public void init() {
                //JFrame setting
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 메모리까지 종료
        this.setSize(screenSize.getWidth(),screenSize.getHeight());
        this.setVisible(true);

        //board display setting
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);

        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5)
        );
        mainPanel.setBorder(border);

        this.getContentPane().add(mainPanel,BorderLayout.CENTER);
        setTitle();

        //btnPanel setting
        JPanel btnPanel = new JPanel();
        GridLayout gridLayout=new GridLayout( 6,1);
        btnPanel.setLayout(gridLayout);
        btnPanel.setBackground(Color.BLACK);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(screenSize.getHeight()/4,0,0,0));



        for (int i = 0; i < settingList.length; i++) {
            buttons[i] = new JButton(settingList[i]);
            btnPanel.add(buttons[i]);

        }
        mainPanel.add(btnPanel, "Center");

        btnClicked.setFixSizeBtnClicked();
        if(btnClicked.isFixSizeBtnClicked()){
            buttons[0].setForeground(Color.red);
        }

        //화면사이즈 조절 event
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FixSizeUI();
                setVisible(false);
            }
        });

        //화면사이즈 조절 event
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new KeySettingUI();
                setVisible(false);
            }
        });

        //스코어 보드 기록 초기화
        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?","스코어 보드 기록 초기화",YES_NO_OPTION);
                if(result==0){
                    scoreList.deleteAll();
                }
            }
        });

        //색맹모드 UI
        buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ColorSettingUI();
                setVisible(false);
            }
        });

        //모든 설정 기본으로 돌리기
        buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "모든 설정을 초기화 하시겠습니까??","설정 초기화",YES_NO_OPTION);
                if(result==0){


                    screenSize.setWidth(800);
                    screenSize.setHeight(600);

                    colorBlind.setCurColorBlind(ColorBlind.ColorSetting.BASIC);


                    keySetting.resetDefault();

                    setVisible(false);
                    new SettingUI();
                }
            }
        });



        //시작메뉴로
        buttons[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(prevScene) {
                    case StartUI:
                        new StartUI();
                        dispose();
                        break;
                    case GameUI:
                        new GameUI();
                        dispose();
                        break;
                }
            }
        });

        //메뉴 방향키 조작
        buttons[5].addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if(btnClicked.isFixSizeBtnClicked()){
                        btnClicked.setBackStartBtnClicked();
                        buttons[5].setForeground(Color.red);
                        buttons[0].setForeground(Color.black);
                    }
                    else if(btnClicked.isKsuBtnClicked()){
                        btnClicked.setFixSizeBtnClicked();
                        buttons[0].setForeground(Color.red);
                        buttons[1].setForeground(Color.black);
                    }
                    else if(btnClicked.isInitialScbBtnClicked()){
                        btnClicked.setKsuBtnClicked();
                        buttons[1].setForeground(Color.red);
                        buttons[2].setForeground(Color.black);
                    }
                    else if(btnClicked.isColorBlindBtnClicked()){
                        btnClicked.setInitialScbBtnClicked();
                        buttons[2].setForeground(Color.red);
                        buttons[3].setForeground(Color.black);
                    }
                    else if(btnClicked.isReturnAllBtnClicked()){
                        btnClicked.setColorBlindBtnClicked();
                        buttons[3].setForeground(Color.red);
                        buttons[4].setForeground(Color.black);
                    }
                    else if(btnClicked.isBackStartBtnClicked()){
                        btnClicked.setReturnAllBtnClicked();
                        buttons[4].setForeground(Color.red);
                        buttons[5].setForeground(Color.black);
                    }

                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if(btnClicked.isFixSizeBtnClicked()){
                        btnClicked.setKsuBtnClicked();
                        buttons[1].setForeground(Color.red);
                        buttons[0].setForeground(Color.black);
                    }
                    else if(btnClicked.isKsuBtnClicked()){
                        btnClicked.setInitialScbBtnClicked();
                        buttons[2].setForeground(Color.red);
                        buttons[1].setForeground(Color.black);
                    }
                    else if(btnClicked.isInitialScbBtnClicked()){
                        btnClicked.setColorBlindBtnClicked();
                        buttons[3].setForeground(Color.red);
                        buttons[2].setForeground(Color.black);
                    }
                    else if(btnClicked.isColorBlindBtnClicked()){
                        btnClicked.setReturnAllBtnClicked();
                        buttons[4].setForeground(Color.red);
                        buttons[3].setForeground(Color.black);
                    }
                    else if(btnClicked.isReturnAllBtnClicked()){
                        btnClicked.setBackStartBtnClicked();
                        buttons[5].setForeground(Color.red);
                        buttons[4].setForeground(Color.black);
                    }
                    else if(btnClicked.isBackStartBtnClicked()){
                        btnClicked.setFixSizeBtnClicked();
                        buttons[0].setForeground(Color.red);
                        buttons[5].setForeground(Color.black);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if(btnClicked.isFixSizeBtnClicked()){
                        btnClicked.setBackStartBtnClicked();
                        buttons[5].setForeground(Color.red);
                        buttons[0].setForeground(Color.black);
                    }
                    else if(btnClicked.isKsuBtnClicked()){
                        btnClicked.setFixSizeBtnClicked();
                        buttons[0].setForeground(Color.red);
                        buttons[1].setForeground(Color.black);
                    }
                    else if(btnClicked.isInitialScbBtnClicked()){
                        btnClicked.setKsuBtnClicked();
                        buttons[1].setForeground(Color.red);
                        buttons[2].setForeground(Color.black);
                    }
                    else if(btnClicked.isColorBlindBtnClicked()){
                        btnClicked.setInitialScbBtnClicked();
                        buttons[2].setForeground(Color.red);
                        buttons[3].setForeground(Color.black);
                    }
                    else if(btnClicked.isReturnAllBtnClicked()){
                        btnClicked.setColorBlindBtnClicked();
                        buttons[3].setForeground(Color.red);
                        buttons[4].setForeground(Color.black);
                    }
                    else if(btnClicked.isBackStartBtnClicked()){
                        btnClicked.setReturnAllBtnClicked();
                        buttons[4].setForeground(Color.red);
                        buttons[5].setForeground(Color.black);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if(btnClicked.isFixSizeBtnClicked()){
                        btnClicked.setKsuBtnClicked();
                        buttons[1].setForeground(Color.red);
                        buttons[0].setForeground(Color.black);
                    }
                    else if(btnClicked.isKsuBtnClicked()){
                        btnClicked.setInitialScbBtnClicked();
                        buttons[2].setForeground(Color.red);
                        buttons[1].setForeground(Color.black);
                    }
                    else if(btnClicked.isInitialScbBtnClicked()){
                        btnClicked.setColorBlindBtnClicked();
                        buttons[3].setForeground(Color.red);
                        buttons[2].setForeground(Color.black);
                    }
                    else if(btnClicked.isColorBlindBtnClicked()){
                        btnClicked.setReturnAllBtnClicked();
                        buttons[4].setForeground(Color.red);
                        buttons[3].setForeground(Color.black);
                    }
                    else if(btnClicked.isReturnAllBtnClicked()){
                        btnClicked.setBackStartBtnClicked();
                        buttons[5].setForeground(Color.red);
                        buttons[4].setForeground(Color.black);
                    }
                    else if(btnClicked.isBackStartBtnClicked()){
                        btnClicked.setFixSizeBtnClicked();
                        buttons[0].setForeground(Color.red);
                        buttons[5].setForeground(Color.black);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if(btnClicked.isFixSizeBtnClicked()){
                        new FixSizeUI();
                        setVisible(false);
                    }
                    else if(btnClicked.isKsuBtnClicked()){
                        new KeySettingUI();
                        setVisible(false);
                    }
                    else if(btnClicked.isInitialScbBtnClicked()){
                        int result = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?","스코어 보드 기록 초기화",YES_NO_OPTION);
                        if(result==0){
                            scoreList.deleteAll();
                        }
                    }
                    else if(btnClicked.isColorBlindBtnClicked()){
                        new ColorSettingUI();
                        setVisible(false);
                    }
                    else if(btnClicked.isReturnAllBtnClicked()){
                        int result = JOptionPane.showConfirmDialog(null, "모든 설정을 초기화 하시겠습니까??","설정 초기화",YES_NO_OPTION);
                        if(result==0){
                            screenSize.setWidth(800);
                            screenSize.setHeight(600);

                            keySetting.resetDefault();

                            setVisible(false);
                            new SettingUI();
                        }
                    }
                    else if(btnClicked.isBackStartBtnClicked()){
                        new StartUI();
                        setVisible(false);
                    }
                }

            }
        });
        buttons[5].requestFocus();


//        setLabel();
//        backBtn();

        //종료 시 현재 setting값 저장
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

        mainPanel.add(titleBtn);
    }


}
