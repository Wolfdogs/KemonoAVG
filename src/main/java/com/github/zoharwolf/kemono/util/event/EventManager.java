/**
 * Copyright (C) 2011-2012 MK124
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.zoharwolf.kemono.util.event;

/**
 * Provide interface for managing event handlers and dispatching events.
 * 
 * @author MK124
 */
public interface EventManager
{
	/**
	 * Interface of EventHandler entry that is registered. 
	 * Use for getting informations of handler and canceling event.
	 */
	public interface HandlerEntry
	{
		/**
		 * Get the related instance of EventManager.
		 * 
		 * @return Instance of EventManager.
		 */
		public EventManager getEventManager();
		
		/**
		 * Cancel this event handling process.
		 */
		public void cancel();
		
		/**
		 * Get the event type that this entry registered.
		 * 
		 * @return Event
		 */
		public Class<? extends Event> getType();
		
		/**
		 * Get the instance of related Object that this entry registered.
		 * 
		 * @return Related Object
		 */
		public Object getRelatedObject();
		
		/**
		 * Get the instance of related Class that this entry registered.
		 * 
		 * @return Related Class
		 */
		public Class<?> getRelatedClass();
		
		/**
		 * Get the instance of EventHandler.
		 * 
		 * @return Instance of EventHandler
		 */
		public EventHandler getHandler();
		
		/**
		 * Get the priority of EventHandler.
		 * 
		 * @return Priority
		 */
		public short getPriority();
	}
	
	/**
	 * Interface of exception handler that thrown by event handler.
	 */
	public static interface ThrowableHandler
	{
		/**
		 * Handle the exception.
		 * 
		 * @param throwable Exception thrown by event handler.
		 */
		void handleThrowable(Throwable throwable);
	}
	
	/**
	 * Priority of event handler.
	 */
	public enum HandlerPriority
	{
		/**
		 * The bottom priority. Handlers of this level will process events at the very last.
		 * Use {@code LOWEST} if not necessary.
		 * Don't use if you don't know what you are doing.
		 */
		BOTTOM((short) -32768),
		
		/**
		 * The lowest priority.
		 */
		LOWEST((short) -16384),
		
		/**
		 * The low priority.
		 */
		LOW((short) -8192),
		
		/**
		 * The normal priority.
		 */
		NORMAL((short) 0),
		
		/**
		 * The high priority.
		 */
		HIGH((short) 8192),
		
		/**
		 * The highest priority.
		 */
		HIGHEST((short) 16384),
		
		/**
		 * The monitor priority. Handlers of this level will process events first.
		 * Use {@code HIGHEST} if not necessary.
		 * Don't use if you don't know what you are doing.
		 */
		MONITOR((short) 32767);
		
		private final short value;
		
		
		private HandlerPriority(short value)
		{
			this.value = value;
		}
		
		/**
		 * Get the actual value of priority.
		 * 
		 * @return Value of priority
		 */
		public short getValue()
		{
			return value;
		}
	}
	
	/**
	 * Register a global event handler.
	 * 
	 * @param type Related event type
	 * @param handler Instance of event handler
	 * @param priority Priority of event handler
	 *  
	 * @return Entry of event handler
	 *  
	 * @see HandlerPriority
	 */
	HandlerEntry registerHandler(Class<? extends Event> type, EventHandler handler, HandlerPriority priority);
	
	/**
	 * Register a global event handler.
	 * 
	 * @param type Related event type
	 * @param handler Instance of event handler
	 * @param priority Custom priority of event handler
	 *  
	 * @return Entry of event handler
	 *  
	 * @see HandlerPriority
	 */
	HandlerEntry registerHandler(Class<? extends Event> type, EventHandler handler, short priority);
	
	/**
	 * Register a event handler that related with a class.<p>
	 * Only monitor the event that related with this class.
	 * 
	 * @param type Related event type
	 * @param clz Related class
	 * @param handler Instance of event handler
	 * @param priority Priority of event handler
	 *  
	 * @return Entry of event handler
	 *  
	 * @see HandlerPriority
	 */
	HandlerEntry registerHandler(Class<? extends Event> type, Class<?> clz, EventHandler handler, HandlerPriority priority);
	
	/**
	 * Register a event handler that related with a class.<p>
	 * Only monitor the event that related with this class.
	 * 
	 * @param type Related event type
	 * @param clz Related class
	 * @param handler Instance of event handler
	 * @param priority Custom priority of event handler
	 *  
	 * @return Entry of event handler
	 *  
	 * @see HandlerPriority
	 */
	HandlerEntry registerHandler(Class<? extends Event> type, Class<?> clz, EventHandler handler, short priority);
	
	/**
	 * Register a event handler that related with an instance.<p>
	 * Only monitor the event that related with this instance.
	 * 
	 * @param type Related event type
	 * @param object Related instance
	 * @param handler Instance of event handler
	 * @param priority Priority of event handler
	 *  
	 * @return Entry of event handler
	 *  
	 * @see HandlerPriority
	 */
	HandlerEntry registerHandler(Class<? extends Event> type, Object object, EventHandler handler, HandlerPriority priority);
	
	/**
	 * Register a event handler that related with an instance.<p>
	 * Only monitor the event that related with this instance.
	 * 
	 * @param type Related event type
	 * @param object Related instance
	 * @param handler Instance of event handler
	 * @param priority Custom priority of event handler
	 *  
	 * @return Entry of event handler
	 *  
	 * @see HandlerPriority
	 */
	HandlerEntry registerHandler(Class<? extends Event> type, Object object, EventHandler handler, short priority);
	
	/**
	 * Dispatch events according to handler's priority.
	 * It might be interrupted if event allowed.
	 * If the handler throw exception, it will be print out and keep dispatching.
	 * 
	 * @param event Instance of event to be dispatch
	 * @param objects Related objects
	 */
	<T extends Event> void dispatchEvent(T event, Object... objects);
	
	/**
	 * Dispatch events according to handler's priority.
	 * It might be interrupted if event allowed.
	 * 
	 * @param handler Instance of exception handler. Print out the exception directly if it's {@code null}.
	 * @param event Instance of event to be dispatch
	 * @param objects Related objects
	 */
	<T extends Event> void dispatchEvent(ThrowableHandler handler, T event, Object... objects);
}
