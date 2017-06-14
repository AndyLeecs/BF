package ui;

import java.io.IOException;
import java.rmi.RemoteException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
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
	public MenuItem newfile;
	@FXML
	public MenuItem open;
	@FXML
	public MenuItem save;
	@FXML
	public MenuItem exit;
	@FXML
	public MenuItem run;
	@FXML
	public MenuItem version;
	@FXML
	public MenuItem logout;
	
	@FXML
	void initialize(){
		mainTextArea.setDisable(true);
		inputTextArea.setDisable(true);
		
	}
	
	@FXML
	public void newSetOnAction(){
		mainTextArea.setDisable(false);
		inputTextArea.setDisable(false);
	}
	
	@FXML
	public void openSetOnAction(){
		try {
			RemoteHelper.getInstance().getIOService().readFileList(userId);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

	}
	
	@FXML
	public void saveSetOnAction(){
		String code = mainTextArea.getText();
		try {
			RemoteHelper.getInstance().getIOService().writeFile(code, "admin", "code",Language.bf);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

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
			RemoteHelper.getInstance().getExecuteService().execute(code,param);
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
