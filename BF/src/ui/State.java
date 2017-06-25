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

private static String filename;

private static boolean fileCreated = false;

	private static File[] files;//获取该文件夹下所有的文件(夹)名
	
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
		this.files = new File(username).listFiles();
		
	}

	public static Language getLanguage()
	{
		return language;
	}

	public static void setLanguage(Language l)
	{
		State.language= l;
	}

	public static String getFilename()
	{
		return filename;
	}

	public static void setFilename(String filename)
	{
		State.filename = filename;
	}

	public static boolean isFileCreated()
	{
		return fileCreated;
	}

	public static void setFileCreated(boolean fileCreated)
	{
		State.fileCreated = fileCreated;
	}
}
