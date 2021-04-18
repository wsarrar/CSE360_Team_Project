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
	
	// This method is for loading in files, and saving files
	// TODO
	
	// Send back to main controller (the interpreter)
	// TODO
}
