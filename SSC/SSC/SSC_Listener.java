package SSC;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;


public class SSC_Listener implements ActionListener{

	private SSC_GUI ssc_GUI;
	public SSC_Test ssc_Test;
	public String startTime;
	public Date startTimeDate;
	public Scheduler greenScheduler = new Scheduler();
	public Scheduler redScheduler = new Scheduler();
	public Schedule_Viewer schedule_viewer;
	

	public SSC_Listener(SSC_GUI mbo_GUI) {
		this.ssc_GUI = mbo_GUI;
		this.greenScheduler.line = "green";
		this.redScheduler.line = "red";
	}

	public void actionPerformed(ActionEvent e) {	
		if(e.getSource().equals(this.ssc_GUI.btn_load_tracks)){
			loadTracks();
		}
		else if(e.getSource().equals(this.ssc_GUI.btn_start_time)){
			startTime();
		}
		else if(e.getSource().equals(this.ssc_GUI.btn_green_throughput)){
			greenThroughput();
		}
		else if(e.getSource().equals(this.ssc_GUI.btn_red_throughput)){
			redThroughput();
		}
		else if(e.getSource().equals(this.ssc_GUI.btn_generate_sch)){
			generateSchedules();
		}
		else if(e.getSource().equals(this.ssc_GUI.btn_view_green_tp)){
			viewGreenThroughputSch();
		}
		else if(e.getSource().equals(this.ssc_GUI.btn_view_red_tp)){
			viewRedThroughputSch();
		}
		else if(e.getSource().equals(this.ssc_GUI.btn_view_routes)){
			viewRoutes();
		}
		else if(e.getSource().equals(this.ssc_GUI.btn_view_green_sch)){
			viewGreenSch();
		}
		else if(e.getSource().equals(this.ssc_GUI.btn_view_red_sch)){
			viewRedSch();
		}
		else if(e.getSource().equals(this.ssc_GUI.btn_view_engineer_sch)){
			viewEngineerSch();
		}
		else if(e.getSource().equals(this.ssc_GUI.btn_export_sch)){
			exportSchedules();
		}
		
		else if(e.getSource().equals(this.schedule_viewer.btn_approve_schedule)){
			this.schedule_viewer.label.setText("approved");
			this.schedule_viewer.frame.dispose();
			checkApproved();
		}
	}


	public void startTime(){	

		JSpinner spinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "HH:mm");
		dateEditor.getTextField().setEditable(false);
		spinner.setEditor(dateEditor);

		JPanel startTimePanel = new JPanel();
		startTimePanel.add(spinner);

		int okCancel = JOptionPane.showOptionDialog(null, startTimePanel, "Schedule Start Time", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

		if(okCancel == 0){
			this.startTime = dateEditor.getTextField().getText();
			this.startTimeDate = (Date) spinner.getValue();
			this.greenScheduler.startTime = this.startTime;
			this.greenScheduler.startTimeDate = this.startTimeDate;
			this.redScheduler.startTime = this.startTime;
			this.redScheduler.startTimeDate = this.startTimeDate;
			this.ssc_GUI.lbl_start_time.setText(this.startTime);

			this.ssc_GUI.btn_start_time.setEnabled(false);
			this.ssc_GUI.btn_load_tracks.setEnabled(true);
		}
	}



	public void loadTracks(){
		ArrayList<String> test = new ArrayList<String>();
		this.greenScheduler.track = test;
		this.redScheduler.track = test;
		this.ssc_GUI.lbl_load_tracks.setText("loaded");

		this.ssc_GUI.btn_load_tracks.setEnabled(false);
		this.ssc_GUI.btn_green_throughput.setEnabled(true);
		this.ssc_GUI.btn_red_throughput.setEnabled(true);
	}



