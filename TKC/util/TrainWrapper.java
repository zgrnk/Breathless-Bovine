package TKC.util;

import java.util.ArrayList;

import TKC.util.Limits;
import TKM.Block;
import TNM.Train;

public class TrainWrapper {
	public Train train;
	private Block blockLocation;
	private Block futureBlock;
	private Limits currLimits;

	public TrainWrapper(Train paramTrain, Block blockId) {
		this.train = paramTrain;
		this.setBlockLocation(blockId);
		try {
			this.setFutureBlock(this.train.route.get(this.train.routeIndex + 1));
		} catch (IndexOutOfBoundsException ex) {
			this.setFutureBlock(null);
		}
	}

	public void updateLocation() {
		this.blockLocation = this.train.positionBlock;
		try {
			this.setFutureBlock(this.train.route.get(this.train.routeIndex + 1));
		} catch (IndexOutOfBoundsException ex) {
			this.setFutureBlock(null);
		}
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
	 * distance in meters to the specified train, only to be used if determining trains that aren't leaving zone
	 * @param toTrain
	 * @return distance to train in meters, if specified train is not in current train's route currently than
	 * returned distance is to the end of the current zone.
	 */
	public double distToTrain(ArrayList<Integer> endBlks, TrainWrapper toTrain) {
		double dist = 0.0;
		int index = train.routeIndex;
		boolean flag = true;

		while (flag) {
			try {
				if (train.route.get(index + 1).id == toTrain.blockLocation.id) {
					flag = false;
				}else if (isEndBlk(endBlks, train.route.get(index + 1).id)) {
					dist += train.route.get(index + 1).length;
					flag = false;
				}else {
					dist += train.route.get(index + 1).length;
					index++;
				}
			}catch (IndexOutOfBoundsException e) {
				flag = false;
			}
		}

		return dist;

	}

	/**
	 * is specified block an end block
	 * @return
	 */
	public static boolean isEndBlk(ArrayList<Integer> endBlks, Integer id)
	{
		for (Integer i : endBlks) {
			if (i.equals(id))
				return true;
		}
		return false;
	}

	public Limits getCurrLimits() {
		return currLimits;
	}

	public void setCurrLimits(Limits currLimits) {
		this.currLimits = currLimits;
	}
}

