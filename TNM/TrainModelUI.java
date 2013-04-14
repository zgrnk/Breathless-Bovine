package TNM;
import TKM.*;
import TNC.*;

/*
XXX
change resolution of screen so that both popups fully appear
handle case if train starts rolling backwards while attempting to go uphill
	train position may needchanged to prevBlock and routeIndex--
when train moves out of block, don't want to necessarily change isOccupied to false since there may be another train in the block
handle crashing of trains?...
XXX
*/





import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.BorderFactory; 
import javax.swing.border.Border;





public class TrainModelUI
{
	private static boolean isSolo = false;
	private static int soloNumTrains = 0;
	private static double soloTime = 0;
	private static int soloDelta = 100;
	private static int refreshUI = 0;
	
	protected static ArrayList<Train> trainList;
	protected static int selectedId;
	protected static String[] idArray;
	protected static boolean isPaused;
	protected static boolean isVisible;
	protected static boolean isVisibleStatic;
	
	// Main JFrames
	protected static JFrame dynamicWindow;
	protected static JFrame staticWindow;
	Border borderline = BorderFactory.createLineBorder(Color.black, 2);
	
	// dynamicWindow JButtons
	protected static JButton btnShowStaticValues;
	protected static JButton btnSelectTrain;
	protected static JButton btnPauseResume;
	protected static JButton btnSetManRecPower;
	protected static JButton btnToggleManRecPower;
	protected static JButton btnSetManDesSpdLmt;
	protected static JButton btnToggleManDesSpdLmt;
	protected static JButton btnToggleSignalPickupFailure;
	protected static JButton btnToggleEngineFailure;
	protected static JButton btnToggleBrakeFailure;
	protected static JButton btnToggleServiceBrake;
	protected static JButton btnToggleEmergencyBrake;
	protected static JButton btnSetManLights;
	protected static JButton btnToggleManLights;
	protected static JButton btnSetManDoors;
	protected static JButton btnToggleManDoors;
	protected static JButton btnSetManTarTemperature;
	protected static JButton btnToggleManTarTemperature;
	
	// dynamicWindow JLabels
	protected static JLabel jlTime;
	protected static JLabel jlCurVel;
	protected static JLabel jlCurAccel;
	protected static JLabel jlRecPowerTNC;
	protected static JLabel jlManRecPower;
	protected static JLabel jlToggleManRecPower;
	protected static JLabel jlPostedSpdLmt;
	protected static JLabel jlManDesSpdLmt;
	protected static JLabel jlToggleManDesSpdLmt;
	protected static JLabel jlGrade;
	protected static JLabel jlTotalMass;
	protected static JLabel jlPassengerCount;
	protected static JLabel jlCrewCount;
	protected static JLabel jlPosition;
	protected static JLabel jlToggleSignalPickupFailure;
	protected static JLabel jlToggleEngineFailure;
	protected static JLabel jlToggleBrakeFailure;
	protected static JLabel jlToggleServiceBrake;
	protected static JLabel jlToggleEmergencyBrake;
	protected static JLabel jlLights;
	protected static JLabel jlManLights;
	protected static JLabel jlToggleManLights;
	protected static JLabel jlDoors;
	protected static JLabel jlManDoors;
	protected static JLabel jlToggleManDoors;
	protected static JLabel jlCurTemperature;
	protected static JLabel jlTarTemperature;
	protected static JLabel jlManTarTemperature;
	protected static JLabel jlToggleManTarTemperature;
	protected static JLabel jlAnnouncement;
	
	// staticWindow JLabels
	protected static JLabel jlLength;
	protected static JLabel jlWidth;
	protected static JLabel jlHeight;
	protected static JLabel jlNumCars;
	protected static JLabel jlMotorPower;
	protected static JLabel jlMaxSpeed;
	protected static JLabel jlServiceBrakeDecel;
	protected static JLabel jlEmergencyBrakeDecel;
	protected static JLabel jlFrictionCoeff;
	protected static JLabel jlEmptyTrainMass;
	protected static JLabel jlPersonMass;
	protected static JLabel jlMaxSeatedCount;
	protected static JLabel jlMaxStandingCount;
	protected static JLabel jlMaxCrewCount;
	
	
	
