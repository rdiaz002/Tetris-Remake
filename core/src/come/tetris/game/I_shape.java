package come.tetris.game;

import com.badlogic.gdx.graphics.Color;


/**
 * Created by Ronny on 8/25/2017.
 */

public class I_shape extends Shape {
    public I_shape() {
        boolean[][] t_piece= {{true},{true},{true}};
        piece = new boolean[3][1];
        piece=t_piece;
        color = 1;
    }
    public void rotate() {
        super.rotate();
    }

}
