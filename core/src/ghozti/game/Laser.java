package ghozti.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class Laser {

    //characteristics
    float speed;

    //positioning
    Rectangle boundingRect;

    //graphics
    TextureRegion region;

    public Laser(float x, float y, float width, float height,float speed,  TextureRegion region) {
        this.speed = speed;
        boundingRect = new Rectangle(x-width/2,y,width,height);
        this.region = region;
    }

    public void draw(Batch batch){
        batch.draw(region,boundingRect.x,boundingRect.y,boundingRect.width,boundingRect.height);
    }
}