	public TrainModelUI()
	{
		try
		{
			JPanel emptyJPanel = new JPanel();
			emptyJPanel.add(new JLabel("                                                                                                                                                                                                                                                                                                                                           "));
			isPaused = true;
			
			
			
			//////////////////////////////////////////////////////////////////////////////////////////////////
			// Setup the dynamicWindow.
			
			btnShowStaticValues = buildJButton("Show Static Values");
			btnSelectTrain = buildJButton("Select Train");
			btnPauseResume = buildJButton("Pause");
			btnSetManRecPower = buildJButton("Set Manual Received Power");
			btnToggleManRecPower = buildJButton("Toggle Manual Received Power");
			btnSetManDesSpdLmt = buildJButton("Set Manual Desired Speed Limit");
			btnToggleManDesSpdLmt = buildJButton("Toggle Manual Desired Speed Limit");
			btnToggleSignalPickupFailure = buildJButton("Toggle Signal Pickup Failure");
			btnToggleEngineFailure = buildJButton("Toggle Engine Failure");
			btnToggleBrakeFailure = buildJButton("Toggle Brake Failure");
			btnToggleServiceBrake = buildJButton("Toggle Service Brake");
			btnToggleEmergencyBrake = buildJButton("Toggle Emergency Brake");
			btnSetManLights = buildJButton("Set Manual Lights Status");
			btnToggleManLights = buildJButton("Toggle Manual Lights Status");
			btnSetManDoors = buildJButton("Set Manual Doors Status");
			btnToggleManDoors = buildJButton("Toggle Manual Doors Status");
			btnSetManTarTemperature = buildJButton("Set Manual Target Temp.");
			btnToggleManTarTemperature = buildJButton("Toggle Manual Target Temp.");
			
			jlTime = new JLabel("XX:XX:XX", JLabel.CENTER);
			jlCurVel = new JLabel("", JLabel.CENTER);
			jlCurAccel = new JLabel("", JLabel.CENTER);
			jlRecPowerTNC = new JLabel("", JLabel.CENTER);
			jlManRecPower = new JLabel("", JLabel.CENTER);
			jlToggleManRecPower = new JLabel("", JLabel.CENTER);
			jlPostedSpdLmt = new JLabel("", JLabel.CENTER);
			jlManDesSpdLmt = new JLabel("", JLabel.CENTER);
			jlToggleManDesSpdLmt = new JLabel("", JLabel.CENTER);
			jlGrade = new JLabel("", JLabel.CENTER);
			jlTotalMass = new JLabel("", JLabel.CENTER);
			jlPassengerCount = new JLabel("", JLabel.CENTER);
			jlCrewCount = new JLabel("", JLabel.CENTER);
			jlPosition = new JLabel("", JLabel.CENTER);
			jlToggleSignalPickupFailure = new JLabel("", JLabel.CENTER);
			jlToggleEngineFailure = new JLabel("", JLabel.CENTER);
			jlToggleBrakeFailure = new JLabel("", JLabel.CENTER);
			jlToggleServiceBrake = new JLabel("", JLabel.CENTER);
			jlToggleEmergencyBrake = new JLabel("", JLabel.CENTER);
			jlLights = new JLabel("", JLabel.CENTER);
			jlManLights = new JLabel("", JLabel.CENTER);
			jlToggleManLights = new JLabel("", JLabel.CENTER);
			jlDoors = new JLabel("", JLabel.CENTER);
			jlManDoors = new JLabel("", JLabel.CENTER);
			jlToggleManDoors = new JLabel("", JLabel.CENTER);
			jlCurTemperature = new JLabel("", JLabel.CENTER);
			jlTarTemperature = new JLabel("", JLabel.CENTER);
			jlManTarTemperature = new JLabel("", JLabel.CENTER);
			jlToggleManTarTemperature = new JLabel("", JLabel.CENTER);
			jlAnnouncement = new JLabel("", JLabel.CENTER);
			
			JPanel dwPanel1 = new JPanel();
			dwPanel1.setLayout(new GridLayout(18, 2));
			dwPanel1.add(buildJPanel(new JLabel("Current Velocity (m/s)", JLabel.CENTER)));
			dwPanel1.add(buildJPanel(jlCurVel));
			dwPanel1.add(buildJPanel(new JLabel("Current Acceleration (m/s^2)", JLabel.CENTER)));
			dwPanel1.add(buildJPanel(jlCurAccel));
			dwPanel1.add(buildJPanel(new JLabel("Received Power from TNC (W)", JLabel.CENTER)));
			dwPanel1.add(buildJPanel(jlRecPowerTNC));
			dwPanel1.add(buildJPanel(btnSetManRecPower));
			dwPanel1.add(buildJPanel(jlManRecPower));
			dwPanel1.add(buildJPanel(btnToggleManRecPower));
			dwPanel1.add(buildJPanel(jlToggleManRecPower));
			dwPanel1.add(buildJPanel(new JLabel("Speed Limit Posted on Signs (km/hr)", JLabel.CENTER)));
			dwPanel1.add(buildJPanel(jlPostedSpdLmt));
			dwPanel1.add(buildJPanel(btnSetManDesSpdLmt));
			dwPanel1.add(buildJPanel(jlManDesSpdLmt));
			dwPanel1.add(buildJPanel(btnToggleManDesSpdLmt));
			dwPanel1.add(buildJPanel(jlToggleManDesSpdLmt));
			dwPanel1.add(buildJPanel(new JLabel("Relative Grade from TKM (%)", JLabel.CENTER)));
			dwPanel1.add(buildJPanel(jlGrade));
			dwPanel1.add(buildJPanel(new JLabel("Total Mass (including passengers/crew) (kg)", JLabel.CENTER)));
			dwPanel1.add(buildJPanel(jlTotalMass));
			dwPanel1.add(buildJPanel(new JLabel("Passenger Count", JLabel.CENTER)));
			dwPanel1.add(buildJPanel(jlPassengerCount));
			dwPanel1.add(buildJPanel(new JLabel("Crew Count", JLabel.CENTER)));
			dwPanel1.add(buildJPanel(jlCrewCount));
			dwPanel1.add(buildJPanel(new JLabel("Position from Onboard GPS ([current_block], m)", JLabel.CENTER)));
			dwPanel1.add(buildJPanel(jlPosition));
			dwPanel1.add(buildJPanel(btnToggleSignalPickupFailure));
			dwPanel1.add(buildJPanel(jlToggleSignalPickupFailure));
			dwPanel1.add(buildJPanel(btnToggleEngineFailure));
			dwPanel1.add(buildJPanel(jlToggleEngineFailure));
			dwPanel1.add(buildJPanel(btnToggleBrakeFailure));
			dwPanel1.add(buildJPanel(jlToggleBrakeFailure));
			dwPanel1.add(buildJPanel(btnToggleServiceBrake));
			dwPanel1.add(buildJPanel(jlToggleServiceBrake));
			dwPanel1.add(buildJPanel(btnToggleEmergencyBrake));
			dwPanel1.add(buildJPanel(jlToggleEmergencyBrake));
			JPanel dwPanel2 = new JPanel();
			dwPanel2.setLayout(new GridLayout(18, 2));
			dwPanel2.add(buildJPanel(new JLabel("Lights Status", JLabel.CENTER)));
			dwPanel2.add(buildJPanel(jlLights));
			dwPanel2.add(buildJPanel(btnSetManLights));
			dwPanel2.add(buildJPanel(jlManLights));
			dwPanel2.add(buildJPanel(btnToggleManLights));
			dwPanel2.add(buildJPanel(jlToggleManLights));
			dwPanel2.add(buildJPanel(new JLabel("Doors Status", JLabel.CENTER)));
			dwPanel2.add(buildJPanel(jlDoors));
			dwPanel2.add(buildJPanel(btnSetManDoors));
			dwPanel2.add(buildJPanel(jlManDoors));
			dwPanel2.add(buildJPanel(btnToggleManDoors));
			dwPanel2.add(buildJPanel(jlToggleManDoors));
			dwPanel2.add(buildJPanel(new JLabel("Current Temperature (degrees Celsius)", JLabel.CENTER)));
			dwPanel2.add(buildJPanel(jlCurTemperature));
			dwPanel2.add(buildJPanel(new JLabel("Target Temperature from TNC (degrees Celsius)", JLabel.CENTER)));
			dwPanel2.add(buildJPanel(jlTarTemperature));
			dwPanel2.add(buildJPanel(btnSetManTarTemperature));
			dwPanel2.add(buildJPanel(jlManTarTemperature));
			dwPanel2.add(buildJPanel(btnToggleManTarTemperature));
			dwPanel2.add(buildJPanel(jlToggleManTarTemperature));
			dwPanel2.add(buildJPanel(new JLabel("Announcement", JLabel.CENTER)));
			dwPanel2.add(buildJPanel(jlAnnouncement));
			
			JPanel primaryButtons = new JPanel();
			primaryButtons.setLayout(new GridLayout(1, 4));
			primaryButtons.add(btnShowStaticValues);
			primaryButtons.add(btnSelectTrain);
			primaryButtons.add(btnPauseResume);
			primaryButtons.add(buildJPanel(jlTime));
			
			JPanel jp = new JPanel();
			jp.add(primaryButtons);
			jp.add(emptyJPanel);
			jp.add(dwPanel1);
			jp.add(new JLabel("        "));
			jp.add(dwPanel2);
			
			dynamicWindow = new JFrame();
			dynamicWindow.setTitle("Train Model (Chris Paskie)   -   UI   (Train ID:   --)");
			dynamicWindow.setSize(1300, 700);
			dynamicWindow.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
			dynamicWindow.add(jp);
			isVisible = false;
			dynamicWindow.setVisible(isVisible);
			
			
			
			//////////////////////////////////////////////////////////////////////////////////////////////////
			// Setup the staticWindow.
			
			jlLength = new JLabel("", JLabel.CENTER);
			jlWidth = new JLabel("", JLabel.CENTER);
			jlHeight = new JLabel("", JLabel.CENTER);
			jlNumCars = new JLabel("", JLabel.CENTER);
			jlMotorPower = new JLabel("", JLabel.CENTER);
			jlMaxSpeed = new JLabel("", JLabel.CENTER);
			jlServiceBrakeDecel = new JLabel("", JLabel.CENTER);
			jlEmergencyBrakeDecel = new JLabel("", JLabel.CENTER);
			jlFrictionCoeff = new JLabel("", JLabel.CENTER);
			jlEmptyTrainMass = new JLabel("", JLabel.CENTER);
			jlPersonMass = new JLabel("", JLabel.CENTER);
			jlMaxSeatedCount = new JLabel("", JLabel.CENTER);
			jlMaxStandingCount = new JLabel("", JLabel.CENTER);
			jlMaxCrewCount = new JLabel("", JLabel.CENTER);
			
			JPanel swPanel = new JPanel();
			swPanel.setLayout(new GridLayout(14, 2));
			swPanel.add(buildJPanel(new JLabel("Length (m)", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlLength));
			swPanel.add(buildJPanel(new JLabel("Width (m)", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlWidth));
			swPanel.add(buildJPanel(new JLabel("Height (m)", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlHeight));
			swPanel.add(buildJPanel(new JLabel("Number of Cars", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlNumCars));
			swPanel.add(buildJPanel(new JLabel("Motor Power (W)", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlMotorPower));
			swPanel.add(buildJPanel(new JLabel("Maximum Speed (m/s)", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlMaxSpeed));
			swPanel.add(buildJPanel(new JLabel("Service Brake Deceleration (m/s^2)", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlServiceBrakeDecel));
			swPanel.add(buildJPanel(new JLabel("Emergency Brake Deceleration (m/s^2)", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlEmergencyBrakeDecel));
			swPanel.add(buildJPanel(new JLabel("Coefficient of Friction", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlFrictionCoeff));
			swPanel.add(buildJPanel(new JLabel("Train Mass (not including passengers/crew) (kg)", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlEmptyTrainMass));
			swPanel.add(buildJPanel(new JLabel("Mass Per Passenger/Crew (kg)", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlPersonMass));
			swPanel.add(buildJPanel(new JLabel("Maximum Seated Passenger Count", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlMaxSeatedCount));
			swPanel.add(buildJPanel(new JLabel("Maximum Standing Passenger Count", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlMaxStandingCount));
			swPanel.add(buildJPanel(new JLabel("Maximum Crew Count", JLabel.CENTER)));
			swPanel.add(buildJPanel(jlMaxCrewCount));
			
			JScrollPane staticJSP = new JScrollPane(swPanel);
			
			staticWindow = new JFrame();
			staticWindow.setTitle("Train Model (Chris Paskie)   -   Static Values   (Train ID:   --)");
			staticWindow.setSize(700, 600);
			staticWindow.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
			staticWindow.add(staticJSP);
			isVisibleStatic = false;
			staticWindow.setVisible(isVisibleStatic);
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public JButton buildJButton(String text)
	{
		JButton jb = new JButton(text);
		jb.addActionListener(new TrainModelButtonListener());
		jb.setEnabled(true);
		return jb;
	}
	
	public JPanel buildJPanel(JComponent jc)
	{
		JPanel jp = new JPanel(new GridLayout(1, 1), false);
		jp.add(jc);
		jp.setBorder(borderline);
		
		JPanel container  = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(jp);
		return container;
	}
	
	public ArrayList<Train> getTrainList()
	{
		return trainList;
	}
	
	public boolean getIsPaused()
	{
		return isPaused;
	}
	
	public boolean getIsVisible()
	{
		return isVisible;
	}
	
	public boolean getIsVisibleStatic()
	{
		return isVisibleStatic;
	}
	
	public void setTrainList(ArrayList<Train> trainList)
	{
		this.trainList = trainList;
		if (trainList != null  &&  trainList.size() > 0)
		{
			idArray = new String[trainList.size()];
			for (int i=0; i<trainList.size(); i++)
				idArray[i] = new String(trainList.get(i).getStringId());
		}
		else
			idArray = null;
	}
	
	public void setIsPaused(boolean isPaused)
	{
		this.isPaused = isPaused;
		if (isPaused)
			btnPauseResume.setText("Resume");
		else
			btnPauseResume.setText("Pause");
	}
	
	public void setIsVisible(boolean isVisible)
	{
		this.isVisible = isVisible;
		dynamicWindow.setVisible(isVisible);
	}
	
	public void setIsVisibleStatic(boolean isVisibleStatic)
	{
		this.isVisibleStatic = isVisibleStatic;
		staticWindow.setVisible(isVisibleStatic);
	}
	
	public static void setSelectedId(int selId)
	{
		selectedId = selId;
		dynamicWindow.setTitle("Train Model (Chris Paskie)   -   UI   (Train ID:   " + trainList.get(selectedId-1).getStringId() + ")");
		staticWindow.setTitle("Train Model (Chris Paskie)   -   Static Values   (Train ID:   " + trainList.get(selectedId-1).getStringId() + ")");
		int hrs = (int)soloTime / (60 * 60);
		int min = ((int)soloTime / 60) % 60;
		int sec = (int)soloTime - (hrs * 60 * 60 + min * 60);
		jlTime.setText((hrs < 10 ? "0" : "") + hrs + ":" + (min < 10 ? "0" : "") + min + ":" + (sec < 10 ? "0" : "") + sec);
		
		jlCurVel.setText("" + trainList.get(selectedId-1).getCurVelocity());
		jlCurAccel.setText("" + trainList.get(selectedId-1).getCurAccel());
		jlRecPowerTNC.setText("" + trainList.get(selectedId-1).getReceivedPower());
		jlManRecPower.setText("" + trainList.get(selectedId-1).getManualPower());
		jlToggleManRecPower.setText("" + trainList.get(selectedId-1).getIssetManualPower());
		jlPostedSpdLmt.setText("" + trainList.get(selectedId-1).getPostedSpeedLimit());
		jlManDesSpdLmt.setText("" + trainList.get(selectedId-1).getManualSpeedLimit());
		jlToggleManDesSpdLmt.setText("" + trainList.get(selectedId-1).getIssetManualSpeedLimit());
		jlGrade.setText("" + trainList.get(selectedId-1).getPositionBlock().grade);
		jlTotalMass.setText("" + trainList.get(selectedId-1).getTotalMass());
		jlPassengerCount.setText("" + trainList.get(selectedId-1).getNumPassengers());
		jlCrewCount.setText("" + trainList.get(selectedId-1).getNumCrew());
		jlPosition.setText("" + ((trainList.get(selectedId-1).getIssetSignalPickupFailure()) ? "???????" : ("[ " + trainList.get(selectedId-1).getPositionBlock().id + " , " + trainList.get(selectedId-1).getPositionMeters() + " ]")));
		jlToggleSignalPickupFailure.setText("" + trainList.get(selectedId-1).getIssetSignalPickupFailure());
		jlToggleEngineFailure.setText("" + trainList.get(selectedId-1).getIssetEngineFailure());
		jlToggleBrakeFailure.setText("" + trainList.get(selectedId-1).getIssetBrakeFailure());
		jlToggleServiceBrake.setText("" + trainList.get(selectedId-1).getIssetServiceBrake());
		jlToggleEmergencyBrake.setText("" + trainList.get(selectedId-1).getIssetEmerBrake());
		jlLights.setText("" + (trainList.get(selectedId-1).getIssetLightsOn() ? "On" : "Off"));
		jlManLights.setText("" + (trainList.get(selectedId-1).getIssetLightsOnManual() ? "On" : "Off"));
		jlToggleManLights.setText("" + trainList.get(selectedId-1).getIssetLightsOnUseManual());
		jlDoors.setText("" + (trainList.get(selectedId-1).getIssetDoorsOpen() ? "Open" : "Closed"));
		jlManDoors.setText("" + (trainList.get(selectedId-1).getIssetDoorsOpenManual() ? "Open" : "Closed"));
		jlToggleManDoors.setText("" + trainList.get(selectedId-1).getIssetDoorsOpenUseManual());
		jlCurTemperature.setText("" + trainList.get(selectedId-1).getCurTemperature());
		jlTarTemperature.setText("" + trainList.get(selectedId-1).getTargetTemperatureTNC());
		jlManTarTemperature.setText("" + trainList.get(selectedId-1).getTargetTemperatureManual());
		jlToggleManTarTemperature.setText("" + trainList.get(selectedId-1).getIssetTargetTemperatureManual());
		jlAnnouncement.setText("" + trainList.get(selectedId-1).getAnnouncement());
		
		jlLength.setText("" + trainList.get(selectedId-1).getLength());
		jlWidth.setText("" + trainList.get(selectedId-1).getWidth());
		jlHeight.setText("" + trainList.get(selectedId-1).getHeight());
		jlNumCars.setText("" + trainList.get(selectedId-1).getNumCars());
		jlMotorPower.setText("" + trainList.get(selectedId-1).getMotorPower());
		jlMaxSpeed.setText("" + trainList.get(selectedId-1).getMaxSpeed());
		jlServiceBrakeDecel.setText("" + trainList.get(selectedId-1).getServiceBrakeDecel());
		jlEmergencyBrakeDecel.setText("" + trainList.get(selectedId-1).getEmerBrakeDecel());
		jlFrictionCoeff.setText("" + trainList.get(selectedId-1).getFrictionCoeff());
		jlEmptyTrainMass.setText("" + trainList.get(selectedId-1).getEmptyTrainMass());
		jlPersonMass.setText("" + trainList.get(selectedId-1).getPersonMass());
		jlMaxSeatedCount.setText("" + trainList.get(selectedId-1).getMaxCapacitySeated());
		jlMaxStandingCount.setText("" + trainList.get(selectedId-1).getMaxCapacityStanding());
		jlMaxCrewCount.setText("" + trainList.get(selectedId-1).getMaxCapacityCrew());
	}
	
	public ArrayList<Train> getTrainsInBlock(int id)
	{
		ArrayList<Train> tempAL = new ArrayList<Train>();
		for (int i=0; i<trainList.size(); i++)
		{
			if (trainList.get(i).getId() == id)
				tempAL.add(trainList.get(i));
		}
		return tempAL;
	}
	
	public static void timeTick(double time, int period, int delta)
	{
		if (!isPaused)
		{
			refreshUI += soloDelta;
			for (int i=0; i<trainList.size(); i++)
				trainList.get(i).timeTick(time, ((double)(period))*delta/1000.0, false);
				// YYY - trainList.get(i).timeTick(time, ((double)(period))*delta/1000.0, isSolo);
			
			if (refreshUI >= 1000)
			{
				if (isSolo)
				{
					soloTime += period;
					if (soloTime >= 24*60*60)
						soloTime = soloTime % (24*60*60);
				}
				else
					soloTime = time;
				
				refreshUI = refreshUI % 1000;
				setSelectedId(selectedId);
			}
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			isSolo = true;
			
			// Get the number of trains.
			if (args.length != 1)
			{
				System.out.println("Invalid number of arguments.");
				System.exit(1);
			}
			soloNumTrains = Integer.parseInt(args[0], 10);
			if (soloNumTrains < 1  ||  soloNumTrains > 9)
			{
				System.out.println("Invalid argument. The number of trains must be greater than 0 and less than 10.");
				System.exit(1);
			}
			
			// Create the test block loop.
			ArrayList<Block> route = new ArrayList<Block>();
			Block bYard = new Block(0, 100.0, 0.0, true, 5.0, null, null, null, null, true, false, "Welcome to the Yard!", false, false, false, false, false, 0, 0.0, 0.0, 0.0, true);
			Block b01 = new Block(1, 500.0, 0.0, true, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b02 = new Block(2, 500.0, -22.2, true, 1.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b03 = new Block(3, 50.0, 0.0, true, 15.0, null, null, null, null, false, false, "", false, false, false, true, false, 0, 0.0, 0.0, 0.0, false);
			Block b04 = new Block(4, 500.0, 11.1, true, 30.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b05 = new Block(5, 50.0, 0.0, true, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b06 = new Block(6, 100.0, 0.0, true, 5.0, null, null, null, null, false, true, "Welcome to Station Alpha!", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b07 = new Block(7, 500.0, -11.1, false, 30.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b08 = new Block(8, 50.0, 0.0, false, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b09 = new Block(9, 500.0, 22.2, false, 1.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b10 = new Block(10, 50.0, 0.0, false, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b11 = new Block(11, 100.0, 0.0, false, 5.0, null, null, null, null, false, true, "Welcome to Station Beta!", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b12 = new Block(12, 50.0, 11.1, true, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b13 = new Block(13, 50.0, 0.0, false, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b14 = new Block(14, 50.0, -11.1, true, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b15 = new Block(15, 50.0, 0.0, false, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b16 = new Block(16, 100.0, 0.0, true, 5.0, null, null, null, null, false, true, "Welcome to Station Gamma!", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			Block b17 = new Block(17, 50.0, 0.0, false, 15.0, null, null, null, null, false, false, "", false, false, false, false, false, 0, 0.0, 0.0, 0.0, false);
			
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
			
			// Create the trains.
			trainList = new ArrayList<Train>();
			idArray = new String[soloNumTrains];
			for (int i=0; i<soloNumTrains; i++)
			{
				trainList.add(new Train(i+1, "T"+(i+1), "Test", (8*60*60+i*30*60)%(24*60*60), route, new Engineer(true, false, 0.0, (8*60*60+i*30*60+4*60*60)%(24*60*60)), bYard));
				idArray[i] = new String("T"+(i+1));
			}
			
			// Create the UI.
			TrainModelUI tnmUI = new TrainModelUI();
			tnmUI.setTrainList(trainList);
			
			// Setup the timer.
			soloTime = 7*60*60+59*60+30;
			ActionListener taskPerformer = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					timeTick(soloTime, 5, soloDelta);
				}
			};
			new javax.swing.Timer(soloDelta, taskPerformer).start();
			
			/* YYY
			// Disable all "Toggle" buttons that are associated with a manual entry.
			btnToggleManRecPower.setEnabled(false);
			btnToggleManDesSpdLmt.setEnabled(false);
			btnToggleManLights.setEnabled(false);
			btnToggleManDoors.setEnabled(false);
			btnToggleManTarTemperature.setEnabled(false);
			
			// Make it so only manual values are used.
			for (int i=0; i<soloNumTrains; i++)
			{
				Train t = trainList.get(i);
				t.setIssetManualPower(true);
				t.setIssetManualSpeedLimit(true);
				t.setIssetLightsOnUseManual(true);
				t.setIssetDoorsOpenUseManual(true);
				t.setIssetTargetTemperatureManual(true);
			}
			YYY */
			
			tnmUI.setSelectedId(trainList.get(0).getId());
			tnmUI.setIsPaused(tnmUI.getIsPaused());
			tnmUI.setIsVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace(System.err);
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	
	
	private class TrainModelButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent aEvent)
		{
			try
			{
				// Show Static Values
				if (aEvent.getActionCommand() == "Show Static Values")
				{
					staticWindow.setVisible(true);
				}
				
				// Select Train
				else if (aEvent.getActionCommand() == "Select Train")
				{
					try
					{
						int intId = 1;
						String tempId = (String)JOptionPane.showInputDialog(null, "", "Train Model - Select Train", JOptionPane.QUESTION_MESSAGE, null, idArray, null);
						if (tempId != null)
						{
							int i;
							for (i=0; i<trainList.size(); i++)
							{
								if (trainList.get(i).getStringId().equals(tempId))
									break;
							}
							setSelectedId(i+1);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace(System.err);
						JOptionPane.showMessageDialog(null, "Invalid input.", "Train Model - Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
				
				// Pause / Resume
				else if (aEvent.getActionCommand() == "Pause"  ||  aEvent.getActionCommand() == "Resume")
				{
					setIsPaused(!isPaused);
				}
				
				// Set Manual Received Power
				else if (aEvent.getActionCommand() == "Set Manual Received Power")
				{
					try
					{
						String tempManRecPower = (String)JOptionPane.showInputDialog(null, "Enter the received power (W) (number only):", "Train Model - Set Received Power", JOptionPane.QUESTION_MESSAGE);
						if (tempManRecPower != null)
						{
							trainList.get(selectedId-1).setManualPower(Double.parseDouble(tempManRecPower));
							jlManRecPower.setText("" + trainList.get(selectedId-1).getManualPower());
						}
					}
					catch(Exception e)
					{
						e.printStackTrace(System.err);
						JOptionPane.showMessageDialog(null, "Invalid received power value entered.", "Train Model - Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
				
				// Toggle Manual Received Power
				else if (aEvent.getActionCommand() == "Toggle Manual Received Power")
				{
					trainList.get(selectedId-1).setIssetManualPower(!trainList.get(selectedId-1).getIssetManualPower());
					jlToggleManRecPower.setText("" + trainList.get(selectedId-1).getIssetManualPower());
				}
				
				// Set Manual Desired Speed Limit
				else if (aEvent.getActionCommand() == "Set Manual Desired Speed Limit")
				{
					try
					{
						String tempManDesSpdLmt = (String)JOptionPane.showInputDialog(null, "Enter the desired speed limit (km/hr) (number only):", "Train Model - Set Desired Speed Limit", JOptionPane.QUESTION_MESSAGE);
						if (tempManDesSpdLmt != null)
						{
							trainList.get(selectedId-1).setManualSpeedLimit(Double.parseDouble(tempManDesSpdLmt));
							jlManDesSpdLmt.setText("" + trainList.get(selectedId-1).getManualSpeedLimit());
						}
					}
					catch(Exception e)
					{
						e.printStackTrace(System.err);
						JOptionPane.showMessageDialog(null, "Invalid desired speed limit entered.", "Train Model - Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
				
				// Toggle Manual Desired Speed Limit
				else if (aEvent.getActionCommand() == "Toggle Manual Desired Speed Limit")
				{
					trainList.get(selectedId-1).setIssetManualSpeedLimit(!trainList.get(selectedId-1).getIssetManualSpeedLimit());
					jlToggleManDesSpdLmt.setText("" + trainList.get(selectedId-1).getIssetManualSpeedLimit());
				}
				
				// Toggle Signal Pickup Failure
				else if (aEvent.getActionCommand() == "Toggle Signal Pickup Failure")
				{
					trainList.get(selectedId-1).setIssetSignalPickupFailure(!trainList.get(selectedId-1).getIssetSignalPickupFailure());
					jlToggleSignalPickupFailure.setText("" + trainList.get(selectedId-1).getIssetSignalPickupFailure());
					jlPosition.setText("" + ((trainList.get(selectedId-1).getIssetSignalPickupFailure()) ? "???????" : ("[ " + trainList.get(selectedId-1).getPositionBlock().id + " , " + trainList.get(selectedId-1).getPositionMeters() + " ]")));
				}
				
				// Toggle Engine Failure
				else if (aEvent.getActionCommand() == "Toggle Engine Failure")
				{
					trainList.get(selectedId-1).setIssetEngineFailure(!trainList.get(selectedId-1).getIssetEngineFailure());
					jlToggleEngineFailure.setText("" + trainList.get(selectedId-1).getIssetEngineFailure());
				}
				
				// Toggle Brake Failure
				else if (aEvent.getActionCommand() == "Toggle Brake Failure")
				{
					trainList.get(selectedId-1).setIssetBrakeFailure(!trainList.get(selectedId-1).getIssetBrakeFailure());
					jlToggleBrakeFailure.setText("" + trainList.get(selectedId-1).getIssetBrakeFailure());
				}
				
				// Toggle Service Brake
				else if (aEvent.getActionCommand() == "Toggle Service Brake")
				{
					trainList.get(selectedId-1).setIssetServiceBrake(!trainList.get(selectedId-1).getIssetServiceBrake());
					jlToggleServiceBrake.setText("" + trainList.get(selectedId-1).getIssetServiceBrake());
				}
				
				// Toggle Emergency Brake
				else if (aEvent.getActionCommand() == "Toggle Emergency Brake")
				{
					trainList.get(selectedId-1).setIssetEmerBrake(!trainList.get(selectedId-1).getIssetEmerBrake());
					jlToggleEmergencyBrake.setText("" + trainList.get(selectedId-1).getIssetEmerBrake());
				}
				
				// Set Manual Lights Status
				else if (aEvent.getActionCommand() == "Set Manual Lights Status")
				{
					try
					{
						String[] optionsManLights = new String[2];
						optionsManLights[0] = "On";
						optionsManLights[1] = "Off";
						String tempManLights = (String)JOptionPane.showInputDialog(null, "", "Train Model - Set Lights Status", JOptionPane.QUESTION_MESSAGE, null, optionsManLights, null);
						if (tempManLights != null)
						{
							trainList.get(selectedId-1).setIssetLightsOnManual(tempManLights.equals(optionsManLights[0]));
							jlManLights.setText("" + (trainList.get(selectedId-1).getIssetLightsOnManual() ? "On" : "Off"));
						}
					}
					catch(Exception e)
					{
						e.printStackTrace(System.err);
						JOptionPane.showMessageDialog(null, "Invalid input.", "Train Model - Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
				
				// Toggle Manual Lights Status
				else if (aEvent.getActionCommand() == "Toggle Manual Lights Status")
				{
					trainList.get(selectedId-1).setIssetLightsOnUseManual(!trainList.get(selectedId-1).getIssetLightsOnUseManual());
					jlToggleManLights.setText("" + trainList.get(selectedId-1).getIssetLightsOnUseManual());
				}
				
				// Set Manual Doors Status
				else if (aEvent.getActionCommand() == "Set Manual Doors Status")
				{
					try
					{
						String[] optionsManDoors = new String[2];
						optionsManDoors[0] = "Open";
						optionsManDoors[1] = "Closed";
						String tempManDoors = (String)JOptionPane.showInputDialog(null, "", "Train Model - Set Doors Status", JOptionPane.QUESTION_MESSAGE, null, optionsManDoors, null);
						if (tempManDoors != null)
						{
							trainList.get(selectedId-1).setIssetDoorsOpenManual(tempManDoors.equals(optionsManDoors[0]));
							jlManDoors.setText("" + (trainList.get(selectedId-1).getIssetDoorsOpenManual() ? "Open" : "Closed"));
						}
					}
					catch(Exception e)
					{
						e.printStackTrace(System.err);
						JOptionPane.showMessageDialog(null, "Invalid input.", "Train Model - Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
				
				// Toggle Manual Doors Status
				else if (aEvent.getActionCommand() == "Toggle Manual Doors Status")
				{
					trainList.get(selectedId-1).setIssetDoorsOpenUseManual(!trainList.get(selectedId-1).getIssetDoorsOpenUseManual());
					jlToggleManDoors.setText("" + trainList.get(selectedId-1).getIssetDoorsOpenUseManual());
				}
				
				// Set Manual Target Temp.
				else if (aEvent.getActionCommand() == "Set Manual Target Temp.")
				{
					try
					{
						String tempManTarTemperature = (String)JOptionPane.showInputDialog(null, "Enter the target temperature (degrees Celsius) (number only):", "Train Model - Set Target Temperature", JOptionPane.QUESTION_MESSAGE);
						if (tempManTarTemperature != null)
						{
							trainList.get(selectedId-1).setTargetTemperatureManual(Double.parseDouble(tempManTarTemperature));
							jlManTarTemperature.setText("" + trainList.get(selectedId-1).getTargetTemperatureManual());
						}
					}
					catch(Exception e)
					{
						e.printStackTrace(System.err);
						JOptionPane.showMessageDialog(null, "Invalid target temperature entered.", "Train Model - Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
				
				// Toggle Manual Target Temp.
				else if (aEvent.getActionCommand() == "Toggle Manual Target Temp.")
				{
					trainList.get(selectedId-1).setIssetTargetTemperatureManual(!trainList.get(selectedId-1).getIssetTargetTemperatureManual());
					jlToggleManTarTemperature.setText("" + trainList.get(selectedId-1).getIssetTargetTemperatureManual());
				}
				
				else
				{
					JOptionPane.showMessageDialog(null, "Invalid action event.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace(System.err);
				JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	} 
}