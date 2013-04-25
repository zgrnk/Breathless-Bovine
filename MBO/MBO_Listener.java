package MBO;

import TNM.*;
import TKM.*;
import CTCOffice.*;


import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.Timer;


public class MBO_Listener implements ActionListener{

	private MBO_GUI mbo_GUI;
	//public MBO_Test mbo_Test;
	private CTCOffice ctc;	
	private ArrayList<Train> trainList;

	
	public MBO_Listener(MBO_GUI mbo_GUI, ArrayList<Train> trainList) {
		this.mbo_GUI = mbo_GUI;
		this.trainList = trainList;
		//this.mboTimer.start();
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


	public Timer mboTimer = new Timer(100, new ActionListener() {
		public void actionPerformed(ActionEvent evt) { 
			for(Train train : trainList){

				if(!train.issetSignalPickupFailure){
					MBO_Calculator mbo_Calc = new MBO_Calculator(train, trainList);
					
					train.distToNextTrain = mbo_Calc.getDistToNextTrain();
					train.distToPrevTrain = mbo_Calc.getDistToPrevTrain();
					train.distToNextStation = mbo_Calc.getDistToNextStation();
					
					train.mboSuggestedAuthority = mbo_Calc.calculateAuthority();
					train.mboSuggestedSpeed = mbo_Calc.calculateSetspeed();
				}
				else{
					train.mboSuggestedAuthority = 0;
					train.mboSuggestedSpeed = 0;
				}
			}
		}
	});


	public Timer labelTimer = new Timer(200, new ActionListener() {
		public void actionPerformed(ActionEvent evt) { 
			String trainID = mbo_GUI.comboBox.getSelectedItem().toString();
			int index = Integer.valueOf(trainID.substring(1, 2));
			
			Train train = trainList.get(index);	
			mbo_GUI.lbl_line_val.setText(train.line);		
			mbo_GUI.lbl_signal_pickup_val.setText(String.valueOf(!train.issetSignalPickupFailure));

			if(!train.issetSignalPickupFailure){
				mbo_GUI.lbl_current_block_val.setText(Integer.toString(train.gps.block.id));
				mbo_GUI.lbl_current_speed_val.setText(Double.toString((int)(train.gps.speed * 1000 / 360)));

				mbo_GUI.lbl_train_ahead_dist_val.setText(Double.toString((int)train.distToNextTrain));
				mbo_GUI.lbl_train_behind_dist_val.setText(Double.toString((int)train.distToPrevTrain));
				mbo_GUI.lbl_next_station_dist_val.setText(Double.toString((int)train.distToNextStation));

				mbo_GUI.lbl_calc_authority_val.setText(Double.toString((int)train.mboSuggestedAuthority));
				mbo_GUI.lbl_calc_speed_val.setText(Double.toString((int)train.mboSuggestedSpeed));
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