package nsecs.TKC.util;

/**
 * 
 * @author Dominic Visco
 *
 */
public class SafetyInfo {
	public Component switchState;
	public Component lightState;
	public boolean safetyState;
	
	/**
	 * Constructor for safety info
	 * @param swState current switch state
	 * @param lState current light state
	 * @param safState current safety state
	 */
	public SafetyInfo(Component swState, Component lState, boolean safState)
	{
		this.switchState = swState;
		this.lightState = lState;
		this.safetyState = safState;
	}
}
