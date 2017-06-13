package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**  
* 类说明   
*  
* @author Andy
* @version  
*/

public interface TransformService extends Remote
{
	public String transform(String code, String param, Language l) throws RemoteException;
}
