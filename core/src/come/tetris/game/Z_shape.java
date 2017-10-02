package come.tetris.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Ronny on 8/25/2017.
 */

public class Z_shape extends Shape {
    public Z_shape() {

        boolean[][] t_piece= {{true,true,false},{false,true,true}};
        piece = new boolean[2][3];
        piece=t_piece;
        color = 3;

    }
    public void rotate() {
        super.rotate();
    }

}
