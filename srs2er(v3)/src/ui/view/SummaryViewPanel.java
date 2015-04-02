package ui.view;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ui.data.Data;

public class SummaryViewPanel extends JPanel
{
	JTable table;

	public SummaryViewPanel()
	{
		String[] colNames = {"Sentence", "Min Cost", "Status"};
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
		table.getColumn("Sentence").setPreferredWidth(300);
		this.setVisible(true);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVisible(true);

		table.setFillsViewportHeight(true);

		this.setLayout(new BorderLayout());
		this.add(table.getTableHeader(), BorderLayout.PAGE_START);
		this.add(table, BorderLayout.CENTER);

	}

	private Object[][] readData()
	{
		int sentCount = Data.para.getSentences().size();
		Object[][] data = new Object[sentCount][3];

		for (int i = 0; i < sentCount; i++)
		{
			data[i][0] = Data.para.getSentences().get(i).getValue();
			data[i][1] = Data.para.getSentences().get(i).getMinCost();
			data[i][2] = (int) data[i][1] > 0 ? "Pending" : "Complete";
		}

		return data;
	}
	
	private void setTableMouseAdapter() {
		table.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{

				
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
			
		});
	}

}
