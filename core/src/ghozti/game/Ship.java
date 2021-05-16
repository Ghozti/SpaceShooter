package ghozti.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class Ship {

    //ship characteristics
    float speed;//world units per second
    int shield;

    //position
    Rectangle boundingRect;

    //graphics
    TextureRegion ship,shieldT,laserTexture;
    //laser stuff
    float laserW,laserH, timeBetweenShots,timeSinceLastShot = 0, laserSpeed;

    boolean enemy;

    public Ship(float speed, int shield, float x, float y, float width, float height, TextureRegion ship, TextureRegion shieldT,TextureRegion laserTexture,boolean enemy,float laserW,float laserH,float timeBetweenShots,float laserSpeed) {
        this.speed = speed;
        this.shield = shield;

        this.boundingRect = new Rectangle(x,y,width,height);

        this.ship = ship;
        this.shieldT = shieldT;
        this.laserTexture = laserTexture;
        this.enemy = enemy;
        this.laserW = laserW;
        this.laserH = laserH;
        this.timeBetweenShots = timeBetweenShots;
        this.laserSpeed = laserSpeed;
    }

    public void update(float delta){
        timeSinceLastShot += delta;
    }

    public boolean canFireLaser(){
        return timeSinceLastShot - timeBetweenShots >= 0;
    }

    public abstract Laser[] fireLasers();

    public boolean intersects(Rectangle rectangle){
        return boundingRect.overlaps(rectangle);
    }

    public void hit(Laser laser){
        if (shield > 0){
            shield--;
        }
    }

    public void draw(Batch batch){
        batch.draw(ship,boundingRect.x,boundingRect.y,boundingRect.width,boundingRect.height);
        if (shield  > 0 && enemy){
            batch.draw(shieldT,boundingRect.x,boundingRect.y-3,boundingRect.width,boundingRect.height);
        }else if(shield > 0) batch.draw(shieldT,boundingRect.x,boundingRect.y,boundingRect.width,boundingRect.height);
    }

    public void trasnlate(float x, float y){
        boundingRect.setPosition(boundingRect.x+x,boundingRect.y+y);
    }
}
