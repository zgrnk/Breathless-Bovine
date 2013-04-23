


/*
public class GPS{
	
	public Block block;
	public double speed;
	public double metersIntoBlock;
	public int travelDirection;
		
	
	public GPS(){
		
	}
	
}
*/


public class GPS
{
	public Block block;
	public int metersIntoBlock=0;
	public double speed;
	public boolean travelDirection;
	
	public GPS(Block block, int metersIntoBlock, double speed, boolean travelDirection)
	{
		this.block = block;
		this.metersIntoBlock = metersIntoBlock;
		this.speed = speed;
		this.travelDirection = travelDirection;
	}
	
	public Block getBlock()
	{
		return block;
	}
	
	public int getMetersIntoBlock()
	{
		return metersIntoBlock;
	}
	
	public double getSpeed()
	{
		return speed;
	}
	
	public boolean getTravelDirection()
	{
		return travelDirection;
	}
}