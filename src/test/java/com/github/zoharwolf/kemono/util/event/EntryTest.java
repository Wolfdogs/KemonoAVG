package com.github.zoharwolf.kemono.util.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.zoharwolf.kemono.util.event.EventManager.HandlerEntry;
import com.github.zoharwolf.kemono.util.event.EventManager.HandlerPriority;
import com.github.zoharwolf.kemono.util.event.events.UselessEvent;
import com.github.zoharwolf.kemono.util.event.events.UselessEventHandler;

public class EntryTest
{
	private EventManager eventManager;


	public EntryTest()
	{

	}

	@Before
	public void setUp() throws Exception
	{
		eventManager = new RootEventManager();
	}

	@After
	public void tearDown() throws Exception
	{
		eventManager = null;
	}
	
	@Test
	public void testEntryProperties()
	{
		UselessEventHandler handler = new UselessEventHandler()
		{
		};
		
		HandlerEntry entry = eventManager.registerHandler(UselessEvent.class, EntryTest.class, handler, HandlerPriority.LOWEST);

		assertSame(UselessEvent.class, entry.getType());
		assertSame(EntryTest.class, entry.getRelatedObject());
		assertSame(EntryTest.class, entry.getRelatedClass());
		assertSame(handler, entry.getHandler());
		assertEquals(HandlerPriority.LOWEST.getValue(), entry.getPriority());
	}
}
