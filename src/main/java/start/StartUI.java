package start;

import game.GameUI;
import game.GameUI;
import game.SelectDualGameTypeUI;
import game.SelectGameTypeUI;
import game.manager.DualModeUtils.UserNumber;
import scoreBoard.NoItemScoreBoard.ScoreBoardUI;
import scoreBoard.SelectScoreBoardUI;
import setting.DuplicateKeySettingException;
import setting.SaveAndLoad;
import setting.ScreenSize;
import setting.UI.SettingUI;


import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.*;


import static start.IsButtonClicked.btnClicked;

public class StartUI extends JFrame {

    public  JPanel mainPanel;
    private ScreenSize screenSize = ScreenSize.getInstance();
    private KeyListener startInteraction;
    public UserNumber userNumber = UserNumber.getInstance();

    ImageIcon titleImg1 = new ImageIcon(getClass().getClassLoader().getResource("title1.png"));
    ImageIcon titleImg2 = new ImageIcon(getClass().getClassLoader().getResource("title2.png"));
    ImageIcon titleImg3 = new ImageIcon(getClass().getClassLoader().getResource("title3.png"));

    private static StartUI instance;
    public static StartUI getInstance() {
        if(instance != null)
            return instance;
        else
        {
            System.out.println("Error : GameUI Instance == null ");
            return null;
        }
    }


