package TNM;

/**
 * @class ResponseUI
 * 
 * @date 04/23/2013
 * 
 * @author Ben Tomasulo
 */


 

/**
 * This file contains the main container class which holds the response between a 
 * given train's controller and the UI after each timeTick.
 */
public class ResponseUI {
	public double powerCommand;
	public boolean issetServiceBrake;
	public boolean issetEmerBrake;
	public boolean issetLightsOn;
	public boolean issetDoorsOpen;
	public double targetTemperatureTNC;
	public String announcement;
	
	public ResponseUI(double powerCommand, boolean issetServiceBrake, boolean issetEmerBrake, 
			boolean issetLightsOn, boolean issetDoorsOpen, double targetTemperatureTNC, 
			String announcement) {
		this.powerCommand = powerCommand;
		this.issetServiceBrake = issetServiceBrake;
		this.issetEmerBrake = issetEmerBrake;
		this.issetLightsOn = issetLightsOn;
		this.issetDoorsOpen = issetDoorsOpen;
		this.targetTemperatureTNC = targetTemperatureTNC;
		this.announcement = announcement;
	}
}