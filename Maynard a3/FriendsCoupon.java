/**
 * Created by Lenny on 11/7/15.
 */

import java.util.*;
import java.io.*;

public class FriendsCoupon {

    static int[][] friendship;
    static int numOfCoupons;

    /**
     * Checks if a partial solution is a complete and valid solution.
     * @param partial The partial solution
     * @return true if the partial solution is complete and valid, false otherwise.
     */
    public static boolean isFullSolution(int[] partial) {
        // Check that everyone has a coupon
        for (int i = 0; i < friendship.length; i++) {
            if (partial[i] == 0) {
                return false;
            }
        }
        // The solution is known to be complete, check if it is valid
        if (reject(partial)) {
            return false;
        }
        // The solution is complete and valid
        return true;
    }


    /**
     * Checks if a partial solution should be rejected because it can never be extended to a
     * complete solution.
     * @param partial The partial solution
     * @return true if the partial solution should be rejected, false otherwise.
     */
    public static boolean reject(int[] partial) {
        for(int i = 0; i < friendship.length; i++){
            for(int j = 0; j < friendship.length; j++){
                //if the two people are friends and have the same coupon, we should reject
                if(friendship[i][j] == 1 && partial[i] == partial[j] && partial[i] != 0) return true;
            }
        }

        // No pair of friends were in conflict, so we should not reject
        return false;
    }


    /**
     * Extends a partial solution by distributing one additional coupon.
     * @param partial The partial solution
     * @return a partial solution with one more coupon added, or null if no coupon can be added.
     */
    public static int[] extend(int[] partial) {
        // Initialize the new partial solution
        int[] temp = new int[partial.length];
        for (int i = 0; i < partial.length; i++) {
            if (partial[i] != 0) {
                // Copy each coupon that has been distributed
                temp[i] = partial[i];
            } else {
                // Give a coupon to the first person without one
                temp[i] = 1;
                return temp;
            }
        }
        // If we reached this point, everyone already has a coupon, so we
        // cannot extend. Return null.
        return null;
    }


    /**
     * Changes the most recently given coupon to the next coupon.
     * @param partial The partial solution
     * @return a partial solution with the most recently given coupon changed to the next value,
     * or null if we are out of options for the most recent person.
     */
    public static int[] next(int[] partial) {
        int[] temp = new int[partial.length];
        int i = 0;
        while (i < partial.length) {
            if (i == partial.length-1 || partial[i+1] == 0) {
                // The most recently given coupon is the last person, or the last one with a coupon
                if (partial[i] >= numOfCoupons) {
                    // The person has the final coupon, so we cannot try anything else
                    return null;
                } else {
                    // Give the person the next coupon
                    temp[i] = partial[i] + 1;
                    break;
                }
            } else {
                // This isn't the last person, so just copy it
                temp[i] = partial[i];
            }
            i++;
        }
        return temp;
    }





    /**
     * Tests isFullSolution using a partial solution.
     * @param test The partial solution to test
     */
    static void testIsFullSolutionUnit(int[] test) {
        if (isFullSolution(test)) {
            System.err.println("Full sol'n:\t" + Arrays.toString(test));
        } else {
            System.err.println("Not full sol'n:\t" + Arrays.toString(test));
        }
    }

    /**
     * Tests the isFullSolution method using several partial solutions.
     */
    public static void testIsFullSolution() {
        System.err.println("Testing isFullSolution()");

        // Full solutions:
        testIsFullSolutionUnit(new int[] {1, 2, 1, 1, 2, 3});
        testIsFullSolutionUnit(new int[] {1, 2, 1, 3, 4, 3});
        testIsFullSolutionUnit(new int[] {1, 2, 3, 4, 5, 6});


        // Not full solutions:
        testIsFullSolutionUnit(new int[] {0, 0, 0, 0, 0, 0});
        testIsFullSolutionUnit(new int[] {1, 2, 1, 1, 2, 0});
        testIsFullSolutionUnit(new int[] {1, 1, 1, 1, 2, 3});

    }








    /**
     * Tests reject using a partial solution.
     * @param test The partial solution to test
     */
    static void testRejectUnit(int[] test) {
        if (reject(test)) {
            System.err.println("Rejected:\t" + Arrays.toString(test));
        } else {
            System.err.println("Not rejected:\t" + Arrays.toString(test));
        }
    }

    /**
     * Tests the reject method using several partial solutions.
     */
    public static void testReject() {
        System.err.println("Testing reject()");

        // Should not be rejected:
        testRejectUnit(new int[] {0, 1, 2, 3, 4, 5});
        testRejectUnit(new int[] {1, 2, 1, 1, 2, 3});
        testRejectUnit(new int[] {1, 2, 1, 0, 0, 0});


        // Should be rejected:
        testRejectUnit(new int[]{1, 1, 1, 1, 1, 1});
        testRejectUnit(new int[] {1, 1, 3, 2, 2, 1});
    }

