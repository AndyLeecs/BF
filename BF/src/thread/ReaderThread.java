
package thread;

import java.rmi.RemoteException;
import java.util.Date;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import rmi.RemoteHelper;
import ui.MainWinController;

/**
 * 实时保存用户代码输入的类
 * 
 * @author qwe
 *
 */
public class ReaderThread extends Thread
{

	/**
	 * 主窗口控制器
	 */
	MainWinController controller;
	/**
	 * 上一次键盘输入时间
	 */
	long time1 = 1;

	/**
	 * 本次键盘输入时间
	 */
	long time2;

	public ReaderThread(MainWinController controller)
	{
		this.controller = controller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 * 
	 */
	public void run()
	{
		System.out.println(controller.getClass().getName());
		controller.mainTextArea.setOnKeyTyped(new WriteTempHandler());
		// controller.undo.setOnAction(new StopTempHandler());
		// controller.redo.setOnAction(new StopTempHandler());

	}

	/*
	 * private class StopTempHandler implements EventHandler{
	 * 
	 * (non-Javadoc)
	 * 
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 * 
	 * @Override public void handle(Event event) { // TODO Auto-generated method
	 * stub
	 * 
	 * 
	 * }
	 * 
	 * }
	 */
	private class WriteTempHandler implements EventHandler<KeyEvent>
	{

		/*
		 * (non-Javadoc)
		 * 
		 * @see javafx.event.EventHandler#handle(javafx.event.Event)
		 */
		@Override
		public void handle(KeyEvent event)
		{
			System.out.println("thread running");
			System.out.println(time1 + "");
			System.out.println(time2 + "");
			time2 = new Date().getTime();
			// 时间差大于300毫秒，写入临时文件夹
			if (time2 >= time1 + 300)
			{
				try
				{
					System.out.println(controller.mainTextArea.getText());
					ui.State.setVersion(
							RemoteHelper.getInstance().getIOService().writeTemp(controller.mainTextArea.getText()));
					ui.State.setLatestVersion();
					System.out.println(ui.State.getVersion() + "");
					System.out.println(ui.State.getLatestVersion() + "");
				} catch (RemoteException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			time1 = time2;

		}

	}

}
