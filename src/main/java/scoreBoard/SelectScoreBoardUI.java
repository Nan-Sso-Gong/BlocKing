package scoreBoard;

import scoreBoard.NoItemScoreBoard.ScoreBoardUI;
import scoreBoard.NoItemScoreBoard.ScoreInputUI;
import scoreBoard.scoreBoradItem.ItemScoreBoardUI;
import setting.ColorBlind;
import setting.DuplicateKeySettingException;
import setting.SaveAndLoad;
import setting.ScreenSize;
import setting.UI.SettingUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SelectScoreBoardUI extends JFrame {
    private JPanel colorSettingPanel;
    ButtonGroup btnGroup = new ButtonGroup();
    private JRadioButton[] colorSettingBtns = new JRadioButton[2];
    private ScreenSize screenSize =ScreenSize.getInstance();
    private ColorBlind colorBlind = ColorBlind.getInstance();
    ImageIcon titleImg1 = new ImageIcon(getClass().getClassLoader().getResource("title1.png"));
    ImageIcon titleImg2 = new ImageIcon(getClass().getClassLoader().getResource("title2.png"));
    ImageIcon titleImg3 = new ImageIcon(getClass().getClassLoader().getResource("title3.png"));

    public SelectScoreBoardUI() {
        //JFrame setting
        super("software-tetris");//제목
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 메모리까지 종료
        this.setSize(screenSize.getWidth(),screenSize.getHeight());
        this.setVisible(true);

        //board display setting
        colorSettingPanel = new JPanel();
        colorSettingPanel.setBackground(Color.BLACK);

        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5)
        );
        colorSettingPanel.setBorder(border);

        this.getContentPane().add(colorSettingPanel,BorderLayout.CENTER);
        setTitle();
        selectSize();
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

        colorSettingPanel.add(titleBtn);
    }



    public void selectSize() {
        JPanel radioPanel = new JPanel();
        GridLayout gridLayout=new GridLayout( 2,1);
        radioPanel.setLayout(gridLayout);
        radioPanel.setBackground(Color.BLACK);
        radioPanel.setBorder(BorderFactory.createEmptyBorder(screenSize.getHeight()/4,0,0,0));

        colorSettingBtns[0] = new JRadioButton("스코어보드-일반MODE");
        colorSettingBtns[1] = new JRadioButton("스코어보드-ITEMMODE");

        for (int i = 0; i < 2; i++) {
            btnGroup.add(colorSettingBtns[i]);
        }

        for (int i = 0; i < 2; i++) {
            radioPanel.add(colorSettingBtns[i]);
        }

        colorSettingPanel.add(radioPanel, "Center");

        JPanel checkPanel = new JPanel();
        JButton btn = new JButton("완료");
        checkPanel.add(btn);
        checkPanel.setBorder((BorderFactory.createEmptyBorder(screenSize.getHeight() / 4, 0, 0, 0)));
        checkPanel.setBackground(Color.black);
        colorSettingPanel.add(checkPanel, "South");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (colorSettingBtns[0].isSelected()) {
                    new ScoreBoardUI();
                    dispose();

                } else if (colorSettingBtns[1].isSelected()) {
                    new ItemScoreBoardUI();
                    dispose();
                }

            }
        });


    }
}