public class Block
{
	public int id;
	public double length;
	public double grade;
	public boolean defaultDirection;
	public double postedSpeedLimit;
	public Block prevBlock;
	public Block prevBlockAlt;
	public Block nextBlock;
	public Block nextBlockAlt;
	public boolean isYard;
	public boolean isStation;
	public String announcement;
	public boolean brokenRailFailure;
	public boolean trackCircuitFailure;
	public boolean powerFailure;
	public boolean isUnderground;
	public boolean hasSwitch;
	public int switchState;
	public double switchPosition;
	public double suggestedSpeed;
	public double suggestedAuthority;
	public boolean isOccupied;
	
	public Block(int id, 
				 double length, 
				 double grade, 
				 boolean defaultDirection, 
				 double postedSpeedLimit, 
				 Block prevBlock, 
				 Block prevBlockAlt, 
				 Block nextBlock, 
				 Block nextBlockAlt, 
				 boolean isYard, 
				 boolean isStation, 
				 String announcement, 
				 boolean brokenRailFailure, 
				 boolean trackCircuitFailure, 
				 boolean powerFailure,
				 boolean isUnderground,
				 boolean hasSwitch,
				 int switchState,
				 double switchPosition,
				 double suggestedSpeed,
				 double suggestedAuthority,
				 boolean isOccupied)
	{
		this.id = id;
		this.length = length;
		this.grade = grade;
		this.defaultDirection = defaultDirection;
		this.postedSpeedLimit = postedSpeedLimit;
		this.prevBlock = prevBlock;
		this.prevBlockAlt = prevBlockAlt;
		this.nextBlock = nextBlock;
		this.nextBlockAlt = nextBlockAlt;
		this.isYard = isYard;
		this.isStation = isStation;
		this.announcement = announcement;
		this.brokenRailFailure = brokenRailFailure;
		this.trackCircuitFailure = trackCircuitFailure;
		this.powerFailure = powerFailure;
		this.isUnderground = isUnderground;
		this.hasSwitch = hasSwitch;
		this.switchState = switchState;
		this.switchPosition = switchPosition;
		this.suggestedSpeed = suggestedSpeed;
		this.suggestedAuthority = suggestedAuthority;
		this.isOccupied = isOccupied;
	}
}