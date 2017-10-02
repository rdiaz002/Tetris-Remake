package come.tetris.game;

import com.badlogic.gdx.graphics.Color;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Ronny on 8/1/2017.
 */

public class Shape {
    protected Bit9 piece;
    protected Bit9 rotate;
    protected int color;


    public Shape() {

        color = 0;
    }

    public void rotate(){
        piece = piece.xor(rotate);

//        boolean[][] temp_piece = new boolean[piece[0].length][piece.length];
//        for ( int i = 0 ; i< piece.length ; i++){
//            for ( int j = 0 ; j< piece[i].length ; j++){
//                temp_piece[temp_piece.length-1-j][i] = piece[i][j];
//            }
//        }
//        piece=temp_piece;

    }

}
