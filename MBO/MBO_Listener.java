package MBO;

import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.Timer;


public class MBO_Listener implements ActionListener{

	private MBO_GUI mbo_GUI;
	public MBO_Test mbo_Test;
	

	public MBO_Listener(MBO_GUI mbo_GUI) {
		this.mbo_GUI = mbo_GUI;
		this.mboTimer.start();
		this.labelTimer.start();
	}

	public void actionPerformed(ActionEvent e) {	
		if(e.getSource().equals(mbo_GUI.btn_toggle_authority)){
			toggleManualAuthority();
		}
		else if(e.getSource().equals(mbo_GUI.btn_toggle_speed)){
			toggleManualSpeed();
		}
		else if(e.getSource().equals(mbo_GUI.btn_manual_authority)){
			getManualAuthority();
		}
		else if(e.getSource().equals(mbo_GUI.btn_manual_speed)){
			getManualSpeed();
		}
	}


	public void toggleManualAuthority(){
		mbo_GUI.lbl_toggle_authority.setText(Boolean.toString(mbo_GUI.btn_toggle_authority.isSelected()));
		if(mbo_GUI.btn_toggle_authority.isSelected()){
			//use manual authority
		}
	}

	public void toggleManualSpeed(){
		mbo_GUI.lbl_toggle_speed.setText(Boolean.toString(mbo_GUI.btn_toggle_speed.isSelected()));
		if(mbo_GUI.btn_toggle_speed.isSelected()){
			//use manual speed
		}
	}


	public void getManualAuthority(){
		String manualAuthorityStr = JOptionPane.showInputDialog("Enter authority in meters");
		if(manualAuthorityStr != null){
			try{
				int manualAuthority = Integer.parseInt(manualAuthorityStr);
				if(manualAuthority<0 || manualAuthority>9999){
					JOptionPane.showConfirmDialog(null, "Input must be number between 0 and 9999", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				else{
					mbo_GUI.lbl_manual_authority.setText(manualAuthorityStr);
					mbo_GUI.btn_toggle_authority.setEnabled(true);
				}
			}
			catch(NumberFormatException e){
				JOptionPane.showConfirmDialog(null, "Input must be number between 0 and 9999", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	public void getManualSpeed(){
		String manualSpeedStr = JOptionPane.showInputDialog("Enter speed in km/hr");
		if(manualSpeedStr != null){
			try{
				int manualSpeed = Integer.parseInt(manualSpeedStr);
				if(manualSpeed<0 || manualSpeed > 70){
					JOptionPane.showConfirmDialog(null, "Input must be number between 0 and 70", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				else{
					mbo_GUI.lbl_manual_speed.setText(manualSpeedStr);
					mbo_GUI.btn_toggle_speed.setEnabled(true);
				}
			}
			catch(NumberFormatException e){
				JOptionPane.showConfirmDialog(null, "Input must be number between 0 and 70", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	public Timer mboTimer = new Timer(50, new ActionListener() {
		public void actionPerformed(ActionEvent evt) { 
			for(Train train : mbo_Test.trainList){

				if(train.signalPickup){
					MBO_Calculator mbo_Calc = new MBO_Calculator(train);
					
					train.trainAheadDist = mbo_Calc.getNextTrainFrontDist();
					train.trainBehindDist = mbo_Calc.getNextTrainBehindDist();
					train.nextStationDist = mbo_Calc.getNextStationDist();
					
					train.MBOsuggestedAuthority = mbo_Calc.calculateAuthority();
					train.MBOsuggestedSetspeed = mbo_Calc.calculateSetspeed();
				}
				else{
					train.MBOsuggestedAuthority = 0;
					train.MBOsuggestedSetspeed = 0;
				}
			}
		}
	});


	public Timer labelTimer = new Timer(200, new ActionListener() {
		public void actionPerformed(ActionEvent evt) { 
			String trainID = mbo_GUI.comboBox.getSelectedItem().toString();

			Train train = mbo_Test.trainTable.get(trainID);	
			mbo_GUI.lbl_line_val.setText(train.line);		
			mbo_GUI.lbl_signal_pickup_val.setText(String.valueOf(train.signalPickup));

			if(train.signalPickup){
				mbo_GUI.lbl_current_block_val.setText(train.gps.block.id);
				mbo_GUI.lbl_current_speed_val.setText(Double.toString((int)train.gps.speed));

				mbo_GUI.lbl_train_ahead_dist_val.setText(Double.toString((int)train.trainAheadDist));
				mbo_GUI.lbl_train_behind_dist_val.setText(Double.toString((int)train.trainBehindDist));
				mbo_GUI.lbl_next_station_dist_val.setText(Double.toString((int)train.nextStationDist));

				mbo_GUI.lbl_calc_authority_val.setText(Double.toString((int)train.MBOsuggestedAuthority));
				mbo_GUI.lbl_calc_speed_val.setText(Double.toString((int)train.MBOsuggestedSetspeed));
			}
			else{
				for(int i=0; i<mbo_GUI.gpsLabels.size(); i++){
					mbo_GUI.gpsLabels.get(i).setText("?");
				}
			}
			mbo_GUI.repaint();
		}
	});

}