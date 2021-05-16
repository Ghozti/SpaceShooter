package ghozti.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyShip extends Ship{

    public EnemyShip(float speed, int shield, float x, float y, float width, float height, TextureRegion ship, TextureRegion shieldT, TextureRegion laserTexture, boolean enemy, float laserW, float laserH, float timeBetweenShots, float laserSpeed) {
        super(speed, shield, x, y, width, height, ship, shieldT, laserTexture, enemy, laserW, laserH, timeBetweenShots, laserSpeed);
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
