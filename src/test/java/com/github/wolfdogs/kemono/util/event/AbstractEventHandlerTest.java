package com.github.wolfdogs.kemono.util.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.wolfdogs.kemono.util.event.AbstractEventHandler;
import com.github.wolfdogs.kemono.util.event.Event;
import com.github.wolfdogs.kemono.util.event.EventManager;
import com.github.wolfdogs.kemono.util.event.RootEventManager;
import com.github.wolfdogs.kemono.util.event.EventManager.HandlerPriority;
import com.github.wolfdogs.kemono.util.event.events.UselessEvent;

public class AbstractEventHandlerTest
{
	private EventManager eventManager;


	public AbstractEventHandlerTest()
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
	public void testIllgalConstruct1()
	{
		class Handler extends AbstractEventHandler
		{
			protected Handler()
			{
				super(AbstractEventHandler.class);
			}
		}
		
		TapableCounter counter = new TapableCounter();
		
		try
		{
			new Handler();
		}
		catch (IllegalArgumentException e)
		{
			counter.tap();
		}
		
		assertEquals(1, counter.getTaps());
	}
	
	@Test
	public void testIllgalConstruct2()
	{
		class Handler extends AbstractEventHandler
		{
			protected Handler()
			{
				super(Object.class);
			}
		}
		
		TapableCounter counter = new TapableCounter();
		
		try
		{
			new Handler();
		}
		catch (IllegalArgumentException e)
		{
			counter.tap();
		}
		
		assertEquals(1, counter.getTaps());
	}

	@Test
	public void testPrivateHandlerMethod()
	{
		class Handler extends AbstractEventHandler
		{
			protected Handler()
			{
				super(Handler.class);
			}

			@SuppressWarnings("unused")
			private void onUselessEvent(UselessEvent event)
			{
				event.setProcessed(true);
			}
		};
		
		AbstractEventHandler handler = new Handler();
		
		eventManager.registerHandler(UselessEvent.class, handler, HandlerPriority.NORMAL);
		
		UselessEvent event = new UselessEvent();
		eventManager.dispatchEvent(event);
		
		assertFalse(event.isProcessed());
	}
	
	@Test
	public void testIllegalParamHandlerMethod()
	{
		class Handler extends AbstractEventHandler
		{
			protected Handler()
			{
				super(Handler.class);
			}

			@SuppressWarnings("unused")
			public void onUselessEvent(UselessEvent event, Object object)
			{
				event.setProcessed(true);
			}
		};
		
		AbstractEventHandler handler = new Handler();
		
		eventManager.registerHandler(UselessEvent.class, handler, HandlerPriority.NORMAL);
		
		UselessEvent event = new UselessEvent();
		eventManager.dispatchEvent(event);
		
		assertFalse(event.isProcessed());
	}

	@Test
	public void testIllegalParamHandlerMethod2()
	{
		class Handler extends AbstractEventHandler
		{
			protected Handler()
			{
				super(Handler.class);
			}

			@SuppressWarnings("unused")
			public void onUselessEvent(Event e)
			{
				UselessEvent event;
				
				if( e instanceof UselessEvent )
				{
					event = (UselessEvent) e;
					event.setProcessed(true);
				}
			}
		};
		
		AbstractEventHandler handler = new Handler();
		
		eventManager.registerHandler(UselessEvent.class, handler, HandlerPriority.NORMAL);
		
		UselessEvent event = new UselessEvent();
		eventManager.dispatchEvent(event);
		
		assertFalse(event.isProcessed());
	}

	@Test
	public void testIllegalParamHandlerMethod3()
	{
		class Handler extends AbstractEventHandler
		{
			protected Handler()
			{
				super(Handler.class);
			}

			@SuppressWarnings("unused")
			public void onUselessEvent(Object e)
			{
				UselessEvent event;
				
				if( e instanceof UselessEvent )
				{
					event = (UselessEvent) e;
					event.setProcessed(true);
				}
			}
		};
		
		AbstractEventHandler handler = new Handler();
		
		eventManager.registerHandler(UselessEvent.class, handler, HandlerPriority.NORMAL);
		
		UselessEvent event = new UselessEvent();
		eventManager.dispatchEvent(event);
		
		assertFalse(event.isProcessed());
	}
	
	@Test
	public void testIllegalClassException()
	{
		boolean throwed = false;
		
		try
		{
			new AbstractEventHandler(Object.class)
			{
			};
		}
		catch (IllegalArgumentException e)
		{
			throwed = true;
		}
		
		assertTrue(throwed);
	}
}
