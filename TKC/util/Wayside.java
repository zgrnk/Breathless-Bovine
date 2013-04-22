package TKC.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import CTCOffice.CTCOffice;
import TKM.Block;
import TKM.Switch;
import TNM.Train;


/**
 * 
 * @author Dominic Visco
 *
 */
public class Wayside {

	public PLCProgram loadedPLC;
	public Hashtable<Integer, Block> blockTable;
	private ArrayList<Integer> endBlocks;
	private	LinkedList<Block> activeBlocks;
	public LinkedList<TrainWrapper> trainList;
	
	private CTCOffice mainCTCOffice;
	private SafetyInfo currStateInfo;
	public Switch	centralSwitch;
	
	
	/**
	 * Wayside Constructor
	 * @param table
	 * @param mainCTCOffice
	 * @param currInfo
	 */
	public Wayside(Hashtable<Integer, Block> table, ArrayList<Integer> endBlks, CTCOffice mainCTCOffice, SafetyInfo currInfo, Switch cSwitch) {
		this.blockTable = table;
		this.endBlocks = endBlks;
		this.mainCTCOffice = mainCTCOffice;
		this.currStateInfo = currInfo;
		this.centralSwitch = cSwitch;
		
		trainList = new LinkedList<TrainWrapper>();
		this.loadedPLC = new PLCProgram();
	}
	
	/*public void setPLC(PLCProgram plc) {
		this.loadedPLC = plc;
	}*/
	
	/**
	 * Updates active block list with all currently occupied blocks in wayside's zone.
	 * Accomplishes the task by retrieving all values in the block table and iterating
	 * through the list to check each block's current occupied status.
	 */
	private void updateActiveBlocks() {
		Iterator<Block> itr = blockTable.values().iterator();
		Block temp;
		
		activeBlocks = new LinkedList<Block>();
		while (itr.hasNext())
		{
			temp = itr.next();
			if(temp.occupied) {
				activeBlocks.add(temp);
				
				//DEBUG
				//System.out.println(temp.id);
			}
		}
	}
	
	/**
	 * Sets the current limits to the specified block
	 * @param blockID
	 * @param current
	 */
	private void setLimits(Integer blockID, Limits current) {
		Block temp = blockTable.get(blockID);
		temp.fbAuthority = current.getAuthority();
		temp.fbSpeed = current.getSpeedLimit();
	}
	
	/**
	 * Updates the wayside's train list
	 */
	private void updateTrainList() {
		
		/*If list empty than search if any end blocks currently are active */
		if (trainList.isEmpty()) {
			for (Block blk : activeBlocks) {
				if (TrainWrapper.isEndBlk(endBlocks, blk.id)) {
					Train temp = mainCTCOffice.getTrainsInBlock(blk.id).get(0);
							trainList.add(new TrainWrapper(temp, blk));
				}
			}
		}else {
			//updates positions of currently tracked trains
			LinkedList<TrainWrapper> removals = new LinkedList<TrainWrapper>();
			
			for (TrainWrapper sTrain : trainList) {
				sTrain.updateLocation();
				if (!blockIsInZone(sTrain.getBlockLocation()))
					removals.add(sTrain);
			}
			
			for (TrainWrapper rTrain : removals) {
				trainList.remove(rTrain);
			}
			
			//adds new trains found on endblocks that weren't in the train list
			for (Integer blkId : endBlocks) {
				if (isActive(blkId)) {
					boolean flag = false;
					for (TrainWrapper sTrain : trainList) {
						if (sTrain.getBlockLocation().id == blkId) {
							flag = true;
							break;
						}
					}
					if (!flag) {
						for (Train tR : mainCTCOffice.getTrainsInBlock(blkId)) {
							if (!inTrainList(tR))
								trainList.add(new TrainWrapper(tR, blockTable.get(blkId)));
						}
					}
				}
			}
		}
	}

	/**
	 * checks if specified train is in the current trainlist
	 * @param tr
	 * @return
	 */
	public boolean inTrainList(Train tr) {
		for (TrainWrapper sTrain : trainList) {
			if (sTrain.train.id == tr.id)
				return true;
		}
		return false;
	}
	
