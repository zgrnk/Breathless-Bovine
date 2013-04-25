package TNM;

/**
 * @class PowJar
 * 
 * @date 04/24/2013
 * 
 * @author Ben Tomasulo
 */


 

/**
 * This file contains the container class which holds the 
 * power response of a Train Controller PLC
 * 
 */
public class PowJar {
	public double jPower;
	public double jUk;
	public double jErr;
	public boolean jEBrake;
	public boolean jSBrake;
	
	public PowJar(double jPower, double jUk, double jErr, boolean jEBrake, boolean jSBrake) {
		this.jPower=jPower;
		this.jUk=jUk;
		this.jErr=jErr;
		this.jEBrake=jEBrake;
		this.jSBrake=jSBrake;
	}
}