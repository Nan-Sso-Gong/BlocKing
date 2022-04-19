package setting;

public class ScreenSize {
    /*
    *default값은 400*500
    *
     */

    private  int width;

    private  int height;

    private static ScreenSize screenSize = new ScreenSize();

    private ScreenSize() {
        this.width = 400;
        this.height=500;
    }

    public static ScreenSize getInstance() {
        return screenSize;
    }

    public  int getWidth() {
        return width;
    }

    public  void setWidth(int width) {
        this.width = width;
    }

    public  int getHeight() {
        return height;
    }

    public  void setHeight(int height) {
        this.height = height;
    }
}
