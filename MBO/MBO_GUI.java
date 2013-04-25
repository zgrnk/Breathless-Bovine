package MBO;

import CTCOffice.*;
import TNM.*;

import java.awt.*;
import javax.swing.*;
import java.util.*;


public class MBO_GUI extends JFrame{

	public JComboBox<String> comboBox;
	public JLabel lbl_line_val;
	public JLabel lbl_signal_pickup_val;
	public JLabel lbl_current_block_val;
	public JLabel lbl_current_speed_val;
	public JLabel lbl_train_ahead_dist_val;
	public JLabel lbl_train_behind_dist_val;
	public JLabel lbl_next_station_dist_val;
	public JLabel lbl_calc_authority_val;
	public JLabel lbl_manual_authority;
	public JLabel lbl_toggle_authority;
	public JLabel lbl_calc_speed_val;
	public JLabel lbl_toggle_speed;
	public JLabel lbl_manual_speed;
	public ArrayList<JLabel> gpsLabels;

	public JToggleButton btn_toggle_authority;
	public JToggleButton btn_toggle_speed;
	public JButton btn_manual_authority;
	public JButton btn_manual_speed;
	
	public MBO_Listener mboListener;
	
	
	public MBO_GUI(ArrayList<Train> trainList) {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);

		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setTitle("MBO-MGP14");
		setResizable(false);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel_left = new JPanel();
		panel_left.setIgnoreRepaint(true);
		getContentPane().add(panel_left, BorderLayout.WEST);
		GridBagLayout gbl_panel_left = new GridBagLayout();
		gbl_panel_left.columnWidths = new int[] {0, 0};
		gbl_panel_left.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
		gbl_panel_left.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_left.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel_left.setLayout(gbl_panel_left);

		JLabel lblTrain = new JLabel("Train:");
		lblTrain.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTrain = new GridBagConstraints();
		gbc_lblTrain.fill = GridBagConstraints.BOTH;
		gbc_lblTrain.insets = new Insets(0, 0, 5, 0);
		gbc_lblTrain.gridx = 0;
		gbc_lblTrain.gridy = 0;
		panel_left.add(lblTrain, gbc_lblTrain);

		JLabel lbl_line = new JLabel("Line:");
		lbl_line.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_line = new GridBagConstraints();
		gbc_lbl_line.fill = GridBagConstraints.BOTH;
		gbc_lbl_line.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_line.gridx = 0;
		gbc_lbl_line.gridy = 1;
		panel_left.add(lbl_line, gbc_lbl_line);

		JLabel lbl_signal_pickup = new JLabel("Signal Pickup:");
		lbl_signal_pickup.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_signal_pickup = new GridBagConstraints();
		gbc_lbl_signal_pickup.fill = GridBagConstraints.BOTH;
		gbc_lbl_signal_pickup.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_signal_pickup.gridx = 0;
		gbc_lbl_signal_pickup.gridy = 2;
		panel_left.add(lbl_signal_pickup, gbc_lbl_signal_pickup);

		JLabel lbl_current_block = new JLabel("Block:");
		lbl_current_block.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_current_block = new GridBagConstraints();
		gbc_lbl_current_block.fill = GridBagConstraints.BOTH;
		gbc_lbl_current_block.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_current_block.gridx = 0;
		gbc_lbl_current_block.gridy = 3;
		panel_left.add(lbl_current_block, gbc_lbl_current_block);
		
		JLabel lbl_current_speed = new JLabel("Speed (km/hr):");
		lbl_current_speed.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_current_speed = new GridBagConstraints();
		gbc_lbl_current_speed.fill = GridBagConstraints.BOTH;
		gbc_lbl_current_speed.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_current_speed.gridx = 0;
		gbc_lbl_current_speed.gridy = 4;
		panel_left.add(lbl_current_speed, gbc_lbl_current_speed);

		JLabel lbl_train_ahead_dist = new JLabel("Train Ahead (m):");
		lbl_train_ahead_dist.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_train_ahead_dist = new GridBagConstraints();
		gbc_lbl_train_ahead_dist.fill = GridBagConstraints.BOTH;
		gbc_lbl_train_ahead_dist.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_train_ahead_dist.gridx = 0;
		gbc_lbl_train_ahead_dist.gridy = 5;
		panel_left.add(lbl_train_ahead_dist, gbc_lbl_train_ahead_dist);

		JLabel lbl_train_behind_dist = new JLabel("Train Behind (m):");
		lbl_train_behind_dist.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_train_behind_dist = new GridBagConstraints();
		gbc_lbl_train_behind_dist.fill = GridBagConstraints.BOTH;
		gbc_lbl_train_behind_dist.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_train_behind_dist.gridx = 0;
		gbc_lbl_train_behind_dist.gridy = 6;
		panel_left.add(lbl_train_behind_dist, gbc_lbl_train_behind_dist);

