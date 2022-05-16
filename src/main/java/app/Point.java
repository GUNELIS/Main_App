package app;

public class Point {

    private int x ;
    private int y ;
    private boolean legal ;
    private boolean visited ;
    private boolean isteleport ;
    private int x_teleport ;
    private int y_teleport ;
    private double teleportOrientationview ;
    private boolean MDFS_visited;
    private boolean MDFS_explored;
    private Point MDFS_parent;
    private String MDFS_id;
    private Agent occupier;
    private boolean markedonline = false;
    private boolean marked = false;
    private boolean exploredBM = false ;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        legal = true ;
        isteleport = false ;
        visited=false ;
        exploredBM = false ;

    }

    public void setExploredBM(){
        exploredBM = true ;
    }

    public boolean isExploredBM() {
        return exploredBM;
    }

    public Point(){}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isOccupied(){ 
        if(occupier!=null) return true;
        return false;
    }

    public Agent getOccupier(){ return occupier;}
    
    public void setOccupier(Agent o){ occupier = o;}

    public void setMarked(boolean m){ marked = m;}

    public boolean getmarked(){ return marked;}


    
    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }

    public boolean isLegal() {
        return legal;
    }

    public void setLegal(boolean legal) {
        this.legal = legal;
    }

    public boolean isVisited() {return visited;}
    public void setVisited(boolean visited) {this.visited = visited;}

    public boolean isIsteleport() {
        return isteleport;
    }

    public void setIsteleport(boolean teleport){
        this.isteleport=teleport ;
    }

    public void setY_teleport(int y_teleport) {
        this.y_teleport = y_teleport;
        //System.out.println("y"+this.y_teleport);
    }

    public void setX_teleport(int x_teleport) {
        this.x_teleport = x_teleport;
        //System.out.println(this.x_teleport);
    }

    public int getX_teleport() {
        return x_teleport;
    }

    public int getY_teleport() {
        return y_teleport;
    }

    public double getTeleportOrientationview() {
        return teleportOrientationview;
    }

    public void setTeleportOrientationview(double teleportOrientationview) {
        this.teleportOrientationview = teleportOrientationview;
    }
    
    public boolean isMDFS_visited() {return MDFS_visited;}
    public void set_MDFS_visited(boolean v) {this.MDFS_visited = v;}
    
    public boolean isMDFS_explored() {return MDFS_explored;}
    public void set_MDFS_explored(boolean e) {this.MDFS_explored = e;}

    public Point getMDFS_parent() {return MDFS_parent;}
    public void set_MDFS_parent(Point p) {this.MDFS_parent = p;}

    public String getMDFS_id() {return MDFS_id;}
    public void set_MDFS_id(String id) {this.MDFS_id = id;}
    
    public void setMarkedOnLine(){markedonline = true;}
    public boolean getMarkedOnLine(){return markedonline;}


}

