package projet.snake.version9;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("serial")
public class SnakeGamePanel extends JPanel {
	private final SnakeGame snakeGame = new SnakeGame();
	private Timer timer = new Timer();
	private static final int MIN_BORDER_SIZE = 40;

	// Variables pour les images du serpent et de la pomme
	private Image appleImage;
	private Image background;
	private Image superAppleImage;

	private int speed = 350;

	public SnakeGamePanel() {
		appleImage = new ImageIcon(getClass().getResource("/images/apple.png")).getImage();
		superAppleImage = new ImageIcon(getClass().getResource("/images/superApple.png")).getImage();
		background = new ImageIcon(getClass().getResource("/images/background.png")).getImage();

		initializeKeyListener();
		initializeTimer();
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);

		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

		g.setColor(Color.DARK_GRAY);
		g.fillRect(getXBorder(), getYBorder(), SnakeGame.GRID_COLUMNS * getCellSize(),
				SnakeGame.GRID_LINES * getCellSize());

		g.setColor(Color.RED);
		g.setFont(g.getFont().deriveFont(20f));
		g.drawString("Score: " + snakeGame.getScore(), 10, 30);

		for (int line = 0; line < SnakeGame.GRID_LINES; line++)
			for (int column = 0; column < SnakeGame.GRID_COLUMNS; column++)
				paintPosition(g, line, column);
	}

	private void paintPosition(Graphics g, int line, int column) {
		int x = getX(column);
		int y = getY(line);

		if (snakeGame.isSnakeAt(new Position(line, column))) {
			if (snakeGame.isHeadAt(new Position(line, column))) {
				drawSnakeHead(g, x, y, getCellSize());
			} else {
				drawSnakeSegment(g, x, y, getCellSize());
			}
		} else if (snakeGame.isAppleAt(new Position(line, column))) {
			g.drawImage(appleImage, x, y, getCellSize(), getCellSize(), this);

		}

		if (snakeGame.isSuperAppleAt(new Position(line, column))) {
			g.drawImage(superAppleImage, x, y, getCellSize(), getCellSize(), this);
		}

	}

	private void drawSnakeSegment(Graphics g, int x, int y, int size) {
		int arcSize = size / 2; // Taille des coins arrondis

		g.setColor(new Color(46, 204, 113)); // Couleur principale
		g.fillRoundRect(x, y, size, size, arcSize, arcSize); // Forme arrondie

		g.setColor(new Color(39, 174, 96));
		g.drawRoundRect(x, y, size, size, arcSize, arcSize);
	}

	private void drawSnakeHead(Graphics g, int x, int y, int size) {
		g.setColor(new Color(39, 174, 96)); // Couleur de la tête
		g.fillRoundRect(x, y, size, size, size / 2, size / 2);

		g.setColor(Color.WHITE);
		g.fillOval(x + size / 4, y + size / 4, size / 6, size / 6); // Oeil gauche
		g.fillOval(x + size / 2, y + size / 4, size / 6, size / 6); // Oeil droit
	}

	public int getCellSize() {
		int cellMaximumHeight = (getHeight() - (2 * MIN_BORDER_SIZE)) / SnakeGame.GRID_LINES;
		int cellMaximumWidth = (getWidth() - (2 * MIN_BORDER_SIZE)) / SnakeGame.GRID_COLUMNS;
		return Math.min(cellMaximumWidth, cellMaximumHeight);
	}

	private int getX(int column) {
		return getXBorder() + column * getCellSize();
	}

	private int getY(int line) {
		return getYBorder() + line * getCellSize();
	}

	private int getXBorder() {
		return (getWidth() - (SnakeGame.GRID_COLUMNS * getCellSize())) / 2;
	}

	private int getYBorder() {
		return (getHeight() - (SnakeGame.GRID_LINES * getCellSize())) / 2;
	}

	private void initializeKeyListener() {
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP)
					snakeGame.changeDirection(Direction.SOUTH);
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
					snakeGame.changeDirection(Direction.NORTH);
				else if (e.getKeyCode() == KeyEvent.VK_LEFT)
					snakeGame.changeDirection(Direction.WEST);
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					snakeGame.changeDirection(Direction.EAST);
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					snakeGame.initialize();
			}
		});
	}

	private void initializeTimer() {

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				snakeGame.advance();
				adjustSpeed();
				repaint();
			}
		}, 1000, speed);
	}

	private void adjustSpeed() {
		int newSpeed = 350 - ((snakeGame.getScore() / 10) * 100);
		if (newSpeed < 150) {
			newSpeed = 150;
		}
		if (newSpeed != speed) {
			speed = newSpeed;
			initializeTimer();
		}
	}
}