package CTCOffice;

import javax.swing.JFrame;
import processing.core.*;


public class DisplayFrame extends JFrame{
	public DisplayFrame(){
		this.setSize(1000, 800); //The window Dimensions
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setBounds(20, 20, 1000, 800);
        PApplet sketch = new CTCOffice();
        panel.add(sketch);
        this.add(panel);
        sketch.init(); //this is the function used to start the execution of the sketch
        //this.setVisible(true);
        this.setTitle("Breathless Bovine: CTC Office         by Jake Lyons");
	}
}