package app;

import java.util.ArrayList;

public class Line {

    int p;
    private double slope;
    private ArrayList<Point> linePoints = new ArrayList();
    private Point a;
    private Point b;
    private int a_x;
    private int a_y;
    private int b_x;
    private int b_y;

    private double length;
        
    private MapArray shared;



    public Line(Point start, Point end){
        this.a = start;
        this.b = end;
        a_x = a.getX();
        a_y = a.getY();
        b_x = b.getX();
        b_y = b.getY();
        this.shared = shared;
        // calc_slope();
        //calc_Distance();
        //System.out.println("Slope " + getSlope()+ " Length "+ length);

    }

    public double getSlope(){return slope;}
    public void setSlope(double s){ this.slope = s;}

    public double getLength(){return length;}
    public void setLength(double s){ this.length = s;}


    public void calc_slope(){
        int num = b_y - a_y;
        int den = b_x - a_x;
        slope = num/den;
    }

    public boolean isOrthogonal(Line other){
        
        if(slope * other.getSlope()==-1){ return true;}
        
        return false;
    }

    public void calc_Distance(){
        double part1 = Math.pow(b_x- a_x,2);
        double part2 = Math.pow(b_y -a_y , 2);
        length = Math.sqrt(part1+part2);
    }


    public void createFunction(){

        for(int i=0; i<= Math.abs(a_x-b_x);i++){
            for(int j=0; j<= Math.abs(a_y-a_y);j++){
                
            }
        }

    }

    public ArrayList<Point> markShortestPath(Point currentPos, Point destination){

        int xcp = currentPos.getX();
        int ycp = currentPos.getY();
        int xd = destination.getX();
        int yd = destination.getY();

        Point lp = new Point(0,0);

        if(xcp == xd) {              // case 1 input and target are on the same row
            for (int i = ycp; i <= yd; i++) {
                Point p = new Point(0,0);
                p.setX(xcp);
                p.setY(i);
                p.setMarkedOnLine();
                linePoints.add(p);
            }
            return linePoints;
        }


        if(ycp == yd) {              // case 2 input and target are on the same line
            for (int i = xcp; i <= xd; i++) {
                Point p = new Point(0,0);
                p.setX(ycp);
                p.setY(i);
                p.setMarkedOnLine();
                linePoints.add(p);
            }
            return linePoints;
        }

        if((ycp != yd) && (xcp != xd)) {  // case 3 input and target do not share any coordinates
            for (int i = xcp; i <= xd; i++)
                for (int j = ycp; j <= yd; j++) {
                    Point p = new Point(0,0);
                    p.setX(i);
                    p.setY(j);
                    p.setMarkedOnLine();
                    linePoints.add(p);
                }
         return linePoints;
        }

        return null;
    }

    public static void main(String[] args) {

        ArrayList<Point> minimap = new ArrayList<>();

        Point p1 = new Point(1,1);
        Point p2 = new Point(1,2);
        Point p3 = new Point(1,3);
        Point p4 = new Point(1,4);
        Point p5 = new Point(1,5);
        Point p6 = new Point(2,1);
        Point p7 = new Point(2,2);
        Point p8 = new Point(2,3);
        Point p9 = new Point(2,4);
        Point p10 = new Point(2,5);
        Point p11 = new Point(3,1);
        Point p12 = new Point(3,2);
        Point p13 = new Point(3,3);
        Point p14 = new Point(3,4);
        Point p15 = new Point(3,5);
        Point p16 = new Point(4,1);
        Point p17 = new Point(4,2);
        Point p18 = new Point(4,3);
        Point p19 = new Point(4,4);
        Point p20 = new Point(4,5);


        minimap.add(p1);
        minimap.add(p2);
        minimap.add(p3);
        minimap.add(p4);
        minimap.add(p5);
        minimap.add(p6);
        minimap.add(p7);
        minimap.add(p8);
        minimap.add(p9);
        minimap.add(p10);

        Line l = new Line(p6,p14);

        ArrayList<Point> alinepoint = new ArrayList<>();

        alinepoint = l.markShortestPath(p6,p14);
        System.out.println(alinepoint);

    }

    // public int getReward(Point pos){

    // }


    
}
