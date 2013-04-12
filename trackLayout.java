import processing.core.PApplet;
import controlP5.Canvas;

	class trackLayout extends Canvas {

	  int y;

	  public void setup(PApplet theApplet) {
	    y = 200;
	  }  

	  public void draw(PApplet p) {
	    // renders a square with randomly changing colors
	    // make changes here.
	    p.fill(100);
	    p.rect(p.mouseX-20, y-20, 240, 30);
	    p.fill(255);
	    p.text("This text is drawn by MyCanvas", p.mouseX,y);
	  }
	}