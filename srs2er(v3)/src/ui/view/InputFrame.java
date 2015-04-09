package ui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
	JTextArea inputTextArea;
	JButton browseButton;
	JButton tagButton;
	JLabel titleLabel;
	JTextArea helpLabel;
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
		textPanel.add(titleLabel, BorderLayout.PAGE_START);
		 textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		textPanel.add(scroll, BorderLayout.LINE_START);

		/*
		 * Lower button components
		 */
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		/* Adding components */
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(browseButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(tagButton);

		/*
		 * Fix everything on the main panel
		 */
		this.setLayout(new BorderLayout());
		this.add(textPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.PAGE_END);

		// TODO Remove this default entry
		inputTextArea
				.setText("The school admits students based on their grades and financial status. "
						+ "Schools classify students according to their grades and financial status. "
						+ "Students are classified by the school according to their grades as well as financial status. "
						+ "Grades are dependent on the academic performance of the student.");
		//
		inputTextArea
				.setText("A student takes a course. Student has a name and address. Student can select faculty.");
	}
	private void initComponents()
	{
		inputTextArea = new JTextArea();
		inputTextArea.setPreferredSize(new Dimension(300, 400));
		inputTextArea.setAlignmentX(LEFT_ALIGNMENT);
		inputTextArea.setWrapStyleWord(true);
		inputTextArea.setLineWrap(true);
		scroll = new JScrollPane(inputTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		/* The Upper Label and TextArea */
		titleLabel = new JLabel();
		titleLabel.setText("Select file or enter text:");

		/*
		 * Buttons
		 */

		/*
		 * BROWSE BUTTON
		 */
		browseButton = new JButton();
		browseButton.setText("Browse");
		browseButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JFileChooser chooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("Text Files", new String[]{"txt"});
				chooser.setFileFilter(filter);

				if (arg0.getSource() == browseButton)
				{
					int retVal = chooser.showOpenDialog(browseButton);
					if (retVal == JFileChooser.APPROVE_OPTION)
					{
						// FIXME Load the content of file in the text area
					}
					else
					{
						inputTextArea.setText("File select cancelled.");
					}
				}

			}

		});

		/*
		 * TAG BUTTON
		 */
		tagButton = new JButton();
		tagButton.setText("Tag");
		tagButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// RootFrame.rootFrame.setEnabled(false);
				RootFrame.rootFrame.setContentPane(new ProgressBar());
				TestParagraph para = ERTagger.getInstance().tagParagraph(inputTextArea.getText());

				RootFrame.rootFrame.setEnabled(true);
				RootFrame.rootFrame.setExtendedState(RootFrame.rootFrame.getExtendedState()
						| JFrame.MAXIMIZED_BOTH);
				RootFrame.rootFrame.setContentPane(new Feedback(para));
				RootFrame.rootFrame.setTitle("Feedback - Data Model Extractor");

			}

		});

	}

}
