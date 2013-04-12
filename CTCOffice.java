import processing.core.*;
import controlP5.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import TNM.*;
import SSC.*;

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
	//train shit
	TrainModelUI tnmUI;
	ArrayList<Train> trainList;
	Integer[] idArray;
	ArrayList<Block> route;
	Block bYard,b01,b02,b03,b04,b05,b06,b07,b08,b09,b10,b11,b12,b13,b14,b15,b16,b17;
	double current_clock_sec;
	
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "CTCOffice" });
	}

	// processing functions
	public void setup() {  
	  size(1000,800);
	  smooth();
	  frameRate(30);
	  lastTick = 0;
	  numTrains = 2;
	  simTimeRatio = 5;

	  cp5 = new ControlP5(this);
	  timer = new ControlTimer();
	  showTimer = new Textlabel(cp5,"--",100,100);
	  timer.setSpeedOfTime(1);
	  
	  simStarted = false;
	  init = false;
	  
	  testInit();
	  track_layout = new trackLayout();
	  addTrackGroup();
	  addClockGroup();
	  addScheduleGroup();
	  addStartButton();
	  addSimStartedWindows();
	  
	  
	  
	  tnmUI.setIsPaused(true);
	  tnmUI.setTrainList(trainList);
	  tnmUI.setSelectedId(trainList.get(0).getId());
	  tnmUI.setIsVisible(true);
	  
	  //new SSC_GUI();	

	}
	
	public void draw() {
		  background(193,205,193);
		  showTimer.setValue(timer.toString());
		  showTimer.setPosition(1, 1);
		  
		  if (simStarted){
			  showTimer.draw(this);
			  if (lastTick != timer.second()){
				  current_clock_sec += simTimeRatio;
				  tnmUI.timeTick((double)current_clock_sec, simTimeRatio);
				  lastTick = timer.second();
			  }
			  trainList = tnmUI.getTrainList();
		  }
		  
		  try{
			  String t_id = trainInfo_drop.getStringValue(); 
			  if(t_id.length()>5){
				  int sp = (int) trainList.get(Integer.parseInt(t_id)).getCurVelocity();
				  kn_trainSpeed.setValue((float)sp);
				  //kn_trainSpeed.update();
			  }
		  }
		  
		  catch (Exception e){
			  
		  }

	}
	
	
	// GUI init functions
	public void addTrackGroup(){
		  track = cp5.addGroup("Edit Track")
				  .setPosition(100,400)
				  .setBackgroundHeight(200)
				  .setBackgroundColor(color(255,50))
				  .setBarHeight(30)
				  .setWidth(300)
				  .hideArrow()
				  .setOpen(false)
				  .setMoveable(true)
				  ;
		  
		  trackRadio = cp5.addRadioButton("radio")
				  .setPosition(85,30)
				  .setSize(20,15)
				  .setColorLabel(145)
				  .addItem("Close for Maintenance",0)
				  .addItem("Remove",1)
				  .addItem("Add",2)
				  .setGroup(track)
				  ;
		  
		  closeTrack = cp5.addDropdownList("Select Track to Close...")
				  .setPosition(50,125)
				  .setSize(200,200)
				  .setGroup(track)
				  ;
				  
		  removeTrack = cp5.addDropdownList("Select Track to Remove...")
				  .setPosition(50,125)
				  .setSize(200,200)
				  .setGroup(track)
				  ;    
		  
		  customizeDropDownBlock(closeTrack, route);
		  customizeDropDownBlock(removeTrack, route);
		  closeTrack.hide();
		  removeTrack.hide();
	}
	
	public void addClockGroup(){
		  g_clock = cp5.addGroup("Simulation to Realtime Ratio")
				  .setPosition(550,400)
				  .setBackgroundHeight(200)
				  .setBackgroundColor(color(255,50))
				  .setBarHeight(30)
				  .setWidth(300)
				  .hideArrow()
				  .setOpen(false)
				  .setMoveable(true)
				  ;
		  
		  cp5.addSlider("Select")
		     .setPosition(60,75)
		     .setSize(180,15)
		     .setGroup(g_clock)
		     ;
		  
			 SimRatioBtn = cp5.addButton("Set Ratio")
				     .setValue(1)
				     .setPosition(115,150)
				     .setDefaultValue(5)
				     
				     .setSize(75,25)
				     .setId(2)
				     .setGroup(g_clock);
		     
/*		  cp5.addSlider("label2")
		     .setPosition(60,100)
		     .setSize(180,15)
		     .setGroup(g_clock)
		     ;*/

	}
	
	public void addScheduleGroup(){
		  sched = cp5.addGroup("Configure Schedule")
				  .setPosition(250,75)
				  .setBackgroundHeight(200)
				  .setBackgroundColor(color(255,50))
				  .setBarHeight(30)
				  .setWidth(450)
				  .hideArrow()
				  .setOpen(false)
				  .setMoveable(true)
				  ;
		  
/*		  cp5.addSlider("label3")
		     .setPosition(60,50)
		     .setSize(180,15)
		     .setGroup(sched)
		     ;
		     
		  cp5.addSlider("label4")
		     .setPosition(60,100)
		     .setSize(180,15)
		     .setGroup(sched)
		     ;*/

	}
	
	public void addStartButton(){
			startBtn = cp5.addButton("Start")
		     .setValue(1)
		     .setPosition(375,650)
		     .setSize(200,50)
		     .setId(1);
	}
	
	public void addSimStartedWindows(){
		TLgroup = cp5.addGroup("Track Layout")
		  .setPosition(75,150)
		  .setBackgroundHeight(350)
		  .setBackgroundColor(color(255,50))
		  .setBarHeight(30)
		  .setWidth(500)
		  .hideArrow()
		  .setOpen(true)
		  .setMoveable(true)
		  .disableCollapse()
		  .hide()
		  ;
		
		trainInfo = cp5.addGroup("Train Info")
				  .setPosition(600,150)
				  .setBackgroundHeight(350)
				  .setBackgroundColor(color(255,50))
				  .setBarHeight(30)
				  .setWidth(300)
				  .hideArrow()
				  .setOpen(false)
				  .setMoveable(true)
				  .hide()
				  ;
		

		trainInfo_drop = cp5.addDropdownList("Select Train")
				  .setPosition(50,50)
				  .setSize(200,200)
				  .setGroup(trainInfo)
				  ;
		
		  cp5.addSlider("Select Speed")
		     .setPosition(60,250)
		     .setSize(180,15)
		     .setDefaultValue(0)
		     .setGroup(trainInfo)
		     .setMax(70)
		     ;
		
		kn_trainSpeed = cp5.addKnob("knob")
	               .setRange(0,70)
	               .setValue(cp5.getController("Select Speed").getValue())
	               .setPosition(100,100)
	               .setRadius(50)
	               .setCaptionLabel("Current Speed")
	               .setGroup(trainInfo)
	               .lock()
	               ;
		
		 setTrainSpd = cp5.addButton("Set")
			     .setValue(1)
			     .setPosition(115,285)
			     .setSize(75,25)
			     .setId(2)
			     .setGroup(trainInfo);
		  
		customizeDropDownTrain(trainInfo_drop, trainList);
				
		
/*		  track_layout.pre(); // use cc.post(); to draw on top of existing controllers.
		  cp5.addCanvas(track_layout)
		    .setGroup()
		  ;*/
	}
	
	public void customizeDropDownBlock(DropdownList list, ArrayList<Block> aList) {
		  // a convenience function to customize a DropdownList
		  list.setBackgroundColor(color(190));
		  list.setItemHeight(30);
		  list.setBarHeight(15);
		  //list.getCaptionLabel().setText("dropdown");
		  list.getCaptionLabel().setPaddingY(3);
		  list.getCaptionLabel().setPaddingX(3);
		  list.getValueLabel().setPaddingX(3);
		  for (int i=0; i<aList.size() ;i++) {
		    list.addItem(""+aList.get(i).id, i);
		  }
		  //list.scroll(0);
		  list.setColorBackground(color(60));
		  list.setColorActive(color(255, 128));
		}
	
	public void customizeDropDownTrain(DropdownList list, ArrayList<Train> aList) {
		  // a convenience function to customize a DropdownList
		  list.setBackgroundColor(color(190));
		  list.setItemHeight(30);
		  list.setBarHeight(15);
		  //list.getCaptionLabel().setText("dropdown");
		  list.getCaptionLabel().setPaddingY(3);
		  list.getCaptionLabel().setPaddingX(3);
		  list.getValueLabel().setPaddingX(3);
		  for (int i=0; i<aList.size() ;i++) {
		    list.addItem(""+aList.get(i).getId(), i);
		  }
		  //list.scroll(0);
		  list.setColorBackground(color(60));
		  list.setColorActive(color(255, 128));
		}
	
	


	// Event handling
	public void controlEvent(ControlEvent theEvent) {
	  if(theEvent.isFrom(trackRadio)){
		  int index = (int)theEvent.getValue();
			if (index == 0){
				closeTrack.show();
				removeTrack.hide();
			}
			else if (index == 1){
				closeTrack.hide();
				removeTrack.show();
			}
			else{
				
			}
	  }
	  else if(theEvent.isFrom(startBtn)){
			if (simStarted){
				simStarted = false;
				tnmUI.setIsPaused(true);
				startBtn.setCaptionLabel("Start");
		    	track.show();
		    	g_clock.show();
		    	sched.show();
		    	TLgroup.hide();
		    	trainInfo.hide();
				
			}
		    else{
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
	  }
	  else if(theEvent.isGroup()) {
	    println("got an event from group "
	            +theEvent.getGroup().getName()
	            +", isOpen? "+theEvent.getGroup().isOpen()
	            );
	            
	  } else if (theEvent.isController()){
	    println("got something from a controller "
	            +theEvent.getController().getName()
	            );
	  }
	}
	
	public void testInit(){
		tnmUI = new TrainModelUI();
		
		current_clock_sec = 8*60*60+59*60+30;
				
		route = new ArrayList<Block>();
		bYard = new Block(0, 100.0, 0.0, true, 5.0, null, null, null, null, true, false, "Welcome to the Yard!", false, false, false, false, false, 0, 0.0, 0.0, 0.0, true);
		b01 = new Block(1, 500.0, 0.0, true, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b02 = new Block(2, 500.0, -22.2, true, 1.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b03 = new Block(3, 50.0, 0.0, true, 15.0, null, null, null, null, false, false, "", false, false, false, true, false, 0, 0.0, 0.0, 0.0, false);
		b04 = new Block(4, 500.0, 11.1, true, 30.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b05 = new Block(5, 50.0, 0.0, true, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b06 = new Block(6, 100.0, 0.0, true, 5.0, null, null, null, null, false, true, "Welcome to Station Alpha!", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b07 = new Block(7, 500.0, -11.1, false, 30.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b08 = new Block(8, 50.0, 0.0, false, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b09 = new Block(9, 500.0, 22.2, false, 1.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b10 = new Block(10, 50.0, 0.0, false, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b11 = new Block(11, 100.0, 0.0, false, 5.0, null, null, null, null, false, true, "Welcome to Station Beta!", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b12 = new Block(12, 50.0, 11.1, true, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b13 = new Block(13, 50.0, 0.0, false, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b14 = new Block(14, 50.0, -11.1, true, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b15 = new Block(15, 50.0, 0.0, false, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b16 = new Block(16, 100.0, 0.0, true, 5.0, null, null, null, null, false, true, "Welcome to Station Gamma!", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		b17 = new Block(17, 50.0, 0.0, false, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
		
		bYard.nextBlock = b01;
		b01.nextBlock = b02;
		b02.nextBlock = b03;
		b03.nextBlock = b04;
		b04.nextBlock = b05;
		b05.nextBlock = b06;
		b06.nextBlock = b07;
		b07.nextBlock = b06;
		b08.nextBlock = b07;
		b09.nextBlock = b08;
		b10.nextBlock = b09;
		b11.nextBlock = b10;
		b12.nextBlock = b13;
		b13.nextBlock = b12;
		b14.nextBlock = b15;
		b15.nextBlock = b14;
		b16.nextBlock = b17;
		b17.nextBlock = b16;
		
		bYard.prevBlock = b17;
		b01.prevBlock = bYard;
		b02.prevBlock = b01;
		b03.prevBlock = b02;
		b04.prevBlock = b03;
		b05.prevBlock = b04;
		b06.prevBlock = b05;
		b07.prevBlock = b08;
		b08.prevBlock = b09;
		b09.prevBlock = b10;
		b10.prevBlock = b11;
		b11.prevBlock = b12;
		b12.prevBlock = b11;
		b13.prevBlock = b14;
		b14.prevBlock = b13;
		b15.prevBlock = b16;
		b16.prevBlock = b15;
		b17.prevBlock = bYard;
		
		route.add(bYard);
		route.add(b01);
		route.add(b02);
		route.add(b03);
		route.add(b04);
		route.add(b05);
		route.add(b06);
		route.add(b07);
		route.add(b08);
		route.add(b09);
		route.add(b10);
		route.add(b11);
		route.add(b12);
		route.add(b13);
		route.add(b14);
		route.add(b15);
		route.add(b16);
		route.add(b17);
		route.add(bYard);
		route.add(b01);
		route.add(b02);
		route.add(b03);
		route.add(b04);
		route.add(b05);
		route.add(b06);
		route.add(b07);
		route.add(b08);
		route.add(b09);
		route.add(b10);
		route.add(b11);
		route.add(b12);
		route.add(b13);
		route.add(b14);
		route.add(b15);
		route.add(b16);
		route.add(b17);
		route.add(bYard);
		
		trainList = new ArrayList<Train>();
		idArray = new Integer[numTrains];
		// Create the trains.
		for (int i=0; i<numTrains; i++)
		{
			Train tempTrain = new Train(i+1, "Test", (9*60*60+i*30*60)%(24*60*60), route, new Engineer(true, false, 0.0, (9*60*60+i*30*60+4*60*60)%(24*60*60)), bYard);
			trainList.add(tempTrain);
			Integer tempInt = new Integer(i+1);
			idArray[i] = new Integer(tempInt);
		}
	}

	


	
}
