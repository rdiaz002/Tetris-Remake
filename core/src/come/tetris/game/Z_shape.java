package come.tetris.game;

import com.badlogic.gdx.graphics.Color;

import java.math.BigInteger;

/**
 * Created by Ronny on 8/25/2017.
 */

public class Z_shape extends Shape {
    public Z_shape() {

        piece = new Bit9("000110011",3,2);
        rotate = new Bit9("010000111",0,0);
        color = 3;

    }
    public void rotate() {
        piece = piece.xor(rotate);

    }

}
