package ghozti.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;

public class Screen implements com.badlogic.gdx.Screen {

    //Screen
    private Camera camera;
    private Viewport viewport;
    //Game
    private SpriteBatch batch;
    private TextureRegion[] background;
    private TextureRegion playerShip, playerShield,enemyShip,enemyShield,playerLaser,enemyLaser;
    private TextureAtlas textureAtlas;
    //Timing
    private float[] backgroundOffset = {0,0,0,0};
    private float backgroundMaxScrollSpeed;
    //World
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;
    //objects
    private Ship player;
    private Ship enemy;

    //linked list
    LinkedList<Laser> playerLaserL;
    LinkedList<Laser> enemyLaserL;


    Screen(){

        camera = new OrthographicCamera();//ortho cam is a 2d camera with no 3d features. Perfect for 2d games
        viewport = new StretchViewport(WORLD_WIDTH,WORLD_HEIGHT,camera);//what the user sees. Takes in 3 param. w,h,cam
        textureAtlas = new TextureAtlas("images.atlas");

        background = new TextureRegion[4];
        background[0] = textureAtlas.findRegion("Starscape00");
        background[1] = textureAtlas.findRegion("Starscape01");
        background[2] = textureAtlas.findRegion("Starscape02");
        background[3] = textureAtlas.findRegion("Starscape03");
        backgroundMaxScrollSpeed = (float)WORLD_HEIGHT/4;

        playerShip = textureAtlas.findRegion("playerShip2_blue");
        enemyShip = textureAtlas.findRegion("enemyRed3");
        playerShield = textureAtlas.findRegion("shield1");
        enemyShield = textureAtlas.findRegion("shield2");
        enemyShield.flip(false,true);
        playerLaser = textureAtlas.findRegion("laserBlue01");
        enemyLaser = textureAtlas.findRegion("laserRed01");

        player = new PlayerShip(2,3,WORLD_WIDTH/2,WORLD_HEIGHT/4,10,10,playerShip,playerShield,playerLaser,false,.4f,4,.5f,45);
        enemy = new EnemyShip(2,1,WORLD_WIDTH/2,WORLD_HEIGHT*3/4,10,10,enemyShip,enemyShield,enemyLaser,false,.4f,4,.8f,45);

        playerLaserL = new LinkedList<>();
        enemyLaserL = new LinkedList<>();
        batch = new SpriteBatch();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();//always include begin and end in a render method
        player.update(delta);
        enemy.update(delta);

        //Scrolling bg
        renderBackground(delta);

        //ships
        player.draw(batch);
        enemy.draw(batch);

        //laser
        if (player.canFireLaser()){
            Laser[] lasers = player.fireLasers();
            for (Laser i : lasers){
                playerLaserL.add(i);
            }
        }

        if (enemy.canFireLaser()){
            Laser[] lasers = enemy.fireLasers();
            for (Laser i : lasers){
                enemyLaserL.add(i);
            }
        }

        ListIterator<Laser> iterator = playerLaserL.listIterator();

        while (iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.y += laser.speed*delta;
            if (laser.y > WORLD_HEIGHT) iterator.remove();
        }

        iterator = enemyLaserL.listIterator();
        while (iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.y -= laser.speed*delta;
            if (laser.y + laser.height < 0) iterator.remove();
        }
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
