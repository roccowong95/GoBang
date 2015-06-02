package blackclient;

public class Point {
    int x;
    int y;
    int score;
    int abs;
    int prevx;
    int prevy;

    Point() {
        this.x = -1;
        this.y = -1;
        this.score = 0;
        this.abs = 0;
        this.prevx = 0;
        this.prevy = 0;
    }

    Point(int x, int y, int score, int px, int py) {
        setX(x);
        setY(y);
        setScore(score);
        setPrevx(px);
        setPrevy(py);
    }

    Point(int x, int y, int score) {
        setX(x);
        setY(y);
        setScore(score);
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
        this.abs = Math.abs(score);
    }

    public int getPrevx() {
        return prevx;
    }

    public void setPrevx(int prevx) {
        this.prevx = prevx;
    }

    public void setPrevy(int prevy) {
        this.prevy = prevy;
    }
}
