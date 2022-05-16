package app;

import java.util.ArrayList;
import java.util.Random;

public class BigSquares {
    
    private Agent guard;
    private Point lastLoc;
    private Actions action;
    public static int move_count =0;

   public BigSquares(Agent p){
    guard = p;
    lastLoc = chooseRnd(getBigNeighbours(guard.getLocation()));
    action = guard.getAction();
    action.spin360();

   }
   public static void setMove_count_Zero(){
       move_count = 0 ;
   }


   public void execute(){

        action.teleport();

       Point current_cell = guard.getLocation();
        action.spin360();
        ArrayList<Point> neighbours = getBigNeighbours(current_cell);

            if(!current_cell.isMDFS_explored()){ //if current cell is unexplored
                current_cell.set_MDFS_explored(true);
                current_cell.set_MDFS_id(guard.getmyID());
                current_cell.set_MDFS_parent(lastLoc);
            }

            if(thereIsUnexplored_neighbour(neighbours)){ //if there is unexplored neighbours around
                Point rndUnexplored = chooseRnd(get_Unexplored_Neighbours(neighbours));
                lastLoc = current_cell;
                walk_to(current_cell, rndUnexplored);
               // System.out.println("one");
                move_count = move_count+1;
            }

            else{ //if all the neighbourse are explored 

                if(current_cell.getMDFS_id().equals(guard.getmyID())){ //if it was explored by me
                    current_cell.set_MDFS_visited(true);
                    lastLoc = current_cell;
                    walk_to(current_cell, current_cell.getMDFS_parent());
                   // System.out.println("two");
                    move_count = move_count+1;
                }
                else{ //if it was explored by someone else
                    Point rndExplored = chooseRnd(neighbours);
                    lastLoc = current_cell;
                    walk_to(current_cell, rndExplored);
                  //  System.out.println("Three");
                    move_count = move_count+1;
                }
            }
        }




    public ArrayList<Point> getBigNeighbours(Point location){
        ArrayList<Point> bigN = new ArrayList<>();
        int radius = guard.getViewObject().getLength();
        int scale = guard.getSize();
        int x = location.getX();
        int y = location.getY();
        
        //LEFT
        boolean canAdd2Arr = true;
        for (int i = 0; i < radius; i++) {
            if(!guard.getMapArray().getPoint(x-scale*i, y).isLegal()){
                canAdd2Arr=false;
                break;
            }  
    
        }
        if(canAdd2Arr) bigN.add(guard.getMapArray().getPoint(x-scale*(radius-1), y));
        
        //RIGHT
        canAdd2Arr = true;
        for (int i = 0; i < radius; i++) {
            if(!guard.getMapArray().getPoint(x+scale*i, y).isLegal()){
                canAdd2Arr=false;
                break;
            }  
        }

        if(canAdd2Arr) bigN.add(guard.getMapArray().getPoint(x+scale*(radius-1), y));

                //UP
                canAdd2Arr = true;
                for (int i = 0; i < radius; i++) {
                    if(!guard.getMapArray().getPoint(x, y-scale*i).isLegal()){
                        canAdd2Arr=false;
                        break;
                    }  
            
                }
                if(canAdd2Arr) bigN.add(guard.getMapArray().getPoint(x, y-scale*(radius-1)));
                
                //Down
                canAdd2Arr = true;
                for (int i = 0; i < radius; i++) {
                    if(!guard.getMapArray().getPoint(x, y+scale*i).isLegal()){
                        canAdd2Arr=false;
                        break;
                    }  
                }
        
        if(canAdd2Arr) bigN.add(guard.getMapArray().getPoint(x, y+scale*(radius-1)));
        return bigN;
    }

    
    public boolean isInBounds(int x, int y){
        x=x/guard.getScenerio().getScale();
        y=y/guard.getScenerio().getScale(); 
        if(x >= guard.getScenerio().mapWidth || x<0 || y>= guard.getScenerio().mapHeight || y<0) return false;
        
        if(!guard.getMapArray().getPoint(x, y).isLegal()) return false;
        return true;
    }

    public ArrayList<Point> get_Unexplored_Neighbours(ArrayList<Point> bigN){
        ArrayList<Point> unexplored = new ArrayList<>();
        for(Point p: bigN){
            if(!p.isMDFS_explored()) unexplored.add(p);
        }
        return unexplored;
    }

    public boolean thereIsUnexplored_neighbour(ArrayList<Point> neighbours){
        if(get_Unexplored_Neighbours(neighbours).size()>0) return true;
        return false;
    }

    public Point chooseRnd(ArrayList<Point> array){
        int Arrsize = array.size();
      
        int rndIndex = new Random().nextInt(Arrsize);
        return array.get(rndIndex);

    }


    public void walk_to(Point current, Point target){
        String facing = getDirectionfromPoints(current, target);
        
        for(int i=0; i<guard.getViewObject().getLength(); i++){
            if(facing.equals("U")) action.moveUp();
            else if(facing.equals("D")) action.moveDown();
            else if(facing.equals("L")) action.moveLeft();
            else if(facing.equals("R")) action.moveRight();
            // action.moveInDirection(facing);
            action.spin360();
            guard.getViewObject().setView(facing);
        }
    } 

    /**
     * @param currentLoc is the current location of the agent
     * The method makes the agent be able to see 360
     */
    public void spin360(Point currentLoc){
        View v = guard.getViewObject();
        v.setView("R");
        v.setView("L");
        v.setView("U");
        v.setView("D");
    }

    /**
     * 
     * @param a current position
     * @param b target position
     * @return where the agent is facing
     */

    public String getDirectionfromPoints(Point a, Point b){
        int xa = a.getX();
        int ya = a.getY();
        int xb = b.getX();
        int yb = b.getY();
        
        if(xa==xb && ya!=yb){
            if(ya<yb) return "D";
            else      return "U";
        }

        if(xa!=xb && ya==yb){
            if(xa>xb) return "L";
            else      return "R";
        }
        return "Wrong";
    }
}
