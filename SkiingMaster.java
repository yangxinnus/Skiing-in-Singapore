package test.xiyang;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Using Recursive call to find the best rout of skiing given 2d map.
 * The main approach is to sacrifice some space for the speed. That is,
 * a 2D shadow array of type Pair is created to captured the visited place information.
 * So that we make sure the program can finish in O(N^2) time complexity.
 * 
 * From the sample request of 1000*1000 map, the program finishes in 0.163 seconds.
 */
public class SkiingMaster {

    private static int[][] map;
    private static Pair[][] flags;
    private static int ROW = 0;

    private static int COLUMN = 0;
    private static int MAXIMUM_STEPS = 0;
    private static int MAXIMUM_ABSOLUTE_DISTANCE = 0;

    public static void setUpMap() {
        populateMap();
        initializeFlags();
    }

    public static void run() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                findOptimizedPath(i, j);
            }
        }
    }

    /**
     * @param row
     * @param column
     * @return Pair, the left indicates the maximum steps that can happen from the current place (including the current place),
     *               the right one is the lowest place on this route that gives you the above maximum steps
     */
    private static Pair findOptimizedPath(int row, int column) {

        Pair maxStepsAndAbolulateDistance = Pair.of(0, 0);
        //Base
        boolean reachedLowestPoint = true;
        if (row + 1 < ROW && map[row][column] > map[row + 1][column]) {//down - SOUTH
            reachedLowestPoint = false;
            Pair down = getSubValue(row + 1, column);
            //process neighbor
            updateMaxStepsAndAbsoluteDistance(row, column, down, maxStepsAndAbolulateDistance);

        }
        if (row - 1 >= 0 && map[row][column] > map[row - 1][column]) {//up - NORTH
            reachedLowestPoint = false;
            Pair up = getSubValue(row - 1, column);
            //process neighbor
            updateMaxStepsAndAbsoluteDistance(row, column, up, maxStepsAndAbolulateDistance);

        }
        if (column + 1 < COLUMN && map[row][column] > map[row][column + 1]) {//right - EAST
            reachedLowestPoint = false;
            Pair right = getSubValue(row, column + 1);
            //process neighbor
            updateMaxStepsAndAbsoluteDistance(row, column, right, maxStepsAndAbolulateDistance);
        }
        if (column - 1 >= 0 && map[row][column] > map[row][column - 1]) {//left - WEST
            reachedLowestPoint = false;
            Pair left = getSubValue(row, column - 1);
            //process neighbor
            updateMaxStepsAndAbsoluteDistance(row, column, left, maxStepsAndAbolulateDistance);
        }

        return aggregateLocationDetails(reachedLowestPoint, row, column, maxStepsAndAbolulateDistance.getLeft(),
                maxStepsAndAbolulateDistance.getRight());
    }

    private static void updateMaxStepsAndAbsoluteDistance(int row, int column, Pair neighbor,
            Pair maxStepsAndAbsolulateDistnce) {
        int maxSteps = maxStepsAndAbsolulateDistnce.getLeft();
        int maxAbsoluteDistance = maxStepsAndAbsolulateDistnce.getRight();
        if (maxSteps == 1 + neighbor.getLeft()) {
            if (map[row][column] - neighbor.getRight() > maxAbsoluteDistance) {
                maxAbsoluteDistance = map[row][column] - neighbor.getRight();
            }
        } else if (maxSteps < 1 + neighbor.getLeft()) {
            maxSteps = 1 + neighbor.getLeft();
            maxAbsoluteDistance = map[row][column] - neighbor.getRight();
        }
        maxStepsAndAbsolulateDistnce.setLeft(maxSteps);
        maxStepsAndAbsolulateDistnce.setRight(maxAbsoluteDistance);
    }

    /**
     * when all the left/right/up/down info is processed, 
     * this function is to update the in progress data
     * @param isReachedLowestPoint - indicate whether the current point is the lowest point
     * @param row
     * @param column
     * @param maxSteps - the max steps that can be reached from this location
     * @param maxAbsoluteDistance - the max absolute distance corresponding to that route that gives you maximum route.
     * @return
     */
    private static Pair aggregateLocationDetails(boolean isReachedLowestPoint, int row, int column, int maxSteps,
            int maxAbsoluteDistance) {
        if (true == isReachedLowestPoint) {
            flags[row][column] = Pair.of(1, map[row][column]);
            return Pair.of(1, map[row][column]);
        } else {
            if (MAXIMUM_STEPS == maxSteps) {
                if (MAXIMUM_ABSOLUTE_DISTANCE < maxAbsoluteDistance) {
                    MAXIMUM_ABSOLUTE_DISTANCE = maxAbsoluteDistance;
                }
            } else if (MAXIMUM_STEPS < maxSteps) {
                MAXIMUM_STEPS = maxSteps;
                MAXIMUM_ABSOLUTE_DISTANCE = maxAbsoluteDistance;
            }
            flags[row][column] = Pair.of(maxSteps, map[row][column] - maxAbsoluteDistance);
            return Pair.of(maxSteps, map[row][column] - maxAbsoluteDistance);
        }
    }

    private static Pair getSubValue(int row, int column) {
        // when it is already visited, we can use the information already
        if (flags[row][column].getLeft() != -1) {
            return Pair.of(flags[row][column].getLeft(), flags[row][column].getRight());
        } else {
            return findOptimizedPath(row, column);
        }
    }

    /**
     * Read the data from console and build the map
     */
    private static void populateMap() {
        Scanner scanner = new Scanner(System.in);
        ROW = scanner.nextInt();
        COLUMN = scanner.nextInt();
        scanner.nextLine();
        map = new int[ROW][COLUMN];
        flags = new Pair[ROW][COLUMN];
        for (int i = 0; i < ROW; i++) {
            // create each row at a time
            populateRow(i, scanner.nextLine());
        }
        initializeFlags();
    }

    private static void populateRow(int row, String input) {
        StringTokenizer st = new StringTokenizer(input);
        int i = 0;
        while (st.hasMoreElements()) {
            map[row][i] = new Integer(st.nextToken()).intValue();
            i++;
        }
    }

    /**
     * initialized the visited location information.
     */
    private static void initializeFlags() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                flags[i][j] = Pair.of(-1, -1);
            }
        }
    }

    public static int getMAXIMUM_STEPS() {
        return MAXIMUM_STEPS;
    }

    public static void setMAXIMUM_STEPS(int mAXIMUM_STEPS) {
        MAXIMUM_STEPS = mAXIMUM_STEPS;
    }

    public static int getMAXIMUM_ABSOLUTE_DISTANCE() {
        return MAXIMUM_ABSOLUTE_DISTANCE;
    }

    public static void setMAXIMUM_ABSOLUTE_DISTANCE(int mAXIMUM_ABSOLUTE_DISTANCE) {
        MAXIMUM_ABSOLUTE_DISTANCE = mAXIMUM_ABSOLUTE_DISTANCE;
    }

}
