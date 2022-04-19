package setting;

public class KeySetting {

    //싱글톤
    private static KeySetting keySetting = new KeySetting();

    public static KeySetting getInstance() {
        return keySetting;
    }

    private int left;

    private int right;

    private int turnBlock;

    private int downBlock;

    private int stop;

    private int oneTimeDown;

    private int escape;

    private int keyNum=7;

    public KeySetting() {
        this.left = 37;
        this.right = 39;
        this.turnBlock = 38;
        this.downBlock = 40;
        this.stop = 84;
        this.oneTimeDown = 32;
        this.escape=27;

    }

    public void resetDefault(){
        this.left = 37;
        this.right = 39;
        this.turnBlock = 38;
        this.downBlock = 40;
        this.stop = 84;
        this.oneTimeDown = 32;
        this.escape=27;
    }

    //key 중복값 있는지 확인
    public boolean overLapKeySetting(){
        boolean flag=false;

        int[] checkOverlap = new int[keyNum];

        checkOverlap[0] = this.left;
        checkOverlap[1]=this.right;
        checkOverlap[2] = this.turnBlock;
        checkOverlap[3] = this.downBlock;
        checkOverlap[4] = this.oneTimeDown;
        checkOverlap[5] = this.stop;
        checkOverlap[6] = this.escape;

        for (int i = 0; i < checkOverlap.length; i++) {
            for (int j = i + 1; j < checkOverlap.length - 1; j++) {
                if (checkOverlap[i] == checkOverlap[j]) {
                    flag=true;
                }
            }
            if (flag) {
                break;
            }
        }

        return flag;


    }

    public static KeySetting getKeySetting() {
        return keySetting;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTurnBlock() {
        return turnBlock;
    }

    public int getDownBlock() {
        return downBlock;
    }

    public int getStop() {
        return stop;
    }

    public int getOneTimeDown() {
        return oneTimeDown;
    }

    public void setKeySetting(int left, int right, int turnBlock, int downBlock, int stop, int oneTimeDown,int escape) {
        this.left = left;
        this.right = right;
        this.turnBlock = turnBlock;
        this.downBlock = downBlock;
        this.stop = stop;
        this.oneTimeDown = oneTimeDown;
        this.escape = escape;
    }


    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setTurnBlock(int turnBlock) {
        this.turnBlock = turnBlock;
    }

    public void setDownBlock(int downBlock) {
        this.downBlock = downBlock;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public void setOneTimeDown(int oneTimeDown) {
        this.oneTimeDown = oneTimeDown;
    }

    public int getEscape() {
        return escape;
    }

    public void setEscape(int escape) {
        this.escape = escape;
    }
}
