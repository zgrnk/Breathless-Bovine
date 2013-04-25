package CTCOffice;

import processing.core.*;
import controlP5.*;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JFrame;

import TNM.*;
import SSC.*;
import TKC.util.ControllerUI;
import TKC.util.TrackController;
import TKM.*;
import MBO.*;

public class CTCOffice extends PApplet {

	//GUI vars
	ControlP5 cp5;
	DropdownList closeTrack, removeTrack, addTrack1, addTrack2, trainInfo_drop;
	RadioButton trackRadio, simRadio;
	Group trackGroup, simGroup, sched, TLgroup, trainInfo;
	Tab trackTab;
	Button startBtn, setTrainSpd, SimRatioBtn, schedulerBtn, submitTrackBtn, SetSimBtn, editTrackWindowBtn;
	Knob kn_trainSpeed;
	PImage title;
	int width, height;
	
	ArrayList<Group> groupsA;
	ArrayList<Group> groupsB;
	ArrayList<Button> startButtons;
	ArrayList<Button> buttonsB;
	
	//CTC vars
	boolean welcomeScreen, init, dropDown1;
	int simTimeRatio, lastTick, numTrains;
	int cnt = 0;
	
	//dates
	Date currentSimTime; 
	Calendar targetTime;
	Calendar tempTime; 

	//Global
	ArrayList<Train> trainList = new ArrayList<Train>();
	ArrayList<Block> testRedRoute;
	TrackLayout testTrack;
	TrackMapPanel pMap;
	Block bYard, b01, b02, b03, b04, b05, b06, b07, b08, b09, b10, b11, b12, 
	b13, b14, b15, b16, b17;

	TrainModelUI tnmUI;
	TKMGui tkmgui;
	ControllerUI tkcgui;
	TrackController tkc;
	MBO_GUI mboGUI;
	SSC_GUI sscGUI;
	
	//edit track tracklayoutwindow
	JFrame editTrackWindow;


	public static void main(String args[]) {
		//PApplet.main(new String[] { "--present", "CTCOffice" });
		DisplayFrame df = new DisplayFrame();
		df.setVisible(true);
	}


	// processing functions
	public void setup() {
		width = 500;
		height = 600;
		size(width, height);
		smooth();
		frameRate(10);
		lastTick = 0;
		//in GUI set simToRealtime = 10
		//simTimeRatio = above * FrameRate
		//milliSecPerFrame = 1000
		simTimeRatio = 100;
		
		title = loadImage("BBtext.png");
		title.resize(300,0);
	
		cp5 = new ControlP5(this);
		targetTime = Calendar.getInstance();
		tempTime = Calendar.getInstance();

		welcomeScreen = true;
		init = false;
		dropDown1 = true;

		createRedRoute();
		createEditTrackGUI();

		addHomeButtons();
		addTrackGroup();
		addSimRatioGroup();
		addSimStartedWindows();

		

	}

