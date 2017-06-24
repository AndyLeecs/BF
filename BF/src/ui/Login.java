package ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**  
* 类说明   
*  
* @author Andy
* @version  
*/

public class Login extends Stage
{

	AnchorPane anchorpane;
	
	public Login() throws IOException{
		anchorpane = FXMLLoader.load(getClass().getResource("LoginWin.fxml"));
		
		Scene scene = new Scene(anchorpane,600,400);
		scene.setFill(Color.TRANSPARENT);
//		scene.getStylesheets().add(getClass().getResource("MainWin.css").toExternalForm());
		
		this.setScene(scene);
		this.setResizable(false);
		this.initStyle(StageStyle.TRANSPARENT);
		this.show();
		
	}
	
}
