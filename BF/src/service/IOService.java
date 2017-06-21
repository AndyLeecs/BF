//服务器IOService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface IOService extends Remote{
	public String[] writeFile(String file, String userId, String fileName, Language l)throws RemoteException;
	
	public String readFile(String userId, String fileName, Language l)throws RemoteException;
	
	public ArrayList<String> readFileList(String userId, Language l)throws RemoteException;
}
