package setting;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class KeySettingTest {
    KeySetting keySetting = KeySetting.getInstance();



    @Test
    @DisplayName("키 설정 기본값으로 설정")
    public void resetDefault() {
        //given
        keySetting.setLeft(32);
        //when

        //then
    }

    @Test
    public void setKeySetting() {
    }
}