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
	private String newTime;


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



		
		//
		//THROUGHPUT
		//
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");		
		Calendar test = Calendar.getInstance();
		newTime = startTime.substring(0, 2) + ":00";

		throughputSch.add("HOUR\tSHADY\tHERRON\tSWISS\tPENN\tSTEEL\tFIRST\tSQUARE\tS.HILLS");
		for(int i=0; i<24; i++){		
			throughputSch.add(newTime + "\t" + (int)(Math.random()*30) + "\t\t" 
					+ (int)(Math.random()*30) + "\t\t" + (int)(Math.random()*30) + "\t\t" 
					+ (int)(Math.random()*30) + "\t\t" + (int)(Math.random()*30) + "\t\t" 
					+ (int)(Math.random()*30) + "\t\t" + (int)(Math.random()*30) + "\t\t" 
					+ (int)(Math.random()*30));			
			try {
				test.setTime(sdf.parse(this.newTime));
				test.add(Calendar.HOUR, 1);
				newTime = sdf.format(test.getTime());
			} catch (ParseException e) {}
		}
		//
		//
		//
		

		

		//
		//ROUTES
		//
		routesSch.add("TRAIN\tLINE\tDISPATCH\tROUTE");
		for(int i=0; i<this.throughput; i++){
			routesSch.add("G"+i +"\t\t" + this.line + "\t" + this.startTime + "\t\t" + "test,test,test");
		}
		//
		//
		//


		

		//
		//SCHEDULE
		//
		trainSch.add("TRAIN\tYARD\tSHADY\tHERRON\tSWISS\tPENN\tSTEEL\tFIRST\tSQUARE\tS.HILLS");
		for(int i=0; i<this.throughput; i++){
			trainSch.add("G"+i +"\t\t" + "00:00\t00:00\t00:00\t00:00\t00:00\t00:00\t00:00\t00:00\t00:00");
		}
		//
		//
		//




		//
		//ENGINEER SCH
		//
		engineerSch.add("NAME\tTRAIN\tSTART\tEND\t\tSTART\tEND");
		for(int i=0; i<this.throughput; i++){
			engineerSch.add("E"+i +"\t\t" + "G"+i + "\t\t" + "00:00\t00:00\t00:00\t00:00");
		}
		//
		//
		//

	}
}