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

package com.github.wolfdogs.kemono.util.event;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Event manager that is managed.
 * Used for managing and recycling event handlers that has lifetime.
 * 
 * @author MK124
 */
public class ManagedEventManager extends AbstractManagedEventManager implements EventManager
{
	private Collection<HandlerEntry> handlerEntries;
	
	
	public ManagedEventManager(EventManager eventManager)
	{
		super(eventManager);
		handlerEntries = new ConcurrentLinkedQueue<>();
	}
	
	@Override
	protected HandlerEntry register(HandlerEntry originalEntry)
	{
		HandlerEntry entry = new HandlerEntryImpl(this, originalEntry);
		handlerEntries.add(entry);
		return entry;
	}
	
	@Override
	protected void remove(HandlerEntry entry)
	{
		handlerEntries.remove(entry);
	}
	
	@Override
	public void cancelAll()
	{
		for (HandlerEntry entry : handlerEntries) entry.cancel();
	}
}
