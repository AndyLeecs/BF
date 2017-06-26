package ui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
	public AnchorPane gridpane;
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
	public AnchorPane newFileAnchorPane;
	@FXML
	public HBox fileNameBox;
	@FXML
	public TextArea filename_field;
	@FXML
	public ComboBox<String> language;
	@FXML
	public Button name_submit_button;
//	@FXML
//	public Menu newfile;
	@FXML
	public Text tip;
	@FXML
	public Menu open;
	@FXML
	public MenuItem save;
	@FXML
	public MenuItem exit;
	@FXML
	public Menu execute;
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
		tip.setVisible(false);
		language.getItems().add("bf");
		language.getItems().add("ook");
		language.setValue("bf");
		filename = "";
		
		try {
			
			ArrayList<String>filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername(),State.getLanguage());
			
			for(int i = 0; (i < filelist.size())&&(i < MAX_FILE_LIST) ;i++){
				if(!open.getItems().contains(new MenuItem(filelist.get(i)))){
					
			MenuItem newitem = new MenuItem(filelist.get(i));
				open.getItems().add(newitem);
				newitem.setOnAction(new itemEventHandler());
				
			}
			
			
			
		}} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		

	}
	@FXML
	public void newFileSetOnAction(){
		newFileAnchorPane.setVisible(true);
		fileNameBox.setVisible(true);
//		filename_field.setMaxHeight(36.0);
//		filename_field.setMinHeight(36.0);
//		filename_field.setPrefHeight(36.0);

//		filename_field.setPrefRowCount(1);
//		filename_field.setStyle("-overflow-x:hidden;");
		System.out.println(filename_field.getHeight());
		System.out.println(filename_field.getWidth());
		
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
		
		//检查是否在允许的文字大小范围内
		if(filename.length()>255)
		{
			tip.setText("longer than 255 chars.");
			tip.setStyle("-fx-text-fill:red;");
			tip.setTextAlignment(TextAlignment.CENTER);
			tip.setVisible(true);
			filename_field.setText("");
			fileNameBox.setVisible(true);
			return;

		}
		//检查是否含有非法字符
		String[] illegalChars = new String[]{"/","\\",":","*","?","<",">","\"","|"};
		for(String s:illegalChars)
		if(filename.contains(s)){
			tip.setText("contains illegal char '"+s+"'.");
			tip.setStyle("-fx-text-fill:red;");
			tip.setTextAlignment(TextAlignment.CENTER);
			tip.setVisible(true);
			filename_field.setText("");
			fileNameBox.setVisible(true);
			return;
		}
		
		//检查是否有重名
		ArrayList<String> filelist;
		try
		{
			filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername(),State.getLanguage());
			for(String s:filelist){
				
				System.out.println(s+"."+State.getLanguage());
				if((filename+"."+State.getLanguage()).equals(s)){
					tip.setText("filename exists.");
					tip.setStyle("-fx-text-fill:red;");
					tip.setTextAlignment(TextAlignment.CENTER);
					tip.setVisible(true);
					filename_field.setText("");
					fileNameBox.setVisible(true);
					return;
				}
			}
		
		} catch (RemoteException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(!filename.equals("")){
		try
		{
			RemoteHelper.getInstance().getIOService().readFileList(State.getUsername(),State.getLanguage());
		} catch (RemoteException e)
		{
			
			e.printStackTrace();
		}
		fileNameBox.setVisible(false);
		newFileAnchorPane.setVisible(false);
	
		tip.setVisible(false);
		mainTextArea.setText("");
		inputTextArea.setText("");
		outputLabel.setText("");
		mainTextArea.setDisable(false);
		inputTextArea.setDisable(false);
		outputLabel.setDisable(false);

		
		}
	
	}
	
	private class itemEventHandler implements EventHandler<ActionEvent> {  
		  
	    @Override  
	    public void handle(ActionEvent event) {  
	        
	        
			String result = "";
			try
			{
				result = RemoteHelper.getInstance().getIOService().readFile(State.getUsername(),((MenuItem) event.getSource()).getText(),State.getLanguage());
			} catch (RemoteException e)
			{
				e.printStackTrace();
			}
			mainTextArea.setText(result);
			inputTextArea.setText("");
			outputLabel.setText("");
			lastcode = result;
			
		mainTextArea.setDisable(false);
		inputTextArea.setDisable(false);
		outputLabel.setDisable(false);
		
		filename = ((MenuItem) event.getSource()).getText().split("\\.")[0];

		
		try {
			
			ArrayList<String>filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername()+"\\"+State.getLanguage() + "\\" + filename,State.getLanguage());
			version.getItems().clear();
			for(int i = 0; (i < filelist.size())&&(i < MAX_FILE_LIST) ;i++){
				
				//open.getItems().add(new MenuItem(filelist.get(i)));
				MenuItem newitem = new MenuItem(filelist.get(i));
				newitem.setOnAction(new versionItemEventHandler());
				version.getItems().add(newitem);
		    
				
				System.out.println(filelist.get(i));
				
				}
				

			
	}catch (RemoteException e1) {
			e1.printStackTrace();
		}

	    }
	} 

	private class versionItemEventHandler implements EventHandler<ActionEvent> {  
		  
	    @Override  
	    public void handle(ActionEvent event) {  
	        
	        
			String result = "";
			try
			{
				result = RemoteHelper.getInstance().getIOService().readFile(State.getUsername()+"\\"+State.getLanguage() + "\\" + filename,((MenuItem) event.getSource()).getText(),State.getLanguage());
			} catch (RemoteException e)
			{
				e.printStackTrace();
			}
			mainTextArea.setText(result);
			System.out.println("this version is " + result);
			inputTextArea.setText("");
			outputLabel.setText("");
			lastcode = result;
			
		mainTextArea.setDisable(false);
		inputTextArea.setDisable(false);
		outputLabel.setDisable(false);
		
		
		try {
			
			ArrayList<String>filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername()+"\\"+State.getLanguage() + "\\" + filename,State.getLanguage());
			version.getItems().clear();
			for(int i = 0; (i < filelist.size())&&(i < MAX_FILE_LIST) ;i++){
				
				//open.getItems().add(new MenuItem(filelist.get(i)));
				MenuItem newitem = new MenuItem(filelist.get(i));
				newitem.setOnAction(new versionItemEventHandler());
				version.getItems().add(newitem);
		    
				
				System.out.println(filelist.get(i));
				
				}
				

			
	}catch (RemoteException e1) {
			e1.printStackTrace();
		}

	    }
	} 
	@FXML
	public void openSetOnAction(){
		System.out.println("open is clicked");
		
		try {
			
			ArrayList<String>filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername(),State.getLanguage());
			open.getItems().clear();
			for(int i = 0; (i < filelist.size())&&(i < MAX_FILE_LIST) ;i++){
				
				//open.getItems().add(new MenuItem(filelist.get(i)));
				MenuItem newitem = new MenuItem(filelist.get(i));
				newitem.setOnAction(new itemEventHandler());
				open.getItems().add(newitem);
		    
				
				System.out.println(filelist.get(i));
				
				}
				
			
				
				ArrayList<String>filelist1 = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername()+"\\"+State.getLanguage() + "\\" + filename,State.getLanguage());
				version.getItems().clear();
				for(int i = 0; (i < filelist1.size())&&(i < MAX_FILE_LIST) ;i++){
					
					//open.getItems().add(new MenuItem(filelist.get(i)));
					MenuItem newitem = new MenuItem(filelist1.get(i));
					newitem.setOnAction(new versionItemEventHandler());
					version.getItems().add(newitem);
			    
					
					System.out.println(filelist1.get(i));
					
					}
					

				
		}catch (RemoteException e1) {
				e1.printStackTrace();
			}

		    }

		

	


	
	
	@FXML
	public void saveSetOnAction(){
		System.out.println(lastcode);
		String code = mainTextArea.getText();
System.out.println(code);
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
		
		try {
			
			ArrayList<String>filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername()+"\\"+State.getLanguage() + "\\" + filename,State.getLanguage());
			version.getItems().clear();
			for(int i = 0; (i < filelist.size())&&(i < MAX_FILE_LIST) ;i++){
				
				//open.getItems().add(new MenuItem(filelist.get(i)));
				MenuItem newitem = new MenuItem(filelist.get(i));
				newitem.setOnAction(new versionItemEventHandler());
				version.getItems().add(newitem);
		    
				
				System.out.println(filelist.get(i));
				
				}
				

			
	}catch (RemoteException e1) {
			e1.printStackTrace();
		}
		}
		
