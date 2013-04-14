package TKC.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import CTCOffice.CTCOffice;
import TKM.Block;



/**
 * 
 * @author Dominic Visco
 *
 */
public class TrackController {

	private Wayside currentWayside;
	private ArrayList<Wayside> controllerList;
	
	public TrackController(ArrayList<Block> blockList, CTCOffice ctc) {
		this.controllerList = initControllers(blockList, ctc);
	}
	
	public void nextTick() {
		for (Wayside way : controllerList)
		{
			way.nextTick();
		}
		
	}
	public ArrayList<Wayside> initControllers(ArrayList<Block> blockList, CTCOffice ctc) {
		
		ArrayList<Wayside> waysideList = new ArrayList<Wayside>();
		
		Component switchS = new Component(5,1);
		Component lightS = new Component(0,0);
		SafetyInfo newInfo = new SafetyInfo(switchS, lightS, true);
		
		
		Hashtable<Integer, Block> table = new Hashtable<Integer, Block>();
		ArrayList<Integer> endBlks = new ArrayList<Integer>();
		
		table.put(1, blockList.get(1));
		table.put(2, blockList.get(2));
		table.put(3, blockList.get(3));
		table.put(4, blockList.get(4));
		table.put(5, blockList.get(5));
		table.put(6, blockList.get(6));
		table.put(7, blockList.get(7));
		table.put(8, blockList.get(8));
		table.put(10, blockList.get(10));
		table.put(11, blockList.get(11));
		table.put(12, blockList.get(12));
		
		endBlks.add(1);
		endBlks.add(12);
		endBlks.add(13);
		endBlks.add(4);
		
		Wayside tmp = new Wayside(table, endBlks, ctc, newInfo);
		tmp.setPLC(new PLCProgram(){

			@Override
			public SafetyInfo runPLC(Hashtable<Integer, Block> blkTable,
					ArrayList<Integer> endBlks, LinkedList<Block> activeBlks,
					LinkedList<TrainWrapper> trainList) {
				Component swS = new Component(5,1);
				Component litS = new Component(0,0);
				boolean isSafe = true;
				
				if (blkTable.get(4).isOccupied) {
					TrainWrapper foundT = null;
					for (TrainWrapper sTrain : trainList) {
						if (sTrain.getBlockLocation().id == 4) {
							foundT = sTrain;
							break;
						}
					}
					
					if (foundT != null) {
						
						//System.out.println("INDEX is: " + foundT.train.getRoute().get(foundT.train.getRouteIndex() -).id + "\n");
						System.out.println("PRESENT in 2 is: " + foundT.train.getRoute().get(foundT.train.getRouteIndex() + 1).id + "\n");
						if (foundT.train.getRoute().get(foundT.train.getRouteIndex() + 1).id == 10 ) {
							swS.setState(2);
						}
					}
					else {
						isSafe = false;
					}
				}
				
				
				SafetyInfo newFo = new SafetyInfo(swS, litS, isSafe);
				return newFo;
			}
			
		}
		);
		waysideList.add(tmp);
		
		
		
		
		
		
		return waysideList;
		
	}
}
