package com.github.wolfdogs.kemono.util.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.wolfdogs.kemono.util.event.EventManager;
import com.github.wolfdogs.kemono.util.event.RootEventManager;
import com.github.wolfdogs.kemono.util.event.WeakManagedEventManager;
import com.github.wolfdogs.kemono.util.event.EventManager.HandlerEntry;
import com.github.wolfdogs.kemono.util.event.EventManager.HandlerPriority;
import com.github.wolfdogs.kemono.util.event.events.InterruptableEvent;
import com.github.wolfdogs.kemono.util.event.events.UselessEvent;
import com.github.wolfdogs.kemono.util.event.events.UselessEventHandler;

public class WeakManagedEventManagerTest
{
	private EventManager rootEventManager;
	private WeakManagedEventManager eventManager;
	
	
	public WeakManagedEventManagerTest()
	{

	}

	@Before
	public void setUp() throws Exception
	{
		rootEventManager = new RootEventManager();
		eventManager = new WeakManagedEventManager(rootEventManager);
	}

	@After
	public void tearDown() throws Exception
	{
		rootEventManager = null;
		eventManager = null;
	}
	
	@Test
	public void testRegisterHandlerAndDispatchEvent()
	{
		UselessEventHandler handler = new UselessEventHandler()
		{
			@Override
			public void onUselessEvent(UselessEvent event)
			{
				event.setProcessed(true);
			}
		};
		
		eventManager.registerHandler(UselessEvent.class, handler, HandlerPriority.NORMAL);
		
		UselessEvent event = new UselessEvent();
		rootEventManager.dispatchEvent(event);
		
		assertTrue(event.isProcessed());
	}

	@Test
	public void testRegisterHandlerAndDispatchEvent2()
	{
		final TapableCounter counter = new TapableCounter();
		
		UselessEventHandler handler = new UselessEventHandler()
		{
			@Override
			public void onInterruptableEvent(InterruptableEvent event)
			{
				counter.tap();
			}
		};
		
		eventManager.registerHandler(InterruptableEvent.class, this, handler, HandlerPriority.NORMAL);
		
		InterruptableEvent event = new InterruptableEvent();
		rootEventManager.dispatchEvent(event, this);
		rootEventManager.dispatchEvent(event, new Object());
		rootEventManager.dispatchEvent(event);
		
		assertEquals(1, counter.getTaps());
	}

	@Test
	public void testRegisterHandlerAndDispatchEvent3()
	{
		final TapableCounter counter = new TapableCounter();
		
		UselessEventHandler handler = new UselessEventHandler()
		{
			@Override
			public void onInterruptableEvent(InterruptableEvent event)
			{
				counter.tap();
			}
		};
		
		eventManager.registerHandler(InterruptableEvent.class, getClass(), handler, HandlerPriority.NORMAL);
		
		InterruptableEvent event = new InterruptableEvent();
		rootEventManager.dispatchEvent(event, this);
		rootEventManager.dispatchEvent(event, new Object());
		rootEventManager.dispatchEvent(event);
		
		assertEquals(1, counter.getTaps());
	}
	
	@Test
	public void testRegisterHandlerAndDispatchEvent4()
	{
		final TapableCounter counter = new TapableCounter();
		
		UselessEventHandler handler = new UselessEventHandler()
		{
			@Override
			public void onInterruptableEvent(InterruptableEvent event)
			{
				counter.tap();
			}
		};
		
		Cloneable cloneable = new Cloneable()
		{
		};
		
		eventManager.registerHandler(InterruptableEvent.class, Cloneable.class, handler, HandlerPriority.NORMAL);
		
		InterruptableEvent event = new InterruptableEvent();
		rootEventManager.dispatchEvent(event, cloneable);
		rootEventManager.dispatchEvent(event, new Object());
		rootEventManager.dispatchEvent(event);
		
		assertEquals(1, counter.getTaps());
	}
	
	@Test
	public void testCancelHandler()
	{
		UselessEventHandler handler = new UselessEventHandler()
		{
			@Override
			public void onUselessEvent(UselessEvent event)
			{
				event.setProcessed(true);
			}
		};
		
		HandlerEntry entry = eventManager.registerHandler(UselessEvent.class, handler, HandlerPriority.NORMAL);
		entry.cancel();
		
		UselessEvent event = new UselessEvent();
		rootEventManager.dispatchEvent(event);
		
		assertFalse(event.isProcessed());
	}
	
	@Test
	public void testCancelAllHandler()
	{
		UselessEventHandler handler = new UselessEventHandler()
		{
			@Override
			public void onUselessEvent(UselessEvent event)
			{
				event.setProcessed(true);
			}
		};
		
		eventManager.registerHandler(UselessEvent.class, handler, HandlerPriority.NORMAL);
		eventManager.cancelAll();
		
		UselessEvent event = new UselessEvent();
		rootEventManager.dispatchEvent(event);
		
		assertFalse(event.isProcessed());
	}
	
	@Test
	public void testGcCancelAllHandler() throws InterruptedException
	{
		UselessEventHandler handler = new UselessEventHandler()
		{
			@Override
			public void onUselessEvent(UselessEvent event)
			{
				event.setProcessed(true);
			}
		};
		
		final Object waitObject = new Object();
		WeakManagedEventManager eventManager = new WeakManagedEventManager(rootEventManager)
		{
			@Override
			protected void finalize() throws Throwable
			{
				super.finalize();
				synchronized (waitObject)
				{
					waitObject.notify();
				}
			}
		};
		
		eventManager.registerHandler(UselessEvent.class, handler, HandlerPriority.NORMAL);
		eventManager = null;
		
		System.gc();
		
		synchronized (waitObject)
		{
			waitObject.wait();
		}
		
		UselessEvent event = new UselessEvent();
		rootEventManager.dispatchEvent(event);
		
		assertFalse(event.isProcessed());
	}
}
