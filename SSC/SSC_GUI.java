package SSC;

import java.awt.*;

import javax.swing.*;
//import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SSC_GUI extends JFrame {

	public JButton btn_load_tracks;
	public JButton btn_start_time;
	public JButton btn_green_throughput;
	public JButton btn_red_throughput;
	public JButton btn_generate_sch;
	public JButton btn_view_green_tp;
	public JButton btn_view_red_tp;
	public JButton btn_view_routes;
	public JButton btn_view_green_sch;
	public JButton btn_view_red_sch;
	public JButton btn_view_engineer_sch;
	public JButton btn_export_sch;
	public JLabel lbl_load_tracks;
	public JLabel lbl_start_time;
	public JLabel lbl_green_throughput;
	public JLabel lbl_red_throughput;
	public JLabel lbl_generate_sch;
	public JLabel lbl_view_routes;
	public JLabel lbl_green_sch;
	public JLabel lbl_red_sch;
	public JLabel lbl_engineer_sch;
	public JLabel lbl_export_sch;
	public JLabel lbl_view_green_tp;
	public JLabel lbl_view_red_tp;


	public SSC_GUI() {

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
		setTitle("SSC-MGP14");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel_left = new JPanel();
		getContentPane().add(panel_left, BorderLayout.WEST);
		GridBagLayout gbl_panel_left = new GridBagLayout();
		gbl_panel_left.columnWidths = new int[] {150, 0};
		gbl_panel_left.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
		gbl_panel_left.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_left.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel_left.setLayout(gbl_panel_left);


		btn_start_time = new JButton("Input Start Time");
		GridBagConstraints gbc_btn_start_time = new GridBagConstraints();
		gbc_btn_start_time.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_start_time.insets = new Insets(0, 0, 5, 0);
		gbc_btn_start_time.gridx = 0;
		gbc_btn_start_time.gridy = 0;
		panel_left.add(btn_start_time, gbc_btn_start_time);


		btn_load_tracks = new JButton("Load Tracks");
		GridBagConstraints gbc_btn_load_tracks = new GridBagConstraints();
		gbc_btn_load_tracks.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_load_tracks.insets = new Insets(0, 0, 5, 0);
		gbc_btn_load_tracks.gridx = 0;
		gbc_btn_load_tracks.gridy = 1;
		panel_left.add(btn_load_tracks, gbc_btn_load_tracks);


		btn_green_throughput = new JButton("Enter Green Throughput");
		GridBagConstraints gbc_btn_green_throughput = new GridBagConstraints();
		gbc_btn_green_throughput.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_green_throughput.insets = new Insets(0, 0, 5, 0);
		gbc_btn_green_throughput.gridx = 0;
		gbc_btn_green_throughput.gridy = 2;
		panel_left.add(btn_green_throughput, gbc_btn_green_throughput);

		btn_red_throughput = new JButton("Enter Red Throughput");
		GridBagConstraints gbc_btn_red_throughput = new GridBagConstraints();
		gbc_btn_red_throughput.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_red_throughput.insets = new Insets(0, 0, 5, 0);
		gbc_btn_red_throughput.gridx = 0;
		gbc_btn_red_throughput.gridy = 3;
		panel_left.add(btn_red_throughput, gbc_btn_red_throughput);

		btn_generate_sch = new JButton("Generate Schedules");
		btn_generate_sch.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_btn_generate_sch = new GridBagConstraints();
		gbc_btn_generate_sch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_generate_sch.insets = new Insets(0, 0, 5, 0);
		gbc_btn_generate_sch.gridx = 0;
		gbc_btn_generate_sch.gridy = 4;
		panel_left.add(btn_generate_sch, gbc_btn_generate_sch);

		btn_view_routes = new JButton("View Routes");
		GridBagConstraints gbc_btn_view_routes = new GridBagConstraints();
		gbc_btn_view_routes.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_view_routes.insets = new Insets(0, 0, 5, 0);
		gbc_btn_view_routes.gridx = 0;
		gbc_btn_view_routes.gridy = 5;
		panel_left.add(btn_view_routes, gbc_btn_view_routes);

		btn_view_green_tp = new JButton("View Green TP Schedule");
		GridBagConstraints gbc_btn_view_green_tp = new GridBagConstraints();
		gbc_btn_view_green_tp.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_view_green_tp.insets = new Insets(0, 0, 5, 0);
		gbc_btn_view_green_tp.gridx = 0;
		gbc_btn_view_green_tp.gridy = 6;
		panel_left.add(btn_view_green_tp, gbc_btn_view_green_tp);

		btn_view_red_tp = new JButton("View Red TP Schedule");
		GridBagConstraints gbc_btn_view_red_tp = new GridBagConstraints();
		gbc_btn_view_red_tp.insets = new Insets(0, 0, 5, 0);
		gbc_btn_view_red_tp.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_view_red_tp.gridx = 0;
		gbc_btn_view_red_tp.gridy = 7;
		panel_left.add(btn_view_red_tp, gbc_btn_view_red_tp);

		btn_view_green_sch = new JButton("View Green Schedule");
		GridBagConstraints gbc_btn_view_green_sch = new GridBagConstraints();
		gbc_btn_view_green_sch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_view_green_sch.insets = new Insets(0, 0, 5, 0);
		gbc_btn_view_green_sch.gridx = 0;
		gbc_btn_view_green_sch.gridy = 8;
		panel_left.add(btn_view_green_sch, gbc_btn_view_green_sch);

		btn_view_red_sch = new JButton("View Red Schedule");
		GridBagConstraints gbc_btn_view_red_sch = new GridBagConstraints();
		gbc_btn_view_red_sch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_view_red_sch.insets = new Insets(0, 0, 5, 0);
		gbc_btn_view_red_sch.gridx = 0;
		gbc_btn_view_red_sch.gridy = 9;
		panel_left.add(btn_view_red_sch, gbc_btn_view_red_sch);

		btn_view_engineer_sch = new JButton("View Engineer Schedule");
		GridBagConstraints gbc_btn_view_engineer_sch = new GridBagConstraints();
		gbc_btn_view_engineer_sch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_view_engineer_sch.insets = new Insets(0, 0, 5, 0);
		gbc_btn_view_engineer_sch.gridx = 0;
		gbc_btn_view_engineer_sch.gridy = 10;
		panel_left.add(btn_view_engineer_sch, gbc_btn_view_engineer_sch);

		btn_export_sch = new JButton("Export Schedules");
		btn_export_sch.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_btn_export_sch = new GridBagConstraints();
		gbc_btn_export_sch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_export_sch.gridx = 0;
		gbc_btn_export_sch.gridy = 11;
		panel_left.add(btn_export_sch, gbc_btn_export_sch);


		JPanel panel_right = new JPanel();
		getContentPane().add(panel_right, BorderLayout.EAST);
		GridBagLayout gbl_panel_right = new GridBagLayout();
		gbl_panel_right.columnWidths = new int[] {100, 0};
		gbl_panel_right.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
		gbl_panel_right.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_right.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel_right.setLayout(gbl_panel_right);

		lbl_start_time = new JLabel("------");
		lbl_start_time.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_start_time = new GridBagConstraints();
		gbc_lbl_start_time.fill = GridBagConstraints.BOTH;
		gbc_lbl_start_time.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_start_time.gridx = 0;
		gbc_lbl_start_time.gridy = 0;
		panel_right.add(lbl_start_time, gbc_lbl_start_time);

		lbl_load_tracks = new JLabel("------");
		lbl_load_tracks.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_load_tracks = new GridBagConstraints();
		gbc_lbl_load_tracks.fill = GridBagConstraints.BOTH;
		gbc_lbl_load_tracks.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_load_tracks.gridx = 0;
		gbc_lbl_load_tracks.gridy = 1;
		panel_right.add(lbl_load_tracks, gbc_lbl_load_tracks);

		lbl_green_throughput = new JLabel("------");
		lbl_green_throughput.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_green_throughput = new GridBagConstraints();
		gbc_lbl_green_throughput.fill = GridBagConstraints.BOTH;
		gbc_lbl_green_throughput.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_green_throughput.gridx = 0;
		gbc_lbl_green_throughput.gridy = 2;
		panel_right.add(lbl_green_throughput, gbc_lbl_green_throughput);

		lbl_red_throughput = new JLabel("------");
		lbl_red_throughput.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_red_throughput = new GridBagConstraints();
		gbc_lbl_red_throughput.fill = GridBagConstraints.BOTH;
		gbc_lbl_red_throughput.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_red_throughput.gridx = 0;
		gbc_lbl_red_throughput.gridy = 3;
		panel_right.add(lbl_red_throughput, gbc_lbl_red_throughput);

		lbl_generate_sch = new JLabel("------");
		lbl_generate_sch.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_generate_sch = new GridBagConstraints();
		gbc_lbl_generate_sch.fill = GridBagConstraints.BOTH;
		gbc_lbl_generate_sch.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_generate_sch.gridx = 0;
		gbc_lbl_generate_sch.gridy = 4;
		panel_right.add(lbl_generate_sch, gbc_lbl_generate_sch);

		lbl_view_routes = new JLabel("------");
		lbl_view_routes.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_view_routes = new GridBagConstraints();
		gbc_lbl_view_routes.fill = GridBagConstraints.BOTH;
		gbc_lbl_view_routes.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_view_routes.gridx = 0;
		gbc_lbl_view_routes.gridy = 5;
		panel_right.add(lbl_view_routes, gbc_lbl_view_routes);

		lbl_view_green_tp = new JLabel("------");
		lbl_view_green_tp.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_view_green_tp = new GridBagConstraints();
		gbc_lbl_view_green_tp.fill = GridBagConstraints.BOTH;
		gbc_lbl_view_green_tp.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_view_green_tp.gridx = 0;
		gbc_lbl_view_green_tp.gridy = 6;
		panel_right.add(lbl_view_green_tp, gbc_lbl_view_green_tp);

		lbl_view_red_tp = new JLabel("------");
		lbl_view_red_tp.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_view_red_tp = new GridBagConstraints();
		gbc_lbl_view_red_tp.fill = GridBagConstraints.BOTH;
		gbc_lbl_view_red_tp.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_view_red_tp.gridx = 0;
		gbc_lbl_view_red_tp.gridy = 7;
		panel_right.add(lbl_view_red_tp, gbc_lbl_view_red_tp);

		lbl_green_sch = new JLabel("------");
		lbl_green_sch.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_green_sch = new GridBagConstraints();
		gbc_lbl_green_sch.fill = GridBagConstraints.BOTH;
		gbc_lbl_green_sch.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_green_sch.gridx = 0;
		gbc_lbl_green_sch.gridy = 8;
		panel_right.add(lbl_green_sch, gbc_lbl_green_sch);

		lbl_red_sch = new JLabel("------");
		lbl_red_sch.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_red_sch = new GridBagConstraints();
		gbc_lbl_red_sch.fill = GridBagConstraints.BOTH;
		gbc_lbl_red_sch.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_red_sch.gridx = 0;
		gbc_lbl_red_sch.gridy = 9;
		panel_right.add(lbl_red_sch, gbc_lbl_red_sch);

		lbl_engineer_sch = new JLabel("------");
		lbl_engineer_sch.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_engineer_sch = new GridBagConstraints();
		gbc_lbl_engineer_sch.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_engineer_sch.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_engineer_sch.gridx = 0;
		gbc_lbl_engineer_sch.gridy = 10;
		panel_right.add(lbl_engineer_sch, gbc_lbl_engineer_sch);

		lbl_export_sch = new JLabel("------");
		lbl_export_sch.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_export_sch = new GridBagConstraints();
		gbc_lbl_export_sch.fill = GridBagConstraints.BOTH;
		gbc_lbl_export_sch.gridx = 0;
		gbc_lbl_export_sch.gridy = 11;
		panel_right.add(lbl_export_sch, gbc_lbl_export_sch);

		pack();

		SSC_Listener sscListener = new SSC_Listener(this);
		btn_start_time.addActionListener(sscListener);
		btn_load_tracks.addActionListener(sscListener);
		btn_green_throughput.addActionListener(sscListener);
		btn_red_throughput.addActionListener(sscListener);
		btn_generate_sch.addActionListener(sscListener);
		btn_view_green_tp.addActionListener(sscListener);
		btn_view_red_tp.addActionListener(sscListener);
		btn_view_routes.addActionListener(sscListener);
		btn_view_green_sch.addActionListener(sscListener);
		btn_view_red_sch.addActionListener(sscListener);
		btn_view_engineer_sch.addActionListener(sscListener);
		btn_export_sch.addActionListener(sscListener);
		
		btn_load_tracks.setEnabled(false);
		btn_green_throughput.setEnabled(false);
		btn_red_throughput.setEnabled(false);
		btn_generate_sch.setEnabled(false);
		btn_view_green_tp.setEnabled(false);
		btn_view_red_tp.setEnabled(false);
		btn_view_routes.setEnabled(false);
		btn_view_green_sch.setEnabled(false);
		btn_view_red_sch.setEnabled(false);
		btn_view_engineer_sch.setEnabled(false);
		btn_export_sch.setEnabled(false);
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		setVisible(true);
	}
}
