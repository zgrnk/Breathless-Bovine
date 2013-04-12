package nsecs.TKC.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import nsecs.NonTKC.util.Block;
import nsecs.NonTKC.util.TrainWrapper;

/**
 * 
 * @author Dominic Visco
 *
 */
public interface PLCProgram {

	abstract SafetyInfo runPLC(Hashtable<Integer, Block> blkTable, ArrayList<Integer> endBlks, LinkedList<Block> activeBlks, LinkedList<TrainWrapper> trainList);
}
