package TNM;

/**
 * NSECS-TNC USER INTERFACE
 * USE EITHER AS PART OF WHOLE CONTROL SYSTEM
 * OR AS A TESTING PLATFORM BY RUNNING TNC_TEST MODULE
 * 
 * @author (Ben Tomasulo)
 * @version (4/24/2013)
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import TKM.*;

public class TNC_UI
{

    JFrame theWindow;       // This is the main window
    private JTextField txtUser;
    private JTextField txtUser_1;
    private JTextField txtUser_2;
    private JTextField txtUser_3;
    private JTextField txtUser_4;
    private JTextField txtUser_5;
    private JTextField txtUser_7;
    private JTextField txtUser_8;
    
    //initialize field variables
    private int userTrain;
    public int currTrain;
    private double currVel;
    private double cvo;
    private boolean cvoT;
    private double currPow;
    private double currSetpoint;
    private double userSetpoint;
    private double speedLimit;
    private double slo;
    private boolean sloT;
    private double aut;
    private double autO;
    private boolean autOT;
    private double plcVel;
    private int tbn;
    private int tbnO;
    private boolean tbnOT;
    private double cit;
    private double userIdealT;
    private int timeH;
    private int timeM;
    private int timeS;
    private double timeO;
    private boolean timeOT;
    private boolean pause;
    private boolean tick;
    private boolean trainEF;
    private int trainEFoT;
    private boolean signalPF;
    private int signalPFoT;
    private boolean brakeF;
    private int brakeFoT;
    private boolean brokenR;
    private int brokenRoT;
    private boolean trackCF;
    private int trackCFoT;
    private boolean powerF;
    private int powerFoT;
    private boolean sBrake;
    private int sBrakeOT;
    private boolean eBrake;
    private int eBrakeOT;
    private boolean doors;
    private String announ;
    private boolean lights;
    public int lsize;
    
    //initialize JLabels
    private JLabel lblVel;
    private JLabel label;
    private JLabel lblPause;
    private JLabel lblOnoff;
    private JLabel lblEngaged_CVOT;
    private JLabel lblFail;
    private JLabel lblPow;
    private JLabel lblFail_1;
    private JLabel lblSetVel;
    private JLabel lblFail_2;
    private JLabel lblFail_3;
    private JLabel lblVelLimit;
    private JLabel lblFail_4;
    private JLabel lblFail_5;
    private JLabel lblEngaged_5;
    private JLabel lblFail_6;
    private JLabel lblAutVel;
    private JLabel lblFail_7;
    private JLabel lblFail_8;
    private JLabel lblEngaged_6;
    private JLabel lblFail_9;
    private JLabel lblPlcVel;
    private JLabel lblFail_10;
    private JLabel lblBlock;
    private JLabel lblFail_11;
    private JLabel lblEngaged;
    private JLabel lblBlockOverride;
    private JLabel lblEngaged_1;
    private JLabel lblIdealtemp;
    private JLabel lblClosed;
    private JLabel lblTheRainIn;
    private JLabel lblTime;
    private JLabel lblLightsOn;
    private JLabel lblEngaged_2;
    private JLabel lblTimeOverride;
    private JLabel lblEngaged_3;
    
    //initialize JButtons
    private JButton btnPauseresumeToggle;
    private JButton btnSet_1;
    
    public TNC_UI(boolean control)
    {
        // default field values
        currTrain=1;
        currVel=-1;
        cvoT=false;
        currPow=-1;
        currSetpoint=5;
        speedLimit=-1;
        sloT=false;
        aut=-1;
        autO=-1;
        autOT=false;
        plcVel=-1;
        tbn=-1;
        tbnO=-1;
        tbnOT=false;
        cit=22;
        timeH=-1;
        timeM=-1;
        timeS=-1;
        timeO=0;
        timeOT=false;
        pause=false;
        tick=control;
        trainEF=false;
        trainEFoT=0;
        signalPF=false;
        signalPFoT=0;
        brakeF=false;
        brakeFoT=0;
        trackCF=false;
        trackCFoT=0;
        powerF=false;
        powerFoT=0;
        sBrake=false;
        sBrakeOT=0;
        eBrake=false;
        eBrakeOT=0;
        doors=false;
        announ="-1";
        lights=false;
        lsize=1;
        
        // create display window
        theWindow = new JFrame("NSECS-TNC USER INTERFACE");
        theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        theWindow.setSize(950,750);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{278, 134, 75, 32, 278, 108, 0};
        gridBagLayout.rowHeights = new int[] {30, 29, 30, 29, 29, 16, 30, 16, 30, 29, 29, 30, 29, 
            16, 29, 30, 29, 16, 30, 29, 16, 30};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        theWindow.getContentPane().setLayout(gridBagLayout);
        
        // display window information
        JLabel lblSelectTrain = new JLabel("Select Train:");
        GridBagConstraints gbc_lblSelectTrain = new GridBagConstraints();
        gbc_lblSelectTrain.anchor = GridBagConstraints.EAST;
        gbc_lblSelectTrain.insets = new Insets(0, 0, 5, 5);
        gbc_lblSelectTrain.gridx = 0;
        gbc_lblSelectTrain.gridy = 0;
        theWindow.getContentPane().add(lblSelectTrain, gbc_lblSelectTrain);
        
        txtUser = new JTextField();
        txtUser.setText("user #");
        GridBagConstraints gbc_txtUser = new GridBagConstraints();
        gbc_txtUser.anchor = GridBagConstraints.NORTH;
        gbc_txtUser.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtUser.insets = new Insets(0, 0, 5, 5);
        gbc_txtUser.gridx = 1;
        gbc_txtUser.gridy = 0;
        theWindow.getContentPane().add(txtUser, gbc_txtUser);
        txtUser.setColumns(10);
        
        JButton btnSet = new JButton("set");
        GridBagConstraints gbc_btnSet = new GridBagConstraints();
        gbc_btnSet.anchor = GridBagConstraints.SOUTHWEST;
        gbc_btnSet.insets = new Insets(0, 0, 5, 5);
        gbc_btnSet.gridx = 2;
        gbc_btnSet.gridy = 0;
        theWindow.getContentPane().add(btnSet, gbc_btnSet);
        btnSet.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                int currInt=Integer.parseInt(txtUser.getText());
                if((currInt>=0)&&(currInt<=lsize))
                {
                	currTrain=currInt;
                }
            }
        });     
        
        JLabel lblCurrentTrainSelected = new JLabel("Current Train Selected:");
        GridBagConstraints gbc_lblCurrentTrainSelected = new GridBagConstraints();
        gbc_lblCurrentTrainSelected.anchor = GridBagConstraints.EAST;
        gbc_lblCurrentTrainSelected.insets = new Insets(0, 0, 5, 5);
        gbc_lblCurrentTrainSelected.gridx = 4;
        gbc_lblCurrentTrainSelected.gridy = 0;
        theWindow.getContentPane().add(lblCurrentTrainSelected, gbc_lblCurrentTrainSelected);
        
        label = new JLabel(""+currTrain);
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.anchor = GridBagConstraints.WEST;
        gbc_label.insets = new Insets(0, 0, 5, 0);
        gbc_label.gridx = 5;
        gbc_label.gridy = 0;
        theWindow.getContentPane().add(label, gbc_label);
        
        JLabel lblCurrentVelocitykmh = new JLabel("Current Velocity (m/s):");
        GridBagConstraints gbc_lblCurrentVelocitykmh = new GridBagConstraints();
        gbc_lblCurrentVelocitykmh.anchor = GridBagConstraints.EAST;
        gbc_lblCurrentVelocitykmh.insets = new Insets(0, 0, 5, 5);
        gbc_lblCurrentVelocitykmh.gridx = 0;
        gbc_lblCurrentVelocitykmh.gridy = 1;
        theWindow.getContentPane().add(lblCurrentVelocitykmh, gbc_lblCurrentVelocitykmh);
        
        lblVel = new JLabel(""+currVel);
        GridBagConstraints gbc_lblVel = new GridBagConstraints();
        gbc_lblVel.anchor = GridBagConstraints.WEST;
        gbc_lblVel.insets = new Insets(0, 0, 5, 5);
        gbc_lblVel.gridx = 1;
        gbc_lblVel.gridy = 1;
        theWindow.getContentPane().add(lblVel, gbc_lblVel);
        
        btnPauseresumeToggle = new JButton("Pause/Resume Toggle");
        GridBagConstraints gbc_btnPauseresumeToggle = new GridBagConstraints();
        gbc_btnPauseresumeToggle.anchor = GridBagConstraints.NORTHEAST;
        gbc_btnPauseresumeToggle.insets = new Insets(0, 0, 5, 5);
        gbc_btnPauseresumeToggle.gridx = 4;
        gbc_btnPauseresumeToggle.gridy = 1;
        theWindow.getContentPane().add(btnPauseresumeToggle, gbc_btnPauseresumeToggle);
        btnPauseresumeToggle.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                pause=bToggle(pause);
            }
        });  
        
        lblPause = new JLabel(bPause(pause));
        GridBagConstraints gbc_lblPause = new GridBagConstraints();
        gbc_lblPause.anchor = GridBagConstraints.WEST;
        gbc_lblPause.insets = new Insets(0, 0, 5, 0);
        gbc_lblPause.gridx = 5;
        gbc_lblPause.gridy = 1;
        theWindow.getContentPane().add(lblPause, gbc_lblPause);
        
        JLabel lblCurrentVelocityOverride = new JLabel("Current Velocity Override (m/s):");
        GridBagConstraints gbc_lblCurrentVelocityOverride = new GridBagConstraints();
        gbc_lblCurrentVelocityOverride.anchor = GridBagConstraints.EAST;
        gbc_lblCurrentVelocityOverride.insets = new Insets(0, 0, 5, 5);
        gbc_lblCurrentVelocityOverride.gridx = 0;
        gbc_lblCurrentVelocityOverride.gridy = 2;
        theWindow.getContentPane().add(lblCurrentVelocityOverride, gbc_lblCurrentVelocityOverride);
        
        txtUser_1 = new JTextField();
        txtUser_1.setText("user #");
        GridBagConstraints gbc_txtUser_1 = new GridBagConstraints();
        gbc_txtUser_1.anchor = GridBagConstraints.NORTH;
        gbc_txtUser_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtUser_1.insets = new Insets(0, 0, 5, 5);
        gbc_txtUser_1.gridx = 1;
        gbc_txtUser_1.gridy = 2;
        theWindow.getContentPane().add(txtUser_1, gbc_txtUser_1);
        txtUser_1.setColumns(10);
        
        btnSet_1 = new JButton("set");
        GridBagConstraints gbc_btnSet_1 = new GridBagConstraints();
        gbc_btnSet_1.anchor = GridBagConstraints.SOUTHWEST;
        gbc_btnSet_1.insets = new Insets(0, 0, 5, 5);
        gbc_btnSet_1.gridx = 2;
        gbc_btnSet_1.gridy = 2;
        theWindow.getContentPane().add(btnSet_1, gbc_btnSet_1);
        btnSet_1.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                double currDou=Double.parseDouble(txtUser_1.getText());
                cvo=currDou;
            }
        });    
        
        JLabel lblTickOverride = new JLabel("Tick Override:");
        GridBagConstraints gbc_lblTickOverride = new GridBagConstraints();
        gbc_lblTickOverride.anchor = GridBagConstraints.EAST;
        gbc_lblTickOverride.insets = new Insets(0, 0, 5, 5);
        gbc_lblTickOverride.gridx = 4;
        gbc_lblTickOverride.gridy = 2;
        theWindow.getContentPane().add(lblTickOverride, gbc_lblTickOverride);
        
        lblOnoff = new JLabel(bTick(tick));
        GridBagConstraints gbc_lblOnoff = new GridBagConstraints();
        gbc_lblOnoff.anchor = GridBagConstraints.WEST;
        gbc_lblOnoff.insets = new Insets(0, 0, 5, 0);
        gbc_lblOnoff.gridx = 5;
        gbc_lblOnoff.gridy = 2;
        theWindow.getContentPane().add(lblOnoff, gbc_lblOnoff);
        
        JButton btnCurrentVelocityOverride = new JButton("Current Velocity Override Toggle");
        GridBagConstraints gbc_btnCurrentVelocityOverride = new GridBagConstraints();
        gbc_btnCurrentVelocityOverride.anchor = GridBagConstraints.NORTHEAST;
        gbc_btnCurrentVelocityOverride.insets = new Insets(0, 0, 5, 5);
        gbc_btnCurrentVelocityOverride.gridx = 0;
        gbc_btnCurrentVelocityOverride.gridy = 3;
        theWindow.getContentPane().add(btnCurrentVelocityOverride, gbc_btnCurrentVelocityOverride);
        btnCurrentVelocityOverride.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                cvoT=bToggle(cvoT);
            }
        });  
        
        lblEngaged_CVOT = new JLabel(bTout(cvoT));
        GridBagConstraints gbc_lblEngaged_CVOT = new GridBagConstraints();
        gbc_lblEngaged_CVOT.anchor = GridBagConstraints.WEST;
        gbc_lblEngaged_CVOT.insets = new Insets(0, 0, 5, 5);
        gbc_lblEngaged_CVOT.gridx = 1;
        gbc_lblEngaged_CVOT.gridy = 3;
        theWindow.getContentPane().add(lblEngaged_CVOT, gbc_lblEngaged_CVOT);
        
        JLabel lblTrainEngineFailure = new JLabel("Train Engine Failure:");
        GridBagConstraints gbc_lblTrainEngineFailure = new GridBagConstraints();
        gbc_lblTrainEngineFailure.anchor = GridBagConstraints.EAST;
        gbc_lblTrainEngineFailure.insets = new Insets(0, 0, 5, 5);
        gbc_lblTrainEngineFailure.gridx = 4;
        gbc_lblTrainEngineFailure.gridy = 3;
        theWindow.getContentPane().add(lblTrainEngineFailure, gbc_lblTrainEngineFailure);
        
        lblFail = new JLabel(bFout(trainEF));
        GridBagConstraints gbc_lblFail = new GridBagConstraints();
        gbc_lblFail.anchor = GridBagConstraints.WEST;
        gbc_lblFail.insets = new Insets(0, 0, 5, 0);
        gbc_lblFail.gridx = 5;
        gbc_lblFail.gridy = 3;
        theWindow.getContentPane().add(lblFail, gbc_lblFail);
        
        JLabel lblCurrentPowerw = new JLabel("Current Power (W):");
        GridBagConstraints gbc_lblCurrentPowerw = new GridBagConstraints();
        gbc_lblCurrentPowerw.anchor = GridBagConstraints.EAST;
        gbc_lblCurrentPowerw.insets = new Insets(0, 0, 5, 5);
        gbc_lblCurrentPowerw.gridx = 0;
        gbc_lblCurrentPowerw.gridy = 4;
        theWindow.getContentPane().add(lblCurrentPowerw, gbc_lblCurrentPowerw);
        
        lblPow = new JLabel(""+currPow);
        GridBagConstraints gbc_lblPow = new GridBagConstraints();
        gbc_lblPow.anchor = GridBagConstraints.WEST;
        gbc_lblPow.insets = new Insets(0, 0, 5, 5);
        gbc_lblPow.gridx = 1;
        gbc_lblPow.gridy = 4;
        theWindow.getContentPane().add(lblPow, gbc_lblPow);
        
        JButton btnTrainEngineFailiure = new JButton("Train Engine Failure Override Toggle");
        GridBagConstraints gbc_btnTrainEngineFailiure = new GridBagConstraints();
        gbc_btnTrainEngineFailiure.anchor = GridBagConstraints.NORTHEAST;
        gbc_btnTrainEngineFailiure.insets = new Insets(0, 0, 5, 5);
        gbc_btnTrainEngineFailiure.gridx = 4;
        gbc_btnTrainEngineFailiure.gridy = 4;
        theWindow.getContentPane().add(btnTrainEngineFailiure, gbc_btnTrainEngineFailiure);
        btnTrainEngineFailiure.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                trainEFoT=iToggle(trainEFoT);
            }
        });
        
        lblFail_1 = new JLabel(iTfail(trainEFoT));
        GridBagConstraints gbc_lblFail_1 = new GridBagConstraints();
        gbc_lblFail_1.anchor = GridBagConstraints.WEST;
        gbc_lblFail_1.insets = new Insets(0, 0, 5, 0);
        gbc_lblFail_1.gridx = 5;
        gbc_lblFail_1.gridy = 4;
        theWindow.getContentPane().add(lblFail_1, gbc_lblFail_1);
        
        JLabel lblCurrentSetpointVelocity = new JLabel("Current Setpoint Velocity (m/s):");
        GridBagConstraints gbc_lblCurrentSetpointVelocity = new GridBagConstraints();
        gbc_lblCurrentSetpointVelocity.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblCurrentSetpointVelocity.insets = new Insets(0, 0, 5, 5);
        gbc_lblCurrentSetpointVelocity.gridx = 0;
        gbc_lblCurrentSetpointVelocity.gridy = 5;
        theWindow.getContentPane().add(lblCurrentSetpointVelocity, gbc_lblCurrentSetpointVelocity);
        
        lblSetVel = new JLabel(""+currSetpoint);
        GridBagConstraints gbc_lblSetVel = new GridBagConstraints();
        gbc_lblSetVel.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblSetVel.insets = new Insets(0, 0, 5, 5);
        gbc_lblSetVel.gridx = 1;
        gbc_lblSetVel.gridy = 5;
        theWindow.getContentPane().add(lblSetVel, gbc_lblSetVel);
        
        JLabel lblSignalPickupFailure = new JLabel("Signal Pickup Failure:");
        GridBagConstraints gbc_lblSignalPickupFailure = new GridBagConstraints();
        gbc_lblSignalPickupFailure.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblSignalPickupFailure.insets = new Insets(0, 0, 5, 5);
        gbc_lblSignalPickupFailure.gridx = 4;
        gbc_lblSignalPickupFailure.gridy = 5;
        theWindow.getContentPane().add(lblSignalPickupFailure, gbc_lblSignalPickupFailure);
        
        lblFail_2 = new JLabel(bFout(signalPF));
        GridBagConstraints gbc_lblFail_2 = new GridBagConstraints();
        gbc_lblFail_2.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblFail_2.insets = new Insets(0, 0, 5, 0);
        gbc_lblFail_2.gridx = 5;
        gbc_lblFail_2.gridy = 5;
        theWindow.getContentPane().add(lblFail_2, gbc_lblFail_2);
        
        JLabel lblSetpointVelocitykmh = new JLabel("Setpoint Velocity (m/s):");
        GridBagConstraints gbc_lblSetpointVelocitykmh = new GridBagConstraints();
        gbc_lblSetpointVelocitykmh.anchor = GridBagConstraints.EAST;
        gbc_lblSetpointVelocitykmh.insets = new Insets(0, 0, 5, 5);
        gbc_lblSetpointVelocitykmh.gridx = 0;
        gbc_lblSetpointVelocitykmh.gridy = 6;
        theWindow.getContentPane().add(lblSetpointVelocitykmh, gbc_lblSetpointVelocitykmh);
        
        txtUser_2 = new JTextField();
        txtUser_2.setText("user #");
        GridBagConstraints gbc_txtUser_2 = new GridBagConstraints();
        gbc_txtUser_2.anchor = GridBagConstraints.NORTH;
        gbc_txtUser_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtUser_2.insets = new Insets(0, 0, 5, 5);
        gbc_txtUser_2.gridx = 1;
        gbc_txtUser_2.gridy = 6;
        theWindow.getContentPane().add(txtUser_2, gbc_txtUser_2);
        txtUser_2.setColumns(10);
        
        JButton btnSet_2 = new JButton("set");
        GridBagConstraints gbc_btnSet_2 = new GridBagConstraints();
        gbc_btnSet_2.anchor = GridBagConstraints.SOUTHWEST;
        gbc_btnSet_2.insets = new Insets(0, 0, 5, 5);
        gbc_btnSet_2.gridx = 2;
        gbc_btnSet_2.gridy = 6;
        theWindow.getContentPane().add(btnSet_2, gbc_btnSet_2);
        btnSet_2.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                double currDou=Double.parseDouble(txtUser_2.getText());
                currSetpoint=currDou;
            }
        });   
        
        JButton btnSignalPickupFailure = new JButton("Signal Pickup Failure Override Toggle");
        GridBagConstraints gbc_btnSignalPickupFailure = new GridBagConstraints();
        gbc_btnSignalPickupFailure.anchor = GridBagConstraints.SOUTHWEST;
        gbc_btnSignalPickupFailure.insets = new Insets(0, 0, 5, 5);
        gbc_btnSignalPickupFailure.gridx = 4;
        gbc_btnSignalPickupFailure.gridy = 6;
        theWindow.getContentPane().add(btnSignalPickupFailure, gbc_btnSignalPickupFailure);
        btnSignalPickupFailure.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                signalPFoT=iToggle(signalPFoT);
            }
        });
        
        lblFail_3 = new JLabel(iTfail(signalPFoT));
        GridBagConstraints gbc_lblFail_3 = new GridBagConstraints();
        gbc_lblFail_3.anchor = GridBagConstraints.WEST;
        gbc_lblFail_3.insets = new Insets(0, 0, 5, 0);
        gbc_lblFail_3.gridx = 5;
        gbc_lblFail_3.gridy = 6;
        theWindow.getContentPane().add(lblFail_3, gbc_lblFail_3);
        
        JLabel lblSpeedLimitkmh = new JLabel("Speed Limit (m/s):");
        GridBagConstraints gbc_lblSpeedLimitkmh = new GridBagConstraints();
        gbc_lblSpeedLimitkmh.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblSpeedLimitkmh.insets = new Insets(0, 0, 5, 5);
        gbc_lblSpeedLimitkmh.gridx = 0;
        gbc_lblSpeedLimitkmh.gridy = 7;
        theWindow.getContentPane().add(lblSpeedLimitkmh, gbc_lblSpeedLimitkmh);
        
        lblVelLimit = new JLabel(""+speedLimit);
        GridBagConstraints gbc_lblVelLimit = new GridBagConstraints();
        gbc_lblVelLimit.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblVelLimit.insets = new Insets(0, 0, 5, 5);
        gbc_lblVelLimit.gridx = 1;
        gbc_lblVelLimit.gridy = 7;
        theWindow.getContentPane().add(lblVelLimit, gbc_lblVelLimit);
        
        JLabel lblBrakeFailure = new JLabel("Brake Failure:");
        GridBagConstraints gbc_lblBrakeFailure = new GridBagConstraints();
        gbc_lblBrakeFailure.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblBrakeFailure.insets = new Insets(0, 0, 5, 5);
        gbc_lblBrakeFailure.gridx = 4;
        gbc_lblBrakeFailure.gridy = 7;
        theWindow.getContentPane().add(lblBrakeFailure, gbc_lblBrakeFailure);
        
        lblFail_4 = new JLabel(bFout(brakeF));
        GridBagConstraints gbc_lblFail_4 = new GridBagConstraints();
        gbc_lblFail_4.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblFail_4.insets = new Insets(0, 0, 5, 0);
        gbc_lblFail_4.gridx = 5;
        gbc_lblFail_4.gridy = 7;
        theWindow.getContentPane().add(lblFail_4, gbc_lblFail_4);
        
        JLabel lblSpeedLimitOverride = new JLabel("Speed Limit Override (m/s):");
        GridBagConstraints gbc_lblSpeedLimitOverride = new GridBagConstraints();
        gbc_lblSpeedLimitOverride.anchor = GridBagConstraints.EAST;
        gbc_lblSpeedLimitOverride.insets = new Insets(0, 0, 5, 5);
        gbc_lblSpeedLimitOverride.gridx = 0;
        gbc_lblSpeedLimitOverride.gridy = 8;
        theWindow.getContentPane().add(lblSpeedLimitOverride, gbc_lblSpeedLimitOverride);
        
        txtUser_3 = new JTextField();
        txtUser_3.setText("user #");
        GridBagConstraints gbc_txtUser_3 = new GridBagConstraints();
        gbc_txtUser_3.anchor = GridBagConstraints.NORTH;
        gbc_txtUser_3.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtUser_3.insets = new Insets(0, 0, 5, 5);
        gbc_txtUser_3.gridx = 1;
        gbc_txtUser_3.gridy = 8;
        theWindow.getContentPane().add(txtUser_3, gbc_txtUser_3);
        txtUser_3.setColumns(10);
        
        JButton btnSet_3 = new JButton("set");
        GridBagConstraints gbc_btnSet_3 = new GridBagConstraints();
        gbc_btnSet_3.anchor = GridBagConstraints.SOUTHWEST;
        gbc_btnSet_3.insets = new Insets(0, 0, 5, 5);
        gbc_btnSet_3.gridx = 2;
        gbc_btnSet_3.gridy = 8;
        theWindow.getContentPane().add(btnSet_3, gbc_btnSet_3);
        btnSet_3.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                double currDou=Double.parseDouble(txtUser_3.getText());
                slo=currDou;
            }
        });  
        
        JButton btnBrakeFailureOverride = new JButton("Brake Failure Override Toggle");
        GridBagConstraints gbc_btnBrakeFailureOverride = new GridBagConstraints();
        gbc_btnBrakeFailureOverride.anchor = GridBagConstraints.SOUTHEAST;
        gbc_btnBrakeFailureOverride.insets = new Insets(0, 0, 5, 5);
        gbc_btnBrakeFailureOverride.gridx = 4;
        gbc_btnBrakeFailureOverride.gridy = 8;
        theWindow.getContentPane().add(btnBrakeFailureOverride, gbc_btnBrakeFailureOverride);
        btnBrakeFailureOverride.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                brakeFoT=iToggle(brakeFoT);
            }
        });
        
        lblFail_5 = new JLabel(iTfail(brakeFoT));
        GridBagConstraints gbc_lblFail_5 = new GridBagConstraints();
        gbc_lblFail_5.anchor = GridBagConstraints.WEST;
        gbc_lblFail_5.insets = new Insets(0, 0, 5, 0);
        gbc_lblFail_5.gridx = 5;
        gbc_lblFail_5.gridy = 8;
        theWindow.getContentPane().add(lblFail_5, gbc_lblFail_5);
        
        JButton btnSpeedLimitOverride = new JButton("Speed Limit Override Toggle");
        GridBagConstraints gbc_btnSpeedLimitOverride = new GridBagConstraints();
        gbc_btnSpeedLimitOverride.anchor = GridBagConstraints.NORTHEAST;
        gbc_btnSpeedLimitOverride.insets = new Insets(0, 0, 5, 5);
        gbc_btnSpeedLimitOverride.gridx = 0;
        gbc_btnSpeedLimitOverride.gridy = 9;
        theWindow.getContentPane().add(btnSpeedLimitOverride, gbc_btnSpeedLimitOverride);
        btnSpeedLimitOverride.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                sloT=bToggle(sloT);
            }
        });
        
        lblEngaged_5 = new JLabel(bTout(sloT));
        GridBagConstraints gbc_lblEngaged_5 = new GridBagConstraints();
        gbc_lblEngaged_5.anchor = GridBagConstraints.WEST;
        gbc_lblEngaged_5.insets = new Insets(0, 0, 5, 5);
        gbc_lblEngaged_5.gridx = 1;
        gbc_lblEngaged_5.gridy = 9;
        theWindow.getContentPane().add(lblEngaged_5, gbc_lblEngaged_5);
        
        JLabel lblBrokenRail = new JLabel("Broken Rail:");
        GridBagConstraints gbc_lblBrokenRail = new GridBagConstraints();
        gbc_lblBrokenRail.anchor = GridBagConstraints.EAST;
        gbc_lblBrokenRail.insets = new Insets(0, 0, 5, 5);
        gbc_lblBrokenRail.gridx = 4;
        gbc_lblBrokenRail.gridy = 9;
        theWindow.getContentPane().add(lblBrokenRail, gbc_lblBrokenRail);
        
        lblFail_6 = new JLabel(bFout(brokenR));
        GridBagConstraints gbc_lblFail_6 = new GridBagConstraints();
        gbc_lblFail_6.anchor = GridBagConstraints.WEST;
        gbc_lblFail_6.insets = new Insets(0, 0, 5, 0);
        gbc_lblFail_6.gridx = 5;
        gbc_lblFail_6.gridy = 9;
        theWindow.getContentPane().add(lblFail_6, gbc_lblFail_6);
        
        JLabel lblAuthoritykmh = new JLabel("Authority (m/s):");
        GridBagConstraints gbc_lblAuthoritykmh = new GridBagConstraints();
        gbc_lblAuthoritykmh.anchor = GridBagConstraints.EAST;
        gbc_lblAuthoritykmh.insets = new Insets(0, 0, 5, 5);
        gbc_lblAuthoritykmh.gridx = 0;
        gbc_lblAuthoritykmh.gridy = 10;
        theWindow.getContentPane().add(lblAuthoritykmh, gbc_lblAuthoritykmh);
        
        lblAutVel = new JLabel(""+aut);
        GridBagConstraints gbc_lblAutVel = new GridBagConstraints();
        gbc_lblAutVel.anchor = GridBagConstraints.WEST;
        gbc_lblAutVel.insets = new Insets(0, 0, 5, 5);
        gbc_lblAutVel.gridx = 1;
        gbc_lblAutVel.gridy = 10;
        theWindow.getContentPane().add(lblAutVel, gbc_lblAutVel);
        
        JButton btnBrokenRailOverride = new JButton("Broken Rail Override Toggle");
        GridBagConstraints gbc_btnBrokenRailOverride = new GridBagConstraints();
        gbc_btnBrokenRailOverride.anchor = GridBagConstraints.NORTHEAST;
        gbc_btnBrokenRailOverride.insets = new Insets(0, 0, 5, 5);
        gbc_btnBrokenRailOverride.gridx = 4;
        gbc_btnBrokenRailOverride.gridy = 10;
        theWindow.getContentPane().add(btnBrokenRailOverride, gbc_btnBrokenRailOverride);
        btnBrokenRailOverride.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                brokenRoT=iToggle(brokenRoT);
            }
        });
        
        lblFail_7 = new JLabel(iTfail(brokenRoT));
        GridBagConstraints gbc_lblFail_7 = new GridBagConstraints();
        gbc_lblFail_7.anchor = GridBagConstraints.WEST;
        gbc_lblFail_7.insets = new Insets(0, 0, 5, 0);
        gbc_lblFail_7.gridx = 5;
        gbc_lblFail_7.gridy = 10;
        theWindow.getContentPane().add(lblFail_7, gbc_lblFail_7);
        
        JLabel lblAuthorityOverridekmh = new JLabel("Authority Override (m/s):");
        GridBagConstraints gbc_lblAuthorityOverridekmh = new GridBagConstraints();
        gbc_lblAuthorityOverridekmh.anchor = GridBagConstraints.EAST;
        gbc_lblAuthorityOverridekmh.insets = new Insets(0, 0, 5, 5);
        gbc_lblAuthorityOverridekmh.gridx = 0;
        gbc_lblAuthorityOverridekmh.gridy = 11;
        theWindow.getContentPane().add(lblAuthorityOverridekmh, gbc_lblAuthorityOverridekmh);
        
        txtUser_4 = new JTextField();
        txtUser_4.setText("user #");
        GridBagConstraints gbc_txtUser_4 = new GridBagConstraints();
        gbc_txtUser_4.anchor = GridBagConstraints.NORTH;
        gbc_txtUser_4.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtUser_4.insets = new Insets(0, 0, 5, 5);
        gbc_txtUser_4.gridx = 1;
        gbc_txtUser_4.gridy = 11;
        theWindow.getContentPane().add(txtUser_4, gbc_txtUser_4);
        txtUser_4.setColumns(10);
        
        JButton btnSet_4 = new JButton("set");
        GridBagConstraints gbc_btnSet_4 = new GridBagConstraints();
        gbc_btnSet_4.anchor = GridBagConstraints.SOUTHWEST;
        gbc_btnSet_4.insets = new Insets(0, 0, 5, 5);
        gbc_btnSet_4.gridx = 2;
        gbc_btnSet_4.gridy = 11;
        theWindow.getContentPane().add(btnSet_4, gbc_btnSet_4);
        btnSet_4.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                double currDou=Double.parseDouble(txtUser_4.getText());
                autO=currDou;
            }
        });  
        
        JLabel lblTrackCircuitFailure = new JLabel("Track Circuit Failure:");
        GridBagConstraints gbc_lblTrackCircuitFailure = new GridBagConstraints();
        gbc_lblTrackCircuitFailure.anchor = GridBagConstraints.EAST;
        gbc_lblTrackCircuitFailure.insets = new Insets(0, 0, 5, 5);
        gbc_lblTrackCircuitFailure.gridx = 4;
        gbc_lblTrackCircuitFailure.gridy = 11;
        theWindow.getContentPane().add(lblTrackCircuitFailure, gbc_lblTrackCircuitFailure);
        
        lblFail_8 = new JLabel(bFout(trackCF));
        GridBagConstraints gbc_lblFail_8 = new GridBagConstraints();
        gbc_lblFail_8.anchor = GridBagConstraints.WEST;
        gbc_lblFail_8.insets = new Insets(0, 0, 5, 0);
        gbc_lblFail_8.gridx = 5;
        gbc_lblFail_8.gridy = 11;
        theWindow.getContentPane().add(lblFail_8, gbc_lblFail_8);
        
        JButton btnAuthorityOverrideToggle = new JButton("Authority Override Toggle");
        GridBagConstraints gbc_btnAuthorityOverrideToggle = new GridBagConstraints();
        gbc_btnAuthorityOverrideToggle.anchor = GridBagConstraints.NORTHEAST;
        gbc_btnAuthorityOverrideToggle.insets = new Insets(0, 0, 5, 5);
        gbc_btnAuthorityOverrideToggle.gridx = 0;
        gbc_btnAuthorityOverrideToggle.gridy = 12;
        theWindow.getContentPane().add(btnAuthorityOverrideToggle, gbc_btnAuthorityOverrideToggle);
        btnAuthorityOverrideToggle.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                autOT=bToggle(autOT);
            }
        });
        
        lblEngaged_6 = new JLabel(bTout(autOT));
        GridBagConstraints gbc_lblEngaged_6 = new GridBagConstraints();
        gbc_lblEngaged_6.anchor = GridBagConstraints.WEST;
        gbc_lblEngaged_6.insets = new Insets(0, 0, 5, 5);
        gbc_lblEngaged_6.gridx = 1;
        gbc_lblEngaged_6.gridy = 12;
        theWindow.getContentPane().add(lblEngaged_6, gbc_lblEngaged_6);
        
        JButton btnTrackCircuitFailure = new JButton("Track Circuit Failure Override Toggle");
        GridBagConstraints gbc_btnTrackCircuitFailure = new GridBagConstraints();
        gbc_btnTrackCircuitFailure.anchor = GridBagConstraints.NORTHEAST;
        gbc_btnTrackCircuitFailure.insets = new Insets(0, 0, 5, 5);
        gbc_btnTrackCircuitFailure.gridx = 4;
        gbc_btnTrackCircuitFailure.gridy = 12;
        theWindow.getContentPane().add(btnTrackCircuitFailure, gbc_btnTrackCircuitFailure);
        btnTrackCircuitFailure.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                trackCFoT=iToggle(trackCFoT);
            }
        });
        
        lblFail_9 = new JLabel(iTfail(trackCFoT));
        GridBagConstraints gbc_lblFail_9 = new GridBagConstraints();
        gbc_lblFail_9.anchor = GridBagConstraints.WEST;
        gbc_lblFail_9.insets = new Insets(0, 0, 5, 0);
        gbc_lblFail_9.gridx = 5;
        gbc_lblFail_9.gridy = 12;
        theWindow.getContentPane().add(lblFail_9, gbc_lblFail_9);
        
        JLabel lblPlcChosenVelocity = new JLabel("PLC Chosen Velocity (m/s):");
        GridBagConstraints gbc_lblPlcChosenVelocity = new GridBagConstraints();
        gbc_lblPlcChosenVelocity.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblPlcChosenVelocity.insets = new Insets(0, 0, 5, 5);
        gbc_lblPlcChosenVelocity.gridx = 0;
        gbc_lblPlcChosenVelocity.gridy = 13;
        theWindow.getContentPane().add(lblPlcChosenVelocity, gbc_lblPlcChosenVelocity);
        
        lblPlcVel = new JLabel(""+plcVel);
        GridBagConstraints gbc_lblPlcVel = new GridBagConstraints();
        gbc_lblPlcVel.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblPlcVel.insets = new Insets(0, 0, 5, 5);
        gbc_lblPlcVel.gridx = 1;
        gbc_lblPlcVel.gridy = 13;
        theWindow.getContentPane().add(lblPlcVel, gbc_lblPlcVel);
        
        JLabel lblPowerFailure = new JLabel("Power Failure:");
        GridBagConstraints gbc_lblPowerFailure = new GridBagConstraints();
        gbc_lblPowerFailure.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblPowerFailure.insets = new Insets(0, 0, 5, 5);
        gbc_lblPowerFailure.gridx = 4;
        gbc_lblPowerFailure.gridy = 13;
        theWindow.getContentPane().add(lblPowerFailure, gbc_lblPowerFailure);
        
        lblFail_10 = new JLabel(bFout(powerF));
        GridBagConstraints gbc_lblFail_10 = new GridBagConstraints();
        gbc_lblFail_10.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblFail_10.insets = new Insets(0, 0, 5, 0);
        gbc_lblFail_10.gridx = 5;
        gbc_lblFail_10.gridy = 13;
        theWindow.getContentPane().add(lblFail_10, gbc_lblFail_10);
        
        JLabel lblTrackBlockNumber = new JLabel("Track Block Number:");
        GridBagConstraints gbc_lblTrackBlockNumber = new GridBagConstraints();
        gbc_lblTrackBlockNumber.anchor = GridBagConstraints.EAST;
        gbc_lblTrackBlockNumber.insets = new Insets(0, 0, 5, 5);
        gbc_lblTrackBlockNumber.gridx = 0;
        gbc_lblTrackBlockNumber.gridy = 14;
        theWindow.getContentPane().add(lblTrackBlockNumber, gbc_lblTrackBlockNumber);
        
        lblBlock = new JLabel(""+tbn);
        GridBagConstraints gbc_lblBlock = new GridBagConstraints();
        gbc_lblBlock.anchor = GridBagConstraints.WEST;
        gbc_lblBlock.insets = new Insets(0, 0, 5, 5);
        gbc_lblBlock.gridx = 1;
        gbc_lblBlock.gridy = 14;
        theWindow.getContentPane().add(lblBlock, gbc_lblBlock);
        
        JButton btnPowerFailureOverride = new JButton("Power Failure Override Toggle");
        GridBagConstraints gbc_btnPowerFailureOverride = new GridBagConstraints();
        gbc_btnPowerFailureOverride.anchor = GridBagConstraints.NORTHEAST;
        gbc_btnPowerFailureOverride.insets = new Insets(0, 0, 5, 5);
        gbc_btnPowerFailureOverride.gridx = 4;
        gbc_btnPowerFailureOverride.gridy = 14;
        theWindow.getContentPane().add(btnPowerFailureOverride, gbc_btnPowerFailureOverride);
        btnPowerFailureOverride.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                powerFoT=iToggle(powerFoT);
            }
        });
        
        lblFail_11 = new JLabel(iTfail(powerFoT));
        GridBagConstraints gbc_lblFail_11 = new GridBagConstraints();
        gbc_lblFail_11.anchor = GridBagConstraints.WEST;
        gbc_lblFail_11.insets = new Insets(0, 0, 5, 0);
        gbc_lblFail_11.gridx = 5;
        gbc_lblFail_11.gridy = 14;
        theWindow.getContentPane().add(lblFail_11, gbc_lblFail_11);
        
        JLabel lblTrackBlockNumber_1 = new JLabel("Track Block Number Override:");
        GridBagConstraints gbc_lblTrackBlockNumber_1 = new GridBagConstraints();
        gbc_lblTrackBlockNumber_1.anchor = GridBagConstraints.EAST;
        gbc_lblTrackBlockNumber_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblTrackBlockNumber_1.gridx = 0;
        gbc_lblTrackBlockNumber_1.gridy = 15;
        theWindow.getContentPane().add(lblTrackBlockNumber_1, gbc_lblTrackBlockNumber_1);
        
        txtUser_5 = new JTextField();
        txtUser_5.setText("user #");
        GridBagConstraints gbc_txtUser_5 = new GridBagConstraints();
        gbc_txtUser_5.anchor = GridBagConstraints.NORTH;
        gbc_txtUser_5.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtUser_5.insets = new Insets(0, 0, 5, 5);
        gbc_txtUser_5.gridx = 1;
        gbc_txtUser_5.gridy = 15;
        theWindow.getContentPane().add(txtUser_5, gbc_txtUser_5);
        txtUser_5.setColumns(10);
        
        JButton btnSet_5 = new JButton("set");
        GridBagConstraints gbc_btnSet_5 = new GridBagConstraints();
        gbc_btnSet_5.anchor = GridBagConstraints.SOUTHWEST;
        gbc_btnSet_5.insets = new Insets(0, 0, 5, 5);
        gbc_btnSet_5.gridx = 2;
        gbc_btnSet_5.gridy = 15;
        theWindow.getContentPane().add(btnSet_5, gbc_btnSet_5);
        btnSet_5.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                int currInt=Integer.parseInt(txtUser_5.getText());
                if((currInt==-1)||(currInt==-2))
                {
                	tbnO=currInt;
             	 }
            }
        });  
        
        JLabel lblServiceBrake = new JLabel("Service Brake State:");
        GridBagConstraints gbc_lblServiceBrake = new GridBagConstraints();
        gbc_lblServiceBrake.anchor = GridBagConstraints.EAST;
        gbc_lblServiceBrake.insets = new Insets(0, 0, 5, 5);
        gbc_lblServiceBrake.gridx = 4;
        gbc_lblServiceBrake.gridy = 15;
        theWindow.getContentPane().add(lblServiceBrake, gbc_lblServiceBrake);
        
        lblEngaged = new JLabel(bTout(sBrake));
        GridBagConstraints gbc_lblEngaged = new GridBagConstraints();
        gbc_lblEngaged.anchor = GridBagConstraints.WEST;
        gbc_lblEngaged.insets = new Insets(0, 0, 5, 0);
        gbc_lblEngaged.gridx = 5;
        gbc_lblEngaged.gridy = 15;
        theWindow.getContentPane().add(lblEngaged, gbc_lblEngaged);
        
        JButton btnTrackBlockNumber = new JButton("Track Block Number Override Toggle");
        GridBagConstraints gbc_btnTrackBlockNumber = new GridBagConstraints();
        gbc_btnTrackBlockNumber.anchor = GridBagConstraints.NORTHEAST;
        gbc_btnTrackBlockNumber.insets = new Insets(0, 0, 5, 5);
        gbc_btnTrackBlockNumber.gridx = 0;
        gbc_btnTrackBlockNumber.gridy = 16;
        theWindow.getContentPane().add(btnTrackBlockNumber, gbc_btnTrackBlockNumber);
        btnTrackBlockNumber.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                tbnOT=bToggle(tbnOT);
            }
        });
        
        lblBlockOverride = new JLabel(bTout(tbnOT));
        GridBagConstraints gbc_lblBlockOverride = new GridBagConstraints();
        gbc_lblBlockOverride.anchor = GridBagConstraints.WEST;
        gbc_lblBlockOverride.insets = new Insets(0, 0, 5, 5);
        gbc_lblBlockOverride.gridx = 1;
        gbc_lblBlockOverride.gridy = 16;
        theWindow.getContentPane().add(lblBlockOverride, gbc_lblBlockOverride);
        
        JButton btnServiceBrakeOverride = new JButton("Service Brake Override Toggle");
        GridBagConstraints gbc_btnServiceBrakeOverride = new GridBagConstraints();
        gbc_btnServiceBrakeOverride.anchor = GridBagConstraints.NORTHEAST;
        gbc_btnServiceBrakeOverride.insets = new Insets(0, 0, 5, 5);
        gbc_btnServiceBrakeOverride.gridx = 4;
        gbc_btnServiceBrakeOverride.gridy = 16;
        theWindow.getContentPane().add(btnServiceBrakeOverride, gbc_btnServiceBrakeOverride);
        btnServiceBrakeOverride.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                sBrakeOT=iToggle(sBrakeOT);
            }
        });
        
        lblEngaged_1 = new JLabel(iTbrake(sBrakeOT));
        GridBagConstraints gbc_lblEngaged_1 = new GridBagConstraints();
        gbc_lblEngaged_1.anchor = GridBagConstraints.WEST;
        gbc_lblEngaged_1.insets = new Insets(0, 0, 5, 0);
        gbc_lblEngaged_1.gridx = 5;
        gbc_lblEngaged_1.gridy = 16;
        theWindow.getContentPane().add(lblEngaged_1, gbc_lblEngaged_1);
        
        JLabel lblCurrentIdealTemperature = new JLabel("Current Ideal Temperature (\u00B0C):");
        GridBagConstraints gbc_lblCurrentIdealTemperature = new GridBagConstraints();
        gbc_lblCurrentIdealTemperature.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblCurrentIdealTemperature.insets = new Insets(0, 0, 5, 5);
        gbc_lblCurrentIdealTemperature.gridx = 0;
        gbc_lblCurrentIdealTemperature.gridy = 17;
        theWindow.getContentPane().add(lblCurrentIdealTemperature, gbc_lblCurrentIdealTemperature);
        
        lblIdealtemp = new JLabel(""+cit);
        GridBagConstraints gbc_lblIdealtemp = new GridBagConstraints();
        gbc_lblIdealtemp.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblIdealtemp.insets = new Insets(0, 0, 5, 5);
        gbc_lblIdealtemp.gridx = 1;
        gbc_lblIdealtemp.gridy = 17;
        theWindow.getContentPane().add(lblIdealtemp, gbc_lblIdealtemp);
        
        JLabel lblDoorsState = new JLabel("Doors State:");
        GridBagConstraints gbc_lblDoorsState = new GridBagConstraints();
        gbc_lblDoorsState.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblDoorsState.insets = new Insets(0, 0, 5, 5);
        gbc_lblDoorsState.gridx = 4;
        gbc_lblDoorsState.gridy = 17;
        theWindow.getContentPane().add(lblDoorsState, gbc_lblDoorsState);
        
        lblClosed = new JLabel(oDoors(doors));
        GridBagConstraints gbc_lblClosed = new GridBagConstraints();
        gbc_lblClosed.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblClosed.insets = new Insets(0, 0, 5, 0);
        gbc_lblClosed.gridx = 5;
        gbc_lblClosed.gridy = 17;
        theWindow.getContentPane().add(lblClosed, gbc_lblClosed);
        
        JLabel lblIdealTemperaturec = new JLabel("Ideal Temperature (\u00B0C):");
        GridBagConstraints gbc_lblIdealTemperaturec = new GridBagConstraints();
        gbc_lblIdealTemperaturec.anchor = GridBagConstraints.EAST;
        gbc_lblIdealTemperaturec.insets = new Insets(0, 0, 5, 5);
        gbc_lblIdealTemperaturec.gridx = 0;
        gbc_lblIdealTemperaturec.gridy = 18;
        theWindow.getContentPane().add(lblIdealTemperaturec, gbc_lblIdealTemperaturec);
        
        txtUser_7 = new JTextField();
        txtUser_7.setText("user #");
        GridBagConstraints gbc_txtUser_7 = new GridBagConstraints();
        gbc_txtUser_7.anchor = GridBagConstraints.NORTH;
        gbc_txtUser_7.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtUser_7.insets = new Insets(0, 0, 5, 5);
        gbc_txtUser_7.gridx = 1;
        gbc_txtUser_7.gridy = 18;
        theWindow.getContentPane().add(txtUser_7, gbc_txtUser_7);
        txtUser_7.setColumns(10);
        
        JButton btnSet_7 = new JButton("set");
        GridBagConstraints gbc_btnSet_7 = new GridBagConstraints();
        gbc_btnSet_7.anchor = GridBagConstraints.SOUTHWEST;
        gbc_btnSet_7.insets = new Insets(0, 0, 5, 5);
        gbc_btnSet_7.gridx = 2;
        gbc_btnSet_7.gridy = 18;
        theWindow.getContentPane().add(btnSet_7, gbc_btnSet_7);
        btnSet_7.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                double currDou=Double.parseDouble(txtUser_7.getText());
                cit=currDou;
            }
        });
        
        JLabel lblCurrentAnnouncement = new JLabel("Current Announcement:");
        GridBagConstraints gbc_lblCurrentAnnouncement = new GridBagConstraints();
        gbc_lblCurrentAnnouncement.anchor = GridBagConstraints.EAST;
        gbc_lblCurrentAnnouncement.insets = new Insets(0, 0, 5, 5);
        gbc_lblCurrentAnnouncement.gridx = 4;
        gbc_lblCurrentAnnouncement.gridy = 18;
        theWindow.getContentPane().add(lblCurrentAnnouncement, gbc_lblCurrentAnnouncement);
        
        lblTheRainIn = new JLabel(announ);
        GridBagConstraints gbc_lblTheRainIn = new GridBagConstraints();
        gbc_lblTheRainIn.anchor = GridBagConstraints.WEST;
        gbc_lblTheRainIn.insets = new Insets(0, 0, 5, 0);
        gbc_lblTheRainIn.gridx = 5;
        gbc_lblTheRainIn.gridy = 18;
        theWindow.getContentPane().add(lblTheRainIn, gbc_lblTheRainIn);
        
        JLabel lblTimemilitary = new JLabel("Time (military):");
        GridBagConstraints gbc_lblTimemilitary = new GridBagConstraints();
        gbc_lblTimemilitary.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblTimemilitary.insets = new Insets(0, 0, 5, 5);
        gbc_lblTimemilitary.gridx = 0;
        gbc_lblTimemilitary.gridy = 19;
        theWindow.getContentPane().add(lblTimemilitary, gbc_lblTimemilitary);
        
        lblTime = new JLabel(timeH+":"+timeM+":"+timeS);
        GridBagConstraints gbc_lblTime = new GridBagConstraints();
        gbc_lblTime.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblTime.insets = new Insets(0, 0, 5, 5);
        gbc_lblTime.gridx = 1;
        gbc_lblTime.gridy = 19;
        theWindow.getContentPane().add(lblTime, gbc_lblTime);
        
        JLabel lblLightsStatus = new JLabel("Lights Status:");
        GridBagConstraints gbc_lblLightsStatus = new GridBagConstraints();
        gbc_lblLightsStatus.anchor = GridBagConstraints.EAST;
        gbc_lblLightsStatus.insets = new Insets(0, 0, 5, 5);
        gbc_lblLightsStatus.gridx = 4;
        gbc_lblLightsStatus.gridy = 19;
        theWindow.getContentPane().add(lblLightsStatus, gbc_lblLightsStatus);
        
        lblLightsOn = new JLabel(oLights(lights));
        GridBagConstraints gbc_lblLightsOn = new GridBagConstraints();
        gbc_lblLightsOn.anchor = GridBagConstraints.WEST;
        gbc_lblLightsOn.insets = new Insets(0, 0, 5, 0);
        gbc_lblLightsOn.gridx = 5;
        gbc_lblLightsOn.gridy = 19;
        theWindow.getContentPane().add(lblLightsOn, gbc_lblLightsOn);
        
        JLabel lblTimeOverridehours = new JLabel("Time Override (military, hours):");
        GridBagConstraints gbc_lblTimeOverridehours = new GridBagConstraints();
        gbc_lblTimeOverridehours.anchor = GridBagConstraints.EAST;
        gbc_lblTimeOverridehours.insets = new Insets(0, 0, 5, 5);
        gbc_lblTimeOverridehours.gridx = 0;
        gbc_lblTimeOverridehours.gridy = 20;
        theWindow.getContentPane().add(lblTimeOverridehours, gbc_lblTimeOverridehours);
        
        txtUser_8 = new JTextField();
        txtUser_8.setText("user #");
        GridBagConstraints gbc_txtUser_8 = new GridBagConstraints();
        gbc_txtUser_8.anchor = GridBagConstraints.NORTH;
        gbc_txtUser_8.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtUser_8.insets = new Insets(0, 0, 5, 5);
        gbc_txtUser_8.gridx = 1;
        gbc_txtUser_8.gridy = 20;
        theWindow.getContentPane().add(txtUser_8, gbc_txtUser_8);
        txtUser_8.setColumns(10);
        
        JButton btnSet_8 = new JButton("set");
        GridBagConstraints gbc_btnSet_8 = new GridBagConstraints();
        gbc_btnSet_8.anchor = GridBagConstraints.SOUTHWEST;
        gbc_btnSet_8.insets = new Insets(0, 0, 5, 5);
        gbc_btnSet_8.gridx = 2;
        gbc_btnSet_8.gridy = 20;
        theWindow.getContentPane().add(btnSet_8, gbc_btnSet_8);
        btnSet_8.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                double currDou=Double.parseDouble(txtUser_8.getText());
                timeO=currDou;
            }
        });
        
        JLabel lblEbrakeState = new JLabel("E-Brake State:");
        GridBagConstraints gbc_lblEbrakeState = new GridBagConstraints();
        gbc_lblEbrakeState.anchor = GridBagConstraints.EAST;
        gbc_lblEbrakeState.insets = new Insets(0, 0, 5, 5);
        gbc_lblEbrakeState.gridx = 4;
        gbc_lblEbrakeState.gridy = 20;
        theWindow.getContentPane().add(lblEbrakeState, gbc_lblEbrakeState);
        
        lblEngaged_2 = new JLabel(bTout(eBrake));
        GridBagConstraints gbc_lblEngaged_2 = new GridBagConstraints();
        gbc_lblEngaged_2.anchor = GridBagConstraints.WEST;
        gbc_lblEngaged_2.insets = new Insets(0, 0, 5, 0);
        gbc_lblEngaged_2.gridx = 5;
        gbc_lblEngaged_2.gridy = 20;
        theWindow.getContentPane().add(lblEngaged_2, gbc_lblEngaged_2);
        
        JButton btnTimeOverrideToggle = new JButton("Time Override Toggle");
        GridBagConstraints gbc_btnTimeOverrideToggle = new GridBagConstraints();
        gbc_btnTimeOverrideToggle.anchor = GridBagConstraints.NORTHEAST;
        gbc_btnTimeOverrideToggle.insets = new Insets(0, 0, 0, 5);
        gbc_btnTimeOverrideToggle.gridx = 0;
        gbc_btnTimeOverrideToggle.gridy = 21;
        theWindow.getContentPane().add(btnTimeOverrideToggle, gbc_btnTimeOverrideToggle);
        btnTimeOverrideToggle.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                timeOT=bToggle(timeOT);
            }
        });
        
        lblTimeOverride = new JLabel(bTout(timeOT));
        GridBagConstraints gbc_lblTimeOverride = new GridBagConstraints();
        gbc_lblTimeOverride.anchor = GridBagConstraints.WEST;
        gbc_lblTimeOverride.insets = new Insets(0, 0, 0, 5);
        gbc_lblTimeOverride.gridx = 1;
        gbc_lblTimeOverride.gridy = 21;
        theWindow.getContentPane().add(lblTimeOverride, gbc_lblTimeOverride);
        
        JButton btnEbrakeOverrideToggle = new JButton("E-Brake Override Toggle");
        GridBagConstraints gbc_btnEbrakeOverrideToggle = new GridBagConstraints();
        gbc_btnEbrakeOverrideToggle.anchor = GridBagConstraints.NORTHEAST;
        gbc_btnEbrakeOverrideToggle.insets = new Insets(0, 0, 0, 5);
        gbc_btnEbrakeOverrideToggle.gridx = 4;
        gbc_btnEbrakeOverrideToggle.gridy = 21;
        theWindow.getContentPane().add(btnEbrakeOverrideToggle, gbc_btnEbrakeOverrideToggle);
        btnEbrakeOverrideToggle.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                eBrakeOT=iToggle(eBrakeOT);
            }
        });
        
        lblEngaged_3 = new JLabel(iTfail(eBrakeOT));
        GridBagConstraints gbc_lblEngaged_3 = new GridBagConstraints();
        gbc_lblEngaged_3.anchor = GridBagConstraints.WEST;
        gbc_lblEngaged_3.gridx = 5;
        gbc_lblEngaged_3.gridy = 21;
        theWindow.getContentPane().add(lblEngaged_3, gbc_lblEngaged_3);
        theWindow.setVisible(true);
        
        
    }
    
    // toggle for engaged/disengaged options
    private boolean bToggle(boolean currboo)
    {
        if(currboo)
        {
            currboo=false;
        }
        else
        {
            currboo=true;
        }     
        return currboo;
    }
    
    // String builder for boolean toggles
    private String bTout(boolean currboo)
    {
        String outs;
        if(currboo)
        {
            outs="Engaged";
        }
        else
        {
            outs="Disengaged";
        }
        return outs;
    }
    
    // String builder for failure modes
    private String bFout(boolean currboo)
    {
        String outs;
        if(currboo)
        {
            outs="Failure";
        }
        else
        {
            outs="No Failure";
        }
        return outs;
    }
    
    // creates the string for the pause state
    private String bPause(boolean currboo)
    {
        String outs;
        if(currboo)
        {
            outs="Paused";
        }
        else
        {
            outs="Running";
        }
        return outs;
    }
    
    // creates the string for the tick control
    private String bTick(boolean currboo)
    {
        String outs;
        if(currboo)
        {
            outs="Test Control";
        }
        else
        {
            outs="CTC Control";
        }
        return outs;
    }
    
    // the toggle method for failure modes and brakes
    private int iToggle(int currint)
    {
        if(currint<2)
        {
            currint++;
        }
        else
        {
            currint=0;
        }
        return currint;
    }
    
    //creates the string for the failure overrides
    private String iTfail(int currint)
    {
        String outs;
        if(currint==0)
        {
            outs="Disengaged";
        }
        else if(currint==1)
        {
            outs="Forced Fail";
        }
        else
        {
            outs="Forced Pass";
        }
        return outs;
    }
    
    // creates the string for the brake overrides
    private String iTbrake(int currint)
    {
        String outs;
        if(currint==0)
        {
            outs="Override Off";
        }
        else if(currint==1)
        {
            outs="Forced Brake";
        }
        else
        {
            outs="Forced No Brake";
        }
        return outs;
    }
    
    // creates the string for the doors status
    private String oDoors(boolean currboo)
    {
        String outs;
        if(currboo)
        {
            outs="Open";
        }
        else
        {
            outs="Closed";
        }
        return outs;
    }
    
    // creates the string for the lights status
    private String oLights(boolean currboo)
    {
        String outs;
        if(currboo)
        {
            outs="On";
        }
        else
        {
            outs="Off";
        }
        return outs;
    }
    
    // divides the time into seconds, minutes, and hours
    private void timeCalc(double ntime)
    {
        timeS=(int)(ntime%60);
        timeM=(int)((ntime/60)%60);
        timeH=(int)(ntime/3600);
    }
    
    public void setVisible(boolean b){
    	theWindow.setVisible(b);
    }
    
    public void setFrameLoc(int x, int y){
    	theWindow.setLocation(x, y);
    }
    
    // creates the string for the time
    private String timeString()
    {
        if(timeS<10)
        {
            if(timeM<10)
            {
                return timeH+":0"+timeM+":0"+timeS;
            }
            else
            {
                return timeH+":"+timeM+":0"+timeS;
            }
        }
        else if(timeM<10)
        {
            return timeH+":0"+timeM+":"+timeS;
        }
        else
        {
            return timeH+":"+timeM+":"+timeS;
        }
    }
    
    // updates UI
    public ResponseUI updateUI(TrainController uiTNC)
    {
        //do not update if paused
        if(!pause)
        {
        		// take values from TNC
            currVel=Math.round(uiTNC.currspeed*1000)/1000;
            currPow=Math.round(uiTNC.power*1000)/1000;
            speedLimit=uiTNC.slimit;
            aut=Math.round(uiTNC.autspeed*1000)/1000;
            plcVel=Math.round(uiTNC.safespeed*1000)/1000;
            tbn=uiTNC.thisBlock.id;
            timeCalc(uiTNC.time);
            trainEF=uiTNC.engineFail;
            signalPF=uiTNC.signalFail;
            brakeF=uiTNC.brakeFail;
            brokenR=uiTNC.railFail;
            trackCF=uiTNC.trackFail;
            powerF=uiTNC.powerFail;
            sBrake=uiTNC.sBrake;
            eBrake=uiTNC.eBrake;
            doors=uiTNC.doors;
            announ=uiTNC.currAnnoun;
            lights=uiTNC.lights;
            
            //update UI labels
            lblVel.setText(""+currVel);
            label.setText(""+currTrain);
            lblPause.setText(bPause(pause));
            lblEngaged_CVOT.setText(bTout(cvoT));
            lblFail.setText(bFout(trainEF));
            lblPow.setText(""+currPow);
            lblFail_1.setText(iTfail(trainEFoT));
            lblSetVel.setText(""+currSetpoint);
            lblFail_2.setText(bFout(signalPF));
            lblFail_3.setText(iTfail(signalPFoT));
            lblVelLimit.setText(""+speedLimit);
            lblFail_4.setText(bFout(brakeF));
            lblFail_5.setText(iTfail(brakeFoT));
            lblEngaged_5.setText(bTout(sloT));
            lblFail_6.setText(bFout(brokenR));
            lblAutVel.setText(""+aut);
            lblFail_7.setText(iTfail(brokenRoT));
            lblFail_8.setText(bFout(trackCF));
            lblEngaged_6.setText(bTout(autOT));
            lblFail_9.setText(iTfail(trackCFoT));
            lblPlcVel.setText(""+plcVel);
            lblFail_10.setText(bFout(powerF));
            lblBlock.setText(""+tbn);
            lblFail_11.setText(iTfail(powerFoT));
            lblEngaged.setText(bTout(sBrake));
            lblBlockOverride.setText(bTout(tbnOT));
            lblEngaged_1.setText(iTbrake(sBrakeOT));
            lblIdealtemp.setText(""+cit);
            lblClosed.setText(oDoors(doors));
            lblTheRainIn.setText(announ);
            lblTime.setText(timeString());
            lblLightsOn.setText(oLights(lights));
            lblEngaged_2.setText(bTout(eBrake));
            lblTimeOverride.setText(bTout(timeOT));
            lblEngaged_3.setText(iTfail(eBrakeOT));
            
            //send response to Train Controller
            ResponseUI tncRes=new ResponseUI(cvo, cvoT, currSetpoint, slo, sloT, autO, autOT, tbnO, tbnOT, 
                timeO, timeOT, trainEFoT, signalPFoT, brakeFoT, brokenRoT, trackCFoT, powerFoT, sBrakeOT, 
                eBrakeOT, cit, false);
            return tncRes;
        }
        else
        {
            //send paused response to Train Controller if Paused.
            ResponseUI tncRes=new ResponseUI(cvo, cvoT, currSetpoint, slo, sloT, autO, autOT, tbnO, tbnOT, 
                timeO, timeOT, trainEFoT, signalPFoT, brakeFoT, brokenRoT, trackCFoT, powerFoT, sBrakeOT, 
                eBrakeOT, cit, true);
            return tncRes;
        }
    }
    
}
