package app;

import java.security.cert.PolicyQualifierInfo;
import java.util.ArrayList;
import java.util.Scanner;

public class Actions {
    
    Agent agent;
    Point location;
    String facing;
    int size;
    View view;
    MapArray mapArray;
    MapArray sharedArray;
    GameFrame gameFrame;


    /**
     * Current Possible actions:
     * 1.Move UP
     * 2.Move Down
     * 3.Move Right
     * 4.Move Left
     * 5.Look UP
     * 6.Look Down
     * 7.Look Right
     * 8.Look Left
     * 9.Leave Marker
     * 10.Spin 360
     * 11.Teleport
     */

    public Actions(Agent agent){
        this.agent = agent;
        location = agent.getLocation();
        size = agent.getSize();
        view = agent.getViewObject();
        mapArray = agent.getMapArray();
        facing = agent.getFacing();
        sharedArray = agent.getSharedArray();
        gameFrame = agent.getGameFrame();
    }

    public void lookeUp(){ view.setView("U");}

    public void lookDown(){ view.setView("D");}

    public void lookRight(){ view.setView("R");}

    public void lookLeft(){ view.setView("L");}

    public void leaveMarker(){
        location = agent.getLocation();
        facing = agent.getFacing();
        sharedArray.getPoint(location.getX(), location.getY()).setMarked(true);
        mapArray.getPoint(location.getX(), location.getY()).setMarked(true);
    }

    public void spin360(){
        View v = agent.getViewObject();
        v.setView("R");
        v.setView("L");
        v.setView("U");
        v.setView("D");
    }

    public void teleport(){
        agent.teleports();
    }

    public void moveUp(){
        location = agent.getLocation();
        facing = agent.getFacing();

        Point point = new Point(location.getX(), location.getY()-size) ;
        Point pointView = new Point(location.getX(), location.getY()-(view.getLength()*size)) ;
        //generalMove(point, pointView, "direction");
        if(mapArray.notWallsnotBounds(pointView)){
        //  if(pointView.getX()==mapArray.getPoint(pointView.getX(), pointView.getY()).getX()&&pointView.getY()==mapArray.getPoint(pointView.getX(), pointView.getY()).getY()&&mapArray.getPoint(pointView.getX(), pointView.getY()).isLegal()) {
            view.setView(facing);
            agent.setLocation(point);

            if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
            }
            if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                sharedArray.setNumerator(sharedArray.getNumerator()+1);
                gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

            }

            for (int i = 0; i < view.getView().size(); i++) {
                Point p = new Point(view.getView().get(i).getX(),view.getView().get(i).getY()-size) ;
                view.getNextView().add(p);
            }
            view.getView().clear();
            for (int i = 0; i < view.getNextView().size(); i++) {
                view.getView().add(view.getNextView().get(i));
            }
            view.getNextView().clear();
    }
}

    public void moveDown(){
        location = agent.getLocation();
        facing = agent.getFacing();

        Point point = new Point(location.getX(), location.getY() + size);
        Point pointView = new Point(location.getX(), location.getY() + view.getLength() * size);
        if(mapArray.notWallsnotBounds(pointView)){
            view.setView(facing);
            agent.setLocation(point);
            if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
            }
            if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                sharedArray.setNumerator(sharedArray.getNumerator()+1);
                gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

            }
            for (int i = 0; i < view.getView().size(); i++) {
                Point p = new Point(view.getView().get(i).getX(),view.getView().get(i).getY()+size) ;
                view.getNextView().add(p);
            }
            view.getView().clear();
            for (int i = 0; i < view.getNextView().size(); i++) {
                view.getView().add(view.getNextView().get(i));
            }
            view.getNextView().clear();

        }
    }

    public void moveLeft(){
        location = agent.getLocation();
        facing = agent.getFacing();

        Point point = new Point(location.getX()-size, location.getY()) ;
        Point pointView = new Point(location.getX()-(view.getLength()*size), location.getY()) ;
        if(mapArray.notWallsnotBounds(pointView)){
            view.setView(facing);
            agent.setLocation(point);
            if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
            }
            if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                sharedArray.setNumerator(sharedArray.getNumerator()+1);
                gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

            }

            for (int i = 0; i < view.getView().size(); i++) {
                Point p = new Point(view.getView().get(i).getX()-size,view.getView().get(i).getY()) ;
                view.getNextView().add(p);
            }
            view.getView().clear();
            for (int i = 0; i < view.getNextView().size(); i++) {
                view.getView().add(view.getNextView().get(i));
            }
            view.getNextView().clear();
        }
    }

    public void moveRight(){
        location = agent.getLocation();
        facing = agent.getFacing();

        Point point = new Point(location.getX()+size, location.getY()) ;
        Point pointView = new Point(location.getX()+view.getLength()*size, location.getY()) ;
        if(mapArray.notWallsnotBounds(pointView)){
            view.setView(facing);
            agent.setLocation(point);

            if(!mapArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                mapArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
            }
            if(!sharedArray.getMapArray()[point.getX()/size][point.getY()/size].isVisited()) {
                sharedArray.getMapArray()[point.getX() / size][point.getY() / size].setVisited(true);
                sharedArray.setNumerator(sharedArray.getNumerator()+1);
                gameFrame.setLabel(sharedArray.getNumerator(),sharedArray.getTotal());

            }

            for (int i = 0; i < view.getView().size(); i++) {
                Point p = new Point(view.getView().get(i).getX()+size,view.getView().get(i).getY()) ;
                view.getNextView().add(p);
            }
            view.getView().clear();
            for (int i = 0; i < view.getNextView().size(); i++) {
                view.getView().add(view.getNextView().get(i));
            }
            view.getNextView().clear();
        }
    }
    public void shout() {
        location = agent.getLocation();
        int radius = 5;
        Point pointTopLeft = new Point(location.getX() - radius, location.getY() - radius);
        Point pointBottomRight = new Point(location.getX() + radius, location.getY() + radius);
        Area screamArea = new Area(pointTopLeft.getX(), pointTopLeft.getY(), pointBottomRight.getX(), pointBottomRight.getY());
        ArrayList<Point> points = screamArea.getPointList(mapArray);
        System.out.println(points);
        for (Point P : points) {
            if (P.getX() == location.getX() && P.getY() == location.getY()) {
                continue;
            } // obviously I hear myself so we move on
            if (P.isOccupied()) {
                Agent hearing_agent = P.getOccupier();
                hearing_agent.set_heared_noise(true);
            }
        }
    }

}
