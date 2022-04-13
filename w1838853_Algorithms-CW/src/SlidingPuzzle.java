
import java.io.*;
import java.util.*;

public class SlidingPuzzle {

    private Point[][] slidingPuzzleArray;
    private int widthMax;
    private int heightMax;
    private Point startingPoint;
    private Point finishingPoint;
    private String filesLocation;

    /**
     * reads everyline 
     * calculates the width (determines number of characters) 
     * height (determines how many lines of code)
     * Sends error message if file location is invalid
     * Size of the array is determined
     */
    public void initializeSlidingPuzzleArray() {
        String newData = "";
        int height = 0;
        int width = 0;
        try {
            File fileObject = new File(filesLocation);
            Scanner fileReader = new Scanner(fileObject);
            while (fileReader.hasNextLine()) {
                newData = fileReader.nextLine();
                height++;
            }
            width = newData.length();
            heightMax = height;
            widthMax = width;
            fileReader.close();
            slidingPuzzleArray = new Point[heightMax][widthMax]; //used to store the maze
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * reads through the entire file 
     * records an ID, x and y coordinates and Character of all the points
     * Start and End Points recorded based on the characters("S" / "F")
     * Points added to the array (slidingPuzzleArray)
     */
    public void fillSlidingPuzzleArray() {
        String data = "";
        int yCoordinates = 0;
        try {
            File fileObject = new File(filesLocation);
            Scanner fileReader = new Scanner(fileObject);
            int id = 0;
            while (fileReader.hasNextLine()) {
                data = fileReader.nextLine();
                for (int i = 0; i < data.length(); i++){
                    char c = data.charAt(i);
                    if (c == 'S') {//startpoint
                        startingPoint = new Point(id, i, yCoordinates, Character.toString(c));
                    }
                    if (c == 'F') {//finishpoint
                        finishingPoint = new Point(id, i, yCoordinates, Character.toString(c));
                    }
                    Point points = new Point(id, i, yCoordinates, Character.toString(c));
                    id++;
                    slidingPuzzleArray[yCoordinates][i] = points;
                }
                yCoordinates++;
            }
            fileReader.close();
            //System.out.println(Arrays.deepToString(slidingPuzzleArray).replace("], ", "]\n").replace("[[", "[").replace("]]", "]")); - view 2day array
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * searches through 2day array using the vertexid and return the point
     * @param vertexIDs
     * @return point
     */
    public Point getPointFromSlidingPuzzleArray(int vertexIDs) {
        //searches through 2day array using the vertexid and return the point
        for (int i = 0; i < slidingPuzzleArray.length; i++) {
            for (int j = 0; j < slidingPuzzleArray[i].length; j++) {
                if (slidingPuzzleArray[i][j].getPointID() == vertexIDs) {
                    return slidingPuzzleArray[i][j];
                }
            }
        }
        return null;
    }

    /**
     * searches through 2day array using the x and y coordinates and returns the point
     * @param x
     * @param y
     * @return point
     */
    public Point getPointFromSlidingPuzzleArray(int x, int y) {
        for (int i = 0; i < slidingPuzzleArray.length; i++) {
            for (int j = 0; j < slidingPuzzleArray[i].length; j++) {
                if (slidingPuzzleArray[i][j].getX() == x && slidingPuzzleArray[i][j].getY() == y) {
                    return slidingPuzzleArray[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Method to return the Start and Direction statements using point and direction parameters
     * @param point
     * @param direction
     * @return String
     */
    public String getDirectionDetails(Point point, String direction) {
        if (point.getCharacter().equals("S")) {
            return "Start at ("+point.getX()+","+point.getY()+")";
        } else {
            return "Move "+direction+" to ("+point.getX()+","+point.getY()+")";
        }
    }

    /**
     * passes graph created, prints the details of the directions in order from start to finish
     * @param graph
     */
    public void displayPathDetails(directedGraph graph) {
        Map<Integer, Integer> previousVerticesMap = graph.BreadthFirstTraversal(graph, startingPoint.getPointID(), finishingPoint.getPointID());
        List<Integer> finalPathList = graph.determinePathList(previousVerticesMap, startingPoint.getPointID(), finishingPoint.getPointID());
        if (finalPathList.isEmpty()) {
            System.out.println("There is no path!");
        } else {
            Point oldPoints = null;
            for (int i = 0; i < finalPathList.size(); i++) {
                String direction = "";
                Point points = getPointFromSlidingPuzzleArray(finalPathList.get(i));
                if (oldPoints != null) {
                    if (points.getY() == oldPoints.getY()) {
                        if (points.getX() > oldPoints.getX()) {
                            direction = "right";
                        } else {
                            direction = "left";
                        }
                    } else {
                        if (points.getY() > oldPoints.getY()) {
                            direction = "down";
                        } else {
                            direction = "up";
                        }
                    }
                }
                System.out.println((i+1)+". "+getDirectionDetails(points, direction));
                oldPoints = points;
                if (i == finalPathList.size()-1) {
                    System.out.println((finalPathList.size()+1) +". Done!");
                }
            }
        }
    }

    /**
     * Method to create the directed Graph
     * @param graph
     * @return
     */
    public directedGraph createDirectedGraph(directedGraph graph) {
        Stack<Integer> newStack = new Stack<Integer>();
        List<Integer> prevVisitedList = new ArrayList<>();
        Point oldPoints = startingPoint; 
        newStack.push(oldPoints.getPointID());
        graph.addVertex(oldPoints.getPointID());
        boolean pathIsPresent = false; //verify if graph has a path
        while (!newStack.isEmpty()) {
            int vertexIDs = newStack.pop();
            prevVisitedList.add(vertexIDs);
            oldPoints = getPointFromSlidingPuzzleArray(vertexIDs);
            if (finishVertexInPath(oldPoints)) {
                graph.addVertex(finishingPoint.getPointID());
                graph.addEdges(vertexIDs, finishingPoint.getPointID());
                pathIsPresent = true;
                newStack.clear();
                break;
            } else {
                if (canPointTravelNorth(oldPoints)) {
                    Point newPoint = travelToNorth(oldPoints);
                    if (!prevVisitedList.contains(newPoint.getPointID())) {
                        newStack.push(newPoint.getPointID());
                        graph.addVertex(newPoint.getPointID());
                        graph.addEdges(vertexIDs, newPoint.getPointID());
                    }
                }
                if (canPointTravelSouth(oldPoints)) {
                    Point newPoint = travelToSouth(oldPoints);
                    if (!prevVisitedList.contains(newPoint.getPointID())) {
                        newStack.push(newPoint.getPointID());
                        graph.addVertex(newPoint.getPointID());
                        graph.addEdges(vertexIDs, newPoint.getPointID());
                    }
                }
                if (canPointTravelEast(oldPoints)) {
                    Point newPoint = travelToEast(oldPoints);
                    if (!prevVisitedList.contains(newPoint.getPointID())) {
                        newStack.push(newPoint.getPointID());
                        graph.addVertex(newPoint.getPointID());
                        graph.addEdges(vertexIDs, newPoint.getPointID());
                    }
                }
                if (canPointTravelWest(oldPoints)) {
                    Point newPoint = travelToWest(oldPoints);
                    if (!prevVisitedList.contains(newPoint.getPointID())) {
                        newStack.push(newPoint.getPointID());
                        graph.addVertex(newPoint.getPointID());
                        graph.addEdges(vertexIDs, newPoint.getPointID());
                    }
                }
            }
        }
        if (pathIsPresent) {
            return graph;
        } else {
            return null;
        }
    }

    /**
     * passes a point
     * if can tarvel north then y value in the 2d array is decremented by 1 
     * then new point is obtained and is returned
     * @param point
     * @return newPoint
     */
    public Point travelToNorth(Point point) {
        Point newPoint = point;
        while (canPointTravelNorth(newPoint)) {
            newPoint = slidingPuzzleArray[newPoint.getY()-1][newPoint.getX()];
        }
        return newPoint;
    }

    /**
     * passes a point
     * if can tarvel south then y value in the 2d array is decremented by 1 
     * then new point is obtained and is returned
     * @param point
     * @return newPoint
     */
    public Point travelToSouth(Point point) {
        Point newPoint = point;
        while (canPointTravelSouth(newPoint)) {
            newPoint = slidingPuzzleArray[newPoint.getY()+1][newPoint.getX()];
        }
        return newPoint;
    }

    /**
     * passes a point
     * if can tarvel east then x value in the 2d array is decremented by 1 
     * then new point is obtained and is returned
     * @param point
     * @return newPoint
     */
    public Point travelToEast(Point point) {
        Point newPoint = point;
        while (canPointTravelEast(newPoint)) {
            newPoint = slidingPuzzleArray[newPoint.getY()][newPoint.getX()+1];
        }
        return newPoint;
    }

    /**
     * passes a point
     * if can tarvel west then x value in the 2d array is decremented by 1
     * then new point is obtained and is returned
     * @param point
     * @return newPoint
     */
    public Point travelToWest(Point point) {
        Point newPoint = point;
        while (canPointTravelWest(newPoint)) {
            newPoint = slidingPuzzleArray[newPoint.getY()][newPoint.getX()-1];
        }
        return newPoint;
    }

    /**
     * a point is passed (oldPoints) if the point's vertex is equal to finish vertex 
     * then the finish point is found
     * boolean value is returned
     * @param point
     * @return boolean
     */
    public boolean finishVertexInPath(Point point) {
        if (point.getPointID() == finishingPoint.getPointID()) {
            return true;
        }
        if (!(point.getX() == finishingPoint.getX() || point.getY() == finishingPoint.getY())) {
            return false;
        }
        int start;
        int end;
        //checks if a 0 is within path to Finish point 
        if (point.getX() == finishingPoint.getX()) {
            if (point.getY() < finishingPoint.getY()) {
                start = point.getY();
                end = finishingPoint.getY();
            } else {
                start = finishingPoint.getY();
                end = point.getY();
            }
            for (int i=start; i<=end; i++) {
                Point p = getPointFromSlidingPuzzleArray(finishingPoint.getX(), i);
                if (p.getCharacter().equals("0")) {
                    return false;
                }
            }
        } else {
            if (point.getX() < finishingPoint.getX()) {
                start = point.getX();
                end = finishingPoint.getX();
            } else {
                start = finishingPoint.getX();
                end = point.getX();
            }
            for (int i=start; i<=end; i++) {
                Point p = getPointFromSlidingPuzzleArray(i, finishingPoint.getY());
                if (p.getCharacter().equals("0")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * passes oldPoints(startingPoint)
     * checks if toppoint (y coordinate above) != 0 then can go up else can't go up 
     * @param point
     * @return
     */
    public boolean canPointTravelNorth(Point point) {
        if (point.getY() == 0) {
            return false;
        }
        if (!(slidingPuzzleArray[point.getY()-1][point.getX()].getCharacter().equals("0"))) {
            return true;
        }
        return false;
    }

    /**
     * passes oldPoints(startingPoint)
     * checks if belowpoint (y coordinate below) != 0 then can go down else can't go down
     * @param point
     * @return
     */
    public boolean canPointTravelSouth(Point point) {
        if (point.getY() == (heightMax - 1)) { 
            return false;
        }
        if (!(slidingPuzzleArray[point.getY()+1][point.getX()].getCharacter().equals("0"))) {
            return true;
        }
        return false;
    }

    /**
     * passes oldPoints(startingPoint)
     * checks if point to the right (x coordinate to the right) != 0 then can go to the right else can't go to the right
     * @param point
     * @return
     */
    public boolean canPointTravelEast(Point point) {
        if (point.getX() == (widthMax - 1)) {
            return false;
        }
        if (!(slidingPuzzleArray[point.getY()][point.getX()+1].getCharacter().equals("0"))) {
            return true;
        }
        return false;
    }

    /**
     * passes oldPoints(startingPoint)
     * checks if point to the left (x coordinate to the left) != 0 then can go to the left else can't go to the left
     * @param point
     * @return
     */
    public boolean canPointTravelWest(Point point) {
        if (point.getX() == 0) {
            return false;
        }
        if (!(slidingPuzzleArray[point.getY()][point.getX()-1].getCharacter().equals("0"))) {
            return true;
        }
        return false;
    }

    /**
     * getter method to return the slidingPuzzleArray
     * @return
     */
    public Point[][] getSlidingPuzzleArray() {
        return slidingPuzzleArray;
    }

    /**
     * setter method to set the slidingPuzzleArray
     * @param slidingPuzzleArray
     */
    public void setSlidingPuzzleArray(Point[][] slidingPuzzleArray) {
        this.slidingPuzzleArray = slidingPuzzleArray;
    }

    /**
     * getter method to return the startingPoint
     * @return
     */
    public Point getStartingPoint() {
        return startingPoint;
    }

    /**
     * setter method to set the startingPoint
     * @param startingPoint
     */
    public void setStartingPoint(Point startingPoint) {
        this.startingPoint = startingPoint;
    }

    /**
     * getter method to return the finishingPoint
     * @return
     */
    public Point getFinishingPoint() {
        return finishingPoint;
    }

    /**
     * setter method to set the finishingPoint
     * @param finishingPoint
     */
    public void setFinishingPoint(Point finishingPoint) {
        this.finishingPoint = finishingPoint;
    }

    /**
     * getter method to return the filesLocation
     * @return
     */
    public String getFilesLocation() {
        return filesLocation;
    }

    /**
     * setter method to set the fileLocation
     * @param fileLocation
     */
    public void setFilesLocation(String filesLocation) {
        this.filesLocation = filesLocation;
    }

}
