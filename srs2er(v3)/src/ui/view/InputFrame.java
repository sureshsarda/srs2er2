package ui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import nlp.test.TestParagraph;
import erTagger.ERTagger;

public class InputFrame extends JPanel
{
	JTextArea taInput;
	JButton butBrowseFile;
	JButton butTag;
	JLabel labTitle;
	JLabel labHelp;
	JScrollPane scroll;

	public InputFrame()
	{
		initComponents();

		/*
		 * Upper Panel for Title and text area
		 */
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
		textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		/* Adding components */
		textPanel.add(labTitle);
		textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		textPanel.add(scroll);

		/*
		 * Lower button components
		 */
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		/* Adding components */
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(butBrowseFile);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(butTag);

		/*
		 * Fix everything on the main panel
		 */
		this.setLayout(new BorderLayout());
		this.add(textPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.PAGE_END);
		
		//TODO Remove this default entry
		taInput.setText("The school admits students based on their grades and financial status. "
				+ "Schools classify students according to their grades and financial status. "
				+ "Students are classified by the school according to their grades as well as financial status. "
				+ "Grades are dependent on the academic performance of the student.");
//		taInput.setText("A student takes a course. Student has a name and address. Student can select faculty.");
	}
	private void initComponents()
	{
		taInput = new JTextArea();
		taInput.setPreferredSize(new Dimension(300, 400));
		taInput.setAlignmentX(LEFT_ALIGNMENT);
		taInput.setWrapStyleWord(true);
		taInput.setLineWrap(true);
		scroll = new JScrollPane(taInput, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
	
		/* The Upper Label and TextArea */
		labTitle = new JLabel();
		labTitle.setText("Select file or enter text:");

		/*
		 * Buttons
		 */

		/*
		 * BROWSE BUTTON
		 */
		butBrowseFile = new JButton();
		butBrowseFile.setText("Browse");
		butBrowseFile.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JFileChooser chooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("Text Files", new String[]{"txt"});
				chooser.setFileFilter(filter);

				if (arg0.getSource() == butBrowseFile)
				{
					int retVal = chooser.showOpenDialog(butBrowseFile);
					if (retVal == JFileChooser.APPROVE_OPTION)
					{
						// FIXME Load the content of file in the text area
					}
					else
					{
						taInput.setText("File select cancelled.");
					}
				}

			}

		});

		/*
		 * TAG BUTTON
		 */
		butTag = new JButton();
		butTag.setText("Tag");
		butTag.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				TestParagraph para = ERTagger.getInstance().tagParagraph(taInput.getText());

				RootFrame.rootFrame.setContentPane(new Feedback(para));
				
//				RootFrame.rootFrame.dispose();
//				new Feedback(para);

				// RootFrame.rootFrame.removeAll(); //.setVisible(false);
				// RootFrame.rootFrame.add(new Feedback(Data.para));
				// RootFrame.rootFrame.revalidate();
				// RootFrame.rootFrame.repaint();
				// RootFrame.rootFrame.pack();
			}

		});

	}

}
