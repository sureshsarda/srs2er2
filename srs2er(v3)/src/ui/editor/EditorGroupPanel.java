package ui.editor;

import javax.swing.JTabbedPane;

public class EditorGroupPanel extends JTabbedPane
{
	public EditorGroupPanel()
	{
		this.setTabPlacement(BOTTOM);
		this.addTab("Option One", new EditorPanel());
		this.addTab("Option Two", new EditorPanel());
		this.addTab("Option Three", new EditorPanel());
		this.addTab("Option Four", new EditorPanel());
	}
}
