package game;
//import setting.FixSize;
//import setting.MainSetting;
import game.manager.GameInfoManager;
import game.manager.gametype.GameManager_BasicMode;
import game.manager.gametype.GameManager_ItemMode;
import game.manager.gametype.GameManager_TimeAttackMode;
import setting.ColorBlind;
import setting.DuplicateKeySettingException;
import setting.SaveAndLoad;
import game.manager.GameManager;
import game.manager.DualModeUtils.Task;
import game.manager.DualModeUtils.UserNumber;
import game.manager.GameInfoManager.GameMode;
import setting.ScreenSize;
import setting.UI.SettingUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static setting.ColorBlind.ColorSetting.ColorBlinded;


public class GameUI extends JFrame {
    private JPanel mainPanel;
    public JTextPane[] pane = new JTextPane[2];
    public JTextPane[] attackPane = new JTextPane[2];
    public JTextPane[] scorePane = new JTextPane[2];
    public JTextPane[] nextBlockPane = new JTextPane[2];

    private ScreenSize screenSize=ScreenSize.getInstance();

    ColorBlind colorBlind = ColorBlind.getInstance();


    private static GameUI instance;
    public static GameUI getInstance() {
        if(instance != null)
            return instance;
        else
        {
            System.out.println("Error : GameUI Instance == null ");
            return null;
        }
    }

    public GameUI(){
        //JFrame setting
        super("software-tetris");//제목
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 메모리까지 종료

        //screenSize 조절
        if(UserNumber.getInstance().user==2) {
            screenSize.setWidth(1600);
            screenSize.setHeight(900);
        }

        //
        this.setSize(screenSize.getWidth(), screenSize.getHeight());
        this.setVisible(true);

        //board display setting
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.CYAN);

        
        //user 수만큼 화면 출력
        for (int i = 0; i < UserNumber.getInstance().user; i++) {
            setFrame(i);
            //게임 시작
            

            Task task = new Task();
            switch(GameInfoManager.getInstance().mode) {
                case BasicMode:
                    task.runBasicMode(i);
                    break;
                case ItemMode:
                    task.runItemMode(i);
                    break;
                case TimeAttackMode:
                    task.runTimeAttackMode(i);
                    break;
            }
            //task.runBasicMode(i);
        }

        if(GameInfoManager.getInstance().mode == GameMode.TimeAttackMode)
        {
            System.out.println("test");
            GameManager_TimeAttackMode.AdditionalTimer(62f);
        }

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

    public void setFrame(int user){
        JLabel label, scoreLabel;
        Font f1;
        Font f2;


        //보드 UI 부분
        pane[user] = new JTextPane();
        pane[user].setText("start!");
        pane[user].setEditable(false);
        f1 = new Font("monospaced", Font.BOLD,this.getHeight()/38);  //폰트 사이즈 화면크기에 맞게 조정
        f2 = new Font("monospaced", Font.BOLD,this.getHeight()/62);
        pane[user].setBounds(10+user*800,10,this.getWidth()/4,this.getHeight()*8/10);// 변화하는 화면 크기에 맞춰 사이즈 조정
        mainPanel.add(pane[user]);

        pane[user].setBackground(Color.BLACK);
        pane[user].setForeground(Color.GREEN);
        pane[user].setFont(f1);

        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                BorderFactory.createLineBorder(Color.YELLOW, 2)
        );

