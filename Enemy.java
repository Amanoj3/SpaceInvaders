import java.awt.*;

public class Enemy {
    private int enemyX;
    private int enemyY;
    private final int enemySize;
    private boolean isDead;

    public Enemy(int x, int y) {
        this.enemyX = x;
        this.enemyY = y;
        this.enemySize = 45;
        this.isDead = false;
    }

    public int getEnemyX(){
        return this.enemyX;
    }
    public int getEnemyY() {
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

    public Rectangle getEnemyBounds() { // this helps us detect collisions since the intersect method requires a "Rectangle" object
        return new Rectangle(this.enemyX,this.enemyY,this.enemySize,this.enemySize);
    }

}
