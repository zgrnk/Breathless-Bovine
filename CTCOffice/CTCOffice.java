package CTCOffice;

import processing.core.*;
import controlP5.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JFrame;

import TNM.*;
import SSC.*;
import TKC.util.TrackController;
import TKM.*;

public class CTCOffice extends PApplet {

	ControlP5 cp5;
	ControlTimer timer;
	DropdownList closeTrack, removeTrack, AddTrack1, AddTrack2, trainInfo_drop;
	RadioButton trackRadio;
	Group track, g_clock, sched, TLgroup, trainInfo;
	List<Group> groups;
	Textlabel showTimer;
	Button startBtn, setTrainSpd, SimRatioBtn;
	Knob kn_trainSpeed;

	Canvas track_layout;
	boolean simStarted, init;
	int simTimeRatio, lastTick, numTrains;
	int cnt = 0;
	// train shit
	TrainModelUI tnmUI;
	ArrayList<Train> trainList;
	String[] idArray;
	ArrayList<Block> redRoute;
	Block bYard, b01, b02, b03, b04, b05, b06, b07, b08, b09, b10, b11, b12,
			b13, b14, b15, b16, b17;
	double current_clock_sec;
	Date currentTime; 
	Calendar targetTime; 
	Calendar tempTime; 
	TKMGui tkmgui;
	TrackController tkc;

	public static void main(String args[]) {
		//PApplet.main(new String[] { "--present", "CTCOffice" });
		new DisplayFrame().setVisible(true);
	}
	


	// processing functions
	public void setup() {
		size(1000, 800);
		smooth();
		frameRate(10);
		lastTick = 0;
		numTrains = 2;
		//in GUI set simToRealtime = 10
		//simTimeRatio = above * FrameRate
		//milliSecPerFrame = 1000
		
		
		simTimeRatio = 100;

		cp5 = new ControlP5(this);
		timer = new ControlTimer();
		showTimer = new Textlabel(cp5, "--", 100, 100);
		timer.setSpeedOfTime((float)simTimeRatio);
		targetTime = Calendar.getInstance();
		tempTime = Calendar.getInstance();

		simStarted = false;
		init = false;

		testInit();
		//track_layout = new trackLayout();
		addTrackGroup();
		addClockGroup();
		addScheduleGroup();
		addStartButton();
		addSimStartedWindows();

		tnmUI.setIsPaused(true);
		tnmUI.setTrainList(trainList);
		tnmUI.setSelectedId(trainList.get(0).id);
		tnmUI.setIsVisible(true);

		//new SSC_GUI();

	}

	public void draw() {
		background(193, 205, 193);
		showTimer.setValue(timer.toString());
		showTimer.setPosition(1, 1);

		if (simStarted) {
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
			
/*			if (lastTick != timer.millis()) {
				Calendar tempTime = Calendar.getInstance();
				tempTime.setTime(currentTime);
				tempTime.add(Calendar.MILLISECOND,simTimeRatio);
				currentTime = tempTime.getTime();
				tnmUI.timeTick(currentTime, 100);
				lastTick = timer.millis();
			}*/
			trainList = tnmUI.getTrainList();
			
			
			
			
			
/*			showTimer.draw(this);
			//second in relation to simRatio speed
			if (lastTick != timer.millis()) {
				Calendar tempTime = Calendar.getInstance();
				tempTime.setTime(currentTime);
				tempTime.add(Calendar.MILLISECOND,simTimeRatio);
				currentTime = tempTime.getTime();
				tnmUI.timeTick(currentTime, 100);
				lastTick = timer.millis();
			}
			trainList = tnmUI.getTrainList();*/
		}

		try {
			String t_id = trainInfo_drop.getStringValue();
			if (t_id.length() > 5) {
				int sp = (int) trainList.get(Integer.parseInt(t_id))
						.curVelocity;
				kn_trainSpeed.setValue((float) sp);
				// kn_trainSpeed.update();
			}
		}

		catch (Exception e) {

		}

	}

	// GUI init functions
	public void addTrackGroup() {
		track = cp5.addGroup("Edit Track").setPosition(100, 400)
				.setBackgroundHeight(200).setBackgroundColor(color(255, 50))
				.setBarHeight(30).setWidth(300).hideArrow().setOpen(false)
				.setMoveable(true);

		trackRadio = cp5.addRadioButton("radio").setPosition(85, 30)
				.setSize(20, 15).setColorLabel(145)
				.addItem("Close for Maintenance", 0).addItem("Remove", 1)
				.addItem("Add", 2).setGroup(track);

		closeTrack = cp5.addDropdownList("Select Track to Close...")
				.setPosition(50, 125).setSize(200, 200).setGroup(track);

		removeTrack = cp5.addDropdownList("Select Track to Remove...")
				.setPosition(50, 125).setSize(200, 200).setGroup(track);

		customizeDropDownBlock(closeTrack, redRoute);
		customizeDropDownBlock(removeTrack, redRoute);
		closeTrack.hide();
		removeTrack.hide();
	}

	public void addClockGroup() {
		g_clock = cp5.addGroup("Simulation to Realtime Ratio")
				.setPosition(550, 400).setBackgroundHeight(200)
				.setBackgroundColor(color(255, 50)).setBarHeight(30)
				.setWidth(300).hideArrow().setOpen(false).setMoveable(true);

		cp5.addSlider("Select").setPosition(60, 75).setSize(180, 15)
				.setGroup(g_clock);

		SimRatioBtn = cp5.addButton("Set Ratio").setValue(1)
				.setPosition(115, 150).setDefaultValue(5)

				.setSize(75, 25).setId(2).setGroup(g_clock);

		/*
		 * cp5.addSlider("label2") .setPosition(60,100) .setSize(180,15)
		 * .setGroup(g_clock) ;
		 */

	}