        CompoundBorder border2 = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.lightGray, 2),
                BorderFactory.createLineBorder(Color.gray, 2)
        );

        // Next Block 텍스트 출력
        label = new JLabel();
        label.setText("Next Block");
        label.setBackground(Color.BLACK);
        label.setFont(f1);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds((int) ((this.getWidth()/3.8)+(user*800)),0,this.getWidth()/4,30);
        mainPanel.add(label);

        // Next Block 미리보기 UI 부분
        nextBlockPane[user] = new JTextPane();
        nextBlockPane[user].setEditable(false);
        nextBlockPane[user].setBackground(Color.BLACK);
        nextBlockPane[user].setForeground(Color.WHITE);
        nextBlockPane[user].setBorder(border);
        nextBlockPane[user].setFont(f1);
        nextBlockPane[user].setBounds((int) ((this.getWidth()/3.5)+(user*800)),30,this.getWidth()/5,this.getWidth()/8);
        mainPanel.add(nextBlockPane[user]);

        // Score 텍스트 출력
        scoreLabel = new JLabel();
        scoreLabel.setText("Score");
        scoreLabel.setBackground(Color.BLACK);
        scoreLabel.setFont(f1);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        if(UserNumber.getInstance().user==2) {
            scoreLabel.setBounds((int) ((this.getWidth()/4)+(user*800)),180+this.getWidth()/4,this.getWidth()/4,30);
        }
        else{
            scoreLabel.setBounds((int) ((this.getWidth()/4)+(user*800)),30+this.getWidth()/4,this.getWidth()/4,30);
        }
        mainPanel.add(scoreLabel);

        // Score UI 부분
        scorePane[user] = new JTextPane();
        scorePane[user].setEditable(false);
        scorePane[user].setFont(f1);
        scorePane[user].setBackground(Color.BLACK);
        scorePane[user].setForeground(Color.WHITE);
        if(UserNumber.getInstance().user==2) {
            scorePane[user].setBounds((int) ((this.getWidth()/3.5)+(user*800)),210+this.getWidth()/4,this.getWidth()/5,this.getWidth()/8);
        }
        else{
            scorePane[user].setBounds((int) ((this.getWidth()/3.5)+(user*800)),60+this.getWidth()/4,this.getWidth()/5,this.getWidth()/8);
        }
        mainPanel.add(scorePane[user]);


        // 공격 UI 부분 (대전모드)
        if(UserNumber.getInstance().user==2) {
            attackPane[user] = new JTextPane();
            attackPane[user].setEditable(false);
            attackPane[user].setFont(f2);
            attackPane[user].setBackground(Color.BLACK);
            attackPane[user].setForeground(Color.GRAY);
            attackPane[user].setBorder(border2);
            attackPane[user].setBounds((int) ((this.getWidth()/3)+(user*800)),100+this.getWidth()/8,this.getWidth()*1/10,this.getWidth()*7/45);
            mainPanel.add(attackPane[user]);
        }


        //pause 버튼 --> 다른걸로 변경 예정
