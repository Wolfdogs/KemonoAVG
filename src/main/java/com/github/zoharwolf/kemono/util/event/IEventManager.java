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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 提供管理侦听器、派发事件的管理器接口。
 * 
 * @author MK124
 */
public interface IEventManager
{
	/**
	 * 事件侦听器的条目类。 用于保存侦听器的详细信息。
	 */
	public static class Entry
	{
		private Class<? extends Event> type;
		private Object relatedObject;
		private IEventListener listener;
		private short priority;
		
		
		public Class<? extends Event> getType()
		{
			return type;
		}
		
		public Object getRelatedObject()
		{
			return relatedObject;
		}
		
		public IEventListener getListener()
		{
			return listener;
		}
		
		public short getPriority()
		{
			return priority;
		}
		
		public Entry(Class<? extends Event> type, Object relatedObject, IEventListener listener, short priority)
		{
			this.type = type;
			this.relatedObject = relatedObject;
			this.listener = listener;
			this.priority = priority;
		}
		
		@Override
		public String toString()
		{
			return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
		}
		
		public Class<?> getRelatedClass()
		{
			if (relatedObject instanceof Class) return (Class<?>) relatedObject;
			return null;
		}
	}
	
	/**
	 * 处理事件侦听器抛出异常的接口。
	 */
	public static interface ThrowableHandler
	{
		/**
		 * 事件侦听器异常的处理过程。
		 * 
		 * @param throwable 异常的实例
		 */
		void handleThrowable(Throwable throwable);
	}
	
	/**
	 * 事件侦听器的优先级。
	 */
	public static enum Priority
	{
		/**
		 * 最底层优先级
		 */
		BOTTOM((short) -32768),
		
		/**
		 * 最低优先级
		 */
		LOWEST((short) -16384),
		
		/**
		 * 低优先级
		 */
		LOW((short) -8192),
		
		/**
		 * 普通优先级
		 */
		NORMAL((short) 0),
		
		/**
		 * 高优先级
		 */
		HIGH((short) 8192),
		
		/**
		 * 最高优先级
		 */
		HIGHEST((short) 16384),
		
		/**
		 * 最上层优先级
		 */
		MONITOR((short) 32767);
		
		private final short value;
		
		
		private Priority(short value)
		{
			this.value = value;
		}
		
		/**
		 * 获取优先级的实际数据值。
		 * 
		 * @return 优先级的数据值
		 */
		public short getValue()
		{
			return value;
		}
	}
	
	
	/**
	 * 添加一个全局事件侦听器。
	 * 
	 * @param type 需要侦听的事件类型
	 * @param listener 事件侦听器对象实例
	 * @param priority 事件侦听器的优先级
	 * 
	 * @return 事件侦听器的条目对象
	 * 
	 * @see Priority
	 */
	Entry addListener(Class<? extends Event> type, IEventListener listener, Priority priority);
	
	/**
	 * 添加一个全局事件侦听器。
	 * 
	 * @param type 需要侦听的事件类型
	 * @param listener 事件侦听器对象实例
	 * @param priority 事件侦听器的自定义优先级
	 * 
	 * @return 事件侦听器的条目对象
	 */
	Entry addListener(Class<? extends Event> type, IEventListener listener, short priority);
	
	/**
	 * 添加一个兴趣对象类型约束的事件侦听器。
	 * <p>
	 * 只侦听指定兴趣对象类型所相关的事件。
	 * 
	 * @param type 需要侦听的事件类型
	 * @param clz 兴趣对象类型
	 * @param listener 事件侦听器对象实例
	 * @param priority 事件侦听器的优先级
	 * 
	 * @return 事件侦听器的条目对象
	 * 
	 * @see Priority
	 */
	Entry addListener(Class<? extends Event> type, Class<?> clz, IEventListener listener, Priority priority);
	
	/**
	 * 添加一个兴趣对象类型约束的事件侦听器。
	 * <p>
	 * 只侦听指定兴趣对象类型所相关的事件。
	 * 
	 * @param type 需要侦听的事件类型
	 * @param clz 兴趣对象类型
	 * @param listener 事件侦听器对象实例
	 * @param priority 事件侦听器的自定义优先级
	 * 
	 * @return 事件侦听器的条目对象
	 */
	Entry addListener(Class<? extends Event> type, Class<?> clz, IEventListener listener, short priority);
	
	/**
	 * 添加一个兴趣对象实例约束的事件侦听器。
	 * <p>
	 * 只侦听指定兴趣对象实例所相关的事件。
	 * 
	 * @param type 需要侦听的事件类型
	 * @param object 兴趣对象实例
	 * @param listener 事件侦听器对象实例
	 * @param priority 事件侦听器的优先级
	 * 
	 * @return 事件侦听器的条目对象
	 * 
	 * @see Priority
	 */
	Entry addListener(Class<? extends Event> type, Object object, IEventListener listener, Priority priority);
	
