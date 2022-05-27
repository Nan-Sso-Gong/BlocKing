package setting.UI;

import setting.DuplicateKeySettingException;
import setting.KeySetting;
import setting.SaveAndLoad;
import setting.ScreenSize;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;

public class KeySettingUI extends JFrame{
    private JPanel mainPanel;
    private ScreenSize screenSize = ScreenSize.getInstance();
    private KeySetting keySetting = KeySetting.getInstance();
    private JTextField[] keyTextField = new JTextField[12];
    private int[] keyCode = new int[12];
    private JLabel[] keyName = new JLabel[12];
    private JButton[] buttons = new JButton[2];;
    private JPanel radioPanel;
    ImageIcon titleImg1 = new ImageIcon(getClass().getClassLoader().getResource("title1.png"));
    ImageIcon titleImg2 = new ImageIcon(getClass().getClassLoader().getResource("title2.png"));
    ImageIcon titleImg3 = new ImageIcon(getClass().getClassLoader().getResource("title3.png"));
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
        eventListenerBtn();

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

    public void eventListenerBtn() {
        //확인 버튼 클릭시 세팅 값 저장
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int left = keyCode[0];
                int right = keyCode[1];
                int downBlock = keyCode[2];
                int turnBlock = keyCode[3];
                int onetimeBlock = keyCode[4];
                int left2P = keyCode[5];
                int right2P = keyCode[6];
                int downBlock2P = keyCode[7];
                int turnBlock2P = keyCode[8];
                int onetimeBlock2P = keyCode[9];
                int stop = keyCode[10];
                int escape = keyCode[11];

                for (int i = 0; i < keyCode.length; i++) {
                    if (keyCode[i] == 0) {
                        switch (i){
                            case 0:
                                keyCode[0] = keySetting.getLeft();
                                left = keyCode[0];
                                break;
                            case 1:
                                keyCode[1] = keySetting.getRight();
                                right=keyCode[1];
                                break;
                            case 2:
                                keyCode[2] = keySetting.getDownBlock();
                                downBlock=keyCode[2];
                                break;
                            case 3:
                                keyCode[3] = keySetting.getTurnBlock();
                                turnBlock=keyCode[3];
                                break;
                            case 4:
                                keyCode[4] = keySetting.getOneTimeDown();
                                onetimeBlock=keyCode[4];
                                break;
                            case 5:
                                keyCode[5] = keySetting.getLeft2P();
                                left2P = keyCode[5];
                                break;
                            case 6:
                                keyCode[6] = keySetting.getRight2P();
                                right2P = keyCode[6];
                                break;
                            case 7:
                                keyCode[7] = keySetting.getDownBlock2P();
                                downBlock2P = keyCode[7];
                                break;
                            case 8:
                                keyCode[8] = keySetting.getOneTimeDown2P();
                                onetimeBlock2P = keyCode[8];
                                break;
                            case 9:
                                keyCode[9] = keySetting.getTurnBlock2P();
                                turnBlock2P = keyCode[9];
                                break;
                            case 10:
                                keyCode[10] = keySetting.getStop();
                                stop = keyCode[10];
                                break;
                            case 11:
                                keyCode[11] = keySetting.getEscape();
                                escape = keyCode[11];
                                break;

                        }

                    }
                }

                keySetting.setKeySetting(left, right, turnBlock, downBlock, stop, onetimeBlock, escape,left2P,right2P,turnBlock2P,downBlock2P,onetimeBlock2P);
                if (keySetting.overLapKeySetting()) {
                     JOptionPane.showConfirmDialog(null, "중복된 key값이 있습니다.","오류",JOptionPane.WARNING_MESSAGE);
                     return;
                }
                new SettingUI();
                setVisible(false);
            }
        });

        //뒤로가기 버튼 시 저장하지 않고 settingUI로 이동
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingUI();
                setVisible(false);
            }
        });
    }

    public void checkBtn() {
        buttons[0]= new JButton("확인");
        buttons[1]= new JButton("뒤로가기");
        radioPanel.add(buttons[0]);
        radioPanel.add(buttons[1]);
    }



    public void selectKeySet() {
        radioPanel = new JPanel();
        GridLayout gridLayout=new GridLayout( 14,2);
        radioPanel.setLayout(gridLayout);
        radioPanel.setBackground(Color.BLACK);
        radioPanel.setBorder(BorderFactory.createEmptyBorder(screenSize.getHeight() / 4, 0, 0, 0));

        keyName[0]=new JLabel("왼쪽 이동");
        String left = KeyEvent.getKeyText(keySetting.getLeft());
        keyTextField[0]=new JTextField(left);

        keyName[1]=new JLabel("오른쪽 이동");
        String right = KeyEvent.getKeyText(keySetting.getRight());
        keyTextField[1] = new JTextField(right);

        keyName[2] = new JLabel("내리기");
        String downBlock = KeyEvent.getKeyText(keySetting.getDownBlock());
        keyTextField[2]=new JTextField(downBlock);

        keyName[3] = new JLabel("회전시키기");
        String turnBlock = KeyEvent.getKeyText(keySetting.getTurnBlock());
        keyTextField[3] = new JTextField(turnBlock);

        keyName[4]=new JLabel("한번에 내리기");
        String oneTimeDown = KeyEvent.getKeyText(keySetting.getOneTimeDown());
        keyTextField[4] = new JTextField(oneTimeDown);

        keyName[5]=new JLabel("왼쪽 이동(2P)");
        String left2P = KeyEvent.getKeyText(keySetting.getLeft2P());
        keyTextField[5]=new JTextField(left2P);

        keyName[6]=new JLabel("오른쪽 이동(2P)");
        String right2P = KeyEvent.getKeyText(keySetting.getRight2P());
        keyTextField[6] = new JTextField(right2P);

        keyName[7] = new JLabel("내리기(2P)");
        String downBlock2P = KeyEvent.getKeyText(keySetting.getDownBlock2P());
        keyTextField[7]=new JTextField(downBlock2P);

        keyName[8] = new JLabel("회전시키기(2P)");
        String turnBlock2P = KeyEvent.getKeyText(keySetting.getTurnBlock2P());
        keyTextField[8] = new JTextField(turnBlock2P);

        keyName[9]=new JLabel("한번에 내리기(2P)");
        String oneTimeDown2P = KeyEvent.getKeyText(keySetting.getOneTimeDown2P());
        keyTextField[9] = new JTextField(oneTimeDown2P);

        keyName[10] = new JLabel("일시정지");
        String stop = KeyEvent.getKeyText(keySetting.getStop());
        keyTextField[10] = new JTextField(stop);

        keyName[11] = new JLabel("게임 중 시작메뉴로");
        String escape = KeyEvent.getKeyText(keySetting.getEscape());
        keyTextField[11] = new JTextField(escape);

        for (int i = 0; i < keyTextField.length; i++) {
            keyName[i].setForeground(Color.white);

            radioPanel.add(keyName[i]);
            radioPanel.add(keyTextField[i]);
        }
        mainPanel.add(radioPanel);
    }

    public void eventListenerKeyField() {

        for (int i = 0; i < keyName.length; i++) {
            int finalI = i;
            keyTextField[i].addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) { // 키가 눌렷을때의 이벤트
                    int keyCode = e.getKeyCode();
                    String s = e.getKeyText(keyCode); // 키값
                    String upperCase = s.toUpperCase();
                    KeySettingUI.this.keyCode[finalI]=keyCode;
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
