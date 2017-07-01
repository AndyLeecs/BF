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
			// 清空临时文件夹
			try
			{
				System.out.println("clear temp");

				RemoteHelper.getInstance().getIOService().clearTemp();
			} catch (RemoteException e2)
			{
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
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
			State.setVersion(0);
			State.setLatestVersion(1);
			// //存储第一次的版本
			// try
			// {
			// State.setVersion(RemoteHelper.getInstance().getIOService().writeTemp(mainTextArea.getText()));
			// State.setLatestVersion(0);
			// } catch (RemoteException e)
			// {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

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

			// 写入初始文件的状态

			try
			{
				System.out.println(mainTextArea.getText());
				System.out.println("in initial writes");
				State.setVersion(RemoteHelper.getInstance().getIOService().writeTemp(mainTextArea.getText()));
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			State.setLatestVersion(0);
			System.out.println(State.getVersion());
		}
	}

	// 历史版本菜单项的监听
	private class versionItemEventHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event)
		{

			// 清空临时文件夹
			try
			{
				RemoteHelper.getInstance().getIOService().clearTemp();
			} catch (RemoteException e2)
			{
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

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

			State.setVersion(0);
			State.setLatestVersion(0);
			// //存储第一次的版本
			// try
			// {
			// State.setVersion(RemoteHelper.getInstance().getIOService().writeTemp(mainTextArea.getText()));
			// State.setLatestVersion(0);
			// } catch (RemoteException e)
			// {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

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
	/**
	 * 执行操作的菜单
	 */
	@FXML
	public Menu execute;

	/**
	 * 运行代码的菜单项
	 */
	@FXML
	public MenuItem run;
	/**
	 * 查看当前文件版本的菜单
	 */
	@FXML
	public Menu version;
	/**
	 * 登出的菜单项
	 */
	@FXML
	public MenuItem logout;
	/**
	 * 撤销的菜单项
	 */
	@FXML
	public MenuItem undo;
	/**
	 * 重做的菜单项
	 */
	@FXML
	public MenuItem redo;

	/**
	 * 文件名
	 */
	String filename = "";

	/**
	 * 上一次代码
	 */
	String lastcode = "";

	@FXML
	public void executeSetOnAction()
	{
		System.out.println("execute is clicked");
	}

	@FXML
	public void exitSetOnAction()
	{
		// 清空临时文件夹
		try
		{
			RemoteHelper.getInstance().getIOService().clearTemp();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		try
		{
			RemoteHelper.getInstance().getIOService().clearTemp();
			State.setVersion(1);
			State.setLatestVersion(1);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		mainTextArea.setFocusTraversable(false);
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
		// 清空临时文件夹
		try
		{
			RemoteHelper.getInstance().getIOService().clearTemp();

			State.setVersion(1);
			State.setLatestVersion(1);

			RemoteHelper.getInstance().getUserService().logout(State.getUsername());

		} catch (RemoteException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
			// 清空临时文件夹
			try
			{
				RemoteHelper.getInstance().getIOService().clearTemp();
				State.setVersion(1);
				State.setLatestVersion(1);

			} catch (RemoteException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

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

			// 存储第一次的版本
			try
			{
				State.setVersion(RemoteHelper.getInstance().getIOService().writeTemp(mainTextArea.getText()));
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
//		newFileAnchorPane.setVisible(false);
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
//		newFileAnchorPane.setVisible(false);

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
//		newFileAnchorPane.setVisible(false);

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
		newFileAnchorPane.setVisible(false);

	}

	/**
	 * 如果当前不是尚未更改的版本，回到上一个版本
	 */
	@FXML
	public void undoSetOnAction()
	{

		System.out.println("undo pressed");
		System.out.println(State.getVersion() + "");
		System.out.println(State.getLatestVersion() + "");
		System.out.println("code before undo" + mainTextArea.getText());

		// undo之前保存一次当前版本
		if ((State.getVersion() == State.getLatestVersion()))
			try
			{
				System.out.println(mainTextArea.getText());
				System.out.println("in equal version writes");
				if (RemoteHelper.getInstance().getIOService().check(mainTextArea.getText()))
					State.setVersion(RemoteHelper.getInstance().getIOService().writeTemp(mainTextArea.getText()));
				System.out.println(mainTextArea.getText());
				System.out.println(State.getVersion());
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		State.setLatestVersion();
		// System.out.println(State.getVersion());
		//
		// 检查当前是否是尚未操作状态
		if ((filename != "") && State.getVersion() >= 1)
		{
			try
			{
				System.out.println(State.getVersion() + "");
				String code = RemoteHelper.getInstance().getIOService().readTempForward(State.getVersion());
				mainTextArea.setText(code);
				State.setVersionAfterUndo();
			} catch (RemoteException e)
			{
				e.printStackTrace();
			}
		}
		newFileAnchorPane.setVisible(false);
	}

	/**
	 * 如果当前不是最后存入的版本，回到下一版本
	 */
	@FXML
	public void redoSetOnAction()
	{
		// System.out.println("redo pressed");
		// System.out.println("version" + State.getVersion());
		// System.out.println("latestversion" + State.getLatestVersion());
		// System.exit(0);
		// 检查当前是否是记录的最后一次操作
		if ((filename != "") && ((State.getVersion()) < State.getLatestVersion()))
		{
			try
			{
				System.out.println(State.getVersion() + "");
				String code = RemoteHelper.getInstance().getIOService().readTempBack(State.getVersion());
				mainTextArea.setText(code);
				State.setVersionAfterRedo();
			} catch (RemoteException e)
			{
				e.printStackTrace();
			}

			// State.setVersion(State.getVersion() - 1);
			State.setLatestVersion();
			newFileAnchorPane.setVisible(false);
		}
	}

}
