package app;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BrickMortar {
    public static int counter = 0;
    Agent agent;
    MapArray mapAlgo;
    Scenario scenario;
    GameFrame gameFrame;
    ArrayList<Point> neighbour ;
    ArrayList<Integer> countWallsNeighbours ;
    int scale ;
    Graph graph ;
    Point nn ;
    Point ne ;
    Point no ;
    Point ns ;
    Point nextLocation ;



    public BrickMortar(Agent agent) {
        this.agent = agent;
        scenario = agent.getScenerio();
        scale = scenario.getScale();
        gameFrame = agent.getGameFrame();
        mapAlgo = new MapArray(scenario, gameFrame);
        neighbour = new ArrayList<>() ;
    }

    public static void setCounter_Zero(){
        counter = 0 ;
    }

    public void execute(){

        // Step 1 mark current cell

        if(pathExisting())
            mapAlgo.getPoint(agent.getLocation().getX(),agent.getLocation().getY()).setVisited(true);
        else
            mapAlgo.getPoint(agent.getLocation().getX(),agent.getLocation().getY()).setExploredBM();

        // Step 2 navigation

        nn = mapAlgo.getPoint(agent.getLocation().getX(),agent.getLocation().getY()-scale) ;
        ns = mapAlgo.getPoint(agent.getLocation().getX(),agent.getLocation().getY()+scale) ;
        ne = mapAlgo.getPoint(agent.getLocation().getX()+scale,agent.getLocation().getY()) ;
        no = mapAlgo.getPoint(agent.getLocation().getX()-scale,agent.getLocation().getY()) ;

        if(checkUnexploredNeighbour()) {
            counter = counter +1;
            nextLocation = chooseUnexploredNeighbour();
        }





    }
    public Point chooseUnexploredNeighbour(){
        neighbour.clear();
        countWallsNeighbours.clear();

        neighbour.add(nn) ;
        neighbour.add(ns) ;
        neighbour.add(ne);
        neighbour.add(no) ;


        int max = -1 ;

        int count ;

        for (int i = 0; i < neighbour.size(); i++) {
            count = 0 ;

            Point current = neighbour.get(i) ;

            int x = current.getX();
            int y = current.getY() ;

            Point p0 = mapAlgo.getPoint(x - scale, y - scale);
            if(p0.isVisited()||p0.isLegal())
                count = count+1 ;

            Point p1 = mapAlgo.getPoint(x, y - scale);
            if(p1.isVisited()||p1.isLegal())
                count = count+1 ;

            Point p2 = mapAlgo.getPoint(x + scale, y - scale);
            if(p2.isVisited()||p2.isLegal())
                count = count+1 ;

            Point p3 = mapAlgo.getPoint(x - scale, y);
            if(p3.isVisited()||p3.isLegal())
                count = count+1 ;
            Point p5 = mapAlgo.getPoint(x + scale, y);
            if(p5.isVisited()||p5.isLegal())
                count = count+1 ;

            Point p6 = mapAlgo.getPoint(x - scale, y + scale);
            if(p6.isVisited()||p6.isLegal())
                count = count+1 ;

            Point p7 = mapAlgo.getPoint(x, y + scale);
            if(p7.isVisited()||p7.isLegal())
                count = count+1 ;

            Point p8 = mapAlgo.getPoint(x + scale, y + scale);
            if(p8.isVisited()||p8.isLegal())
                count = count+1 ;

            countWallsNeighbours.add(count);
        }

        int getindexMax = -1;
        for (int i = 0; i < countWallsNeighbours.size(); i++) {
           if (countWallsNeighbours.get(i)>max){
               max  = countWallsNeighbours.get(i) ;
               getindexMax = i ;
           }
        }

        if(getindexMax==0) return nn ;
        if (getindexMax==1) return ns ;
        if (getindexMax==2) return ne ;
        if (getindexMax==3) return no ;

        return null ;

    }

    public boolean checkUnexploredNeighbour(){
        if (nn.isExploredBM() ||
                ns.isExploredBM() ||
                ne.isExploredBM() ||
                no.isExploredBM())
            return true ;
        return false ;
    }

    public boolean pathExisting() {
        int x = agent.getLocation().getX();
        int y = agent.getLocation().getY();
        int scale = agent.getSize();

        Point p0 = mapAlgo.getPoint(x - scale, y - scale);
        Point p1 = mapAlgo.getPoint(x, y - scale);
        Point p2 = mapAlgo.getPoint(x + scale, y - scale);

        Point p3 = mapAlgo.getPoint(x - scale, y);
        //Point p4 = mapAlgo.getPoint(x, y);
        Point p5 = mapAlgo.getPoint(x + scale, y);

        Point p6 = mapAlgo.getPoint(x - scale, y + scale);
        Point p7 = mapAlgo.getPoint(x, y + scale);
        Point p8 = mapAlgo.getPoint(x + scale, y + scale);

        List<List<Integer> > g = new ArrayList<>();
        int v = 9;
        for(int i = 0; i < v; i++)
        {
            g.add(new ArrayList<>());
        }

        if(!p0.isVisited()){
            if(!p1.isVisited())
                g.get(0).add(1) ;
            if(!p2.isVisited())
                g.get(0).add(3) ;
        }
        if(!p1.isVisited()){
            if(!p0.isVisited())
                g.get(1).add(0) ;
            //if(!p4.isVisited())
               // g.get(1).add(4) ;
            if(!p2.isVisited())
                g.get(1).add(2) ;
        }
        if(!p2.isVisited()){
            if(!p1.isVisited())
                g.get(2).add(1) ;
            if(!p5.isVisited())
                g.get(2).add(5) ;
        }
        if(!p3.isVisited()){
            if(!p0.isVisited())
                g.get(3).add(0) ;
            //if(!p4.isVisited())
              //  g.get(3).add(4) ;
            if(!p6.isVisited())
                g.get(3).add(6) ;
        }
        /*if(!p4.isVisited()){
            if(!p3.isVisited())
                g.get(4).add(3) ;
            if(!p1.isVisited())
                g.get(4).add(1) ;
            if(!p5.isVisited())
                g.get(4).add(5) ;
            if(!p7.isVisited())
                g.get(4).add(7) ;
        }*/
        if(!p5.isVisited()){
            if(!p2.isVisited())
                g.get(5).add(2) ;
            //if(!p4.isVisited())
              //  g.get(5).add(4) ;
            if(!p8.isVisited())
                g.get(5).add(8) ;
        }
        if(!p6.isVisited()){
            if(!p3.isVisited())
                g.get(6).add(3) ;
            if(!p7.isVisited())
                g.get(6).add(7) ;
        }
        if(!p7.isVisited()){
            if(!p6.isVisited())
                g.get(7).add(6) ;
            //if(!p4.isVisited())
               // g.get(7).add(4) ;
            if(!p8.isVisited())
                g.get(7).add(8) ;
        }
        if(!p8.isVisited()){
            if(!p7.isVisited())
                g.get(8).add(7) ;
            if(!p5.isVisited())
                g.get(8).add(5) ;
        }

       // if(graph.findpaths()

        for (int i = 0; i < g.size(); i++) {
            for (int j = 0; j < g.get(i).size(); j++) {
                if (!graph.findpaths(g,i,j))
                    return false ;
            }
        }
        return true ;
    }

    class Graph {

        // utility function for printing
// the found path in graph
        private void printPath(List<Integer> path) {
            int size = path.size();
            for (Integer v : path) {
                System.out.print(v + " ");
            }
            System.out.println();
        }

        // Utility function to check if current
// vertex is already present in path
        private boolean isNotVisited(int x, List<Integer> path) {
            int size = path.size();
            for (int i = 0; i < size; i++)
                if (path.get(i) == x)
                    return false;

            return true;
        }

        // Utility function for finding paths in graph
// from source to destination
        public boolean findpaths(List<List<Integer>> g, int src, int dst) {

            // Create a queue which stores
            // the paths
            Queue<List<Integer>> queue = new LinkedList<>();

            // Path vector to store the current path
            List<Integer> path = new ArrayList<>();
            path.add(src);
            queue.offer(path);

            while (!queue.isEmpty()) {
                path = queue.poll();
                int last = path.get(path.size() - 1);

                // If last vertex is the desired destination
                // then print the path
                if (last == dst) {
                    //printPath(path);
                    return true;
                }

                // Traverse to all the nodes connected to
                // current vertex and push new path to queue
                List<Integer> lastNode = g.get(last);
                for (int i = 0; i < lastNode.size(); i++) {
                    if (isNotVisited(lastNode.get(i), path)) {
                        List<Integer> newpath = new ArrayList<>(path);
                        newpath.add(lastNode.get(i));
                        queue.offer(newpath);
                    }
                }
            }
            return false;
        }
    }

}



