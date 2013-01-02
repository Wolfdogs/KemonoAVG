package com.github.zoharwolf.kemono.util.event.events;

import com.github.zoharwolf.kemono.util.event.Event;

public class UselessEvent extends Event
{
	private boolean isProcessed = false;
	
	
	public UselessEvent()
	{
		
	}
	
	public void setProcessed(boolean isProcessed)
	{
		this.isProcessed = isProcessed;
	}
	
	public boolean isProcessed()
	{
		return isProcessed;
	}
}
