
package thread;

import java.rmi.RemoteException;

import rmi.RemoteHelper;

/**
 * 程序结束之前登出，并清空临时文件
 * 
 * @author qwe
 *
 */
public class ShutThread extends Thread
{
	@Override
	public void run()
	{
		try
		{
			System.out.println("Shut thread running");
			RemoteHelper.getInstance().getIOService().clearTemp();
			RemoteHelper.getInstance().getUserService().logout(ui.State.getUsername());

		} catch (RemoteException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
