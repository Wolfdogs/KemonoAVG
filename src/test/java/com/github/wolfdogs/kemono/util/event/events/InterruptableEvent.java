package com.github.wolfdogs.kemono.util.event.events;

import com.github.wolfdogs.kemono.util.event.Event;
import com.github.wolfdogs.kemono.util.event.Interruptable;

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
