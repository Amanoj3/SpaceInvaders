import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
// to do: incorporate sound effects and images (as of June 26, 2026 10:27 AM)
// to do: work on enemy projectile movements (as of June 27, 2026 11:28 AM)

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
    private static final int TOP_BOUNDARY = -5;
    private static final int LEFT_BOUNDARY = 0;
    private static final int TIMER_THRESHOLD = 3000; // enemies fire every 3 seconds (adjust if need be)
    private static final int MILLISECOND_INCREMENT = 10;
    private static final int PROJECTILE_SPEED = 2;

    private Enemy randomEnemy;
    private int millisecondCounter;

    private boolean movingLeft;
    private int enemyShotIndex;
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

    private BufferedImage img;

    public gamePanel() {
        int enemyStartX = 100;
        enemyArrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Enemy newEnemy = new Enemy(enemyStartX, 100);
            enemyArrayList.add(newEnemy);
            enemyStartX = enemyStartX + 100;
        }
        this.setPreferredSize(new Dimension(500,500));
        String background_filepath = "/SpaceInvadersBackground.png";

        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource(background_filepath)));
            System.out.println("IMAGE LOADED!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.millisecondCounter = 0;
        this.movingLeft = false;
        this.movingRight = false;
        this.playerX = 225;
        this.playerY = 400;
        this.position = 1;
        this.score = 0;
        this.numberOfLives = 3;
        this.projectileMode = false;
        this.projectileSize = 5;
        this.enemyShotIndex = -1; // no enemy has fired a shot yet
        this.setBackground(new Color(0x87CEEB));
        this.setFocusable(true);
        this.requestFocusInWindow();
        Timer timer = new Timer(10, this);
        timer.start();
        System.out.println("img width: " + img.getWidth());
        System.out.println("img height: " + img.getHeight());
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

        if (img != null) {
            g.drawImage(img,0,0,500,500,this);
        }
        g.setColor(Color.RED);
        g.drawRect(0,0,500,500);
        /*if (backgroundLabel.getIcon() != null) {
            Image img = ((ImageIcon) backgroundLabel.getIcon()).getImage();
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            System.out.println("bg label not null!");
        }
        else {
            this.setBackground(new Color(0x87CEEB));
        }*/


        // Font setup
        g.setFont(new Font("Arial", Font.BOLD,14));
        g.setColor(Color.BLACK);

        String score = "Score: " + this.score;
        String lives = "Lives: " + this.numberOfLives;

        g.drawString(score, SCORE_X, SCORE_Y_AND_LIVES_Y);
        g.drawString(lives,LIVES_X,SCORE_Y_AND_LIVES_Y);

        // Draw protagonist
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

        if (randomEnemy != null) { // prevents null pointer exceptions
            if (randomEnemy.isEnemyProjectileOn() && !randomEnemy.isDead()) {
                g.setColor(Color.RED);
                int enemyProjectileX = (int) randomEnemy.getEnemyProjectileX();
                int enemyProjectileY = (int) randomEnemy.getEnemyProjectileY();
                g.fillRect(enemyProjectileX, enemyProjectileY, projectileSize, projectileSize);
            }
        }
    }

    public boolean NotOutOfBounds(){
        return (!movingLeft || (playerX > LEFT_BOUNDARY)) && (!movingRight || (playerX < RIGHT_BOUNDARY));
    }

    public boolean projectileOutOfBounds() {
        return projectileY <= TOP_BOUNDARY;
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

    public int getRandomEnemyIndex() {
        Random randomObject = new Random();
        return randomObject.nextInt(enemyArrayList.size());
    }


    @Override

    public void actionPerformed(ActionEvent e) {
        this.millisecondCounter = this.millisecondCounter + MILLISECOND_INCREMENT;
        projectileCollisionDetected();
        moveEnemies();

        if (millisecondCounter >= TIMER_THRESHOLD) {
            if (enemyShotIndex == -1) { // ensures only one enemy can fire a shot at a time
                enemyShotIndex = getRandomEnemyIndex();
                randomEnemy = enemyArrayList.get(enemyShotIndex);
                randomEnemy.shoot();
                millisecondCounter = 0;
            }
        }
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
            projectileY = projectileY - PROJECTILE_SPEED;
            if (projectileOutOfBounds()) {
                this.resetProjectile();
                System.out.println("here!");
            }
        }

        if (randomEnemy != null) {
            if (randomEnemy.isEnemyProjectileOn()) {
                double currentEnemyProjectileY = randomEnemy.getEnemyProjectileY();
                double newEnemyProjectileY = currentEnemyProjectileY + PROJECTILE_SPEED;
                randomEnemy.setEnemyProjectileY(newEnemyProjectileY);
                if (randomEnemy.enemyProjectileOutOfBounds()) {
                    randomEnemy.enemyResetProjectile();
                    enemyShotIndex = -1;
                }
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
