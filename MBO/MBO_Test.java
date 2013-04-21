package MBO;


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


		for(int i=0; i<26; i++){
			char blockID = 'A';
			blockID += i;
			greenTrackLayout.add(new Block(Character.toString(blockID)));
		}
		
		for(int i=0; i<20; i++){
			char blockID = 'A';
			blockID += i;
			redTrackLayout.add(new Block(Character.toString(blockID)));
		}
		
		
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



		testTrain = new Train("G1", "Green", true);
		testTrain2 = new Train("G2", "Green", true);
		testTrain3 = new Train("R1", "Red", false);
		trainList.add(testTrain);
		trainList.add(testTrain2);
		trainList.add(testTrain3);
		
		
		testTrain.gps.block = greenTrackLayout.get(0);
		testTrain.gps.block.trainOnBlock = testTrain;
		testTrain2.gps.block = greenTrackLayout.get(20);
		testTrain2.gps.block.trainOnBlock = testTrain2;
		testTrain3.gps.block = redTrackLayout.get(10);
		testTrain3.gps.block.trainOnBlock = testTrain3;

		

		for (Train t : trainList){
			trainTable.put(t.id, t);
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
		    		if(t.gps.metersIntoBlock >= t.gps.block.length){
		    			t.gps.metersIntoBlock = 0;
		    			t.gps.block = t.gps.block.next;
		    		}
		    		t.gps.block.trainOnBlock = t;
		    		t.gps.block.prev.trainOnBlock = null;
		    	}
		 
		    }
		    
		});
		speedSim.start();	
		
	}

}
