package playground.algorithms.miscellanea;

/**
 * Given a 2D matrix of integers, where 0 is traversable and 1 is not, find the shortest path from the top left
 * corner to the bottom right corner.  Function should take in a matrix, and return a list of coordinates of the
 * shortest path.
 *
 * For example, for the following board:
 * 0 0 0 0 0
 * 0 0 0 0 1
 * 1 1 0 1 0
 * 0 0 0 0 0 -> exit
 *
 * This is a possible path
 * X X X X 0
 * 0 0 X X 0
 * 1 1 X 1 0
 * 0 0 X X X -> exit
 *
 * But this is the shortest path:
 *
 * X X X 0 0
 * 0 0 X 0 1
 * 1 1 X 1 0
 * 0 0 X X X -> exit
 *
 * [(0, 0), (0, 1), (0, 2), (1, 2), (2, 2), (3, 2), (3, 3), (3, 4)]
 */
public class ShortestPathInA2DMatrixOfIntegers {
    public void run() throws Exception {
    }
}
