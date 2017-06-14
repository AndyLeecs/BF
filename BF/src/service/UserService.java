//服务器UserService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote{
	public boolean login(String username, String password) throws RemoteException;

	//把返回值改成了void，因为并不知道boolean有啥用
	public void logout(String username) throws RemoteException;
	
	public void register(String username, String password) throws RemoteException;
}
