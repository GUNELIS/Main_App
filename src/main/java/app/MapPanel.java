package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MapPanel extends JPanel implements ActionListener {
    public static final Color CITY = new Color(214, 217, 223);
    public static final Color DESERT = new Color(255, 204, 102);
    public static final Color DESERT2 = new Color(255, 160, 102);
    public static final Color DIRT_ROAD = new Color(153, 102, 0);
    public static final Color FOREST = new Color(0, 102, 0);
    public static final Color HILLS = new Color(51, 153, 0);
    public static final Color LAKE = new Color(0, 153, 153);
    public static final Color MOUNTAINS = new Color(102, 102, 255);
    public static final Color OCEAN = new Color(0, 0, 153);
    public static final Color PAVED_ROAD = new Color(51, 51, 0);
    public static final Color PLAINS = new Color(102, 153, 0);
    private static final int DEFAULT_HEIGHT = 80;
    private static final int DEFAULT_WIDTH = 120;
    private static final int[] p = new int[512];
    private static final int[] permutation = {151, 160, 137, 91, 90, 15,
            131, 13, 201, 95, 96, 53, 194, 233, 7, 142, 8, 99, 37, 240, 21, 10, 23,
            190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33,
            88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 16225, 140, 36, 103, 30, 69, 5, 71, 134, 139, 48, 27, 166,
            77, 146, 158, 231, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244,
            102, 143, 54, 65, 25, 63, 161, 83, 111, 229, 122, 60, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196,
            135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123,
            5, 202, 38, 147, 118, 126, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
            223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
            129, 22, 39, 253, 19, 98, 108, 110, 79, 255, 82, 85, 212, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228,
            251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107,
            49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254,
            138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180
    };

    private static int height;
    private static int width;
    private static double z;
    private static String BIOME;
    public static int mili_sec_per_move;
    public static void setMili_sec_per_move(int mili_sec_per_move) {
        MapPanel.mili_sec_per_move = mili_sec_per_move;
    }
    static {
        for (int i = 0; i < 256; i++)
            p[256 + i] = p[i] = permutation[i];
    }

    private final Scenario scenario;
    private final ArrayList<Area> walls;
    private final ArrayList<TelePortal> telePortals;
    private final ArrayList<Area> shaded;
    private final ArrayList<Guard> guards;
    private final ArrayList<Intruder> intruders;
    private final int scale;
    private final Timer timer;
    private final GameFrame gameFrame;
    public long sumElapsed;
    public double averageElapesd;
    public int count = 0;


    public java.util.List<Point> FOREST_points2 = new ArrayList<>();
    public java.util.List<Point> HILLs_points2 = new ArrayList<>();
    public java.util.List<Point> Desert_points2 = new ArrayList<>();
    public java.util.List<Point> LAKE_points2 = new ArrayList<>();
    public java.util.List<Point> MOUNTAINS_points2 = new ArrayList<>();
    public java.util.List<Point> PLAINS_points2 = new ArrayList<>();
    public java.util.List<Point> SNOW_points2 = new ArrayList<>();
    public java.util.List<Point> Dirt_Road2 = new ArrayList<>();
    double percentage;
    ArrayList<Point> shared = new ArrayList<>();
    private boolean play;
    private ArrayList<Point> seenByAll;
    private ArrayList<Point> locationspawn;

    public MapPanel(Scenario scenario, GameFrame gameFrame, String biome, int width, int height, double z) {
        this.scenario = scenario;
        this.gameFrame = gameFrame;
        BIOME = biome;
        MapPanel.width = width;
        MapPanel.height = height;
        MapPanel.z = z;
        System.out.println(BIOME);
        scale = scenario.getScale();
        walls = scenario.getWalls();
        telePortals = scenario.getTeleportals();
        shaded = scenario.getShaded();
        guards = new ArrayList<>();
        intruders = new ArrayList<>();
        play = false;
        timer = new Timer(10, this);
        setLayout(null);


        createGuard();
        createInturders();
    }

    public Scenario getScenario(){ return scenario;}



    private static double pernil_noise(double x, double y, double z) {
        // Find the unit cube that contains the point
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;
        int Z = (int) Math.floor(z) & 255;

        // Find relative x, y,z of point in cube
        x -= Math.floor(x);
        y -= Math.floor(y);
        z -= Math.floor(z);

        // Compute fade curves for each of x, y, z
        double u = fading(x);
        double v = fading(y);
        double w = fading(z);

        // Hash coordinates of the 8 cube corners
        int A = p[X] + Y;
        int AA = p[A] + Z;
        int AB = p[A + 1] + Z;
        int B = p[X + 1] + Y;
        int BA = p[B] + Z;
        int BB = p[B + 1] + Z;

        // Add blended results from 8 corners of cube
        double res = lerp(
                w,
                lerp(v, lerp(u, gradiant(p[AA], x, y, z), gradiant(p[BA], x - 1, y, z)),
                        lerp(u, gradiant(p[AB], x, y - 1, z), gradiant(p[BB], x - 1, y - 1, z))),
                lerp(v, lerp(u, gradiant(p[AA + 1], x, y, z - 1), gradiant(p[BA + 1], x - 1, y, z - 1)),
                        lerp(u, gradiant(p[AB + 1], x, y - 1, z - 1), gradiant(p[BB + 1], x - 1, y - 1, z - 1))));
        return (res + 1.0) / 2.0;
    }

    private static double fading(double constant) {
        //return constant * constant * constant * (constant * (constant * 6 - 15) + 10);
        return constant;
    }

    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private static double gradiant(int hash, double x, double y, double z) {
        int h = hash & 15;
        double u = h < 8 ? x : y,
                v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    public String getPlace(Agent player) {
        Point p = new Point(player.getLocation().getY() / scale, player.getLocation().getX() / scale);
        // System.out.println("get place poitn :"+ p);
        // TODO add constraints
        String land = "";
        if (FOREST_points2.contains(p)) {
            land = "FOREST";
            player.getViewObject().setReduceView(true);
            player.getViewObject().setExtendView(false);
            // player.setactualSpeed(player.getSpeed()+1);
        } else if (HILLs_points2.contains(p)) {
            land = "HILLS";
            player.getViewObject().setReduceView(false);
            player.getViewObject().setExtendView(true);
            // player.setactualSpeed(player.getSpeed()-1);
        } else if (Desert_points2.contains(p)) {
            land = "DESERT";
            player.getViewObject().setReduceView(false);
            player.getViewObject().setExtendView(false);
            //  player.setactualSpeed(player.getSpeed());
        } else if (PLAINS_points2.contains(p)) {
            land = "PLAINS";
            player.getViewObject().setReduceView(false);
            player.getViewObject().setExtendView(false);
            // player.setactualSpeed(player.getSpeed()+2);
        } else if (MOUNTAINS_points2.contains(p)) {
            land = "MOUNTAINS";
            player.getViewObject().setReduceView(false);
            player.getViewObject().setExtendView(true);
            // player.setactualSpeed(player.getSpeed()-1);
        } else if (LAKE_points2.contains(p)) {
            land = "LAKE";
            player.getViewObject().setReduceView(true);
            player.getViewObject().setExtendView(false);
            //   player.setactualSpeed(player.getSpeed()-1);
        } else if (SNOW_points2.contains(p)) {
            land = "SNOW";
            player.getViewObject().setReduceView(true);
            player.getViewObject().setExtendView(false);
            // player.setactualSpeed(player.getSpeed()-1);
        }
            /*else{
                land="empty";
                player.setSpeed(14);
            }

             */
        //System.out.println(p);
        //System.out.println(Terrain_mapper("FOREST"));
        return land;
    }

    public ArrayList<Guard> getGuards() {
        return guards;
    }
    public ArrayList<Intruder> getIntruders() {
        return intruders;
    }

    public void createGuard() {
        int x1 = scenario.spawnAreaGuards.getX1() * scale;
        int x2 = scenario.spawnAreaGuards.getX2() * scale;
        int y1 = scenario.spawnAreaGuards.getY1() * scale;
        int y2 = scenario.spawnAreaGuards.getY2() * scale;


        for (int i = 0; i < scenario.numGuards; i++) {

            int xx = (int) (Math.random() * (x2 - x1)) + x1;
            int yy = (int) (Math.random() * (y2 - y1)) + y1;

            Point location = new Point(xx, yy);
            Integer u = 1+i;
            String id = "1"+ u.toString(); //intruder id's start with 2 and guards with 1
            
            Guard guard = new Guard(scenario, location, gameFrame,id) ;
            guards.add(guard);

        }
    }


    public void createInturders() {
        int x1 = scenario.spawnAreaIntruders.getX1() * scale;
        int x2 = scenario.spawnAreaIntruders.getX2() * scale;
        int y1 = scenario.spawnAreaIntruders.getY1() * scale;
        int y2 = scenario.spawnAreaIntruders.getY2() * scale;


        for (int i = 0; i < scenario.numIntruders; i++) {

            int xx = (int) (Math.random() * (x2 - x1)) + x1;
            int yy = (int) (Math.random() * (y2 - y1)) + y1;

            Point location = new Point(xx, yy);
            Integer u = 1+i;
            String id = "5"+ u.toString(); //intruder id's start with 2 and guards with 1
            Intruder intruder = new Intruder(scenario, location, gameFrame,id) ;
            intruders.add(intruder);

        }
    }

    public void paint(Graphics g) {
/**
        if (BIOME == "GREEK") {
            //System.out.println("width : "+ width + "height : "+ height);
            for (int i = 0; i < width; ++i) { //y
                for (int j = 0; j < height; ++j) { // x
                    double x = (double) j / ((double) width);
                    double y = (double) i / ((double) height);
                    double n = pernil_noise(10 * x, 10 * y, z);
                    Point p = new Point(j, i);
                    //System.out.println("paint point p "+ p);
                    // Watter (or a Lakes)

                    if (n < 0.25) {
                        g.setColor(FOREST);
                        FOREST_points2.add(p);


                    } else if (n >= 0.25 && n < 0.30) {
                        g.setColor(HILLS);
                        HILLs_points2.add(p);

                    }
                    // Floors (or Planes)
                    else if (n >= 0.30 && n < 0.40) {
                        Desert_points2.add(p);
                        g.setColor(DESERT);


                    } else if (n >= 0.40 && n < 0.5) {
                        g.setColor(LAKE);
                        LAKE_points2.add(p);

                    } else if (n >= 0.5 && n < 0.70) {
                        g.setColor(PLAINS);
                        PLAINS_points2.add(p);

                    } else if (n >= 0.70 && n < 0.75) {
                        FOREST_points2.add(p);
                        g.setColor(FOREST);

                    }
                    // Walls (or Mountains)
                    else if (n >= 0.75 && n < 0.85) {
                        g.setColor(Color.GRAY);
                        MOUNTAINS_points2.add(p);

                    }
                    // Ice (or Snow)
                    else {
                        g.setColor(Color.white);
                        SNOW_points2.add(p);

                    }
                    g.fillRect(i * scale, j * scale, scale, scale);


                    // System.out.println(coord.getX() +" : "+coord.getY());
                }
            }
        }

        if (BIOME == "SAHARA") {
            for (int i = 0; i < width; ++i) { // y
                for (int j = 0; j < height; ++j) { // x
                    double x = (double) j / ((double) width);
                    double y = (double) i / ((double) height);
                    double n = pernil_noise(10 * x, 10 * y, z);
                    Point p = new Point(j, i);
                    // Watter (or a Lakes)

                    if (n < 0.25) {
                        g.setColor(DESERT);
                        Desert_points2.add(p);

                    } else if (n >= 0.25 && n < 0.30) {
                        g.setColor(DESERT);
                        Desert_points2.add(p);

                    }
                    // Floors (or Planes)
                    else if (n >= 0.30 && n < 0.40) {
                        g.setColor(DESERT2);
                        Desert_points2.add(p);


                    } else if (n >= 0.40 && n < 0.5) {
                        g.setColor(DIRT_ROAD);
                        Dirt_Road2.add(p);

                    } else if (n >= 0.50 && n < 0.75) {
                        g.setColor(DESERT);
                        Desert_points2.add(p);
                    }


                    // Walls (or Mountains)
                    else if (n >= 0.75 && n < 0.85) {
                        g.setColor(Color.GRAY);
                        MOUNTAINS_points2.add(p);

                    }
                    // Ice (or Snow)
                    else {
                        g.setColor(Color.white);
                        SNOW_points2.add(p);

                    }
                    g.fillRect(i * scale, j * scale, scale, scale);
       // g.setColor(new Color(17, 86, 225, 68));
       g.setColor(Color.LIGHT_GRAY);
       g.fillRect(0,0,scenario.mapWidth,scenario.mapHeight);


                    // System.out.println(coord.getX() +" : "+coord.getY());

                }
            }
        } */
        int x1, x2, y1, y2, x, y, height, width;


        for (int i = 0; i < guards.size(); i++) {
            for (int k = 0; k < guards.get(i).getMapArray().getMapArray().length; k++) {
                for (int l = 0; l < guards.get(i).getMapArray().getMapArray()[0].length; l++) {
                    if (guards.get(i).getMapArray().getMapArray()[k][l].isVisited()) {
                        g.setColor(new Color(178, 6, 246, 26));
                        // g.setColor(new Color(178, 6, 246, 255));
                        g.fillRect(guards.get(i).getMapArray().getMapArray()[k][l].getX(), guards.get(i).getMapArray().getMapArray()[k][l].getY(), guards.get(i).getSize(), guards.get(i).getSize());

                    }
                }
            }
        }

// ---------Painting the Walls----------
        for (int i = 0; i < walls.size(); i++) {
            x1 = walls.get(i).getX1() * scale;
            x2 = walls.get(i).getX2() * scale;
            y1 = walls.get(i).getY1() * scale;
            y2 = walls.get(i).getY2() * scale;

            x = Math.min(x1, x2);
            y = Math.min(y1, y2);

            width = Math.abs(x1 - x2);
            height = Math.abs(y1 - y2);

            g.setColor(new Color(10, 10, 10, 111));
            g.drawRect(x, y, width, height);
            g.fillRect(x, y, width, height);
        }
    
// ---------Painting the Teleportals----------
        for (int i = 0; i < telePortals.size(); i++) {
            x1 = telePortals.get(i).getX1() * scale;
            x2 = telePortals.get(i).getX2() * scale;
            y1 = telePortals.get(i).getY1() * scale;
            y2 = telePortals.get(i).getY2() * scale;

            x = Math.min(x1, x2);
            y = Math.min(y1, y2);

            width = Math.abs(x1 - x2);
            height = Math.abs(y1 - y2);

            g.setColor(new Color(239, 220, 10, 255));
            g.drawRect(x, y, width, height);
            g.fillRect(x, y, width, height);
        }
// ---------Painting the Shaded----------
        for (int i = 0; i < shaded.size(); i++) {
            x1 = shaded.get(i).getX1() * scale;
            x2 = shaded.get(i).getX2() * scale;
            y1 = shaded.get(i).getY1() * scale;
            y2 = shaded.get(i).getY2() * scale;

            x = Math.min(x1, x2);
            y = Math.min(y1, y2);
            width = Math.abs(x1 - x2);
            height = Math.abs(y1 - y2);

            g.setColor(Color.DARK_GRAY);
            g.drawRect(x, y, width, height);
            g.fillRect(x, y, width, height);

        }
// ---------Painting the Target----------
        if (scenario.targetArea != null) {
            Area targetArea = scenario.targetArea;
            x1 = targetArea.getX1() * scale;
            x2 = targetArea.getX2() * scale;
            y1 = targetArea.getY1() * scale;
            y2 = targetArea.getY2() * scale;

            x = Math.min(x1, x2);
            y = Math.min(y1, y2);
            width = Math.abs(x1 - x2);
            height = Math.abs(y1 - y2);

            g.setColor(Color.red);
            g.drawRect(x, y, width, height);
            g.fillRect(x, y, width, height);
        }
// ---------Painting the Spawn Area for Intruders----------
        if (scenario.spawnAreaIntruders != null) {
            Area spawnAreaIntruders = scenario.spawnAreaIntruders;
            x1 = spawnAreaIntruders.getX1() * scale;
            x2 = spawnAreaIntruders.getX2() * scale;
            y1 = spawnAreaIntruders.getY1() * scale;
            y2 = spawnAreaIntruders.getY2() * scale;

            x = Math.min(x1, x2);
            y = Math.min(y1, y2);
            width = Math.abs(x1 - x2);
            height = Math.abs(y1 - y2);

            g.setColor(Color.gray);
            g.drawRect(x, y, width, height);
            g.fillRect(x, y, width, height);

        }
// ---------Painting the Spawn area for Guards----------
        if (scenario.spawnAreaGuards != null) {
            Area spawnAreaGuards = scenario.spawnAreaGuards;
            x1 = spawnAreaGuards.getX1() * scale;
            x2 = spawnAreaGuards.getX2() * scale;
            y1 = spawnAreaGuards.getY1() * scale;
            y2 = spawnAreaGuards.getY2() * scale;

            x = Math.min(x1, x2);
            y = Math.min(y1, y2);
            width = Math.abs(x1 - x2);
            height = Math.abs(y1 - y2);

            g.setColor(Color.green);
            g.drawRect(x, y, width, height);
            g.fillRect(x, y, width, height);
        }

        for (int i = 0; i < guards.size(); i++) {
            int size = guards.get(i).getSize();
            x = guards.get(i).getLocation().getX();
            y = guards.get(i).getLocation().getY();


            for (int j = 0; j < guards.get(i).getView().size(); j++) {
                g.setColor(new Color(10, 50, 201, 126));
                g.fillRect(guards.get(i).getView().get(j).getX(), guards.get(i).getView().get(j).getY(), size, size);
            }
            g.setColor(new Color(174, 65, 204));
            g.fillRect(x, y, size, size);

            g.setColor(new Color(10, 10, 10, 15));
            int xxx = 980;
            int yyy = 650;
            g.setColor(new Color(3, 23, 2, 39));
            g.fillRect(xxx, yyy, scenario.mapWidth, scenario.mapHeight);


            for (int j = 0; j < guards.size(); j++) {
                for (int k = 0; k < guards.get(i).getMapArray().getMapArray().length; k++) {
                    for (int l = 0; l < guards.get(i).getMapArray().getMapArray()[0].length; l++) {
                        if (guards.get(i).getMapArray().getMapArray()[k][l].isVisited()) {
                            g.setColor(new Color(13, 236, 32, 86));
                            g.fillRect(xxx + guards.get(i).getMapArray().getMapArray()[k][l].getX() / scale, yyy + guards.get(i).getMapArray().getMapArray()[k][l].getY() / scale, 1, 1);
                            // g.setColor(new Color(231, 13, 13, 50));
                            //  g.fillRect(guards.get(i).getMapArray().getMapArray()[k][l].getX(),guards.get(i).getMapArray().getMapArray()[k][l].getY(),size,size);
                        }


                    }
                }
            }



            
        for (int ii = 0; i < intruders.size(); i++) {
            int size1 = intruders.get(ii).getSize();
            x = intruders.get(ii).getLocation().getX();
            y = intruders.get(ii).getLocation().getY();


            for (int j = 0; j < intruders.get(i).getView().size(); j++) {
                g.setColor(new Color(224, 17, 95 ,126));
                g.fillRect(intruders.get(i).getView().get(j).getX(), intruders.get(i).getView().get(j).getY(), size1, size1);
            }
            g.setColor(Color.RED);
            g.fillRect(x, y, size, size);

            g.setColor(new Color(10, 10, 10, 15));
            int xxxx = 980;
            int yyyy = 650;
            g.setColor(new Color(3, 23, 2, 39));
            g.fillRect(xxx, yyy, scenario.mapWidth, scenario.mapHeight);

        }
    }

        if (play) timer.start();
        if (!play) timer.stop();
    }

    public Timer getTimer() {
        return timer;
    }

    public void changePlayToTrue() {
        if (!play) play = true;
    }

    public void changePlayToFalse() {
        if (play) play = false;
    }

    public void actionPerformed(ActionEvent e) {
        // System.out.println("______________-__________");
        for (int i = 0; i < guards.size(); i++) {
            //guards.get(i).moveRandom();
            long start = System.currentTimeMillis();
            int n = i + 1;


            //System.out.println("terrain guard " + n + " :" + getPlace(guards.get(i)));
            // getPlace(guards.get(i));//guards.get(i).moveViewRandom();
            guards.get(i).moveBigSaures();

            count = BigSquares.move_count;
           // count = MoveRandom.count_move;




            //guards.get(i).moveViewRandom();
            //getPlace(guards.get(i));
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;

            //System.out.println("Time elapsed per move:" + timeElapsed);
            sumElapsed =  sumElapsed + timeElapsed;
            averageElapesd = sumElapsed / count;
            try {
                Thread.sleep(mili_sec_per_move);
               // System.out.println("count : "+ count );
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }


        for(Intruder intruder : intruders){
             if(intruder.isCaught()){
                 System.out.println("Intuder Caught");

                 repaint();
                 return;
             }
                //getPlace(intruder);
                intruder.moveRandom();
                //getPlace(intruder);
                if(scenario.getTargetArea().isHit(intruder.getLocation().getX(),intruder.getLocation().getY())){
                    changePlayToFalse();
                    repaint();
                    System.out.println("GAME OVER");
                    return;
                }
            }
        repaint();
       }
    }
