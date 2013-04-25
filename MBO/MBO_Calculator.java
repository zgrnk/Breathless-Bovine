package MBO;

import java.util.ArrayList;

import TNM.*;
import TKM.*;




public class MBO_Calculator{

	//MBO_Test mbo_Test;
	private Train train;
	private double distanceToNext=0;
	private double distanceToPrev=0;
	private ArrayList<Train> trainList;
	

	public MBO_Calculator(Train train, ArrayList<Train> trainList){
		this.train = train;
		this.trainList = trainList;
	}


	public double getDistToNextTrain(){
		
		this.distanceToPrev = 0;
		
		Block testBlock = this.train.gps.block;
		this.distanceToNext = testBlock.length - this.train.gps.metersIntoBlock;
		testBlock = testBlock.getNext(false); //!this.train.gps.travelDirection ??
		
		while(testBlock.occupied == false){
			this.distanceToNext += testBlock.length;
			testBlock = testBlock.getNext(false);
			
			
			//GETTING STUCK IN HERE
			//if test block is a switch, get next block that is default direction...?
		}
		
		Train nextTrain = this.train;
		for(Train t : trainList){
			if(t.gps.block.equals(testBlock)){
				nextTrain = t;
			}
		}
		
		if(!nextTrain.equals(this.train)){
			return this.distanceToNext + nextTrain.gps.metersIntoBlock - nextTrain.length;
		}
		else{
			return this.distanceToNext - this.train.length; //may not be correct
		}
	}

	
	public double getDistToPrevTrain(){
		
		this.distanceToPrev = 0;
		
		Block testBlock = this.train.gps.block;
		this.distanceToPrev = this.train.gps.metersIntoBlock - this.train.length;
		testBlock = testBlock.getNext(true); //!this.train.gps.travelDirection ??
		
		while(testBlock.occupied == false){
			this.distanceToPrev += testBlock.length;
			testBlock = testBlock.getNext(true);
			
			
			//GETTING STUCK IN HERE
			//if test block is a switch, get prev block that is not default direction...?
		}
		
		Train prevTrain = this.train;
		for(Train t : trainList){
			if(t.gps.block.equals(testBlock)){
				prevTrain = t;
			}
		}
		
		if(!prevTrain.equals(this.train)){
			return this.distanceToPrev + testBlock.length - prevTrain.gps.metersIntoBlock;
		}
		else{
			return this.distanceToPrev + testBlock.length - this.train.gps.metersIntoBlock; //may not be correct

		}
	}

	
	public double getDistToNextStation(){
		
		double distance = 0;
		
		Block testBlock = this.train.gps.block;
	
		while(testBlock.isStation == false){
			distance += testBlock.length;
			testBlock = testBlock.getNext(false);
			
			
			//GETTING STUCK IN HERE
			//if test block is a switch, get next block that is default direction...?
		}
		
		return distance = distance + this.train.gps.metersIntoBlock;
	}
	
	
	public double calculateAuthority(){
		
		double brakingDistance = -((this.train.gps.speed * 1000 / 3600) * (this.train.gps.speed * 1000 / 3600)) / (2*(-1.2));
		
		double authority = this.distanceToNext - brakingDistance - 20;  //buffer?
		
		return authority;
	}

	
	public double calculateSetspeed() {
		//speed limit unless close to train (and must use ebreak) (authority can't be 0) or close to station
		return this.train.gps.block.speedLimit; //for now
	}
}
