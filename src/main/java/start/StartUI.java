package start;

import game.GameUI;
import scoreBoard.NoItemScoreBoard.ScoreBoardUI;
import setting.ScreenSize;
import setting.SettingUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;

import static start.IsButtonClicked.btnClicked;

public class StartUI extends JFrame {

    public  JPanel mainPanel;
    private ScreenSize screenSize = ScreenSize.getInstance();
    private KeyListener startInteraction;
    ImageIcon titleImg1 = new ImageIcon("./src/main/java/start/img/title1.png");
    ImageIcon titleImg2 = new ImageIcon("./src/main/java/start/img/title2.png");
    ImageIcon titleImg3 = new ImageIcon("./src/main/java/start/img/title3.png");



    public StartUI(){
        //JFrame setting
        super("software-tetris");//제목
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 메모리까지 종료
        this.setSize(screenSize.getWidth(), screenSize.getHeight());
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


        titleBtn();

        JButton gameButtons = new JButton("게임 시작");
        mainPanel.add(gameButtons);

        JButton settingButtons = new JButton("설정 메뉴");
        mainPanel.add(settingButtons);

        JButton scbButtons = new JButton("스코어보드");
        mainPanel.add(scbButtons);

        JButton exitButtons = new JButton("게임 종료");
        mainPanel.add(exitButtons);


        btnClicked.setGameBtnClicked();
        if(btnClicked.isGameBtnClicked()){
            gameButtons.setForeground(Color.red);
        }

        //다음으로 넘어가는 event
        gameButtons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameUI();
                setVisible(false);
            }
        });

        gameButtons.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if(btnClicked.isGameBtnClicked()){
                        btnClicked.setExitBtnClicked();
                        exitButtons.setForeground(Color.red);
                        gameButtons.setForeground(Color.black);
                    }
                    else if(btnClicked.isSettingBtnClicked()){
                        btnClicked.setGameBtnClicked();
                        gameButtons.setForeground(Color.red);
                        settingButtons.setForeground(Color.black);
                    }
                    else if(btnClicked.isScbBtnClicked()){
                        btnClicked.setSettingBtnClicked();
                        settingButtons.setForeground(Color.red);
                        scbButtons.setForeground(Color.black);

                    }
                    else if(btnClicked.isExitBtnClicked()){
                        btnClicked.setScbBtnClicked();
                        scbButtons.setForeground(Color.red);
                        exitButtons.setForeground(Color.black);
                    }

                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if(btnClicked.isGameBtnClicked()){
                        btnClicked.setSettingBtnClicked();
                        settingButtons.setForeground(Color.red);
                        gameButtons.setForeground(Color.black);

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
                    if(btnClicked.isGameBtnClicked()){
                        btnClicked.setExitBtnClicked();
                        exitButtons.setForeground(Color.red);
                        gameButtons.setForeground(Color.black);
                    }
                    else if(btnClicked.isSettingBtnClicked()){
                        btnClicked.setGameBtnClicked();
                        gameButtons.setForeground(Color.red);
                        settingButtons.setForeground(Color.black);
                    }
                    else if(btnClicked.isScbBtnClicked()){
                        btnClicked.setSettingBtnClicked();
                        settingButtons.setForeground(Color.red);
                        scbButtons.setForeground(Color.black);

                    }
                    else if(btnClicked.isExitBtnClicked()){
                        btnClicked.setScbBtnClicked();
                        scbButtons.setForeground(Color.red);
                        exitButtons.setForeground(Color.black);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if(btnClicked.isGameBtnClicked()){
                        btnClicked.setSettingBtnClicked();
                        settingButtons.setForeground(Color.red);
                        gameButtons.setForeground(Color.black);

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
                        new GameUI();
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
                new ScoreBoardUI();
                setVisible(false);
            }
        });


        //다음으로 넘어가는 event
        exitButtons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });







        //defaultBtn();
    }




    public void titleBtn(){
        JButton settingTitles = new JButton(titleImg1);
        //설정에서 이미지 크기를 변경하면 타이틀 이미지 크기도 조정됨
        if(screenSize.getWidth() == 400){
            settingTitles = new JButton(titleImg1);
        }
        else if(screenSize.getWidth() == 600){
            settingTitles = new JButton(titleImg2);
        }
        else{
            settingTitles = new JButton(titleImg3);
        }
        settingTitles.setBorderPainted(false);
        settingTitles.setFocusPainted(false);
        settingTitles.setContentAreaFilled(false);

        settingTitles.setPreferredSize(new Dimension((int)(screenSize.getWidth()*0.8), (int)(screenSize.getWidth()*0.17))); // 버튼 크기 지정
        mainPanel.add(settingTitles);



        settingTitles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameUI();
                setVisible(false);
            }
        });
    }

    public void gameBtn(){

    }

    public void settingBtn(){

    }

    public void scbBtn(){//스코어보드 버튼

    }

    public void exitBtn(){

    }



    public void defaultBtn(){
        JButton defaultButtons = new JButton("기본값 버튼입니다");
        mainPanel.add(defaultButtons);

        boolean isClicked = false;
        if(isClicked == true){
            defaultButtons.setForeground(Color.red);
        }

        //다음으로 넘어가는 event
        defaultButtons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("버튼의 기본값입니다.");
            }
        });
    }

}
