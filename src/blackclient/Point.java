package blackclient;

public class Point {
    int x;
    int y;
    int score;
    int abs;
    int prevx;
    int prevy;
    int black;
    int white;
    int index;

    Point() {
        this.x = -1;
        this.y = -1;
        this.score = 0;
        this.abs = 0;
        this.prevx = 0;
        this.prevy = 0;
        this.black = 0;
        this.white = 0;
        this.index= -1;
    }

    public void setWhite(int white) {
        this.white = white;
    }

    public void setBlack(int black) {
        this.black = black;
    }

    Point(int x, int y, int score, int px, int py) {
        setX(x);
        setY(y);
        setScore(score);
        setPrevx(px);
        setPrevy(py);

    }

    Point(int x, int y, int score,int index) {
        setX(x);
        setY(y);
        setScore(score);
        setindex(index);
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setindex(int i) {
        this.index = i;
    }

    public void setY(int y) {
        this.y = y;
    }


    public void setScore(int score) {
        this.score = score;
        this.abs = Math.abs(score);
    }


    public void setPrevx(int prevx) {
        this.prevx = prevx;
    }

    public void setPrevy(int prevy) {
        this.prevy = prevy;
    }
}
