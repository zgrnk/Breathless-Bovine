


import java.util.*;
import java.awt.event.*;
import javax.swing.Timer;


public class MBO_Test{

	
	public static ArrayList<Train> trainList = new ArrayList<Train>();
	public static Hashtable<String,Train> trainTable = new Hashtable<String,Train>();

	public static ArrayList<Block> greenTrackLayout = new ArrayList<Block>();
	public static ArrayList<Block> redTrackLayout = new ArrayList<Block>();
		
	public static Train testTrain, testTrain2, testTrain3;


	public static void main(String[] args){
		
		//System.out.println(System.getProperty("java.runtime.version"));

		TrackLayout tl = new TrackLayout();
		tl.parseTrackDB("track_db.csv");
		tl.constructTrack();
		greenTrackLayout = tl.getBlocks();
		
		
		for(int i=1; i<=76; i++){
			redTrackLayout.add(new Block(i));
		}
		
		
		/*
		greenTrackLayout.get(0).prev = greenTrackLayout.get(greenTrackLayout.size()-1);
		greenTrackLayout.get(0).next = greenTrackLayout.get(1);
		for(int i=1; i<greenTrackLayout.size()-1; i++){
			greenTrackLayout.get(i).next = greenTrackLayout.get(i+1);
			greenTrackLayout.get(i).prev = greenTrackLayout.get(i-1);
		}
		greenTrackLayout.get(greenTrackLayout.size()-1).next = greenTrackLayout.get(0);
		greenTrackLayout.get(greenTrackLayout.size()-1).prev = greenTrackLayout.get(greenTrackLayout.size()-2);

		
		redTrackLayout.get(0).prev = redTrackLayout.get(redTrackLayout.size()-1);
		redTrackLayout.get(0).next = redTrackLayout.get(1);
		for(int i=1; i<redTrackLayout.size()-1; i++){
			redTrackLayout.get(i).next = redTrackLayout.get(i+1);
			redTrackLayout.get(i).prev = redTrackLayout.get(i-1);

		}
		redTrackLayout.get(redTrackLayout.size()-1).next = redTrackLayout.get(0);
		redTrackLayout.get(redTrackLayout.size()-1).prev = redTrackLayout.get(redTrackLayout.size()-2);
		*/


		
		testTrain = new Train(1, "G1", "Green", 0, greenTrackLayout, null, greenTrackLayout.get(0));
		testTrain2 = new Train(2, "G2", "Green", 0, greenTrackLayout, null, greenTrackLayout.get(70));
		testTrain3 = new Train(3, "R1", "Red", 0, redTrackLayout, null, redTrackLayout.get(0));
		trainList.add(testTrain);
		trainList.add(testTrain2);
		trainList.add(testTrain3);


		for (Train t : trainList){
			trainTable.put(t.stringId, t);
		}

		
		new MBO_GUI();	

		
		Timer speedSim = new Timer(10, new ActionListener() {
		    public void actionPerformed(ActionEvent et) { 	
		    	for(Train t : trainList){
		    		double r = (int) 30 + (Math.random()*10);
		    		t.gps.speed = r;
		    		
		    		//if(t.equals(testTrain)) t.gps.speed += 15; ////////////////////////testing

		    		
		    		double distance = t.gps.speed*0.01*60*60/1000;
		    		t.gps.metersIntoBlock += distance;
		    		
		    		if(t.gps.metersIntoBlock >= t.gps.block.length){		///////////////ERROR HERE
		    			//t.gps.metersIntoBlock = 0;
		    			t.gps.block = t.gps.block.getNext(false);
		    		}
		    		
		    		//t.gps.block.trainOnBlock = t;
		    		//t.gps.block.getNext(true).trainOnBlock = null;
		    	}
		 
		    }
		    
		});
		speedSim.start();	
		
	}

}
