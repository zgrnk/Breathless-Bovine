
public class Switch extends TrackElement {
    public static final boolean STATE_STRAIGHT = false;
    public static final boolean STATE_DIVERGENT = true;
    public Block blkMain;
    public Block blkStraight;
    public Block blkDiverg;
    public int mainId;
    public int straightId;
    public int divergId;
    public boolean state;

    public Switch() {
        blkMain = null;
        blkStraight = null;
        blkDiverg = null;
        state = STATE_STRAIGHT;
        id = -1;
    }

    public 

    public void connect(Block main, Block straight, Block diverg) {
        this.blkMain = main;
        this.blkStraight = straight;
        this.blkDiverg = diverg;
    }

    public String toString()
    {
        return ("Switch " + Integer.toString(id));
    }
}
