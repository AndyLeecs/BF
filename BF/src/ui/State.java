package ui;

import java.io.File;

import service.Language;

/**  
* 存储现在程序的情况
*  
* @author Andy
* @version  
*/

public class State
{
	private static String username;

private static Language language = Language.bf;

	private static File[] files = new File(username).listFiles();//获取该文件夹下所有的文件(夹)名
	
	public static String getUsername()
	{
		return username;
	}

	public static void setUsername(String username)
	{
		State.username = username;
	}

	public static File[] getFiles()
	{
		return files;
	}

	public void setFiles(File[] files)
	{
		this.files = files;
	}

	public static Language getLanguage()
	{
		return language;
	}

	public static void setLanguage(Language l)
	{
		State.language= l;
	}
}
