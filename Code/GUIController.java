import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.util.*;


//This class updates and displays all screens, as well as takes in the VaccineRecord data read from the FileController
//to display as a table
public class GUIController {
	JFrame frame;
	JTable table;
	JPanel panel;
	JScrollPane sp;
	JPanel lp;
	GUIController()
	{
		//Create Frame
		frame = new JFrame();
		frame.setTitle("CSE 360 Final Group Project");
		
        //Setup columns and create table
		String columnNames[] = {"ID", "Last Name", "First Name", "Vaccine Type", "Vaccine Date", "Vaccine Location" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.setBounds(100,150,700,500);
		
        //Scroll pane!
		sp = new JScrollPane(table);
		
        //Panel for Border Layout
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(sp, BorderLayout.CENTER);
		
        //Panel for Box Layout
		lp = new JPanel();
		lp.setLayout(new BoxLayout(lp,BoxLayout.Y_AXIS));
		lp.setBorder(new EmptyBorder(new Insets(10,10,10,10)));
		
        //Buttons!
		JButton about = new JButton("About");
		lp.add(about);
		lp.add(Box.createRigidArea(new Dimension(0,110)));
		JButton load = new JButton("Load Data");
		lp.add(load);
		lp.add(Box.createRigidArea(new Dimension(0,110)));
		JButton add = new JButton("Add Data");
		lp.add(add);
		lp.add(Box.createRigidArea(new Dimension(0,110)));
		JButton save = new JButton("Save Data");
		lp.add(save);
		lp.add(Box.createRigidArea(new Dimension(0,110)));
		JButton visPie = new JButton("Plot Pie Chart");
		lp.add(visPie);
		lp.add(Box.createRigidArea(new Dimension(0,110)));
		JButton visBar = new JButton("Plot Bar Graph");
		lp.add(visBar);
		lp.add(Box.createRigidArea(new Dimension(0,110)));
		
        //Putting parts together
		panel.add(lp, BorderLayout.WEST);
		frame.add(panel);
		
		BorderLayout layout = (BorderLayout) panel.getLayout();
		
        //Start of tools for the Add Data feature
        //Creating panel for Add Data
		JPanel gl = new JPanel();
		gl.setLayout(new GridLayout(6,2));
		gl.setBorder(new EmptyBorder(new Insets(50,150,50,150)));
	
        //Labels for Infro to be added
		JLabel date = new JLabel("Date: ");
		JLabel id = new JLabel("ID: ");
		JLabel last = new JLabel("Last Name: ");
		JLabel first = new JLabel("First Name");
		JLabel type = new JLabel("Vaccine Type: ");
		JLabel location = new JLabel("Vaccine Location: ");
		
        //Text Fields so Info can be entered
		JTextField dateTF = new JTextField();
		JTextField idTF = new JTextField();
		JTextField lastTF = new JTextField();
		JTextField firstTF = new JTextField();
		JTextField typeTF = new JTextField();
		JTextField locationTF = new JTextField();
		
        //Add things to the panel
		gl.add(date);
		gl.add(id);
		gl.add(last);
		gl.add(first);
		gl.add(type);
		gl.add(location);
		gl.add(dateTF);
		gl.add(idTF);
		gl.add(lastTF);
		gl.add(firstTF);
		gl.add(typeTF);
		gl.add(locationTF);
		
        //An exit button
		JButton addDone = new JButton("Done");
		
        //Making the exit button work
		addDone.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				//Add That data to the table babey!
                tableModel.addRow(new Object[] { idTF.getText(), lastTF.getText(), firstTF.getText(), typeTF.getText(), dateTF.getText(), locationTF.getText() });
				
				//Empty out those text fields
                idTF.setText("");
				dateTF.setText("");
				lastTF.setText("");
				firstTF.setText("");
				typeTF.setText("");
				dateTF.setText("");
				locationTF.setText("");
			
				panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
				panel.add(sp, BorderLayout.CENTER);
				panel.repaint();
				panel.revalidate();
			}
		});
		
        //Make the Add button work
		add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
				
                //Create the addPanel
				JPanel addPanel = new JPanel();
				addPanel.setLayout(new BorderLayout());
				addPanel.setBorder(new EmptyBorder(new Insets(0,40,150,40)));
				addPanel.add(gl, BorderLayout.CENTER);
				addPanel.add(addDone, BorderLayout.SOUTH);
				
                //Put the add panel onn the main panel
				panel.add(addPanel, BorderLayout.CENTER);
				panel.repaint();
				panel.revalidate();
			}
		});
		
	    //Making the about button work
        about.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				//Setting up the text pane
                JTextPane about = new JTextPane();
				
                //Set the text
                about.setText("\n Team 35 \n Ryan Lewicki \n Jack Hardin \n Austin Porter \n Wasfi Sarrar");
		
                //Make sure no one's messing with it
				about.setEditable(false);
				
				panel.add(about, BorderLayout.CENTER);
				panel.repaint();
				panel.revalidate();
			}
		});
		
	save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
				JFileChooser jfc = new JFileChooser();
	            int opened = jfc.showSaveDialog(null);
	            if (opened == JFileChooser.APPROVE_OPTION) {
	            	File file = jfc.getSelectedFile();
	                filepath = file.getPath();
				
	                Object[][] toSave = getData(table);
	                ArrayList<VaccineEntry> veList = new ArrayList<VaccineEntry>();

	                for (int i = 0; i < toSave.length; i++) {
	                	VaccineEntry ve = new VaccineEntry();
	                    ve.setIdNumber((int)toSave[i][0]);
                		ve.setLastName((String)toSave[i][1]);
                		ve.setFirstName((String)toSave[i][2]);
                		ve.setType((String)toSave[i][3]);
                		ve.setDate((String)toSave[i][4]);
                		ve.setLocation((String)toSave[i][5]);
                
                		veList.add(ve);
	                }
	            FileController fc = new FileController();
	            fc.saveVaccinationData(veList);
	            }
            }
		});
	load.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
				JFileChooser jfc = new JFileChooser();
	            int opened = jfc.showSaveDialog(null);
	            if (opened == JFileChooser.APPROVE_OPTION) {
	            	File file = jfc.getSelectedFile();
	                filepath = file.getPath();
	                FileController fc = new FileController();
	                fc.loadVaccinationData(file);
	            }
			}
		});
        //Make the Pie Chart
		visPie.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
				
                //Create Array Lists for the dataset
                ArrayList<String> places = new ArrayList<String>();
				ArrayList<Integer> nums = new ArrayList<Integer>();
				
                //Get the data from the table itself
				Object[][] toSave = getData(table);
				
				for (int i = 0; i < toSave.length; i++)
				{
					if (places.contains(toSave[i][5]))
					{
						nums.set(places.indexOf(toSave[i][5]), nums.get(places.indexOf(toSave[i][5])) + 1);
					}
					else
					{
						places.add((String) toSave[i][5]);
						nums.add(1);
					}
				}
			
            //make that Chart! (see pie class)    
            new Pie("# of doses per location", places, nums);
			
			panel.add(Pie.createDemoPanel(), BorderLayout.CENTER);
			panel.repaint();
			panel.revalidate();
			}
		});
		
		//Make sure you can see the frame
        frame.setSize(1200,900);
		frame.setVisible(true);
	}
	
	visBar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
				ArrayList<String> types = new ArrayList<String>();
				ArrayList<Integer> nums = new ArrayList<Integer>();
				
				Object[][] toSave = getData(table);
				
				for (int i = 0; i < toSave.length; i++)
				{
					if (types.contains(toSave[i][3]))
					{
						nums.set(types.indexOf(toSave[i][3]), nums.get(types.indexOf(toSave[i][3])) + 1);
					}
					else
					{
						types.add((String) toSave[i][3]);
						nums.add(1);
					}
				}
			new Bar("# of doses per vaccine type", types, nums);
			
			panel.add(Bar.createDemoPanel(), BorderLayout.CENTER);
			panel.repaint();
			panel.revalidate();
			}
		});
		
		frame.setSize(1200,900);
		frame.setVisible(true);
	}
    //function for getting data from table
    public Object[][] getData(JTable table) {
	        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
	        int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
	        Object[][] tableData = new Object[nRow][nCol];
	       
	        for (int i = 0; i < nRow; i++)
	            for (int j = 0; j < nCol; j++)
	                tableData[i][j] = dtm.getValueAt(i, j);
	        
	        return tableData;
	    }
}
