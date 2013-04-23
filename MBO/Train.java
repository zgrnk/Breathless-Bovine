

/*
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
 */




import java.util.*;
import java.io.*;


public class Train
{
	public double g = 9.80665;


	// Static Values
	public int id;
	public String stringId;
	public double length;
	public double width;
	public double height;
	public int numCars;
	public double motorPower;
	public double maxSpeed;
	public double serviceBrakeDecel;
	public double emerBrakeDecel;
	public double frictionCoeff;
	public double emptyTrainMass;
	public double personMass;
	public int maxCapacitySeated;
	public int maxCapacityStanding;
	public int maxCapacityPassengers;
	public int maxCapacityCrew;

	// Dynamic Values
	public double curVelocity;
	public double curAccel;
	public double receivedPower;
	public double manualPower;
	public boolean issetManualPower;
	public double postedSpeedLimit;
	public double manualSpeedLimit;
	public boolean issetManualSpeedLimit;
	public int numPassengers;
	public int numCrew;
	public double totalMass;
	public Block positionBlock;
	public Block positionBlockTail;
	public double positionMeters;
	public boolean positionDirection;
	public boolean issetSignalPickupFailure;
	public boolean issetEngineFailure;
	public boolean issetBrakeFailure;
	public boolean issetServiceBrake;
	public boolean issetEmerBrake;
	public boolean issetLightsOn;
	public boolean issetLightsOnManual;
	public boolean issetLightsOnUseManual;
	public boolean issetDoorsOpen;
	public boolean issetDoorsOpenManual;
	public boolean issetDoorsOpenUseManual;
	public double curTemperature;
	public double targetTemperatureTNC;
	public double targetTemperatureManual;
	public boolean issetTargetTemperatureManual;
	public String announcement;
	
	//ADDED BY MARIO
	public double trainAheadDist;
	public double trainBehindDist;
	public double nextStationDist;
	//ADDED BY MARIO

	// Other Values
	public GPS gps;
	public int footPrint;
	public int nextTrainId;
	public String line;
	public double dispatchTime;
	public ArrayList<Block> route;
	public int routeIndex;
	public double fixedSuggestedAuthority;
	public double fixedSuggestedSpeed;
	public double mboSuggestedAuthority;
	public double mboSuggestedSpeed;
	public double suggestedAuthority;
	public double suggestedSpeed;
	public Engineer engineer;
	public boolean goOnBreak;



	public Train(int id, String stringId, String line, double dispatchTime, ArrayList<Block> route, Engineer engineer, Block positionBlock)
	{
		//tnc = new TrainController();

		// Initialize the following static values from the .csv data file.
		this.id = id;
		this.stringId = stringId;
		length = 32.2;
		width = 2.65;
		height = 3.42;
		numCars = 1;
		motorPower = 120000;
		maxSpeed = 19.4444;
		serviceBrakeDecel = 1.2;
		emerBrakeDecel = 2.73;
		frictionCoeff = 0.002;
		emptyTrainMass = 37103.86;
		personMass = 80;
		maxCapacitySeated = 74;
		maxCapacityStanding = 148;
		maxCapacityPassengers = maxCapacitySeated + maxCapacityStanding;
		maxCapacityCrew = 1;

		// Initialize the following dynamic values.
		curVelocity = 0.0;
		curAccel = 0.0;
		receivedPower = 0.0;
		manualPower = 0.0;
		issetManualPower = false;
		manualSpeedLimit = 0.0;
		issetManualSpeedLimit = false;
		numPassengers = 0;
		numCrew = 0;
		totalMass = emptyTrainMass + personMass * (numPassengers + numCrew);
		this.positionBlock = positionBlock;
		this.positionBlockTail = positionBlock;
		positionMeters = 0.0;
		positionDirection = true;
		postedSpeedLimit = positionBlock.speedLimit;
		issetSignalPickupFailure = false;
		issetEngineFailure = false;
		issetBrakeFailure = false;
		issetServiceBrake = false;
		issetEmerBrake = false;
		issetLightsOn = false;
		issetLightsOnManual = false;
		issetLightsOnUseManual = false;
		issetDoorsOpen = true;
		issetDoorsOpenManual = true;
		issetDoorsOpenUseManual = false;
		curTemperature = 72.0;
		targetTemperatureTNC = 72.0;
		targetTemperatureManual = 72.0;
		issetTargetTemperatureManual = false;
		announcement = "";

		// Initialize the following other values.
		gps = new GPS(positionBlock, (int)positionMeters, curVelocity, positionDirection);
		footPrint = 0;
		nextTrainId = 0;
		this.line = line;
		this.dispatchTime = dispatchTime;
		this.route = route;
		routeIndex = 0;
		fixedSuggestedAuthority = 0.0;
		fixedSuggestedSpeed = 0.0;
		mboSuggestedAuthority = 0.0;
		mboSuggestedSpeed = 0.0;
		suggestedAuthority = 0.0;
		suggestedSpeed = 0.0;
		//this.engineer = engineer;
		goOnBreak = false;
	}


}
