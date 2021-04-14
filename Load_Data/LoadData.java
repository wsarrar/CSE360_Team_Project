package TP_GUI_APP;

import java.util.Scanner;

import TP_GUI_APP.FunctionalCode.VaccineEntry;
import TP_GUI_APP.FunctionalCode.VaccineRecord;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

//The purpose for this class is to load information from a .csv file

public class LoadData 
{	
	public static void main(String[] args) throws Exception 
	{
		Scanner scanFile = new Scanner(new File("TeamProjectRandomData_10People.csv"));
		
		// Ask the user for filepath to .csv file
		VaccineEntry vacEntry = new VaccineEntry();
		System.out.println("Enter the filepath for the .csv file: ");
		
		// Call procedure to read the file
		//
		
		// Call procedure to load data into a data structure
		VaccineRecord vacRecord = new VaccineRecord();
		//
		
		// Display data in a table in the application
		//
		
		// Note: .csv file format: ID, Last Name, First Name, Vaccine Type, Vaccination Date, Vaccine Location
	}
}
