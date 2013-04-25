package TNM;

import TKM.*;


/**
 * NSECS-TNC TEST INTERFACE
 * 
 * @author (Ben Tomasulo) 
 * @version (4/24/2013)
 */
public class TNC_Test
{
    public static TNC_UI tncUI;
    public static TrainController testTNC;
    public static Block negout = new Block(-3, "A", "I", 100.0, 3.0, 60.0, false, false, true, false, false, "You found the secret stop, exit now to collect 100 rupies!", false, false, false);
    public static Block negone = new Block(-1, "B", "II", 100.0, 3.0, 55.0, false, false, false, false, false, "", false, false, false);
    public static void main(String [] args)
    {
        testTNC=new TrainController();
        tncUI=new TNC_UI(true);
        for(int i=28800; i<86400; i++)
        {
            ResponseUI tncData=tncUI.updateUI(testTNC);
            testTNC.updateUI(tncData);
            testTNC.timeTick(i,-1, 1, negone, negout, false, false, false, 100, 100, false, true, negone.transponderMessage);
            long t0,t1;
            t0=System.currentTimeMillis();
            do{
                t1=System.currentTimeMillis();
            }
            while (t1-t0<100);
        }
    }
    
}
