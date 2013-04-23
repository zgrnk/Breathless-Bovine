package CTCOffice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

import javax.swing.*;

import TNM.*;
import SSC.*;
import TKC.util.ControllerUI;
import TKC.util.TrackController;
import TKM.*;

public class CTCOffice {

	public boolean simRunning;
	private boolean init;
	private int lastTick;
	private int numTrains;
	private int cnt;
	private String currentTrainId;

	private TrackLayout track;

	// train shit
	TrainModelUI tnmUI;
	ArrayList<Train> trainList;
	String[] idArray;
	ArrayList<Block> redRoute;
	double current_clock_sec;
	Date currentTime; 
	Calendar targetTime; 
	Calendar tempTime;
	Block bYard;
	TKMGui tkmgui;
	//ControllerUI tkcgui;
	TrackController tkc;

	public CTCOffice(TrackLayout tl, TrainModelUI tnmui) {
		this.track = tl;
		this.tnmUI = tnmui;
		setup();
	}

	public Date getSimClk() {
		//Calendar rcal = new GregorianCalendar();
		//rcal.setTime(currentTime);
		//return rcal;
		return currentTime;
	}

	public void setTrackController(TrackController tkc) {
	  this.tkc = tkc;
	}
	
	public void resume() {
		simRunning = true;
		tnmUI.setIsPaused(false);
	}

	public void pause() {
		simRunning = false;
		tnmUI.setIsPaused(true);
	}
	
	public void setup() {

		simRunning = false;
		lastTick = 0;
		numTrains = 2;

		
		targetTime = Calendar.getInstance();
		tempTime = Calendar.getInstance();

		simRunning = false;
		init = false;

		testInit();

		tnmUI.setIsPaused(true);
		tnmUI.setTrainList(trainList);
		tnmUI.setSelectedId(trainList.get(0).id);
		//tnmUI.setIsVisible(true);

	}

	public void frameStep() {
		//System.out.println("tick");
		if (simRunning) {
			targetTime.setTime(currentTime);
			targetTime.add(Calendar.MILLISECOND,1000);
			
			while(currentTime.getTime() < targetTime.getTime().getTime()){
				tnmUI.timeTick(currentTime, 100);
				tkc.nextTick();
				tempTime.setTime(currentTime);
				tempTime.add(Calendar.MILLISECOND,100);
				currentTime = tempTime.getTime();
			}
			
			tkmgui.repaint();
			trainList = tnmUI.getTrainList();
		}

	}

	
	public ArrayList<Train> getTrainsInBlock(int blockNum){
		return tnmUI.getTrainsInBlock(blockNum);
	}

	public void testInit() {
		//tnmUI = new TrainModelUI();

		//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");	
		tempTime.set(Calendar.HOUR_OF_DAY,7);
		tempTime.set(Calendar.MINUTE,59);
		tempTime.set(Calendar.SECOND,0);
		tempTime.set(Calendar.MILLISECOND,0);
		
		currentTime = tempTime.getTime();
		
		//current_clock_sec = 7 * 60 * 60 + 59 * 60 + 30;
			
		bYard = track.redLine.yard;
		redRoute = new ArrayList<Block>();
		
		//create red line route
		//9-1, 16-66, 52-16, 1-9, yard(0) 
		int i;
		//route.add((Block)track.getElementById(0));
		for (i=9; i>0; i--){
			redRoute.add((Block)track.getElementById(i));
		}
		for (i=16; i<67; i++){
			redRoute.add((Block)track.getElementById(i));
		}
		for (i=52; i>15; i--){
			redRoute.add((Block)track.getElementById(i));
		}
		for (i=52; i>15; i--){
			redRoute.add((Block)track.getElementById(i));
		}
		for (i=1; i<10; i++){
			redRoute.add((Block)track.getElementById(i));
		}
		//yard
		redRoute.add((Block)track.getElementById(0));
		


		trainList = new ArrayList<Train>();
		idArray = new String[numTrains];
		// Create the trains.
		for (i = 0; i < numTrains; i++) {
			Train tempTrain = new Train(i + 1, "T"+(i+1), "Test",
					(8 * 60 * 60 + i * 15 * 60) % (24 * 60 * 60), redRoute,
					new Engineer(true, false, 0.0,
							(8 * 60 * 60 + i * 15 * 60 + 4 * 60 * 60)
									% (24 * 60 * 60)), bYard);
			trainList.add(tempTrain);
			Integer tempInt = new Integer(i + 1);
			idArray[i] = new String("T"+(i+1));
		}
		
		track.setTrainList(trainList);
		tkmgui = new TKMGui(track);
		
		//tkc = new TrackController(track, this);
		//tkcgui = new ControllerUI(tkc);
	}

}


