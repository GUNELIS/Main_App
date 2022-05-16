package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class MenuFrame extends JFrame{
    private Scenario scenario;
    private int scale = 10;
    private final ImageIcon image ;
    private final ImageIcon image2 ;
    private JLabel bg ;
    private JLabel label1;
    private JPanel panel1;
    private JCheckBox bMusic ;
    private JButton buttonStart ;
    private JRadioButton r1 ;
    private JRadioButton r2 ;
    private ButtonGroup buttonGroup ;
    private GameFrame gameFrame ;
    private double z ;
    private int height ;
    private int width ;
    private int ggfsw ;

    private JComboBox<String>comboBox ;
    private JComboBox<String>comboBoxspeed ;


    java.util.List<Point> snow ;
    java.util.List<Point> forest ;
    java.util.List<Point> hills ;
    java.util.List<Point> Mountains ;
    java.util.List<Point> Desert  ;
    java.util.List<Point> Lake  ;
    List<Point> plains ;


    public MenuFrame(){

        long seed = 13515420;
        // long seed =1111111;
        Random rand = new Random(seed);



        setTitle("Main Menu");
        setSize(120*scale, 80*scale);
        setLayout(new BorderLayout());


        image = new ImageIcon(new ImageIcon("assets/img.jpeg").getImage().getScaledInstance(120*10, 80*10, Image.SCALE_DEFAULT));
        image2= new ImageIcon(new ImageIcon("assets/img2.jpeg").getImage().getScaledInstance(250,20,Image.SCALE_DEFAULT));

        bg= new JLabel(image);
        bg.setLayout(null);

        bMusic = new JCheckBox("Music") ;
        bMusic.setBounds(140,150,80,20);
        bMusic.setFont(new Font("Dialog", Font.PLAIN,17));
        bMusic.setLocation(10, 10);
        bMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bMusic.isSelected())
                {
                    System.out.println("Music is supposed to play");
                }
            }
        });

        MapFiles mapFiles = new MapFiles() ;

        comboBox = new JComboBox<String>(mapFiles.getFiles()) ;
        comboBox.setSize(150,22);
        comboBox.setLocation(getWidth()/2-200,getHeight()/2+170);


        String[] values = { "10", "25", "50", "75", "100" };
        comboBoxspeed = new JComboBox(values);
        comboBoxspeed.setSelectedIndex(4);

        comboBoxspeed.setSize(150,22);
        comboBoxspeed.setLocation(getWidth()/2,getHeight()/2);
        label1 = new JLabel("Select movement speed:");
        panel1 = new JPanel(new FlowLayout());
        panel1.setLocation(getWidth()/2,getHeight()/2);
        panel1.add(label1);
        panel1.add(comboBoxspeed);
        panel1.setVisible(true);










        buttonStart = new JButton("Start the game",image2) ;
        buttonStart.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonStart.setSize(150, 22);
        buttonStart.setForeground(Color.BLACK);
        buttonStart.setFont(new Font("Dialog", Font.PLAIN,15));
        buttonStart.setLocation(getWidth()/2,getHeight()/2+170);



        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(r1.isSelected()) {
                    if (comboBoxspeed.getSelectedItem() == "10"){
                        MapPanel.setMili_sec_per_move(10);
                    }
                    if (comboBoxspeed.getSelectedItem() == "25"){
                        MapPanel.setMili_sec_per_move(25);
                    }
                    if (comboBoxspeed.getSelectedItem() == "50"){
                        MapPanel.setMili_sec_per_move(50);
                    }
                    if (comboBoxspeed.getSelectedItem() == "75"){
                        MapPanel.setMili_sec_per_move(75);
                    }
                    if (comboBoxspeed.getSelectedItem() == "100"){
                        MapPanel.setMili_sec_per_move(100);
                    }

                    scenario = new Scenario((String) comboBox.getSelectedItem()) ;
                    height = scenario.mapHeight ;
                    System.out.println(height);
                    width = scenario.mapWidth ;
                    z = rand.nextDouble();
                    gameFrame = new GameFrame("GREEK");

                 //  gameFrame.getContentPane().add(gameFrame) ;
                   gameFrame.repaint();
                    setVisible(false);
                    gameFrame.setVisible(true);

                }
                if(r2.isSelected()){
                    if (comboBoxspeed.getSelectedItem() == "10"){
                        MapPanel.setMili_sec_per_move(10);
                    }
                    if (comboBoxspeed.getSelectedItem() == "25"){
                        MapPanel.setMili_sec_per_move(25);
                    }
                    if (comboBoxspeed.getSelectedItem() == "50"){
                        MapPanel.setMili_sec_per_move(50);
                    }
                    if (comboBoxspeed.getSelectedItem() == "75"){
                        MapPanel.setMili_sec_per_move(75);
                    }
                    if (comboBoxspeed.getSelectedItem() == "100"){
                        MapPanel.setMili_sec_per_move(100);
                    }

                    scenario = new Scenario((String) comboBox.getSelectedItem()) ;
                    height = scenario.mapHeight ;
                    width = scenario.mapWidth ;
                    z = rand.nextDouble();
                    gameFrame = new GameFrame("SAHARA");

                  //  gameFrame.getContentPane().add(gameFrame) ;
                    gameFrame.repaint();
                    setVisible(false);
                    gameFrame.setVisible(true);
                   /* gameFrame = new GameFrame(scenario,SHARA.BIOME,MenuFrame.this) ;
                    gameFrame.getContentPane().add(sahara.BIOME) ;
                    gameFrame.repaint();
                    setVisible(false);
                    gameFrame.setVisible(true);*/

                }
            }
        });

        r1=new JRadioButton("GREEK");
        r1.setSelected(true);
        r2=new JRadioButton("SAHARA");
        r1.setBounds(75,50,100,30);
        r1.setLocation(10,45);
        r2.setBounds(75,100,100,30);
        r2.setLocation(10,70);

        buttonGroup=new ButtonGroup();
        buttonGroup.add(r1);
        buttonGroup.add(r2);

        bg.add(r1);
        bg.add(r2);
        bg.add(buttonStart);
        bg.add(comboBox) ;
        bg.add(bMusic);
        bg.add(comboBoxspeed);

        add(bg) ;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
