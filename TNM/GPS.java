/**
 * NSECS-TNM & MBO
 * 
 * This file contains the main container class which holds the response from a 
 *   train to the MBO/GPS after each timeTick.
 * 
 * @author Chris Paskie 
 * @version 04/25/2013
*/


package TNM;
import TKM.*;

public class GPS {
	public Block block;					// Block currently occupied by the front of the train.
	public int metersIntoBlock;			// Position meters within block (number line reference).
	public double speed;				// Current velocity of train rounded to nearest meter.
	public boolean travelDirection;		// Is the train travelling in the default direction of the block?
	
	public GPS(Block block, int metersIntoBlock, double speed, boolean travelDirection) {
		this.block = block;
		this.metersIntoBlock = metersIntoBlock;
		this.speed = speed;
		this.travelDirection = travelDirection;
	}
}