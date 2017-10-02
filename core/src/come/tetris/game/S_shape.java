package come.tetris.game;

import com.badlogic.gdx.graphics.Color;

import java.math.BigInteger;

/**
 * Created by Ronny on 8/25/2017.
 */

public class S_shape extends Shape {
    public S_shape() {
        piece = new Bit9("000011110",3,2);
        rotate = new Bit9("100101100",0,0);
        color =5;
    }
    public void rotate() {

        piece = piece.xor(rotate);
    }
}
