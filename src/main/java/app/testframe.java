package app;

import javax.swing.*;
import java.util.Random;

public class testframe extends JFrame {
    public static GameFrame gameFrame;
    static MapArray sharred;
    private static Scenario scenario;
    private final int scale = 10;
    private final double z;
    private final int height;
    private final int width;


    public testframe() {

        long seed = 13515420;
        // long seed =1111111;
        Random rand = new Random(seed);


        MapPanel.setMili_sec_per_move(10);
        scenario = new Scenario("testmap.txt");

        height = scenario.mapHeight;
        System.out.println(height);
        width = scenario.mapWidth;
        z = rand.nextDouble();
        gameFrame = new GameFrame("GREEK");
        gameFrame.mapPanel.changePlayToTrue();
        sharred = new MapArray(scenario, gameFrame);
        gameFrame.repaint();
        gameFrame.setVisible(true);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static double getdiscoverd() {
        double total;
        int numm;

        total = sharred.getTotal();
        numm = 0;
        for (int i = 0; i < sharred.getMapArray().length; i++) {
            for (int j = 0; j < sharred.getMapArray()[0].length; j++) {
                if (sharred.getMapArray()[i][j].isLegal() && sharred.getMapArray()[i][j].isVisited())
                    numm = numm + 1;
            }

        }
        double precent = numm / total * 100;
        return precent;
    }
}


