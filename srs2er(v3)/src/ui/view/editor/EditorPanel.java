package ui.view.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.view.Feedback;
import nlp.objects.Model;

@SuppressWarnings("serial")
public class EditorPanel extends JPanel
{
	private Model dataModel;
	Editor editor;
	
	public EditorPanel(Model dataModel)
	{
		
		this.dataModel = dataModel;
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		this.setPreferredSize(new Dimension(300, 300));
		this.setMinimumSize(new Dimension(200, 200));
		
		/*
		 * Upper Sentence Text
		 */
		this.add(new JLabel("Sentence text here"), BorderLayout.PAGE_START);
		
		
		/*
		 * Center Editor Panel
		 */
		editor = new Editor(dataModel);
		editor.setSize(300, 300);
		editor.setLocation(10, 10);
		this.add(editor, BorderLayout.CENTER);
		
		
		/*
		 * Lower Button Panel
		 */
		JButton saveButton = new JButton("Save this diagram");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				
				saveThisDiagram_click();
				
			}
			
		});
		JPanel buttons = new JPanel();
		buttons.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		buttons.add(saveButton);
		//buttons.add(new JButton("Reset"));
		buttons.setOpaque(false);
		this.add(buttons, BorderLayout.PAGE_END);
		
		
	}
	
	public void saveThisDiagram_click() {
		Model data = editor.data.getDataModel();
		EditorGroupPanel egp =(EditorGroupPanel)this.getParent(); 
		egp.sentence.setDataModel(data);
		Feedback.instance.dataModels.add(data);
		Feedback.instance.removeSentence(egp.sentence.getValue());
		
	}
}
