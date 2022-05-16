package app;

import java.util.ArrayList;

import javax.naming.directory.SearchResult;

public class View {
    private ArrayList<Point> view ;
    private Point locationGuard ;
    private int size ;
    private int scale ;
    private String facing ;
    private Agent guard ;
    private Intruder intruder;
    private ArrayList<Point> nextView ;
    private MapArray mapArray ;
    private int length = 4;
    private GameFrame gameFrame ;
    private MapArray sharedArray ;
    private boolean extendView = false;
    private boolean reduceView = false;
    private boolean wasReduced = false;
    private boolean wasExtended = false;
    Point point ;

    public View(Agent agent, GameFrame gameFrame){

        this.guard = agent ;
        this.gameFrame = gameFrame ;
        scale = agent.getScenerio().getScale() ;
        sharedArray = guard.getSharedArray() ;
        mapArray = guard.getMapArray()  ;
        locationGuard = guard.getLocation() ;
        size = guard.getSize();
        facing = "R" ;
        nextView = new ArrayList<>() ;
        fillView();
    }

    public ArrayList<Point> getNextView() {
        return nextView;
    }

    public void setExtendView(boolean b){ extendView = b;}
    public void setReduceView(boolean b){ reduceView = b;}

    public boolean getExtendView(){ return extendView;}
    public boolean getReduceView(){ return reduceView;}

