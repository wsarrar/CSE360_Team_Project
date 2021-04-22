package TP_GUI_APP;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Sole purpose of the class is to ask users for a file to read, load in files, add files, save files, 
//and send it back to the main controller will also be the interpreter, converting .csv files from csv format 
//to VaccineEntry format in a VaccineRecord object and vice versa.
public class FileController extends GUIController {
	private static final String newLine = "\n";
	JFileChooser fileToChoose;
	JFrame fileFrame;
	JTextArea textAreaForFile;
	JButton loadFileButton, saveFileButton;
	
	public FileController() 
	{
		textAreaForFile = new JTextArea(5,20);
		textAreaForFile.setMargin(new Insets(5,5,5,5));
        textAreaForFile.setEditable(false);
        JScrollPane fileScrollPane = new JScrollPane(textAreaForFile);
        //For layout purposes, put the buttons in a separate panel to show only file handling
        JPanel filePanel = new JPanel(); //uses FlowLayout
        filePanel.add(loadFileButton);
        filePanel.add(saveFileButton);

        //Add the load file and save file buttons and the textAreaForFile to the file panel 
        // for choosing to open the CSV file or save the CSV file
        fileFrame.add(filePanel, BorderLayout.PAGE_START);
        fileFrame.add(fileScrollPane, BorderLayout.CENTER);
        
        loadFileButton.addActionListener(new ActionListener() 
        {
			public void actionPerformed(ActionEvent loadArg) 
			{
				String newline = "\n";
				//Handle open button action.
		        if (loadArg.getSource() == loadFileButton) 
		        {
		            int returnVal1 = fileToChoose.showOpenDialog(fileToChoose);

		            if (returnVal1 == JFileChooser.APPROVE_OPTION) 
		            {
		            	fileToChoose.getSelectedFile();
		            }
		                //This is where a GUI application would open the CSV file
		            	textAreaForFile.append("Opening: " + fileToChoose.getName() + "." + newLine );
		            } 
		        
		        	else 	textAreaForFile.append("Open command cancelled by user." + newLine);
		            
		        textAreaForFile.setCaretPosition(textAreaForFile.getDocument().getLength());
		        } 
        	
        });
        
        saveFileButton.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent saveArg) 
			{
        		if (saveArg.getSource() == saveFileButton) {
		            int returnVal2 = fileToChoose.showSaveDialog(fileToChoose);
		            if (returnVal2 == JFileChooser.APPROVE_OPTION) 
		            {
		                fileToChoose.getSelectedFile();
		                //This is where a real application would save the CSV file
		                textAreaForFile.append("Saving: " + fileToChoose.getName() + "." + newLine);
		            } 
		            else 	textAreaForFile.append("Save command cancelled by user." + newLine);
		            
		            textAreaForFile.setCaretPosition(textAreaForFile.getDocument().getLength());
		        }
			}
        });
        
		fileToChoose = new JFileChooser();
		fileToChoose.setCurrentDirectory(new File(System.getProperty("C:\\Users")));
		int result = fileToChoose.showOpenDialog(fileFrame);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileToChoose.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
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
	
	// This method is for loading in (reading in) data from the CSV file
	// Need to load all the vaccination data
	public VaccineRecord loadVaccinationData(File CSVFile) {
		Scanner scanInput = new Scanner(System.in);
		System.out.println("Enter the CSV file to read from:");
		String fileName = scanInput.nextLine();	// Assign the file from user input to a String
		
		// Ensure that the file is a valid type
		boolean fileType = fileName.endsWith(".csv");
		if(!CSVFile.exists() && !fileType)
			System.out.println("Invalid file, the file does not exist");
		
		Scanner scanFile = null;
		try {
			scanFile = new Scanner(new File(fileName));
		} 
		catch (FileNotFoundException fileException) {
			fileException.printStackTrace();
		}
		Scanner dataToScan = null;
		
		VaccineRecord loadVacRecord = new VaccineRecord();	// Initialize a new vaccine record for loading data
		int index = 0;
		while(scanFile.hasNextLine()) 
		{
			dataToScan = new Scanner(scanFile.nextLine());
			dataToScan.useDelimiter(",");
			VaccineEntry vacEntry = new VaccineEntry();
			while(dataToScan.hasNext()) 
			{
				String vacData = dataToScan.next();
				if(index == 0) {
					vacEntry.setIdNumber(Integer.parseInt(vacData));
				}
				
				else if(index == 1) {
					vacEntry.setLastName(vacData);
				}
				
				else if(index == 2) {
					vacEntry.setFirstName(vacData);
				}
				
				else if(index == 3) {
					vacEntry.setType(vacData);
				}
				
				else if(index == 4) {
					vacEntry.setDate(vacData);
				}
				
				else if(index == 5) {
					vacEntry.setLocation(vacData);
				}
				
				else	System.out.println("Invalid Vaccine Data" + vacEntry);
				index++;
			}
			loadVacRecord.getRow(index);
			loadVacRecord.addNewRow(vacEntry);	
		}
		scanFile.close();
		loadVacRecord.getRecord();
		// Return the list of vaccine records
		return loadVacRecord;
	}
}
