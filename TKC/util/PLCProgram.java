package TKC.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import TKM.Block;
import TKM.Switch;


/**
 * 
 * @author Dominic Visco
 *
 */
public class PLCProgram {

	public PLCProgram() {

	}

	public SafetyInfo runPLC(Hashtable<Integer, Block> blkTable, ArrayList<Integer> endBlks, 
			LinkedList<Block> activeBlks, LinkedList<TrainWrapper> trainList, Switch cSwitch) {

		boolean swS = Switch.STATE_STRAIGHT;
		Component litS = new Component(0,0);
		boolean isSafe = true;

		TrainWrapper onMain = trainOnBlock(cSwitch.blkMain, trainList);
		TrainWrapper onStraight = trainOnBlock(cSwitch.blkStraight, trainList);
		TrainWrapper onDivergent = trainOnBlock(cSwitch.blkDiverg, trainList);

		if (onMain != null) {
			if (onMain.getFutureBlock() == null) {
				SafetyInfo newInfo = new SafetyInfo(swS, litS, false);
				return newInfo;
			} else {
				if (onMain.getFutureBlock().id == cSwitch.blkStraight.id) {
					swS = Switch.STATE_STRAIGHT;
				} else if (onMain.getFutureBlock().id == cSwitch.blkDiverg.id) {
					swS = Switch.STATE_DIVERGENT;
				}
			}
		} else if (onStraight != null) {
			if (onStraight.getFutureBlock() == null) {
				SafetyInfo newInfo = new SafetyInfo(swS, litS, false);
				return newInfo;
			} else {
				if (onStraight.getFutureBlock().id == cSwitch.blkMain.id) {
					swS = Switch.STATE_STRAIGHT;
				}
			}
		} else if (onDivergent != null) {
			if (onDivergent.getFutureBlock() == null) {
				SafetyInfo newInfo = new SafetyInfo(swS, litS, false);
				return newInfo;
			} else {
				if (onDivergent.getFutureBlock().id == cSwitch.blkMain.id) {
					swS = Switch.STATE_DIVERGENT;
				}
			}
		}

		for (TrainWrapper fromTrain : trainList) {
			for (TrainWrapper toTrain : trainList) {
				if (trainsHeadedTogether(blkTable, endBlks, fromTrain, toTrain))
					isSafe = false;
			}
		}

		SafetyInfo newInfo = new SafetyInfo(swS, litS, isSafe);

		return newInfo;

	}

	/**
	 * helper function for determining if the block contains an actual train
	 * @param blkId
	 * @param trainList
	 * @return
	 */
	private TrainWrapper trainOnBlock(Block blkId, LinkedList<TrainWrapper> trainList) {

		TrainWrapper temp = null;

		for (TrainWrapper sTrain : trainList) {
			if (sTrain.getBlockLocation().id == blkId.id) {
				temp = sTrain;
			}
		}

		return temp;

	}

	/**
	 * determines if trains are headed together which would case an unsafe state and require immediate shutdown
	 * @param blkTable
	 * @param endBlks
	 * @param fromTrain
	 * @param toTrain
	 * @return
	 */
	private boolean trainsHeadedTogether(Hashtable<Integer, Block> blkTable, ArrayList<Integer> endBlks, 
			TrainWrapper fromTrain, TrainWrapper toTrain) {

		boolean fromTowardsTo = false;
		boolean toTowardsFrom = false;

		if (fromTrain.train.id == toTrain.train.id) {
			return false;
		}

		//put in check to see if any train is leaving zone on an endblk
		//if so then trains can't be headed together

		if (!isInZone(blkTable, fromTrain.getFutureBlock())) {
			return false;
		} else if (!isInZone(blkTable, toTrain.getFutureBlock())) {
			return false;
		}

		int index = fromTrain.train.routeIndex;
		boolean flag = true;

		while (flag) {
			try {
				if (fromTrain.train.route.get(index + 1).id == toTrain.getBlockLocation().id) {
					fromTowardsTo = true;
					flag = false;
				}else if (TrainWrapper.isEndBlk(endBlks, fromTrain.train.route.get(index + 1).id)) {
					flag = false;
				}else {
					index++;
				}
			}catch (IndexOutOfBoundsException e) {
				flag = false;
			}
		}

		index = toTrain.train.routeIndex;
		flag = true;

		while (flag) {
			try {
				if (toTrain.train.route.get(index + 1).id == fromTrain.getBlockLocation().id) {
					toTowardsFrom = true;
					flag = false;
				}else if (TrainWrapper.isEndBlk(endBlks, toTrain.train.route.get(index + 1).id)) {
					flag = false;
				}else {
					index++;
				}
			}catch (IndexOutOfBoundsException e) {
				flag = false;
			}
		}


		if (fromTowardsTo && toTowardsFrom) {
			return true;
		} else {
			return false;
		}		
	}

	/**
	 * checks if block is in wayside's zone
	 * @param blk
	 * @return
	 */
	private boolean isInZone(Hashtable<Integer, Block> blkTable, Block blk) {

		Iterator<Block> itr = blkTable.values().iterator();
		Block tempBlk;

		if (blk == null)
			return false;

		while(itr.hasNext())
		{
			tempBlk = itr.next();
			if (tempBlk.id == blk.id) {
				return true;
			}
		}
		return false;
	}
}
