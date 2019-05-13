package application;

import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {

	@FXML private TextField txfPath1;
	@FXML private TextField txfPath2;
	@FXML private TextArea duplicateFiles;
	

	@FXML
	public void display(ActionEvent e) throws Exception {
		System.out.println("button clicked");
		Checker m = new Checker(txfPath1.getText().toString(), txfPath2.getText().toString());
		HashSet<String> returnedDirectory = new HashSet<>();
		Thread displayThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					returnedDirectory.addAll(m.loadFiles());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String temp="";
				for(String set:returnedDirectory)
					temp += set + "\n";
				duplicateFiles.setText(temp);
				if(temp =="")
					duplicateFiles.setText("No Duplicate files found");
			}
			
		});
		displayThread.start();
		displayThread.join();

	}
}
