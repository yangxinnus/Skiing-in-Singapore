package test.xiyang;

/**
 * This is the main class for testing the SkiingMaster
 */
public class Main {

    public static void main(String[] args) {

        //setup
        SkiingMaster.setUpMap();

        long startTime = System.currentTimeMillis();
        //run searching algorithm
        SkiingMaster.run();
        long endTime = System.currentTimeMillis();

        System.out.println("Done in " + (double) (endTime - startTime) / 1000 + "seconds!");
        System.out.println("MAXIMUM_STEPS = " + SkiingMaster.getMAXIMUM_STEPS());
        System.out.println("MAXIMUM_ABSOLUTE_DISTANCE = " + SkiingMaster.getMAXIMUM_ABSOLUTE_DISTANCE());
    }
}
