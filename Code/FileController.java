package TP_GUI_APP;

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
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("C:\\Users")));
		int result = fileChooser.showOpenDialog(fileFrame);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
	}
	
	public void createScrollPaneWindow() {
		// Display the window.
		// The following creates the text area, generates the scroll pane's client, 
		// and adds the scroll pane to a container 
		  
		//In a container that uses a BorderLayout:
		textAreaForFile = new JTextArea(5, 30);

		fileScrollPane = new JScrollPane(textAreaForFile);
		//setPreferredSize(new Dimension(450, 110));
		//add(scrollPane, BorderLayout.CENTER);
		
		// Create and set up a window to allow the user and admin to scroll up and down and side to side
		// to view the vaccination data from the CSV file
        fileFrame.setSize(200, 200);
        fileFrame.setVisible(true);
        fileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        // set flow layout for the frame
        fileFrame.getContentPane().setLayout(new FlowLayout());
 
        //textArea = new JTextArea(50, 50);
        //scrollableTextArea = new JScrollPane(textArea);
 
        //scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
 
        //frame.getContentPane().add(scrollableTextArea);
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
	
	// This method is for loading in (reading in) data from the CSV file
	// Need to load all the vaccination data
	public VaccineRecord loadVaccinationData(String csvFileName) {
		// Open the CSV file using FileReader object
		VaccineRecord loadVacRecord = new VaccineRecord();
		Path filePath = Paths.get(csvFileName);
		
		// Create BufferedReader from FileReader using try and importing US_ACII as the standard character set
		try(BufferedReader bReader = Files.newBufferedReader(filePath, StandardCharsets.US_ASCII)) {
			// Reads the first line from the CSV file line by line
			String csvLine = bReader.readLine();
			
			// This while loop allows reading through the entire CSV file until every line is read
			while(csvLine != null) {
				// Using the .split to load a 'String' array with attributes for vaccine data from 
				// each line of the CSV file using a ',' as the delimiter
				String[] vacAttributes = csvLine.split(",");
				// Create an object for vaccine entry
				VaccineEntry vacEntry = new VaccineEntry();
				// Retrieve the vaccine ID number for a VaccineEntry and convert it to a String
				int vacID = vacEntry.getIdNumber();
				String vacIDStringForm = String.valueOf(vacID);
				
				vacAttributes[0] = vacIDStringForm;
				vacAttributes[1] = vacEntry.getLastName();
				vacAttributes[2] = vacEntry.getFirstName();
				vacAttributes[3] = vacEntry.getType();
				vacAttributes[4] = vacEntry.getDate();
				vacAttributes[5] = vacEntry.getLocation();
				
				// Add those objects into the VaccineRecord data structure using add() method
				loadVacRecord.addNewRow(vacEntry);
				
				// Now read the next line before next iteration, however, if the end of the file is
				// reached, then the line would be null
				csvLine = bReader.readLine();
			}
		}
		catch(IOException exceptionToCheck) {
			exceptionToCheck.printStackTrace();
		}
		// Return the list of vaccine records
		return loadVacRecord;
	}
	
	// Method for saving vaccine data to a new CSV file
	public VaccineRecord saveVaccinationData() {
		//
		return null;
	}
		
	// Send back to main controller (the interpreter)
	public void sendToMainController() {
		//
	}	
}
