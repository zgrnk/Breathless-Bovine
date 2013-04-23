package CTCOffice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Dimension;
import java.util.*;

import javax.swing.*;

import TNM.*;
import SSC.*;
import TKC.util.ControllerUI;
import TKC.util.TrackController;
import TKM.*;

public class CTCGui extends JPanel implements ActionListener {

  private CTCOffice office;
  private JLabel lblSimClk;
  private JButton btnStartPause;
  private JTabbedPane tPane;

	javax.swing.Timer tmFrame;

  private JComponent pCtc;
  private JComponent pTkm;
  private JComponent pTnm;
  private JComponent pSim;
  private JComponent pTkc;

	public static void main(String[] args) {
		new CTCGui();
	}

  public CTCGui()
  {
    TrackLayout tl = new TrackLayout();
    tl.parseTrackDB("track_db.csv");


    TrainModelUI tnmUi = new TrainModelUI();
    office = new CTCOffice(tl, tnmUi);
    TrackController tkc = new TrackController(tl, office);
    ControllerUI tkcUi = new ControllerUI(tkc);
    office.setTrackController(tkc);

		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				guiRefresh();
			}
		};
		tmFrame = new javax.swing.Timer(100, taskPerformer);

    
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    /* Global simulation stuff */
    pSim = new JPanel();
    pSim.setLayout(new BoxLayout(pSim, BoxLayout.Y_AXIS));
    pSim.setMaximumSize(new Dimension(200, 1000));

    lblSimClk = new JLabel("00:00:00");
    btnStartPause = new JButton("Start");
    btnStartPause.addActionListener(this);

    pSim.add(lblSimClk);
    pSim.add(btnStartPause);
    pSim.add(Box.createVerticalGlue());

    /* CTC UI - not global simulation stuff */
    pCtc = new JPanel();
    pCtc.add(new JLabel("CTC panel"));
    pCtc.add(new JLabel("- Add/remove track"));
    pCtc.add(new JLabel("- Look at stuff"));
    
    pTkm = new TKMGui(tl);
    pTkc = tkcUi.getGuiPanel();
    pTnm = tnmUi.getGuiPanel();

    tPane = new JTabbedPane();
    tPane.addTab("CTC", pCtc);
    tPane.addTab("Track Map", pTkm);
    tPane.addTab("Trains", pTnm);
    tPane.addTab("Wayside Controllers", pTkc);
    //tPane.setMaximumSize(new Dimension(400,2000));

    add(pSim);
    add(tPane);
    //add(new TrackMapPanel(tl));

    JFrame mainWin = new JFrame();
    mainWin.setContentPane(this);
    mainWin.setVisible(true);
    
		tmFrame.start();
  }

  public void guiRefresh()
  {
    DateFormat df = new SimpleDateFormat("HH:mm:ss");
    lblSimClk.setText(df.format(office.getSimClk()));
    office.frameStep();
    pTkm.repaint();
  }

  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == btnStartPause) {
      if (office.simRunning) {
        office.pause();
        btnStartPause.setText("Resume");
      } else {
        office.resume();
        btnStartPause.setText("Pause");
      }
    }
  }

}
