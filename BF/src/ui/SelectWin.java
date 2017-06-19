package ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**  
* 暂时弃用的选择框   
*  
* @author Andy
* @version  
*/

public class SelectWin extends Stage
{
	GridPane gridpane;
	
	public SelectWin() throws IOException{
		gridpane = FXMLLoader.load(getClass().getResource("SelectWin.fxml"));
		
		Scene scene = new Scene(gridpane,300,200);
		scene.setFill(Color.TRANSPARENT);
//		scene.getStylesheets().add(getClass().getResource("MainWin.css").toExternalForm());
		
		this.setScene(scene);
		this.setResizable(false);
		this.initStyle(StageStyle.TRANSPARENT);
		this.show();
		
	}
}
