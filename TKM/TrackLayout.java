/* TKM - Stephen Albert */
/* TrackLayout.java */

package TKM;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;
import java.io.Console;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

    
public class TrackLayout {
	
    TrainLocation tloc;


    boolean dir = Block.DIRECTION_FWD;

    private ArrayList<TrackElement> elements;
    private ArrayList<Block> blocks;
    private ArrayList<Switch> switches;
    private Block yard;
    private TrackElement selected;

    public void setSelectedElement(TrackElement t) {
        selected = t;
    }

    public TrackElement getSelectedElement() {
        return selected;
    }

    public TrackElement getElementById(int id) {
        ListIterator<TrackElement> iter = elements.listIterator();

        while (iter.hasNext()) {
            TrackElement elem = iter.next();
            //System.out.printf("%d,",elem.id);
            if (elem.id == id) {
                //System.out.printf("Found element %d\n", id);
                return elem;
            }
        }


        System.out.printf("Could not find element %d\n", id);
        return null;
    }

    public AbstractList<Block> getBlocks()
    {
        return blocks;
    }
    
    public AbstractList<Switch> getSwitches()
    {
        return switches;
    }

    public String[] getIdArray()
    {
        /* Turn element list into strings */
        String[] rets = new String[elements.size()];

        ListIterator<TrackElement> iter = elements.listIterator();

        int i = 0;
        
        while (iter.hasNext()) {
            TrackElement elem = iter.next();
            rets[i] = Integer.toString(elem.id);
            i++;
        }

        return rets;
    }

    public void chooChoo(TrackMapPanel pMap) {
        /* Simulate train traversing track */

        //Switch sw = (Switch) getElementById(1002);
        //sw.state = Switch.STATE_DIVERGENT;
        
        /* run this once */
        if (tloc == null) tloc = new TrainLocation(yard, Block.DIRECTION_FWD, 0.0);

        System.out.printf("(%s) Block %d, dir:%s, dist:%f", tloc.block.sectionId, tloc.block.id, Boolean.toString(tloc.direction), tloc.distance);

        if (tloc.block.isStation) {
            System.out.printf(": Arriving at %s", tloc.block.stationName);
        }
        System.out.printf(" +%f", tloc.block.speedLimit/18.241);
        System.out.printf("\n");
        //old = cur;
        
        
        
        Block.advanceTrain(tloc, tloc.block.speedLimit/20);
        
        pMap.setMarker(tloc.getMapX(), tloc.getMapY());
        
        if (tloc.block == yard) {
            System.out.printf("Returned to yard. Done.\n");
            return;
        }
        if (tloc.failed) {
            System.out.printf("Movement failed\n");
            return;
        }
            //if (old == cur) {
            //    System.out.printf("Arrived at same block!\n");
            //    return;
            //}

    }

    public void constructTrack() {
        /* Find the yard and make it the root */
        yard = (Block) getElementById(0);
        ListIterator<TrackElement> iter = elements.listIterator();

        int numFaults = 0;

        while (iter.hasNext()) {
            TrackElement cur = iter.next();
            //System.out.printf("Processing element %d\n", cur.id);
            if (cur instanceof Block) {
                Block curBlk = (Block) cur;
                if (curBlk.next == null) {
                    curBlk.next = getElementById(curBlk.fwdId);
                    if (curBlk.next == null) numFaults++;
                }
                if (curBlk.prev == null) {
                    curBlk.prev = getElementById(curBlk.revId);
                    if (curBlk.prev == null) numFaults++;
                }
            } else {
                Switch curSw = (Switch) cur;
                if (curSw.blkMain == null) {
                    curSw.blkMain = (Block) getElementById(curSw.mainId);
                    if (curSw.blkMain == null) numFaults++;
                }
                if (curSw.blkStraight == null) {
                    curSw.blkStraight = (Block) getElementById(curSw.straightId);
                    if (curSw.blkStraight == null) numFaults++;
                }
                if (curSw.blkDiverg == null) {
                    curSw.blkDiverg = (Block) getElementById(curSw.divergId);
                    if (curSw.blkDiverg == null) numFaults++;
                }
            }
        }

        System.out.printf("Track model has %d elements\n", elements.size());
        System.out.printf("Built track model with %d faults\n", numFaults);
    }

