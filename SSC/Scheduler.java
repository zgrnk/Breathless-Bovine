package SSC;

import java.text.*;
import java.util.*;


public class Scheduler{

	//public String line;
	public String startTime, tempTime;
	public Date startTimeDate;
	//public int throughput;
	public int greenThroughput;
	public int redThroughput;
	//public ArrayList<String> track;
	//public ArrayList<String> throughputSch = new ArrayList<String>();
	public ArrayList<String> greenThroughputSch = new ArrayList<String>();
	public ArrayList<String> redThroughputSch = new ArrayList<String>();
	public ArrayList<String> routesSch = new ArrayList<String>();
	//public ArrayList<String> trainSch = new ArrayList<String>();
	public ArrayList<String> greenTrainSch = new ArrayList<String>();
	public ArrayList<String> redTrainSch = new ArrayList<String>();
	public ArrayList<String> engineerSch = new ArrayList<String>();
	//public ArrayList<ArrayList<String>> trainsNtimes = new ArrayList<ArrayList<String>>();
	public ArrayList<String> greenTrack;
	public ArrayList<String> redTrack;


	public Scheduler(){}


	public void generateSchedules(){

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");		
		Calendar test = Calendar.getInstance();
		

		//
		//THROUGHPUT
		//

		//GREEN THROUGHPUT
		tempTime = startTime.substring(0, 2) + ":00";
		greenThroughputSch.add("HOUR\tPIONEER\tEDGEBROOK\tWHITED\tS.BANK\tCENTRAL\tINGLEWOOD\tOVERBROOK\tDORMONT\tMTLEBANON\tPOPLAR\tC.SHANNON\tDORMONT\tGLENBURY\tOVERBROOK\tINGLEWOOD\tCENTRAL\t");
		for(int i=0; i<24; i++){		
			greenThroughputSch.add(tempTime + "\t" + getRandomTP(greenThroughput) + "\t\t" 
					+ getRandomTP(greenThroughput) + "\t\t" + getRandomTP(greenThroughput) + "\t\t" 
					+ getRandomTP(greenThroughput) + "\t\t" + getRandomTP(greenThroughput) + "\t\t" 
					+ getRandomTP(greenThroughput) + "\t\t" + getRandomTP(greenThroughput) + "\t\t" 
					+ getRandomTP(greenThroughput) + "\t\t" + getRandomTP(greenThroughput) + "\t\t"
					+ getRandomTP(greenThroughput) + "\t\t" + getRandomTP(greenThroughput) + "\t\t"
					+ getRandomTP(greenThroughput) + "\t\t" + getRandomTP(greenThroughput) + "\t\t"
					+ getRandomTP(greenThroughput) + "\t\t" + getRandomTP(greenThroughput) + "\t\t"
					+ getRandomTP(greenThroughput));		
			try {
				test.setTime(sdf.parse(tempTime));
				test.add(Calendar.HOUR, 1);
				tempTime = sdf.format(test.getTime());
			} catch (ParseException e) {}
		}

		//RED THROUGHPUT
		tempTime = startTime.substring(0, 2) + ":00";
		redThroughputSch.add("HOUR\tSHADY\tHERRON\tSWISS\tPENN\tSTEEL\tFIRST\tSQUARE\tS.HILLS");
		for(int i=0; i<24; i++){		
			redThroughputSch.add(tempTime + "\t" + getRandomTP(redThroughput) + "\t\t" 
					+ getRandomTP(redThroughput) + "\t\t" + getRandomTP(redThroughput) + "\t\t" 
					+ getRandomTP(redThroughput) + "\t\t" + getRandomTP(redThroughput) + "\t\t" 
					+ getRandomTP(redThroughput) + "\t\t" + getRandomTP(redThroughput) + "\t\t" 
					+ getRandomTP(redThroughput));			
			try {
				test.setTime(sdf.parse(tempTime));
				test.add(Calendar.HOUR, 1);
				tempTime = sdf.format(test.getTime());
			} catch (ParseException e) {}
		}



		//
		//ROUTES
		//
		routesSch.add("TRAIN\tLINE\tDISPATCH\tROUTE");
		
		//GREEN ROUTE
		tempTime = startTime;
		for(int i=0; i<greenThroughput; i++){
			
			routesSch.add("T" + i + "\t\t" + "Green" + "\t" + tempTime + "\t\t" + "[list of blocks]");
			System.out.println(tempTime);
			try {
				test.setTime(sdf.parse(tempTime));
				test.add(Calendar.MINUTE, 15);
				tempTime = sdf.format(test.getTime());
			} catch (ParseException e) {}
		}
		
		//RED ROUTE
		tempTime = startTime;
		int j = 0;
		for(int i=greenThroughput; i<greenThroughput+redThroughput; i++){
			
			routesSch.add("T" + i + "\t\t" + "Red" + "\t" + tempTime + "\t\t" + "[list of blocks]");
			
			try {
				test.setTime(sdf.parse(tempTime));
				test.add(Calendar.MINUTE, 15);
				tempTime = sdf.format(test.getTime());
			} catch (ParseException e) {}
		}


		/*
		
		
		//
		//SCHEDULE
		//
		if(this.line == "green"){
			trainSch.add("TRAIN\tPIONEER\tEDGEBROOK\tWHITED\tS.BANK\tCENTRAL\tINGLEWOOD\tOVERBROOK\tDORMONT\tMTLEBANON\tPOPLAR\tC.SHANNON\tDORMONT\tGLENBURY\tOVERBROOK\tINGLEWOOD\tCENTRAL\t");
		}
		else if(this.line == "red"){
			trainSch.add("TRAIN\tSHADY\tHERRON\tSWISS\tPENN\tSTEEL\tFIRST\tSQUARE\tS.HILLS");
		}
		//
		//
		//



		*/
		
		//
		//ENGINEER SCH
		//
		engineerSch.add("NAME\tTRAIN\tSTART\tEND\t\tSTART\tEND");
		
		//GREEN ENGINEERS
		for(int i=0; i<greenThroughput; i++){
			
			try{
				test.setTime(sdf.parse(startTime));
			} catch (ParseException e) {}
			test.add(Calendar.MINUTE, 15*i);
			String start1 = sdf.format(test.getTime());
			test.add(Calendar.HOUR, 4);
			String end1 = sdf.format(test.getTime());
			test.add(Calendar.MINUTE, 30);
			String start2 = sdf.format(test.getTime());
			test.add(Calendar.HOUR, 4);
			String end2 = sdf.format(test.getTime());
			
			engineerSch.add("E"+ i +"\t\t" + "T" + i + "\t\t" + start1 + "\t" +end1 + "\t" + start2 + "\t" + end2);
		}
		
		//RED ENGINEERS
		for(int i=greenThroughput; i<greenThroughput+redThroughput; i++){

			try{
				test.setTime(sdf.parse(startTime));
			} catch (ParseException e) {}
			test.add(Calendar.MINUTE, 15*(i-greenThroughput));
			String start1 = sdf.format(test.getTime());
			test.add(Calendar.HOUR, 4);
			String end1 = sdf.format(test.getTime());
			test.add(Calendar.MINUTE, 30);
			String start2 = sdf.format(test.getTime());
			test.add(Calendar.HOUR, 4);
			String end2 = sdf.format(test.getTime());
			
			engineerSch.add("E"+ i +"\t\t" + "T" + i + "\t\t" + start1 + "\t" +end1 + "\t" + start2 + "\t" + end2);
		}


	}

	private int getRandomTP(int throughput){
		int min=0, max=0;

		if(throughput == 0){
			return 0;
		}
		else if(throughput == 1){
			min=0;
			max=20;
		}
		else if(throughput == 2){
			min=10;
			max=30;
		}
		else if(throughput == 3){
			min=20;
			max=50;
		}


		int r = min + (int)(Math.random()*max); 

		return r;
	}
}