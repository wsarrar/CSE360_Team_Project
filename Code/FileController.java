
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader.*;
import java.io.IOException;
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
	public VaccineRecord loadVaccinationData(File CSVFile) throws IOException {
		Scanner scanInput = new Scanner(System.in);
		System.out.println("Enter the CSV file to read from:");
		String fileName = scanInput.nextLine();	// Assign the file from user input to a String
		
		// Ensure that the file is a valid type
		boolean fileType = fileName.endsWith(".csv");
		if(!CSVFile.exists() && !fileType)
			System.out.println("Invalid file, the file does not exist");
		
		Scanner scanFile = new Scanner(new File(fileName));
		Scanner dataToScan = null;
		List<VaccineEntry> vacDataList = new ArrayList<>();
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
				
				else if(index == 2) {}
				
				else if(index == 3) {}
				
				else if(index == 4) {}
				
				else if(index == 5) {}
				
				else	System.out.println("Invalid Vaccine Data" + vacEntry); 
			}
		}
		VaccineRecord loadVacRecord = new VaccineRecord();	// Initialize a new vaccine record for loading data
		// Return the list of vaccine records
		return loadVacRecord;
	}
	
	// Method for saving vaccine data to a new CSV file
    public void saveVaccinationData(VaccineRecord data) {
        try
        {
            //doesn't matter the csv file so save it to a csvOutput.csv file
            BufferedWriter csvWriter = Files.newBufferedWriter(Paths.get("csvOutput.csv"));

            //add all of the sections to the csv file
            csvWriter.write("ID, Last Name, First Name, Vaccine Type, Date, Location");
            csvWriter.newLine();

            List<String> rowData = data.getRecord().toString();
            for(int i = 0; i < data.size(); i++) {
                List<String> record = Arrays.asList(data.getRow(i)); //might need to manually make a list by getting all objects
                    list.add(data.getRow(i).getFirstName());
		    list.add(data.getRow(i).getLastName());
		    list.add(data.getRow(i).getIdNumber());
		    list.add(data.getRow(i).getType());
		    list.add(data.getRow(i).getDate());
		    list.add(data.getRow(i).getLocation());                 
                csvWriter.write(String.join(",",record));
                csvWriter.newLine();
            }
            csvWriter.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
		
	// Send back to main controller (the interpreter)
	public void sendToMainController() {
		//
	}	
}
