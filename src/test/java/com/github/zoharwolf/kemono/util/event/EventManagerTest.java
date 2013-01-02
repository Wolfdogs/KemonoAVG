package com.github.zoharwolf.kemono.util.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.zoharwolf.kemono.util.event.EventManager.Priority;
import com.github.zoharwolf.kemono.util.event.event.EventListenerAddedEvent;
import com.github.zoharwolf.kemono.util.event.event.EventListenerEventListener;
import com.github.zoharwolf.kemono.util.event.event.EventListenerRemovedEvent;

public class EventManagerTest
{
	private EventManager eventManager;
	
	
	public EventManagerTest()
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
	public void testAddAndRemove()
	{
		final Map<Class<? extends Event>, EventHandler> addedListeners = new HashMap<>();
		final Map<Class<? extends Event>, EventHandler> removedListeners = new HashMap<>();
		
		
		final EventListenerEventListener testingListener = new EventListenerEventListener()
		{
		};
		
		final EventListenerEventListener listener = new EventListenerEventListener()
		{
			@Override
			public void onEvnetListenerAdded( EventListenerAddedEvent event )
			{
				EventHandler listener = event.getListener();
				Class<? extends Event> type = event.getType();
				
				if( listener != testingListener ) return;
			
				assertFalse( addedListeners.containsKey(type) );
				addedListeners.put( type, listener );
			}
			
			@Override
			public void onEvnetListenerRemoved( EventListenerRemovedEvent event )
			{
				EventHandler listener = event.getListener();
				Class<? extends Event> type = event.getType();
				
				if( listener != testingListener ) return;

				assertFalse( removedListeners.containsKey(type) );
				removedListeners.put( type, listener );
			}
		};

		eventManager.addListener( EventListenerAddedEvent.class, listener, Priority.HIGHEST );
		eventManager.addListener( EventListenerRemovedEvent.class, listener, Priority.HIGHEST );

		eventManager.addListener( EventListenerAddedEvent.class, testingListener, Priority.NORMAL );
		eventManager.addListener( EventListenerRemovedEvent.class, testingListener, Priority.NORMAL );

		assertTrue( eventManager.hasListener(EventListenerAddedEvent.class, testingListener) );
		assertTrue( eventManager.hasListener(EventListenerRemovedEvent.class, testingListener) );

		eventManager.removeListener( EventListenerAddedEvent.class, testingListener );
		eventManager.removeListener( EventListenerRemovedEvent.class, testingListener );

		assertFalse( eventManager.hasListener(EventListenerAddedEvent.class, testingListener) );
		assertFalse( eventManager.hasListener(EventListenerRemovedEvent.class, testingListener) );

		assertSame( addedListeners.get(EventListenerAddedEvent.class), testingListener );
		assertSame( addedListeners.get(EventListenerRemovedEvent.class), testingListener );
		assertSame( removedListeners.get(EventListenerAddedEvent.class), testingListener );
		assertSame( removedListeners.get(EventListenerRemovedEvent.class), testingListener );
	}

	@Test
	public void testRelatedClassListener()
	{
		class TestEvent extends Event
		{
			final Number number;
			
			TestEvent( Number number )
			{
				this.number = number;
			}
		}
		
		Number[] numbers =
			{
				new Byte( (byte) 1 ),
				new Short( (short) 2 ),
				new Integer( 3 ),
				new Long( 4 )
			};
		
		final long[] total = new long[1];
		
		EventHandler[] listeners = new EventHandler[ numbers.length ];
		for( int i=0; i<numbers.length; i++ )
		{
			final Number number = numbers[i];
			listeners[i] = new EventHandler()
			{
				@Override
				public void handleEvent( Event e )
				{
					assertSame( TestEvent.class, e.getClass() );
					
					TestEvent event = (TestEvent) e;
					assertSame( number, event.number );
					
					total[0] += event.number.longValue();
				}
			};
			
			eventManager.addListener( TestEvent.class, numbers[i].getClass(), listeners[i], Priority.NORMAL );
		}
		
		eventManager.addListener( TestEvent.class,
			new EventHandler()
			{
				@Override
				public void handleEvent( Event e )
				{
					assertSame( TestEvent.class, e.getClass() );
					
					TestEvent event = (TestEvent) e;
					total[0] += event.number.longValue();
				}
			}, Priority.NORMAL );

		
		long expectedTotal = 0;
		for( Number num : numbers )
		{
			expectedTotal += num.longValue();
			eventManager.dispatchEvent( new TestEvent(num), num );
		}
		
		assertTrue( total[0] == expectedTotal * 2 );
	}
}
