public class ResponseTNC
{
	public double powerCommand;
	public boolean issetServiceBrake;
	public boolean issetEmerBrake;
	public boolean issetLightsOn;
	public boolean issetDoorsOpen;
	public double targetTemperatureTNC;
	public String announcement;
	
	public ResponseTNC(double powerCommand, boolean issetServiceBrake, boolean issetEmerBrake, boolean issetLightsOn, boolean issetDoorsOpen, double targetTemperatureTNC, String announcement)
	{
		this.powerCommand = powerCommand;
		this.issetServiceBrake = issetServiceBrake;
		this.issetEmerBrake = issetEmerBrake;
		this.issetLightsOn = issetLightsOn;
		this.issetDoorsOpen = issetDoorsOpen;
		this.targetTemperatureTNC = targetTemperatureTNC;
		this.announcement = announcement;
	}
}