package blackclient;

public class Point implements Comparable<Point> {
	int x;
	int y;
	int score;

	Point() {
		this.x = -1;
		this.y = -1;
		this.score = 0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(Point o) {
		// TODO Auto-generated method stub
		if (this.getScore() > o.getScore())
			return 1;
		else if (this.getScore() == o.getScore())
			return 0;
		else
			return -1;
	}

}
