package ui;

import javafx.scene.control.ComboBox;

/**  
* 暂时弃用的选择框
*  
* @author Andy
* @version  
*/

public class SelectWinController
{
//@FXML
//ChoiceBox choicebox;

//@FXML
//void initialize(){
//choicebox = new ChoiceBox(FXCollections.observableArrayList(
//    "bf", "ook"));
//
//choicebox.setTooltip(new Tooltip("Select the language."));
//}

@SuppressWarnings("rawtypes")
static
ComboBox combobox = new ComboBox();
static{
for(int i = 0; i < State.getFiles().length; i++){
	combobox.getItems().add(State.getFiles()[i].getAbsolutePath());
	
}
	

}
}
