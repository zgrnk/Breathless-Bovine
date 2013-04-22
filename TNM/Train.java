package TNM;
import TKM.*;

/*
XXX
*/





import java.util.*;
import java.io.*;





public class Train
{
	public double g = 9.80665;
	
	public TrainController tnc;
	
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
		tnc = new TrainController();
		
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
		positionDirection = Block.DIRECTION_FWD;
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
		gps = new GPS(positionBlock, (int)positionMeters, curVelocity, (positionDirection==Block.DIRECTION_FWD));
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
		this.engineer = engineer;
		goOnBreak = false;
	}
	
/*
	public int getId()
	{
		return id;
	}
	
	public String getStringId()
	{
		return stringId;
	}
	
	public TrainController getTNC()
	{
		return tnc;
	}
	
	public double getLength()
	{
		return length;
	}
	
	public double getWidth()
	{
		return width;
	}
	
	public double getHeight()
	{
		return height;
	}
	
	public int getNumCars()
	{
		return numCars;
	}
	
	public double getMotorPower()
	{
		return motorPower;
	}
	
	public double getMaxSpeed()
	{
		return maxSpeed;
	}
	
	public double getServiceBrakeDecel()
	{
		return serviceBrakeDecel;
	}
	
	public double getEmerBrakeDecel()
	{
		return emerBrakeDecel;
	}
	
	public double getFrictionCoeff()
	{
		return frictionCoeff;
	}
	
	public double getEmptyTrainMass()
	{
		return emptyTrainMass;
	}
	
	public double getPersonMass()
	{
		return personMass;
	}
	
	public int getMaxCapacitySeated()
	{
		return maxCapacitySeated;
	}
	
	public int getMaxCapacityStanding()
	{
		return maxCapacityStanding;
	}
	
	public int getMaxCapacityCrew()
	{
		return maxCapacityCrew;
	}
	
	public double getCurVelocity()
	{
		return curVelocity;
	}
	
	public double getCurAccel()
	{
		return curAccel;
	}
	
	public double getReceivedPower()
	{
		return receivedPower;
	}
	
	public double getManualPower()
	{
		return manualPower;
	}
	
	public boolean getIssetManualPower()
	{
		return issetManualPower;
	}
	
	public double getPostedSpeedLimit()
	{
		return positionBlock.speedLimit;
	}
	
	public double getManualSpeedLimit()
	{
		return manualSpeedLimit;
	}
	
	public boolean getIssetManualSpeedLimit()
	{
		return issetManualSpeedLimit;
	}
	
	public int getNumPassengers()
	{
		return numPassengers;
	}
	
	public int getNumCrew()
	{
		return numCrew;
	}
	
	public double getTotalMass()
	{
		return totalMass;
	}
	
	public Block getPositionBlock()
	{
		return positionBlock;
	}
	
	public Block getPositionBlockTail()
	{
		return positionBlockTail;
	}
	
	public double getPositionMeters()
	{
		return positionMeters;
	}
	
	public boolean getpositionDirection()
	{
		return positionDirection;
	}
	
	public boolean getIssetSignalPickupFailure()
	{
		return issetSignalPickupFailure;
	}
	
	public boolean getIssetEngineFailure()
	{
		return issetEngineFailure;
	}
	
	public boolean getIssetBrakeFailure()
	{
		return issetBrakeFailure;
	}
	
	public boolean getIssetServiceBrake()
	{
		return issetServiceBrake;
	}
	
	public boolean getIssetEmerBrake()
	{
		return issetEmerBrake;
	}
	
	public boolean getIssetLightsOn()
	{
		return issetLightsOn;
	}
	
	public boolean getIssetLightsOnManual()
	{
		return issetLightsOnManual;
	}
	
	public boolean getIssetLightsOnUseManual()
	{
		return issetLightsOnUseManual;
	}
	
	public boolean getIssetDoorsOpen()
	{
		return issetDoorsOpen;
	}
	
	public boolean getIssetDoorsOpenManual()
	{
		return issetDoorsOpenManual;
	}
	
	public boolean getIssetDoorsOpenUseManual()
	{
		return issetDoorsOpenUseManual;
	}
	
	public double getCurTemperature()
	{
		return curTemperature;
	}
	
	public double getTargetTemperatureTNC()
	{
		return targetTemperatureTNC;
	}
	
	public double getTargetTemperatureManual()
	{
		return targetTemperatureManual;
	}
	
	public boolean getIssetTargetTemperatureManual()
	{
		return issetTargetTemperatureManual;
	}
	
	public String getAnnouncement()
	{
		return announcement;
	}
	
	public GPS getGPS()
	{
		return gps;
	}
	
	public int getFootPrint()
	{
		return footPrint;
	}
	
	public int getNextTrainId()
	{
		return nextTrainId;
	}
	
	public String getLine()
	{
		return line;
	}
	
	public double getDispatchTime()
	{
		return dispatchTime;
	}
	
	public ArrayList<Block> getRoute()
	{
		return route;
	}
	
	public int getRouteIndex()
	{
		return routeIndex;
	}
	
	public double getFixedSuggestedAuthority()
	{
		return fixedSuggestedAuthority;
	}
	
	public double getFixedSuggestedSpeed()
	{
		return fixedSuggestedSpeed;
	}
	
	public double getMBOSuggestedAuthority()
	{
		return mboSuggestedAuthority;
	}
	
	public double getMBOSuggestedSpeed()
	{
		return mboSuggestedSpeed;
	}
	
	public double getSuggestedAuthority()
	{
		return suggestedAuthority;
	}
	
	public double getSuggestedSpeed()
	{
		return suggestedSpeed;
	}
	
	public Engineer getEngineer()
	{
		return engineer;
	}
	
	public boolean getGoOnBreak()
	{
		return goOnBreak;
	}
	
	public void setManualPower(double manualPower)
	{
		this.manualPower = manualPower;
	}
	
	public void setIssetManualPower(boolean issetManualPower)
	{
		this.issetManualPower = issetManualPower;
	}
	
	public void setManualSpeedLimit(double manualSpeedLimit)
	{
		this.manualSpeedLimit = manualSpeedLimit;
	}
	
	public void setIssetManualSpeedLimit(boolean issetManualSpeedLimit)
	{
		this.issetManualSpeedLimit = issetManualSpeedLimit;
	}
	
	public void setIssetSignalPickupFailure(boolean issetSignalPickupFailure)
	{
		this.issetSignalPickupFailure = issetSignalPickupFailure;
	}
	
	public void setIssetEngineFailure(boolean issetEngineFailure)
	{
		this.issetEngineFailure = issetEngineFailure;
	}
	
	public void setIssetBrakeFailure(boolean issetBrakeFailure)
	{
		this.issetBrakeFailure = issetBrakeFailure;
	}
	
	public void setIssetServiceBrake(boolean issetServiceBrake)
	{
		this.issetServiceBrake = issetServiceBrake;
	}
	
	public void setIssetEmerBrake(boolean issetEmerBrake)
	{
		this.issetEmerBrake = issetEmerBrake;
	}
	
	public void setIssetLightsOnManual(boolean issetLightsOnManual)
	{
		this.issetLightsOnManual = issetLightsOnManual;
	}
	
	public void setIssetLightsOnUseManual(boolean issetLightsOnUseManual)
	{
		this.issetLightsOnUseManual = issetLightsOnUseManual;
	}
	
	public void setIssetDoorsOpenManual(boolean issetDoorsOpenManual)
	{
		this.issetDoorsOpenManual = issetDoorsOpenManual;
	}
	
	public void setIssetDoorsOpenUseManual(boolean issetDoorsOpenUseManual)
	{
		this.issetDoorsOpenUseManual = issetDoorsOpenUseManual;
	}
	
	public void setTargetTemperatureManual(double targetTemperatureManual)
	{
		this.targetTemperatureManual = targetTemperatureManual;
	}
	
	public void setIssetTargetTemperatureManual(boolean issetTargetTemperatureManual)
	{
		this.issetTargetTemperatureManual = issetTargetTemperatureManual;
	}
	
	public void setFixedSuggestedAuthority(double fixedSuggestedAuthority)
	{
		this.fixedSuggestedAuthority = fixedSuggestedAuthority;
	}
	
	public void setFixedSuggestedSpeed(double fixedSuggestedSpeed)
	{
		this.fixedSuggestedSpeed = fixedSuggestedSpeed;
	}
	
	public void setMBOSuggestedAuthority(double mboSuggestedAuthority)
	{
		this.mboSuggestedAuthority = mboSuggestedAuthority;
	}
	
	public void setMBOSuggestedSpeed(double mboSuggestedSpeed)
	{
		this.mboSuggestedSpeed = mboSuggestedSpeed;
	}
	
	public void setSuggestedAuthority(double suggestedAuthority)
	{
		this.suggestedAuthority = suggestedAuthority;
	}
	
	public void setSuggestedSpeed(double suggestedSpeed)
	{
		this.suggestedSpeed = suggestedSpeed;
	}
	
	public void setEngineer(Engineer engineer)
	{
		this.engineer = engineer;
	}
	
	public void setGoOnBreak(boolean goOnBreak)
	{
		this.goOnBreak = goOnBreak;
	}
*/
	
	public void timeTick(double time, double period, boolean isSolo)
	{
System.out.println("XXX - ////////////////////////////////////////////////////");
		// TrainController timeTick and Response
		ResponseTNC tncResponse = new ResponseTNC(0.0, false, false, false, false, 0.0, "");
		if (!isSolo)
			tncResponse = tnc.timeTick(time, curVelocity, period, positionBlock, positionBlockTail, issetSignalPickupFailure, issetEngineFailure, issetBrakeFailure, fixedSuggestedSpeed, mboSuggestedSpeed, issetEmerBrake, curTemperature);
		
		if (!issetDoorsOpen  &&  numCrew > 0)
		{
			// Slope Direction
			boolean uphill = false;
			boolean downhill = false;
			boolean flat = false;
			if ((positionBlock.grade > 0.0  &&  positionDirection==Block.DIRECTION_FWD)  ||  (positionBlock.grade < 0.0  &&  positionDirection!=Block.DIRECTION_FWD))
				uphill = true;
			else if ((positionBlock.grade > 0.0  &&  positionDirection!=Block.DIRECTION_FWD)  ||  (positionBlock.grade < 0.0  &&  positionDirection==Block.DIRECTION_FWD))
				downhill = true;
			else
				flat = true;
System.out.println("XXX - uphill\t\t"+(uphill));
System.out.println("XXX - downhill\t\t"+(downhill));
System.out.println("XXX - flat\t\t"+(flat));
			
			// Angle of Inclination
			double angle = 0.0;
			if (positionBlock.grade > 0.0)
				angle = Math.atan(positionBlock.grade / 100.0);
			else if (positionBlock.grade < 0.0)
				angle = Math.atan(((-1.0) * positionBlock.grade) / 100.0);
			
			// Power Command
			if (!isSolo)
				receivedPower = tncResponse.powerCommand;
			if (manualPower > motorPower)
				manualPower = motorPower;
			if (receivedPower > motorPower)
				receivedPower = motorPower;
			
			// Brake Commands
			if (!isSolo)
			{
				issetServiceBrake = tncResponse.issetServiceBrake;
				issetEmerBrake = tncResponse.issetEmerBrake;
			}
			
			// Engine
			double accelEngine;
			if (issetEngineFailure)
				accelEngine = 0.0;
			else
			{
				if (!isSolo  &&  !issetManualPower)
				{
					if (curVelocity <= 0.0)
						accelEngine = receivedPower / (0.0001 * totalMass);
					else
						accelEngine = receivedPower / (curVelocity * totalMass);
				}
				else
				{
					if (curVelocity <= 0.0)
						accelEngine = manualPower / (0.0001 * totalMass);
					else
						accelEngine = manualPower / (curVelocity * totalMass);
				}
			}
System.out.println("XXX - issetEngineFailure\t\t"+(issetEngineFailure));
System.out.println("XXX - !isSolo  &&  !issetManualPower\t"+(!isSolo  &&  !issetManualPower));
System.out.println("XXX - curVelocity <= 0.0\t\t"+(curVelocity <= 0.0));
			if (accelEngine > (motorPower /  (0.0001 * totalMass)))
				accelEngine = motorPower / (0.0001 * totalMass);
			double velEngine = accelEngine * period;
			if (velEngine > maxSpeed)
				velEngine = maxSpeed;
			
			// Brakes
			double accelBrakes = 0.0;
			if (!issetBrakeFailure  &&  (issetEmerBrake  ||  issetServiceBrake))
			{
				accelEngine = 0.0;
				velEngine = 0.0;
				
				if (issetEmerBrake)
					accelBrakes = emerBrakeDecel;
				else if (issetServiceBrake)
					accelBrakes = serviceBrakeDecel;
				
				if (curVelocity > 0.0)
					accelBrakes *= (-1.0);
				else if (curVelocity == 0.0)
					accelBrakes = 0.0;
			}
			double velBrakes = accelBrakes * period;
			
			// Gravity
			double accelGravity = 0.0;
			if (uphill)
				accelGravity = (-1.0) * g * Math.sin(angle);
			else if (downhill)
				accelGravity = g * Math.sin(angle);
			else if (flat)
				accelGravity = 0.0;
			double velGravity = accelGravity * period;
			
			// Current w/o Brakes & Friction
			curAccel = accelEngine + accelGravity;
			double newVelocity = curVelocity + velEngine + velGravity;
			
			// Friction
			double accelFriction;
			accelFriction = frictionCoeff * g * Math.cos(angle);
			if (newVelocity > 0)
				accelFriction *= (-1.0);
			else if (newVelocity == 0.0)
				accelFriction = 0.0;
			double velFriction = accelFriction * period;
System.out.println("XXX - accelEngine\t"+accelEngine);
System.out.println("XXX - accelBrakes\t"+accelBrakes);
System.out.println("XXX - accelGravity\t"+accelGravity);
System.out.println("XXX - accelFriction\t"+accelFriction);
System.out.println("XXX - velEngine\t\t"+velEngine);
System.out.println("XXX - velBrakes\t\t"+velBrakes);
System.out.println("XXX - velGravity\t"+velGravity);
System.out.println("XXX - velFriction\t"+velFriction);
			
			// Current (and Limits)
			curAccel += (accelBrakes + accelFriction);
			curAccel = round(curAccel, 3);
			newVelocity += (velBrakes + velFriction);
			newVelocity = round(newVelocity, 3);
			if (((curVelocity < 0.0  &&  newVelocity > 0.0)  ||  (curVelocity > 0.0  &&  newVelocity < 0.0))  &&  ((!issetBrakeFailure  &&  (issetEmerBrake  ||  issetServiceBrake))  ||  flat))
			{
				// If the train's direction of travel has changed and (the brakes are being applied or the slope is flat), then the train should come to a complete stop.
				curVelocity = 0.0;
			}
			else if (uphill  &&  newVelocity > maxSpeed + velFriction + velGravity)
			{
				// If the train is traveling uphill, then the final max speed of the train will actually have friction and gravity factored in.
				curVelocity = maxSpeed + velFriction + velGravity;
			}
			else if (downhill  &&  curVelocity < maxSpeed + velFriction  &&  newVelocity > maxSpeed + velFriction  &&  velEngine > 0.0)
			{
				// If the train is traveling downhill, then there is no maximum speed of the train, but the speed from the engine must still be limited
				// (and have friction factored in as seen in the above if statement).
				double velEngineDifference = velEngine - (maxSpeed - curVelocity);
				if (velEngineDifference > 0.0)
				{
					// If the engine speed is responsible for causing the train to exceed the max speed, then the difference must be taken away.
					curVelocity = newVelocity - velEngineDifference;
				}
				else
				{
					// Else gravity was responsible for causing the train to exceed the max speed.
					curVelocity = newVelocity;
				}
			}
			else if (flat  &&  newVelocity > maxSpeed + velFriction)
			{
				// If the train is travelling on a flat block, then the final max speed of the train will actually have friction factored in.
				curVelocity = maxSpeed + velFriction;
			}
			else
			{
				curVelocity = newVelocity;
			}
			curVelocity = round(curVelocity, 3);
System.out.println("XXX - curVelocity\t"+curVelocity);
			
/*
			// Position
			if (positionDirection)
			{
//System.out.println("XXX - positionDirection\t"+positionDirection);
				positionMeters += (curVelocity * period);
				
				if (positionMeters >= positionBlock.length)
				{
//System.out.println("XXX - positionMeters >= positionBlock.length");
					double difference = positionMeters - positionBlock.length;
//System.out.println("XXX - difference\t"+difference);
					
					if (positionBlock.id == positionBlock.nextBlock.nextBlock.id)
						positionDirection = false;
					else
						positionDirection = true;
//System.out.println("XXX - positionDirection\t"+positionDirection);
					
					positionBlock.isOccupied = false;
					positionBlock = positionBlock.nextBlock;
					positionBlock.isOccupied = true;
					routeIndex++;
					
					if (positionDirection)
						positionMeters = difference;
					else
						positionMeters = positionBlock.length - difference;
//System.out.println("XXX - positionMeters\t"+positionMeters);
				}
			}
			else
			{
//System.out.println("XXX - positionDirection\t"+positionDirection);
				positionMeters -= (curVelocity * period);
				
				if (positionMeters < 0.0)
				{
//System.out.println("XXX - positionMeters < 0.0");
					double difference = positionMeters * (-1.0);
//System.out.println("XXX - difference\t"+difference);
					
					if (positionBlock.id == positionBlock.prevBlock.nextBlock.id)
						positionDirection = false;
					else
						positionDirection = true;
//System.out.println("XXX - positionDirection\t"+positionDirection);
					
					positionBlock.isOccupied = false;
					positionBlock = positionBlock.prevBlock;
					positionBlock.isOccupied = true;
					routeIndex++;
					
					if (positionDirection)
						positionMeters = difference;
					else
						positionMeters = positionBlock.length - difference;
//System.out.println("XXX - positionMeters\t"+positionMeters);
				}
			}
			positionBlockTail.isOccupied = false;
			if ((positionDirection  &&  positionMeters > length)  ||  (!positionDirection  &&  positionBlock.length - positionMeters > length))
				positionBlockTail = positionBlock;
			positionBlockTail.isOccupied = true;
			positionMeters = round(positionMeters, 3);
			gps = new GPS(positionBlock, (int)positionMeters, curVelocity, positionDirection);
*/
System.out.println("XXX - this.positionDirection\t"+this.positionDirection);
System.out.println("XXX - curVelocity * period\t"+(curVelocity * period));
			Block.advanceTrain(this, curVelocity * period);
			gps = new GPS(positionBlock, (int)positionMeters, curVelocity, (positionDirection==Block.DIRECTION_FWD));
		}
System.out.println("XXX - positionBlock.id\t"+positionBlock.id);
System.out.println("XXX - positionMeters\t"+positionMeters);
		
		// Lights
		if (issetLightsOnUseManual  ||  isSolo)
			issetLightsOn = issetLightsOnManual;
		else
			issetLightsOn = tncResponse.issetLightsOn;
		
		// Doors
		if (issetDoorsOpenUseManual  ||  isSolo)
			issetDoorsOpen = issetDoorsOpenManual;
		else
			issetDoorsOpen = tncResponse.issetDoorsOpen;
		
		// Temperature
		if (!isSolo)
			targetTemperatureTNC = tncResponse.targetTemperatureTNC;
		if (issetTargetTemperatureManual  ||  isSolo)
		{
			if (curTemperature > targetTemperatureManual)
				curTemperature -= 0.1;
			else if (curTemperature < targetTemperatureManual)
				curTemperature += 0.1;
		}
		else
		{
			if (curTemperature > targetTemperatureTNC)
				curTemperature -= 0.1;
			else if (curTemperature < targetTemperatureTNC)
				curTemperature += 0.1;
		}
		curTemperature = round(curTemperature, 1);
		
		// Announcement
		if (!isSolo)
			announcement = tncResponse.announcement;
		else
		{
			if (positionBlock.isStation)
				announcement = "Welcome to Station " + positionBlock.stationName + "!";
			else if (positionBlock.isYard)
				announcement = "Welcome to the Yard!";
			else
				announcement = "";
		}
		
		// Passengers
		if (positionBlock.isStation  &&  (isSolo  ||  (!isSolo/*  &&  XXX trainShouldStopHere XXX*/))  &&  issetDoorsOpen)
		{
			Random r = new Random(System.currentTimeMillis());
			if (numPassengers > 0)
				numPassengers -= r.nextInt(numPassengers);
			if (isSolo)
				numPassengers += r.nextInt(maxCapacityPassengers);
			else
				{ /* numPassengers += r.nextInt(XXX throughput XXX); */ }
			if (numPassengers > maxCapacityPassengers)
				numPassengers = maxCapacityPassengers;
		}
		
		// Crew
/*
System.out.println("XXX - XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
System.out.println("XXX - "+positionBlock.isYard+"\tpositionBlock.isYard");
System.out.println("XXX - "+issetDoorsOpen+"\tissetDoorsOpen");
System.out.println("XXX - "+engineer.getGoOnBreak()+"\tengineer.getGoOnBreak()");
System.out.println("XXX - "+(routeIndex > 0)+"\trouteIndex > 0");
System.out.println("XXX - "+(routeIndex < route.size() - 1)+"\trouteIndex < route.size() - 1");
System.out.println("XXX - "+(engineer.timeOnBreakStarts >= time)+"\tengineer.timeOnBreakStarts >= time");
System.out.println("XXX - XXXXXXXXXXXXXXX");
System.out.println("XXX - "+positionBlock.isYard+"\tpositionBlock.isYard");
System.out.println("XXX - "+engineer.getOnBreak()+"\tengineer.getOnBreak()");
System.out.println("XXX - "+(routeIndex > 0)+"\trouteIndex > 0");
System.out.println("XXX - "+(routeIndex < route.size() - 1)+"\trouteIndex < route.size() - 1");
System.out.println("XXX - XXXXXXXXXXXXXXX");
System.out.println("XXX - "+positionBlock.isYard+"\tpositionBlock.isYard");
System.out.println("XXX - "+issetDoorsOpen+"\tissetDoorsOpen");
System.out.println("XXX - "+(routeIndex == 0)+"\trouteIndex == 0");
System.out.println("XXX - "+(time - period < dispatchTime)+"\ttime - period < dispatchTime");
System.out.println("XXX - "+(time >= dispatchTime)+"\ttime >= dispatchTime");
System.out.println("XXX - "+(time - period < 0)+"\ttime - period < 0");
System.out.println("XXX - "+(24*60*60 + time - period < dispatchTime)+"\t24*60*60 + time - period < dispatchTime");
System.out.println("XXX - XXXXXXXXXXXXXXX");
System.out.println("XXX - "+positionBlock.isYard+"\tpositionBlock.isYard");
System.out.println("XXX - "+issetDoorsOpen+"\tissetDoorsOpen");
System.out.println("XXX - "+(routeIndex == route.size() - 1)+"\trouteIndex == route.size() - 1");
System.out.println("XXX - XXXXXXXXXXXXXXX");
System.out.println("XXX - "+time+"\ttime");
System.out.println("XXX - "+period+"\tperiod");
System.out.println("XXX - "+dispatchTime+"\tdispatchTime");
*/
		if (positionBlock.isYard  &&  issetDoorsOpen  &&  engineer.getGoOnBreak()  &&  routeIndex > 0  &&  routeIndex < route.size() - 1  &&  engineer.timeOnBreakStarts >= time  &&  numCrew > 0)
		{
			// Break Starts
			numCrew -= 1;
			engineer.goOnBreak = false;
			engineer.onBreak = true;
		}
		else if (positionBlock.isYard  &&  engineer.getOnBreak()  &&  routeIndex > 0  &&  routeIndex < route.size() - 1)
		{
			// On Break
			engineer.timeOnBreak += period;
			
			if (issetDoorsOpen  &&  engineer.timeOnBreak >= 30*60  &&  numCrew <= 0)
			{
				// Break Ends
				numCrew += 1;
				engineer.onBreak = false;
			}
		}
		else if (positionBlock.isYard  &&  issetDoorsOpen  &&  routeIndex == 0  &&  ((time - period < dispatchTime  &&  time >= dispatchTime)  ||  (time - period < 0  &&  24*60*60 + time - period < dispatchTime))  &&  numCrew <= 0)
		{
			// Shift Starts
			numCrew += 1;
		}
		else if (positionBlock.isYard  &&  issetDoorsOpen  &&  routeIndex == route.size() - 1  &&  numCrew > 0)
		{
			// Shift Ends
			numCrew -= 1;
			engineer.goOnBreak = true;
			engineer.onBreak = false;
			engineer.timeOnBreak = 0.0;
			routeIndex = 0;
		}
		
		// Mass
		totalMass = emptyTrainMass + personMass * (numPassengers + numCrew);
	}
	
	private double round(double x, int places)
	{
		if (places == 1)
			return ((double)((int)(x*10)))/10.0;
		else if (places == 2)
			return ((double)((int)(x*100)))/100.0;
		else if (places == 3)
			return ((double)((int)(x*1000)))/1000.0;
		else
			return 0.0;
	}
}