//        btn = new JButton("pause");
//        btn.setBounds(300,300,100,20);
//        mainPanel.add(btn);
//
//        btn.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e) {
//                    System.out.println("버튼이 클릭되었습니다.");
//            }
//        });

        // 글자 가운데 정렬 설정
        StyledDocument doc = pane[user].getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        StyledDocument nextDoc = nextBlockPane[user].getStyledDocument();
        SimpleAttributeSet nextCenter = new SimpleAttributeSet();
        StyleConstants.setAlignment(nextCenter, StyleConstants.ALIGN_CENTER);
        nextDoc.setParagraphAttributes(0, nextDoc.getLength(), nextCenter, false);

        StyledDocument scoreDoc = scorePane[user].getStyledDocument();
        SimpleAttributeSet scoreCenter = new SimpleAttributeSet();
        StyleConstants.setAlignment(scoreCenter, StyleConstants.ALIGN_CENTER);
        scoreDoc.setParagraphAttributes(0, scoreDoc.getLength(), center, false);


        if(UserNumber.getInstance().user==2){
            StyledDocument attackDoc = attackPane[user].getStyledDocument();
            SimpleAttributeSet attackCenter = new SimpleAttributeSet();
            StyleConstants.setAlignment(attackCenter, StyleConstants.ALIGN_CENTER);
            attackDoc.setParagraphAttributes(0, doc.getLength(), attackCenter, false);
            javax.swing.text.Style styles = attackPane[user].addStyle("L", null);
            StyleConstants.setForeground(styles, Color.LIGHT_GRAY);
            attackDoc.setParagraphAttributes(0, doc.getLength(), styles, false);

        }




        //글자 색 정보 저장
        addCharacterPaneStyle(pane[user]);
        addCharacternextBlockPaneStyle(nextBlockPane[user]);



        //setBtn();

        this.getContentPane().add(mainPanel);

        instance = this;


        //Screensize 값 가져오기
        ScreenSize.getInstance().getWidth();
        ScreenSize.getInstance().getHeight();
    }



    private void addCharacternextBlockPaneStyle(JTextPane nextBlockPane)
    {
        //색깔 종류
        javax.swing.text.Style style5 = nextBlockPane.addStyle("B", null);
        javax.swing.text.Style style6 = nextBlockPane.addStyle("P", null);
        javax.swing.text.Style style7 = nextBlockPane.addStyle("L", null);
        javax.swing.text.Style style8 = nextBlockPane.addStyle("A", null);
        javax.swing.text.Style style12 = nextBlockPane.addStyle("D", null);
        //블록에 대한 색깔들
        javax.swing.text.Style IBlock = nextBlockPane.addStyle("R", null);
        javax.swing.text.Style JBlock = nextBlockPane.addStyle("O", null);
        javax.swing.text.Style LBlock = nextBlockPane.addStyle("W", null);
        javax.swing.text.Style OBlock = nextBlockPane.addStyle("C", null);
        javax.swing.text.Style SBlock = nextBlockPane.addStyle("Y", null);
        javax.swing.text.Style TBlock = nextBlockPane.addStyle("G", null);
        javax.swing.text.Style ZBlock = nextBlockPane.addStyle("M", null);


        //색깔 설정
        StyleConstants.setForeground(IBlock, Color.RED);
        StyleConstants.setForeground(JBlock, Color.ORANGE);
        StyleConstants.setForeground(SBlock, Color.YELLOW);
        StyleConstants.setForeground(TBlock, Color.GREEN);
        StyleConstants.setForeground(LBlock, Color.WHITE);
        StyleConstants.setForeground(OBlock, Color.CYAN);
        StyleConstants.setForeground(ZBlock, Color.MAGENTA);
        StyleConstants.setForeground(style5, Color.GRAY);
        StyleConstants.setForeground(style6, Color.PINK);
        StyleConstants.setForeground(style7, Color.LIGHT_GRAY);
        StyleConstants.setForeground(style8, Color.GRAY);
        StyleConstants.setForeground(style12, Color.DARK_GRAY);


        if(colorBlind.getColorBlind()== ColorBlinded){
            StyleConstants.setForeground(ZBlock,  new Color(0X00FFFF));
            StyleConstants.setForeground(SBlock,new Color(0XD55E00));
            StyleConstants.setForeground(TBlock, new Color(0X0000FF));
            StyleConstants.setForeground(JBlock, new Color(0X009E73));
            StyleConstants.setForeground(LBlock, new Color(0XFFA500));
            StyleConstants.setForeground(OBlock,  new Color(0Xfec2de));
            StyleConstants.setForeground(IBlock, new Color(0XFFFF00));
        }
    }

    private void addCharacterPaneStyle(JTextPane pane)
    {
        //색깔 종류
        javax.swing.text.Style style5 = pane.addStyle("B", null);
        javax.swing.text.Style style6 = pane.addStyle("P", null);
        javax.swing.text.Style style7 = pane.addStyle("L", null);
        javax.swing.text.Style style8 = pane.addStyle("A", null);
        javax.swing.text.Style style12 = pane.addStyle("D", null);
        //블록에 대한 색깔들
        javax.swing.text.Style IBlock = pane.addStyle("R", null);
        javax.swing.text.Style JBlock = pane.addStyle("O", null);
        javax.swing.text.Style LBlock = pane.addStyle("W", null);
        javax.swing.text.Style OBlock = pane.addStyle("C", null);
        javax.swing.text.Style SBlock = pane.addStyle("Y", null);
        javax.swing.text.Style TBlock = pane.addStyle("G", null);
        javax.swing.text.Style ZBlock = pane.addStyle("M", null);


        //색깔 설정
        StyleConstants.setForeground(IBlock, Color.RED);
        StyleConstants.setForeground(JBlock, Color.ORANGE);
        StyleConstants.setForeground(SBlock, Color.YELLOW);
        StyleConstants.setForeground(TBlock, Color.GREEN);
        StyleConstants.setForeground(style5, Color.GRAY);
        StyleConstants.setForeground(style6, Color.PINK);
        StyleConstants.setForeground(style7, Color.LIGHT_GRAY);
        StyleConstants.setForeground(style8, Color.GRAY);
        StyleConstants.setForeground(LBlock, Color.WHITE);
        StyleConstants.setForeground(OBlock, Color.CYAN);
        StyleConstants.setForeground(ZBlock, Color.MAGENTA);
        StyleConstants.setForeground(style12, Color.DARK_GRAY);

        if(colorBlind.getColorBlind()== ColorBlinded){
            StyleConstants.setForeground(ZBlock,  new Color(0X00FFFF));
            StyleConstants.setForeground(SBlock,new Color(0XD55E00));
            StyleConstants.setForeground(TBlock, new Color(0X0000FF));
            StyleConstants.setForeground(JBlock, new Color(0X009E73));
            StyleConstants.setForeground(LBlock, new Color(0XFFA500));
            StyleConstants.setForeground(OBlock,  new Color(0Xfec2de));
            StyleConstants.setForeground(IBlock, new Color(0XFFFF00));
        }
    }

}

//        if(colorBlind.getColorBlind()== ColorBlinded){   // 원래 색
//            StyleConstants.setForeground(IBlock, new Color(255, 194, 10));
//            StyleConstants.setForeground(JBlock, new Color(12,123,220));
//            StyleConstants.setForeground(SBlock, new Color(153,79,0));
//            StyleConstants.setForeground(TBlock, new Color(254,254,98));
//            StyleConstants.setForeground(LBlock, new Color(211,95,183));
//            StyleConstants.setForeground(OBlock,  new Color(225,190,160));
//            StyleConstants.setForeground(ZBlock,  new Color(212,17,89));
//        }