	/**
	 * checks if block id is an active block
	 * @param id
	 * @return
	 */
	private boolean isActive(int id) {
		for (Block blk : activeBlocks) {
			if (blk.id == id) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * checks if block is in wayside's zone
	 * @param blk
	 * @return
	 */
	private boolean blockIsInZone(Block blk) {
		
		Iterator<Block> itr = blockTable.values().iterator();
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
	
	/**
	 * checks for any broken track circuits by comparing active blocks to verified trains' locations.
	 */
	private void checkForBrokenCircuits() {
		for (Block blk : activeBlocks) {
			boolean flag = false;
			for (TrainWrapper sTrain : trainList) {
				if (blk.id == sTrain.train.positionBlock.id) {
					flag = true;
					break;
				}
			}
			//block doesn't have a recorded train and therefore contains a malfunction circuit
			if (!flag) {
				blk.brokenRailFailure = true;
			}
		}
	}
	
	
	/**
	 * Activates the wayside for its next cycle
	 */
	public void nextTick() {
		updateActiveBlocks();
		updateTrainList();
		checkForBrokenCircuits();

		//Now stage is set
		
		//run PLC redundancy 
		runRedundancy();
		
		//set safetyInfo
		if (currStateInfo.safetyState) {
			
			if (currStateInfo.lightState.getState() != 0) {
				toggleComponent(currStateInfo.lightState.getBlockLocation(), 
						ComponentType.LIGHT_COMP, currStateInfo.lightState.getState());
			}
			
			toggleSwitch(currStateInfo.switchState);
			
			//calculate limits
			for (TrainWrapper sTrain : trainList)
			{
				//if train's next block is in the zone then it is receiving the train and should edit the limits
				//otherwise it shouldn't set the limits
				if (blockIsInZone(sTrain.getFutureBlock())) {
					
					Limits newLimits;
					double auth;
					double currSpeed = sTrain.train.curVelocity;
					
					double distance = Double.MAX_VALUE;
					for (TrainWrapper tTrain : trainList)
					{
						double temp = sTrain.distToTrain(endBlocks, tTrain);
						if (temp < distance)
							distance = temp;
					}
					
					//only train on the track or no tracks are ahead or something went wrong somehow
					if (distance == Double.MAX_VALUE) {
						distance = 200; //make a safety judgment of
					}
					
					double tempAuth = 0;
					boolean notGood = true;
					while (currSpeed > 0 && notGood) {
						
						tempAuth = -Math.pow(currSpeed, 2.0)/(2*-1.2);
						if (tempAuth <= distance)
						{
							auth = distance - tempAuth;
							newLimits = new Limits(currSpeed, auth);
							sTrain.setCurrLimits(newLimits);
							setLimits(sTrain.getBlockLocation().id,sTrain.getCurrLimits());
							notGood = false;
						}
						currSpeed = currSpeed - 1.0;
					}
					
					if (notGood)
					{
						newLimits = new Limits(0.0, 0.0);
						sTrain.setCurrLimits(newLimits);
						setLimits(sTrain.getBlockLocation().id,sTrain.getCurrLimits());
						
						// TO-DO must adjust suggested speed
						System.out.print("Authority Calculation may be incorrect");
						
					}
				}
				
			}
	
			
		}
		else {
			shutdown();
		}
	}
	
	/**
	 * Toggles the selected component's state in the designated block
	 * @param blkID
	 * @param state
	 */
	private void toggleComponent(Integer blkID, ComponentType type, int state) {
		switch (type) {
		case LIGHT_COMP:
			//set light state
			//blockTable.get(blkID).lightState = state;
			break;
		}
	}
	
	private void toggleSwitch(boolean currState) {
		this.centralSwitch.state = currState;
	}
	
	/**
	 * Run the triple redundancy protocol for the PLCProgram 
	 */
	public void runRedundancy() {
		
		SafetyInfo runOne = loadedPLC.runPLC(blockTable, endBlocks, activeBlocks, trainList, centralSwitch);
		SafetyInfo runTwo = loadedPLC.runPLC(blockTable, endBlocks, activeBlocks, trainList, centralSwitch);
		SafetyInfo runThree = loadedPLC.runPLC(blockTable, endBlocks, activeBlocks, trainList, centralSwitch);
		
		boolean isSafe;
		boolean swState;
		if (runOne.safetyState == runTwo.safetyState && runOne.safetyState == runThree.safetyState) {
			isSafe = runOne.safetyState;
		} else if (runOne.safetyState != runTwo.safetyState && runOne.safetyState == runThree.safetyState) {
			isSafe = runOne.safetyState;
		} else if (runOne.safetyState != runTwo.safetyState && runOne.safetyState != runThree.safetyState) {
			isSafe = runTwo.safetyState;
		} else {
			isSafe = runTwo.safetyState;
		}
		
		if (runOne.switchState == runTwo.switchState && runOne.switchState == runThree.switchState) {
			swState = runOne.switchState;
		} else if (runOne.switchState != runTwo.switchState && runOne.switchState == runThree.switchState) {
			swState = runOne.switchState;
		} else if (runOne.switchState != runTwo.switchState && runOne.switchState != runThree.switchState) {
			swState = runTwo.switchState;
		} else {
			swState = runTwo.switchState;
		}
		
		currStateInfo = new SafetyInfo(swState, runOne.lightState, isSafe);
	}
	
	/**
	 * Shuts down the NSECS application due to unsafe conditions
	 */
	private void shutdown() {
		System.out.println("Track is Unsafe and therefore must be shutdown!!!\n");
	}
	
	public enum ComponentType {
		SWITCH_COMP, LIGHT_COMP;
	}
	
	
}
