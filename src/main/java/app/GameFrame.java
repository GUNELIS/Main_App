package app;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Random;

public class GameFrame extends JFrame {
    private Scenario scenario ;
    private JPanel panelWest ;
    private JButton buttonPlay ;
    private JButton buttonPause ;
    private JButton buttonExit ;
    public double discoverd;
    public MapPanel mapPanel ;
    public JLabel label ;
    private DecimalFormat df ;
    private MapArray sharred ;
    int numm ;
    private double z ;
    private testframe testframe;



    public GameFrame(String biome){
        scenario = new Scenario("testmap.txt") ;
        //scenario = new Scenario("examinermap_phase1.txt") ;
        //this.menuFrame = menuFrame;
        sharred = new MapArray(scenario,this) ;
        df = new DecimalFormat("0.00") ;

        label = new JLabel(" 0%") ;
        label.setForeground(Color.green);
        label.setFont(new Font("Verdana", Font.PLAIN, 18));


        setTitle("Game Frame");
        setSize(scenario.mapWidth*10, scenario.mapHeight*10);
        setLayout(new BorderLayout());

        long seed =100;
        Random rand = new Random(seed);
        z = rand.nextDouble();
        if (biome== "GREEK"){
            mapPanel = new MapPanel(scenario,this,"GREEK",scenario.mapWidth,scenario.mapHeight,z );
        }
        if (biome== "SAHARA"){
            mapPanel = new MapPanel(scenario,this,"SAHARA",scenario.mapWidth,scenario.mapHeight,z );
        }
        ;
        add(BorderLayout.CENTER,mapPanel) ;
        createPanelWest();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);


    }

    public MapArray getSharred() {
        return sharred;
    }

    public Scenario getScenario(){return scenario ; }


    private void createPanelWest() {
        panelWest = new JPanel();
        panelWest.setBackground(new Color(36, 95, 131));
        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.PAGE_AXIS));


        buttonPlay = createButtonPlay();
        buttonPause = createButtonBreak();
        panelWest.add(buttonPlay);
        panelWest.add(buttonPause);
        panelWest.add(label) ;
        // panelWest.add(buttonExit);
        add(BorderLayout.WEST, panelWest);
    }

    private JButton createButtonPlay() {

        buttonPlay = new JButton("Play");
        buttonPlay.addActionListener(e -> {this.mapPanel.changePlayToTrue();
        this.mapPanel.getTimer().start();});
        return buttonPlay;

    }

    private JButton createButtonBreak() {
        buttonPause = new JButton("Pause");
      //  buttonPause.setSize(150, 18);
        buttonPause.addActionListener(e -> this.mapPanel.changePlayToFalse());
        return buttonPause;
    }

    public void  setLabel(double num, double total){

            numm = 0 ;
        for (int i = 0; i < sharred.getMapArray().length; i++) {
            for (int j = 0; j < sharred.getMapArray()[0].length; j++) {
                if(sharred.getMapArray()[i][j].isLegal()&&sharred.getMapArray()[i][j].isVisited())
                    numm = numm+1 ;
            }

        }
        discoverd = numm/total*100;
        label.setText(" "+df.format(numm/total*100)+"%");
        label.repaint();

    }
    private JButton createButtonExit() {
        buttonExit = new JButton("Exit");

        buttonExit.setSize(150, 18);
        buttonExit.setForeground(Color.BLACK);
        buttonExit.setFont(new Font("Dialog", Font.PLAIN, 15));
        buttonExit.setLocation(140, 150);
        buttonExit.addActionListener(e -> {
            setVisible(false);
            testframe.setVisible(true);
        });

        return buttonExit;
    }

}