		JLabel lbl_next_station_dist = new JLabel("Next Station (m):");
		lbl_next_station_dist.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_next_station_dist = new GridBagConstraints();
		gbc_lbl_next_station_dist.fill = GridBagConstraints.BOTH;
		gbc_lbl_next_station_dist.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_next_station_dist.gridx = 0;
		gbc_lbl_next_station_dist.gridy = 7;
		panel_left.add(lbl_next_station_dist, gbc_lbl_next_station_dist);

		JLabel lbl_calc_authority = new JLabel("MBO Authority (m):");
		lbl_calc_authority.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_calc_authority = new GridBagConstraints();
		gbc_lbl_calc_authority.fill = GridBagConstraints.BOTH;
		gbc_lbl_calc_authority.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_calc_authority.gridx = 0;
		gbc_lbl_calc_authority.gridy = 8;
		panel_left.add(lbl_calc_authority, gbc_lbl_calc_authority);

		btn_manual_authority = new JButton("Manual Authority (m)");
		GridBagConstraints gbc_btn_manual_authority = new GridBagConstraints();
		gbc_btn_manual_authority.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_manual_authority.insets = new Insets(0, 0, 5, 0);
		gbc_btn_manual_authority.gridx = 0;
		gbc_btn_manual_authority.gridy = 9;
		panel_left.add(btn_manual_authority, gbc_btn_manual_authority);

		btn_toggle_authority = new JToggleButton("Toggle Manual");
		GridBagConstraints gbc_btn_toggle_authority = new GridBagConstraints();
		gbc_btn_toggle_authority.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_toggle_authority.insets = new Insets(0, 0, 5, 0);
		gbc_btn_toggle_authority.gridx = 0;
		gbc_btn_toggle_authority.gridy = 10;
		panel_left.add(btn_toggle_authority, gbc_btn_toggle_authority);
		btn_toggle_authority.setEnabled(false);

		JLabel lbl_calc_speed = new JLabel("MBO Speed (km/hr):");
		lbl_calc_speed.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_calc_speed = new GridBagConstraints();
		gbc_lbl_calc_speed.fill = GridBagConstraints.BOTH;
		gbc_lbl_calc_speed.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_calc_speed.gridx = 0;
		gbc_lbl_calc_speed.gridy = 11;
		panel_left.add(lbl_calc_speed, gbc_lbl_calc_speed);

		btn_manual_speed = new JButton("Manual Speed (km/hr)");
		GridBagConstraints gbc_btn_manual_speed = new GridBagConstraints();
		gbc_btn_manual_speed.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_manual_speed.insets = new Insets(0, 0, 5, 0);
		gbc_btn_manual_speed.gridx = 0;
		gbc_btn_manual_speed.gridy = 12;
		panel_left.add(btn_manual_speed, gbc_btn_manual_speed);

		btn_toggle_speed = new JToggleButton("Toggle Manual");
		GridBagConstraints gbc_btn_toggle_speed = new GridBagConstraints();
		gbc_btn_toggle_speed.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_toggle_speed.gridx = 0;
		gbc_btn_toggle_speed.gridy = 13;
		panel_left.add(btn_toggle_speed, gbc_btn_toggle_speed);
		btn_toggle_speed.setEnabled(false);

		JPanel panel_right = new JPanel();
		getContentPane().add(panel_right, BorderLayout.EAST);
		GridBagLayout gbl_panel_right = new GridBagLayout();
		gbl_panel_right.columnWidths = new int[] {75, 0};
		gbl_panel_right.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
		gbl_panel_right.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_right.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel_right.setLayout(gbl_panel_right);

		comboBox = new JComboBox<String>();
		comboBox.setMinimumSize(new Dimension(25, 20));
		comboBox.setPreferredSize(new Dimension(25, 20));

		for(int i=0; i<trainList.size(); i++){
			comboBox.addItem(trainList.get(i).stringId);
		}

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		panel_right.add(comboBox, gbc_comboBox);

		lbl_line_val = new JLabel("...");
		lbl_line_val.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_line_val = new GridBagConstraints();
		gbc_lbl_line_val.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_line_val.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_line_val.gridx = 0;
		gbc_lbl_line_val.gridy = 1;
		panel_right.add(lbl_line_val, gbc_lbl_line_val);

		lbl_signal_pickup_val = new JLabel("...");
		lbl_signal_pickup_val.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_signal_pickup_val = new GridBagConstraints();
		gbc_lbl_signal_pickup_val.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_signal_pickup_val.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_signal_pickup_val.gridx = 0;
		gbc_lbl_signal_pickup_val.gridy = 2;
		panel_right.add(lbl_signal_pickup_val, gbc_lbl_signal_pickup_val);

		lbl_current_block_val = new JLabel("...");
		lbl_current_block_val.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_current_block_val = new GridBagConstraints();
		gbc_lbl_current_block_val.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_current_block_val.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_current_block_val.gridx = 0;
		gbc_lbl_current_block_val.gridy = 3;
		panel_right.add(lbl_current_block_val, gbc_lbl_current_block_val);
		
		lbl_current_speed_val = new JLabel("...");
		lbl_current_speed_val.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_current_speed_val = new GridBagConstraints();
		gbc_lbl_current_speed_val.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_current_speed_val.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_current_speed_val.gridx = 0;
		gbc_lbl_current_speed_val.gridy = 4;
		panel_right.add(lbl_current_speed_val, gbc_lbl_current_speed_val);