    public void parseTrackDB (String pathname) {
        elements = new ArrayList<TrackElement>();
        blocks = new ArrayList<Block>();
        switches = new ArrayList<Switch>();
        
        /* open csv */
        Path path = Paths.get(pathname);

        Scanner scanner;
        try {

            scanner = new Scanner(path, StandardCharsets.UTF_8.name());
            
            
            /* parse csv */
            int mode = -1;
            boolean skip = false;

            System.out.println("Begin parsing");
            while (scanner.hasNextLine()){

                
                String line = scanner.nextLine();
                //System.out.println(line);

                if (skip) {
                    skip = false;
                    continue;
                }

                String[] tokens = line.split(",",20);

                if (tokens.length != 17) {
                    System.out.printf("Incorrect number of tokens: %d != 17", tokens.length);
                    System.exit(1);
                }

                //System.out.println(tokens[0]);

                if (tokens[0].equals("BLOCKS")) {
                    mode = 0;
                    skip = true;
                }
                else if (tokens[0].equals("SWITCHES")) {
                    mode = 1;
                    skip = true;
                } else {
                    if (mode == 0) {
                        /* read a block line*/
                        /* 0    1    2     3      4     5        6   7  8   9   10      11   12
                        /* Line,Sect,BlkID,Length,Grade,SpeedLim,Ugr,Sw,Rev,Fwd,TravDir,Xing,Station */

                        Block blk = new Block();

                        blk.lineId = tokens[0];
                        blk.sectionId = tokens[1];
                        blk.id = Integer.parseInt(tokens[2]);
                        blk.length = Double.parseDouble(tokens[3]);
                        blk.grade = Double.parseDouble(tokens[4]);
                        blk.speedLimit = Integer.parseInt(tokens[5]);
                        blk.isUground = tokens[6].equals("y");
                        //blk.hasSwitch [7]
                        blk.revId = Integer.parseInt(tokens[8]);
                        blk.fwdId = Integer.parseInt(tokens[9]);

                        blk.isBidir = false;
                        
                        if (tokens[10].equals("both")) {
                            //blk.allowFwd = true;
                            //blk.allowRev = true;
                            blk.isBidir = true;
                        }

                        blk.isCrossing = tokens[11].equals("y");
                        blk.isStation = !tokens[12].equals("");
                        blk.stationName = tokens[12];
                        blk.mapX1 = Integer.parseInt(tokens[13]);
                        blk.mapY1 = Integer.parseInt(tokens[14]);
                        blk.mapX2 = Integer.parseInt(tokens[15]);
                        blk.mapY2 = Integer.parseInt(tokens[16]);

                        //System.out.printf("Block:  id=%d, length=%f station=%s\n",
                        //    blk.id, blk.length, blk.stationName);


                        elements.add(blk);
                        blocks.add(blk);
                            
                    } else if (mode == 1) {
                        /* read a switch line */
                        /* 0    1    2  34567    8        9
                        /* Line,Sect,ID,,,,,Main,Straight,Div,,, */
                        Switch sw = new Switch();
                        sw.lineId = tokens[0];
                        sw.sectionId = tokens[1];
                        sw.id = Integer.parseInt(tokens[2]);
                        sw.mainId = Integer.parseInt(tokens[7]);
                        sw.straightId = Integer.parseInt(tokens[8]);
                        sw.divergId = Integer.parseInt(tokens[9]);
                        sw.mapX1 = Integer.parseInt(tokens[13]);
                        sw.mapY1 = Integer.parseInt(tokens[14]);
                        
                        //System.out.printf("Switch: id=%d\n", sw.id);

                        elements.add(sw);
                        switches.add(sw);
                    } else {
                        System.out.println("Error: stray line");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }
}
