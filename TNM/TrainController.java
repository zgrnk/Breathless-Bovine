package TNM;
import TKM.*;

/**
 * NSECS-TNC INDIVIDUAL CONTROL MODULE
 * REQUIRES EXTERNAL CLASS TO ACTIVATE
 * EITHER TNM or TNC-UI
 * 
 * @author Ben Tomasulo 
 * @version 4/22/2013
 */
public class TrainController
{
    // instance variables
    public double power;
    public boolean sBrake;
    public boolean eBrake;
    public boolean lights;
    public boolean doors;
    public double tTemp;
    public Block thisBlock;
    public Block oldBlock;
    public double time;
    public double setpoint;
    public double autspeed;
    public double slimit;
    public double safespeed;
    public double currspeed;
    public final double maxpower=120000;
    public boolean signalFail;
    public boolean brakeFail;
    public boolean engineFail;
    public boolean railFail;
    public boolean trackFail;
    public boolean powerFail;
    public int stationState;
    public double dtime;
    public String currAnnoun;
    public double tperiod;
    public final double Kp=800;
    public final double Ki=200;
    public double ukprev;
    public double prerr;
    public double ukOne;
    public double errOne;
    public double ukTwo;
    public double errTwo;
    public double ukThree;
    public double errThree;
    public boolean opstate;
    public String nextName;
    public PowJar powone;
    public PowJar powtwo;
    public PowJar powthree;
    public double spone;
    public double sptwo;
    public double spthree;
    public int checkid;
    
    // create test blocks
    Block negout = new Block(-4, "A", "I", 100.0, 3.0, 60.0, false, false, true, false, false, "You found the secret stop, exit now to collect 100 rupies!", false, false, false);
    Block negone = new Block(-1, "B", "II", 100.0, 3.0, 55.0, false, false, false, false, false, "Arriving at 0", false, false, false);
    
    
    /**
     * Constructor for objects of class TrainController
     */
    public TrainController()
    {
        // initialise instance variables
        tTemp=22;
        sBrake=true;
        eBrake=false;
        lights=false;
        doors=true;
        thisBlock=negone;
        oldBlock=thisBlock;
        time=-1;
        setpoint=100;
        autspeed=-1;
        slimit=-1;
        setLights();
        safespeed=0;
        spone=0;
        sptwo=0;
        spthree=0;
        currspeed=0;
        signalFail=false;
        brakeFail=false;
        engineFail=false;
        railFail=thisBlock.brokenRailFailure;
        trackFail=thisBlock.trackCircuitFailure;
        powerFail=thisBlock.powerFailure;
        stationState=0;
        currAnnoun="";
        tperiod=1;
        ukprev=0;
        prerr=0;
        opstate=false;
        nextName=null;
        checkid=-1;
    }

    // Sets target temperature
    public void setTTemp(double y)
    {
        this.tTemp=y;
    }
    
    // Turns lights on and off
    public void setLights()
    {
        if((!thisBlock.isUground)&&(time<68400)&&(time>21600)
        &&(!oldBlock.isUground))
        {
            lights=false;
        }
        else
        {
            lights=true;
        }
    }
      
    // lights override toggle
    public void toggleLightsOverride()
    {
        if(lights)
        {
            lights=false;
        }
        else
        {
            lights=true;
        }
    }
    
    // eBrake toggle
    public void toggleEBrake()
    {
        if(eBrake)
        {
            lights=false;
        }
        else
        {
            lights=true;
        }
    }
    
    // set setpoint
    public void setSetpoint(double y)
    {
        setpoint=y;
    }
    
    // set safe power, set brakes if needed
    public PowJar setPower()
    {
        // coast if negative power request
        double thispower=0;
        double thisuk=0;
        double thiserr=0;
        boolean thiseBrake=eBrake;
        boolean thissBrake=sBrake;
        
        if(currspeed==0)
        {
            thisuk=0;
        }
        if(railFail||trackFail)
        {
            // cuts engine power, activates the eBrake, and releases passengers after a stop
            // has been reached for more serious problems.
            thiseBrake=true;
        }
        else if(powerFail)
        {
            // service brake activates if there is a power failure, train will 
            // resume when power is restured
            thissBrake=true;
        }
        else if(engineFail||brakeFail||signalFail)
        {
            // cuts engine power if the above problems are detected
            // releases passengers when stopped
        }
        else
        {
            if((currspeed < safespeed)&&(!eBrake))
            {
                //disengage service brake and close doors if needed
                thissBrake=false;
                doors=false;
                
                // increase power
                thispower=(safespeed-currspeed)*Kp+Ki*(ukprev+tperiod*(safespeed-currspeed+prerr)/2);
                thiserr=safespeed-currspeed;
                // engine can't deliver more power than max
                if(power>maxpower)
                {
                    thispower=maxpower;
                }
                // update Uk otherwise
                else
                {
                    thisuk=ukprev+tperiod*(safespeed-currspeed+prerr)/2;
                }
            }
            else if(currspeed+1>safespeed)
            {
                // engage service brake if going 1m/s or more over safe speed
                thissBrake=true;
                thiserr=safespeed-currspeed;
                thisuk=ukprev+tperiod*(safespeed-currspeed+prerr)/2;
            }
            else
            {
                // coast if going over safe speed but less than 1m/s over
                thissBrake=false;
                thiserr=safespeed-currspeed;
                thisuk=ukprev+tperiod*(safespeed-currspeed+prerr)/2;
            }
        }
        
        PowJar currJar=new PowJar(thispower, thisuk, thiserr, thiseBrake, thissBrake);
        return currJar;
    }
    
