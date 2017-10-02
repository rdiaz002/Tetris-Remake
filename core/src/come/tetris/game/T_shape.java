package come.tetris.game;

import com.badlogic.gdx.graphics.Color;

import java.math.BigInteger;


/**
 * Created by Ronny on 8/30/2017.
 */

public class T_shape extends Shape {
    public T_shape() {
        piece = new Bit9("111010000",3,2);
        rotate = new Bit9("011100100",0,0);
        color = 4;
    }
    public void rotate() {
        super.rotate();

    }
}