//		Platform.runLater(() ->{
//			try
//			{
//				new SelectWin();
//			} catch (IOException e)
//			{
//			
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
			
			mainTextArea.setText("");
			inputTextArea.setText("");
			outputLabel.setText("");
			mainTextArea.setDisable(true);
			inputTextArea.setDisable(true);
			outputLabel.setDisable(true);
			// StorySelectBox ssb = new StorySelectBox(9);
//			gridpane.getScene().getWindow().hide();
		});

	}
	
	@FXML
	public void executeSetOnAction(){
		System.out.println("execute is clicked");
	}
	
	@FXML
	public void runSetOnAction(){
		String code = mainTextArea.getText();
		String param = inputTextArea.getText();
		try {
			String s = RemoteHelper.getInstance().getTransformService().transform(code, param, State.getLanguage());
			if(s.startsWith("Error:"))
			{
				s = s.replaceFirst("Error:", "");
				outputLabel.setStyle("-fx-text-fill:red;");
			
			}else{
				outputLabel.setStyle("-fx-text-fill:black;");
			}
			outputLabel.setText(s);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

	}
	
	@FXML
	public void versionSetOnAction(){
		System.out.println("version is clicked");
		try {
			
			ArrayList<String>filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername()+"\\"+State.getLanguage() + "\\" + filename,State.getLanguage());
			version.getItems().clear();
			for(int i = 0; (i < filelist.size())&&(i < MAX_FILE_LIST) ;i++){
				
				//open.getItems().add(new MenuItem(filelist.get(i)));
				MenuItem newitem = new MenuItem(filelist.get(i));
				newitem.setOnAction(new versionItemEventHandler());
				version.getItems().add(newitem);
		    
				
				System.out.println(filelist.get(i));
				
				}
				

			
	}catch (RemoteException e1) {
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
				
				e.printStackTrace();
			}
		gridpane.getScene().getWindow().hide();
		
	});
		
		
	}
	
	
}
