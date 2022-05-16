package app;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class testrunner {

    static double total;
    static int numm;
    static double discoverd;
    static long[] start;
    static long[] timer_finised;
    static long[] average_time_move;
    static int[] taken_moves;
    static int iterations = 10;
    static long[] elepsed_time;
    static boolean running;
    static private MapArray sharred;
    private static Scenario scenario;

    public static void discoverytimer() throws IOException {
        start = new long[iterations];
        timer_finised = new long[iterations];
        elepsed_time = new long[iterations];
        taken_moves = new int[iterations];
        int iter_counter = -1;

        for (int k = 0; k < iterations; k++) {
            running = true;
            iter_counter = iter_counter + 1;
            start[iter_counter] = System.currentTimeMillis();
            testframe testframe = new testframe();

            while (running == true) {
                discoverd = testframe.gameFrame.discoverd;

                if (running == true) {
                    //  System.out.println("Discoverd terrain : " + discoverd + " %    iteration: "+ (iter_counter+1));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                if (discoverd > 99) {
                    System.out.println("last discovery :" + discoverd + " %");

                    timer_finised[iter_counter] = System.currentTimeMillis();
                    elepsed_time[iter_counter] = (timer_finised[iter_counter] - start[iter_counter]);
                    taken_moves[iter_counter] = testframe.gameFrame.mapPanel.count;
                    BigSquares.setMove_count_Zero();
                    MoveRandom.setCount_move_Zero();
                    BrickMortar.setCounter_Zero();

                    System.out.println("moves taken till finished " + taken_moves[iter_counter]);
                    //average_time_move[iter_counter] = (elepsed_time[iter_counter] / taken_moves[iter_counter]);
                    System.out.println("average time per move : " + (elepsed_time[iter_counter] / taken_moves[iter_counter]));
                    testframe.gameFrame.mapPanel.changePlayToFalse();

                    testframe.gameFrame.dispose();
                    testframe.dispose();

                    // testframe.gameFrame.dispatchEvent(new WindowEvent(testframe.gameFrame, WindowEvent.WINDOW_CLOSING));
                    running = false;

                    if (iter_counter % 1 == 0) {
                        System.out.println(("elapsed time : " + elepsed_time[iter_counter]));
                        System.out.println("-----------------------------------------------     iteration: " + (iter_counter + 1));
                    }
                    FileWriter writer = new FileWriter("demo.txt");
                    int len = elepsed_time.length;
                    for (int i = 0; i < len; i++) {
                        writer.write("iteration :  " + (iter_counter + 1) + "  elapsed time : " + elepsed_time[iter_counter] + "\t" + " | ");
                        writer.write("iteration :  " + (iter_counter + 1) + "  moves taken : " + taken_moves[iter_counter] + "\t" + " | ");
                    }
                    writer.close();


                }
            }


        }
    }


    public static void main(String[] args) throws IOException {
        discoverytimer();
        System.out.println("ELAPSED TIME (miliseconds) : " + Arrays.toString(elepsed_time));
        System.out.println("MOVES TAKEN : " + Arrays.toString(taken_moves));

        System.exit(0);


    }

}
/**
 * >99% coverage
 * ELEPSED TIME (miliseconds) : [111140, 268043, 108369, 239939, 232679, 219493, 175807, 115973, 84177, 143846]
 * MOVES TAKEN : [5590, 11639, 5484, 10549, 10348, 9721, 8134, 6069, 4396, 6831]
 */