package ghozti.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class Laser {

    //characteristics
    float speed;

    //positioning
    float x,y;
    float width,height;

    //graphics
    TextureRegion region;

    public Laser(float x, float y, float width, float height,float speed,  TextureRegion region) {
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.region = region;
    }

    public void draw(Batch batch){
        batch.draw(region,x - width/2,y,width,height);
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(x,y,width,height);
    }
}
