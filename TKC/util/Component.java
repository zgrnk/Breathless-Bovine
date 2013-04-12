package nsecs.TKC.util;

public class Component {
	
	private Integer blockLocation;
	private int state;
	
	public Component(Integer ID, int state) {
		
		this.setBlockLocation(ID);
		this.setState(state);
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Integer getBlockLocation() {
		return blockLocation;
	}

	public void setBlockLocation(Integer blockLocation) {
		this.blockLocation = blockLocation;
	}
}
