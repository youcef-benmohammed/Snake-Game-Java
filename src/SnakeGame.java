package projet.snake.version9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGame {

	public static final int GRID_LINES = 15;
	public static final int GRID_COLUMNS = 15;
	private List<Position> segments;
	private Direction direction;
	private boolean gameOver;
	private Position apple;
	private int score;
	private int appleCount;

	// SuperApple Feature
	private Position superApple;
	private static final int SUPER_APPLE_SCORE = 3;
	private boolean superAppleEaten;

	// Level


	public SnakeGame() {
		initialize();
	}

	public void initialize() {

		segments = new ArrayList<Position>();

		segments.add(new Position(GRID_LINES / 2, GRID_COLUMNS / 2));
		segments.add(new Position(GRID_LINES / 2, GRID_COLUMNS / 2 - 1));
		segments.add(new Position(GRID_LINES / 2, GRID_COLUMNS / 2 - 2));

		direction = Direction.EAST;

		placeApple();

		superApple = null;
		superAppleEaten = false;

		score = 0;
		appleCount = 0;
		gameOver = false;

	}

	public int getScore() {
		return score;
	}

	public void placeApple() {
		Random random = new Random();
		Position newApple;
		do {
			newApple = new Position(random.nextInt(GRID_LINES), random.nextInt(GRID_COLUMNS));
		} while (isSnakeAt(newApple));

		apple = newApple;
	}

	public void placeSuperApple() {
		if (!superAppleEaten && appleCount % 3 == 0 && superApple == null) {
			Random random = new Random();
			Position newApple;
			do {
				newApple = new Position(random.nextInt(GRID_LINES), random.nextInt(GRID_COLUMNS));
			} while (isSnakeAt(newApple));

			superApple = newApple;
		}
	}

	public void advance() {
		if (gameOver)
			return;

		Position newPosition = segments.get(0).getNext(direction);

		if (!isValidPosition(newPosition) || isSnakeAt(newPosition)) {
			gameOver = true;
			return;
		}

		if (newPosition.equals(apple)) {
			segments.add(0, newPosition);
			placeApple();
			score++;
			appleCount++;
			placeSuperApple();
		} else {
			segments.add(0, newPosition);
			segments.remove(segments.size() - 1);
		}

		if (superApple != null && newPosition.equals(superApple)) {
			for (int i = 0; i < SUPER_APPLE_SCORE; i++) {
				segments.add(0, newPosition);
			}
			superApple = null; // Supprimer la super-pomme
			score += SUPER_APPLE_SCORE; // Mise à jour du score
			superAppleEaten = true;
		}

		if (superAppleEaten && appleCount % 3 != 0) {
			superAppleEaten = false;
			placeSuperApple();
		}

	}

	public boolean isValidPosition(Position position) {
		return (position.line >= 0 && position.line < GRID_LINES && position.column >= 0
				&& position.column < GRID_COLUMNS);

	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void changeDirection(Direction newDirection) {
		if ((direction == Direction.EAST && newDirection == Direction.WEST)
				|| (direction == Direction.WEST && newDirection == Direction.EAST)
				|| (direction == Direction.NORTH && newDirection == Direction.SOUTH)
				|| (direction == Direction.SOUTH && newDirection == Direction.NORTH))
			return;

		direction = newDirection;
	}

	public boolean isSnakeAt(Position position) {
		for (Position segment : segments) {
			if (segment.equals(position))
				return true;
		}
		return false;
	}

	public boolean isAppleAt(Position position) {
		return apple.equals(position);
	}

	public boolean isSuperAppleAt(Position position) {
		return superApple != null && superApple.equals(position);
	}

	public boolean isHeadAt(Position position) {
		return segments.get(0).equals(position);
	}

}