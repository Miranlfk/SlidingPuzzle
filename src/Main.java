//Miran Kur
public class Main {

    /**
     * carries out the functionality of the application
     * new puzzle object made, file location set, puzzle array will be initialized and filled 
     * graph object made and graph will be created 
     * path details to be printed using the graph 
     * @param args
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        //hashmap - to store possible paths, 2d array - to store the puzzle, arraylist - to check visited or not, queue is for bfs, stack to add the vertex into a stack and loop 
        SlidingPuzzle puzzle = new SlidingPuzzle(); 
        puzzle.setFilesLocation("src/mazes_and_puzzles/puzzle_320.txt");
        puzzle.initializeSlidingPuzzleArray(); 
        puzzle.fillSlidingPuzzleArray();
        directedGraph graph = new directedGraph();
        graph = puzzle.createDirectedGraph(graph);
        puzzle.displayPathDetails(graph);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Execution time in milliseconds  : " + totalTime);

    }


}
