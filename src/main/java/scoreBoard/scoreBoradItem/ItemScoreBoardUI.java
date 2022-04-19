package scoreBoard.scoreBoradItem;

import scoreBoard.ScoreList;
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

public class ItemScoreBoardUI extends JFrame{

    private JPanel mainPanel;
    private ScreenSize screenSize = ScreenSize.getInstance();
    private ScoreList scoreList = ScoreList.getInstance();
    //각 label 및 텍스트 위치 설정 (배경 크기 기준)
    private int height = screenSize.getHeight();
    private int width = screenSize.getWidth();

    private JPanel scorePanel;
    private JButton inputBtn;
    private JButton exitBtn;
    private JButton backToStartBtn;


    //시작화면에서의 scoreBoardUI 점수 입력값 없음
    public ItemScoreBoardUI(){
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

        showScoreListForStartMenu();
        showBackToStartBtn();

        //종료 시 현재 setting값 저장
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                SaveAndLoad.SaveSetting();
            }
        });

    }


//    //게임이 끝난 후 사용자에게 점수입력을 받기위해 생성자 parameter로 설정
//    public ScoreBoardUI(int score){
//        //JFrame setting
//        super("software-tetris");//제목
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 메모리까지 종료
//        this.setSize(screenSize.getWidth(),screenSize.getHeight());
//        this.setVisible(true);
//
//        //board display setting
//        mainPanel = new JPanel();
//        mainPanel.setBackground(Color.BLACK);
//
//        CompoundBorder border = BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(Color.GRAY, 10),
//                BorderFactory.createLineBorder(Color.DARK_GRAY, 5)
//        );
//        mainPanel.setBorder(border);
//        this.getContentPane().add(mainPanel,BorderLayout.CENTER);
//
//        showScoreList();
//        showInputBtn(score);
//
//    }



//    private void showInputBtn(int score) {
//         inputBtn = new JButton("점수 입력");
//         exitBtn = new JButton("시작 화면");
//
//        scorePanel.add(inputBtn);
//        scorePanel.add(new JLabel(" "));
//        scorePanel.add(exitBtn);
//        scorePanel.add(new JLabel(" "));
//
//        //사용자의 이름 및 점수 입력 받음
//        inputBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new ScoreInputUI(score,mode);
//                setVisible(false);
//            }
//        });
//
//
//        //사용자 입력을 받지 않고 시작화면으로 이동
//        //사용자의 이름 및 점수 입력 받음
//        exitBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new StartUI();
//                setVisible(false);
//            }
//        });
//
//    }

    private void showBackToStartBtn(){
        backToStartBtn = new JButton("뒤로가기");
        scorePanel.add(backToStartBtn);


        //시작메뉴로 가기(현재는 세팅으로 감)
        backToStartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartUI();
                setVisible(false);
            }
        });



    }

    private void showScoreListForStartMenu(){
        //스코어 리스트 최대 10개까지 설정
        int listToIndex;
        if (scoreList.getList().size() >= 10) {
            listToIndex=10;
        }
        else{
            listToIndex = scoreList.getList().size();
        }


        scorePanel = new JPanel();
        scorePanel.setBackground(Color.BLACK);
        GridLayout gridLayout = new GridLayout(listToIndex+3, 3,1,height/30);
        scorePanel.setLayout(gridLayout);
        scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 70, 10, 0));


        JLabel titleLabel = new JLabel(" 스코어 리스트-ItemMode");
        titleLabel.setForeground(Color.WHITE);
        scorePanel.add(titleLabel);
        scorePanel.add(new JLabel(" "));
        scorePanel.add(new JLabel(" "));

        JLabel nameLabel = new JLabel("이름");
        nameLabel.setForeground(Color.WHITE);
        scorePanel.add(nameLabel);
        JLabel scoreLabel = new JLabel("점수");
        scoreLabel.setForeground(Color.WHITE);
        scorePanel.add(scoreLabel);

        JLabel modeLabel = new JLabel("MODE");
        modeLabel.setForeground(Color.WHITE);
        scorePanel.add(modeLabel);



        for (int i = 0; i < listToIndex; i++) {

            JLabel userNameLabel = new JLabel(scoreList.getList().get(i).getName());
            userNameLabel.setForeground(Color.WHITE);
            scorePanel.add(userNameLabel);

            JLabel userScoreLabel = new JLabel(Integer.toString(scoreList.getList().get(i).getScore()));
            userScoreLabel.setForeground(Color.WHITE);
            scorePanel.add(userScoreLabel);

            JLabel userModeLabel = new JLabel(scoreList.getList().get(i).getMode());
            userModeLabel.setForeground(Color.WHITE);
            scorePanel.add(userModeLabel);
        }

        mainPanel.add(scorePanel,CENTER_ALIGNMENT);
    }



//    private void showScoreList() {
//        //스코어 리스트 최대 10개까지 설정
//        int listToIndex;
//        if (scoreList.getList().size() >= 10) {
//            listToIndex=10;
//        }
//        else{
//            listToIndex = scoreList.getList().size();
//        }
//
//
//        scorePanel = new JPanel();
//        scorePanel.setBackground(Color.BLACK);
//        GridLayout gridLayout = new GridLayout(listToIndex+4, 3,1,height/30);
//        scorePanel.setLayout(gridLayout);
//        scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 70, 10, 0));
//
//
//        JLabel titleLabel = new JLabel("   스코어 리스트");
//        titleLabel.setForeground(Color.WHITE);
//        scorePanel.add(titleLabel);
//        scorePanel.add(new JLabel(" "));
//
//        JLabel nameLabel = new JLabel("이름");
//        nameLabel.setForeground(Color.WHITE);
//        scorePanel.add(nameLabel);
//        JLabel scoreLabel = new JLabel("점수");
//        scoreLabel.setForeground(Color.WHITE);
//        scorePanel.add(scoreLabel);
//        JLabel modeLabel = new JLabel("MODE");
//        modeLabel.setForeground(Color.WHITE);
//        scorePanel.add(modeLabel);
//
//
//
//        for (int i = 0; i < listToIndex; i++) {
//
//            JLabel userNameLabel = new JLabel(scoreList.getList().get(i).getName());
//            userNameLabel.setForeground(Color.WHITE);
//            scorePanel.add(userNameLabel);
//
//            JLabel userScoreLabel = new JLabel(Integer.toString(scoreList.getList().get(i).getScore()));
//            userScoreLabel.setForeground(Color.WHITE);
//            scorePanel.add(userScoreLabel);
//
//            JLabel userModeLabel = new JLabel(scoreList.getList().get(i).getMode());
//            userModeLabel.setForeground(Color.WHITE);
//            scorePanel.add(userModeLabel);
//        }
//
//        mainPanel.add(scorePanel,CENTER_ALIGNMENT);
//    }

}