		lbl_train_ahead_dist_val = new JLabel("...");
		lbl_train_ahead_dist_val.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_train_ahead_dist_val = new GridBagConstraints();
		gbc_lbl_train_ahead_dist_val.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_train_ahead_dist_val.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_train_ahead_dist_val.gridx = 0;
		gbc_lbl_train_ahead_dist_val.gridy = 5;
		panel_right.add(lbl_train_ahead_dist_val, gbc_lbl_train_ahead_dist_val);

		lbl_train_behind_dist_val = new JLabel("...");
		lbl_train_behind_dist_val.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_train_behind_dist_val = new GridBagConstraints();
		gbc_lbl_train_behind_dist_val.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_train_behind_dist_val.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_train_behind_dist_val.gridx = 0;
		gbc_lbl_train_behind_dist_val.gridy = 6;
		panel_right.add(lbl_train_behind_dist_val, gbc_lbl_train_behind_dist_val);

		lbl_next_station_dist_val = new JLabel("...");
		lbl_next_station_dist_val.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_next_station_dist_val = new GridBagConstraints();
		gbc_lbl_next_station_dist_val.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_next_station_dist_val.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_next_station_dist_val.gridx = 0;
		gbc_lbl_next_station_dist_val.gridy = 7;
		panel_right.add(lbl_next_station_dist_val, gbc_lbl_next_station_dist_val);

		lbl_calc_authority_val = new JLabel("...");
		lbl_calc_authority_val.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_calc_authority_val = new GridBagConstraints();
		gbc_lbl_calc_authority_val.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_calc_authority_val.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_calc_authority_val.gridx = 0;
		gbc_lbl_calc_authority_val.gridy = 8;
		panel_right.add(lbl_calc_authority_val, gbc_lbl_calc_authority_val);

		lbl_manual_authority = new JLabel("...");
		GridBagConstraints gbc_lbl_manual_authority = new GridBagConstraints();
		gbc_lbl_manual_authority.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_manual_authority.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_manual_authority.gridx = 0;
		gbc_lbl_manual_authority.gridy = 9;
		panel_right.add(lbl_manual_authority, gbc_lbl_manual_authority);

		lbl_toggle_authority = new JLabel();
		lbl_toggle_authority.setText(Boolean.toString(btn_toggle_authority.isSelected()));
		lbl_toggle_authority.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_toggle_authority = new GridBagConstraints();
		gbc_lbl_toggle_authority.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_toggle_authority.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_toggle_authority.gridx = 0;
		gbc_lbl_toggle_authority.gridy = 10;
		panel_right.add(lbl_toggle_authority, gbc_lbl_toggle_authority);

		lbl_calc_speed_val = new JLabel("...");
		lbl_calc_speed_val.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_calc_speed_val = new GridBagConstraints();
		gbc_lbl_calc_speed_val.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_calc_speed_val.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_calc_speed_val.gridx = 0;
		gbc_lbl_calc_speed_val.gridy = 11;
		panel_right.add(lbl_calc_speed_val, gbc_lbl_calc_speed_val);

		lbl_manual_speed = new JLabel("...");
		GridBagConstraints gbc_lbl_manual_speed = new GridBagConstraints();
		gbc_lbl_manual_speed.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_manual_speed.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_manual_speed.gridx = 0;
		gbc_lbl_manual_speed.gridy = 12;
		panel_right.add(lbl_manual_speed, gbc_lbl_manual_speed);

		lbl_toggle_speed = new JLabel();
		lbl_toggle_speed.setText(Boolean.toString(btn_toggle_speed.isSelected()));
		lbl_toggle_speed.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_toggle_speed = new GridBagConstraints();
		gbc_lbl_toggle_speed.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_toggle_speed.gridx = 0;
		gbc_lbl_toggle_speed.gridy = 13;
		panel_right.add(lbl_toggle_speed, gbc_lbl_toggle_speed);

		pack();

		gpsLabels = new ArrayList<JLabel>();
		gpsLabels.add(lbl_current_block_val);
		gpsLabels.add(lbl_current_speed_val);
		gpsLabels.add(lbl_train_ahead_dist_val);
		gpsLabels.add(lbl_train_behind_dist_val);
		gpsLabels.add(lbl_next_station_dist_val);
		gpsLabels.add(lbl_current_block_val);
		gpsLabels.add(lbl_calc_speed_val);
		gpsLabels.add(lbl_calc_authority_val);

		//MBO_Listener mboListener = new MBO_Listener(this, trainList);
		mboListener = new MBO_Listener(this, trainList);
		comboBox.addActionListener(mboListener);
		btn_toggle_authority.addActionListener(mboListener);
		btn_toggle_speed.addActionListener(mboListener);
		btn_manual_authority.addActionListener(mboListener);
		btn_manual_speed.addActionListener(mboListener);
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		//setVisible(true);			////////////////////////////////////////////////////////////////////////////////

	}
}
