package ghozti.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class EnemyShip extends Ship{

    Vector2 directionV;
    float timeSinceLastMove = 0, changeFre = 0.75f;

    public EnemyShip(float speed, int shield, float x, float y, float width, float height, TextureRegion ship, TextureRegion shieldT, TextureRegion laserTexture, boolean enemy, float laserW, float laserH, float timeBetweenShots, float laserSpeed) {
        super(speed, shield, x, y, width, height, ship, shieldT, laserTexture, enemy, laserW, laserH, timeBetweenShots, laserSpeed);

        directionV = new Vector2(0,-1);
    }

    public Vector2 getDirectionV(){
        return directionV;
    }

    private void randomizeDir(){
        double bearing = Game.rand.nextDouble()*6.283185;
        directionV.x = (float)Math.sin(bearing);
        directionV.y = (float)Math.cos(bearing);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        timeSinceLastMove += delta;
        if(timeSinceLastMove > changeFre){
            randomizeDir();
            timeSinceLastMove -= changeFre;
        }
    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(boundingRect.x+boundingRect.width*0.18f,boundingRect.y,laserW,laserH,laserSpeed,laserTexture);
        laser[1] = new Laser(boundingRect.x+boundingRect.width*0.82f,boundingRect.y,laserW,laserH,laserSpeed,laserTexture);
        timeSinceLastShot = 0;
        return laser;
    }
}
