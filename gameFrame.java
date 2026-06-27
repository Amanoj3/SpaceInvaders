import javax.swing.*;

public class gameFrame extends JFrame {
    public gameFrame(int windowWidth, int windowHeight) {
        this.setTitle("Game Window!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(windowWidth, windowHeight);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.requestFocusInWindow();

    }
}
