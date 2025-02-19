package projet.snake.version9;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class SnakeGameFrame extends JFrame {
	public SnakeGameFrame() {
		setTitle("Snake");
		setSize(new Dimension(400, 400));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		add(new SnakeGamePanel());
	}
}
