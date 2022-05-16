package app;

import java.util.ArrayList;

public class Guard implements Agent {
    
    private final int size ;
    private Point location ;
    private MapArray mapArray ;
    private Scenario scenario ;
    private View view ;
    private String facing ;
    GameFrame gameFrame ;
    private MapArray sharedArray ;
    private ArrayList<TelePortal> teleports ;
    private String myID;
    private BigSquares BS;
    private MoveRandom MR;
    private boolean I_heared_noise;
    Actions action;





    public Guard(Scenario scenario,Point location, GameFrame gameFrame, String id){
        this.location = location ;
        this.scenario = scenario ;
        size = scenario.getScale() ;
        this.gameFrame = gameFrame ;
        mapArray = new MapArray(scenario, gameFrame) ;
        sharedArray = gameFrame.getSharred();
        setLocation(new Point(4* scenario.getScale(),4* scenario.getScale()));
        view = new View(this,gameFrame) ;
        action = new Actions(this);
        teleports= scenario.getTeleportals();
        facing = "D" ;
        myID = id;
        BS = new BigSquares(this); 
        MR = new MoveRandom(this);
        I_heared_noise = false;
    }

    // GETTERS & SETTERS

    public MapArray getMapArray() {return mapArray;}

    public ArrayList<Point> getView(){return view.getView() ; }

    public View getViewObject(){ return view;}

    public MapArray getSharedArray(){ return sharedArray ; }

    public int getSize() { return size; }
   
    public Actions getAction(){ return action;}
    
    public GameFrame getGameFrame(){ return gameFrame;}

    public String getFacing(){ return facing;}

    public void setFacing(String f){ facing = f;}

    public Point getLocation() {return location;}

    public Scenario getScenerio(){ return scenario;}

    public String getmyID(){ return myID;}

    public boolean heared_noise(){ return I_heared_noise;}

    public void set_heared_noise(boolean b){ I_heared_noise = b;}

    public void setLocation(Point locationn) {
        sharedArray.getPoint(location.getX(), location.getY()).setOccupier(null); //we make the current point unoccupied 
        this.location = locationn; //we move to the next position 
        sharedArray.getPoint(location.getX(), location.getY()).setOccupier(this);  //we set the new position as occupied
    }

    // MOVEMENT ALGORITHMS

    public void moveRandom(){
        MR.executeRndMove();
    }

    public void moveBigSaures() { 
        BS.execute();
    }

    public void moveViewRandom(){
        MR.executeRndView();     
    }

    public boolean isGuard(){
        int int_id = Integer.parseInt(myID);
        if(int_id>50){ return false;} //Intruders have id 50 and above
        return true;
    }


    public void teleports(){

        int x = location.getX();
        int y = location.getY();

        Point pp = mapArray.getPoint(location.getX(), location.getY()) ;
        Point teleportTarget = mapArray.getPoint(pp.getX_teleport(), pp.getY_teleport()) ;

        if(pp.isIsteleport()){

            setLocation(mapArray.getPoint(mapArray.getPoint(location.getX(), location.getY()).getX_teleport(),mapArray.getPoint(location.getX(), location.getY()).getY_teleport())) ;

            if(teleportTarget.getTeleportOrientationview()>=90 && teleportTarget.getTeleportOrientationview()<=315)
                view.setView("U");

            for (int i = 0; i < mapArray.getMapArray().length; i++) {
                for (int j = 0; j < mapArray.getMapArray()[0].length; j++) {
                    if(mapArray.getMapArray()[i][j].isIsteleport()) mapArray.getMapArray()[i][j].setVisited(true);

                }

            }
            view.setTeleportView(x, y, teleportTarget);
    }

}

    // public void teleports(){

    //     int x = location.getX();
    //     int y = location.getY();
    //     Point pp = mapArray.getPoint(location.getX(), location.getY()) ;
    //     Point teleportTarget = mapArray.getPoint(pp.getX_teleport(), pp.getY_teleport()) ;
    //     if(pp.isIsteleport()){
    //         pp.setVisited(true);
    //         setLocation(teleportTarget) ;
    //         if(teleportTarget.getTeleportOrientationview()>=90 && teleportTarget.getTeleportOrientationview()<=315)
    //             view.setView("U");
    //     }
    //     view.setTeleportView(x, y, teleportTarget);
    // }
}
