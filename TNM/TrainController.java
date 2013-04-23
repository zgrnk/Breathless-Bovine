package TNM;
import TKM.*;

/**
 * NSECS-TNC INDIVIDUAL CONTROL MODULE
 * REQUIRES EXTERNAL CLASS TO ACTIVATE
 * EITHER TNM or TNC-UI
 * 
 * @author Ben Tomasulo 
 * @version 4/12/2013
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
    public double speedlimit;
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
    public final double Kp=8000;
    public final double Ki=300;
    public double ukprev;
    public double prerr;
    public boolean opstate;
    public String nextName;
    
    // initialize fake blocks
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
        autspeed=100;
        speedlimit=-1;
        setLights();
        safespeed=0;
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
    public void setPower()
    {
        if(railFail||trackFail)
        {
            // cuts engine power, activates the eBrake, and releases passengers after a stop
            // has been reached for more serious problems.
            power=0;
            eBrake=true;
            if (currspeed==0)
            {
                doors=true;
            }
        }
        else if(engineFail||brakeFail)
        {
            // cuts engine power if a moderate problem is detected
            // releases passengers when stopped
            power=0;
            if (currspeed==0)
            {
                doors=true;
            }
            
        }
        else if(signalFail||powerFail)
        {
            // cuts engine power if a minor problem is detected
            // train will resume operation if the problem is fixed.
            power=0;
        }
        else
        {
            if((currspeed < safespeed)&&(!eBrake))
            {
                //disengage service brake and close doors if needed
                sBrake=false;
                doors=false;
                
                // increase power
                power=(safespeed-currspeed)*Kp+Ki*(ukprev+tperiod*(safespeed-currspeed+prerr)/2);
                prerr=safespeed-currspeed;
                // engine can't deliver more power than max
                if(power>maxpower)
                {
                    power=maxpower;
                }
                // coast if negative power request
                else if(power<0)
                {
                    power=0;
                }
                // update Uk otherwise
                else
                {
                    ukprev=ukprev+tperiod*(safespeed-currspeed+prerr)/2;
                }
            }
            else if(currspeed+5>safespeed)
            {
                // engage service brake if going 5km/h or more over safe speed
                sBrake=true;
                power=0;
                prerr=safespeed-currspeed;
                ukprev=ukprev+tperiod*(safespeed-currspeed+prerr)/2;
            }
            else
            {
                // coast if going over safe speed but less than 5km/h over
                sBrake=false;
                power=0;
                prerr=safespeed-currspeed;
                ukprev=ukprev+tperiod*(safespeed-currspeed+prerr)/2;
            }
            // System.out.println("power: "+power);
        }
    }
    
    // sets safe speed
    public void setSafeSpeed()
    {
        if((autspeed<speedlimit)&&(autspeed<setpoint))
        {
            safespeed=autspeed;
        }
        else if((speedlimit<autspeed)&&(speedlimit<setpoint))
        {
            safespeed=speedlimit;
        }
        else
        {
            safespeed=setpoint;
        }
    }
    
    // timeTick method that updates Train Controller from Train Model
    public ResponseTNC timeTick(double ntime, double curVelocity, double period, Block positionBlock, 
    Block positionBlockTail, boolean issetSignalPickupFailure, boolean issetEngineFailure, 
    boolean issetBrakeFailure, double fixedSuggestedSpeed, double mboSuggestedSpeed, 
    boolean issetEmerBrake, boolean operator, String nextStationName)
    {
        time=ntime;
        tperiod=period;
        currspeed=curVelocity;
        thisBlock=positionBlock;
        oldBlock=positionBlockTail;
        signalFail=issetSignalPickupFailure;
        engineFail=issetEngineFailure;
        opstate=operator;
        // note that brake commands may still be sent even if brake failure is detected
        brakeFail=issetBrakeFailure;
        //autspeed=fixedSuggestedSpeed;
        if(mboSuggestedSpeed<autspeed)
        {
        //    autspeed=mboSuggestedSpeed;
        }
        speedlimit=positionBlock.speedLimit;
        nextName=nextStationName;
        currAnnoun=setAnnouncement();
        
       //begin computer actions
        setSafeSpeed();
        setPower();
        setLights();
        checkStation();
        ResponseTNC tnmSignal=new ResponseTNC(power, sBrake, eBrake, lights, doors, tTemp, currAnnoun);
        return tnmSignal;
        
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
                thisAnnounce=nextName;
            }
        }
        else
        {
            thisAnnounce=thisBlock.stationName;
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
				doors=true;
            }
            else if(stationState==0)
            {
                power=0;
                sBrake=true;
                if(currspeed==0)
                {
                    doors=true;
                    dtime=time;
                    stationState++;
                }
            }
            else if(stationState==1)
            {
                power=0;
                sBrake=true;
                if(time>=dtime+35)
                {
                    doors=false;
                    stationState++;
                }
            }
        }
		else
		{
			stationState=0;
		}
    }
}
