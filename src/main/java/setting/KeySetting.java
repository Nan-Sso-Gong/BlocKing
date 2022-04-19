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

    public KeySetting() {
        this.left = 37;
        this.right = 39;
        this.turnBlock = 38;
        this.downBlock = 40;
        this.stop = 84;
        this.oneTimeDown = 32;
    }

    public void resetDefault(){
        this.left = 37;
        this.right = 39;
        this.turnBlock = 38;
        this.downBlock = 40;
        this.stop = 84;
        this.oneTimeDown = 32;
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

    public void setKeySetting(int left, int right, int turnBlock, int downBlock, int stop, int oneTimeDown) {
        this.left = left;
        this.right = right;
        this.turnBlock = turnBlock;
        this.downBlock = downBlock;
        this.stop = stop;
        this.oneTimeDown = oneTimeDown;
    }








}
