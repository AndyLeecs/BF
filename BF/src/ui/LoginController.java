package ui;

import java.io.IOException;
import java.rmi.RemoteException;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import rmi.RemoteHelper;

/**
 * 登陆窗口控制器
 * 
 * @author qwe
 */

/**
 * @author qwe
 *
 */
/**
 * @author qwe
 *
 */
/**
 * @author qwe
 *
 */
public class LoginController
{
	/**
	 * 主面板
	 */
	@FXML
	private AnchorPane anchorpane;
	/**
	 * 用户名输入框
	 */
	@FXML
	private TextField textField;
	/**
	 * 图片
	 */
	@FXML
	private ImageView imageView;
	/**
	 * 密码框
	 */
	@FXML
	private PasswordField passwordField;
	/**
	 * 确认登录按钮
	 */
	@FXML
	private Button LoginButton;
	/**
	 * 提示标签
	 */
	@FXML
	private Label tip;
	/**
	 * 注册按钮
	 */
	@FXML
	private Button newComer;
	/**
	 * 退出按钮
	 */
	@FXML
	private Button exit;
	/**
	 * 记录是否为新注册的用户
	 */
	public boolean newcomer = false;

	/**
	 * 退出
	 */
	@FXML
	public void exitOnAction()
	{
		Platform.runLater(() ->
		{
			anchorpane.getScene().getWindow().hide();
			System.exit(0);
		});

	}

	@FXML
	void initialize()
	{
		textField.setPromptText("请输入精神病院患者名");
		passwordField.setPromptText("请输入精神病院患者密码");
		newcomer = false;

		DoubleProperty x = new SimpleDoubleProperty();
		x.set(400);
		imageView.xProperty().bind(x);
		anchorpane.heightProperty().add(x);

		DoubleProperty y = new SimpleDoubleProperty();
		y.set(600);
		imageView.yProperty().bind(y);
		anchorpane.widthProperty().add(y);

	}

	/**
	 * 登入
	 */
	@FXML
	public void LoginButtonOnAction()
	{
		// 如果用户名和密码中有一个为空
		if ((textField.getText() == null) || (passwordField.getText() == null) || textField.getText().length() == 0
				|| passwordField.getText().length() == 0)
			tip.setText("   呵呵");

		else if ((textField.getText() != null && !textField.getText().isEmpty())
				&& (passwordField.getText() != null && !passwordField.getText().isEmpty()))
		{
			try
			{
				// 如果是新用户
				if (newcomer)
				{
					try
					{
						if (RemoteHelper.getInstance().getUserService().register(textField.getText(),
								passwordField.getText()))
						{
							Platform.runLater(() ->
							{
								try
								{
									State.setUsername(textField.getText());
									new MainWin();

								} catch (IOException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								anchorpane.getScene().getWindow().hide();
							});
						} else
						{
							// 如果新用户的用户名已存在
							tip.setText("   这傻名儿有人用");
						}
					} catch (RemoteException e)
					{
						e.printStackTrace();

					}
				}

				else if (RemoteHelper.getInstance().getUserService().login(textField.getText(),
						passwordField.getText()))
					Platform.runLater(() ->
					{
						try
						{
							State.setUsername(textField.getText());
							new MainWin();
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						anchorpane.getScene().getWindow().hide();
					});
				else
				{
					// 如果用户不存在
					// 或者密码错误
					// 或者已经登入了
					tip.setText("   像你这种不让进的");

				}
			} catch (RemoteException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 注册
	 */
	@FXML
	public void newComerOnAction()
	{
		textField.clear();
		passwordField.clear();
		textField.setPromptText("新来的写个名儿");
		passwordField.setPromptText("新来的输个密码");
		newcomer = true;

	}
}
