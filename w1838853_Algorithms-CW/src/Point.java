
public class Point {
    private int pointID;
    private int x;
    private int y;
    private String character;

    public Point() {}

    public Point(int pointID, int x, int y, String character) {
        this.pointID = pointID;
        this.x = x; //x coordinate
        this.y = y;//y coordinate
        this.character = character; //letter present
    }

    /**
     * getter method to return pointID
     * @return
     */
    public int getPointID() {
        return pointID;
    }

    /**
     * setter method to set the pointID
     * @param pointID
     */
    public void setPointID(int pointID) {
        this.pointID = pointID;
    }

    /**
     * getter method to return the x coordinate
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * setter method to set the x coordinate
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * getter method to return the y coordinate
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * setter method to set the y coordinate
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * getter method to return the character at the given point
     * @return
     */
    public String getCharacter() {
        return character;
    }

    /**
     * setter method to set the character at a given point
     * @param character
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return "Point [id=" + pointID + ", x=" + x + ", y=" + y + ", letter=" + character + "]";
    }

}