	public void draw() {
		background(16, 159, 202);
		image(title,100,20);
		
		if (!welcomeScreen) {
			sendTicks();
			trainList = tnmUI.getTrainList();
			
			if (tkmgui.isVisible()) { tkmgui.repaint(); }
	
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
		//welcomescreen
		else{
			if (closeTrack.isOpen() || removeTrack.isOpen()	|| addTrack1.isOpen()  || addTrack2.isOpen()){ 
				submitTrackBtn.setVisible(false);	
			}
			if (editTrackWindow.isVisible()) { editTrackWindow.repaint(); }
			
		}

	}
	
	public void sendTicks(){
		targetTime.setTime(currentSimTime);
		targetTime.add(Calendar.MILLISECOND,1000);
		int count = 0;

		while(currentSimTime.getTime() < targetTime.getTime().getTime()){
			tnmUI.timeTick(currentSimTime, simTimeRatio);
			tkc.nextTick();

			tempTime.setTime(currentSimTime);
			tempTime.add(Calendar.MILLISECOND,simTimeRatio);
			currentSimTime = tempTime.getTime();
			count++;
		}
	}

	// GUI init functions
	public void addHomeButtons(){
		schedulerBtn = cp5.addButton("System Scheduler").setValue(1).setPosition(200, 130)
				.setSize(100, 50).setId(0);
		startBtn = cp5.addButton("Start").setValue(1).setPosition(150, 500)
				.setSize(200, 50).setId(1).setVisible(false);
		editTrackWindowBtn = cp5.addButton("Edit Track").setValue(1).setPosition(75, 170)
				.setSize(100, 50).setId(2).setVisible(true);
		SetSimBtn = cp5.addButton("Set Sim Ratio").setValue(1).setPosition(325, 170)
				.setSize(100, 50).setId(3).setVisible(true);
	}
	
	public void addTrackGroup() {
		trackGroup = cp5.addGroup("Track").setPosition(100, 250)
				.hideBar().setMoveable(true).setVisible(false)
				.setBackgroundHeight(200).setBackgroundColor(color(255, 50))
				.setBarHeight(30).setWidth(300).hideArrow().setOpen(true);

		trackRadio = cp5.addRadioButton("radio").setPosition(85, 30).setSpacingRow(5)
				.setSize(20, 15).setColorLabel(145).setColorActive(-1)
				.addItem("Close for Maintenance", 0).addItem("Remove", 1)
				.addItem("Add", 2).setGroup(trackGroup);

		closeTrack = cp5.addDropdownList("Select Track")
				.setPosition(90, 125).setSize(115, 200).setGroup(trackGroup).bringToFront();

		removeTrack = cp5.addDropdownList("Select Track ")
				.setPosition(90, 125).setSize(115, 200).setGroup(trackGroup);
		addTrack1 = cp5.addDropdownList("Starting Block")
				.setPosition(60, 125).setSize(75, 200).setGroup(trackGroup);
		addTrack2 = cp5.addDropdownList("End Block")
				.setPosition(160, 125).setSize(75, 200).setGroup(trackGroup);

		customizeDropDownBlock(closeTrack, testTrack.redLine.getBlocks(), false);
		customizeDropDownBlock(removeTrack, testTrack.redLine.getBlocks(), false);
		customizeDropDownBlock(addTrack1, testTrack.redLine.getBlocks(), true);
		customizeDropDownBlock(addTrack2, testTrack.redLine.getBlocks(), true);
		closeTrack.hide();
		removeTrack.hide();
		addTrack1.hide();
		addTrack2.hide();
		
		
		submitTrackBtn = cp5.addButton("Submit").setValue(1).setPosition(100, 150)
				.setSize(75, 25).setId(1).setGroup(trackGroup).setVisible(false);
	}

	public void addSimRatioGroup() {
		simGroup = 	cp5.addGroup("Simulation to Realtime Ratio").setPosition(100, 250)
				.hideBar().setMoveable(true).setVisible(false)
				.setBackgroundHeight(200).setBackgroundColor(color(255, 50))
				.setBarHeight(30).setWidth(300).hideArrow().setOpen(true);


		simRadio = cp5.addRadioButton("sim_radio").setPosition(85, 50).setSpacingRow(5)
				.setSize(20, 15).setColorLabel(145).setColorActive(-1)
				.addItem("Real Time", 0).addItem("x10", 1)
				.addItem("x100", 2).setGroup(simGroup);

		SimRatioBtn = cp5.addButton("Set Ratio").setValue(1)
				.setPosition(115, 125).setDefaultValue(5)
				.setSize(75, 25).setId(2).setGroup(simGroup);

		/*
		 * cp5.addSlider("label2") .setPosition(60,100) .setSize(180,15)
		 * .setGroup(simGroup) ;
		 */

	}


	public void addScheduleGroup() {

		sched = cp5.addGroup("Configure Schedule").setPosition(250, 75)
				.setBackgroundHeight(200).setBackgroundColor(color(255, 75))
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

	public void customizeDropDownBlock(DropdownList list, ArrayList<Block> aList, boolean isTrackList) {
		list.setBackgroundColor(color(190));
		list.setItemHeight(30);
		list.setBarHeight(20);
		// list.getCaptionLabel().setText("dropdown");
		list.getCaptionLabel().setPaddingY(3);
		list.getCaptionLabel().setPaddingX(3);
		list.getValueLabel().setPaddingX(3);
		if (isTrackList){
			list.addItem("DEADEND", 0);
		}
		for (int i = 1; i < aList.size(); i++) {
			list.addItem("" + aList.get(i).id, i);
		}

		// list.scroll(0);
		list.setColorBackground(color(60));
		list.setColorActive(color(255, 128));
		list.bringToFront();
	}

	public void customizeDropDownTrain(DropdownList list, ArrayList<Train> aList) {
		// a convenience function to customize a DropdownList
		list.setBackgroundColor(color(190));
		list.setItemHeight(30);
		list.setBarHeight(30);
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
	
	public void resetDropdown(DropdownList list, ArrayList<Block> aList, boolean isTrackList){
		list.clear();
		if (isTrackList){
			list.addItem("DEADEND", 0);
		}
		for (int i = 1; i < aList.size(); i++) {
			list.addItem("" + aList.get(i).id, i);
		}
	}

	// Event handling
	public void controlEvent(ControlEvent theEvent) {
		if (theEvent.isFrom(closeTrack) || theEvent.isFrom(removeTrack) 
				|| theEvent.isFrom(addTrack1)  || theEvent.isFrom(addTrack2)){
			int index = (int)theEvent.getGroup().getValue();
			if(theEvent.isFrom(addTrack1)  || theEvent.isFrom(addTrack2) ){
				// 0 set to "DEADEND"
				index++;
			}
			submitTrackBtn.setVisible(true);
			editTrackWindowBtn.setVisible(true);
			//System.out.println((int)theEvent.getGroup().getValue());
			//testTrack.setSelectedElement( (Block)testTrack.getElementById( (int)theEvent.getGroup().getValue() ) );
			testTrack.setSelectedElement( (Block)testTrack.redLine.getBlocks().get( index ) );
			//System.out.println(testTrack.redLine.getBlocks().get( (int)theEvent.getGroup().getValue() ));
		}
		else if (theEvent.isFrom(schedulerBtn)){
			getScheduleFromSSC();
			createTrainList();
			schedulerBtn.setOff();
			startBtn.setVisible(true);
		}
		else if (theEvent.isFrom(SetSimBtn)){
			simGroup.setVisible(true);
			trackGroup.setVisible(false);
			editTrackWindow.setVisible(false);
		}
		else if (theEvent.isFrom(editTrackWindowBtn)){
			trackGroup.setVisible(true);
			editTrackWindow.setVisible(true);
			simGroup.setVisible(false);
		}
		
		else if (theEvent.isFrom(submitTrackBtn)){
		      if (closeTrack.isVisible()){
		    	  Block bl = (Block)testTrack.redLine.getBlocks().get( (int)closeTrack.getValue() );
		    	  bl.isClosed = true;
		    	  resetDropdown(closeTrack, testTrack.redLine.getBlocks(), false);
		      } 
		      else if (removeTrack.isVisible()) {
		    	  Block bl = (Block)testTrack.redLine.getBlocks().get( (int)removeTrack.getValue() );
		    	  testTrack.redLine.removeBlock(bl);
		    	  resetDropdown(closeTrack, testTrack.redLine.getBlocks(), false);
		      }
		      else if (addTrack1.isVisible()) {
		    	  
		    	  if (addTrack1.getValue() == 0.0 && addTrack2.getValue() == 0.0){
		    		  // dont do anything
		    	  }
		    	  else if (addTrack1.getValue() == 0.0){
		    		  Block blk = testTrack.redLine.addBlock(null, 
		    				  (Block)testTrack.redLine.getBlocks().get( (int)addTrack2.getValue()));
		    		  pMap.setBlockLocation(blk);
		    		  resetDropdown(closeTrack, testTrack.redLine.getBlocks(), true);
		    		
		    	  }
		    	  else if (addTrack2.getValue() == 0.0){
		    		  Block blk = testTrack.redLine.addBlock((Block)testTrack.redLine.getBlocks().get( (int)addTrack1.getValue()), null);
		    		  pMap.setBlockLocation(blk);
		    		  resetDropdown(closeTrack, testTrack.redLine.getBlocks(), true);
		    	  }
		    	  
		    	  else{
			    	  Block from = (Block)testTrack.redLine.getBlocks().get( (int)addTrack1.getValue() );
			    	  Block to = (Block)testTrack.redLine.getBlocks().get( (int)addTrack2.getValue() );
			    	  Block blk = testTrack.redLine.addBlock(from, to);
			    	  resetDropdown(closeTrack, testTrack.redLine.getBlocks(), true);
			    	  pMap.setBlockLocation(blk);
		    	  }

		      }
		}

		else if (theEvent.isFrom(startBtn)) {
			if (welcomeScreen) {
				testTrack.setTrainList(trainList);
				
				tnmUI = new TrainModelUI();
				tkmgui = new TKMGui(testTrack);
				tkc = new TrackController(testTrack, this);
				tkcgui = new ControllerUI(tkc);
				
				mboGUI = new MBO_GUI(trainList);
				tnmUI.setIsPaused(true);
				tnmUI.setTrainList(trainList);
				tnmUI.setSelectedId(trainList.get(0).id);
				
				tkmgui.setVisible(false);
				tnmUI.setIsVisible(false);
				tkcgui.setVisible(false);
				mboGUI.setVisible(false);
				
				tnmUI.setIsPaused(false);
				startBtn.setCaptionLabel("Pause");
				trackGroup.hide();
				simGroup.hide();
				TLgroup.show();
				trainInfo.show();

				welcomeScreen = false;
				init = true;


			} 
			else {
				tnmUI.setIsPaused(true);
				startBtn.setCaptionLabel("Resume");
				trackGroup.show();
				simGroup.show();
				sched.show();
				TLgroup.hide();
				trainInfo.hide();
				System.out.println("SYSTEM PAUSED");
				welcomeScreen = true;
			}
		} 

	}
	
	//event handler for radio group
	public void radio(int index) {
		if (index == 0) {
			closeTrack.show();
			removeTrack.hide();
			addTrack1.hide();
			addTrack2.hide();
		} else if (index == 1) {
			closeTrack.hide();
			removeTrack.show();
			addTrack1.hide();
			addTrack2.hide();
		} else if(index == 2){
			addTrack1.show();
			addTrack2.show();
			closeTrack.hide();
			removeTrack.hide();
		}
		else {
			addTrack1.hide();
			addTrack2.hide();
			closeTrack.hide();
			removeTrack.hide();
		}
	}
	
	public void Start(boolean b){
		System.out.println(b);
		
	}

	public ArrayList<Train> getTrainsInBlock(int blockNum){

		return tnmUI.getTrainsInBlock(blockNum);
	}
	
	public void setDropdown(Block block){
		if (closeTrack.isVisible()) closeTrack.setValue(block.id);
		if (removeTrack.isVisible()) removeTrack.setValue(block.id);
		if (addTrack1.isVisible() && dropDown1) {
			dropDown1 = !dropDown1;
			addTrack1.setValue(block.id);
		}
		else if( addTrack2.isVisible() && !dropDown1) {
				dropDown1 = !dropDown1;
				addTrack2.setValue(block.id);
		}

	}

	
	public void createEditTrackGUI(){
		editTrackWindow = new JFrame();
		pMap = new TrackMapPanel(testTrack, this);
		editTrackWindow.setContentPane(pMap); //,this
		editTrackWindow.setSize(550, 710);
		editTrackWindow.setTitle("Edit Track");
		editTrackWindow.setLocation(1150, 150);
	}
	
	public void createRedRoute(){
		testTrack = new TrackLayout();	
		testTrack.parseTrackDB("track_db.csv");	
		bYard = testTrack.redLine.yard;
		testRedRoute = new ArrayList<Block>();

		//create red line route
		//9-1, 16-66, 52-16, 1-9, yard(0) 
		int i;
		testRedRoute.add((Block)testTrack.getElementById(0));
		for (i=9; i>0; i--){
			testRedRoute.add((Block)testTrack.getElementById(i));
		}
		for (i=16; i<67; i++){
			testRedRoute.add((Block)testTrack.getElementById(i));
		}
		for (i=52; i>15; i--){
			testRedRoute.add((Block)testTrack.getElementById(i));
		}
		for (i=52; i>15; i--){
			testRedRoute.add((Block)testTrack.getElementById(i));
		}
		for (i=1; i<10; i++){
			testRedRoute.add((Block)testTrack.getElementById(i));
		}

	}

	public void getScheduleFromSSC() {
		sscGUI = new SSC_GUI();
		sscGUI.setLocation(1150, 350);

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar thisTempTime = Calendar.getInstance();

		while(sscGUI.lbl_start_time.getText().equals("------")){
			wait(1);
		}

		try {
			thisTempTime.setTime(sdf.parse(sscGUI.sscListener.scheduler.startTime + ":00"));
		} catch (ParseException e) {}
		
		thisTempTime.add(Calendar.MINUTE, -1);

		currentSimTime = thisTempTime.getTime();

		while(sscGUI.lbl_generate_sch.getText().equals("------")){
			wait(1);
		}

	}
	
	public void createTrainList(){
		trainList = new ArrayList<Train>();

		ArrayList<String> routesList = sscGUI.sscListener.scheduler.routesSch;
		ArrayList<String[]> routesArray = new ArrayList<String[]>();

		for(int k=1; k<routesList.size(); k++){
			StringTokenizer st = new StringTokenizer(routesList.get(k), "\t");

			String[] singleRoute = new String[4];

			int x=0;
			while (st.hasMoreTokens()) {
				singleRoute[x] = st.nextToken();
				x++;
			}
			routesArray.add(singleRoute);
		}

		for(int r=0; r<routesArray.size(); r++){

			double dispatchTime = ((Integer.valueOf(routesArray.get(r)[2].substring(0, 2))*60*60 + Integer.valueOf(routesArray.get(r)[2].substring(3, 5))*60)) % (24*60*60);
			double breakTime = (dispatchTime + (4*360)) % (24*60*60);

			Train tempTrain = new Train(r+1, routesArray.get(r)[0], routesArray.get(r)[1],
					dispatchTime, testRedRoute,new Engineer(false, false, 0,breakTime), bYard);

			trainList.add(tempTrain);
		}
	}

	public static void wait (int k){
		long time0, time1;
		time0 = System.currentTimeMillis();
		do{
			time1 = System.currentTimeMillis();
		}
		while ((time1 - time0) < k * 1000);
	}

}


