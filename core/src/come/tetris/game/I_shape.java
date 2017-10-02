package come.tetris.game;

import com.badlogic.gdx.graphics.Color;

import java.math.BigInteger;


/**
 * Created by Ronny on 8/25/2017.
 */

public class I_shape extends Shape {
    public I_shape() {
        piece = new Bit9("100100100",1,3);
        rotate = new Bit9("100011100",0,0);
        color = 1;
    }
    public void rotate() {
        piece=piece.xor(rotate);
    }

}
