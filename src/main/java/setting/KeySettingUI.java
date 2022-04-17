package setting;

import scoreBoard.ScoreList;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class KeySettingUI extends JFrame{
    private JPanel mainPanel;
    private ScreenSize screenSize = ScreenSize.getInstance();

    private JTextField[] keyTextField = new JTextField[6];
    private JLabel[] keyName = new JLabel[6];

    private JButton checkBtn = new JButton("확인");
    private ButtonGroup btnGroup = new ButtonGroup();
    private JPanel radioPanel;
    ImageIcon titleImg1 = new ImageIcon("./src/main/java/start/img/title1.png");
    ImageIcon titleImg2 = new ImageIcon("./src/main/java/start/img/title2.png");
    ImageIcon titleImg3 = new ImageIcon("./src/main/java/start/img/title3.png");

    public KeySettingUI(){
        //JFrame setting
        super("software-tetris");//제목
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
        selectKeySet();
        eventListenerKeyField();
        checkBtn();

        //종료 시 현재 setting 및 scoreBoard 저장
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                SaveAndLoad.SaveSetting();
            }
        });
    }

    private void checkBtn() {
        radioPanel.add(checkBtn);
    }


    //A와 B중 구현
    private void selectKeySet() {
        radioPanel = new JPanel();
        GridLayout gridLayout=new GridLayout( 7,2);
        radioPanel.setLayout(gridLayout);
        radioPanel.setBackground(Color.BLACK);
        radioPanel.setBorder(BorderFactory.createEmptyBorder(screenSize.getHeight() / 4, 0, 0, 0));

        keyName[0]=new JLabel("왼쪽 이동");

        keyTextField[0]=new JTextField("LEFT");

        keyName[1]=new JLabel("오른쪽 이동");
        keyTextField[1] = new JTextField("RIGHT");

        keyName[2] = new JLabel("내리기");
        keyTextField[2]=new JTextField("DOWN");

        keyName[3] = new JLabel("회전시키기");
        keyTextField[3] = new JTextField("UP");

        keyName[4]=new JLabel("한번에 내리기");
        keyTextField[4] = new JTextField("SPACE");

        keyName[5] = new JLabel("아이템 사용");
        keyTextField[5] = new JTextField("R");

        for (int i = 0; i < 6; i++) {
            keyName[i].setForeground(Color.white);

            radioPanel.add(keyName[i]);
            radioPanel.add(keyTextField[i]);
        }
        mainPanel.add(radioPanel);
    }

    private void eventListenerKeyField() {

        for (int i = 0; i < keyName.length; i++) {

            int finalI = i;
            keyTextField[i].addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) { // 키가 눌렷을때의 이벤트
                    String s = e.getKeyText(e.getKeyCode()); // 키값
                    String upperCase = s.toUpperCase();
                    if(s.length()==1) {
                        keyTextField[finalI].setText("");
                        keyTextField[finalI].setText(upperCase);
                    }
                    else{
                        keyTextField[finalI].setText("");
                        keyTextField[finalI].setText(upperCase);
                    }
                }
            });
        }
    }

    private void setTitle() {
        JButton titleBtn;
        if(screenSize.getWidth() == 400){
            titleBtn = new JButton(titleImg1);
        }
        else if(screenSize.getWidth() == 600){
            titleBtn = new JButton(titleImg2);
        }
        else{
            titleBtn = new JButton(titleImg3);
        }
        titleBtn.setBackground(Color.BLACK);

        mainPanel.add(titleBtn);
    }

}
