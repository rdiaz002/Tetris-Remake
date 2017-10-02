package come.tetris.game;

import com.badlogic.gdx.graphics.Color;

import java.math.BigInteger;

/**
 * Created by Ronny on 8/25/2017.
 */

public class L_shape extends Shape {

    public L_shape() {
        piece = new Bit9("100100110",2,3);
        rotate = new Bit9("101011110",0,0);
        color = 0;
    }

    @Override
    public void rotate() {
        super.rotate();
    }
}
