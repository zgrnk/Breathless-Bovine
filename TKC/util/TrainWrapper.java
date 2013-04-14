package TKC.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import TKC.util.Limits;

public class TrainWrapper {
	public Train train;
	private Block blockLocation;
	private Block futureBlock;
	private Limits currLimits;
	
	public TrainWrapper(Train paramTrain, Block blockId) {
		this.train = paramTrain;
		this.setBlockLocation(blockId);
		this.setFutureBlock(this.train.getRoute().get(this.train.getRouteIndex()));
	}
	
	public void updateLocation() {
		this.blockLocation = this.futureBlock;
		this.setFutureBlock(this.train.getRoute().get(this.train.getRouteIndex()));
	}

	public Block getFutureBlock() {
		return futureBlock;
	}

	private void setFutureBlock(Block futureBlock) {
		this.futureBlock = futureBlock;
	}

	public Block getBlockLocation() {
		return blockLocation;
	}

	public void setBlockLocation(Block blockLocation) {
		this.blockLocation = blockLocation;
	}
	
	/**
	 * distance in meters to the closest occupied block
	 * @param toTrain
	 * @return
	 */
	public double distToTrain(TrainWrapper toTrain) {
		
		if (train.getRoute().contains(toTrain.blockLocation)) {
			Iterator<Block> itr = train.getRoute().iterator();
			boolean flag = false;
			double dist = 0.0;
			Block temp;
			
			while(itr.hasNext() && !flag)
			{
				temp = itr.next();
				if(temp.equals(toTrain.blockLocation)) {
					flag = true;
				}
				else {
					dist += temp.length;
				}
					
			}
			return dist;
		}
		else {
			return -1.0;
		}
		
	}
	
	/**
	 * distance to the end of the train's current zone
	 * @return
	 *//*
	public double distToEndZone(ArrayList<String> endblks, Hashtable<String,Block> blkTable)
	{
		for (String id : endblks)
		{
			if
		}
			
			
		if (train.route.contains(future) {
			Iterator<Block> itr = train.route.iterator();
			boolean flag = false;
			double dist = 0.0;
			Block temp;
			
			while(itr.hasNext() && !flag)
			{
				temp = itr.next();
				if(temp.equals(toTrain.blockLocation)) {
					flag = true;
				}
				else {
					dist += temp.getLength();
				}
					
			}
			return dist;
		}
		else {
			return -1.0;
		}
		return 0;
	}*/

	public Limits getCurrLimits() {
		return currLimits;
	}

	public void setCurrLimits(Limits currLimits) {
		this.currLimits = currLimits;
	}
}