    public StartUI(){
        //JFrame setting
        super("software-tetris");//제목
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 메모리까지 종료
        this.setSize(screenSize.getWidth(), screenSize.getHeight());
        this.setVisible(true);

        //board display setting
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.BLACK);

        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5)
        );
        mainPanel.setBorder(border);



        instance = this;

        this.getContentPane().add(mainPanel);


        titleBtn();

        JButton gameButtons = new JButton("게임 시작");
        gameButtons.setBounds(this.getWidth()/2-this.getWidth()/12,this.getHeight()/3,this.getWidth()/6,this.getHeight()/16);
        mainPanel.add(gameButtons);

        JButton dualGameButtons = new JButton("2P 게임 시작");
        dualGameButtons.setBounds(this.getWidth()/2-this.getWidth()/12,this.getHeight()/3+this.getHeight()/16,this.getWidth()/6,this.getHeight()/16);
        mainPanel.add(dualGameButtons);

        JButton settingButtons = new JButton("설정 메뉴");
        settingButtons.setBounds(this.getWidth()/2-this.getWidth()/12,this.getHeight()/3+this.getHeight()*2/16,this.getWidth()/6,this.getHeight()/16);

        mainPanel.add(settingButtons);

        JButton scbButtons = new JButton("스코어보드");
        scbButtons.setBounds(this.getWidth()/2-this.getWidth()/12,this.getHeight()/3+this.getHeight()*3/16,this.getWidth()/6,this.getHeight()/16);

        mainPanel.add(scbButtons);

        JButton exitButtons = new JButton("게임 종료");
        exitButtons.setBounds(this.getWidth()/2-this.getWidth()/12,this.getHeight()/3+this.getHeight()*4/16,this.getWidth()/6,this.getHeight()/16);

        mainPanel.add(exitButtons);


        btnClicked.setGameBtnClicked();
        if(btnClicked.isGameBtnClicked()){
            gameButtons.setForeground(Color.red);
        }

        //다음으로 넘어가는 event
        gameButtons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userNumber.user=1;
                new SelectGameTypeUI();
                dispose();
            }
        });

        gameButtons.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (btnClicked.isGameBtnClicked()) {
                        btnClicked.setExitBtnClicked();
                        exitButtons.setForeground(Color.red);
                        gameButtons.setForeground(Color.black);
                    } else if (btnClicked.isDualGameBtnClicked()) {
                        btnClicked.setGameBtnClicked();
                        gameButtons.setForeground(Color.red);
                        dualGameButtons.setForeground(Color.black);
                    } else if (btnClicked.isSettingBtnClicked()) {
                        btnClicked.setDualGameBtnClicked();
                        dualGameButtons.setForeground(Color.red);
                        settingButtons.setForeground(Color.black);
                    } else if (btnClicked.isScbBtnClicked()) {
                        btnClicked.setSettingBtnClicked();
                        settingButtons.setForeground(Color.red);
                        scbButtons.setForeground(Color.black);

                    } else if (btnClicked.isExitBtnClicked()) {
                        btnClicked.setScbBtnClicked();
                        scbButtons.setForeground(Color.red);
                        exitButtons.setForeground(Color.black);
                    }

                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if(btnClicked.isGameBtnClicked()){
                        btnClicked.setDualGameBtnClicked();
                        dualGameButtons.setForeground(Color.red);
                        gameButtons.setForeground(Color.black);
                    }else if(btnClicked.isDualGameBtnClicked()){
                        btnClicked.setSettingBtnClicked();
                        settingButtons.setForeground(Color.red);
                        dualGameButtons.setForeground(Color.black);
                    }
                    else if(btnClicked.isSettingBtnClicked()){
                        btnClicked.setScbBtnClicked();
                        scbButtons.setForeground(Color.red);
                        settingButtons.setForeground(Color.black);
                    }
                    else if(btnClicked.isScbBtnClicked()){
                        btnClicked.setExitBtnClicked();
                        exitButtons.setForeground(Color.red);
                        scbButtons.setForeground(Color.black);
                    }
                    else if(btnClicked.isExitBtnClicked()){
                        btnClicked.setGameBtnClicked();
                        gameButtons.setForeground(Color.red);
                        exitButtons.setForeground(Color.black);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (btnClicked.isGameBtnClicked()) {
                        btnClicked.setExitBtnClicked();
                        exitButtons.setForeground(Color.red);
                        gameButtons.setForeground(Color.black);
                    } else if (btnClicked.isDualGameBtnClicked()) {
                        btnClicked.setGameBtnClicked();
                        gameButtons.setForeground(Color.red);
                        dualGameButtons.setForeground(Color.black);
                    } else if (btnClicked.isSettingBtnClicked()) {
                        btnClicked.setDualGameBtnClicked();
                        dualGameButtons.setForeground(Color.red);
                        settingButtons.setForeground(Color.black);
                    } else if (btnClicked.isScbBtnClicked()) {
                        btnClicked.setSettingBtnClicked();
                        settingButtons.setForeground(Color.red);
                        scbButtons.setForeground(Color.black);

                    } else if (btnClicked.isExitBtnClicked()) {
                        btnClicked.setScbBtnClicked();
                        scbButtons.setForeground(Color.red);
                        exitButtons.setForeground(Color.black);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if(btnClicked.isGameBtnClicked()){
                        btnClicked.setDualGameBtnClicked();
                        dualGameButtons.setForeground(Color.red);
                        gameButtons.setForeground(Color.black);
                    }else if(btnClicked.isDualGameBtnClicked()){
                        btnClicked.setSettingBtnClicked();
                        settingButtons.setForeground(Color.red);
                        dualGameButtons.setForeground(Color.black);
                    }
                    else if(btnClicked.isSettingBtnClicked()){
                        btnClicked.setScbBtnClicked();
                        scbButtons.setForeground(Color.red);
                        settingButtons.setForeground(Color.black);
                    }
                    else if(btnClicked.isScbBtnClicked()){
                        btnClicked.setExitBtnClicked();
                        exitButtons.setForeground(Color.red);
                        scbButtons.setForeground(Color.black);
                    }
                    else if(btnClicked.isExitBtnClicked()){
                        btnClicked.setGameBtnClicked();
                        gameButtons.setForeground(Color.red);
                        exitButtons.setForeground(Color.black);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if(btnClicked.isGameBtnClicked()){
                        //new GameUI();
                        new SelectGameTypeUI();
                        setVisible(false);
                    }
                    else if(btnClicked.isDualGameBtnClicked()){
                        userNumber.user=2;
                        new SelectDualGameTypeUI();
                        setVisible(false);
                    }
                    else if(btnClicked.isSettingBtnClicked()){
                        new SettingUI();
                        setVisible(false);
                    }
                    else if(btnClicked.isScbBtnClicked()){
                        new ScoreBoardUI();
                        setVisible(false);
                    }
                    else if(btnClicked.isExitBtnClicked()){
                        System.exit(0);
                    }
                }

            }
        });
        gameButtons.requestFocus();

        //다음으로 넘어가는 event(2p 모드 선택)
        dualGameButtons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userNumber.user=2;
                new SelectDualGameTypeUI();
                setVisible(false);
            }
        });



        //다음으로 넘어가는 event
        settingButtons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingUI();
                setVisible(false);
            }
        });




        //다음으로 넘어가는 event
        scbButtons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SelectScoreBoardUI();
                dispose();
            }
        });


        //다음으로 넘어가는 event
        exitButtons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

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





        //defaultBtn();
    }




    public void titleBtn(){
        JButton settingTitles = new JButton(titleImg1);
        //설정에서 이미지 크기를 변경하면 타이틀 이미지 크기도 조정됨
        if(screenSize.getWidth() == 800){
            settingTitles = new JButton(titleImg1);
        }
        else if(screenSize.getWidth() == 1024){
            settingTitles = new JButton(titleImg2);
        }
        else{
            settingTitles = new JButton(titleImg3);
        }
        settingTitles.setBorderPainted(false);
        settingTitles.setFocusPainted(false);
        settingTitles.setContentAreaFilled(false);
        settingTitles.setBounds(0,30,this.getWidth(),this.getHeight()/8);



        settingTitles.setPreferredSize(new Dimension((int)(screenSize.getWidth()*0.8), (int)(screenSize.getWidth()*0.17))); // 버튼 크기 지정
        mainPanel.add(settingTitles);



        settingTitles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new GameUI();
                new GameUI();
                setVisible(false);
            }
        });
    }







}
