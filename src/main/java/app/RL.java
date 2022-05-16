package app;

import java.util.ArrayList;


// The whole methods are built for intruders 

public class RL {

    ArrayList<ReplayMemory> replayMemory;
    final int wall_penalty = -2;
    final int caught_penalty = -10;
    final int target_reward = 10;
    final int targetLine_reward = 3;
    Agent agent;
    Point Intruders_target;
    Area target;

    public RL(Agent agent, Area target){
        this.agent = agent;
        this.target = target;
    }

    public void update_replay_memory(ReplayMemory newOne){
        replayMemory.add(newOne);
    }

    public void takeAction(int choice){

        Actions action_obj = agent.getAction();

        if(choice == 1) action_obj.lookeUp();
        if(choice == 2) action_obj.lookDown();
        if(choice == 3) action_obj.lookLeft();
        if(choice == 5) action_obj.lookRight();
        if(choice == 6) action_obj.moveUp();
        if(choice == 7) action_obj.moveDown();
        if(choice == 8) action_obj.moveLeft();
        if(choice == 9) action_obj.moveRight();
        if(choice == 10) action_obj.leaveMarker();
    }

    public int calcReward(Point position){
        int reward = 0;
        if(!agent.isGuard()){ //if this is agent is an intruder
            Intruder intruder = (Intruder) agent;
            
            if(target.isHit(position.getX(), position.getY())){ //if intruder is reached target
                reward += target_reward;
            }
            if(position.getMarkedOnLine()){ //if the intruder is on the right path to target
                reward += targetLine_reward;
            }
            if(intruder.isCaught()){//if the intruder is seen by the guard 
                reward += caught_penalty;
            }
            //This statement is not supposed to happen bcs the way move is designed
            if(!intruder.getMapArray().notWall(position)){  //if the intruder hit a wall
                reward += wall_penalty;
            }
        }
        return reward;
    }
}

    class ReplayMemory {
        Point position;
        Actions action;
        int reward;
        Point newPoisition;

        public ReplayMemory(Point position, Actions action, int reward, Point newPoisition) {
            this.position = position;
            this.action = action;
            this.reward = reward;
            this.newPoisition = newPoisition;
        }

        public Point getPosition(){ return position;}
        public void setPosition(Point p){ position = p;}
        public Actions getAction(){ return action;}
        public void setAction(Actions a){ action = a;}
        public int getReward(){ return reward;}
        public void setReward(int r){ reward =r;}
        public Point getNewPosition() { return newPoisition;}
        public void setNewPosition(Point newP){ newPoisition = newP; }  
        
 
    }
    