	public void greenThroughput(){
		ButtonGroup traffic = new ButtonGroup();
		JRadioButton none = new JRadioButton("NONE - 0 trains");
		JRadioButton low = new JRadioButton("LOW - 1 train");
		JRadioButton med = new JRadioButton("MEDIUM - 2 trains");
		JRadioButton high = new JRadioButton("HIGH - 3 trains");

		traffic.add(none);
		traffic.add(low);
		traffic.add(med);
		traffic.add(high);

		JLabel prompt = new JLabel("Choose amount of traffic:");

		JPanel throughputPanel = new JPanel();
		throughputPanel.setLayout(new GridLayout(5,1));
		throughputPanel.add(prompt);
		throughputPanel.add(none);
		throughputPanel.add(low);
		throughputPanel.add(med);
		throughputPanel.add(high);

		int okCancel = JOptionPane.showOptionDialog(null, throughputPanel, "Green Throughput", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

		if(okCancel == 0){
			String tpLabel = "------";

			if(none.isSelected()){
				this.greenScheduler.throughput = 0;
				tpLabel = "none";
			}
			else if(low.isSelected()){
				this.greenScheduler.throughput = 1;
				tpLabel = "low";
			}
			else if(med.isSelected()){
				this.greenScheduler.throughput = 2;
				tpLabel = "medium";
			}
			else if(high.isSelected()){
				this.greenScheduler.throughput = 3;
				tpLabel = "high";
			}

			this.ssc_GUI.lbl_green_throughput.setText(tpLabel);

			if(!ssc_GUI.lbl_red_throughput.getText().equals("------")){
				ssc_GUI.btn_generate_sch.setEnabled(true);
			}
		}
	}


	public void redThroughput(){
		ButtonGroup traffic = new ButtonGroup();
		JRadioButton none = new JRadioButton("NONE - 0 trains");
		JRadioButton low = new JRadioButton("LOW - 1 train");
		JRadioButton med = new JRadioButton("MEDIUM - 2 trains");
		JRadioButton high = new JRadioButton("HIGH - 3 trains");

		traffic.add(none);
		traffic.add(low);
		traffic.add(med);
		traffic.add(high);

		JLabel prompt = new JLabel("Choose amount of traffic:");

		JPanel throughputPanel = new JPanel();
		throughputPanel.setLayout(new GridLayout(5,1));
		throughputPanel.add(prompt);
		throughputPanel.add(none);
		throughputPanel.add(low);
		throughputPanel.add(med);
		throughputPanel.add(high);

		int okCancel = JOptionPane.showOptionDialog(null, throughputPanel, "Red Throughput", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

		if(okCancel == 0){
			String tpLabel = "------";

			if(none.isSelected()){
				this.redScheduler.throughput = 0;
				tpLabel = "none";
			}
			else if(low.isSelected()){
				this.redScheduler.throughput = 1;
				tpLabel = "low";
			}
			else if(med.isSelected()){
				this.redScheduler.throughput = 2;
				tpLabel = "medium";
			}
			else if(high.isSelected()){
				this.redScheduler.throughput = 3;
				tpLabel = "high";
			}

			this.ssc_GUI.lbl_red_throughput.setText(tpLabel);

			if(!ssc_GUI.lbl_green_throughput.getText().equals("------")){
				ssc_GUI.btn_generate_sch.setEnabled(true);
			}
		}
	}


	public void generateSchedules(){
		this.greenScheduler.generateSchedules();
		this.redScheduler.generateSchedules();
		this.ssc_GUI.lbl_generate_sch.setText("generated");

		this.ssc_GUI.btn_green_throughput.setEnabled(false);
		this.ssc_GUI.btn_red_throughput.setEnabled(false);
		this.ssc_GUI.btn_generate_sch.setEnabled(false);

		this.ssc_GUI.btn_view_green_tp.setEnabled(true);
		this.ssc_GUI.btn_view_red_tp.setEnabled(true);
		this.ssc_GUI.btn_view_routes.setEnabled(true);
		this.ssc_GUI.btn_view_green_sch.setEnabled(true);
		this.ssc_GUI.btn_view_red_sch.setEnabled(true);
		this.ssc_GUI.btn_view_engineer_sch.setEnabled(true);

	}


	public void viewGreenThroughputSch(){
		viewSchedule("Green Throughput Schedule", this.ssc_GUI.lbl_view_green_tp, this.greenScheduler.throughputSch);
	}


	public void viewRedThroughputSch(){
		viewSchedule("Red Throughput Schedule", this.ssc_GUI.lbl_view_red_tp, this.redScheduler.throughputSch);
	}
	

	public void viewRoutes(){
		ArrayList<String> combinedRoutes = new ArrayList<String>(this.greenScheduler.routesSch);
		for(int i=1; i<this.redScheduler.routesSch.size(); i++){
			combinedRoutes.add(redScheduler.routesSch.get(i));
		}
		
		viewSchedule("Green and Red Routes", ssc_GUI.lbl_view_routes, combinedRoutes);
	}


	public void viewGreenSch(){
		viewSchedule("Green Line Train Schedule", ssc_GUI.lbl_green_sch, this.greenScheduler.trainSch);
	}


	public void viewRedSch(){
		viewSchedule("Red Line Train Schedule", ssc_GUI.lbl_red_sch, this.redScheduler.trainSch);
	}


	public void viewEngineerSch(){
		ArrayList<String> combinedEngSch = new ArrayList<String>(this.greenScheduler.engineerSch);
		for(int i=1; i<this.redScheduler.engineerSch.size(); i++){
			combinedEngSch.add(redScheduler.engineerSch.get(i));
		}
		
		viewSchedule("Engineer Schedule", ssc_GUI.lbl_engineer_sch, combinedEngSch);
	}
	
	
	public void viewSchedule(String title, JLabel label, ArrayList<String> schedule){
		String[] array = null;
		String[][] data = null;
		int i=0;

		for(int k=0; k<schedule.size(); k++){
			StringTokenizer st = new StringTokenizer(schedule.get(k), "\t");

			if(k==0){
				while (st.hasMoreTokens()) {
					i++;
					st.nextToken();
				}

				array = new String[i]; 
				int j=0;
				st = new StringTokenizer(schedule.get(k), "\t");
				while (st.hasMoreTokens()) {
					array[j] = st.nextToken();
					j++;
				}
				data = new String[schedule.size()-1][i];
			}
			else{
				int x=0;
				while (st.hasMoreTokens()) {
					data[k-1][x] = st.nextToken();
					x++;
				}
			}
		}

		this.schedule_viewer = new Schedule_Viewer(title, label, array, data);
		this.schedule_viewer.btn_approve_schedule.addActionListener(this);
	}
	

	public void checkApproved(){
		if(this.ssc_GUI.lbl_view_green_tp.getText().equals("approved")
				&& this.ssc_GUI.lbl_view_red_tp.getText().equals("approved")
				&& this.ssc_GUI.lbl_view_routes.getText().equals("approved")
				&& this.ssc_GUI.lbl_green_sch.getText().equals("approved")
				&& this.ssc_GUI.lbl_red_sch.getText().equals("approved")
				&& this.ssc_GUI.lbl_engineer_sch.getText().equals("approved")){
			this.ssc_GUI.btn_export_sch.setEnabled(true);
		}
	}


	
	public void exportSchedules(){
		File greenThroughputSch = new File("GreenThroughputSchedule.txt");
		try{
			BufferedWriter greenTpOut = new BufferedWriter(new FileWriter(greenThroughputSch));
			for(String str : greenScheduler.throughputSch){
				greenTpOut.write(str + "\n");
			}
			greenTpOut.close();
		}catch (IOException e){}



		File redThroughputSch = new File("RedThroughputSchedule.txt");
		try{
			BufferedWriter redTpOut = new BufferedWriter(new FileWriter(redThroughputSch));
			for(String str : redScheduler.throughputSch){
				redTpOut.write(str + "\n");
			}
			redTpOut.close();
		}catch (IOException e){}



		File routesSch = new File("Routes.txt");
		try{
			BufferedWriter routesOut = new BufferedWriter(new FileWriter(routesSch));
			for(String str : greenScheduler.routesSch){
				routesOut.write(str + "\n");
			}
			routesOut.close();
		}catch (IOException e){}



		File greenSch = new File("GreenSchedule.txt");
		try{
			BufferedWriter greenOut = new BufferedWriter(new FileWriter(greenSch));
			for(String str : greenScheduler.trainSch){
				greenOut.write(str + "\n");
			}
			greenOut.close();
		}catch (IOException e){}



		File redSch = new File("RedSchedule.txt");
		try{
			BufferedWriter redOut = new BufferedWriter(new FileWriter(redSch));
			redOut.write("test");
			redOut.close();
		}catch (IOException e){}



		File engineerSch = new File("EngineerSchedule.txt");
		try{
			BufferedWriter engineerOut = new BufferedWriter(new FileWriter(engineerSch));
			for(String str : greenScheduler.engineerSch){
				engineerOut.write(str + "\n");
			}
			engineerOut.close();
		}catch (IOException e){}


		this.ssc_GUI.lbl_export_sch.setText("exported");
	}


}