    public void fillView(){
        locationGuard=guard.getLocation();
        view = new ArrayList<>() ;
        Point p0 = new Point(locationGuard.getX()+size, locationGuard.getY()) ;
        Point p1 = new Point(locationGuard.getX()+size, locationGuard.getY()-size ) ;
        Point p2 = new Point(locationGuard.getX()+size, locationGuard.getY()+size) ;
       
        Point p3 = new Point(locationGuard.getX()+2*size, locationGuard.getY()-2*size) ;
        Point p4 = new Point(locationGuard.getX()+2*size, locationGuard.getY()-size ) ;
        Point p5 = new Point(locationGuard.getX()+2*size, locationGuard.getY()) ;
        Point p6 = new Point(locationGuard.getX()+2*size, locationGuard.getY()+size ) ;
        Point p7 = new Point(locationGuard.getX()+2*size, locationGuard.getY()+2*size ) ;
       
        Point p8 = new Point(locationGuard.getX()+3*size, locationGuard.getY()-3*size) ;
        Point p9 = new Point(locationGuard.getX()+3*size, locationGuard.getY()-2*size ) ;
        Point p10 = new Point(locationGuard.getX()+3*size, locationGuard.getY()-size ) ;
        Point p11 = new Point(locationGuard.getX()+3*size, locationGuard.getY()) ;
        Point p12 = new Point(locationGuard.getX()+3*size, locationGuard.getY()+size ) ;
        Point p13 = new Point(locationGuard.getX()+3*size, locationGuard.getY()+2*size ) ;
        Point p14 = new Point(locationGuard.getX()+3*size, locationGuard.getY()+3*size ) ;

        Point p15 = new Point(locationGuard.getX()+4*size, locationGuard.getY()-4*size ) ;
        Point p16 = new Point(locationGuard.getX()+4*size, locationGuard.getY()-3*size) ;
        Point p17 = new Point(locationGuard.getX()+4*size, locationGuard.getY()-2*size ) ;
        Point p18 = new Point(locationGuard.getX()+4*size, locationGuard.getY()-size ) ;
        Point p19 = new Point(locationGuard.getX()+4*size, locationGuard.getY()) ;
        Point p20 = new Point(locationGuard.getX()+4*size, locationGuard.getY()+size ) ;
        Point p21 = new Point(locationGuard.getX()+4*size, locationGuard.getY()+2*size ) ;
        Point p22 = new Point(locationGuard.getX()+4*size, locationGuard.getY()+3*size ) ;
        Point p23 = new Point(locationGuard.getX()+4*size, locationGuard.getY()+4*size ) ;
        
        
        view.add(p0);
        view.add(p1);
        view.add(p2);
        view.add(p3);
        view.add(p4);
        view.add(p5);
        view.add(p6);
        view.add(p7);
        if(!reduceView){
            view.add(p8);
            view.add(p9);
            view.add(p10);
            view.add(p11);
            view.add(p12);
            view.add(p13);
            view.add(p14);
        }
        if(extendView){
            view.add(p15);
            view.add(p16);
            view.add(p17);
            view.add(p18);
            view.add(p19);
            view.add(p20);
            view.add(p21);
            view.add(p22);
            view.add(p23);
        }
        for(Point p : view){
            if(!mapArray.getMapArray()[p.getX()/size][p.getY()/size].isVisited()) {
                mapArray.getMapArray()[p.getX() / size][p.getY() / size].setVisited(true);
            }
            if(!sharedArray.getMapArray()[p.getX()/size][p.getY()/size].isVisited()) {
                sharedArray.getMapArray()[p.getX() / size][p.getY() / size].setVisited(true);
                sharedArray.setNumerator(sharedArray.getNumerator()+1);
                gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());
            }
        }
    }
    public void updateView(){
        if(facing == "D"){
            if(!wasReduced){
                if(!wasExtended){
                    if(getExtendView()) {
                        view.add(new Point(guard.getLocation().getX() - 4 * size, guard.getLocation().getY() + 4 * size));
                        view.add(new Point(guard.getLocation().getX() - 3 * size, guard.getLocation().getY() + 4 * size));
                        view.add(new Point(guard.getLocation().getX() - 2 * size, guard.getLocation().getY() + 4 * size));
                        view.add(new Point(guard.getLocation().getX() - size, guard.getLocation().getY() + 4 * size));
                        view.add(new Point(guard.getLocation().getX(), guard.getLocation().getY() + 4 * size));
                        view.add(new Point(guard.getLocation().getX() + size, guard.getLocation().getY() + 4 * size));
                        view.add(new Point(guard.getLocation().getX() + 2 * size, guard.getLocation().getY() + 4 * size));
                        view.add(new Point(guard.getLocation().getX() + 3 * size, guard.getLocation().getY() + 4 * size));
                        view.add(new Point(guard.getLocation().getX() + 4 * size, guard.getLocation().getY() + 4 * size));
                        wasExtended = true;
                    }
                    else if(getReduceView()){
                        view.remove(14);
                        view.remove(13);
                        view.remove(12);
                        view.remove(11);
                        view.remove(10);
                        view.remove(9);
                        view.remove(8);
                        wasReduced=true;
                    }
                }
                else if(!getExtendView()){
                    view.remove(23);
                    view.remove(22);
                    view.remove(21);
                    view.remove(20);
                    view.remove(19);
                    view.remove(18);
                    view.remove(17);
                    view.remove(16);
                    view.remove(15);
                    if(getReduceView()){
                        view.remove(14);
                        view.remove(13);
                        view.remove(12);
                        view.remove(11);
                        view.remove(10);
                        view.remove(9);
                        view.remove(8);
                        wasReduced=true;
                    }
                    wasExtended=false;
                }
            }
            else if (!getReduceView()){
                view.add(new Point(guard.getLocation().getX()-3*size, guard.getLocation().getY()+3*size ));
                view.add(new Point(guard.getLocation().getX()-2*size, guard.getLocation().getY()+3*size ));
                view.add(new Point(guard.getLocation().getX()-size, guard.getLocation().getY()+3*size ));
                view.add(new Point(guard.getLocation().getX(), guard.getLocation().getY()+3*size ));
                view.add(new Point(guard.getLocation().getX()+size, guard.getLocation().getY()+3*size ));
                view.add(new Point(guard.getLocation().getX()+2*size, guard.getLocation().getY()+3*size ));
                view.add(new Point(guard.getLocation().getX()+3*size, guard.getLocation().getY()+3*size ));
                if(getExtendView()){
                    view.add(new Point(guard.getLocation().getX()-4*size, guard.getLocation().getY()+4*size ));
                    view.add(new Point(guard.getLocation().getX()-3*size, guard.getLocation().getY()+4*size ));
                    view.add(new Point(guard.getLocation().getX()-2*size, guard.getLocation().getY()+4*size ));
                    view.add(new Point(guard.getLocation().getX()-size, guard.getLocation().getY()+4*size ));
                    view.add(new Point(guard.getLocation().getX(), guard.getLocation().getY()+4*size ));
                    view.add(new Point(guard.getLocation().getX()+size, guard.getLocation().getY()+4*size ));
                    view.add(new Point(guard.getLocation().getX()+2*size, guard.getLocation().getY()+4*size ));
                    view.add(new Point(guard.getLocation().getX()+3*size, guard.getLocation().getY()+4*size ));
                    view.add(new Point(guard.getLocation().getX()+4*size, guard.getLocation().getY()+4*size ));
                    wasExtended=true;
                }
                wasReduced=false;
            }
        }
        if(facing=="U"){
            if(!wasReduced){
                if(!wasExtended){
                    if(getExtendView()) {
                        view.add(new Point(guard.getLocation().getX() - 4 * size, guard.getLocation().getY() - 4 * size));
                        view.add(new Point(guard.getLocation().getX() - 3 * size, guard.getLocation().getY() - 4 * size));
                        view.add(new Point(guard.getLocation().getX() - 2 * size, guard.getLocation().getY() - 4 * size));
                        view.add(new Point(guard.getLocation().getX() - size, guard.getLocation().getY() - 4 * size));
                        view.add(new Point(guard.getLocation().getX(), guard.getLocation().getY() - 4 * size));
                        view.add(new Point(guard.getLocation().getX() + size, guard.getLocation().getY() - 4 * size));
                        view.add(new Point(guard.getLocation().getX() + 2 * size, guard.getLocation().getY() - 4 * size));
                        view.add(new Point(guard.getLocation().getX() + 3 * size, guard.getLocation().getY() - 4 * size));
                        view.add(new Point(guard.getLocation().getX() + 4 * size, guard.getLocation().getY() - 4 * size));
                        wasExtended = true;
                    }
                    else if(getReduceView()){
                        view.remove(14);
                        view.remove(13);
                        view.remove(12);
                        view.remove(11);
                        view.remove(10);
                        view.remove(9);
                        view.remove(8);
                        wasReduced=true;
                    }
                }
                else if(!getExtendView()){
                    view.remove(23);
                    view.remove(22);
                    view.remove(21);
                    view.remove(20);
                    view.remove(19);
                    view.remove(18);
                    view.remove(17);
                    view.remove(16);
                    view.remove(15);
                    if(getReduceView()){
                        view.remove(14);
                        view.remove(13);
                        view.remove(12);
                        view.remove(11);
                        view.remove(10);
                        view.remove(9);
                        view.remove(8);
                        wasReduced=true;
                    }
                    wasExtended=false;
                }
            }
            else if (!getReduceView()){
                view.add(new Point(guard.getLocation().getX()-3*size, guard.getLocation().getY()-3*size ));
                view.add(new Point(guard.getLocation().getX()-2*size, guard.getLocation().getY()-3*size ));
                view.add(new Point(guard.getLocation().getX()-size, guard.getLocation().getY()-3*size ));
                view.add(new Point(guard.getLocation().getX(), guard.getLocation().getY()-3*size ));
                view.add(new Point(guard.getLocation().getX()+size, guard.getLocation().getY()-3*size ));
                view.add(new Point(guard.getLocation().getX()+2*size, guard.getLocation().getY()-3*size ));
                view.add(new Point(guard.getLocation().getX()+3*size, guard.getLocation().getY()-3*size ));
                if(getExtendView()){
                    view.add(new Point(guard.getLocation().getX()-4*size, guard.getLocation().getY()-4*size ));
                    view.add(new Point(guard.getLocation().getX()-3*size, guard.getLocation().getY()-4*size ));
                    view.add(new Point(guard.getLocation().getX()-2*size, guard.getLocation().getY()-4*size ));
                    view.add(new Point(guard.getLocation().getX()-size, guard.getLocation().getY()-4*size ));
                    view.add(new Point(guard.getLocation().getX(), guard.getLocation().getY()-4*size ));
                    view.add(new Point(guard.getLocation().getX()+size, guard.getLocation().getY()-4*size ));
                    view.add(new Point(guard.getLocation().getX()+2*size, guard.getLocation().getY()-4*size ));
                    view.add(new Point(guard.getLocation().getX()+3*size, guard.getLocation().getY()-4*size ));
                    view.add(new Point(guard.getLocation().getX()+4*size, guard.getLocation().getY()-4*size ));
                    wasExtended=true;
                }
                wasReduced=false;
            }

        }
        if(facing=="R"){
            if(!wasReduced){
                if(!wasExtended){
                    if(getExtendView()) {
                        view.add(new Point(guard.getLocation().getX() + 4 * size, guard.getLocation().getY() - 4 * size));
                        view.add(new Point(guard.getLocation().getX() + 4 * size, guard.getLocation().getY() - 3 * size));
                        view.add(new Point(guard.getLocation().getX() + 4 * size, guard.getLocation().getY() - 2 * size));
                        view.add(new Point(guard.getLocation().getX() + 4 * size, guard.getLocation().getY() - size));
                        view.add(new Point(guard.getLocation().getX() + 4 * size, guard.getLocation().getY()));
                        view.add(new Point(guard.getLocation().getX() + 4 * size, guard.getLocation().getY() + size));
                        view.add(new Point(guard.getLocation().getX() + 4 * size, guard.getLocation().getY() + 2 * size));
                        view.add(new Point(guard.getLocation().getX() + 4 * size, guard.getLocation().getY() + 3 * size));
                        view.add(new Point(guard.getLocation().getX() + 4 * size, guard.getLocation().getY() + 4 * size));
                        wasExtended = true;
                    }
                    else if(getReduceView()){
                        view.remove(14);
                        view.remove(13);
                        view.remove(12);
                        view.remove(11);
                        view.remove(10);
                        view.remove(9);
                        view.remove(8);
                        wasReduced=true;
                    }
                }
                else if(!getExtendView()){
                    view.remove(23);
                    view.remove(22);
                    view.remove(21);
                    view.remove(20);
                    view.remove(19);
                    view.remove(18);
                    view.remove(17);
                    view.remove(16);
                    view.remove(15);
                    if(getReduceView()){
                        view.remove(14);
                        view.remove(13);
                        view.remove(12);
                        view.remove(11);
                        view.remove(10);
                        view.remove(9);
                        view.remove(8);
                        wasReduced=true;
                    }
                    wasExtended=false;
                }
            }
            else if (!getReduceView()){

                view.add(new Point(guard.getLocation().getX()+3*size, guard.getLocation().getY()-3*size ));
                view.add(new Point(guard.getLocation().getX()+3*size, guard.getLocation().getY()-2*size ));
                view.add(new Point(guard.getLocation().getX()+3*size, guard.getLocation().getY()-size ));
                view.add(new Point(guard.getLocation().getX()+3*size, guard.getLocation().getY() ));
                view.add(new Point(guard.getLocation().getX()+3*size, guard.getLocation().getY()+size ));
                view.add(new Point(guard.getLocation().getX()+3*size, guard.getLocation().getY()+2*size ));
                view.add(new Point(guard.getLocation().getX()+3*size, guard.getLocation().getY()+3*size ));
                if(getExtendView()){
                    view.add(new Point(guard.getLocation().getX()+4*size, guard.getLocation().getY()-4*size ));
                    view.add(new Point(guard.getLocation().getX()+4*size, guard.getLocation().getY()-3*size ));
                    view.add(new Point(guard.getLocation().getX()+4*size, guard.getLocation().getY()-2*size ));
                    view.add(new Point(guard.getLocation().getX()+4*size, guard.getLocation().getY()-size ));
                    view.add(new Point(guard.getLocation().getX()+4*size, guard.getLocation().getY() ));
                    view.add(new Point(guard.getLocation().getX()+4*size, guard.getLocation().getY()+size ));
                    view.add(new Point(guard.getLocation().getX()+4*size, guard.getLocation().getY()+2*size ));
                    view.add(new Point(guard.getLocation().getX()+4*size, guard.getLocation().getY()+3*size ));
                    view.add(new Point(guard.getLocation().getX()+4*size, guard.getLocation().getY()+4*size ));
                    wasExtended=true;
                }
                wasReduced=false;
            }
        }
        if (facing=="L"){
            if(!wasReduced){
                if(!wasExtended){
                    if(getExtendView()) {
                        view.add(new Point(guard.getLocation().getX() - 4 * size, guard.getLocation().getY() - 4 * size));
                        view.add(new Point(guard.getLocation().getX() - 4 * size, guard.getLocation().getY() - 3 * size));
                        view.add(new Point(guard.getLocation().getX() - 4 * size, guard.getLocation().getY() - 2 * size));
                        view.add(new Point(guard.getLocation().getX() - 4 * size, guard.getLocation().getY() - size));
                        view.add(new Point(guard.getLocation().getX() - 4 * size, guard.getLocation().getY()));
                        view.add(new Point(guard.getLocation().getX() - 4 * size, guard.getLocation().getY() + size));
                        view.add(new Point(guard.getLocation().getX() - 4 * size, guard.getLocation().getY() + 2 * size));
                        view.add(new Point(guard.getLocation().getX() - 4 * size, guard.getLocation().getY() + 3 * size));
                        view.add(new Point(guard.getLocation().getX() - 4 * size, guard.getLocation().getY() + 4 * size));
                        wasExtended = true;
                    }
                    if(getReduceView()){
                        view.remove(14);
                        view.remove(13);
                        view.remove(12);
                        view.remove(11);
                        view.remove(10);
                        view.remove(9);
                        view.remove(8);
                        wasReduced=true;
                    }
                }
                else if(!getExtendView()){
                    view.remove(23);
                    view.remove(22);
                    view.remove(21);
                    view.remove(20);
                    view.remove(19);
                    view.remove(18);
                    view.remove(17);
                    view.remove(16);
                    view.remove(15);
                    if(getReduceView()){
                        view.remove(14);
                        view.remove(13);
                        view.remove(12);
                        view.remove(11);
                        view.remove(10);
                        view.remove(9);
                        view.remove(8);
                        wasReduced=true;
                    }
                    wasExtended=false;
                }
            }
            else if (!getReduceView()){
                view.add(new Point(guard.getLocation().getX()-3*size, guard.getLocation().getY()-3*size ));
                view.add(new Point(guard.getLocation().getX()-3*size, guard.getLocation().getY()-2*size ));
                view.add(new Point(guard.getLocation().getX()-3*size, guard.getLocation().getY()-size ));
                view.add(new Point(guard.getLocation().getX()-3*size, guard.getLocation().getY() ));
                view.add(new Point(guard.getLocation().getX()-3*size, guard.getLocation().getY()+size ));
                view.add(new Point(guard.getLocation().getX()-3*size, guard.getLocation().getY()+2*size ));
                view.add(new Point(guard.getLocation().getX()-3*size, guard.getLocation().getY()+3*size ));
                if(getExtendView()){
                    view.add(new Point(guard.getLocation().getX()-4*size, guard.getLocation().getY()-4*size ));
                    view.add(new Point(guard.getLocation().getX()-4*size, guard.getLocation().getY()-3*size ));
                    view.add(new Point(guard.getLocation().getX()-4*size, guard.getLocation().getY()-2*size ));
                    view.add(new Point(guard.getLocation().getX()-4*size, guard.getLocation().getY()-size ));
                    view.add(new Point(guard.getLocation().getX()-4*size, guard.getLocation().getY() ));
                    view.add(new Point(guard.getLocation().getX()-4*size, guard.getLocation().getY()+size ));
                    view.add(new Point(guard.getLocation().getX()-4*size, guard.getLocation().getY()+2*size ));
                    view.add(new Point(guard.getLocation().getX()-4*size, guard.getLocation().getY()+3*size ));
                    view.add(new Point(guard.getLocation().getX()-4*size, guard.getLocation().getY()+4*size ));
                    wasExtended=true;
                }
                wasReduced=false;
            }
        }
    }

    public int getSize() {
        return view.size();
    }

    public ArrayList<Point> getView() {
        return view;
    }

    public void setView(String nextFacing){

        int distanceX = 0;
        int distanceY = 0;
        int yNew = 0;
        int xNew = 0;
        int x = guard.getLocation().getX() ;
        int y = guard.getLocation().getY() ;
        //updateView();

        if(facing == "D"){

            if(nextFacing == "D"){
                for (int i = 0; i < view.size(); i++) {
                    nextView.add(view.get(i));
                }
            }


            if(nextFacing == "U"){
                for (int i = 0; i < view.size(); i++) {
                    distanceY = view.get(i).getY() - y;
                    yNew = y - distanceY ;
                    //Point point = new Point(view.get(i).getX(), yNew) ;
                    //point = new Point(view.get(i).getX(), yNew) ;
                    point = mapArray.getPoint(view.get(i).getX(), yNew) ;
                    if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                    }
                    if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                       // mapArray.setNumerator(mapArray.getNumerator() + 1);
                      //  gameFrame.setLabel(mapArray.getNumerator(),mapArray.getTotal());
                        sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                        sharedArray.setNumerator(sharedArray.getNumerator()+1);
                        gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

                    }
                   /* if(point.getX()==mapArray.getPoint(point.getX(), point.getY()).getX() && point.getY()==mapArray.getPoint(point.getX(), point.getY()).getY() && mapArray.getPoint(point.getX(), point.getY()).isLegal())
                        point.setLegal(false);*/
                    nextView.add(point);
                }
            }

            if(nextFacing == "R") {
                for (int i = 0; i < view.size(); i++) {
                    distanceY = view.get(i).getY() - y;
                    distanceX = view.get(i).getX() - x;

                    xNew = x + distanceY;
                    yNew = y - distanceX;

                    //Point point = new Point(xNew, yNew);
                    //point = new Point(xNew, yNew);
                    point = mapArray.getPoint(xNew,yNew) ;
                    if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                    }
                    if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        // mapArray.setNumerator(mapArray.getNumerator() + 1);
                        //  gameFrame.setLabel(mapArray.getNumerator(),mapArray.getTotal());
                        sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                        sharedArray.setNumerator(sharedArray.getNumerator()+1);
                        gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

                    }
                   /* if (point.getX() == mapArray.getPoint(point.getX(), point.getY()).getX() && point.getY() == mapArray.getPoint(point.getX(), point.getY()).getY() && mapArray.getPoint(point.getX(), point.getY()).isLegal())
                        point.setLegal(false);*/
                    nextView.add(point);
                }
            }

            if(nextFacing=="L"){
                for (int i = 0; i < view.size(); i++) {
                    distanceY = view.get(i).getY() - y;
                    distanceX = view.get(i).getX() - x;

                    xNew = x - distanceY;
                    yNew = y + distanceX;

                    //Point point = new Point(xNew, yNew);
                    //point = new Point(xNew, yNew);
                    point = mapArray.getPoint(xNew,yNew) ;
                    if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                    }
                    if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        // mapArray.setNumerator(mapArray.getNumerator() + 1);
                        //  gameFrame.setLabel(mapArray.getNumerator(),mapArray.getTotal());
                        sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                        sharedArray.setNumerator(sharedArray.getNumerator()+1);
                        gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

                    }
                   /* if (point.getX() == mapArray.getPoint(point.getX(), point.getY()).getX() && point.getY() == mapArray.getPoint(point.getX(), point.getY()).getY() && mapArray.getPoint(point.getX(), point.getY()).isLegal())
                        point.setLegal(false);*/
                    nextView.add(point);
                }
            }
        }

        if(facing=="U"){

            if(nextFacing == "U"){
                for (int i = 0; i < view.size(); i++) {
                    nextView.add(view.get(i));
                }
            }

            if(nextFacing=="D") {

                for (int i = 0; i < view.size(); i++) {
                    distanceY = view.get(i).getY() - y;
                    distanceX = view.get(i).getX() - x;

                    yNew = y + ((-1) * distanceY);

                   // Point point = new Point(view.get(i).getX(), yNew);
                   // point = new Point(view.get(i).getX(), yNew);
                    point = mapArray.getPoint(view.get(i).getX(),yNew) ;
                    if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                    }
                    if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        // mapArray.setNumerator(mapArray.getNumerator() + 1);
                        //  gameFrame.setLabel(mapArray.getNumerator(),mapArray.getTotal());
                        sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                        sharedArray.setNumerator(sharedArray.getNumerator()+1);
                        gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

                    }
                   /* if (point.getX() == mapArray.getPoint(point.getX(), point.getY()).getX() && point.getY() == mapArray.getPoint(point.getX(), point.getY()).getY() && mapArray.getPoint(point.getX(), point.getY()).isLegal())
                        point.setLegal(false);*/
                    nextView.add(point);
                }
            }

            if(nextFacing=="R"){

                for (int i = 0; i < view.size(); i++) {
                    distanceY = view.get(i).getY() - y;
                    distanceX = view.get(i).getX() - x;

                    xNew = x - distanceY;
                    yNew = y + distanceX;

                    //Point point = new Point(xNew, yNew);
                    //point = new Point(xNew, yNew);
                    point = mapArray.getPoint(xNew,yNew) ;
                    if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                    }
                    if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        // mapArray.setNumerator(mapArray.getNumerator() + 1);
                        //  gameFrame.setLabel(mapArray.getNumerator(),mapArray.getTotal());
                        sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                        sharedArray.setNumerator(sharedArray.getNumerator()+1);
                        gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

                    }
                  /*  if (point.getX() == mapArray.getPoint(point.getX(), point.getY()).getX() && point.getY() == mapArray.getPoint(point.getX(), point.getY()).getY() && mapArray.getPoint(point.getX(), point.getY()).isLegal())
                        point.setLegal(false);*/
                    nextView.add(point);
                }
            }

            if(nextFacing =="L") {
                for (int i = 0; i < view.size(); i++) {
                    distanceY = view.get(i).getY() - y;
                    distanceX = view.get(i).getX() - x;

                    xNew = x + distanceY;
                    yNew = y - distanceX;

                    //Point point = new Point(xNew, yNew);
                    //point = new Point(xNew, yNew);
                    point = mapArray.getPoint(xNew,yNew) ;
                    if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                    }
                    if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        // mapArray.setNumerator(mapArray.getNumerator() + 1);
                        //  gameFrame.setLabel(mapArray.getNumerator(),mapArray.getTotal());
                        sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                        sharedArray.setNumerator(sharedArray.getNumerator()+1);
                        gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

                    }
                 /*   if (point.getX() == mapArray.getPoint(point.getX(), point.getY()).getX() && point.getY() == mapArray.getPoint(point.getX(), point.getY()).getY() && mapArray.getPoint(point.getX(), point.getY()).isLegal())
                        point.setLegal(false);*/
                    nextView.add(point);
                }
            }
        }

        if(facing=="R"){

            if(nextFacing == "R"){
                for (int i = 0; i < view.size(); i++) {
                    nextView.add(view.get(i));
                }
            }

            if(nextFacing=="L") {

                for (int i = 0; i < view.size(); i++) {
                    distanceX = view.get(i).getX() - x;

                    xNew = x - distanceX;

                   // Point point = new Point(xNew, view.get(i).getY());
                    //point = new Point(xNew, view.get(i).getY());
                    point = mapArray.getPoint(xNew,view.get(i).getY()) ;
                    if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                    }
                    if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        // mapArray.setNumerator(mapArray.getNumerator() + 1);
                        //  gameFrame.setLabel(mapArray.getNumerator(),mapArray.getTotal());
                        sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                        sharedArray.setNumerator(sharedArray.getNumerator()+1);
                        gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

                    }
                  /*  if (point.getX() == mapArray.getPoint(point.getX(), point.getY()).getX() && point.getY() == mapArray.getPoint(point.getX(), point.getY()).getY() && mapArray.getPoint(point.getX(), point.getY()).isLegal())
                        point.setLegal(false);*/
                    nextView.add(point);
                }
            }

            if(nextFacing=="U") {

                for (int i = 0; i < view.size(); i++) {
                    distanceY = view.get(i).getY() - y;
                    distanceX = view.get(i).getX() - x;

                    xNew = x + distanceY;
                    yNew = y - distanceX;

                    //Point point = new Point(xNew, yNew);
                    //point = new Point(xNew, yNew);
                    point = mapArray.getPoint(xNew,yNew) ;
                    if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                    }
                    if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        // mapArray.setNumerator(mapArray.getNumerator() + 1);
                        //  gameFrame.setLabel(mapArray.getNumerator(),mapArray.getTotal());
                        sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                        sharedArray.setNumerator(sharedArray.getNumerator()+1);
                        gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

                    }
                   /* if (point.getX() == mapArray.getPoint(point.getX(), point.getY()).getX() && point.getY() == mapArray.getPoint(point.getX(), point.getY()).getY() && mapArray.getPoint(point.getX(), point.getY()).isLegal())
                        point.setLegal(false);*/
                    nextView.add(point);
                }
            }

            if(nextFacing=="D") {
                for (int i = 0; i < view.size(); i++) {
                    distanceY = view.get(i).getY() - y;
                    distanceX = view.get(i).getX() - x;

                    xNew = x - distanceY;
                    yNew = y + distanceX;

                    //Point point = new Point(xNew, yNew);
                    //point = new Point(xNew, yNew);
                    point = mapArray.getPoint(xNew,yNew) ;
                    if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                    }
                    if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        // mapArray.setNumerator(mapArray.getNumerator() + 1);
                        //  gameFrame.setLabel(mapArray.getNumerator(),mapArray.getTotal());
                        sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                        sharedArray.setNumerator(sharedArray.getNumerator()+1);
                        gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

                    }
                   /* if (point.getX() == mapArray.getPoint(point.getX(), point.getY()).getX() && point.getY() == mapArray.getPoint(point.getX(), point.getY()).getY() && mapArray.getPoint(point.getX(), point.getY()).isLegal())
                        point.setLegal(false);*/
                    nextView.add(point);
                }
            }
        }

        if(facing=="L"){

            if(nextFacing == "L"){
                for (int i = 0; i < view.size(); i++) {
                    nextView.add(view.get(i));
                }
            }
            if(nextFacing=="R") {
                for (int i = 0; i < view.size(); i++) {
                    distanceX = view.get(i).getX() - x;
                    xNew = x - distanceX;

                    //Point point = new Point(xNew, view.get(i).getY());
                    //point = new Point(xNew, view.get(i).getY());
                    point = mapArray.getPoint(xNew,view.get(i).getY()) ;
                    if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                    }
                    if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        // mapArray.setNumerator(mapArray.getNumerator() + 1);
                        //  gameFrame.setLabel(mapArray.getNumerator(),mapArray.getTotal());
                        sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                        sharedArray.setNumerator(sharedArray.getNumerator()+1);
                        gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

                    }
                   /* if (point.getX() == mapArray.getPoint(point.getX(), point.getY()).getX() && point.getY() == mapArray.getPoint(point.getX(), point.getY()).getY() && mapArray.getPoint(point.getX(), point.getY()).isLegal())
                        point.setLegal(false);*/
                    nextView.add(point);
                }
            }

            if(nextFacing=="U") {
                for (int i = 0; i < view.size(); i++) {
                    distanceY = view.get(i).getY() - y;
                    distanceX = view.get(i).getX() - x;

                    xNew = x - distanceY;
                    yNew = y + distanceX;

                    //Point point = new Point(xNew, yNew);
                    //point = new Point(xNew, yNew);
                    point = mapArray.getPoint(xNew,yNew) ;
                    if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                    }
                    if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                         sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                        sharedArray.setNumerator(sharedArray.getNumerator()+1);
                        gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

                    }
                    nextView.add(point);
                }
            }

            if(nextFacing=="D") {
                for (int i = 0; i < view.size(); i++) {
                    distanceY = view.get(i).getY() - y;
                    distanceX = view.get(i).getX() - x;

                    xNew = x + distanceY;
                    yNew = y - distanceX;

                    //Point point = new Point(xNew, yNew);
                    //point = new Point(xNew, yNew);
                    point = mapArray.getPoint(xNew,yNew) ;
                    if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                    }
                    if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                        // mapArray.setNumerator(mapArray.getNumerator() + 1);
                        //  gameFrame.setLabel(mapArray.getNumerator(),mapArray.getTotal());
                        sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                        sharedArray.setNumerator(sharedArray.getNumerator()+1);
                        gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

                    }
                   /* if (point.getX() == mapArray.getPoint(point.getX(), point.getY()).getX() && point.getY() == mapArray.getPoint(point.getX(), point.getY()).getY() && mapArray.getPoint(point.getX(), point.getY()).isLegal())
                        point.setLegal(false);*/
                    nextView.add(point);
                }
            }
        }
        view.clear();
        for (Point vp : nextView) {
            int idValue = Integer.parseInt(guard.getmyID());
            //if the current agent is a guard and it saw an intruder
            if(idValue< 50 && isOccupiedByIntruder(vp)){ 
                Point yf = sharedArray.getPoint(vp.getX(), vp.getY());
                Intruder Intruder = (Intruder)sharedArray.getPoint(vp.getX(), vp.getY()).getOccupier(); 
                Intruder.setCaught(true);
                System.out.println("got one");
            }
            view.add(vp);
        }
        nextView.clear();
        facing = nextFacing ;
    }

    public void setFacing(String facingg){
        facing=facingg ;
    }

    public void moveViewRandom(String nextFacing){
        if(nextFacing=="R"){
            for (int i = 0; i < view.size(); i++) {
                view.get(i).setX(view.get(i).getX()+1);
            }
        }

        if(nextFacing=="L"){
            for (int i = 0; i < view.size(); i++) {
                view.get(i).setX(view.get(i).getX()-1);
            }
        }

        if(nextFacing=="D"){
            for (int i = 0; i < view.size(); i++) {
                view.get(i).setY(view.get(i).getY()+1);
            }
        }

        if(nextFacing=="U"){
            for (int i = 0; i < view.size(); i++) {
                view.get(i).setY(view.get(i).getY()-1);
            }
        }

    }

    public void setTeleportView(int x, int y, Point teleportTarget){
        int xx,yy;
        
        if(x<teleportTarget.getX())
               xx =  Math.abs(x-teleportTarget.getX()) ;
            else
                xx = Math.abs(x-teleportTarget.getX()) * (-1) ;

            if(y<teleportTarget.getY())
                yy = Math.abs(y- teleportTarget.getY()) ;
            else
                yy = Math.abs(y- teleportTarget.getY())*(-1) ;

            for (int i = 0; i < getView().size(); i++) {
                if(getView().get(i).getX()+xx<0 || getView().get(i).getY()+yy<0){ 
                    System.out.println(x+" "+y+ " "+xx+ " "+yy+ " " + getView().get(i).getX()+xx +" " +getView().get(i).getY()+yy+ " ");}

                Point p = mapArray.getPoint(getView().get(i).getX()+xx,getView().get(i).getY()+yy) ;

                getNextView().add(p);
            }
            getView().clear();
            for (int i = 0; i < getNextView().size(); i++) {
                getView().add(getNextView().get(i));
            }
            getNextView().clear();


            if((teleportTarget.getTeleportOrientationview()>=0 && teleportTarget.getTeleportOrientationview()<=45)||(teleportTarget.getTeleportOrientationview()>=315&&teleportTarget.getTeleportOrientationview()<=360))
                setView("R");

            if((teleportTarget.getTeleportOrientationview()>=45 && teleportTarget.getTeleportOrientationview()<=135))
                setView("U");

            if((teleportTarget.getTeleportOrientationview()>=135 && teleportTarget.getTeleportOrientationview()<=225))
                setView("L");

            if((teleportTarget.getTeleportOrientationview()>=225 && teleportTarget.getTeleportOrientationview()<=225))
                setView("D");
        }

    public int getLength(){return length ; }


    public boolean isOccupiedByIntruder(Point p){
        int x = p.getX();
        int y = p.getY();
        Point sharedPoint = sharedArray.getPoint(x, y); 
        if(sharedPoint.getOccupier()==null) return false; //if nobody is on this cell
        else{
            int idValue = Integer.parseInt(sharedPoint.getOccupier().getmyID());
            if(idValue >= 50){
                // Intruders have id's of 50 and higher
                return true;
            }
        }
        return false;
    }
}

