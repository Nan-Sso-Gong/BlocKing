package setting;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FixSizeUI extends JFrame {
    private JPanel FixSizePanel;
    ButtonGroup sizeBtnGroup = new ButtonGroup();
    private JRadioButton[] radioButtons = new JRadioButton[3];
    private ScreenSize screenSize =ScreenSize.getInstance();
    ImageIcon titleImg1 = new ImageIcon("./src/main/java/start/img/title1.png");
    ImageIcon titleImg2 = new ImageIcon("./src/main/java/start/img/title2.png");
    ImageIcon titleImg3 = new ImageIcon("./src/main/java/start/img/title3.png");

    public FixSizeUI() {
        //JFrame setting
        super("software-tetris");//제목
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 메모리까지 종료
        this.setSize(screenSize.getWidth(),screenSize.getHeight());
        this.setVisible(true);

        //board display setting
        FixSizePanel = new JPanel();
        FixSizePanel.setBackground(Color.BLACK);

        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5)
        );
        FixSizePanel.setBorder(border);

        this.getContentPane().add(FixSizePanel,BorderLayout.CENTER);
        setTitle();
        selectSize();
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

        FixSizePanel.add(titleBtn);
    }

    public void selectSize() {
        JPanel radioPanel = new JPanel();
        GridLayout gridLayout=new GridLayout( 3,1);
        radioPanel.setLayout(gridLayout);
        radioPanel.setBackground(Color.BLACK);
        radioPanel.setBorder(BorderFactory.createEmptyBorder(screenSize.getHeight()/4,0,0,0));

        radioButtons[0] = new JRadioButton("400*500");
        radioButtons[1] = new JRadioButton("600*800");
        radioButtons[2] = new JRadioButton("800*1000");

        for (int i = 0; i < 3; i++) {
            sizeBtnGroup.add(radioButtons[i]);
        }

        for (int i = 0; i < 3; i++) {
            radioPanel.add(radioButtons[i]);
        }

        FixSizePanel.add(radioPanel, "Center");

        JPanel checkPanel = new JPanel();
        JButton btn = new JButton("완료");
        checkPanel.add(btn);
        checkPanel.setBorder((BorderFactory.createEmptyBorder(screenSize.getHeight() / 4, 0, 0, 0)));
        checkPanel.setBackground(Color.black);
        FixSizePanel.add(checkPanel, "South");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButtons[0].isSelected()) {
                    //400*500
                    screenSize.setWidth(400);
                    screenSize.setHeight(500);

                } else if (radioButtons[1].isSelected()) {
                    screenSize.setWidth(600);
                    screenSize.setHeight(800);

                }
                else{
                    screenSize.setWidth(800);
                    screenSize.setHeight(1000);

                }

                new SettingUI();
                setVisible(false);

            }
        });


    }
}
