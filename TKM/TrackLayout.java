/* TKM - Stephen Albert */
/* TrackLayout.java */

package TKM;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;
import java.io.Console;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import TNM.Train;

    
public class TrackLayout {

    public static final double TRANSPONDER_MIN_DIST =  200; //m

    public class TrackLine {
        //private ArrayList<TrackElement> elements;
        private ArrayList<Block> blocks;
        private ArrayList<Switch> switches;
        public Block yard;
        public String id;

        public TrackLine(String lineId)
        {
            //elements = new ArrayList<TrackElement>();
            blocks = new ArrayList<Block>();
            switches = new ArrayList<Switch>();
            id = lineId;
        }

        public void removeBlock(Block blk) {
            blk.prev.disconnect(blk);
            blk.next.disconnect(blk);
            blocks.remove(blk);
        }
        
        public AbstractCollection<Block> getBlocks()
        {
            //ArrayList
            return blocks;
        }
        
        public AbstractCollection<Switch> getSwitches()
        {
            return switches;
        }

        public Block getBlockById(int id) {
            for (Block b : blocks) {
                if (b.id == id) {
                    return b;
                }
            }
            return null;
        }
        
        public Switch getSwitchById(int id) {
            for (Switch s : switches) {
                if (s.id == id) {
                    return s;
                }
            }
            return null;
        }

        public TrackElement getElementById(int id) {
            for (Block b : blocks) {
                if (b.id == id) {
                    return b;
                }
            }
            for (Switch s : switches) {
                if (s.id == id) {
                    return s;
                }
            }
            System.out.printf("Could not find element %d\n", id);
            return null;
        }

        private void constructTrack() {
            /* Find the yard and make it the root */
            yard = (Block) getBlockById(0);

            int numFaults = 0;

            for (Block curBlk : blocks) {
                if (curBlk.next == null) {
                    curBlk.next = getElementById(curBlk.fwdId);
                    if (curBlk.next == null) numFaults++;
                }
                if (curBlk.prev == null) {
                    curBlk.prev = getElementById(curBlk.revId);
                    if (curBlk.prev == null) numFaults++;
                }
            }

            for (Switch curSw : switches) {
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
            
            /* Add transponders to blocks adjacent to stations */
            /* If the station is two blocks ahead, add it */
            //~ for (Block curBlk : blocks) {
                //~ if (curBlk.next instanceof Block && ((Block)curBlk.next).isStation) {
                    //~ curBlk.transponderMessageFwd = ((Block)curBlk.next).stationName;
                //~ } else if (curBlk.next instanceof Switch) {
                    //~ Switch nextSw = (Switch) curBlk.next;
                    //~ if (
                            //~ nextSw.blkMain.isStation
                            //~ && (
                                //~ nextSw.blkStraight == curBlk
                                //~ || nextSw.blkDiverg == curBlk
                            //~ )
                       //~ ) {
                        //~ curBlk.transponderMessageFwd = nextSw.blkMain.stationName;
                    //~ } else if ((nextSw.blkStraight.isStation && nextSw.blkMain == curBlk)
                       //~ || (nextSw.blkDiverg.isStation && nextSw.blkMain == curBlk)) {
                           //~ System.out.println("Bad transponder location");
                    //~ }
                //~ }
//~ 
                //~ if (curBlk.isBidir && curBlk.prev instanceof Block && ((Block)curBlk.prev).isStation) {
                    //~ curBlk.transponderMessageRev = ((Block)curBlk.prev).stationName;
                //~ } else if (curBlk.isBidir && curBlk.prev instanceof Switch) {
                    //~ Switch nextSw = (Switch) curBlk.prev;
                //~ }
            //~ }

            for (Block curBlk : blocks) {
                /* Check if this block is en route to a station */
                
                ArrayList<Block> fwdList = curBlk.getBlocksOnPath(0., Block.DIRECTION_FWD, TRANSPONDER_MIN_DIST);
                for (Block b : fwdList) {
                    if (b.isStation) {
                        curBlk.transponderMessageFwd = b.stationName;
                        System.out.printf("Block %d has fwd xponder to %s\n", curBlk.id, b.stationName);
                        break;
                    }
                }

                if (curBlk.isBidir) {
                    ArrayList<Block> revList = curBlk.getBlocksOnPath(0., Block.DIRECTION_REV, TRANSPONDER_MIN_DIST);
                    for (Block b : revList) {
                        if (b.isStation) {
                            curBlk.transponderMessageRev = b.stationName;
                            System.out.printf("Block %d has rev xponder to %s\n", curBlk.id, b.stationName);
                            break;
                        }
                    }
                }
            }
            
            System.out.printf("Track model has %d elements\n", blocks.size() + switches.size());
            System.out.printf("Built %s Line with %d faults\n", blocks.get(0).lineId, numFaults);
        }

        public String toString()
        {
            return id;
        }
    }
	

