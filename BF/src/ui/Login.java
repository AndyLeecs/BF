package ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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

	GridPane gridpane;
	
	public Login() throws IOException{
		gridpane = FXMLLoader.load(getClass().getResource("Login.fxml"));
		
		Scene scene = new Scene(gridpane,1200,800);
		scene.setFill(Color.TRANSPARENT);
//		scene.getStylesheets().add(getClass().getResource("MainWin.css").toExternalForm());
		
		this.setScene(scene);
		this.setResizable(false);
		this.initStyle(StageStyle.TRANSPARENT);
		this.show();
		
	}
	
}
