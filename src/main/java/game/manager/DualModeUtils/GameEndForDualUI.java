package game.manager.DualModeUtils;



import setting.DuplicateKeySettingException;
import setting.SaveAndLoad;
import setting.ScreenSize;
import start.StartUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameEndForDualUI extends JFrame {
    private JPanel panel;
    private ScreenSize screenSize =ScreenSize.getInstance();
    private JButton backToStartBtn;
    private JPanel resultPanel;
    private int height = screenSize.getHeight();
    private int width = screenSize.getWidth();
    ImageIcon titleImg1 = new ImageIcon(getClass().getClassLoader().getResource("title1.png"));
    ImageIcon titleImg2 = new ImageIcon(getClass().getClassLoader().getResource("title2.png"));
    ImageIcon titleImg3 = new ImageIcon(getClass().getClassLoader().getResource("title3.png"));

    public GameEndForDualUI(int scoreA,int scoreB) {
        //JFrame setting
        super("software-tetris");//제목
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 메모리까지 종료
        this.setSize(screenSize.getWidth(),screenSize.getHeight());
        this.setVisible(true);

        //board display setting
        panel = new JPanel();
        panel.setBackground(Color.BLACK);


        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5)
        );
        panel.setBorder(border);

        this.getContentPane().add(panel,BorderLayout.CENTER);
        setResult(scoreA, scoreB);
        showBackToStartBtn();
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

        resultPanel.add(titleBtn);
    }

    public void setResult(int scoreA, int scoreB) {
        resultPanel = new JPanel();
        resultPanel.setBackground(Color.BLACK);
        GridLayout gridLayout = new GridLayout(5, 1, 1, height / 30);
        resultPanel.setLayout(gridLayout);
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 70, 10, 0));
        setTitle();
        JLabel resultLabel;
        JLabel scoreLabel;
        JLabel cautionLabel;
        if (scoreA > scoreB) {
            resultLabel = new JLabel("                                                       A WINNER!!");
            scoreLabel = new JLabel("                                                       A팀이" + (scoreA - scoreB) + "점수 차이로 승리하였습니다!!");
        } else if (scoreA == scoreB) {
            resultLabel = new JLabel("                                                        Draw");
            scoreLabel = new JLabel("                                                       A,B팀이" + scoreA + "점수로 비겼습니다.");
        }
        else{
            resultLabel = new JLabel("                                                       B WINNER!!");
            scoreLabel = new JLabel("                                                       B팀이" + (scoreB - scoreA) + "점수 차이로 승리하였습니다!!");
        }
        scoreLabel.setBounds(0, 0, width / 3,0);
        cautionLabel = new JLabel("                                                       DualMode는 스코어 보드에 기록되지 않습니다!");
        cautionLabel.setForeground(Color.WHITE);
        resultLabel.setForeground(Color.WHITE);
        scoreLabel.setForeground(Color.WHITE);
        resultPanel.add(resultLabel);
        resultPanel.add(scoreLabel);
        resultPanel.add(cautionLabel);

    }

    private void showBackToStartBtn(){
        backToStartBtn = new JButton("시작메뉴로");
        resultPanel.add(backToStartBtn);


        //시작메뉴로 가기(현재는 세팅으로 감)
        backToStartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartUI();
                setVisible(false);
            }
        });
        resultPanel.setBounds(width/4,height/3,width/10,height/10);
        panel.add(resultPanel);




    }






}
