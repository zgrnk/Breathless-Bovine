

/*
public class Block{
	
	public String id;
	public Block next;
	public Block prev;
	public Train trainOnBlock;
	public double length = 500;
	public double speedLimit = 50;
		
	
	public Block(String id){
		this.id = id;
	}
}
*/


public class Block extends TrackElement
{
    public static final boolean DIRECTION_FWD = false;
    public static final boolean DIRECTION_REV = true; 

    /* For sane double comparisons */
    public static final double SMALL_DOUBLE = 0.000001;

    /* TODO: clean this up */

    /* Static info */
    public double length;
    public double grade;
    public double speedLimit;
    public boolean isBidir;
    public boolean isUground;
    public boolean isYard;
    public boolean isStation;
    public boolean isCrossing;
    public String stationName;
    public TrackElement prev;
    public TrackElement next;

    /* Dynamic info*/
    public boolean occupied;
    public boolean brokenRailFailure;
    public boolean trackCircuitFailure;
    public boolean powerFailure;
    
    
    //ADDED BY MARIO
    public Train trainOnBlock;
    //ADDED BY MARIO

    //public double mbSpeed;
    //public double mbAuthority;
    public double fbSpeed;
    public double fbAuthority;

    /* etc */
    public int fwdId;
    public int revId;
    public int mapX2;
    public int mapY2;

    
	public Block(int id){
		this.id = id;
	}

    public Block() {
        prev = null;
        next = null;
        id = -1;
    }

    public Block(int id, String lineId, String sectionId, double length, double grade,
                    double speedLimit, boolean isBidir, boolean isUground, boolean isYard,
                    boolean isStation, boolean isCrossing, String stationName,
                    boolean brokenRailFailure, boolean trackCircuitFailure, boolean powerFailure) {
        this.id = id;
        this.lineId = lineId;   
        this.sectionId = sectionId;
        this.length = length;
        this.grade = grade;
        this.speedLimit = speedLimit;
        this.isBidir = isBidir;
        this.isUground = isUground;
        this.isYard = isYard;
        this.isStation = isStation;
        this.isCrossing = isCrossing;
        this.stationName = stationName;
        this.brokenRailFailure = brokenRailFailure;
        this.trackCircuitFailure = trackCircuitFailure;
        this.powerFailure = powerFailure;
    }


    public void connect(TrackElement prev, TrackElement next)
    {
        this.prev = prev;
        this.next = next;
    }
                        

    public boolean isOccupied() {
        /* TODO: Correctly evaluate failure mode */
        return occupied;
    }


    private Block getSwitchDest(Switch sw, boolean dryRun) {
        if (!dryRun) System.out.printf("Switch %d from block %d\n", sw.id, this.id);
        /* NYI: Derail if switch is not set properly */
        
        if (this == sw.blkMain) {
            /* Go in the direction according to the switch state */
            if (sw.state == Switch.STATE_STRAIGHT)
                return sw.blkStraight;
            else if (sw.state == Switch.STATE_DIVERGENT)
                return sw.blkDiverg;
        } else if (this == sw.blkStraight) {
            if (!dryRun && sw.state != Switch.STATE_STRAIGHT) {
                System.out.printf("Warning: switch %d auto-flipped to STRAIGHT\n", sw.id);
                sw.state = Switch.STATE_STRAIGHT;
            }
            return sw.blkMain;
        } else if (this == sw.blkDiverg) {
            if (!dryRun && sw.state != Switch.STATE_DIVERGENT) {
                System.out.printf("Warning: switch %d auto-flipped to DIVERGENT\n", sw.id);
                sw.state = Switch.STATE_DIVERGENT;
            }
            return sw.blkMain;
        }
        
        System.out.print("Switching error\n");
        return null;
    }

    public Block getNext(boolean direction) {
        return getNext(direction, true);
    }

    public Block getNext(boolean direction, boolean dryRun) {

        Block dest = null;

        if (direction == DIRECTION_FWD) { /* direction is toward next */
            if (next != null && next instanceof Switch) {
                dest =  getSwitchDest((Switch) next, dryRun);
            } else {
                dest = (Block) next;
            }
        } else { /* direction is toward prev */
            if (prev != null && prev instanceof Switch) {
                dest = getSwitchDest((Switch) prev, dryRun);
            } else {
                /* prev is a block */
                dest = (Block) prev;
            }
        }

        return dest;
    }

    public String toString()
    {
        return ("Block " + Integer.toString(id));
    }
}
