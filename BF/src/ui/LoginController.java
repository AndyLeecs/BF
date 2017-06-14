package ui;

import java.io.IOException;
import java.rmi.RemoteException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import rmi.RemoteHelper;

/**  
* 类说明   
*  
* @author Andy
* @version  
*/

public class LoginController
{
	@FXML
	private GridPane gridpane;
	@FXML
	private TextField textField;
	
	@FXML 
	private PasswordField passwordField;
	@FXML
	private Button LoginButton;
	@FXML
	private Label tip;
	@FXML
	private Button newComer;
	@FXML
	private Button exit;
	
	@FXML
	void initialize(){
		textField.setPromptText("请输入精神病院患者名");
		passwordField.setPromptText("请输入精神病院患者密码");
		LoginButton.setText("我要进去");
		newComer.setText("你是新来的？嘿嘿嘿");
	}
	
	@FXML
	public void LoginButtonOnAction(){
		if((textField.getText()!=null && !textField.getText().isEmpty())
			&&(passwordField.getText()!=null&&!passwordField.getText().isEmpty())){
			try{
				if(RemoteHelper.getInstance().getUserService().login(textField.getText(),passwordField.getText()))
					Platform.runLater(()->{
					try
					{
						new MainWin();
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gridpane.getScene().getWindow().hide();
					});	
				else{
					tip.setText("正常人不让进的");
				}
			}catch(RemoteException e){
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	public void newComerOnAction(){
		textField.setPromptText("新来的写个名儿");
		passwordField.setPromptText("新来的输个密码");
		//没有实现密码确认
	}
	
	@FXML
	public void exitOnAction(){
		Platform.runLater(() ->{
			gridpane.getScene().getWindow().hide();
			System.exit(0);
		});
		
	}
}