    //private ArrayList<TrackElement> elements;
    //private ArrayList<Block> blocks;
    //private ArrayList<Switch> switches;
    
    //public Block yard;
    public TrackLine redLine;
    public TrackLine greenLine;
    private ArrayList lines;
    private TrackElement selected;

    public AbstractCollection<Train> trains;

    public TrackLayout() {
        lines = new ArrayList<TrackLine>(2);

    }
        
    public AbstractCollection<TrackLine> getLines() {
        return lines;
    }

    public void setTrainList(AbstractCollection<Train> tl) {
        trains = tl;
    }

    public void setSelectedElement(TrackElement t) {
        selected = t;
    }

    public TrackElement getSelectedElement() {
        return selected;
    }

    public TrackElement getElementById(int id) {
        /* FIXME only returns red line */
        /* Deprecated */
        return getElementById(redLine, id);
    }

    public TrackElement getElementById(TrackLine line, int id) {
        return line.getElementById(id);
    }

    public AbstractCollection<Block> getBlocks()
    {
        ArrayList<Block> allBlocks = new ArrayList();
        allBlocks.addAll(redLine.getBlocks());
        allBlocks.addAll(greenLine.getBlocks());
        
        return allBlocks;
    }
    
    public AbstractCollection<Switch> getSwitches()
    {
        ArrayList<Switch> allSwitches = new ArrayList();
        allSwitches.addAll(redLine.getSwitches());
        allSwitches.addAll(greenLine.getSwitches());
        
        return allSwitches;
    }

 

    public void parseTrackDB (String pathname) {
        redLine = new TrackLine("Red Line");
        greenLine = new TrackLine("Green Line");

        TrackLine tLine = redLine;
        
        /* open csv */
        Path path = Paths.get(pathname);

        Scanner scanner;
        try {

            scanner = new Scanner(path); //StandardCharsets.UTF_8.name());

            /* parse csv */
            int mode = -1;
            boolean skip = false;

            System.out.println("Parsing track database");
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

                        if (blk.lineId.equalsIgnoreCase("green")) {
                            tLine = greenLine;
                        } else {
                            tLine = redLine;
                        }
                            
                        blk.sectionId = tokens[1];
                        blk.id = Integer.parseInt(tokens[2]);
                        blk.length = Double.parseDouble(tokens[3]);
                        blk.grade = Double.parseDouble(tokens[4]);
                        /* Speed limit is given in km/h but we need m/s */
                        blk.speedLimit = Double.parseDouble(tokens[5]) * 0.27777;
                        blk.isUground = tokens[6].equals("y");
                        //blk.hasSwitch [7]
                        blk.revId = Integer.parseInt(tokens[8]);
                        blk.fwdId = Integer.parseInt(tokens[9]);

                        blk.isBidir = false;
                        
                        if (tokens[10].equals("both")) {
                            blk.isBidir = true;
                        }

                        blk.isCrossing = tokens[11].equals("y");
                        blk.isStation = !tokens[12].equals("");
                        blk.stationName = tokens[12];
                        blk.mapX1 = Integer.parseInt(tokens[13]);
                        blk.mapY1 = Integer.parseInt(tokens[14]);
                        blk.mapX2 = Integer.parseInt(tokens[15]);
                        blk.mapY2 = Integer.parseInt(tokens[16]);

                        blk.isYard = (blk.id == 0);

                        //System.out.printf("Block:  id=%d, length=%f station=%s\n",
                        //    blk.id, blk.length, blk.stationName);

                        blk.line = tLine;
                        tLine.blocks.add(blk);
                            
                    } else if (mode == 1) {
                        /* read a switch line */
                        /* 0    1    2  34567    8        9
                        /* Line,Sect,ID,,,,,Main,Straight,Div,,, */
                        Switch sw = new Switch();
                        sw.lineId = tokens[0];

                        if (sw.lineId.equalsIgnoreCase("green")) {
                            tLine = greenLine;
                        } else {
                            tLine = redLine;
                        }
                        
                        sw.sectionId = tokens[1];
                        sw.id = Integer.parseInt(tokens[2]);
                        sw.mainId = Integer.parseInt(tokens[7]);
                        sw.straightId = Integer.parseInt(tokens[8]);
                        sw.divergId = Integer.parseInt(tokens[9]);
                        sw.mapX1 = Integer.parseInt(tokens[13]);
                        sw.mapY1 = Integer.parseInt(tokens[14]);
                        
                        //System.out.printf("Switch: id=%d\n", sw.id);

                        sw.line = tLine;
                        tLine.switches.add(sw);
                    } else {
                        System.out.println("Error: stray line");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }

        redLine.constructTrack();
        greenLine.constructTrack();
        lines.add(redLine);
        lines.add(greenLine);
    }
}
