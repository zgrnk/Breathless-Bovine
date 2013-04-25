package TKM;

public abstract class TrackElement {
    public int id;
    public int mapX1;
    public int mapY1;
    public String sectionId;
    public String lineId;
    public TrackLayout.TrackLine line;
    abstract public void disconnect(TrackElement te);
    abstract public String toString();
}
