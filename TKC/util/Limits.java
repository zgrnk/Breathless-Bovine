package nsecs.TKC.util;

public class Limits {
	private double speedLimit;
	private double authority;
	
	public Limits (double speed, double auth) {
		this.setSpeedLimit(speed);
		this.setAuthority(auth);
	}

	public double getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(double speedLimit) {
		this.speedLimit = speedLimit;
	}

	public double getAuthority() {
		return authority;
	}

	public void setAuthority(double authority) {
		this.authority = authority;
	}

}
