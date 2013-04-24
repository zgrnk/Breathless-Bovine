/**
 * @class Train
 * 
 * @version 1.0
 * 
 * @date 04/25/2013
 * 
 * @author Chris Paskie
 */


package TNM;

import TKM.*;
import java.util.*;
import java.io.*;

/**
 * This file contains the main class which represents a single train.
 * It is responsible for performing all of the physics calculations of each 
 * train as they move along the track.
 */
public class Train {
	
	public static final double GRAVITY_CONSTANT = 9.80665;
	
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
	public Engineer engineer;
	public boolean justVisitedStation;
	
	
	/**
	 * Constructor of the train.  Each train will have its own train controller.
	 */
	public Train(int id, String stringId, String line, double dispatchTime, ArrayList<Block> route, 
			Engineer engineer, Block positionBlock) {
		
		tnc = new TrainController();
		
		/*
		 * Initialize the following static values as defined in the given Excel file 
		 * containing train data, the track layout, etc.
		 */
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
		curTemperature = 22.0;
		targetTemperatureTNC = 22.0;
		targetTemperatureManual = 22.0;
		issetTargetTemperatureManual = false;
		announcement = "";
		
		// Initialize the following other values.
		gps = new GPS(positionBlock, (int) positionMeters, curVelocity, (positionDirection == Block.DIRECTION_FWD));
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
		this.engineer = engineer;
		justVisitedStation = false;
	}
	
	/**
	 * Get the grade of the current block based on the direction in which the train is travelling.
	 */
	public double getRelativeGrade() {
		if ((positionBlock.grade == 0.0) || (positionDirection == Block.DIRECTION_FWD)) {
			return positionBlock.grade;
		} else {
			return (-1.0) * positionBlock.grade;
		}
	}
	
