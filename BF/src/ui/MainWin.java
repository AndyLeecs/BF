package ui;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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

public class MainWin extends Stage
{
	AnchorPane gridpane;
	
	public MainWin() throws IOException{
		gridpane = FXMLLoader.load(getClass().getResource("MainWin.fxml"));
		
		Scene scene = new Scene(gridpane,1180,800);
		scene.setFill(Color.TRANSPARENT);
//		scene.getStylesheets().add(getClass().getResource("MainWin.css").toExternalForm());
		this.setTitle("BF&OOK Hospital!");
		this.setScene(scene);
		this.setResizable(false);
		this.initStyle(StageStyle.DECORATED);
//		this.initStyle(StageStyle.TRANSPARENT);
		this.show();
		
	}
	
//	public static void main(String args[]) throws IOException{
//		Platform.runLater(() ->
//		{
//				try
//				{
//					new MainWin();
//				} catch (IOException e)
//				{
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	});
//
//}
}
