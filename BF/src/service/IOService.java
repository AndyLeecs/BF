//服务器IOService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IOService extends Remote
{
	/**
	 * @param userId
	 *            用户名
	 * @param fileName
	 *            文件名
	 * @param l
	 *            语言类型
	 * @return 文件内容
	 * @throws RemoteException
	 */
	public String readFile(String userId, String fileName, Language l) throws RemoteException;

	/**
	 * @param userId
	 *            用户名
	 * @param l
	 *            语言类型
	 * @return 该用户的文件列表或该文件的历史版本，历史版本数目超过10个时返回10个，将时间最远的一个在文件夹中清空
	 * @throws RemoteException
	 */
	public ArrayList<String> readFileList(String userId, Language l) throws RemoteException;

	/**
	 * @param file
	 *            文件内容
	 * @param userId
	 *            用户名
	 * @param fileName
	 *            文件名
	 * @param l
	 *            语言类型
	 * @return 文件列表
	 * @throws RemoteException
	 */
	public String[] writeFile(String file, String userId, String fileName, Language l) throws RemoteException;

	/**
	 * @param code
	 *            当前用户输入的代码
	 * @return 本次输入代码保存的版本
	 * @throws RemoteException
	 */
	public int writeTemp(String code) throws RemoteException;

	/**
	 * @param version
	 *            本次版本
	 * @return 上一个版本的代码
	 * @throws RemoteException
	 */
	public String readTempBack(int version) throws RemoteException;

	/**
	 * @param version
	 *            本次版本
	 * @return 下一个版本的代码
	 * @throws RemoteException
	 */
	public String readTempForward(int version) throws RemoteException;

	/**
	 * 清空临时文件夹
	 * 
	 * @throws RemoteException
	 */
	public void clearTemp() throws RemoteException;

	/**
	 * @param code
	 *            当前代码
	 * @return 是否能够写入新代码
	 * @throws RemoteException
	 */
	boolean check(String code) throws RemoteException;

}
