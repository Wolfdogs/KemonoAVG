package com.github.wolfdogs.kemono.avg;

public class Config
{
	private int screenWidth, screenHeight;
	private boolean isFullScreen;
	private boolean isVSyncEnabled;
	
	private String title;
	
	
	public Config()
	{
		
	}
	
	public int getScreenWidth()
	{
		return screenWidth;
	}
	
	public void setScreenWidth(int screenWidth)
	{
		this.screenWidth = screenWidth;
	}
	
	public int getScreenHeight()
	{
		return screenHeight;
	}
	
	public void setScreenHeight(int screenHeight)
	{
		this.screenHeight = screenHeight;
	}
	
	public boolean isFullScreen()
	{
		return isFullScreen;
	}
	
	public void setFullScreen(boolean isFullScreen)
	{
		this.isFullScreen = isFullScreen;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public boolean isVSyncEnabled()
	{
		return isVSyncEnabled;
	}
	
	public void setVSyncEnabled(boolean isVSyncEnabled)
	{
		this.isVSyncEnabled = isVSyncEnabled;
	}
}
