package runner;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import rmi.RemoteHelper;
import service.Language;
import thread.ShutThread;
import ui.Login;

/**
 * 连接到服务器 初始化GUI
 */
public class ClientRunner
{
	public static void main(String[] args)
	{
		new ClientRunner();
		System.out.println("main");
		// cr.test();
		Runtime.getRuntime().addShutdownHook(new ShutThread());
	}

	private RemoteHelper remoteHelper;

	public ClientRunner()
	{
		linkToServer();
		System.out.println("clientrunner");
		new JFXPanel();
		initGUI();
	}

	private void initGUI()
	{

		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					new Login();

				} catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		});
	}

	private void linkToServer()
	{
		try
		{
			remoteHelper = RemoteHelper.getInstance();
			remoteHelper.setRemote(Naming.lookup("rmi://localhost:8887/DataRemoteObject"));
			System.out.println("linked");
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (RemoteException e)
		{
			e.printStackTrace();
		} catch (NotBoundException e)
		{
			e.printStackTrace();
		}
	}

	public void test()
	{
		try
		{
			System.out.println(remoteHelper.getUserService().login("admin", "123456a"));
			System.out.println(remoteHelper.getIOService().writeFile("2", "admin", "testFile", Language.bf));
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
}
