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
    TextureRegion ship,shieldT;

    public Ship(float speed, int shield, float x, float y, float width, float height, TextureRegion ship, TextureRegion shieldT) {
        this.speed = speed;
        this.shield = shield;
        this.x = x - width/2;
        this.y = y - width/2;
        this.width = width;
        this.height = height;
        this.ship = ship;
        this.shieldT = shieldT;
    }

    public void draw(Batch batch){
        batch.draw(ship,x,y,width,height);
        if (shield  > 0){
            batch.draw(shieldT,x,y,width,height);
        }
    }
}
