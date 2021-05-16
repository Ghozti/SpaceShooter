package ghozti.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Laser {

    //characteristics
    float speed;

    //positioning
    float x,y;
    float width,height;

    //graphics
    TextureRegion region;

    public Laser(float speed, float x, float y, float width, float height, TextureRegion region) {
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
}
