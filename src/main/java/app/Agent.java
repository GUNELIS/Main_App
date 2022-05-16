package app;

public interface Agent {
    public Point getLocation();
    public void setLocation(Point l);
    public String getFacing();
    public void setFacing(String f);
    public GameFrame getGameFrame();
    public MapArray getSharedArray();
    public MapArray getMapArray();
    public int getSize();
    public String getmyID();
    public View getViewObject();
    public Scenario getScenerio();
    public Actions getAction();
    public void teleports();
    public boolean isGuard();
    public boolean heared_noise();
    public void set_heared_noise(boolean b);

}