    /**
     * Tests extend using a partial solution.
     * @param test The partial solution to test
     */
    static void testExtendUnit(int[] test) {
        System.err.println("Extended " + Arrays.toString(test) + " to " + Arrays.toString(extend(test)));
    }

    /**
     * Tests the extend method using several partial solutions.
     */
    public static void testExtend() {
        System.err.println("Testing extend()");

        // Cannot be extended:
        testExtendUnit(new int[] {1, 2, 1, 3, 4, 3});
        testExtendUnit(new int[] {1, 1, 1, 1, 1, 1});
        testExtendUnit(new int[] {1, 1, 3, 2, 2, 1});

        // Can be extended:
        testExtendUnit(new int[] {0, 0, 0, 0, 0, 0});
        testExtendUnit(new int[] {1, 1, 1, 1, 1, 0});
        testExtendUnit(new int[] {1, 1, 3, 2, 2, 0});
    }


    /**
     * Tests next using a partial solution.
     * @param test The partial solution to test
     */
    static void testNextUnit(int[] test) {
        System.err.println("Nexted " + Arrays.toString(test) + " to " + Arrays.toString(next(test)));
    }

    /**
     * Tests the next method using several partial solutions.
     */
    public static void testNext() {
        System.err.println("Testing next()");
        System.out.println("Number of different coupons: 3");
        numOfCoupons = 3;

        // Cannot be next'd:
        testNextUnit(new int[] {1, 3, 2, 1, 3, 0});
        testNextUnit(new int[] {2, 1, 3, 2, 1, 3});

        // Can be next'd:
        testNextUnit(new int[] {2, 1, 3, 2, 1, 2});
        testNextUnit(new int[] {0, 0, 0, 0, 0, 0});
        testNextUnit(new int[] {2, 1, 3, 2, 0, 0});
        testNextUnit(new int[] {3, 3, 3, 3, 3, 2});

    }




    /**
     * Prints the solution by assigning numbered people to lettered coupons
     * @param sol The solution to print
     */
    public static void printSolution(int[] sol){
        for(int i = 0; i < sol.length; i++){
            System.out.println("Person "+(i+1)+" gets coupon "+Character.toString((char) (64+sol[i])));
        }
    }









    /**
     * Finds a way to correctly distribute coupons and prints a solution
     * @param partial The partial solution
     */
    public static void solve(int[] partial) {
        if (reject(partial)) return;
        if (isFullSolution(partial)){
            printSolution(partial);
            System.exit(0);
        }
        int[] attempt = extend(partial);
        while (attempt != null) {
            solve(attempt);
            attempt = next(attempt);
        }
    }





    public static void main (String[] args)throws IOException{
        if (args.length >= 1 && args[0].equals("-t")) {
            readGrid("test.txt");
            System.out.println("Array position represents each person, 0 means no coupon, nums 1+ represent different coupons");
            testReject();
            testIsFullSolution();
            testExtend();
            testNext();
        } else {
            readGrid(args[0]);
            numOfCoupons = Integer.parseInt(args[1]);

            int[] start = new int[friendship.length];
            for (int i = 0; i < friendship.length; i++) start[i] = 0;
            solve(start);
            System.out.print("No solution found");
        }

    }

    /**
     * Reads in a text file containing a grid and produces a
     * two-dimensional array representing friend relationships
     * @param filename The name of the file containing a grid
     */
    public static void readGrid(String filename){
        Scanner fReader;
        File fName;

        // Make sure the file name is valid
        try
        {
            fName = new File(filename);
            fReader = new Scanner(fName);
        }
        catch (IOException e)
        {
            System.out.println("File not found");
            return;
        }


        String[] line = fReader.nextLine().split(" ");
        friendship = new int[line.length][line.length];
        for(int i =0; i<line.length; i++){
            int x = Integer.parseInt(line[i]);
            friendship[0][i] = x;
        }
        int counter =1;
        while(fReader.hasNext()){
            line = fReader.nextLine().split(" ");
            for(int i =0; i<line.length; i++){
                int x = Integer.parseInt(line[i]);
                friendship[counter][i] = x;
            }
            counter++;
        }

        //check for error if a person is friends with themselves
        for(int i = 0; i < friendship.length; i++){
            if(friendship[i][i] == 1){
                System.out.print("ERROR: Person "+(i+1)+" is friends with themselves");
                System.exit(0);
            }
        }
    }


}
