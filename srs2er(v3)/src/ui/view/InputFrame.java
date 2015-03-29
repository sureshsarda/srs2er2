package ui.view;

import java.awt.BorderLayout;
import java.awt.Container;
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

import erTagger.ERTagger;

public class InputFrame extends Container
{
	JTextArea taInput;
	JButton butBrowseFile;
	JButton butTag;
	JLabel labTitle;
	JLabel labHelp;

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
		textPanel.add(taInput);

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

	}
	private void initComponents()
	{
		taInput = new JTextArea();
		taInput.setPreferredSize(new Dimension(300, 400));
		taInput.setAlignmentX(LEFT_ALIGNMENT);
		taInput.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(taInput, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); //TODO This code is not working

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
				ERTagger tagger = new ERTagger();
				tagger.tagParagraph(taInput.getText());

			}

		});

	}

}
