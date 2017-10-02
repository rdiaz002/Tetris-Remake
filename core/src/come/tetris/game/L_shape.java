package come.tetris.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Ronny on 8/25/2017.
 */

public class L_shape extends Shape {

    public L_shape() {
        boolean[][] t_piece= {{true,false},{true,false},{true,true}};
        piece = new boolean[3][2];
        piece=t_piece;
        color = 0;
    }

    @Override
    public void rotate() {
        super.rotate();
    }
}
