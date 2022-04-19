package scoreBoard;

import setting.SaveAndLoad;
import setting.ScreenSize;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ScoreInputUI  extends JFrame{
    private JPanel mainPanel;
    private ScreenSize screenSize = ScreenSize.getInstance();
    private JPanel inputScorePanel;
    private JTextField name = new JTextField(10);


    //게임 후 점수를 parameter로 받음
    public ScoreInputUI(int score){
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

        createInputScore(score);
        createCompleteBtn(score);
        //종료 시 현재 setting값 저장
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                SaveAndLoad.SaveSetting();
            }
        });





    }

    private void createCompleteBtn(int score) {
        JButton completeInput = new JButton("완료");
        inputScorePanel.add(completeInput);

        //사용자 점수 입력 받은 후 업데이트 스코어보드 화면
        completeInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User(name.getText(), score);
                new ScoreBoardAfterInputUI(user);
                setVisible(false);
            }
        });

    }

    public void createInputScore(int score) {
        inputScorePanel = new JPanel();
        inputScorePanel.setBackground(Color.BLACK);

        GridLayout gridLayout=new GridLayout( 4,1);
        inputScorePanel.setLayout(gridLayout);

        String scoreToString = Integer.toString(score);


        JLabel jLabel2 = new JLabel("등록점수:"+scoreToString);
        jLabel2.setForeground(Color.WHITE);
        inputScorePanel.add(jLabel2);

        JLabel jLabel3 = new JLabel("이름입력");
        jLabel3.setForeground(Color.WHITE);
        inputScorePanel.add(jLabel3);
        inputScorePanel.add(name);

        inputScorePanel.setBorder(BorderFactory.createEmptyBorder(screenSize.getHeight()/4,0,0,0));

        mainPanel.add(inputScorePanel);

    }
}
