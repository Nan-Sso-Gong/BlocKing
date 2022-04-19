package setting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import scoreBoard.ScoreList;
import scoreBoard.User;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class SaveAndLoad {
    private static ScreenSize screenSize = ScreenSize.getInstance();
    private static ScoreList scoreList = ScoreList.getInstance();
    private static KeySetting keySetting = KeySetting.getInstance();


    public static void SaveSetting(){

        //화면
        JSONObject screenSizeJson = new JSONObject();
        screenSizeJson.put("width", screenSize.getWidth());
        screenSizeJson.put("height", screenSize.getHeight());

        //keySetting 저장
        JSONObject keySettingJson = new JSONObject();
        keySettingJson.put("left", keySetting.getLeft());
        keySettingJson.put("right", keySetting.getRight());
        keySettingJson.put("downBlock", keySetting.getDownBlock());
        keySettingJson.put("turnBlock", keySetting.getTurnBlock());
        keySettingJson.put("oneTimeDown", keySetting.getOneTimeDown());
        keySettingJson.put("stop", keySetting.getStop());

        //스코어보드
        List<User> list = scoreList.getList();
        JSONObject scoreBoardJson = new JSONObject();
        int size = list.size();
        //10개 까지만 저장
        if(size>=10){
            size=10;
        }
        for (int i=0;i<size;i++){
            scoreBoardJson.put("name"+i, list.get(i).getName());
            scoreBoardJson.put("score"+i, list.get(i).getScore());
        }

        try{
            //스크린 사이즈 저장
            FileWriter screenSizeFile = new FileWriter("src/main/java/save/ScreenSize.json");
            screenSizeFile.write(screenSizeJson.toJSONString());
            screenSizeFile.flush();
            screenSizeFile.close();

            //keySetting json 파일에 저장
            FileWriter keySettingFile = new FileWriter("src/main/java/save/KeySetting.json");
            keySettingFile.write(keySettingJson.toJSONString());
            keySettingFile.flush();
            keySettingFile.close();

            //스코어리스트 10개까지 저장
            FileWriter scoreListFile = new FileWriter("src/main/java/save/ScoreList.json");
            scoreListFile.write(scoreBoardJson.toJSONString());
            scoreListFile.flush();
            scoreListFile.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void LoadSetting() {
        JSONParser parser = new JSONParser();

        try{
            //화면 크기 load
//            FileReader readerToScreenSize = new FileReader("src/main/java/save/ScreenSize.json");
//            Object screenSizeJson1 = parser.parse(readerToScreenSize);
//            JSONObject screenSizeJson2 =(JSONObject) screenSizeJson1;
//            readerToScreenSize.close();
            InputStream getScreenSize = new FileInputStream("src/main/java/save/ScreenSize.json");
            HashMap<String,Object> screenSizeMap = new ObjectMapper().readValue(getScreenSize, HashMap.class);
            screenSize.setWidth((Integer) screenSizeMap.get("width"));
            screenSize.setHeight((Integer) screenSizeMap.get("height"));

            //스코어 보드 load
//            FileReader readerToScoreList = new FileReader("src/main/java/save/ScoreList.json");
//            Object scoreListJson1 = parser.parse(readerToScoreList);
//            JSONObject scoreListJson2 =(JSONObject) scoreListJson1;
//            readerToScoreList.close();
            InputStream getScoreBoard = new FileInputStream("src/main/java/save/ScoreList.json");
            HashMap<String,Object> scoreBoardMap = new ObjectMapper().readValue(getScoreBoard, HashMap.class);
            System.out.println("scoreBoardMap = " + scoreBoardMap.size());
            int size = scoreBoardMap.size()/2;
            if (size >= 10) {
                size=10;
            }
            for (int i = 0; i < size; i++) {
                scoreList.push(new User((String) scoreBoardMap.get("name" + i), (Integer) scoreBoardMap.get("score" + i)));
            }
            scoreList.sortDescByScore();

            //keySetting Load
            InputStream getKeySetting = new FileInputStream("src/main/java/save/KeySetting.json");
            HashMap<String,Object> getKeySettingMap = new ObjectMapper().readValue(getKeySetting, HashMap.class);
            int left = (Integer)getKeySettingMap.get("left");
            int right = (Integer)getKeySettingMap.get("right");
            int downBlock = (Integer)getKeySettingMap.get("downBlock");
            int turnBlock = (Integer)getKeySettingMap.get("turnBlock");
            int stop = (Integer)getKeySettingMap.get("stop");
            int oneTimeDown = (Integer)getKeySettingMap.get("oneTimeDown");
            keySetting.setKeySetting(left,right,turnBlock,downBlock,stop,oneTimeDown);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
