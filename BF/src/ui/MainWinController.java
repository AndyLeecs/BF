package ui;

import java.io.IOException;
import java.rmi.RemoteException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import rmi.RemoteHelper;
import service.Language;

/**  
* 类说明   
*  
* @author Andy
* @version  
*/

public class MainWinController
{
	@FXML
	public GridPane gridpane;
	@FXML
	public TextArea mainTextArea;
	@FXML
	public TextArea inputTextArea;
	@FXML
	public Button user;
//	@FXML
//	public Tooltip tooltip;
	@FXML
	public Label outputLabel;
	@FXML
	public Menu newfile;
//	@FXML
//	public MenuItem bf;
//	@FXML
//	public MenuItem ook;
	@FXML
	public TextField filename_field;
	@FXML
	public ComboBox<String> language;
	@FXML
	public Button name_submit_button;
	@FXML
	public Menu open;
	@FXML
	public MenuItem save;
	@FXML
	public MenuItem exit;
	@FXML
	public MenuItem run;
	@FXML
	public Menu version;
	@FXML
	public MenuItem logout;
	
	private static final int MAX_FILE_LIST = 10;//open时显示的最大长度
	String filename = "";
	String lastcode = "";
	
	
	@FXML
	void initialize(){
		mainTextArea.setDisable(true);
		inputTextArea.setDisable(true);
		language.getItems().add("bf");
		language.getItems().add("ook");
		language.setValue("bf");
		filename = "";
		

	}
	
	@FXML
	public void name_submit_buttonSetOnAction(){
		
		filename = filename_field.getText();
		if(language.getValue().equals("ook"))
			State.setLanguage(Language.ook);
		mainTextArea.setDisable(false);
		inputTextArea.setDisable(false);
		
	}
	
	@FXML
	public void openSetOnAction(){
		try {
			
			String[]filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername());
			for(int i = 0; (i < filelist.length)&&(i < MAX_FILE_LIST) ;i++){
				open.getItems().add(new MenuItem(filelist[i]));
				
			}
			
			
			
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

	}
	
	@FXML
	public void saveSetOnAction(){
		
		String code = mainTextArea.getText();

		if(lastcode != code){
		try {
			
			RemoteHelper.getInstance().getIOService().writeFile(code, State.getUsername(), filename,State.getLanguage());
			lastcode = code;
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}}
		
//		Platform.runLater(() ->{
//			try
//			{
//				new SelectWin();
//			} catch (IOException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
//		
//	});		
		}

	
	
	@FXML
	public void exitSetOnAction(){
		Platform.runLater(() ->
		{
			mainTextArea.setDisable(true);
			inputTextArea.setDisable(true);
			outputLabel.setText("");
			// StorySelectBox ssb = new StorySelectBox(9);
//			gridpane.getScene().getWindow().hide();
		});

	}
	
	@FXML
	public void runSetOnAction(){
		String code = mainTextArea.getText();
		String param = inputTextArea.getText().replace(" ", "");
		try {
			RemoteHelper.getInstance().getTransformService().transform(code, param, State.getLanguage());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

	}
	
	@FXML
	public void versionSetOnAction(){
		try {
			String code = mainTextArea.getText();
			String[]filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername()+"//"+filename);
			for(int i = 0; (i < filelist.length)&&(i < MAX_FILE_LIST) ;i++){
				open.getItems().add(new MenuItem(filelist[i]));
				
			}
			
			
			
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}


	}
	
	@FXML
	public void logoutSetOnAction(){
		Platform.runLater(() ->{
			try
			{
				new Login();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		gridpane.getScene().getWindow().hide();
		
	});
		
		
	}
	
	
}
