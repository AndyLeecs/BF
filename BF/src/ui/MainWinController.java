package ui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
	public TextArea outputLabel;
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
//	@FXML
//	public Menu newfile;
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
		outputLabel.setDisable(true);
		language.getItems().add("bf");
		language.getItems().add("ook");
		language.setValue("bf");
		filename = "";
		
		try {
			
			ArrayList<String>filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername(),State.getLanguage());
			
			for(int i = 0; (i < filelist.size())&&(i < MAX_FILE_LIST) ;i++){
				if(!open.getItems().contains(new MenuItem(filelist.get(i))))
				open.getItems().add(new MenuItem(filelist.get(i)));
				
			}
			
			
			
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

	}
	@FXML
	public void name_submit_buttonSetOnAction(){
		
		filename = filename_field.getText();
		System.out.println(filename);
		if(language.getValue().equals("ook"))
			State.setLanguage(Language.ook);
		System.out.println(Language.ook.toString());
//		mainTextArea.
//		inputTextArea.clear();
//		outputLabel.clear();
		try
		{
			RemoteHelper.getInstance().getIOService().readFileList(State.getUsername(),State.getLanguage());
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainTextArea.setText("");
		inputTextArea.setText("");
		outputLabel.setText("");
		mainTextArea.setDisable(false);
		inputTextArea.setDisable(false);
		outputLabel.setDisable(false);

		
		
	
	}
	
	private class itemEventHandler implements EventHandler<ActionEvent> {  
		  
	    @Override  
	    public void handle(ActionEvent event) {  
	        String fileName=((MenuItem)event.getSource()).getText();//得到版本名
	        
			String result = "";
			try
			{
				result = RemoteHelper.getInstance().getIOService().readFile(State.getUsername(),((MenuItem) event.getSource()).getText(),State.getLanguage());
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mainTextArea.setText(result);
	    }  
	} 
	
	@FXML
	public void openSetOnAction(){
		try {
			
			ArrayList<String>filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername(),State.getLanguage());
			open.getItems().clear();
			for(int i = 0; (i < filelist.size())&&(i < MAX_FILE_LIST) ;i++){
				
				open.getItems().add(new MenuItem(filelist.get(i)));
				MenuItem newitem = new MenuItem(filelist.get(i));
				newitem.setOnAction(new itemEventHandler());
		    
				
				System.out.println(filelist.get(i));
				
				}
				
			}
			
			
			
		catch (RemoteException e1) {
			e1.printStackTrace();
		}
		

	}


	
	
	@FXML
	public void saveSetOnAction(){
		
		String code = mainTextArea.getText();

		if(!lastcode.equals(code)){
		try {
			System.out.println(code+State.getUsername()+filename+State.getLanguage());
			RemoteHelper.getInstance().getIOService().writeFile(code, State.getUsername(), filename,State.getLanguage());
			lastcode = code;
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		try {
			
			ArrayList<String>filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername(),State.getLanguage());
			
			for(int i = 0; (i < filelist.size())&&(i < MAX_FILE_LIST) ;i++){
				if(!open.getItems().contains(new MenuItem(filelist.get(i)))){
//					open.getItems().
				open.getItems().add(new MenuItem(filelist.get(i)));
				System.out.println(filelist.get(i));
				}
				
			}
			
			
			
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		}
		
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
			outputLabel.setDisable(true);
			// StorySelectBox ssb = new StorySelectBox(9);
//			gridpane.getScene().getWindow().hide();
		});

	}
	
	@FXML
	public void runSetOnAction(){
		String code = mainTextArea.getText();
		String param = inputTextArea.getText().replace(" ", "");
		try {
			outputLabel.setText(RemoteHelper.getInstance().getTransformService().transform(code, param, State.getLanguage()));
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

	}
	
	@FXML
	public void versionSetOnAction(){
		try {
			String code = mainTextArea.getText();
			ArrayList<String>filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername()+"//"+filename,State.getLanguage());
			for(int i = 0; (i < filelist.size())&&(i < MAX_FILE_LIST) ;i++){
				open.getItems().add(new MenuItem(filelist.get(i)));
				
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
