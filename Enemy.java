import java.awt.*;

public class Enemy {

    private static final double MOVE_FACTOR = 0.30;
    private static final int RIGHT_BOUNDARY = 435;
    private static final double ENEMY_PROJECTILE_OFFSET_X = 22.5;
    private static final double ENEMY_PROJECTILE_OFFSET_Y = 5;

    private double enemyX;
    private double enemyY;
    private final int enemySize;
    private boolean isDead;
    private boolean enemyMovingLeft;
    private boolean enemyMovingRight;
    private double enemyProjectileX;
    private double enemyProjectileY;
    private boolean enemyProjectileOn;


    public Enemy(int x, int y) {
        this.enemyX = x;
        this.enemyY = y;
        this.enemySize = 45;
        this.isDead = false;
        this.enemyMovingLeft = true;
        this.enemyMovingRight = false;
        this.enemyProjectileX = this.enemyX + ENEMY_PROJECTILE_OFFSET_X;
        this.enemyProjectileY = this.enemyY + ENEMY_PROJECTILE_OFFSET_Y; // it goes down
        this.enemyProjectileOn = false;
    }

    public double getEnemyX(){
        return this.enemyX;
    }
    public double getEnemyY() {
        return this.enemyY;
    }
    public int getEnemySize() {
        return this.enemySize;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead() {
        if (!isDead) {
            this.isDead = true;
        }
    }

    private boolean enemyOutOfBounds() {
        if (enemyMovingLeft && enemyX < 0) {
            return true;
        }
        return enemyMovingRight && enemyX > RIGHT_BOUNDARY;
    }

    public void move() {
        if (enemyOutOfBounds()) {
            if (enemyMovingLeft) {
                moveRight();
                return;
            }
            if (enemyMovingRight) {
                moveLeft();
            }
        }
        else {
            if (enemyMovingLeft) {
                moveLeft();
                return;
            }
            if (enemyMovingRight) {
                moveRight();
            }
        }
    }

    public boolean enemyProjectileOutOfBounds() {
        return enemyProjectileY >= 505;
    }

    public boolean isEnemyProjectileOn() {
        return this.enemyProjectileOn;
    }

    public void enemyResetProjectile() {
        this.enemyProjectileOn = false;
        this.enemyProjectileX = this.enemyX + ENEMY_PROJECTILE_OFFSET_X;
        this.enemyProjectileY = this.enemyY + ENEMY_PROJECTILE_OFFSET_Y;
    }

    public void shoot() {

        if (!this.enemyProjectileOn) {
            this.enemyProjectileOn = true;
            this.enemyProjectileX = this.enemyX + ENEMY_PROJECTILE_OFFSET_X;
            this.enemyProjectileY = this.enemyY + ENEMY_PROJECTILE_OFFSET_Y;
        }

    }

    public void moveLeft() {
        this.enemyMovingLeft = true;
        this.enemyMovingRight = false;
        this.enemyX = this.enemyX - MOVE_FACTOR;
    }

    public void moveRight() {
        this.enemyMovingRight = true;
        this.enemyMovingLeft = false;
        this.enemyX = this.enemyX + MOVE_FACTOR;
    }

    public double getEnemyProjectileX() {
        return this.enemyProjectileX;
    }

    public double getEnemyProjectileY() {
        return this.enemyProjectileY;
    }

    public void setEnemyProjectileY(double enemyProjectileY) {
        this.enemyProjectileY = enemyProjectileY;
    }

    public Rectangle getEnemyBounds() { // this helps us detect collisions since the intersect method requires a "Rectangle" object
        return new Rectangle((int) this.enemyX, (int) this.enemyY,this.enemySize,this.enemySize);
    }

}
