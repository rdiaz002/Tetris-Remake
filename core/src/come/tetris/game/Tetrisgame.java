package come.tetris.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;


import java.util.Random;

import com.badlogic.gdx.utils.Logger;

public class Tetrisgame extends ApplicationAdapter implements GestureDetector.GestureListener {

    private static Grid_cell[][] gameGrid;
    private boolean PIM = false;
    private boolean fr = true;

    private int DELAY = 50;
    private int C_Delay = 0;

    private float piece_x = 0;
    private float piece_y = 0;
    private float bracket_Height;
    private float screen_Width;
    private float screen_Height;
    private float comm_Width;
    private float grid_Width;
    private float grid_block_ratio_width;
    private float grid_block_ratio_height;
    private static float def_x;

    private Shape c_piece = new Shape();
    private Shape n_piece = new Shape();
    private String temp_n;
    private String temp_b;
    private SpriteBatch batch;
    private Texture[] blocks;
    private Texture background;
    private Texture grid;
    private Texture bracket;
    Random rand = new Random(System.currentTimeMillis());


    @Override
    public void create() {

        Gdx.input.setInputProcessor(new GestureDetector(this));
        gameGrid = new Grid_cell[32][16];
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                gameGrid[i][j] = new Grid_cell();

            }
        }

        batch = new SpriteBatch();
        blocks = new Texture[]{new Texture("brown.png"), new Texture("darker_green.png"), new Texture("gold.png"), new Texture("light_green.png"), new Texture("pure_green.png"), new Texture("purple.png")};
        background = new Texture("backeground.png");
        grid = new Texture("grid_Back.png");
        bracket = new Texture("next_piece_bracket.png");
        screen_Height = Gdx.graphics.getHeight();
        screen_Width = Gdx.graphics.getWidth();
        bracket_Height = (float) (screen_Height * 0.30);
        comm_Width = (float) (screen_Width * 0.20);
        grid_Width = (float) (screen_Width * 0.60);
        grid_block_ratio_width = (float) (grid_Width / gameGrid[0].length);
        grid_block_ratio_height = (float) (screen_Height / gameGrid.length);
        def_x = (float) (comm_Width + (int) (gameGrid[0].length / 2) * grid_block_ratio_width);
        piece_x = comm_Width;
        piece_y = screen_Height - (3 * grid_block_ratio_height);


    }

    @Override
    public void render() {
        boardCheck();
        update();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, screen_Width, screen_Height);
        batch.draw(grid, comm_Width, 0, grid_Width, screen_Height);
        batch.draw(bracket, 0, screen_Height - bracket_Height, comm_Width, bracket_Height);
        drawPiece();
        drawGrid();
        batch.end();
    }

    public void drawPiece() {
        float temp_x = piece_x, temp_y = piece_y;

        for (int i = 1; i <= c_piece.piece.data.length; i++) {
            if (i > 3 && ((i - 1) % 3 == 0)) {
                temp_y += grid_block_ratio_height;
                temp_x = piece_x;
            }
            if (c_piece.piece.data[i - 1]) {
                batch.draw(blocks[c_piece.color], temp_x, temp_y, grid_block_ratio_width, grid_block_ratio_height);

            }
            temp_x += grid_block_ratio_width;
        }
        temp_x = (float) (comm_Width * 0.35);
        temp_y = screen_Height - (float) (bracket_Height * 0.37);
        for (int i = 1; i <= n_piece.piece.data.length; i++) {
            if (i > 3 && ((i - 1) % 3 == 0)) {
                temp_y += grid_block_ratio_height;
                temp_x = (float) (comm_Width * 0.35);
            }
            if (n_piece.piece.data[i - 1]) {
                batch.draw(blocks[n_piece.color], temp_x, temp_y, grid_block_ratio_width, grid_block_ratio_height);
            }
            temp_x += grid_block_ratio_width;
        }
//        for (int i = 0; i < c_piece.piece.length; i++) {
//            for (int j = 0; j < c_piece.piece[0].length; j++) {
//                if (c_piece.piece[i][j]) {
//                    batch.draw(blocks[c_piece.color], piece_x + (j * grid_block_ratio_width), piece_y + (i * grid_block_ratio_height), grid_block_ratio_width, grid_block_ratio_height);
//                }
//            }
//
//        }
//        for (int i = 0; i < n_piece.piece.length; i++) {
//            for (int j = 0; j < n_piece.piece[0].length; j++) {
//                if (n_piece.piece[i][j]) {
//                    batch.draw(blocks[n_piece.color], (float) (comm_Width * 0.35) + (j * grid_block_ratio_width), screen_Height - (float) (bracket_Height * 0.37) + (i * grid_block_ratio_height), grid_block_ratio_width, grid_block_ratio_height);
//                }
//            }
//
//        }
    }

    public void drawGrid() {
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                if (gameGrid[i][j].color != 7) {
                    batch.draw(blocks[gameGrid[i][j].color], comm_Width + (j * grid_block_ratio_width), i * grid_block_ratio_height, grid_block_ratio_width, grid_block_ratio_height);
                }
            }
        }
    }

    public void update() {
        if (!PIM) {
            randomSelector();
            piece_x = def_x;
            piece_y = screen_Height - (3 * grid_block_ratio_height);
            PIM = true;
        } else if (PIM && DELAY <= C_Delay) {
            PIM = hitbox();
            if (!PIM) {
                return;
            }
            C_Delay = 0;
            piece_y -= grid_block_ratio_height;

        } else {
            C_Delay++;
        }


    }

    public void randomSelector() {
        int index;
        int next_piece;
        if (!fr) {
            c_piece = n_piece;
            next_piece = rand.nextInt(6);
        } else {
            index = rand.nextInt(6);
            next_piece = rand.nextInt(6);
            fr = false;
            switch (index) {
                case 0:
                    c_piece = new S_shape();
                    break;
                case 1:
                    c_piece = new L_shape();
                    break;
                case 2:
                    c_piece = new I_shape();
                    break;
                case 3:
                    c_piece = new Box_shape();
                    break;
                case 4:
                    c_piece = new Z_shape();
                    break;
                case 5:
                    c_piece = new T_shape();
                    break;
            }
        }
        switch (next_piece) {
            case 0:
                n_piece = new S_shape();
                break;
            case 1:
                n_piece = new L_shape();
                break;
            case 2:
                n_piece = new I_shape();
                break;
            case 3:
                n_piece = new Box_shape();
                break;
            case 4:
                n_piece = new Z_shape();
                break;
            case 5:
                n_piece = new T_shape();
                break;
        }


    }

    public boolean hitbox() {
        float temp_x = piece_x, temp_y = piece_y;
        for (int i = 1; i <= c_piece.piece.data.length; i++) {
            if (i > 3 && ((i - 1) % 3 == 0)) {
                temp_y += grid_block_ratio_height;
                temp_x = piece_x;
            }
            if (c_piece.piece.data[i - 1]) {
                int x = (int) ((temp_x - comm_Width) / grid_block_ratio_width);
                int y = (int) (temp_y / grid_block_ratio_height);
                if (y - 1 < 0 || gameGrid[y - 1][x].taken) {
                    placePiece();
                    return false;
                }

            }
            temp_x += grid_block_ratio_width;
        }
        return true;
//        for (int i = 0; i < c_piece.piece.length; i++) {
//            for (int j = 0; j < c_piece.piece[0].length; j++) {
//                if (c_piece.piece[i][j]) {
//                    int x = (int) ((piece_x - comm_Width) / grid_block_ratio_width) + j;
//                    int y = (int) (piece_y / grid_block_ratio_height) + i;
//                    if (y - 1 < 0 || gameGrid[y - 1][x].taken) {
//                        placePiece();
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
    }

    public void placePiece() {
        float temp_x = piece_x, temp_y = piece_y;
        for (int i = 1; i <= c_piece.piece.data.length; i++) {
            if (i > 3 && ((i - 1) % 3 == 0)) {
                temp_y += grid_block_ratio_height;
                temp_x = piece_x;
            }
            if (c_piece.piece.data[i - 1]) {
                int x = (int) ((temp_x - comm_Width) / grid_block_ratio_width);
                int y = (int) (temp_y / grid_block_ratio_height);
                gameGrid[y][x].taken = true;
                gameGrid[y][x].color = c_piece.color;

            }
            temp_x += grid_block_ratio_width;
        }
        DELAY = 50;
//        for (int i = 0; i < c_piece.piece.length; i++) {
//            for (int j = 0; j < c_piece.piece[0].length; j++) {
//                if (c_piece.piece[i][j]) {
//                    int x = (int) ((piece_x - comm_Width) / grid_block_ratio_width) + j;
//                    int y = (int) (piece_y / grid_block_ratio_height) + i;
//                    gameGrid[y][x].taken = true;
//                    gameGrid[y][x].color = c_piece.color;
//                }
//            }
//        }
//        DELAY = 50;
    }

    public void boardCheck() {
        int counter = 0;
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                if (gameGrid[i][j].taken) {
                    counter++;
                }
            }

            if (counter == gameGrid[0].length && i != gameGrid.length - 1) {

                for (int z = i + 1; z <= gameGrid.length - 1; z++) {
                    System.arraycopy(gameGrid[z], 0, gameGrid[z - 1], 0, gameGrid[z].length);
                    gameGrid[z] = new Grid_cell[gameGrid[z - 1].length];
                }

                for (int l = 0; l < gameGrid[0].length; l++) {
                    gameGrid[gameGrid.length - 1][l] = new Grid_cell();

                }
                counter = 0;
            } else if (counter == gameGrid[0].length && i == gameGrid.length - 1) {
                gameGrid[i] = new Grid_cell[gameGrid[0].length];
                counter = 0;
            } else {
                counter = 0;
            }

        }

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {


        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (piece_y == 0) {
            return true;
        }
        c_piece.rotate();

//        if (piece_x + c_piece.piece[0].length * grid_block_ratio_width > screen_Width - comm_Width) {
//            piece_x -= (piece_x + c_piece.piece[0].length * grid_block_ratio_width) - (screen_Width - comm_Width);
//        }
//        if (piece_y + c_piece.piece.length * grid_block_ratio_height > screen_Height) {
//            piece_y -= (piece_y + c_piece.piece.length * grid_block_ratio_height) - screen_Height;
//        }


        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (velocityX < 600 && velocityX > -600 && velocityY > 0) {
            DELAY = 1;
            return true;
        }
        int d = 0;
        boolean e_break = false;
        float temp_x = piece_x, temp_y = piece_y;
        for (int i = 1; i <= c_piece.piece.data.length; i++) {
            if (i > 3 && ((i - 1) % 3 == 0)) {
                temp_y += grid_block_ratio_height;
                temp_x = piece_x;
            }
            if (c_piece.piece.data[i - 1]) {
                int x = (int) ((temp_x - comm_Width) / grid_block_ratio_width);
                int y = (int) (temp_y / grid_block_ratio_height);

                if (velocityX > 0 && x < gameGrid[0].length - 1 && !gameGrid[y][x + 1].taken) {
                    d = 1;
                } else if (velocityX < 0 && x > 0 && !gameGrid[y][x - 1].taken) {
                    d = 2;
                } else {
                    d = 0;
                    break;

                }
            }
            temp_x += grid_block_ratio_width;
        }
        switch (d) {
            case 1:
                if (piece_x + (c_piece.piece.width * grid_block_ratio_width) < screen_Width - comm_Width) {
                    piece_x += grid_block_ratio_width;
                }
                break;
            case 2:
                if (piece_x > comm_Width - grid_block_ratio_width) {
                    piece_x -= grid_block_ratio_width;
                }
                break;
            default:
                break;

        }
//        if (velocityX < 600 && velocityX > -600 && velocityY > 0) {
//            DELAY = 1;
//            return true;
//        }
//        int d = 0;
//        boolean e_break = false;
//        for (int i = 0; i < c_piece.piece.length; i++) {
//            for (int j = 0; j < c_piece.piece[0].length; j++) {
//                if (c_piece.piece[i][j]) {
//                    int x = (int) ((piece_x - comm_Width) / grid_block_ratio_width) + j;
//                    int y = (int) (piece_y / grid_block_ratio_height) + i;
//                    if (velocityX > 0 && x < gameGrid[0].length - 1 && !gameGrid[y][x + 1].taken) {
//                        d = 1;
//                    } else if (velocityX < 0 && x > 0 && !gameGrid[y][x - 1].taken) {
//                        d = 2;
//                    } else {
//                        d = 0;
//                        e_break=true;
//                        break;
//                    }
//
//                }
//            }
//            if(e_break){
//                break;
//            }
//
//        }
//        switch (d) {
//            case 1:
//                if (piece_x + (c_piece.piece[0].length * grid_block_ratio_width) < screen_Width - comm_Width) {
//                    piece_x += grid_block_ratio_width;
//                }
//                break;
//            case 2:
//                if (piece_x > comm_Width) {
//                    piece_x -= grid_block_ratio_width;
//                }
//                break;
//            default:
//                break;
//
//        }
//

        return true;

    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        Gdx.app.log("TAG", " " + x+ " " + y);
        //using the ratio of the width and the x cordinate design this part in a way that will allow the user to go through each index of the array in respect to x. Check for errors or check for hitboxing.
        int tx = (int) ((x - comm_Width) / grid_block_ratio_width);
        int px = (int) ((piece_x - comm_Width) / grid_block_ratio_width);
        Gdx.app.log("TAG", " " + tx+ " " + px+" "+ c_piece.piece.width);
        if(tx>px){
            if (px+c_piece.piece.width-1<15) {
//                Gdx.app.log("TAG", " " + tx+ " " + px);
                piece_x += grid_block_ratio_width;
            }
        }
        else if(tx<px){
            if (px>0) {
                piece_x -= grid_block_ratio_width;
            }
        }
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2
            pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].dispose();
        }
        background.dispose();
        grid.dispose();
        bracket.dispose();

    }
}