	/**
	 * 添加一个兴趣对象实例约束的事件侦听器。
	 * <p>
	 * 只侦听指定兴趣对象实例所相关的事件。
	 * 
	 * @param type 需要侦听的事件类型
	 * @param object 兴趣对象实例
	 * @param listener 事件侦听器对象实例
	 * @param priority 事件侦听器的自定义优先级
	 * 
	 * @return 事件侦听器的条目对象
	 */
	Entry addListener(Class<? extends Event> type, Object object, IEventListener listener, short priority);
	
	/**
	 * 移除一个全局事件侦听器。
	 * 
	 * @param type 所侦听的事件类型
	 * @param listener 事件侦听器对象实例
	 */
	void removeListener(Class<? extends Event> type, IEventListener listener);
	
	/**
	 * 移除一个兴趣对象类型约束的事件侦听器。
	 * 
	 * @param type 所侦听的事件类型
	 * @param clz 兴趣对象类型
	 * @param listener 事件侦听器对象实例
	 */
	void removeListener(Class<? extends Event> type, Class<?> clz, IEventListener listener);
	
	/**
	 * 移除一个兴趣对象实例约束的事件侦听器。
	 * 
	 * @param type 所侦听的事件类型
	 * @param clz 兴趣对象实例
	 * @param listener 事件侦听器对象实例
	 */
	void removeListener(Class<? extends Event> type, Object object, IEventListener listener);
	
	/**
	 * 移除一个指定的事件侦听器条目。
	 * 
	 * @param entry 事件侦听器的条目对象
	 */
	void removeListener(Entry entry);
	
	/**
	 * 检查是否有指定侦听器实例的事件侦听器。
	 * 
	 * @param type 需要检查的事件类型
	 * @param listener 需要检查的侦听器实例
	 * 
	 * @return 当存在指定条件的侦听器的时候，返回 {@code true}
	 */
	boolean hasListener(Class<? extends Event> type, IEventListener listener);
	
	/**
	 * 检查是否有指定对象类型约束的事件侦听器。
	 * 
	 * @param type 需要检查的事件类型
	 * @param clz 兴趣对象类型
	 * 
	 * @return 当存在指定条件的侦听器的时候，返回 {@code true}
	 */
	boolean hasListener(Class<? extends Event> type, Class<?> clz);
	
	/**
	 * 检查是否有指定对象类型约束的事件侦听器。
	 * 
	 * @param type 需要检查的事件类型
	 * @param clz 兴趣对象类型
	 * @param listener 需要检查的侦听器实例
	 * 
	 * @return 当存在指定条件的侦听器的时候，返回 {@code true}
	 */
	boolean hasListener(Class<? extends Event> type, Class<?> clz, IEventListener listener);
	
	/**
	 * 检查是否有指定对象实例约束的事件侦听器。
	 * 
	 * @param type 需要检查的事件类型
	 * @param object 兴趣对象实例
	 * 
	 * @return 当存在指定条件的侦听器的时候，返回 {@code true}
	 */
	boolean hasListener(Class<? extends Event> type, Object object);
	
	/**
	 * 检查是否有指定对象实例约束的事件侦听器。
	 * 
	 * @param type 需要检查的事件类型
	 * @param object 兴趣对象实例
	 * @param listener 需要检查的侦听器实例
	 * 
	 * @return 当存在指定条件的侦听器的时候，返回 {@code true}
	 */
	boolean hasListener(Class<? extends Event> type, Object object, IEventListener listener);
	
	/**
	 * 检查事件侦听器条目是否存在。
	 * 
	 * @param entry 事件侦听器条目
	 * @return 当存在指定条件的侦听器条目的时候，返回 {@code true}
	 */
	boolean hasListener(Entry entry);
	
	/**
	 * 按照各侦听器的优先级，依次派发事件。
	 * <p>
	 * 如果事件允许，派发有可能会被中断。
	 * 
	 * @param event 所要派发的事件实例
	 * @param objects 事件所相关的对象
	 */
	<T extends Event> void dispatchEvent(T event, Object... objects);
	
	/**
	 * 按照各侦听器的优先级，依次派发事件。
	 * <p>
	 * 如果事件允许，派发有可能会被中断。
	 * 
	 * @param handler 侦听器异常的处理实例，为 {@code null} 时直接将异常打印到控制台上
	 * @param event 所要派发的事件实例
	 * @param objects 事件所相关的对象
	 */
	<T extends Event> void dispatchEvent(ThrowableHandler handler, T event, Object... objects);
}
