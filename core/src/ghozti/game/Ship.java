package ghozti.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ship {

    //ship characteristics
    float speed;//world units per second
    int shield;

    //position
    float x,y;
    float width, height;

    //graphics
    TextureRegion ship,shieldT,laserTexture;
    //laser stuff
    float laserW,laserH, timeBetweenShots,timeSinceLAstShot = 0, laserSpeed;

    boolean enemy;

    public Ship(float speed, int shield, float x, float y, float width, float height, TextureRegion ship, TextureRegion shieldT,TextureRegion laserTexture,boolean enemy,float laserW,float laserH,float timeBetweenShots,float laserSpeed) {
        this.speed = speed;
        this.shield = shield;
        this.x = x - width/2;
        this.y = y - width/2;
        this.width = width;
        this.height = height;
        this.ship = ship;
        this.shieldT = shieldT;
        this.laserTexture = laserTexture;
        this.enemy = enemy;
        this.laserW = laserW;
        this.laserH = laserH;
        this.timeBetweenShots = timeBetweenShots;
        this.laserSpeed = laserSpeed;
    }

    public void draw(Batch batch){
        batch.draw(ship,x,y,width,height);
        if (shield  > 0 && enemy){
            batch.draw(shieldT,x,y-3,width,height);
        }else if(shield > 0) batch.draw(shieldT,x,y,width,height);
    }
}
