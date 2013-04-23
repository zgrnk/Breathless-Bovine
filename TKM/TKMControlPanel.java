package TKM;

/* TKM */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TKMControlPanel extends JPanel implements ActionListener {

    private Switch selectedSwitch;
    private Block selectedBlock;
    private TrackMapPanel pMap;

    private TrackLayout lyt;

    JComboBox cbLine;
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
    //JLabel lblTest;
    //JButton bTest;

    public TKMControlPanel(TrackLayout tl) {

        lyt = tl;
        JPanel pSelector = new JPanel();
        JPanel pInfo = new JPanel();
        JPanel pBlkInfo = new JPanel();
        JPanel pSwInfo = new JPanel();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


       pInfo.setLayout(new BoxLayout(pInfo, BoxLayout.PAGE_AXIS));
        pBlkInfo.setLayout(new GridLayout(0,2));
        pSwInfo.setLayout(new GridLayout(0,2));

        cbLine = new JComboBox(lyt.getLines().toArray());
        cbBlock = new JComboBox(lyt.redLine.getBlocks().toArray());
        cbSwitch = new JComboBox(lyt.redLine.getSwitches().toArray());
        cbLine.setMaximumSize(new Dimension(200,20));
        cbBlock.setMaximumSize(new Dimension(200,20));
        cbSwitch.setMaximumSize(new Dimension(200,20));

        cbBlock.setSelectedIndex(0);
        cbSwitch.setSelectedIndex(0);
        cbLine.setSelectedIndex(0);
        cbLine.addActionListener(this);
        cbBlock.addActionListener(this);
        cbSwitch.addActionListener(this);

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

        pBlkInfo.add(new JLabel("Select Line"));
        pBlkInfo.add(cbLine);
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

        //bTest = new JButton("Test train");
        //bTest.addActionListener(this);
        
        //lblTest = new JLabel("Test area");

        pInfo.add(pBlkInfo);
        pInfo.add(new JSeparator(JSeparator.HORIZONTAL));
        pInfo.add(pSwInfo);
        pInfo.add(Box.createVerticalGlue());

        this.add(pInfo);
        //this.add(bTest);
        //this.add(lblTest);
        
        this.add(Box.createVerticalGlue());
        this.setMaximumSize(new Dimension(400, 65535));
       // this.setPreferredSize(new Dimension(400, 65535));

    }

    public void setMapPanel(TrackMapPanel tmp) {
        pMap = tmp;
    }

    public void updateSwInfo(Switch sw) {
        cboxSwDiv.setSelected(sw.state);
    }

    public void updateBlkInfo(Block blk) {
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

    public void actionPerformed(ActionEvent e) {
		
    TrackElement elem;

        if (e.getSource() == cboxSwDiv) {
            selectedSwitch.state = ((JCheckBox)e.getSource()).isSelected();
        }
                
        if ("comboBoxChanged".equals(e.getActionCommand())) {
            JComboBox cb = (JComboBox)e.getSource();
            if (cb == cbLine) {
                TrackLayout.TrackLine line = (TrackLayout.TrackLine) cb.getSelectedItem();
                cbBlock.setModel(new DefaultComboBoxModel(line.getBlocks().toArray()));
                cbSwitch.setModel(new DefaultComboBoxModel(line.getSwitches().toArray()));
            } else if (cb == cbBlock) {
                Block blk = (Block)cb.getSelectedItem();
                lyt.setSelectedElement(blk);
                updateBlkInfo(blk);
                selectedBlock = blk;

            } else if (cb == cbSwitch) {
                Switch sw = (Switch)cb.getSelectedItem();
                lyt.setSelectedElement(sw);
                updateSwInfo(sw);
                selectedSwitch = sw;
            }
        }

        if (pMap != null) {
            pMap.repaint();
        }
    }

}
