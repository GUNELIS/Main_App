package app;

import java.util.ArrayList;

/**
 *
 * @author joel
 */
public class Area {
    protected int leftBoundary;
    protected int rightBoundary;
    protected int topBoundary;
    protected int bottomBoundary;
    private int x1 ;
    private int x2 ;
    private int y1 ;
    private int y2 ;

    public Area(){
        leftBoundary=0;
        rightBoundary=1;
        topBoundary=0;
        bottomBoundary=1;
    }

    public Area(int x1, int y1, int x2, int y2){
        this.x1 = x1 ;
        this.x2 = x2 ;
        this.y1 = y1 ;
        this.y2 = y2 ;
        leftBoundary=Math.min(x1,x2);
        rightBoundary=Math.max(x1,x2);
        topBoundary=Math.max(y1,y2);
        bottomBoundary=Math.min(y1,y2);
    }

    public int getX1(){return x1 ; }

    public int getX2() {return x2 ; }

    public int getY1() {return y1 ; }

    public int getY2() {return y2 ; }

    /*
            Check whether a point is in the target area
        */
    public boolean isHit(int x,int y){
        return (y>=bottomBoundary)&&(y<topBoundary)&&(x>=leftBoundary)&&(x<rightBoundary);
    }

    /*
        Check whether something with a radius is in the target area
        STILL TO BE IMPLEMENTED
    */
    public boolean isHit(double x,double y,double radius){
        return false;
    }

    public ArrayList<Point> getPointList(MapArray ma){
        int rows = ma.getHeight();
        int cols = ma.getWidth();
        ArrayList<Point> arr = new ArrayList<>();
        for(int i=0; i<rows; i++){
            for(int j=0;j<cols; j++ ){
                Point p = ma.getPoint(i,j);
                if(isHit(p.getX(), p.getY())){ //if the point is in the area return
                    arr.add(p);
                }
            }
        }
    return arr;
    }

}
