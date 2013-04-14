package TKM;

/* TKM */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TKMGui extends JPanel implements ActionListener {

    private Switch selectedSwitch;
    private Block selectedBlock;

    private TrackLayout lyt;

    JComboBox cbBlock;
    JComboBox cbSwitch;

    JLabel lblElemId;
    JTextField fieldBlkSection;
    JTextField fieldBlkLength;
    JTextField fieldBlkGrade;
    JTextField fieldBlkSpeedLimit;
    JTextField fieldBlkStationName;

    JCheckBox cboxBlkBidir;
    JCheckBox cboxBlkUground;
    JCheckBox cboxBlkYard;
    JCheckBox cboxBlkCrossing;
    
    JCheckBox cboxSwDiv;
    JLabel lblTest;
    JButton bTest;
    
    TrackMapPanel pMap;


    public void loadGui() {

        lyt = new TrackLayout();

        lyt.parseTrackDB("track_db.csv");

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel pControl = new JPanel();
        JPanel pSelector = new JPanel();
        JPanel pInfo = new JPanel();
        JPanel pBlkInfo = new JPanel();
        JPanel pSwInfo = new JPanel();

        pMap = new TrackMapPanel(lyt);
        pControl.setLayout(new BoxLayout(pControl, BoxLayout.Y_AXIS));
        pInfo.setLayout(new BoxLayout(pInfo, BoxLayout.PAGE_AXIS));
        pBlkInfo.setLayout(new GridLayout(0,2));
        pSwInfo.setLayout(new GridLayout(0,2));

        cbBlock = new JComboBox(lyt.getBlocks().toArray());
        cbSwitch = new JComboBox(lyt.getSwitches().toArray());
        cbBlock.setMaximumSize(new Dimension(200,20));
        cbSwitch.setMaximumSize(new Dimension(200,20));

        cbBlock.setSelectedIndex(0);
        cbSwitch.setSelectedIndex(0);
        cbBlock.addActionListener(this);
        cbSwitch.addActionListener(this);

        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        //Create and set up the window.
        JFrame frame = new JFrame("TKM Proto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        lblElemId = new JLabel();
        lblElemId.setMaximumSize(new Dimension(200,13));
        fieldBlkSection = new JTextField();
        fieldBlkSection.setMaximumSize(new Dimension(200,13));
        fieldBlkLength = new JTextField();
        fieldBlkLength.setMaximumSize(new Dimension(200,13));
        fieldBlkGrade = new JTextField();
        fieldBlkGrade.setMaximumSize(new Dimension(200,13));
        fieldBlkSpeedLimit = new JTextField();
        fieldBlkSpeedLimit.setMaximumSize(new Dimension(200,13));
        fieldBlkStationName = new JTextField();
        fieldBlkStationName.setMaximumSize(new Dimension(200,13));
        
        cboxBlkBidir = new JCheckBox();
        cboxBlkUground = new JCheckBox();
        cboxBlkCrossing = new JCheckBox();


        cboxSwDiv = new JCheckBox();
        cboxSwDiv.addActionListener(this);

        /* Layout */
        //pSelector.add(new JLabel("Select element"));
        //pSelector.add(elemCombo);
        //pSelector.add(new JLabel("Element type"));
        //pSelector.add(lblElemId);
        
        pBlkInfo.add(new JLabel("Block Info"));
        pBlkInfo.add(new JLabel(""));
        pBlkInfo.add(new JLabel("Select Block"));
        pBlkInfo.add(cbBlock);
        pBlkInfo.add(new JLabel("Section"));
        pBlkInfo.add(fieldBlkSection);
        pBlkInfo.add(new JLabel("Length"));
        pBlkInfo.add(fieldBlkLength);
        pBlkInfo.add(new JLabel("Grade"));
        pBlkInfo.add(fieldBlkGrade);
        pBlkInfo.add(new JLabel("Speed Limit"));
        pBlkInfo.add(fieldBlkSpeedLimit);
        pBlkInfo.add(new JLabel("Station"));
        pBlkInfo.add(fieldBlkStationName);
        pBlkInfo.add(new JLabel("Bidirectional"));
        pBlkInfo.add(cboxBlkBidir);
        pBlkInfo.add(new JLabel("Underground"));
        pBlkInfo.add(cboxBlkUground);
        pBlkInfo.add(new JLabel("Is Crossing"));
        pBlkInfo.add(cboxBlkCrossing);
        
        pSwInfo.add(new JLabel("Switch Info"));
        pSwInfo.add(new JLabel(""));
        pSwInfo.add(new JLabel("Select Switch"));
        pSwInfo.add(cbSwitch);
        pSwInfo.add(new JLabel("Switched to divergent"));
        pSwInfo.add(cboxSwDiv);

        bTest = new JButton("Test train");
        bTest.addActionListener(this);
        
        lblTest = new JLabel("Test area");

        pInfo.add(pBlkInfo);
        pInfo.add(new JSeparator(JSeparator.HORIZONTAL));
        pInfo.add(pSwInfo);
        pInfo.add(Box.createVerticalGlue());
        //pControl.add(pSelector);
        pControl.add(pInfo);
        pControl.add(bTest);
        pControl.add(lblTest);
        
        
        pControl.add(Box.createVerticalGlue());
        pControl.setMaximumSize(new Dimension(400, 65535));
        pControl.setPreferredSize(new Dimension(400, 65535));

        add(pControl);
        add(pMap);

        //Create and set up the content pane.
        JComponent newContentPane = this;
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public TKMGui() {
        super();
    }
    
    public static void main(String[] args)
    {
        /* Create GUI */
        TKMGui gui = new TKMGui();
        gui.loadGui();
        //lyt.chooChoo();
    }
    
    public void updateSwInfo(Switch sw)
    {
        cboxSwDiv.setSelected(sw.state);
    }

    public void updateBlkInfo(Block blk)
    {
        lblElemId.setText("Block");

        /* Set block fields */
        fieldBlkSection.setText(blk.sectionId);
        fieldBlkLength.setText(Double.toString(blk.length));
        fieldBlkGrade.setText(Double.toString(blk.grade));
        fieldBlkSpeedLimit.setText(Double.toString(blk.speedLimit));
        if (blk.isStation) {
            fieldBlkStationName.setText(blk.stationName);
        } else {
            fieldBlkStationName.setText("No station");
        }
        cboxBlkBidir.setSelected(blk.isBidir);
        cboxBlkUground.setSelected(blk.isUground);
        cboxBlkCrossing.setSelected(blk.isCrossing);

        //pMap.setMarker(blk.mapX,blk.mapY);
        
    }

    public void startTrainTest() {
        int delay = 30; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                lyt.chooChoo(pMap);
            }
        };
        new Timer(delay, taskPerformer).start();
    }

    public void actionPerformed(ActionEvent e) {
		
                TrackElement elem;
                //String name;

		if (e.getSource() == cboxSwDiv) {
			selectedSwitch.state = ((JCheckBox)e.getSource()).isSelected();
                }
                
                if (e.getSource() == bTest) {
			startTrainTest();
		} 
                
                if ("comboBoxChanged".equals(e.getActionCommand())) {
                        if (e.getSource() == cbBlock) {
                                JComboBox cb = (JComboBox)e.getSource();
                                Block blk = (Block)cb.getSelectedItem();
                                lyt.setSelectedElement(blk);
                                updateBlkInfo(blk);
                                selectedBlock = blk;

                        } else if (e.getSource() == cbSwitch) {
                                JComboBox cb = (JComboBox)e.getSource();
                                Switch sw = (Switch)cb.getSelectedItem();
                                updateSwInfo(sw);
                                selectedSwitch = sw;
                        }
                }

                pMap.repaint();
    }

}
