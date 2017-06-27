package ui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import rmi.RemoteHelper;
import service.Language;

/**
 * 主窗口控制器
 * 
 * @author qwe
 *
 */

public class MainWinController
{
	// 文件菜单项的监听
	private class itemEventHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent event)
		{
			// 读取该文件
			String result = "";
			try
			{
				result = RemoteHelper.getInstance().getIOService().readFile(State.getUsername(),
						((MenuItem) event.getSource()).getText(), State.getLanguage());
			} catch (RemoteException e)
			{
				e.printStackTrace();
			}
			// 把读取的内容显示在ui界面上
			mainTextArea.setText(result);
			inputTextArea.setText("");
			outputLabel.setText("");
			lastcode = result;

			// 允许用户输入
			mainTextArea.setDisable(false);
			inputTextArea.setDisable(false);
			outputLabel.setDisable(false);

			// 获取该文件的历史版本
			filename = ((MenuItem) event.getSource()).getText().split("\\.")[0];

			try
			{

				ArrayList<String> filelist = RemoteHelper.getInstance().getIOService().readFileList(
						State.getUsername() + "\\" + State.getLanguage() + "\\" + filename, State.getLanguage());
				version.getItems().clear();
				for (int i = 0; (i < filelist.size()) && (i < MAX_FILE_LIST); i++)
				{

					// open.getItems().add(new MenuItem(filelist.get(i)));
					MenuItem newitem = new MenuItem(filelist.get(i));
					newitem.setOnAction(new versionItemEventHandler());
					version.getItems().add(newitem);

					System.out.println(filelist.get(i));

				}

			} catch (RemoteException e1)
			{
				e1.printStackTrace();
			}

		}
	}

	// 历史版本菜单项的监听
	private class versionItemEventHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent event)
		{

			// 读选择的文件
			String result = "";
			try
			{
				result = RemoteHelper.getInstance().getIOService().readFile(
						State.getUsername() + "\\" + State.getLanguage() + "\\" + filename,
						((MenuItem) event.getSource()).getText(), State.getLanguage());
			} catch (RemoteException e)
			{
				e.printStackTrace();
			}

			// 清空文本框
			mainTextArea.setText(result);
			System.out.println("this version is " + result);
			inputTextArea.setText("");
			outputLabel.setText("");

			// 更新上次编辑代码
			lastcode = result;

			// 允许用户输入
			mainTextArea.setDisable(false);
			inputTextArea.setDisable(false);
			outputLabel.setDisable(false);

			// 获取历史版本
			try
			{

				ArrayList<String> filelist = RemoteHelper.getInstance().getIOService().readFileList(
						State.getUsername() + "\\" + State.getLanguage() + "\\" + filename, State.getLanguage());
				version.getItems().clear();
				for (int i = 0; (i < filelist.size()) && (i < MAX_FILE_LIST); i++)
				{

					MenuItem newitem = new MenuItem(filelist.get(i));
					newitem.setOnAction(new versionItemEventHandler());
					version.getItems().add(newitem);

					System.out.println(filelist.get(i));

				}

			} catch (RemoteException e1)
			{
				e1.printStackTrace();
			}

		}
	}

	private static final int MAX_FILE_LIST = 10;
	/**
	 * 主窗口
	 */
	@FXML
	public AnchorPane gridpane;
	/**
	 * 代码输入文本框
	 */
	@FXML
	public TextArea mainTextArea;
	/**
	 * 控制台输入文本框
	 */
	@FXML
	public TextArea inputTextArea;
	/**
	 * 用户登出按钮
	 */
	@FXML
	public Button user;

	/**
	 * 控制台输出文本框
	 */
	@FXML
	public TextArea outputLabel;
	/**
	 * 新建文件的菜单选项
	 */
	@FXML
	public Menu newfile;

	/**
	 * 新建文件时跳出的窗口
	 */
	@FXML
	public AnchorPane newFileAnchorPane;
	/**
	 * 新建文件时的hbox
	 */
	@FXML
	public HBox fileNameBox;
	/**
	 * 输入文件名的文本框
	 */
	@FXML
	public TextArea filenameArea;
	/**
	 * 语言的选择器
	 */
	@FXML
	public ComboBox<String> language;
	/**
	 * 确认创建新文件
	 */
	@FXML
	public Button nameSubmitButton;
	/**
	 * 不创建新文件，返回主界面
	 */

	@FXML
	public Button newFileBackButton;

	/**
	 * 提示新文件的创建情况
	 */
	@FXML
	public Text tip;
	/**
	 * 打开新文件的菜单项
	 */
	@FXML
	public Menu open;
	/**
	 * 保存新文件的菜单项
	 */
	@FXML
	public MenuItem save;
	/**
	 * 退出的菜单项
	 */
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

	String filename = "";

	String lastcode = "";

	@FXML
	public void executeSetOnAction()
	{
		System.out.println("execute is clicked");
	}

	@FXML
	public void exitSetOnAction()
	{
		Platform.runLater(() ->
		{

			mainTextArea.setText("");
			inputTextArea.setText("");
			outputLabel.setText("");
			mainTextArea.setDisable(true);
			inputTextArea.setDisable(true);
			outputLabel.setDisable(true);
			// StorySelectBox ssb = new StorySelectBox(9);
			// gridpane.getScene().getWindow().hide();
		});

	}

	@FXML
	void initialize()
	{
		mainTextArea.setDisable(true);
		inputTextArea.setDisable(true);
		outputLabel.setDisable(true);
		tip.setVisible(false);
		language.getItems().add("bf");
		language.getItems().add("ook");
		language.setValue("bf");
		filename = "";

		try
		{

			ArrayList<String> filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername(),
					State.getLanguage());

			for (int i = 0; (i < filelist.size()) && (i < MAX_FILE_LIST); i++)
			{
				if (!open.getItems().contains(new MenuItem(filelist.get(i))))
				{

					MenuItem newitem = new MenuItem(filelist.get(i));
					open.getItems().add(newitem);
					newitem.setOnAction(new itemEventHandler());

				}

			}
		} catch (RemoteException e1)
		{
			e1.printStackTrace();
		}

	}

	@FXML
	public void logoutSetOnAction()
	{
		Platform.runLater(() ->
		{
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

	@FXML
	public void nameSubmitButtonSetOnAction()
	{
		// 记录文件名和当前语言
		filename = filenameArea.getText();
		System.out.println(filename);
		if (language.getValue().equals("ook"))
			State.setLanguage(Language.ook);
		System.out.println(Language.ook.toString());

		// 检查是否在允许的文字大小范围内
		if (filename.length() > 255)
		{
			tip.setText("longer than 255 chars.");
			tip.setStyle("-fx-text-fill:red;");
			tip.setTextAlignment(TextAlignment.CENTER);
			tip.setVisible(true);
			filenameArea.setText("");
			fileNameBox.setVisible(true);
			return;

		}
		// 检查是否含有非法字符
		String[] illegalChars = new String[]
		{ "/", "\\", ":", "*", "?", "<", ">", "\"", "|" };
		for (String s : illegalChars)
			if (filename.contains(s))
			{
				tip.setText("contains illegal char '" + s + "'.");
				tip.setStyle("-fx-text-fill:red;");
				tip.setTextAlignment(TextAlignment.CENTER);
				tip.setVisible(true);
				filenameArea.setText("");
				fileNameBox.setVisible(true);
				return;
			}

		// 检查是否有重名
		ArrayList<String> filelist;
		try
		{
			filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername(), State.getLanguage());
			for (String s : filelist)
			{

				System.out.println(s + "." + State.getLanguage());
				if ((filename + "." + State.getLanguage()).equals(s))
				{
					tip.setText("filename exists.");
					tip.setStyle("-fx-text-fill:red;");
					tip.setTextAlignment(TextAlignment.CENTER);
					tip.setVisible(true);
					filenameArea.setText("");
					fileNameBox.setVisible(true);
					return;
				}
			}

		} catch (RemoteException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 如果文件名非空，更新文件列表
		if (!filename.equals(""))
		{
			try
			{
				RemoteHelper.getInstance().getIOService().readFileList(State.getUsername(), State.getLanguage());
			} catch (RemoteException e)
			{

				e.printStackTrace();
			}
			// 将新建文件窗口设为不可见
			fileNameBox.setVisible(false);
			newFileAnchorPane.setVisible(false);

			// 清空文本框
			tip.setVisible(false);
			mainTextArea.setText("");
			inputTextArea.setText("");
			outputLabel.setText("");
			// 允许用户输入
			mainTextArea.setDisable(false);
			inputTextArea.setDisable(false);
			outputLabel.setDisable(false);

		}

	}

	/**
	 * 用户没有新建文件，从新建文件窗口返回
	 */
	@FXML
	public void newFileBackButtonSetOnAction()
	{
		newFileAnchorPane.setVisible(false);
	}

	/**
	 * 打开新建文件窗口
	 */
	@FXML
	public void newFileSetOnAction()
	{
		newFileAnchorPane.setVisible(true);
		fileNameBox.setVisible(true);
		System.out.println(filenameArea.getHeight());
		System.out.println(filenameArea.getWidth());

	}

	/**
	 * 打开文件
	 */
	@FXML
	public void openSetOnAction()
	{
		System.out.println("open is clicked");
		// 读文件列表
		try
		{

			ArrayList<String> filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername(),
					State.getLanguage());
			open.getItems().clear();
			for (int i = 0; (i < filelist.size()) && (i < MAX_FILE_LIST); i++)
			{

				MenuItem newitem = new MenuItem(filelist.get(i));
				newitem.setOnAction(new itemEventHandler());
				open.getItems().add(newitem);

				System.out.println(filelist.get(i));

			}
			// 读历史记录列表
			ArrayList<String> filelist1 = RemoteHelper.getInstance().getIOService().readFileList(
					State.getUsername() + "\\" + State.getLanguage() + "\\" + filename, State.getLanguage());
			version.getItems().clear();
			for (int i = 0; (i < filelist1.size()) && (i < MAX_FILE_LIST); i++)
			{

				// open.getItems().add(new MenuItem(filelist.get(i)));
				MenuItem newitem = new MenuItem(filelist1.get(i));
				newitem.setOnAction(new versionItemEventHandler());
				version.getItems().add(newitem);

				System.out.println(filelist1.get(i));

			}

		} catch (RemoteException e1)
		{
			e1.printStackTrace();
		}

	}

	/**
	 * 执行文件
	 */
	@FXML
	public void runSetOnAction()
	{
		String code = mainTextArea.getText();
		String param = inputTextArea.getText();
		try
		{
			String s = RemoteHelper.getInstance().getTransformService().transform(code, param, State.getLanguage());
			// 如果是错误信息，去掉prefix，显示红色的错误信息
			if (s.startsWith("Error:"))
			{
				s = s.replaceFirst("Error:", "");
				outputLabel.setStyle("-fx-text-fill:red;");

			} else
			{
				outputLabel.setStyle("-fx-text-fill:black;");
			}
			outputLabel.setText(s);
		} catch (RemoteException e1)
		{
			e1.printStackTrace();
		}

	}

	/**
	 * 存储文件
	 */
	@FXML
	public void saveSetOnAction()
	{
		System.out.println(lastcode);
		String code = mainTextArea.getText();
		System.out.println(code);
		// 确认本次保存和上一次有更改
		if (!lastcode.equals(code))
		{
			// 写文件
			try
			{
				System.out.println(code + State.getUsername() + filename + State.getLanguage());
				RemoteHelper.getInstance().getIOService().writeFile(code, State.getUsername(), filename,
						State.getLanguage());
				lastcode = code;
			} catch (RemoteException e1)
			{
				e1.printStackTrace();
			}
			// 更新打开文件列表
			try
			{

				ArrayList<String> filelist = RemoteHelper.getInstance().getIOService().readFileList(State.getUsername(),
						State.getLanguage());

				for (int i = 0; (i < filelist.size()) && (i < MAX_FILE_LIST); i++)
				{
					if (!open.getItems().contains(new MenuItem(filelist.get(i))))
					{
						// open.getItems().
						open.getItems().add(new MenuItem(filelist.get(i)));
						System.out.println(filelist.get(i));
					}

				}

			} catch (RemoteException e1)
			{
				e1.printStackTrace();
			}
			// 更新历史记录列表
			try
			{

				ArrayList<String> filelist = RemoteHelper.getInstance().getIOService().readFileList(
						State.getUsername() + "\\" + State.getLanguage() + "\\" + filename, State.getLanguage());
				version.getItems().clear();
				for (int i = 0; (i < filelist.size()) && (i < MAX_FILE_LIST); i++)
				{

					// open.getItems().add(new MenuItem(filelist.get(i)));
					MenuItem newitem = new MenuItem(filelist.get(i));
					newitem.setOnAction(new versionItemEventHandler());
					version.getItems().add(newitem);

					System.out.println(filelist.get(i));

				}

			} catch (RemoteException e1)
			{
				e1.printStackTrace();
			}
		}

	}

	/**
	 * 用户选择历史版本
	 */
	@FXML
	public void versionSetOnAction()
	{
		System.out.println("version is clicked");
		try
		{

			ArrayList<String> filelist = RemoteHelper.getInstance().getIOService().readFileList(
					State.getUsername() + "\\" + State.getLanguage() + "\\" + filename, State.getLanguage());
			version.getItems().clear();
			for (int i = 0; (i < filelist.size()) && (i < MAX_FILE_LIST); i++)
			{

				// open.getItems().add(new MenuItem(filelist.get(i)));
				MenuItem newitem = new MenuItem(filelist.get(i));
				newitem.setOnAction(new versionItemEventHandler());
				version.getItems().add(newitem);

				System.out.println(filelist.get(i));

			}

		} catch (RemoteException e1)
		{
			e1.printStackTrace();
		}

	}

}
