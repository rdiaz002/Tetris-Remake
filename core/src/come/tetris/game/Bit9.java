package come.tetris.game;

/**
 * Created by Ronny on 9/26/2017.
 */

public class Bit9 {
    public Boolean[] data = new Boolean[9];

    public int width;
    public int height;
    public Bit9(String val, int w, int h) {
        for (int i = 0; i < val.length(); i++) {
            if (val.charAt(i) == '1') {
                data[i] = true;
            } else {
                data[i] = false;
            }
        }
        width = w;
        height = h;
    }

    public Bit9(int w, int h) {
        for (int i = 0; i < data.length; i++) {
            data[i] = false;
        }
    }

    public Bit9 xor(Bit9 val) {
        Bit9 temp = new Bit9(height,width);
        for (int i = 0; i < data.length; i++) {

            temp.data[i] = (data[i] ^ val.data[i]);

        }
        temp.setHeight(width);
        temp.setWidth(height);
        return temp;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < data.length; i++) {
            if (data[i]) {
                temp += "1";
            } else {
                temp += "0";
            }
        }
        return temp;
    }
}
