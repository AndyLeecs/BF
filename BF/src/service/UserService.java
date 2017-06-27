//服务器UserService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote
{
	/**
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 是否能成功登入
	 * @throws RemoteException
	 */
	public boolean login(String username, String password) throws RemoteException;

	/**
	 * @param username
	 *            用户名
	 * @return 是否成功登出
	 * @throws RemoteException
	 */
	public boolean logout(String username) throws RemoteException;

	/**
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 是否成功注册
	 * @throws RemoteException
	 */
	public boolean register(String username, String password) throws RemoteException;
}
