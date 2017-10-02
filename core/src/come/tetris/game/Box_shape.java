package come.tetris.game;

import com.badlogic.gdx.graphics.Color;

import java.math.BigInteger;


/**
 * Created by Ronny on 8/25/2017.
 */

public class Box_shape extends Shape {
    public Box_shape() {
        piece = new Bit9("000110110",2,2);
        rotate = new Bit9("000000000",0,0);
        color = 2;
    }
    public void rotate() {
        piece = piece.xor(rotate);
    }

}
