package CTCOffice;

import javax.swing.*;
import TKM.*;
import processing.core.*;


public class DisplayFrame extends JFrame{
	public DisplayFrame(TrackLayout tl){
		this.setSize(1500, 900); //The window Dimensions
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        //panel.setBounds(0, 0, 750, 600);
        PApplet sketch = new CTCOffice();
        panel.setSize(750, 800);
        panel.add(sketch);
        this.add(panel);
        TrackMapPanel TLpanel = new TrackMapPanel(tl);
        //TLpanel.setBounds(750, 600, 750, 600);
        this.add(TLpanel);
        sketch.init(); //this is the function used to start the execution of the sketch
        this.setVisible(true);
        this.setTitle("Breathless Bovine: CTC Office         by Jake Lyons");
	}
}