package ui.view;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class SummaryViewPane extends JPanel
{
	protected JTable table;
	
	public SummaryViewPane()
	{
		String[] colNames = {"Id", "Sentence", "Min Cost", "Status"};
		Object[][] data = readData();
		table = new JTable(data, colNames)
		{
			public boolean isCellEditable(int row, int col)
			{
				return false;
			}

		};
		setTableMouseAdapter();
		table.setVisible(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table.getColumn("Sentence").setPreferredWidth(300);
		table.getColumn("Id").setPreferredWidth(20);
		
		

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVisible(true);

		table.setFillsViewportHeight(true);

		this.setLayout(new BorderLayout());
		this.add(table.getTableHeader(), BorderLayout.PAGE_START);
		this.add(table, BorderLayout.CENTER);
		this.setVisible(true);
	}

	private Object[][] readData()
	{
		int sentCount = Feedback.instance.para.getSentences().size();
		Object[][] data = new Object[sentCount][4];

		for (int i = 0; i < sentCount; i++)
		{
			data[i][0] = Feedback.instance.para.getSentences().get(i).getId();
			data[i][1] = Feedback.instance.para.getSentences().get(i).getValue();
			data[i][2] = Feedback.instance.para.getSentences().get(i).getMinCost();
			data[i][3] = (int) data[i][2] > 0 ? "Pending" : "Complete";
		}

		return data;
	}
	
	private void setTableMouseAdapter() {
		table.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e)
			{
				Feedback.instance.updateEditorGroupPanel(table.getSelectedRow());
			
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{

				
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
		});
	}

}
