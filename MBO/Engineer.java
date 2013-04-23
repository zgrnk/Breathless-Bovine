

public class Engineer
{
	public boolean goOnBreak;
	public boolean onBreak;
	public double timeOnBreak;
	public double timeOnBreakStarts;
	
	public Engineer()
	{
		goOnBreak = false;
		onBreak = false;
		timeOnBreak = 0.0;
		timeOnBreakStarts = 0;
	}
	
	public Engineer(boolean goOnBreak, boolean onBreak, double timeOnBreak, double timeOnBreakStarts)
	{
		this.goOnBreak = goOnBreak;
		this.onBreak = onBreak;
		this.timeOnBreak = timeOnBreak;
		this.timeOnBreakStarts = timeOnBreakStarts;
	}
	
	public boolean getGoOnBreak()
	{
		return goOnBreak;
	}
	
	public boolean getOnBreak()
	{
		return onBreak;
	}
	
	public double getTimeOnBreak()
	{
		return timeOnBreak;
	}
	
	public double getTimeOnBreakStarts()
	{
		return timeOnBreakStarts;
	}
}