package TKM;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.AbstractList;
import java.util.ListIterator;
import TNM.Train;


/* TrackMapPanel will provide a sophisticated track map */
/* A comment */


public class TrackMapPanel extends JPanel implements MouseListener{

    //BufferedImage img;
    TrackLayout lyt;
    //AbstractList<Train> trainList;

    private int x;
    private int y;

    public TrackMapPanel(TrackLayout lyt) {
        //try {
        //    img = ImageIO.read(new File("map.png"));
        //} catch (IOException e) {}

        this.lyt = lyt;
        x = 0;
        y = 0;
    }

    private void drawTrain(Graphics g, Train tn) {
        /* Draw the train front */
        Block blk = tn.positionBlock;
        double dist_fact = tn.positionMeters/blk.length;
        int xFr = (int)((blk.mapX2 - blk.mapX1)*dist_fact + blk.mapX1);
        int yFr = (int)((blk.mapY2 - blk.mapY1)*dist_fact + blk.mapY1);
        double thetaFr = Math.atan2((blk.mapY2 - blk.mapY1),(blk.mapX2 - blk.mapX1));
        if (tn.positionDirection == Block.DIRECTION_REV) {
            thetaFr += Math.PI;
        }
        Graphics2D g2 = (Graphics2D) g;

        AffineTransform saveAt = g2.getTransform();
        AffineTransform dirAt = new AffineTransform();
        dirAt.rotate(thetaFr, xFr, yFr);
        g2.transform(dirAt);

        g2.setPaint(Color.BLUE);
        //g2.fill(new Ellipse2D.Double(xFr-8,yFr-4,16,8));
        g2.draw(new Line2D.Double(xFr,yFr,xFr-12,yFr-8));
        g2.draw(new Line2D.Double(xFr,yFr,xFr-12,yFr+8));
        g2.draw(new Line2D.Double(xFr-12,yFr-8,xFr-12,yFr+8));

        /* Restore saved transform */
        g2.setTransform(saveAt);
    }

    private void drawTrackBlock(Graphics g, Block blk) {
        Graphics2D g2 = (Graphics2D) g;

        Stroke s = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

        g2.setStroke(s);
        
        if (blk == lyt.getSelectedElement()) {
            g2.setPaint(Color.WHITE);
        } else if (blk.isOccupied()) {
            g2.setPaint(Color.YELLOW);
        } else {
            g2.setPaint(Color.RED);
        }

        
        //g.drawLine(blk.mapX1, blk.mapY1, blk.mapX2, blk.mapY2);
        g2.draw(new Line2D.Double(blk.mapX1, blk.mapY1, blk.mapX2, blk.mapY2));

        if (blk.isStation) {
            int xAvg = (blk.mapX1 + blk.mapX2)/2;
            int yAvg = (blk.mapY1 + blk.mapY2)/2;
            //g.fillOval(xAvg-4, yAvg-4, 8, 8);
            g2.fill(new Ellipse2D.Double(xAvg-5,yAvg-5,10,10));
            g.setColor(Color.BLACK);
            g.drawString(blk.stationName, xAvg + 15, yAvg + 15);
        }
            
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(img, 0, 0, null);

        /* Draw track */
        ListIterator<Block> iter = lyt.getBlocks().listIterator();

        while (iter.hasNext()) {
            drawTrackBlock(g, iter.next());
        }

        /* Draw trains */
        if (lyt.trains != null)
        {
            ListIterator<Train> tIter = lyt.trains.listIterator();

            while (tIter.hasNext()) {
                drawTrain(g, tIter.next());
            }
        }
    }

    public void MouseClicked(MouseEvent e) {}
    public void MouseEntered(MouseEvent e) {}
    public void MouseExited(MouseEvent e) {}
    public void MousePressed(MouseEvent e) {}
    public void MouseReleased(MouseEvent e) {}
}
