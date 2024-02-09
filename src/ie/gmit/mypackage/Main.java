package ie.gmit.mypackage;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.File;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * The Main class holds the main method.
 */


public class Main extends Application {
	
	PhoneManage pm = new PhoneManage(); // Used for managing Phone
	
	@Override
	public void start(Stage primaryStage) {
		
		int noOfCmdLineArgs = 0;	// Used to set stage title
		String cmdLineArgs = null;	// Used to set stage title
		
		/* Preparing the Scenes */
		// Create GridPane node to use as root node of scene and to arrange child nodes logically
		GridPane gridPane1 = new GridPane();
		// Create child nodes
		// Create Text node for top of scene 1
		Text txtHeader = new Text("Please select an option below:");
		// Create button and TextField for Loading DB
		Button btnLoadPhoneList = new Button("Load Phone from File");
		TextField tfLoadPhoneFilePath = new TextField();
		tfLoadPhoneFilePath.setPromptText("Path to Phone File");
		// Add Phone Button and text fields
		Button btnAddPhone = new Button("Add Phone");
		TextField tfPhoneID = new TextField();
		tfPhoneID.setPromptText("ID Number");
		TextField tfPhoneTitle = new TextField();
		tfPhoneTitle.setPromptText("Phone Title");
		TextField tfPhonePrice = new TextField();
		tfPhonePrice.setPromptText("Price");
		TextField tfPhoneReleaseYear = new TextField();
		tfPhoneReleaseYear.setPromptText("Release Year");
		// Delete Phone
		Button btnDelPhone = new Button("Delete Phone");
		TextField tfPhoneDel = new TextField();
		tfPhoneDel.setPromptText("Phone ID.");
		// Show total number of Phones
		Button btnShowTotal = new Button("Show Total Phone");
		// Show total number of Phones
		Button btnShowPhoneList = new Button("Show Phone List");
		// Save Phones to file
		Button btnSavePhoneList = new Button("Save Phone List");
		TextField tfSavePhoneFilePath = new TextField();
		tfSavePhoneFilePath.setPromptText("Path to Phone File");
		// Add Quit button
		Button btnQuit = new Button("Quit");	
		// Create TextArea node for bottom of scene 1 to display output
		TextArea taMyOutput = new TextArea();
		
		//Setting the padding  
	      gridPane1.setPadding(new Insets(10, 20, 20, 20)); 
	      
	      //Setting the vertical and horizontal gaps between the columns 
	      gridPane1.setVgap(10); 
	      gridPane1.setHgap(10);       
		
		// Adding and arranging all the nodes in the grid - add(node, column, row)
		gridPane1.add(txtHeader, 0, 0);
		gridPane1.add(btnLoadPhoneList, 0, 1);
		gridPane1.add(tfLoadPhoneFilePath, 1, 1, 4, 1);
		gridPane1.add(btnAddPhone, 0, 2);
		gridPane1.add(tfPhoneID, 1, 2);
		gridPane1.add(tfPhoneTitle, 2, 2);
		gridPane1.add(tfPhonePrice, 3, 2);
		gridPane1.add(tfPhoneReleaseYear, 4, 2);
		gridPane1.add(btnDelPhone, 0, 3);
		gridPane1.add(tfPhoneDel, 1, 3);
		gridPane1.add(btnShowTotal, 0, 4);
		gridPane1.add(btnShowPhoneList, 0, 5);
		gridPane1.add(btnSavePhoneList, 0, 6);
		gridPane1.add(tfSavePhoneFilePath, 1, 6, 4, 1);
		gridPane1.add(btnQuit, 0, 7);
		gridPane1.add(taMyOutput, 0, 8, 5, 2);
		
		// make buttons same width 
		btnLoadPhoneList.setMaxWidth(Double.MAX_VALUE);
		btnAddPhone.setMaxWidth(Double.MAX_VALUE);
		btnDelPhone.setMaxWidth(Double.MAX_VALUE);
		btnShowTotal.setMaxWidth(Double.MAX_VALUE);
		btnShowPhoneList.setMaxWidth(Double.MAX_VALUE);
		btnSavePhoneList.setMaxWidth(Double.MAX_VALUE);
		btnQuit.setMaxWidth(Double.MAX_VALUE);
		
		// Adding events to buttons
		// Load Phone DB button
		btnLoadPhoneList.setOnAction(e -> {
			if (tfLoadPhoneFilePath.getText().trim().equals("")) { // If text field is empty
				taMyOutput.setText("Please enter path to Phone file.\n");
			} else {
				File phoneCSVFile = new File(".\\resources\\phone.csv");
				File phoneObjectsFile = new File(tfLoadPhoneFilePath.getText());
				pm.loadPhoneFromCSVFile(phoneCSVFile);
				pm.savePhoneManageObjectToFile(phoneObjectsFile);
				pm = pm.loadPhoneManageObjectFromFile(phoneObjectsFile);
				if (pm == null) {
					pm = new PhoneManage();
					taMyOutput.setText("ERROR: DB path " + tfLoadPhoneFilePath.getText() + " does not exist\n");
					taMyOutput.appendText("Please check DB path and try again");
					tfLoadPhoneFilePath.clear();
				} else {
					taMyOutput.setText("DB loaded successfully from " + tfLoadPhoneFilePath.getText());
					tfLoadPhoneFilePath.clear();
				}
			}
		});
		
		// Add Phone button action
		btnAddPhone.setOnAction(e -> {
			// If any of the Phone fields are empty print prompt message
			if (tfPhoneID.getText().trim().equals("") || 
					tfPhoneTitle.getText().trim().equals("") ||
					tfPhonePrice.getText().trim().equals("") ||
					tfPhoneReleaseYear.getText().trim().equals("")) { 
				taMyOutput.setText("Please enter ALL Phone details\n");
			} else {
				// Create new Phone with information in text fields
				try {
					Phone newPhone= new Phone(tfPhoneID.getText(), tfPhoneTitle.getText(), Float.parseFloat(tfPhonePrice.getText()), Integer.parseInt(tfPhoneReleaseYear.getText()));
					this.pm.addPhone(newPhone); // Add Phone to phone list
					// Print success message
					
					taMyOutput.setText(newPhone.getTitle()+ " has been Added phone list");
					
				
					// Clear input fields
					tfPhoneID.clear();
					tfPhoneTitle.clear();
					tfPhonePrice.clear();
					tfPhoneReleaseYear.clear();
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
					taMyOutput.setText("Please enter a number for Phone release Year");
					taMyOutput.setText("Please enter a decimal numbers for Phone price");
				}
			}
		});
		
		// Delete Phone button action
		btnDelPhone.setOnAction(e -> {		
			if (tfPhoneDel.getText().trim().equals("")) { // If text field is empty
				taMyOutput.setText("Please enter the Phone ID you want to delete");
			} else {
				Phone removedPhone;
				removedPhone = pm.deletePhoneByNumber(Integer.parseInt(tfPhoneDel.getText()));
				if (removedPhone != null) {
					taMyOutput.setText(removedPhone.getTitle() + " " + removedPhone.getPrice() + " has been removed from the phone list!");
					tfPhoneDel.clear();
				} else {
					taMyOutput.setText("Phone " + tfPhoneDel.getText() + " not found\n");
					taMyOutput.appendText("No phone deleted!");
					tfPhoneDel.clear();
				}
			}
		});

		// Show total number of phones
		btnShowTotal.setOnAction(e -> {
			int totalPhone = 0;
			// Find total Phone
			totalPhone = pm.findTotalPhone();
			taMyOutput.setText("Current Total Phone: " + Integer.toString(totalPhone));		
		});
		
		// Show all Phones list
		btnShowPhoneList.setOnAction(e -> {
			taMyOutput.setText(pm.listAllPhone());
		});	
		
		// Save phone list
		btnSavePhoneList.setOnAction(e -> {

			if (tfSavePhoneFilePath.getText().trim().equals("")) { // If text field is empty
				taMyOutput.setText("Please enter path to Phone List.\n");
			} else {
				File phoneListFile = new File(tfSavePhoneFilePath.getText());
				try {
					pm.savePhoneManageObjectToFile(phoneListFile);
					taMyOutput.setText("Phone list saved!");
					tfSavePhoneFilePath.clear();
				} catch (Exception exception) {
					System.out.print("[Error] Cannont save DB. Cause: ");
					exception.printStackTrace();
					taMyOutput.setText("ERROR: Failed to save Phones DB!");
				}
			}
		});
		
		// Quit button action
		btnQuit.setOnAction(e -> Platform.exit());
			
		// Create scene and add the root node i.e. the GridPane
		Scene scene1 = new Scene(gridPane1, 600, 450);
		// Preparing the Stage (i.e. the container of any JavaFX application)      
		
		// Find number of command line arguments supplied 
		noOfCmdLineArgs = getParameters().getRaw().size();
		// If command line arguments have been provided then set the title to 
		// them. If none were provided then set title to default value.
		if (noOfCmdLineArgs > 0) {
			// Get command line arguments as String
			cmdLineArgs = getParameters().getRaw().toString();
			// Remove unwanted characters ([ and ] and ,)from string
			cmdLineArgs = cmdLineArgs.replaceAll("\\[|\\]|\\,", "");
			primaryStage.setTitle(cmdLineArgs);
		} else {
			// Default value
			// Set Stage Title
			primaryStage.setTitle("Phone Manage Application");
		}
		
		// Setting the scene to Stage
		primaryStage.setScene(scene1);
		// Displaying the stage
		primaryStage.show();
		
		
	}// End Start Method
	
	public static void main(String[] args) {
		launch(args);
	}
} // End Main class