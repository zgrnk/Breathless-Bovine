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

	public static final boolean RED_LINE = false;
	public static final boolean GREEN_LINE = true;
	public boolean currentLine;
	public ArrayList<Wayside> redControllerList;
	public ArrayList<Switch> redSwitchList;
	public ArrayList<Block> redBlockList;
	public ArrayList<Wayside> greenControllerList;
	public ArrayList<Switch> greenSwitchList;
	public ArrayList<Block> greenBlockList;
	public TrackLayout layout;
	public CTCOffice ctc;
	
	/**
	 * 
	 * @param lyt
	 * @param ctc
	 */
	public TrackController(TrackLayout lyt, CTCOffice ctc) {
		this.layout = lyt;
		this.ctc = ctc;
		this.redControllerList = new ArrayList<Wayside>();
		this.greenControllerList = new ArrayList<Wayside>();
		initControllers();
	}
	
	public void nextTick() {
		int i = 0;
		for (Wayside way : redControllerList)
		{
			System.out.println("\nRed" + i);
			way.nextTick();
			i++;
		}
		i = 0;
		for (Wayside way : greenControllerList)
		{
			System.out.println("\nGreen" + i);
			way.nextTick();
			i++;
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
		redControllerList.add(one);
		
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
		redControllerList.add(two);
		
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
		redControllerList.add(three);
		
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
		fourBL.put(30, (Block) layout.getElementById(30));
		fourBL.put(31, (Block) layout.getElementById(31));
		fourBL.put(32, (Block) layout.getElementById(32));
		
		ArrayList<Integer> fourEndBL = new ArrayList<Integer>();
		fourEndBL.add(36);
		fourEndBL.add(75);
		fourEndBL.add(30);
		
		Switch fourSw = (Switch) layout.getElementById(1003);
		
		four = new Wayside(fourBL, fourEndBL, ctc, newInfo, fourSw);
		redControllerList.add(four);
		
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
		redControllerList.add(five);
		
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
		
		Component lightXRed = new Component(47,0);
		SafetyInfo newInfoXRed = new SafetyInfo(switchS, lightXRed, true);
		
		six = new Wayside(sixBL, sixEndBL, ctc, newInfoXRed, sixSw);
		redControllerList.add(six);
		
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
		redControllerList.add(seven);
		
		
		
		
		
		/*Initializing controllers for the green line*/
		boolean switchSG = Switch.STATE_DIVERGENT;
		Component lightSG = new Component(0,0);
		SafetyInfo newInfoG = new SafetyInfo(switchS, lightS, true);
		
		Wayside zeroG,oneG,twoG,threeG,fourG,fiveG;
		
		Hashtable<Integer, Block> zeroGBL = new Hashtable<Integer, Block>();
		zeroGBL.put(13, (Block) layout.greenLine.getElementById(13));
		zeroGBL.put(14, (Block) layout.greenLine.getElementById(14));
		zeroGBL.put(15, (Block) layout.greenLine.getElementById(15));
		zeroGBL.put(16, (Block) layout.greenLine.getElementById(16));
		zeroGBL.put(17, (Block) layout.greenLine.getElementById(17));
		zeroGBL.put(18, (Block) layout.greenLine.getElementById(18));
		zeroGBL.put(19, (Block) layout.greenLine.getElementById(19));
		zeroGBL.put(20, (Block) layout.greenLine.getElementById(20));
		
		zeroGBL.put(1, (Block) layout.greenLine.getElementById(1));
		zeroGBL.put(2, (Block) layout.greenLine.getElementById(2));
		zeroGBL.put(3, (Block) layout.greenLine.getElementById(3));
		zeroGBL.put(4, (Block) layout.greenLine.getElementById(4));
		zeroGBL.put(5, (Block) layout.greenLine.getElementById(5));
		zeroGBL.put(6, (Block) layout.greenLine.getElementById(6));
		
		zeroGBL.put(7, (Block) layout.greenLine.getElementById(7));
		zeroGBL.put(8, (Block) layout.greenLine.getElementById(8));
		zeroGBL.put(9, (Block) layout.greenLine.getElementById(9));
		zeroGBL.put(10, (Block) layout.greenLine.getElementById(10));
		zeroGBL.put(11, (Block) layout.greenLine.getElementById(11));
		zeroGBL.put(12, (Block) layout.greenLine.getElementById(12));
		
		ArrayList<Integer> zeroGEBL = new ArrayList<Integer>();
		zeroGEBL.add(7);
		zeroGEBL.add(11);
		zeroGEBL.add(20);
		
		Switch zeroGSW = (Switch) layout.greenLine.getElementById(1001);
		
		Component lightXGreen = new Component(19,0);
		SafetyInfo newInfoXGreen = new SafetyInfo(switchS, lightXGreen, true);
		
		zeroG = new Wayside(zeroGBL, zeroGEBL, ctc, newInfoXGreen, zeroGSW);
		greenControllerList.add(zeroG);
		
		
		
		Hashtable<Integer, Block> oneGBL = new Hashtable<Integer, Block>();
		oneGBL.put(28, (Block) layout.greenLine.getElementById(28));
		oneGBL.put(27, (Block) layout.greenLine.getElementById(27));
		oneGBL.put(26, (Block) layout.greenLine.getElementById(26));
		oneGBL.put(25, (Block) layout.greenLine.getElementById(25));
		oneGBL.put(24, (Block) layout.greenLine.getElementById(24));
		oneGBL.put(23, (Block) layout.greenLine.getElementById(23));
		oneGBL.put(22, (Block) layout.greenLine.getElementById(22));
		oneGBL.put(21, (Block) layout.greenLine.getElementById(21));
		oneGBL.put(20, (Block) layout.greenLine.getElementById(20));
		
		oneGBL.put(110, (Block) layout.greenLine.getElementById(110));
		oneGBL.put(111, (Block) layout.greenLine.getElementById(111));
		oneGBL.put(112, (Block) layout.greenLine.getElementById(112));
		oneGBL.put(113, (Block) layout.greenLine.getElementById(113));
		oneGBL.put(114, (Block) layout.greenLine.getElementById(114));
		oneGBL.put(115, (Block) layout.greenLine.getElementById(115));
		oneGBL.put(116, (Block) layout.greenLine.getElementById(116));
		oneGBL.put(117, (Block) layout.greenLine.getElementById(117));
		oneGBL.put(118, (Block) layout.greenLine.getElementById(118));
		oneGBL.put(119, (Block) layout.greenLine.getElementById(119));
		oneGBL.put(120, (Block) layout.greenLine.getElementById(120));
		oneGBL.put(121, (Block) layout.greenLine.getElementById(121));
		oneGBL.put(122, (Block) layout.greenLine.getElementById(122));
		oneGBL.put(123, (Block) layout.greenLine.getElementById(123));
		oneGBL.put(124, (Block) layout.greenLine.getElementById(124));
		oneGBL.put(125, (Block) layout.greenLine.getElementById(125));
		oneGBL.put(126, (Block) layout.greenLine.getElementById(126));
		oneGBL.put(127, (Block) layout.greenLine.getElementById(127));
		oneGBL.put(128, (Block) layout.greenLine.getElementById(128));
		oneGBL.put(129, (Block) layout.greenLine.getElementById(129));
		oneGBL.put(130, (Block) layout.greenLine.getElementById(130));
		oneGBL.put(131, (Block) layout.greenLine.getElementById(131));
		oneGBL.put(132, (Block) layout.greenLine.getElementById(132));
		oneGBL.put(133, (Block) layout.greenLine.getElementById(133));
		oneGBL.put(134, (Block) layout.greenLine.getElementById(134));
		oneGBL.put(135, (Block) layout.greenLine.getElementById(135));
		oneGBL.put(136, (Block) layout.greenLine.getElementById(136));
		oneGBL.put(137, (Block) layout.greenLine.getElementById(137));
		oneGBL.put(138, (Block) layout.greenLine.getElementById(138));
		oneGBL.put(139, (Block) layout.greenLine.getElementById(139));
		oneGBL.put(140, (Block) layout.greenLine.getElementById(140));
		oneGBL.put(141, (Block) layout.greenLine.getElementById(141));
		oneGBL.put(142, (Block) layout.greenLine.getElementById(142));
		oneGBL.put(143, (Block) layout.greenLine.getElementById(143));
		oneGBL.put(144, (Block) layout.greenLine.getElementById(144));
		oneGBL.put(145, (Block) layout.greenLine.getElementById(145));
		oneGBL.put(146, (Block) layout.greenLine.getElementById(146));
		oneGBL.put(147, (Block) layout.greenLine.getElementById(147));
		oneGBL.put(148, (Block) layout.greenLine.getElementById(148));
		oneGBL.put(149, (Block) layout.greenLine.getElementById(149));
		oneGBL.put(150, (Block) layout.greenLine.getElementById(150));
		
		oneGBL.put(29, (Block) layout.greenLine.getElementById(29));
		oneGBL.put(30, (Block) layout.greenLine.getElementById(30));
		oneGBL.put(31, (Block) layout.greenLine.getElementById(31));
		oneGBL.put(32, (Block) layout.greenLine.getElementById(32));
		oneGBL.put(33, (Block) layout.greenLine.getElementById(33));
		oneGBL.put(34, (Block) layout.greenLine.getElementById(34));
		oneGBL.put(35, (Block) layout.greenLine.getElementById(35));
		oneGBL.put(36, (Block) layout.greenLine.getElementById(36));
		oneGBL.put(37, (Block) layout.greenLine.getElementById(37));
		oneGBL.put(38, (Block) layout.greenLine.getElementById(38));
		oneGBL.put(39, (Block) layout.greenLine.getElementById(39));
		oneGBL.put(40, (Block) layout.greenLine.getElementById(40));
		oneGBL.put(41, (Block) layout.greenLine.getElementById(41));
		oneGBL.put(42, (Block) layout.greenLine.getElementById(42));
		oneGBL.put(43, (Block) layout.greenLine.getElementById(43));
		oneGBL.put(44, (Block) layout.greenLine.getElementById(44));
		oneGBL.put(45, (Block) layout.greenLine.getElementById(45));
		
		ArrayList<Integer> oneGEBL = new ArrayList<Integer>();
		oneGEBL.add(110);
		oneGEBL.add(45);
		oneGEBL.add(20);
		
		Switch oneGSW = (Switch) layout.greenLine.getElementById(1002);
		
		oneG = new Wayside(oneGBL, oneGEBL, ctc, newInfoG, oneGSW);
		greenControllerList.add(oneG);
		
		
		
		Hashtable<Integer, Block> twoGBL = new Hashtable<Integer, Block>();
		twoGBL.put(45, (Block) layout.greenLine.getElementById(45));
		twoGBL.put(46, (Block) layout.greenLine.getElementById(46));
		twoGBL.put(47, (Block) layout.greenLine.getElementById(47));
		twoGBL.put(48, (Block) layout.greenLine.getElementById(48));
		twoGBL.put(49, (Block) layout.greenLine.getElementById(49));
		twoGBL.put(50, (Block) layout.greenLine.getElementById(50));
		twoGBL.put(51, (Block) layout.greenLine.getElementById(51));
		twoGBL.put(52, (Block) layout.greenLine.getElementById(52));
		twoGBL.put(53, (Block) layout.greenLine.getElementById(53));
		twoGBL.put(54, (Block) layout.greenLine.getElementById(54));
		twoGBL.put(55, (Block) layout.greenLine.getElementById(55));
		twoGBL.put(56, (Block) layout.greenLine.getElementById(56));
		twoGBL.put(57, (Block) layout.greenLine.getElementById(57));
		
		twoGBL.put(0, (Block) layout.greenLine.getElementById(0));
		
		twoGBL.put(58, (Block) layout.greenLine.getElementById(58));
		twoGBL.put(59, (Block) layout.greenLine.getElementById(59));
		twoGBL.put(60, (Block) layout.greenLine.getElementById(60));
		
		
		ArrayList<Integer> twoGEBL = new ArrayList<Integer>();
		twoGEBL.add(60);
		twoGEBL.add(0);
		twoGEBL.add(45);
		
		Switch twoGSW = (Switch) layout.greenLine.getElementById(1003);
		
		twoG = new Wayside(twoGBL, twoGEBL, ctc, newInfoG, twoGSW);
		greenControllerList.add(twoG);
		
		
		
		Hashtable<Integer, Block> threeGBL = new Hashtable<Integer, Block>();
		threeGBL.put(63, (Block) layout.greenLine.getElementById(63));
		threeGBL.put(64, (Block) layout.greenLine.getElementById(64));
		threeGBL.put(65, (Block) layout.greenLine.getElementById(65));
		threeGBL.put(66, (Block) layout.greenLine.getElementById(66));
		threeGBL.put(67, (Block) layout.greenLine.getElementById(67));
		threeGBL.put(68, (Block) layout.greenLine.getElementById(68));
		threeGBL.put(69, (Block) layout.greenLine.getElementById(69));
		threeGBL.put(70, (Block) layout.greenLine.getElementById(70));
		threeGBL.put(71, (Block) layout.greenLine.getElementById(71));
		threeGBL.put(72, (Block) layout.greenLine.getElementById(72));
		threeGBL.put(73, (Block) layout.greenLine.getElementById(73));
		
		threeGBL.put(62, (Block) layout.greenLine.getElementById(62));
		threeGBL.put(61, (Block) layout.greenLine.getElementById(61));
		threeGBL.put(60, (Block) layout.greenLine.getElementById(60));
		
		threeGBL.put(0, (Block) layout.greenLine.getElementById(0));
		
		ArrayList<Integer> threeGEBL = new ArrayList<Integer>();
		threeGEBL.add(60);
		threeGEBL.add(0);
		threeGEBL.add(73);
		
		Switch threeGSW = (Switch) layout.greenLine.getElementById(1004);
		
		threeG = new Wayside(threeGBL, threeGEBL, ctc, newInfoG, threeGSW);
		greenControllerList.add(threeG);
		
		Hashtable<Integer, Block> fourGBL = new Hashtable<Integer, Block>();
		fourGBL.put(77, (Block) layout.greenLine.getElementById(77));
		fourGBL.put(78, (Block) layout.greenLine.getElementById(78));
		fourGBL.put(79, (Block) layout.greenLine.getElementById(79));
		fourGBL.put(80, (Block) layout.greenLine.getElementById(80));
		fourGBL.put(81, (Block) layout.greenLine.getElementById(81));
		fourGBL.put(82, (Block) layout.greenLine.getElementById(82));
		
		fourGBL.put(101, (Block) layout.greenLine.getElementById(101));
		fourGBL.put(102, (Block) layout.greenLine.getElementById(102));
		fourGBL.put(103, (Block) layout.greenLine.getElementById(103));
		fourGBL.put(104, (Block) layout.greenLine.getElementById(104));
		fourGBL.put(104, (Block) layout.greenLine.getElementById(105));
		fourGBL.put(106, (Block) layout.greenLine.getElementById(106));
		fourGBL.put(107, (Block) layout.greenLine.getElementById(107));
		fourGBL.put(108, (Block) layout.greenLine.getElementById(108));
		fourGBL.put(109, (Block) layout.greenLine.getElementById(109));
		fourGBL.put(110, (Block) layout.greenLine.getElementById(110));
		
		fourGBL.put(76, (Block) layout.greenLine.getElementById(76));
		fourGBL.put(75, (Block) layout.greenLine.getElementById(75));
		fourGBL.put(74, (Block) layout.greenLine.getElementById(74));
		fourGBL.put(73, (Block) layout.greenLine.getElementById(73));
		
		ArrayList<Integer> fourGEBL = new ArrayList<Integer>();
		fourGEBL.add(110);
		fourGEBL.add(82);
		fourGEBL.add(73);
		
		Switch fourGSW = (Switch) layout.greenLine.getElementById(1005);
		
		fourG = new Wayside(fourGBL, fourGEBL, ctc, newInfoG, fourGSW);
		greenControllerList.add(fourG);
		
		
		
		Hashtable<Integer, Block> fiveGBL = new Hashtable<Integer, Block>();
		fiveGBL.put(85, (Block) layout.greenLine.getElementById(85));
		fiveGBL.put(84, (Block) layout.greenLine.getElementById(84));
		fiveGBL.put(83, (Block) layout.greenLine.getElementById(83));
		fiveGBL.put(82, (Block) layout.greenLine.getElementById(82));

		fiveGBL.put(94, (Block) layout.greenLine.getElementById(94));
		fiveGBL.put(95, (Block) layout.greenLine.getElementById(95));
		fiveGBL.put(96, (Block) layout.greenLine.getElementById(96));
		fiveGBL.put(97, (Block) layout.greenLine.getElementById(97));
		fiveGBL.put(98, (Block) layout.greenLine.getElementById(98));
		fiveGBL.put(99, (Block) layout.greenLine.getElementById(99));
		fiveGBL.put(100, (Block) layout.greenLine.getElementById(100));

		fiveGBL.put(86, (Block) layout.greenLine.getElementById(86));
		fiveGBL.put(87, (Block) layout.greenLine.getElementById(87));
		fiveGBL.put(88, (Block) layout.greenLine.getElementById(88));
		fiveGBL.put(89, (Block) layout.greenLine.getElementById(89));
		fiveGBL.put(90, (Block) layout.greenLine.getElementById(90));
		fiveGBL.put(91, (Block) layout.greenLine.getElementById(91));
		fiveGBL.put(92, (Block) layout.greenLine.getElementById(92));
		fiveGBL.put(93, (Block) layout.greenLine.getElementById(93));
		
		ArrayList<Integer> fiveGEBL = new ArrayList<Integer>();
		fiveGEBL.add(82);
		fiveGEBL.add(93);
		fiveGEBL.add(94);
		
		Switch fiveGSW = (Switch) layout.greenLine.getElementById(1006);
		
		fiveG = new Wayside(fiveGBL, fiveGEBL, ctc, newInfoG, fiveGSW);
		greenControllerList.add(fiveG);
		
	}
}

