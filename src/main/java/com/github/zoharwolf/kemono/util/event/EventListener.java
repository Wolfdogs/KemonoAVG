/**
 * Copyright (C) 2011 MK124
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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author MK124
 *
 */

public abstract class EventListener implements IEventListener
{
	private static final Map< Class<?>, Map<Class<?>, Method> > CLASSES_EVENT_METHOD_MAP = new WeakHashMap<>();
	
	private static Map<Class<?>, Method> generateEventMethodMap( Class<?> cls )
	{
		Map<Class<?>, Method> eventMethodMap = new HashMap<>();
		Method[] methods = cls.getDeclaredMethods();
		
		for( Method method : methods )
		{
			int modifier = method.getModifiers();
			if( Modifier.isPrivate(modifier) || Modifier.isStatic(modifier) ) continue;
			
			Class<?>[] paramTypes = method.getParameterTypes();
			if( paramTypes.length != 1 ) continue;
			if( ! Event.class.isAssignableFrom( paramTypes[0] ) ) continue;
			
			eventMethodMap.put( paramTypes[0], method );
		}
		
		return eventMethodMap;
	}
	
	
	private Map<Class<?>, Method> eventMethodMap;
	
	
	protected EventListener( Class<?> cls )
	{
		if( ! cls.isInstance( this ) ) throw new IllegalArgumentException();
		
		eventMethodMap = CLASSES_EVENT_METHOD_MAP.get(cls);
		if( eventMethodMap == null )
		{
			eventMethodMap = generateEventMethodMap( cls );
			CLASSES_EVENT_METHOD_MAP.put( cls, eventMethodMap );
		}
	}
	
	@Override
	public void handleEvent( Event event )
	{
		Method method = eventMethodMap.get( event.getClass() );
		if( method == null ) return;

		try
		{
			method.invoke( this, event );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
}
