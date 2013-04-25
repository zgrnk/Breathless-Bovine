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
    JLabel lblPrevElem;
    JLabel lblNextElem;

    JCheckBox cboxBlkBidir;
    JCheckBox cboxBlkUground;
    JCheckBox cboxBlkYard;
    JCheckBox cboxBlkCrossing;
    
    JCheckBox cboxSwDiv;
    JLabel lblMainBlk;
    JLabel lblStraightBlk;
    JLabel lblDivBlk;

    JButton btnRemoveTrack;
    JButton btnAddTrack;

    JPanel pAddBlk;
    JDialog dAddBlk;

    public TKMControlPanel(TrackLayout tl) {

        lyt = tl;
        JPanel pSelector = new JPanel();
        JPanel pInfo = new JPanel();
        JPanel pBlkInfo = new JPanel();
        JPanel pSwInfo = new JPanel();


        //pAddBlk = new JPanel();
        //pAddBlk.setLayout(new BoxLayout(this.BoxLayout.Y_AXIS));
        
        //dAddBlk.set


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
        cbLine.setSelectedIndex(0);
        cbBlock.setSelectedIndex(0);
        cbSwitch.setSelectedIndex(0);
        cbLine.addActionListener(this);
        cbBlock.addActionListener(this);
        cbSwitch.addActionListener(this);


        lblElemId = new JLabel();
        lblElemId.setMaximumSize(new Dimension(200,13));

        fieldBlkSection = new JTextField();
        fieldBlkSection.setMaximumSize(new Dimension(200,13));
        fieldBlkSection.addActionListener(this);

        fieldBlkLength = new JTextField();
        fieldBlkLength.setMaximumSize(new Dimension(200,13));
        fieldBlkLength.addActionListener(this);

        fieldBlkGrade = new JTextField();
        fieldBlkGrade.setMaximumSize(new Dimension(200,13));
        fieldBlkGrade.addActionListener(this);

        fieldBlkSpeedLimit = new JTextField();
        fieldBlkSpeedLimit.setMaximumSize(new Dimension(200,13));
        fieldBlkSpeedLimit.addActionListener(this);

        fieldBlkStationName = new JTextField();
        fieldBlkStationName.setMaximumSize(new Dimension(200,13));
        fieldBlkStationName.addActionListener(this);
        
        cboxBlkBidir = new JCheckBox();
        cboxBlkBidir.addActionListener(this);
        cboxBlkUground = new JCheckBox();
        cboxBlkUground.addActionListener(this);
        cboxBlkCrossing = new JCheckBox();
        cboxBlkCrossing.addActionListener(this);

        lblPrevElem = new JLabel();
        lblNextElem = new JLabel();

        btnRemoveTrack = new JButton("Remove this block");
        btnRemoveTrack.addActionListener(this);
        btnAddTrack = new JButton("Add new block");
        btnAddTrack.addActionListener(this);

        cboxSwDiv = new JCheckBox();
        cboxSwDiv.addActionListener(this);
        lblMainBlk = new JLabel();
        lblStraightBlk = new JLabel();
        lblDivBlk = new JLabel();

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
        pBlkInfo.add(new JLabel("Speed Limit (m/s)"));
        pBlkInfo.add(fieldBlkSpeedLimit);
        pBlkInfo.add(new JLabel("Station"));
        pBlkInfo.add(fieldBlkStationName);
        pBlkInfo.add(new JLabel("Bidirectional"));
        pBlkInfo.add(cboxBlkBidir);
        pBlkInfo.add(new JLabel("Underground"));
        pBlkInfo.add(cboxBlkUground);
        pBlkInfo.add(new JLabel("Is Crossing"));
        pBlkInfo.add(cboxBlkCrossing);
        pBlkInfo.add(btnRemoveTrack);
        pBlkInfo.add(btnAddTrack);

        pBlkInfo.add(new JLabel("Previous Element"));
        pBlkInfo.add(lblPrevElem);
        pBlkInfo.add(new JLabel("Next Element"));
        pBlkInfo.add(lblNextElem);
        
        pSwInfo.add(new JLabel("Select Switch"));
        pSwInfo.add(cbSwitch);
        pSwInfo.add(new JLabel("Switched to Divergent"));
        pSwInfo.add(cboxSwDiv);
        pSwInfo.add(new JLabel("Main Block"));
        pSwInfo.add(lblMainBlk);
        pSwInfo.add(new JLabel("Straight Block"));
        pSwInfo.add(lblStraightBlk);
        pSwInfo.add(new JLabel("Divergent Block"));
        pSwInfo.add(lblDivBlk);

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
    
        updateBlkInfo(lyt.redLine.yard, true);
    }

    public void setMapPanel(TrackMapPanel tmp) {
        pMap = tmp;
    }

    public void updateSwInfo(Switch sw, boolean updateCombos) {
        if (updateCombos) {
            cbLine.setSelectedItem(sw.line);
            cbSwitch.setSelectedItem(sw);
            selectedSwitch = sw;
        }

        cboxSwDiv.setSelected(sw.state);
        lblMainBlk.setText(sw.blkMain.toString());
        lblStraightBlk.setText(sw.blkStraight.toString());
        lblDivBlk.setText(sw.blkDiverg.toString());
    }

    private void removeTrackDialog() {
        /* make remove block dialog */
        Object[] options = {"Confirm",
                            "Cancel",
                           };
        int n = JOptionPane.showOptionDialog(this.getTopLevelAncestor(),
            "Are you sure you want to remove " + selectedBlock.toString() + "?",
            "Warning",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            options,
            options[1]);

        if (n == 0) {
            System.out.println("removing blk");
            selectedBlock.line.removeBlock(selectedBlock);
        }
        pMap.repaint();
    }

    public void updateBlkInfo(Block blk, boolean updateCombos) {
        lblElemId.setText("Block");

        if (updateCombos) {
            cbLine.setSelectedItem(blk.line);
            cbBlock.setSelectedItem(blk);
            selectedBlock = blk;
        }

        /* Set block fields */
        if (blk == blk.line.yard) {
            
            fieldBlkSection.setText("Yard");
            fieldBlkSection.setEnabled(false);
            fieldBlkLength.setText("");
            fieldBlkLength.setEnabled(false);
            fieldBlkGrade.setText("");
            fieldBlkGrade.setEnabled(false);
            fieldBlkSpeedLimit.setText("");
            fieldBlkSpeedLimit.setEnabled(false);
            cboxBlkBidir.setSelected(false);
            cboxBlkBidir.setEnabled(false);
            cboxBlkUground.setSelected(false);
            cboxBlkUground.setEnabled(false);
            cboxBlkCrossing.setSelected(false);
            cboxBlkCrossing.setEnabled(false);
            lblPrevElem.setText("");
            lblNextElem.setText("");
        } else {
            fieldBlkSection.setEnabled(true);
            fieldBlkLength.setEnabled(true);
            fieldBlkGrade.setEnabled(true);
            fieldBlkSpeedLimit.setEnabled(true);
            cboxBlkBidir.setEnabled(true);
            cboxBlkUground.setEnabled(true);
            cboxBlkCrossing.setEnabled(true);
            
            fieldBlkSection.setText(blk.sectionId);
            fieldBlkLength.setText(Double.toString(blk.length));
            fieldBlkGrade.setText(Double.toString(blk.grade));
            fieldBlkSpeedLimit.setText(Double.toString(blk.speedLimit));
            if (blk.isStation) {
                fieldBlkStationName.setText(blk.stationName);
                fieldBlkStationName.setEnabled(true);
            } else {
                fieldBlkStationName.setText("No station");
                fieldBlkStationName.setEnabled(false);
            }
            cboxBlkBidir.setSelected(blk.isBidir);
            cboxBlkUground.setSelected(blk.isUground);
            cboxBlkCrossing.setSelected(blk.isCrossing);

            lblPrevElem.setText((blk.prev != null) ? blk.prev.toString() : "dead-end");
            lblNextElem.setText((blk.next != null) ? blk.next.toString() : "dead-end");
            
        }

        //pMap.setMarker(blk.mapX,blk.mapY);
        
    }

    public void actionPerformed(ActionEvent e) {
		
        System.out.println(e.getActionCommand());

        if (e.getSource() == cboxSwDiv) {
            selectedSwitch.state = cboxSwDiv.isSelected();
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
                updateBlkInfo(blk, false);
                selectedBlock = blk;

            } else if (cb == cbSwitch) {
                Switch sw = (Switch)cb.getSelectedItem();
                lyt.setSelectedElement(sw);
                updateSwInfo(sw, false);
                selectedSwitch = sw;
            }
        }

        //System.out.println(e.getSource());

        if(e.getSource() == fieldBlkLength) {
            //System.out.println("updated length");
            selectedBlock.length = Double.parseDouble(fieldBlkLength.getText());
        }
        if(e.getSource() == fieldBlkGrade) {
            selectedBlock.grade = Double.parseDouble(fieldBlkGrade.getText());
        }
        if(e.getSource() == fieldBlkSpeedLimit) {
            selectedBlock.speedLimit = Double.parseDouble(fieldBlkSpeedLimit.getText());
        }
        if(e.getSource() == fieldBlkSection) {
            selectedBlock.sectionId = fieldBlkSection.getText();
        }
        if(e.getSource() == fieldBlkStationName) {
            if (fieldBlkStationName.getText().equals("")) {
                selectedBlock.isStation = false;
            }
            selectedBlock.stationName = fieldBlkStationName.getText();
        }
        
        if(e.getSource() == cboxBlkBidir) {
            selectedBlock.isBidir = cboxBlkBidir.isSelected();
        }
        if(e.getSource() == cboxBlkCrossing) {
            selectedBlock.isCrossing = cboxBlkCrossing.isSelected();
        }
        if(e.getSource() == fieldBlkStationName) {
            selectedBlock.isUground = cboxBlkUground.isSelected();
        }

        if (e.getSource() == btnRemoveTrack) {
            removeTrackDialog();
        }
        if (e.getSource() == btnAddTrack) {
            pMap.setBlockLocation(selectedBlock);
            //System.out.printf("(%d,%d)\n", pt.getX(), pt.getY());
        }

        if (pMap != null) {
            pMap.repaint();
        }
    }

}
