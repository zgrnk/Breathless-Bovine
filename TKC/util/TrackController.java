package TKC.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import CTCOffice.CTCOffice;
import TKM.Block;
import TKM.Switch;
import TKM.TrackLayout;



/**
 * 
 * @author Dominic Visco
 *
 */
public class TrackController {

	public boolean currentLine;
	public ArrayList<Wayside> controllerList;
	public ArrayList<Switch> switchList;
	public ArrayList<Block> blockList;
	public TrackLayout layout;
	public CTCOffice ctc;
	
	/**
	 * 
	 * @param blockList
	 * @param switchList
	 * @param ctc
	 */
	public TrackController(TrackLayout lyt, CTCOffice ctc) {
		this.layout = lyt;
		this.ctc = ctc;
		this.controllerList = new ArrayList<Wayside>();
		initControllers();
	}
	
	public void nextTick() {
		for (Wayside way : controllerList)
		{
			way.nextTick();
		}
		
	}
	public void initControllers() {
		
		
		/*Initializing controllers for the red line*/
		boolean switchS = Switch.STATE_STRAIGHT;
		Component lightS = new Component(0,0);
		SafetyInfo newInfo = new SafetyInfo(switchS, lightS, true);
		
		Wayside one;
		
		Hashtable<Integer, Block> oneBL = new Hashtable<Integer, Block>();
		oneBL.put(0, (Block) layout.getElementById(0));
		oneBL.put(7, (Block) layout.getElementById(7));
		oneBL.put(8, (Block) layout.getElementById(8));
		oneBL.put(9, (Block) layout.getElementById(9));
		oneBL.put(10, (Block) layout.getElementById(10));
		oneBL.put(11, (Block) layout.getElementById(11));
		oneBL.put(12, (Block) layout.getElementById(12));
		
		ArrayList<Integer> oneEndBL = new ArrayList<Integer>();
		oneEndBL.add(0);
		oneEndBL.add(7);
		oneEndBL.add(12);
		
		Switch oneSw = (Switch) layout.getElementById(1001);
		
		one = new Wayside(oneBL, oneEndBL, ctc, newInfo, oneSw);
		controllerList.add(one);
		
		Wayside two;
		
		Hashtable<Integer, Block> twoBL = new Hashtable<Integer, Block>();
		twoBL.put(23, (Block) layout.getElementById(23));
		twoBL.put(22, (Block) layout.getElementById(22));
		twoBL.put(21, (Block) layout.getElementById(21));
		twoBL.put(20, (Block) layout.getElementById(20));
		twoBL.put(19, (Block) layout.getElementById(19));
		twoBL.put(18, (Block) layout.getElementById(18));
		twoBL.put(17, (Block) layout.getElementById(17));
		twoBL.put(16, (Block) layout.getElementById(16));
		twoBL.put(1, (Block) layout.getElementById(1));
		twoBL.put(2, (Block) layout.getElementById(2));
		twoBL.put(3, (Block) layout.getElementById(3));
		twoBL.put(4, (Block) layout.getElementById(4));
		twoBL.put(5, (Block) layout.getElementById(5));
		twoBL.put(6, (Block) layout.getElementById(6));
		twoBL.put(7, (Block) layout.getElementById(7));
		twoBL.put(15, (Block) layout.getElementById(15));
		twoBL.put(14, (Block) layout.getElementById(14));
		twoBL.put(13, (Block) layout.getElementById(13));
		twoBL.put(12, (Block) layout.getElementById(12));
		
		
		ArrayList<Integer> twoEndBL = new ArrayList<Integer>();
		twoEndBL.add(23);
		twoEndBL.add(7);
		twoEndBL.add(12);
		
		Switch twoSw = (Switch) layout.getElementById(1000);
		
		two = new Wayside(twoBL, twoEndBL, ctc, newInfo, twoSw);
		controllerList.add(two);
		
		Wayside three;
		Hashtable<Integer, Block> threeBL = new Hashtable<Integer, Block>();
		threeBL.put(23, (Block) layout.getElementById(23));
		threeBL.put(24, (Block) layout.getElementById(24));
		threeBL.put(25, (Block) layout.getElementById(25));
		threeBL.put(26, (Block) layout.getElementById(26));
		threeBL.put(27, (Block) layout.getElementById(27));
		threeBL.put(76, (Block) layout.getElementById(76));
		threeBL.put(75, (Block) layout.getElementById(75));
		threeBL.put(28, (Block) layout.getElementById(28));
		threeBL.put(29, (Block) layout.getElementById(29));
		threeBL.put(30, (Block) layout.getElementById(30));
		
		ArrayList<Integer> threeEndBL = new ArrayList<Integer>();
		threeEndBL.add(23);
		threeEndBL.add(75);
		threeEndBL.add(30);
		
		Switch threeSw = (Switch) layout.getElementById(1002);
		
		three = new Wayside(threeBL, threeEndBL, ctc, newInfo, threeSw);
		controllerList.add(three);
		
		Wayside four;
		Hashtable<Integer, Block> fourBL = new Hashtable<Integer, Block>();
		fourBL.put(33, (Block) layout.getElementById(33));
		fourBL.put(34, (Block) layout.getElementById(34));
		fourBL.put(35, (Block) layout.getElementById(35));
		fourBL.put(36, (Block) layout.getElementById(36));
		fourBL.put(72, (Block) layout.getElementById(72));
		fourBL.put(73, (Block) layout.getElementById(73));
		fourBL.put(74, (Block) layout.getElementById(74));
		fourBL.put(75, (Block) layout.getElementById(75));
		fourBL.put(30, (Block) layout.getElementById(33));
		fourBL.put(31, (Block) layout.getElementById(31));
		fourBL.put(32, (Block) layout.getElementById(32));
		
		ArrayList<Integer> fourEndBL = new ArrayList<Integer>();
		fourEndBL.add(36);
		fourEndBL.add(75);
		fourEndBL.add(30);
		
		Switch fourSw = (Switch) layout.getElementById(1003);
		
		four = new Wayside(fourBL, fourEndBL, ctc, newInfo, fourSw);
		controllerList.add(four);
		
		Wayside five;
		Hashtable<Integer, Block> fiveBL = new Hashtable<Integer, Block>();
		fiveBL.put(36, (Block) layout.getElementById(36));
		fiveBL.put(37, (Block) layout.getElementById(37));
		fiveBL.put(38, (Block) layout.getElementById(38));
		fiveBL.put(71, (Block) layout.getElementById(36));
		fiveBL.put(70, (Block) layout.getElementById(36));
		fiveBL.put(69, (Block) layout.getElementById(36));
		fiveBL.put(39, (Block) layout.getElementById(39));
		fiveBL.put(40, (Block) layout.getElementById(40));
		fiveBL.put(41, (Block) layout.getElementById(41));
		
		ArrayList<Integer> fiveEndBL = new ArrayList<Integer>();
		fiveEndBL.add(36);
		fiveEndBL.add(69);
		fiveEndBL.add(41);
		
		Switch fiveSw = (Switch) layout.getElementById(1004);
		
		five = new Wayside(fiveBL, fiveEndBL, ctc, newInfo, fiveSw);
		controllerList.add(five);
		
		Wayside six;
		Hashtable<Integer, Block> sixBL = new Hashtable<Integer, Block>();
		sixBL.put(44, (Block) layout.getElementById(44));
		sixBL.put(45, (Block) layout.getElementById(45));
		sixBL.put(46, (Block) layout.getElementById(46));
		sixBL.put(47, (Block) layout.getElementById(47));
		sixBL.put(48, (Block) layout.getElementById(48));
		sixBL.put(67, (Block) layout.getElementById(67));
		sixBL.put(68, (Block) layout.getElementById(68));
		sixBL.put(69, (Block) layout.getElementById(69));
		sixBL.put(41, (Block) layout.getElementById(41));
		sixBL.put(42, (Block) layout.getElementById(42));
		sixBL.put(43, (Block) layout.getElementById(43));
		
		ArrayList<Integer> sixEndBL = new ArrayList<Integer>();
		sixEndBL.add(48);
		sixEndBL.add(69);
		sixEndBL.add(41);
		
		Switch sixSw = (Switch) layout.getElementById(1005);
		
		six = new Wayside(sixBL, sixEndBL, ctc, newInfo, sixSw);
		controllerList.add(six);
		
		Wayside seven;
		Hashtable<Integer, Block> sevenBL = new Hashtable<Integer, Block>();
		sevenBL.put(48, (Block) layout.getElementById(48));
		sevenBL.put(49, (Block) layout.getElementById(49));
		sevenBL.put(50, (Block) layout.getElementById(50));
		sevenBL.put(51, (Block) layout.getElementById(51));
		sevenBL.put(52, (Block) layout.getElementById(52));
		sevenBL.put(66, (Block) layout.getElementById(66));
		sevenBL.put(65, (Block) layout.getElementById(65));
		sevenBL.put(64, (Block) layout.getElementById(64));
		sevenBL.put(63, (Block) layout.getElementById(63));
		sevenBL.put(62, (Block) layout.getElementById(62));
		sevenBL.put(61, (Block) layout.getElementById(61));
		sevenBL.put(53, (Block) layout.getElementById(53));
		sevenBL.put(54, (Block) layout.getElementById(54));
		sevenBL.put(55, (Block) layout.getElementById(55));
		sevenBL.put(56, (Block) layout.getElementById(56));
		sevenBL.put(57, (Block) layout.getElementById(57));
		sevenBL.put(58, (Block) layout.getElementById(58));
		sevenBL.put(59, (Block) layout.getElementById(59));
		sevenBL.put(60, (Block) layout.getElementById(60));
		
		ArrayList<Integer> sevenEndBL = new ArrayList<Integer>();
		sevenEndBL.add(48);
		sevenEndBL.add(60);
		sevenEndBL.add(61);
		
		Switch sevenSw = (Switch) layout.getElementById(1006);
		
		seven = new Wayside(sevenBL, sevenEndBL, ctc, newInfo, sevenSw);
		controllerList.add(seven);
		
		
	}
}

