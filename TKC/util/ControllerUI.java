 package TKC.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.BoxLayout;
import java.awt.Label;
import java.awt.Color;
import javax.swing.JList;
import java.awt.Panel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

public class ControllerUI extends JFrame {

	private JPanel contentPane;
	
	public JButton btnSelTrack;
	public JButton btnSelController;
	public JSpinner spinnerTrack;
	public JSpinner spinnerController;
	public JPanel selectedItemPanel;
	public JPanel trainPanel;
	public JPanel blockPanel;
	public JPanel panel;
	public JPanel trainPanelHeader;
	public JScrollPane scrollPaneTrain;
	public JPanel trainPanelContent;
	public JScrollPane scrollPaneBlock;
	public JPanel blockPanelContent;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControllerUI frame = new ControllerUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*public static void main(String[] args)
	{
	    JFrame frame = new JFrame();
	    JPanel panel = new JPanel()
	    {
	        @Override
	        public Dimension getPreferredSize() {
	            return new Dimension(800, 1000);
	        }
	    };
	    panel.add(new JLabel("Test1"));
	    panel.add(new JLabel("Test2"));
	    frame.getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
	    frame.setSize(600, 800);
	    frame.setVisible(true);
	}*/

	/**
	 * Create the frame.
	 */
	public ControllerUI() {
		setTitle("Track Controller - UI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 778, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnSelTrack = new JButton("Select Track");
		btnSelTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		btnSelController = new JButton("Select Controller");
		
		spinnerTrack = new JSpinner();
		
		spinnerController = new JSpinner();
		
		selectedItemPanel = new JPanel();
		
		trainPanel = new JPanel();
		trainPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		blockPanel = new JPanel();
		blockPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(blockPanel, GroupLayout.PREFERRED_SIZE, 702, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 699, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(spinnerTrack, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSelTrack, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
							.addGap(34)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(spinnerController, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSelController, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addComponent(selectedItemPanel, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(trainPanel, GroupLayout.PREFERRED_SIZE, 702, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(40, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSelTrack)
						.addComponent(btnSelController))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(spinnerTrack, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinnerController, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(selectedItemPanel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(trainPanel, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(blockPanel, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		Label blkIDLabel = new Label("Block ID");
		blkIDLabel.setAlignment(Label.CENTER);
		panel.add(blkIDLabel);
		
		Label blkStateLabel = new Label("State");
		blkStateLabel.setAlignment(Label.CENTER);
		panel.add(blkStateLabel);
		
		Label switchLabel = new Label("Switch Destination");
		switchLabel.setAlignment(Label.CENTER);
		panel.add(switchLabel);
		trainPanel.setLayout(new BoxLayout(trainPanel, BoxLayout.Y_AXIS));
		
		trainPanelHeader = new JPanel();
		trainPanelHeader.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		trainPanelHeader.setBackground(Color.LIGHT_GRAY);
		trainPanel.add(trainPanelHeader);
		trainPanelHeader.setLayout(new BoxLayout(trainPanelHeader, BoxLayout.X_AXIS));
		
		Label trainLabel = new Label("Train ID");
		trainLabel.setAlignment(Label.CENTER);
		trainPanelHeader.add(trainLabel);
		
		Label routeLabel = new Label("Route Blocks");
		routeLabel.setAlignment(Label.CENTER);
		trainPanelHeader.add(routeLabel);
		
		Label authLabel = new Label("Authority");
		authLabel.setAlignment(Label.CENTER);
		trainPanelHeader.add(authLabel);
		
		Label speedLabel = new Label("Speed Limit");
		speedLabel.setAlignment(Label.CENTER);
		trainPanelHeader.add(speedLabel);
		
		scrollPaneTrain = new JScrollPane();
		trainPanel.add(scrollPaneTrain);
		
		trainPanelContent = new JPanel();
		trainPanelContent.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)), new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0))));
		scrollPaneTrain.setViewportView(trainPanelContent);
		trainPanelContent.setLayout(new BoxLayout(trainPanelContent, BoxLayout.Y_AXIS));
		blockPanel.setLayout(new BoxLayout(blockPanel, BoxLayout.Y_AXIS));
		
		scrollPaneBlock = new JScrollPane();
		blockPanel.add(scrollPaneBlock);
		
		blockPanelContent = new JPanel();
		blockPanelContent.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)), new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0))));
		scrollPaneBlock.setViewportView(blockPanelContent);
		blockPanelContent.setLayout(new BoxLayout(blockPanelContent, BoxLayout.X_AXIS));
		
		JLabel lblWayside = new JLabel("Wayside");
		
		JLabel lblTrains = new JLabel("Trains");
		
		JLabel lblBlocks = new JLabel("Blocks");
		
		JLabel waysideLabel = new JLabel("0");
		waysideLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel trainTotalLabel = new JLabel("0");
		trainTotalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel blockTotalLabel = new JLabel("0");
		blockTotalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_selectedItemPanel = new GroupLayout(selectedItemPanel);
		gl_selectedItemPanel.setHorizontalGroup(
			gl_selectedItemPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_selectedItemPanel.createSequentialGroup()
					.addGap(32)
					.addGroup(gl_selectedItemPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(waysideLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblWayside, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(150)
					.addGroup(gl_selectedItemPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTrains, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_selectedItemPanel.createSequentialGroup()
							.addGap(10)
							.addComponent(trainTotalLabel)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_selectedItemPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(blockTotalLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblBlocks, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(55))
		);
		gl_selectedItemPanel.setVerticalGroup(
			gl_selectedItemPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_selectedItemPanel.createSequentialGroup()
					.addGroup(gl_selectedItemPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWayside)
						.addComponent(lblBlocks)
						.addComponent(lblTrains))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_selectedItemPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(waysideLabel)
						.addComponent(blockTotalLabel)
						.addComponent(trainTotalLabel))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		selectedItemPanel.setLayout(gl_selectedItemPanel);
		contentPane.setLayout(gl_contentPane);
	}
}
