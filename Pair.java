package test.xiyang;

/**
 * Pair for returning two values. NOTE: I am not using the java.lang
 */
public class Pair {
    int left;
    int right;

    public Pair(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public static Pair of(int left, int right) {
        return new Pair(left, right);
    }
}
