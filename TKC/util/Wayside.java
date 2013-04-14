package TKC.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import TKM.Block;


/**
 * 
 * @author Dominic Visco
 *
 */
public class Wayside {

	public PLCProgram loadedPLC;
	private Hashtable<Integer, Block> blockTable;
	private ArrayList<Integer> endBlocks;
	private	LinkedList<Block> activeBlocks;
	private LinkedList<TrainWrapper> trainList;
	
	private CTCOffice mainCTCOffice;
	private SafetyInfo currStateInfo;
	
	
	/**
	 * Wayside Constructor
	 * @param table
	 * @param mainCTCOffice
	 * @param currInfo
	 */
	public Wayside(Hashtable<Integer, Block> table, ArrayList<Integer> endBlks, CTCOffice mainCTCOffice, SafetyInfo currInfo) {
		this.blockTable = table;
		this.endBlocks = endBlks;
		this.mainCTCOffice = mainCTCOffice;
		this.currStateInfo = currInfo;
		
		trainList = new LinkedList<TrainWrapper>();
	}
	
	public void setPLC(PLCProgram plc) {
		this.loadedPLC = plc;
	}
	
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
			if(temp.isOccupied) {
				activeBlocks.add(temp);
				
				//DEBUG
				System.out.println(temp.id);
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
		temp.suggestedAuthority = current.getAuthority();
		temp.suggestedSpeed = current.getSpeedLimit();
	}
	
	/**
	 * Updates the wayside's train list
	 */
	private void updateTrainList() {
		
		/*If list empty than search if any end blocks currently */
		if (trainList.isEmpty()) {
			for (Integer blkId : endBlocks) {
				Integer found = -1;
				
				for (Block blk : activeBlocks) {
					if (blkId == blk.id) {
						found = blkId;
					}
				}
				
				if (found != -1) {
					System.out.println("FOUND\n");
					trainList.add(new TrainWrapper(mainCTCOffice.getTrainsInBlock(found).get(0), blockTable.get(blkId)));
				}
				else {
					//System.out.println("NOT FOUND\n");
				}
			}
		}
		else {
			//updates positions of currently tracked trains
			for (TrainWrapper sTrain : trainList) {
				//train has moved to another block
				if (activeBlocks.contains(sTrain.getFutureBlock())) {
					sTrain.updateLocation();

				}//Train is on an end block and therefore should be untracked
				else if (endBlocks.contains(sTrain.getBlockLocation().id)) {
					trainList.remove(sTrain);
					
				}
						
			}
			
			for (Integer blkId : endBlocks) {
				if (activeBlocks.contains(blkId)) {
					boolean flag = false;
					for (TrainWrapper sTrain : trainList) {
						if (sTrain.getBlockLocation().id == blkId) {
							flag = true;
							break;
						}
					}
					if (!flag) {
						for (Train tR : mainCTCOffice.getTrainsInBlock(blkId)) {
							if (!trainList.contains(tR))
								trainList.add(new TrainWrapper(tR, blockTable.get(blkId)));
						}
					}
				}
			}
		}
	}
	
	/**
	 * Activates the wayside for its next cycle
	 */
	public void nextTick() {
		updateActiveBlocks();
		updateTrainList();
		
		//Now stage is set
		
		//run PLC redundancy 
		currStateInfo = loadedPLC.runPLC(blockTable, endBlocks, activeBlocks, trainList);
		
		//set safetyInfo
		if (currStateInfo.safetyState) {
			
			if (currStateInfo.lightState.getState() != 0) {
				toggleComponent(currStateInfo.lightState.getBlockLocation(), 
						ComponentType.LIGHT_COMP, currStateInfo.lightState.getState());
			}
			
			if (currStateInfo.switchState.getState() != 0) {
				toggleComponent(currStateInfo.switchState.getBlockLocation(), 
						ComponentType.SWITCH_COMP, currStateInfo.switchState.getState());
			}
			
			//calculate limits
			for (TrainWrapper sTrain : trainList)
			{
				Limits newLimits;
				double auth;
				double speedLimit = sTrain.getBlockLocation().postedSpeedLimit;
				
				double distance = Double.MAX_VALUE;
				for (TrainWrapper tTrain : trainList)
				{
					double temp = 0.0;
					if (tTrain.getBlockLocation().id != sTrain.getBlockLocation().id) {
						temp = sTrain.distToTrain(tTrain);
						if (temp != -1.0 && temp < distance) {
							distance = temp;
						}
					}
				}
				
				//only train on the track or no tracks are ahead
				if (distance == Double.MAX_VALUE) {
					distance = sTrain.getFutureBlock().length;
				}
				
				double tempAuth = -Math.pow(speedLimit, 2.0)/(2*-1.2);
				if (tempAuth < distance)
				{
					auth = distance - tempAuth;
					newLimits = new Limits(speedLimit, auth);
					sTrain.setCurrLimits(newLimits);
					setLimits(sTrain.getBlockLocation().id,sTrain.getCurrLimits());
				}
				else {
					//System.out.print("over here");
					//shutdown();
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
		case SWITCH_COMP:
			blockTable.get(blkID).switchState = state;
			System.out.print("CHANGED SWITCH STATE TOO: " + state + "\n");
			break;
		case LIGHT_COMP:
			//set light state
			//blockTable.get(blkID).lightState = state;
			break;
		}
	}
	
	/**
	 * Run the triple redundancy protocol for the PLCProgram 
	 */
	public void runRedundancy() {
		
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
