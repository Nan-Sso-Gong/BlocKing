import scoreBoard.NoItemScoreBoard.ScoreInputUI;
import setting.SaveAndLoad;

import javax.swing.*;

public class Play {
    public static void main(String[] args) {

        //맥을 위한 코드
        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
        }
        catch(InstantiationException e) {
            System.out.println("InstantiationException: " + e.getMessage());
        }
        catch(IllegalAccessException e) {
            System.out.println("IllegalAccessException: " + e.getMessage());
        }
        catch(UnsupportedLookAndFeelException e) {
            System.out.println("UnsupportedLookAndFeelException: " + e.getMessage());
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //저장 값 불러오기
                SaveAndLoad.LoadSetting();
//                new StartUI();
                new ScoreInputUI(20,"easy");
            }
        });


    }
}
