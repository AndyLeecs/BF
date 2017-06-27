package rmi;

import java.rmi.Remote;

import service.ExecuteService;
import service.IOService;
import service.TransformService;
import service.UserService;

public class RemoteHelper
{
	private static RemoteHelper remoteHelper = new RemoteHelper();

	public static RemoteHelper getInstance()
	{
		return remoteHelper;
	}

	private Remote remote;

	private RemoteHelper()
	{
	}

	public ExecuteService getExecuteService()
	{
		return (ExecuteService) remote;
	}

	public IOService getIOService()
	{
		return (IOService) remote;
	}

	public TransformService getTransformService()
	{
		return (TransformService) remote;
	}

	public UserService getUserService()
	{
		return (UserService) remote;
	}

	public void setRemote(Remote remote)
	{
		this.remote = remote;
	}
}
