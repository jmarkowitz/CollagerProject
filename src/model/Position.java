package model;

public class Position {
  public final int x_pos;

  public final int y_pos;

  public Position(int xPos, int yPos) {
    x_pos = xPos;
    y_pos = yPos;
  }

  public String toString() {
    return String.format("(r%d,c%d)", this.x_pos, this.y_pos);
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (o != null && this.getClass() == o.getClass()) {
      Position position = (Position)o;
      return this.x_pos == position.x_pos
              && this.y_pos == position.y_pos;
    } else {
      return false;
    }
  }
}
