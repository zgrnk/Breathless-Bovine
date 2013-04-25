package TNM;

/**
 * @class ResponseUI
 * 
 * @date 04/23/2013
 * 
 * @author Ben Tomasulo
 */


 

/**
 * This file contains the main container class which holds the response between a 
 * given train's controller and the UI after each timeTick.
 */
public class ResponseUI {
    public double cvo;
    public boolean cvoT;
    public double currSetpoint;
    public double slo;
    public boolean sloT;
    public double autO;
    public boolean autOT;
    public int tbnO;
    public boolean tbnOT;
    public double timeO;
    public boolean timeOT;
    public int trainEFoT;
    public int signalPFoT; 
    public int brakeFoT;
    public int brokenRoT;
    public int trackCFoT;
    public int powerFoT;
    public int sBrakeOT;
    public int eBrakeOT;
    public double cit;
    public boolean paused;
    
    public ResponseUI(double cvo, boolean cvoT, double currSetpoint, double slo, boolean sloT, 
        double autO, boolean autOT, int tbnO, boolean tbnOT, double timeO, boolean timeOT, 
        int trainEFoT, int signalPFoT, int brakeFoT, int brokenRoT, int trackCFoT, int powerFoT, 
        int sBrakeOT, int eBrakeOT, double cit, boolean paused) {
        this.cvo=cvo;
        this.cvoT=cvoT;
        this.currSetpoint=currSetpoint;
        this.slo=slo;
        this.sloT=sloT;
        this.autO=autO;
        this.autOT=autOT;
        this.tbnO=tbnO;
        this.tbnOT=tbnOT;
        this.timeO=timeO;
        this.timeOT=timeOT;
        this.trainEFoT=trainEFoT;
        this.signalPFoT=signalPFoT; 
        this.brakeFoT=brakeFoT;
        this.brokenRoT=brokenRoT;
        this.trackCFoT=trackCFoT;
        this.powerFoT=powerFoT;
        this.sBrakeOT=sBrakeOT;
        this.eBrakeOT=eBrakeOT;
        this.cit=cit;
        this.paused=paused;
    }
}