package come.tetris.game;

import com.badlogic.gdx.graphics.Color;


/**
 * Created by Ronny on 8/25/2017.
 */

public class Box_shape extends Shape {
    public Box_shape() {
        boolean[][] t_piece= {{true,true},{true,true}};
        piece = new boolean[2][2];
        piece=t_piece;
        color = 2;
    }
    public void rotate() {
        super.rotate();
    }

}
