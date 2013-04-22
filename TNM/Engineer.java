/**
 * NSECS-TNM
 * 
 * This file contains the main container class which holds the information regarding 
 *   the crew member (engineer) and his/her break schedule/status.
 * 
 * @author Chris Paskie 
 * @version 04/25/2013
*/


package TNM;

public class Engineer {
	public boolean goOnBreak;			// Is it past the engineer's break time?
	public boolean onBreak;				// Is the engineer currently on break?
	public double timeOnBreak;			// How long has the engineer been on break today?
	public double timeOnBreakStarts;	// At what tie does the break start?
	
	public Engineer() {
		goOnBreak = false;
		onBreak = false;
		timeOnBreak = 0.0;
		timeOnBreakStarts = 0;
	}
	
	public Engineer(boolean goOnBreak, boolean onBreak, double timeOnBreak, double timeOnBreakStarts) {
		this.goOnBreak = goOnBreak;
		this.onBreak = onBreak;
		this.timeOnBreak = timeOnBreak;
		this.timeOnBreakStarts = timeOnBreakStarts;
	}
}