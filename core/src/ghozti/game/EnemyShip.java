package ghozti.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyShip extends Ship{

    public EnemyShip(float speed, int shield, float x, float y, float width, float height, TextureRegion ship, TextureRegion shieldT, TextureRegion laserTexture, boolean enemy, float laserW, float laserH, float timeBetweenShots, float laserSpeed) {
        super(speed, shield, x, y, width, height, ship, shieldT, laserTexture, enemy, laserW, laserH, timeBetweenShots, laserSpeed);
    }

    @Override
    public Laser[] fireLasers() {
        return new Laser[0];
    }
}
