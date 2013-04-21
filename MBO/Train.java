
public class Train{
	
	public String id;
	public String line;
	public double length = 32.2;
	public double footprint;
	
	public double trainAheadDist;
	public double trainBehindDist;
	public double nextStationDist;
	
	public boolean signalPickup;
	public GPS gps;
	public double MBOsuggestedAuthority;
	public double MBOsuggestedSetspeed;

	
	public Train(String id, String line, boolean signalPickup){
		this.id = id;
		this.line = line;
		this.signalPickup = signalPickup;
		this.gps = new GPS();
	}
	
}
