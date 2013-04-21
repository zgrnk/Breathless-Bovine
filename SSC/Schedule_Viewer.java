import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;


public class Schedule_Viewer extends JPanel {

	public JButton btn_approve_schedule;
	protected JFrame frame;
	public String title;
	public JLabel label;
	public String[][] data;
	public String[] colNames;
	
	
	public Schedule_Viewer(String title, JLabel label, String[] colNames, String[][] data){
		super(new BorderLayout());
		this.title = title;
		this.label = label;
		this.data = data;
		this.colNames = colNames;

		showTable();
	}


	public void showTable() {

		JTable table = new JTable(this.data, this.colNames);
		calcColumnWidths(table);

		if(table.getPreferredSize().getHeight() > 300){
			table.setPreferredScrollableViewportSize(new Dimension((int)table.getPreferredSize().getWidth(), 300));
		}
		else{
			table.setPreferredScrollableViewportSize(table.getPreferredSize());
		}

		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);    

		this.frame = new JFrame(this.title);
		setOpaque(true);
		frame.setContentPane(this);

		JPanel buttonPanel = new JPanel();
		this.btn_approve_schedule = new JButton("Approve Schedule");
		buttonPanel.add(this.btn_approve_schedule);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}



	public void calcColumnWidths(JTable table)
	{
		JTableHeader header = table.getTableHeader();
		TableCellRenderer defaultHeaderRenderer = header.getDefaultRenderer();

		TableColumnModel columns = table.getColumnModel();
		TableModel data = table.getModel();
		int rowCount = data.getRowCount();

		for (int i = columns.getColumnCount() - 1; i >= 0; --i)
		{
			TableColumn column = columns.getColumn(i);
			int columnIndex = column.getModelIndex();
			int width = -1;

			TableCellRenderer h = column.getHeaderRenderer();

			if (h == null) h = defaultHeaderRenderer;

			if (h != null){
				Component c = h.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, -1, i);
				width = c.getPreferredSize().width;
			}

			for (int row = rowCount-1; row >= 0; row--){
				TableCellRenderer r = table.getCellRenderer(row, i);
				Component c = r.getTableCellRendererComponent(table, data.getValueAt(row, columnIndex), false, false, row, i);
				width = Math.max(width, c.getPreferredSize().width);
			}

			column.setPreferredWidth(width + 10);
		}
	}
}

