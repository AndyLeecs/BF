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

	/**
	 * 当前版本号
	 */
	private static int version = 1;

	/**
	 * 最后存入的版本号
	 */
	private static int latestVersion = 0;

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

	public static int getVersion()
	{
		return version;
	}

	public static void setVersion(int version)
	{
		State.version = version;
	}

	/**
	 * 设置redo之后的版本号
	 */
	public static void setVersionAfterRedo()
	{
		State.version++;
	}

	/**
	 * 设置undo之后的版本号
	 */
	public static void setVersionAfterUndo()
	{
		State.version--;
	}

	public static int getLatestVersion()
	{
		return latestVersion;
	}

	/**
	 * 根据当前版本号更新最后存入的版本号
	 */
	public static void setLatestVersion()
	{
		if (State.version > State.latestVersion)
			State.latestVersion = State.version;
	}

	/**
	 * @param i
	 *            手动设定的最后存入的版本号
	 */
	public static void setLatestVersion(int i)
	{
		State.latestVersion = i;
	}
}
