package app;

import java.util.ArrayList;

public class MapArray {
    private Point[][] mapArray;
    private ArrayList<Area> walls;
    private ArrayList<TelePortal> teleportals ;
    private Scenario scenario;
    private int width;
    private int height;
    private final int scale ;
    double total ;
    double numerator ;
    GameFrame gameFrame ;

    public MapArray(Scenario scenario, GameFrame gameFrame) {
        this.scenario = scenario;
        scale = scenario.getScale() ;
        this.gameFrame = gameFrame ;
        walls = scenario.getWalls();
        width = scenario.mapWidth ;
        height = scenario.mapHeight ;
        mapArray = new Point[width][height];
        teleportals=scenario.getTeleportals() ;
        total = 0 ;
        numerator = 0 ;
        fillArray();
    }

    public int getWidth(){ return width;}

    public int getHeight(){ return height;}

    public void fillArray() {

        for (int i = 0; i < mapArray.length; i++) {
            for (int j = 0; j < mapArray[0].length; j++) {
                Point point = new Point(i, j);
                total= total+1 ;

                for (int k = 0; k < walls.size(); k++) {
                    if (walls.get(k).isHit(point.getX(), point.getY())) {
                        if(point.isLegal()) {
                            point.setLegal(false);
                            total=total-1 ;
                        }
                    }
                }

                for (int l = 0; l < teleportals.size(); l++) {
                    if(teleportals.get(l).isHit(point.getX(),point.getY())){
                        point.setIsteleport(true);
                        point.setX_teleport(teleportals.get(l).getxTarget());
                        point.setY_teleport(teleportals.get(l).getyTarget());
                        point.setTeleportOrientationview(teleportals.get(l).getNewOrientation());
                    }

                }

               Point pointadd =  new Point(point.getX()*scale, point.getY()*scale) ;
                if(!point.isLegal()) {
                    pointadd.setLegal(false);
                }

                if(point.isIsteleport()) {
                    pointadd.setIsteleport(true);
                    pointadd.setX_teleport(point.getX_teleport()*scale);
                    pointadd.setY_teleport(point.getY_teleport()*scale);
                }


                mapArray[i][j] = pointadd ;
            }
        }
    }


    public Point getPoint(int x, int y){ return mapArray[x/scale][y/scale] ; }

    public Point[][] getMapArray() {
        return mapArray;
    }

    public double getTotal() {
       // System.out.println(total );
        return total;
    }

    public double getNumerator() {
        return numerator;
    }

    public void setNumerator(double x){
        numerator = x ;
    }

    public boolean isInBounds(Point p){
        int x = p.getX();
        int y = p.getY();
        if(x >= width || x<0 || y>= height || y<0) return false;
        return true;
    }

    public boolean notBounds(Point pointView){
        return ((pointView.getX() >= 0) && (pointView.getY()/ scenario.getScale() < getMapArray()[0].length) && (pointView.getY()>=0) && (pointView.getX()/ scenario.getScale()<getMapArray().length)) ;
    }

    public boolean notWall(Point pointView){
        return (pointView.getX()==getPoint(pointView.getX(), pointView.getY()).getX()
                &&pointView.getY()==getPoint(pointView.getX(), pointView.getY()).getY()
                &&getPoint(pointView.getX(), pointView.getY()).isLegal()) ;
    }

    public Boolean notWallsnotBounds(Point pointView){
        if(!notBounds(pointView)) {
            return false;
        }
        if(notWall(pointView)){
            return true ;
        }
        return false ;
    }
}

