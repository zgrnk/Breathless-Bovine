package MBO;




public class MBO_Calculator{

	MBO_Test mbo_Test;
	Train train;
	double distanceToNext=0;
	double distanceToPrev=0;

	public MBO_Calculator(Train train){
		this.train = train;
	}


	public double getNextTrainFrontDist(){
		
		this.distanceToPrev = 0;
		
		Block testBlock = this.train.gps.block;
		this.distanceToNext += testBlock.length;
		testBlock = testBlock.getNext(false);
		
		while(testBlock.occupied == false){
			this.distanceToNext += testBlock.length;
			testBlock = testBlock.getNext(false);
		}
		
		Train nextTrain = testBlock.trainOnBlock;

		return this.distanceToNext - this.train.gps.metersIntoBlock + nextTrain.gps.metersIntoBlock - nextTrain.length;
	}

	
	public double getNextTrainBehindDist(){
		
		this.distanceToPrev = 0;
		
		Block testBlock = this.train.gps.block.getNext(true);
		while(testBlock.occupied == false){
			this.distanceToPrev += testBlock.length;
			testBlock = testBlock.getNext(true);
		}
		
		Train nextTrain = testBlock.trainOnBlock;

		return this.distanceToPrev - this.train.length + this.train.gps.metersIntoBlock + testBlock.length - nextTrain.gps.metersIntoBlock;
	}

	
	public double getNextStationDist(){
		
		/*
		double distance = 0;
		
		Block testBlock = this.train.gps.block;
	
		while(testBlock.stationOnBlock == null){
			distance += testBlock.length;
			testBlock = testBlock.next;
		}
		
		Station nextStation = testBlock.stationOnBlock;

		return distance = distance + nextStation.metersIntoBlock - this.train.gps.metersIntoBlock;
		*/
		return 0;
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
