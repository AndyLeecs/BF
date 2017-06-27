package ui;

import java.io.File;

import service.Language;

/**
 * 存储现在程序的情况
 * 
 * @author qwe
 */

public class State
{
	/**
	 * 用户名
	 */
	private static String username;

	/**
	 * 语言类型
	 */
	private static Language language = Language.bf;

	/**
	 * 文件名
	 */
	private static String filename;

	/**
	 * 是否成功创建新文件
	 */
	private static boolean fileCreated = false;

	/**
	 * 该文件夹下所有的文件(夹)名
	 */
	private static File[] files;

	public static String getFilename()
	{
		return filename;
	}

	public static File[] getFiles()
	{
		return files;
	}

	public static Language getLanguage()
	{
		return language;
	}

	public static String getUsername()
	{
		return username;
	}

	public static boolean isFileCreated()
	{
		return fileCreated;
	}

	public static void setFileCreated(boolean fileCreated)
	{
		State.fileCreated = fileCreated;
	}

	public static void setFilename(String filename)
	{
		State.filename = filename;
	}

	public static void setLanguage(Language l)
	{
		State.language = l;
	}

	public static void setUsername(String username)
	{
		State.username = username;
	}

	public void setFiles(File[] files)
	{
		State.files = new File(username).listFiles();

	}
}
