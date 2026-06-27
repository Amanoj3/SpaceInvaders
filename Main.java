import javax.swing.*;

private static final int WINDOW_WIDTH = 500;
private static final int WINDOW_HEIGHT = 500;

void main() {

    gameFrame myFrame = new gameFrame(WINDOW_WIDTH,WINDOW_HEIGHT);
    gamePanel myPanel = new gamePanel();
    myFrame.addKeyListener(myPanel);
    myFrame.add(myPanel);
    myFrame.setResizable(false);
}