	/**
	 * Performs calculations and updates counts relating to the train.
	 */
	public void timeTick(double time, double period, boolean isSolo, boolean isSelectedByTNC) {
		
		// TrainController timeTick and Response
		ResponseTNC tncResponse = new ResponseTNC(0.0, false, false, false, false, 0.0, "");
		if (!isSolo) {
			tncResponse = tnc.timeTick(time, curVelocity, period, positionBlock, positionBlockTail, 
										issetSignalPickupFailure, issetEngineFailure, issetBrakeFailure, 
										fixedSuggestedSpeed, mboSuggestedSpeed, issetEmerBrake, (numCrew > 0), 
										positionBlock.readTransponder(positionDirection), isSelectedByTNC);
		}
		
		if ((!issetDoorsOpen) && (numCrew > 0)) {
			// The train can only move while the doors are closed and there is a crew member onboard.
			
			// Slope Direction
			boolean uphill = false;
			boolean downhill = false;
			boolean flat = false;
			if (((positionBlock.grade > 0.0) && (positionDirection == Block.DIRECTION_FWD)) 
					|| ((positionBlock.grade < 0.0) && (positionDirection != Block.DIRECTION_FWD))) {
				uphill = true;
			} else if (((positionBlock.grade > 0.0) && (positionDirection != Block.DIRECTION_FWD)) 
					|| ((positionBlock.grade < 0.0) && (positionDirection == Block.DIRECTION_FWD))) {
				downhill = true;
			} else {
				flat = true;
			}
			
			// Angle of Inclination (used for the later Gravity calculations)
			double angle = 0.0;
			if (positionBlock.grade > 0.0) {
				angle = Math.atan(positionBlock.grade / 100.0);
			} else if (positionBlock.grade < 0.0) {
				angle = Math.atan(((-1.0) * positionBlock.grade) / 100.0);
			}
			
			// Power - Command(s)
			if (!isSolo) {
				receivedPower = tncResponse.powerCommand;
			}
			if (manualPower > motorPower) {
				manualPower = motorPower;		// Limit the manual power if necessary.
			}
			if (receivedPower > motorPower) {
				receivedPower = motorPower;		// Limit the received power if necessary.
			}
			
			// Brakes - Commands
			if (!isSolo){
				issetServiceBrake = tncResponse.issetServiceBrake;
				issetEmerBrake = tncResponse.issetEmerBrake;
			}
			
			// Engine - Acceleration & Velocity
			double accelEngine;
			if (issetEngineFailure) {
				// Engine is incapable of accelerating while it is failing.
				accelEngine = 0.0;
			} else {
				if ((!isSolo) && (!issetManualPower)) {
					if (curVelocity <= 0.0) {
						// Special case when train is currently stopped (or rolling backwards).
						accelEngine = receivedPower / (0.0001 * totalMass);
					} else {
						accelEngine = receivedPower / (curVelocity * totalMass);
					}
				} else {
					if (curVelocity <= 0.0) {
						// Special case when train is currently stopped (or rolling backwards).
						accelEngine = manualPower / (0.0001 * totalMass);
					} else {
						accelEngine = manualPower / (curVelocity * totalMass);
					}
				}
			}
			
			if (accelEngine > (motorPower /  (0.0001 * totalMass))) {
				// Limit the engine acceleration if necessary.
				accelEngine = motorPower / (0.0001 * totalMass);
			}
			double velEngine = accelEngine * period;
			if (velEngine > maxSpeed) {
				// Limit the engine velocity if necessary.
				velEngine = maxSpeed;
			}
			
			// Brakes - Acceleration & Velocity
			double accelBrakes = 0.0;
			if ((!issetBrakeFailure) && (issetEmerBrake || issetServiceBrake)) {
				// Make it so the engine is not factored in while a brake is applied.
				accelEngine = 0.0;
				velEngine = 0.0;
				
				if (issetEmerBrake) {
					accelBrakes = emerBrakeDecel;
				} else if (issetServiceBrake) {
					accelBrakes = serviceBrakeDecel;
				}
				
				// Make it so the brake is applied relative to the current direction of travel.
				if (curVelocity > 0.0) {
					accelBrakes *= (-1.0);
				} else if (curVelocity == 0.0) {
					accelBrakes = 0.0;
				}
			}
			double velBrakes = accelBrakes * period;
			
			// Gravity - Acceleration & Velocity
			double accelGravity = 0.0;
			if (uphill) {
				accelGravity = (-1.0) * GRAVITY_CONSTANT * Math.sin(angle);
			} else if (downhill) {
				accelGravity = GRAVITY_CONSTANT * Math.sin(angle);
			} else if (flat) {
				accelGravity = 0.0;
			}
			double velGravity = accelGravity * period;
			
			// Current - Acceleration & Velocity (w/o Brakes & Friction)
			curAccel = accelEngine + accelGravity;
			double newVelocity = curVelocity + velEngine + velGravity;
			
			// Friction - Acceleration & Velocity
			double accelFriction;
			accelFriction = frictionCoeff * GRAVITY_CONSTANT * Math.cos(angle);
			/*
			 * Make it so the friction is applied relative to the *new* direction of travel
			 * (not including the friction itself or the effect of the brakes).
			 */
			if (newVelocity > 0) {
				accelFriction *= (-1.0);
			} else if (newVelocity == 0.0) {
				accelFriction = 0.0;
			}
			double velFriction = accelFriction * period;
			
			// Current - Acceleration & Velocity (w/ Brakes & Friction)
			curAccel += (accelBrakes + accelFriction);
			curAccel = round(curAccel, 3);
			newVelocity += (velBrakes + velFriction);
			newVelocity = round(newVelocity, 3);
			
			// Handle special cases and limit to max speed depending on slope direction.
			if (((((!issetBrakeFailure) && ((issetEmerBrake) || (issetServiceBrake))) || flat) 
						&& (((curVelocity < 0.0) && (newVelocity > 0.0)) || ((curVelocity > 0.0) && (newVelocity < 0.0)))) 
					|| (((!issetBrakeFailure) && ((issetEmerBrake) || (issetServiceBrake))) 
						&& (curVelocity == 0.0))) {
				/*
				 * If the train's direction of travel has changed and (the brakes are being applied or 
				 * the slope is flat), then the train should come to a complete stop and stay stopped.
				 */
				curVelocity = 0.0;
				curAccel = 0.0;
			} else if ((uphill) && (newVelocity > maxSpeed + velFriction + velGravity)) {
				/*
				 * If the train is traveling uphill, then the final max speed of the train will actually 
				 * have friction and gravity factored in.
				 */
				curVelocity = maxSpeed + velFriction + velGravity;
			} else if ((downhill) && (curVelocity < maxSpeed + velFriction) && (newVelocity > maxSpeed + velFriction) 
					&& (velEngine > 0.0)) {
				/*	
				 * If the train is traveling downhill, then there is no maximum speed of the train, 
				 * but the speed from the engine must still be limited (and have friction factored 
				 * in as seen in the above if statement).
				 */
				double velEngineDifference = velEngine - (maxSpeed - curVelocity);
				if (velEngineDifference > 0.0) {
					/*
					 * If the engine speed is responsible for causing the train to exceed the max speed, 
					 * then the difference must be taken away.
					 */
					curVelocity = newVelocity - velEngineDifference;
				} else {
					/*
					 * Else gravity was responsible for causing the train to exceed the max speed.
					 */
					curVelocity = newVelocity;
				}
			} else if ((flat) && (newVelocity > maxSpeed + velFriction)) {
				/*
				 * If the train is travelling on a flat block, then the final max speed of the train will 
				 * actually have friction factored in.
				 */
				curVelocity = maxSpeed + velFriction;
			} else {
				curVelocity = newVelocity;
			}
			curVelocity = round(curVelocity, 3);
			// Actually update the position of the train on the track.
			Block.advanceTrain(this, curVelocity * period);
			gps = new GPS(positionBlock, (int) (positionMeters + 0.5), curVelocity, (positionDirection == Block.DIRECTION_FWD));
			if (!isSolo) {
				postedSpeedLimit = positionBlock.speedLimit;
				fixedSuggestedAuthority = positionBlock.getFBAuthority();
				fixedSuggestedSpeed = positionBlock.getFBSpeed();
				// mboSuggestedAuthority = XXX;
				// mboSuggestedSpeed = XXX;
			}
		} else {
			curVelocity = 0.0;
			curAccel = 0.0;
		}
		
		// Lights
		if ((issetLightsOnUseManual) || (isSolo)) {
			issetLightsOn = issetLightsOnManual;
		} else {
			issetLightsOn = tncResponse.issetLightsOn;
		}
		
		// Doors
		if ((issetDoorsOpenUseManual) || (isSolo)) {
			issetDoorsOpen = issetDoorsOpenManual;
		} else {
			issetDoorsOpen = tncResponse.issetDoorsOpen;
		}
		
		// Temperature
		if (!isSolo) {
			targetTemperatureTNC = tncResponse.targetTemperatureTNC;
		}
		/*
		 * Simple method for moving the current towards the target temperature 
		 * (either +/- 0.1 once each timeTick call until the target is reached).
		 */
		if ((issetTargetTemperatureManual) || (isSolo)) {
			if (curTemperature > targetTemperatureManual) {
				curTemperature -= 0.1;
			} else if (curTemperature < targetTemperatureManual) {
				curTemperature += 0.1;
			}
		} else {
			if (curTemperature > targetTemperatureTNC) {
				curTemperature -= 0.1;
			} else if (curTemperature < targetTemperatureTNC) {
				curTemperature += 0.1;
			}
		}
		curTemperature = round(curTemperature, 1);
		
		// Announcement
		if (!isSolo) {
			announcement = tncResponse.announcement;
		} else {
			if (positionBlock.isStation) {
				announcement = "Welcome to Station " + positionBlock.stationName + "!";
			} else if (positionBlock.isYard) {
				announcement = "Welcome to the Yard!";
			} else {
				announcement = "";
			}
		}
		
		// Passengers
		if (positionBlock.isStation) {
			if ((issetDoorsOpen) && (curVelocity == 0.0) && (!justVisitedStation)) {
				// Passengers can only enter/exit the train while the doors are open at a station.
				
				Random r = new Random(System.currentTimeMillis());
				
				// Some passengers first exit the train.
				if (numPassengers > 0) {
					numPassengers -= r.nextInt(numPassengers);
				}
				
				// Some passengers then board the train.
				if (isSolo) {
					numPassengers += r.nextInt(maxCapacityPassengers);
				} else {
					/* numPassengers += r.nextInt(XXX SSC_throughput XXX); */
					numPassengers += r.nextInt(maxCapacityPassengers);
				}
				
				// Limit the total number of passengers.
				if (numPassengers > maxCapacityPassengers) {
					numPassengers = maxCapacityPassengers;
				}
				
				justVisitedStation = true;
			}
		} else {
			justVisitedStation = false;
		}
		
		// Crew
		if (positionBlock.isYard) {
			if ((engineer.goOnBreak) && (routeIndex > 0) 
					&& (routeIndex < route.size() - 1) && (time >= engineer.timeOnBreakStarts) && (numCrew > 0)) {
				// Break Starts
				curVelocity = 0.0;
				curAccel = 0.0;
				issetDoorsOpen = true;
				issetDoorsOpenManual = true;
				numCrew -= 1;
				engineer.goOnBreak = false;
				engineer.onBreak = true;
			} else if ((engineer.onBreak) && (routeIndex > 0) && (routeIndex < route.size() - 1)) {
				// On Break
				curVelocity = 0.0;
				curAccel = 0.0;
				engineer.timeOnBreak += period;
				
				if ((issetDoorsOpen) && (numCrew <= 0)
						&& (((!isSolo) && (engineer.timeOnBreak >= 30 * 60)) || ((isSolo) && (engineer.timeOnBreak >= 60)))) {
					// Break Ends
					numCrew += 1;
					issetDoorsOpen = false;
					issetDoorsOpenManual = false;
					engineer.onBreak = false;
				}
			} else if ((issetDoorsOpen) && (routeIndex == 0) 
					&& (((time - period < dispatchTime) && (time >= dispatchTime)) 
							|| ((time - period < 0) && (24 * 60 * 60 + time - period < dispatchTime))) 
					&& (numCrew <= 0)) {
				// Shift Starts
				numCrew += 1;
				issetDoorsOpen = false;
				issetDoorsOpenManual = false;
			} else if ((routeIndex == route.size() - 1) && (numCrew > 0)) {
				// Shift Ends
				curVelocity = 0.0;
				curAccel = 0.0;
				issetDoorsOpen = true;
				issetDoorsOpenManual = true;
				numCrew -= 1;
				engineer.goOnBreak = true;
				engineer.onBreak = false;
				engineer.timeOnBreak = 0.0;
				routeIndex = 0;
			}
		}
		
		// Update the total mass based on the current crew and passengers counts.
		totalMass = emptyTrainMass + personMass * (numPassengers + numCrew);
	}
	
	/**
	 * Round values for better format when displayed and/or to avoid floating point round errors.
	 */
	private double round(double x, int places) {
		if (places == 1) {
			return ((double) ((int) (x * 10 + 0.5))) / 10.0;
		} else if (places == 2) {
			return ((double) ((int) (x * 100 + 0.5))) / 100.0;
		} else if (places == 3) {
			return ((double) ((int) (x * 1000 + 0.5))) / 1000.0;
		} else {
			return 0.0;
		}
	}
}