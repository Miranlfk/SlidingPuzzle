import java.util.*;


public class directedGraph {
    private Map<Vertices, List<Vertices>> adjacentVertices = new HashMap<>();

    /**
     * COnstructor of directedGraph
     */
    public directedGraph() {}

    /**
     * Parametrized Constructor for directedGrapgh 
     * @param adjVertices
     */
    public directedGraph(Map<Vertices, List<Vertices>> adjVertices) {
        this.adjacentVertices = adjVertices;
    }

    /**
     * Map method used to return the vertex and list of adjacent vertices
     * @return
     */
    public Map<Vertices, List<Vertices>> getAdjVertices() {
        return adjacentVertices;
    }

    
    /**
     * setter method used to set the adjvertices hashMap
     * @param adjVertices
     */
    public void setAdjacentVertices(Map<Vertices, List<Vertices>> adjVertices) {
        this.adjacentVertices = adjVertices;
    }

    /**
     * method used to add the vertex
     * @param labels
     */
    public void addVertex(int labels) {
        adjacentVertices.putIfAbsent(new Vertices(labels), new ArrayList<>());
    }

    /**
     * method used to add the edge using 2 vertices
     * @param label1
     * @param label2
     */
    public void addEdges(int label1, int label2) {
        Vertices vertex1 = new Vertices(label1);
        Vertices vertex2 = new Vertices(label2);
        adjacentVertices.get(vertex1).add(vertex2);
    }

    /**
     * getter method used to return adjVertices based on vertex label
     * @param labels
     * @return
     */
    public List<Vertices> getAdjacentVertices(int labels) {
        return adjacentVertices.get(new Vertices(labels));
    }

    /**
     * BreadthFirstTraversal is the algorithm used to find the shortest path in terms of gragh
     * this algorithm is modified to return the path
     * @param graph
     * @param startID
     * @param endID
     * @return
     */
    public Map<Integer, Integer> BreadthFirstTraversal(directedGraph graph, int startID, int endID) {
        Set<Integer> pointVisited = new LinkedHashSet<Integer>();
        Queue<Integer> pointsQueue = new LinkedList<Integer>();
        Map<Integer, Integer> previousVerticesMap = new HashMap<>();
        pointsQueue.add(startID);
        pointVisited.add(startID);
        previousVerticesMap.put(startID, null);
        while (!pointsQueue.isEmpty()) {
            int vertices = pointsQueue.poll();//start point id
            for (Vertices vertex : graph.getAdjacentVertices(vertices)) { //get the vertices that connect to start id it will be a list
                if (!pointVisited.contains(vertex.getLabels())) { //check if that vertex has been visited
                    previousVerticesMap.put(vertex.getLabels(), vertices);
                    pointVisited.add(vertex.getLabels()); //add to visited if not visited
                    pointsQueue.add(vertex.getLabels()); //add to queue
                }
            }
        }
        //System.out.println(previousVerticesMap);
        return previousVerticesMap;
    }

    /**
     * method used to determine the path using previousVerticesMap(Map), startID, endID
     * the endID is recorded in the list
     * Collections.reverse() method used to reverse the list providing list from start to finish
     * list is returned
     * @param previousVerticesMap
     * @param startID
     * @param endID
     * @return
     */
    public List<Integer> determinePathList(Map<Integer, Integer> previousVerticesMap, int startID, int endID) {
        int NewID = -1;
        List<Integer> finalPathList = new ArrayList<>();
        finalPathList.add(endID);
        while (NewID != startID) {
            NewID = previousVerticesMap.get(endID);
            finalPathList.add(NewID);
            endID = NewID;
        }
        //System.out.println(finalPathList);
        Collections.reverse(finalPathList);
        //System.out.println(finalPathList);
        return finalPathList;
    }

}