    // sets safe speed
    public double setSafeSpeed()
    {
        double thisspeed=0;
        //checks which speed is the safest
        if((autspeed<slimit)&&(autspeed<setpoint))
        {
            thisspeed=autspeed;
        }
        else if((slimit<autspeed)&&(slimit<setpoint))
        {
            thisspeed=slimit;
        }
        else
        {
            thisspeed=setpoint;
        }
        //slows down if approaching station
        if((nextName!=null)&&(nextName.length()!=0)&&(thisspeed>5))
        {
            thisspeed=5;
        }
        return thisspeed;
    }
    
    // timeTick method that updates Train Controller from Train Model
    public ResponseTNC timeTick(double ntime, double curVelocity, double period, Block positionBlock, 
    Block positionBlockTail, boolean issetSignalPickupFailure, boolean issetEngineFailure, 
    boolean issetBrakeFailure, double fixedSuggestedSpeed, double mboSuggestedSpeed, 
    boolean issetEmerBrake, boolean operator, String nextStationName, boolean uiChosen)
    {
       //update info from Train Model
       time=ntime;
       tperiod=period;
       currspeed=curVelocity;
       thisBlock=positionBlock;
       oldBlock=positionBlockTail;
       signalFail=issetSignalPickupFailure;
       engineFail=issetEngineFailure;
       opstate=operator;
       brakeFail=issetBrakeFailure;
       railFail=positionBlock.brokenRailFailure;
       trackFail=positionBlock.trackCircuitFailure;
       powerFail=positionBlock.powerFailure;
       autspeed=fixedSuggestedSpeed;
       if(mboSuggestedSpeed<autspeed)
       {
       //    autspeed=mboSuggestedSpeed;
       }
       slimit=positionBlock.speedLimit;
       nextName=nextStationName;
       currAnnoun=setAnnouncement();
        
       // begin redundant PLC actions for critical systems
       spone=setSafeSpeed();
       sptwo=setSafeSpeed();
       spthree=setSafeSpeed();
       safespeed=resolveSpeed(spone, sptwo, spthree);
       powone=setPower();
       powtwo=setPower();
       powthree=setPower();
       power=resolvePower(powone, powtwo, powthree);
       
       // do noncritical calculations
       setLights();
       checkStation();
       setDoors();
       
       if(uiChosen)
       {
           updateUI();
       }
       ResponseTNC tnmSignal=new ResponseTNC(power, sBrake, eBrake, lights, doors, tTemp, currAnnoun);
        
       return tnmSignal; 
    }
    
    // resolves any PLC conflicts about proper safe speed
    public double resolveSpeed(double firsts, double seconds, double thirds)
    {
        // ignores differences past 3 decimal places
        int firsti=(int)(Math.round(firsts*1000));
        int secondi=(int)(Math.round(seconds*1000));
        int thirdi=(int)(Math.round(thirds*1000));
        
        //compares PLC values
        if((firsti==secondi)||(firsti==thirdi))
        {
            return firsts;
        }
        else if(secondi==thirdi)
        {
            return seconds;
        }
        else
        {
            // if two or more PLCs are broken, the safe speed is set to 0
            System.out.println("Error: No Majority PLC speed");
            return 0;
        }
    }
    
    // resolves any PLC conflicts about proper power output
    public double resolvePower(PowJar firsts, PowJar seconds, PowJar thirds)
    {
        // ignores differences past 3 decimal places
        int firsti=(int)(Math.round(firsts.jPower*1000));
        int secondi=(int)(Math.round(seconds.jPower*1000));
        int thirdi=(int)(Math.round(thirds.jPower*1000));
        
        //compares PLC values
        if((firsti==secondi)||(firsti==thirdi))
        {
            eBrake=firsts.jEBrake;
            sBrake=firsts.jSBrake;
            return firsts.jPower;
        }
        else if(secondi==thirdi)
        {
            eBrake=seconds.jEBrake;
            sBrake=seconds.jSBrake;
            return seconds.jPower;
        }
        else
        {
            // if two or more PLCs are broken, the power is set to 0 and the emergency brake is engaged
            System.out.println("Error: No Majority PLC power");
            eBrake=true;
            return 0;
        }
    }
    
    // public ResponseUI updateUI()
    public void updateUI()
    {
        
    }
    
    // sets the doors open when the train is not moving
    public void setDoors()
    {
        if((currspeed!=0)||(stationState==2))
        {
            doors=false;
        }
        else
        {
            doors=true;
        }
    }
        
    // sets the current announcements
    public String setAnnouncement()
    {
        String thisAnnounce="Announcement Error";
        if((thisBlock.stationName==null)||(thisBlock.stationName.length()==0))
        {
            if((nextName==null)||(nextName.length()==0))
            {
                thisAnnounce="";
            }
            else
            {
                thisAnnounce="Next Station: "+nextName;
            }
        }
        else
        {
            thisAnnounce="Now arriving at "+thisBlock.stationName+" Station";
        }
        return thisAnnounce;
    }
    
    // takes action if approaching station
    public void checkStation()
    {
        if(thisBlock.isStation||thisBlock.isYard)
        {
            if(!opstate)
            {
                power=0;
                sBrake=true;
            }
            else if(stationState==0)
            {
                power=0;
                sBrake=true;
                if(currspeed==0)
                {
                    dtime=time;
                    stationState++;
                }
            }
        }
        if(stationState==1)
        {
            power=0;
            sBrake=true;
            if(time>=dtime+35)
            {
                doors=false;
                stationState++;
            }
        }
        else if(checkid!=thisBlock.id)
        {
            stationState=0;
            checkid=thisBlock.id;
        }
    }
}
