package ghozti.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Screen implements com.badlogic.gdx.Screen {

    //Screen
    private Camera camera;
    private Viewport viewport;
    //Game
    private SpriteBatch batch;
    private Texture[] background;
    //Timing
    private float[] backgroundOffset = {0,0,0,0};
    private float backgroundMaxScrollSpeed;
    //World
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;


    Screen(){

        camera = new OrthographicCamera();//ortho cam is a 2d camera with no 3d features. Perfect for 2d games
        viewport = new StretchViewport(WORLD_WIDTH,WORLD_HEIGHT,camera);//what the user sees. Takes in 3 param. w,h,cam
        background = new Texture[4];
        background[0] = new Texture("Starscape00.png");
        background[1] = new Texture("Starscape01.png");
        background[2] = new Texture("Starscape02.png");
        background[3] = new Texture("Starscape03.png");
        backgroundMaxScrollSpeed = (float)WORLD_HEIGHT/4;
        batch = new SpriteBatch();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();//always include begin and end in a render method

        //Scrolling bg
        renderBackground(delta);
        batch.end();
    }

    private void renderBackground(float delta){
        backgroundOffset[0] += delta*backgroundMaxScrollSpeed/8;
        backgroundOffset[1] += delta*backgroundMaxScrollSpeed/4;
        backgroundOffset[2] += delta*backgroundMaxScrollSpeed/2;
        backgroundOffset[3] += delta*backgroundMaxScrollSpeed;

        for(int layer = 0; layer < backgroundOffset.length; layer++){
            if (backgroundOffset[layer] > WORLD_HEIGHT){
                backgroundOffset[layer] = 0;
            }
            batch.draw(background[layer],0,-backgroundOffset[layer],WORLD_WIDTH,WORLD_HEIGHT);
            batch.draw(background[layer],0,-backgroundOffset[layer] + WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT);
        }
    }

    @Override//for when you change the window size and runs and the beginning of the application
    public void resize(int width, int height) {
        viewport.update(width,height,true);//tells the viewport to update accordingly
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
