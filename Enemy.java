import java.awt.*;

public class Enemy {

    private static final double MOVE_FACTOR = 0.50;
    private static final int RIGHT_BOUNDARY = 435;

    private double enemyX;
    private double enemyY;
    private final int enemySize;
    private boolean isDead;
    private boolean enemyMovingLeft;
    private boolean enemyMovingRight;


    public Enemy(int x, int y) {
        this.enemyX = x;
        this.enemyY = y;
        this.enemySize = 45;
        this.isDead = false;
        this.enemyMovingLeft = true;
        this.enemyMovingRight = false;
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

    public Rectangle getEnemyBounds() { // this helps us detect collisions since the intersect method requires a "Rectangle" object
        return new Rectangle((int) this.enemyX, (int) this.enemyY,this.enemySize,this.enemySize);
    }

}
