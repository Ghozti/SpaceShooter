package ghozti.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
    private final float MOVEMENT_THRESHOLD = 0.8F;
    //objects
    private PlayerShip player;
    private EnemyShip enemy;

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

        player = new PlayerShip(106,3,WORLD_WIDTH/2,WORLD_HEIGHT/4,10,10,playerShip,playerShield,playerLaser,false,.4f,4,.5f,45);
        enemy = new EnemyShip(106,1,WORLD_WIDTH/2,WORLD_HEIGHT*3/4,10,10,enemyShip,enemyShield,enemyLaser,true,.4f,4,.8f,45);

        playerLaserL = new LinkedList<>();
        enemyLaserL = new LinkedList<>();
        batch = new SpriteBatch();
    }


    @Override
    public void show() {

    }

    private void renderLasers(float delta){
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
            laser.boundingRect.y += laser.speed*delta;
            if (laser.boundingRect.y > WORLD_HEIGHT) iterator.remove();
        }

        iterator = enemyLaserL.listIterator();
        while (iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingRect.y -= laser.speed*delta;
            if (laser.boundingRect.y + laser.boundingRect.height < 0) iterator.remove();
        }
    }

    private void detectCollision(){
        ListIterator<Laser> iterator = playerLaserL.listIterator();
        while (iterator.hasNext()){
            Laser laser = iterator.next();
            if (enemy.intersects(laser.boundingRect)) {
                player.hit(laser);
                iterator.remove();//removes last item we are working with
            }
        }
        iterator = enemyLaserL.listIterator();
        while (iterator.hasNext()){
            Laser laser = iterator.next();
            if (player.intersects(laser.boundingRect)) {
                enemy.hit(laser);
                iterator.remove();//removes last item we are working with
            }
        }
    }

    private void renderExplosions(float delta){

    }

    private void detectInput(float delta){
        float leftLimit = -player.boundingRect.x,rightLimit = WORLD_WIDTH-player.boundingRect.x-player.boundingRect.width,upLimit = WORLD_HEIGHT/2 - player.boundingRect.y - player.boundingRect.height,downLimit = -player.boundingRect.y;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0){
            float xChange = player.speed*delta;
            xChange = Math.min(xChange,rightLimit);
            player.trasnlate(xChange,0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0){
            float xChange = -player.speed*delta;
            xChange = Math.max(xChange,leftLimit);
            player.trasnlate(xChange,0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit > 0){
            float yChange = player.speed*delta;
            yChange = Math.min(yChange,upLimit);
            player.trasnlate(0f,yChange);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit < 0){
            float yChange = -player.speed*delta;
            yChange = Math.max(yChange,downLimit);
            player.trasnlate(0f,yChange);
        }
        if(Gdx.input.isTouched()){
            float touchX = Gdx.input.getX(),touchY = Gdx.input.getY();
            Vector2 touchPoint = new Vector2(touchX,touchY);
            touchPoint = viewport.unproject(touchPoint);
            Vector2 playerCenter = new Vector2(player.boundingRect.x+player.boundingRect.width/2,player.boundingRect.y+player.boundingRect.height/2);
            float touchDistance = touchPoint.dst(playerCenter);
            if(touchDistance>MOVEMENT_THRESHOLD){
                float xTouchDif = touchPoint.x - playerCenter.x, yTouchDif = touchPoint.y - playerCenter.y;

                float xmove = xTouchDif/touchDistance*player.speed*delta;
                float ymove = yTouchDif/touchDistance*player.speed*delta;

                if (xmove > 0){
                    xmove = Math.min(xmove,rightLimit);
                }else xmove = Math.max(xmove,leftLimit);

                if(ymove>0){
                    ymove = Math.min(ymove,upLimit);
                }else ymove = Math.max(ymove,downLimit);

                player.trasnlate(xmove,ymove);
            }
        }
    }

    public void moveEnemies(float delta){
        float leftLimit = -enemy.boundingRect.x,
                rightLimit = WORLD_WIDTH-enemy.boundingRect.x-enemy.boundingRect.width,
                upLimit = WORLD_HEIGHT - enemy.boundingRect.y - enemy.boundingRect.height,
                downLimit = WORLD_HEIGHT/2-player.boundingRect.y - player.boundingRect.height;

        float xmove = enemy.getDirectionV().x*enemy.speed*delta;
        float ymove = enemy.getDirectionV().y*enemy.speed*delta;

        if (xmove > 0){
            xmove = Math.min(xmove,rightLimit);
        }else xmove = Math.max(xmove,leftLimit);

        if(ymove>0){
            ymove = Math.min(ymove,upLimit);
        }else ymove = Math.max(ymove,downLimit);
        enemy.trasnlate(xmove,ymove);
    }

    @Override
    public void render(float delta) {
        batch.begin();//always include begin and end in a render method
        detectInput(delta);
        moveEnemies(delta);
        player.update(delta);
        enemy.update(delta);

        //Scrolling bg
        renderBackground(delta);
        //ships
        player.draw(batch);
        enemy.draw(batch);
        renderLasers(delta);
        detectCollision();
        renderExplosions(delta);

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
