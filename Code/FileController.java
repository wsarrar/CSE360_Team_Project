package TP_GUI_APP.FunctionalCode;

import java.io.*;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
import javax.swing.JFileChooser;
import java.util.*;
import java.util.List;

// Sole purpose of the class is to ask users for a file to read, load in files, add files, save files, 
// and send it back to the main controller will also be the interpreter, converting .csv files from csv format 
// to VaccineEntry format in a VaccineRecord object and vice versa.
public class FileController extends GUIController {
	static private final String nextLine = "\n";
	JButton loadFileButton, saveFileButton;
	JTextArea appLog;
	JFileChooser fileToChoose;
	
	public FileController() {
		
		// Create the application log for action listeners to refer to it
		appLog = new JTextArea(5, 40);
		appLog.setMargin(new Insets(5, 5, 5, 5));
		JScrollPane appLogScrollPanel = new JScrollPane(appLog);
		
		// Create a file chooser
		fileToChoose = new JFileChooser();
		// Allows both files and directories to be selected
		fileToChoose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		// Creates an open file button to read a .CSV file
		loadFileButton = new JButton("Open a CSV File..");	// TODO
		loadFileButton.addActionListener((ActionListener) this); // TODO
		
		// Creates a save file button to save a .CSV file
		saveFileButton = new JButton ("Save CSV File..");	// TODO
		saveFileButton.addActionListener((ActionListener) this); // TODO
		
		// For layout purposes, assign the buttons in a separate panel
		JPanel fileButtonPanel = new JPanel();
		fileButtonPanel.add(loadFileButton);
		fileButtonPanel.add(saveFileButton);
		
		// Add the buttons and the 'appLog' to the 'appLogScroolPane' panel.
		//add(fileButtonPanel, BorderLayout.PAGE_START);
		//add(appLogScrollPanel, BorderLayout.CENTER);
	}
	
  	// Working on it
	public void actionToPerfrom(ActionEvent appEvent) {
		// Handles the action when the open button is clicked
		if(appEvent.getSource() == loadFileButton) {
			//int valueToReturn = fileToChoose.showOpenDialog(FileController.this);
		}
	}
	
	/*
        Using List<String> data in the format:
                ID number, Last Name, First Name, Vaccine Type, Date, Location
        Create new vaccine entry and return it
        Returns null entry if could not be created (not in the right format)
	     */
    	public VaccineEntry buildEntry(List<String> data) {
		//check to see if the current data row is valid
		VaccineEntry newEntry = isValidEntry(data);
		//might return null
		if(newEntry == null) {
		    System.out.println("Error: Entry was not able to be created...returning NULL.\n");
		}
		return newEntry;
    	}
	/*
        Takes in a list of strings in the format:
            ID number, Last Name, First Name, Vaccine Type, Date, Location

        @RETURNS VaccineEntry that is NULL if not valid or if valid then it has data from List<String>
	     */
	public VaccineEntry isValidEntry(List<String> newData) {
		int newIDNumber = -1;
		//check if the ID number is an integer
		try {
		    newIDNumber = Integer.parseInt(newData.get(0));
		} catch(Exception e) {
		    System.out.println("Error: Data that you entered has a non-int ID number\n");
		    return null;
		}
		String newDate = newData.get(4);
		//make sure the date is "2 digits/2 digits/4 digits"
		//EX: "00/00/0000"
		if(newDate.length() == 10) {
		    //check to see if / at index 2 and 5
		    if(newDate.charAt(2) == '/' && newDate.charAt(5) == '/') {
			//slashes are at the right spot so see if integers for other places
			String checkInt1 = newDate.substring(0,2);
			String checkInt2 = newDate.substring(3,5);
			String checkInt3 = newDate.substring(6);

			//see if all of these spots are numbers
			try {
			    Integer.parseInt(checkInt1);
			    Integer.parseInt(checkInt2);
			    Integer.parseInt(checkInt3);
			    System.out.println("Month: "+checkInt1+"\n");
			    System.out.println("Day: "+checkInt2+"\n");
			    System.out.println("Year: "+checkInt3+"\n");

			} catch(Exception e) {
			    System.out.println("Error: One of the dates entered has non-number value.\n");
			    return null;
			}
		    } else  {
			System.out.println("Error: No slashes or slashes for date entered are in the wrong spot.\n");
			return null;
		    }
		} else {
		    //cannot possibly be right format since not right amount of characters
		    System.out.println("Error: Date is not the right length to be tested.\n");
		    return null;
		}

		//no other strings really matter so just add all of the strings to a new VaccineEntry and return it
		VaccineEntry newEntry = new VaccineEntry();
		newEntry.setIdNumber(newIDNumber);
		newEntry.setLastName(newData.get(1));
		newEntry.setFirstName(newData.get(2));
		newEntry.setType(newData.get(3));
		newEntry.setDate(newDate);
		newEntry.setLocation(newData.get(5));
		//return newly created entry
		return newEntry;
	 }
}
