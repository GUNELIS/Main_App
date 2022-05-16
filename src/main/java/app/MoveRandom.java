package app;

public class MoveRandom {
    public static int count_move=0;
    
    Agent agent;
    Actions action;

   public MoveRandom(Agent p){
    agent = p;
    action = agent.getAction();
//    action.spin360(agent.getLocation());
   } 

    public static void setCount_move_Zero(){
        count_move = 0 ;
    }

   public void executeRndMove(){

        action.teleport();

        int randomFace = (int) (Math.random() * 4 + 1)+1;
        if (randomFace==2){
            agent.setFacing("R");
            action.moveRight();
            count_move = count_move +1;
        }
        if (randomFace==3){
            agent.setFacing("L");
            action.moveLeft();
            count_move = count_move +1;
        }
        if (randomFace==4){
            agent.setFacing("U");
            action.moveUp();
            count_move = count_move +1;
        }
        if (randomFace==5){
            agent.setFacing("D");
            action.moveDown();
            count_move = count_move +1;
        }
    }


    public void executeRndView(){


        int randomFace = (int) (Math.random() * 4 + 1)+1;
        if (randomFace==2) {
            action.lookeUp();
        }
        if (randomFace==3) {
            action.lookDown();
        }
        if (randomFace==4) {
            action.lookLeft();
        }
        if (randomFace==5) {
            action.lookRight();
        }
    }
}
