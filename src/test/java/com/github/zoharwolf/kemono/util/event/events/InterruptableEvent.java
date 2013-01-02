package com.github.zoharwolf.kemono.util.event.events;

import com.github.zoharwolf.kemono.util.event.Event;
import com.github.zoharwolf.kemono.util.event.Interruptable;

public class InterruptableEvent extends Event implements Interruptable
{
	public InterruptableEvent()
	{
		super();
	}
	
	@Override
	public void interrupt()
	{
		super.interrupt();
	}
}
