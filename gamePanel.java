import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
// to do: incorporate sound effects and images (as of June 26, 2026 10:27 AM)

public class gamePanel extends JPanel implements KeyListener, ActionListener {

    // constants
    private static final int PLAYER_SIZE = 50;
    private static final int PROJECTILE_OFFSET_NUM_X = 25;
    private static final int PROJECTILE_OFFSET_NUM_Y = 5;
    private static final int RIGHT_BOUNDARY = 435;
    private static final int SCORE_X = 0; // The X-coordinate for the "Score" string
    private static final int SCORE_Y_AND_LIVES_Y = 25; // The Y-coordinate for the "Score" and "Lives" strings
    private static final int LIVES_X = 430; // The X-coordinate for the "Lives" string
    private static final int SCORE_INCREMENT = 100;


    private boolean movingLeft;
    private boolean movingRight;
    private int playerX;
    private final int playerY;
    private final int position;
    private final int numberOfLives;
    private int score;
    private boolean projectileMode;
    private int projectileX;
    private int projectileY;
    private final int projectileSize;
    private final ArrayList<Enemy> enemyArrayList;
    public gamePanel() {
        int enemyStartX = 100;
        enemyArrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Enemy newEnemy = new Enemy(enemyStartX, 100);
            enemyArrayList.add(newEnemy);
            enemyStartX = enemyStartX + 100;
        }
        this.movingLeft = false;
        this.movingRight = false;
        this.playerX = 225;
        this.playerY = 400;
        this.position = 1;
        this.score = 0;
        this.numberOfLives = 3;
        this.projectileMode = false;
        this.projectileSize = 5;
        this.setBackground(new Color(0x87CEEB));
        this.setFocusable(true);
        this.requestFocusInWindow();
        Timer timer = new Timer(10, this);
        timer.start();
    }

    public void moveEnemies() {
        for (Enemy currentEnemy : enemyArrayList) { //move all enemies
            currentEnemy.move();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!projectileMode) { // ensures only one bullet can be fired at a time
                projectileMode = true;
                this.projectileX = playerX + PROJECTILE_OFFSET_NUM_X;
                this.projectileY = playerY - PROJECTILE_OFFSET_NUM_Y;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movingRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movingLeft = false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Font setup
        g.setFont(new Font("Arial", Font.BOLD,14));
        g.setColor(Color.BLACK);

        String score = "Score: " + this.score;
        String lives = "Lives: " + this.numberOfLives;

        g.drawString(score, SCORE_X, SCORE_Y_AND_LIVES_Y);
        g.drawString(lives,LIVES_X,SCORE_Y_AND_LIVES_Y);

        // Draw background
        g.setColor(Color.WHITE);
        g.fillRect(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);

        // Draw enemy
        for (Enemy currentEnemy : enemyArrayList) {
            if (!currentEnemy.isDead()) {
                g.setColor(Color.GREEN);
                double currentEnemyX = currentEnemy.getEnemyX();
                double currentEnemyY = currentEnemy.getEnemyY();
                int enemySize = currentEnemy.getEnemySize();
                g.fillRect((int) currentEnemyX, (int) currentEnemyY, enemySize, enemySize);
                g.setColor(Color.black);
                g.drawRect((int) currentEnemyX, (int) currentEnemyY, enemySize, enemySize);
                //currentEnemy.move();
            }
        }

        // draw a projectile if triggered
        if (projectileMode) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(projectileX, projectileY, projectileSize,projectileSize);
        }

    }

    public boolean NotOutOfBounds(){
        return (!movingLeft || (playerX > 0)) && (!movingRight || (playerX < RIGHT_BOUNDARY));
    }

    public boolean projectileOutOfBounds() {
        return projectileY <= -5;
    }

    public void projectileCollisionDetected() {
        Rectangle projectileBounds = new Rectangle(projectileX,projectileY,projectileSize,projectileSize);
        for (Enemy enemy: enemyArrayList) {
            if (!enemy.isDead() && projectileBounds.intersects(enemy.getEnemyBounds())) {
                this.score = this.score + SCORE_INCREMENT; // May want to award scores differently based on which enemy dies
                enemy.setDead();
                this.resetProjectile();
                break;
            }
        }
    }

    @Override


    public void actionPerformed(ActionEvent e) {
        projectileCollisionDetected();
        moveEnemies();
        if (movingLeft) {
            if (NotOutOfBounds()) {
                this.playerX = this.playerX - position;
            }
        }
        if (movingRight) {
            if (NotOutOfBounds()) {
                this.playerX = this.playerX + position;
            }
        }

        if (projectileMode) {
            projectileY = projectileY - 2;
            if (projectileOutOfBounds()) {
                this.resetProjectile();
                System.out.println("here!");
            }
        }

        repaint();
    }

    public void resetProjectile() {
        this.projectileX = playerX + PROJECTILE_OFFSET_NUM_X;
        this.projectileY = playerY - PROJECTILE_OFFSET_NUM_Y;
        projectileMode = false;
    }

}
