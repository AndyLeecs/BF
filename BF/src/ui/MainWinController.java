package ui;

import java.rmi.RemoteException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
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
	public void newSetOnAction(){
		try {
			RemoteHelper.getInstance().getIOService().writeFile(file,userId,fileName,l);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

	}
	
	@FXML
	public void openSetOnAction(){
		
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
			
			// StorySelectBox ssb = new StorySelectBox(9);
			gridpane.getScene().getWindow().hide();
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
	
	
}
