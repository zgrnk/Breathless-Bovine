package SSC;

import java.text.*;
import java.util.*;


public class Scheduler{

	public String line;
	public String startTime;
	public Date startTimeDate;
	public int throughput;
	public ArrayList<String> track;
	public ArrayList<String> throughputSch = new ArrayList<String>();
	public ArrayList<String> routesSch = new ArrayList<String>();
	public ArrayList<String> trainSch = new ArrayList<String>();
	public ArrayList<String> engineerSch = new ArrayList<String>();
	
	public ArrayList<ArrayList<String>> trainsNtimes = new ArrayList<ArrayList<String>>();


	public Scheduler(){}


	public void generateSchedules(){

		/*incrementing time example
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");		
		Calendar test = Calendar.getInstance();
		newTime = startTime;
		try {
			test.setTime(sdf.parse(this.newTime));
			test.add(Calendar.HOUR, 1);
			newTime = sdf.format(test.getTime());
		} catch (ParseException e) {}
		 */
		
		String newTime = startTime;
		for (int i=0; i<this.throughput; i++){
			
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");		
			Calendar test = Calendar.getInstance();
			
			try {
				test.setTime(sdf.parse(newTime));
				test.add(Calendar.MINUTE, 30);
				newTime = sdf.format(test.getTime());
			} catch (ParseException e) {}
			
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(Character.toString(this.line.charAt(0)).toUpperCase() + i);
			temp.add(newTime);
			trainsNtimes.add(temp);
		}


		//
		//THROUGHPUT
		//
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");		
		Calendar test = Calendar.getInstance();
		newTime = startTime.substring(0, 2) + ":00";

		if(this.line == "green"){
			throughputSch.add("HOUR\tPIONEER\tEDGEBROOK\tWHITED\tS.BANK\tCENTRAL\tINGLEWOOD\tOVERBROOK\tDORMONT\tMTLEBANON\tPOPLAR\tC.SHANNON\tDORMONT\tGLENBURY\tOVERBROOK\tINGLEWOOD\tCENTRAL\t");
			for(int i=0; i<24; i++){		
				throughputSch.add(newTime + "\t" + getRandomTP() + "\t\t" 
						+ getRandomTP() + "\t\t" + getRandomTP() + "\t\t" 
						+ getRandomTP() + "\t\t" + getRandomTP() + "\t\t" 
						+ getRandomTP() + "\t\t" + getRandomTP() + "\t\t" 
						+ getRandomTP() + "\t\t" + getRandomTP() + "\t\t"
						+ getRandomTP() + "\t\t" + getRandomTP() + "\t\t"
						+ getRandomTP() + "\t\t" + getRandomTP() + "\t\t"
						+ getRandomTP() + "\t\t" + getRandomTP() + "\t\t"
						+ getRandomTP());		
				try {
					test.setTime(sdf.parse(newTime));
					test.add(Calendar.HOUR, 1);
					newTime = sdf.format(test.getTime());
				} catch (ParseException e) {}
			}
		}
		else if(this.line == "red"){
			throughputSch.add("HOUR\tSHADY\tHERRON\tSWISS\tPENN\tSTEEL\tFIRST\tSQUARE\tS.HILLS");
			for(int i=0; i<24; i++){		
				throughputSch.add(newTime + "\t" + getRandomTP() + "\t\t" 
						+ getRandomTP() + "\t\t" + getRandomTP() + "\t\t" 
						+ getRandomTP() + "\t\t" + getRandomTP() + "\t\t" 
						+ getRandomTP() + "\t\t" + getRandomTP() + "\t\t" 
						+ getRandomTP());			
				try {
					test.setTime(sdf.parse(newTime));
					test.add(Calendar.HOUR, 1);
					newTime = sdf.format(test.getTime());
				} catch (ParseException e) {}
			}
		}
		//
		//
		//






		//
		//ROUTES
		//
		routesSch.add("TRAIN\tLINE\tDISPATCH\tROUTE");
		for(int i=0; i<trainsNtimes.size(); i++){
			routesSch.add(trainsNtimes.get(i).get(0) + "\t\t" + this.line + "\t" + trainsNtimes.get(i).get(1) + "\t\t" + "[list of blocks]");
		}
		//
		//
		//




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




		//
		//ENGINEER SCH
		//
		engineerSch.add("NAME\tTRAIN\tSTART\tEND\t\tSTART\tEND");
		for(int i=0; i<this.throughput; i++){

			int r = 99 + (int)(Math.random()*900); 

			engineerSch.add("E"+ r +"\t\t" + routesSch.get(i+1).subSequence(0, 2)+ "\t\t" + "00:00\t00:00\t00:00\t00:00");
		}
		//
		//
		//

	}

	private int getRandomTP(){
		int min=0, max=0;

		if(this.throughput == 0){
			return 0;
		}
		else if(this.throughput == 1){
			min=0;
			max=20;
		}
		else if(this.throughput == 2){
			min=10;
			max=30;
		}
		else if(this.throughput == 3){
			min=20;
			max=50;
		}


		int r = min + (int)(Math.random()*max); 

		return r;
	}
}