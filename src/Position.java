package projet.snake.version9;

public class Position {
	public final int line;
	public final int column;

	public Position(int line, int column) {
		this.line = line;
		this.column = column;
	}

	public String toString() {
		return "(" + line + ", " + column + ")";
	}

	public boolean equals(Object obj) {
		Position position = (Position) obj;
		return position.line == this.line && position.column == this.column;
	}

	public Position getNext(Direction direction) {
		switch (direction) {
		case NORTH:
			return new Position(line + 1, column);
		case SOUTH:
			return new Position(line - 1, column);
		case EAST:
			return new Position(line, column + 1);
		case WEST:
			return new Position(line, column - 1);
		default:
			throw new IllegalArgumentException("Unexpected Direction: " + direction);
		}
	}
}