	public void addScheduleGroup() {
		sched = cp5.addGroup("Configure Schedule").setPosition(250, 75)
				.setBackgroundHeight(200).setBackgroundColor(color(255, 50))
				.setBarHeight(30).setWidth(450).hideArrow().setOpen(false)
				.setMoveable(true);

		/*
		 * cp5.addSlider("label3") .setPosition(60,50) .setSize(180,15)
		 * .setGroup(sched) ;
		 * 
		 * cp5.addSlider("label4") .setPosition(60,100) .setSize(180,15)
		 * .setGroup(sched) ;
		 */

	}

	public void addStartButton() {
		startBtn = cp5.addButton("Start").setValue(1).setPosition(375, 650)
				.setSize(200, 50).setId(1);
	}

	public void addSimStartedWindows() {
		TLgroup = cp5.addGroup("Track Layout").setPosition(75, 150)
				.setBackgroundHeight(350).setBackgroundColor(color(255, 50))
				.setBarHeight(30).setWidth(500).hideArrow().setOpen(true)
				.setMoveable(true).disableCollapse().hide();

		trainInfo = cp5.addGroup("Train Info").setPosition(600, 150)
				.setBackgroundHeight(350).setBackgroundColor(color(255, 50))
				.setBarHeight(30).setWidth(300).hideArrow().setOpen(false)
				.setMoveable(true).hide();

		trainInfo_drop = cp5.addDropdownList("Select Train")
				.setPosition(50, 50).setSize(200, 200).setGroup(trainInfo);

		cp5.addSlider("Select Speed").setPosition(60, 250).setSize(180, 15)
				.setDefaultValue(0).setGroup(trainInfo).setMax(70);

		kn_trainSpeed = cp5.addKnob("knob").setRange(0, 70)
				.setValue(cp5.getController("Select Speed").getValue())
				.setPosition(100, 100).setRadius(50)
				.setCaptionLabel("Current Speed").setGroup(trainInfo).lock();

		setTrainSpd = cp5.addButton("Set").setValue(1).setPosition(115, 285)
				.setSize(75, 25).setId(2).setGroup(trainInfo);

		customizeDropDownTrain(trainInfo_drop, trainList);

		/*
		 * track_layout.pre(); // use cc.post(); to draw on top of existing
		 * controllers. cp5.addCanvas(track_layout) .setGroup() ;
		 */
	}

	public void customizeDropDownBlock(DropdownList list, ArrayList<Block> aList) {
		// a convenience function to customize a DropdownList
		list.setBackgroundColor(color(190));
		list.setItemHeight(30);
		list.setBarHeight(15);
		// list.getCaptionLabel().setText("dropdown");
		list.getCaptionLabel().setPaddingY(3);
		list.getCaptionLabel().setPaddingX(3);
		list.getValueLabel().setPaddingX(3);
		for (int i = 0; i < aList.size(); i++) {
			//list.addItem("" + aList.get(i).id, i);
		}
		// list.scroll(0);
		list.setColorBackground(color(60));
		list.setColorActive(color(255, 128));
	}

	public void customizeDropDownTrain(DropdownList list, ArrayList<Train> aList) {
		// a convenience function to customize a DropdownList
		list.setBackgroundColor(color(190));
		list.setItemHeight(30);
		list.setBarHeight(15);
		// list.getCaptionLabel().setText("dropdown");
		list.getCaptionLabel().setPaddingY(3);
		list.getCaptionLabel().setPaddingX(3);
		list.getValueLabel().setPaddingX(3);
		for (int i = 0; i < aList.size(); i++) {
			list.addItem("" + aList.get(i).id, i);
		}
		// list.scroll(0);
		list.setColorBackground(color(60));
		list.setColorActive(color(255, 128));
	}

	// Event handling
	public void controlEvent(ControlEvent theEvent) {
		if (theEvent.isFrom(trackRadio)) {
			int index = (int) theEvent.getValue();
			if (index == 0) {
				closeTrack.show();
				removeTrack.hide();
			} else if (index == 1) {
				closeTrack.hide();
				removeTrack.show();
			} else {

			}
		} else if (theEvent.isFrom(startBtn)) {
			if (simStarted) {
				simStarted = false;
				tnmUI.setIsPaused(true);
				startBtn.setCaptionLabel("Start");
				track.show();
				g_clock.show();
				sched.show();
				TLgroup.hide();
				trainInfo.hide();

			} else {
				simStarted = true;
				tnmUI.setIsPaused(false);
				startBtn.setCaptionLabel("Pause");
				timer.reset();
				track.hide();
				g_clock.hide();
				sched.hide();
				TLgroup.show();
				trainInfo.show();

			}
		} else if (theEvent.isGroup()) {
			/*println("got an event from group " + theEvent.getGroup().getName()
					+ ", isOpen? " + theEvent.getGroup().isOpen());*/

		} else if (theEvent.isController()) {
		/*	println("got something from a controller "
					+ theEvent.getController().getName());*/
		}
	}
	
	public ArrayList<Train> getTrainsInBlock(int blockNum){
		
		return tnmUI.getTrainsInBlock(blockNum);
	}

	public void testInit() {
		tnmUI = new TrainModelUI();

		//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");	
		tempTime.set(Calendar.HOUR_OF_DAY,7);
		tempTime.set(Calendar.MINUTE,59);
		tempTime.set(Calendar.SECOND,0);
		tempTime.set(Calendar.MILLISECOND,0);
		
		currentTime = tempTime.getTime();
		
		//current_clock_sec = 7 * 60 * 60 + 59 * 60 + 30;
		
		TrackLayout track = new TrackLayout();	
		track.parseTrackDB("track_db.csv");	
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
		
		tkc = new TrackController(track, this);
	}

}


