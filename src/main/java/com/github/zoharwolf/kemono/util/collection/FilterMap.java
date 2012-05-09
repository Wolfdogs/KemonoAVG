package com.github.zoharwolf.kemono.util.collection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class FilterMap<K, V> implements Map<K, V>
{
	public static <V> Map<String, V> lowercasedKeyMap( Map<String, V> m )
	{
		return new FilterMap<>( m,
			new Filter<String>()
			{
				@Override
				public String filter( Object object )
				{
					return object.toString().toLowerCase();
				}
			} );
	}

	public static <V> Map<String, V> uppercasedKeyMap( Map<String, V> m )
	{
		return new FilterMap<>( m,
			new Filter<String>()
			{
				@Override
				public String filter( Object object )
				{
					return object.toString().toUpperCase();
				}
			} );
	}
	
	
	public static interface Filter<K>
	{
		K filter( Object object );
	}
	
	
	private final Map<K, V> map;
	private final Filter<K> filter;


	public FilterMap( Map<K, V> m, Filter<K> filter )
	{
		if( m == null ) throw new NullPointerException();
		
		this.map = m;
		this.filter = filter;
	}

	public int size()
	{
		return map.size();
	}

	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	public boolean containsKey( Object key )
	{
		return map.containsKey( filter.filter(key) );
	}

	public boolean containsValue( Object value )
	{
		return map.containsValue( filter.filter(value) );
	}

	public V get( Object key )
	{
		return map.get( filter.filter(key) );
	}

	public V put( K key, V value )
	{
		return map.put( filter.filter(key), value );
	}

	public V remove( Object key )
	{
		return map.remove( filter.filter(key) );
	}

	public void putAll( Map<? extends K, ? extends V> map )
	{
		this.map.putAll(map);
	}

	public void clear()
	{
		map.clear();
	}

	public Set<K> keySet()
	{
		return map.keySet();

	}

	public Set<Map.Entry<K, V>> entrySet()
	{
		return map.entrySet();
	}

	public Collection<V> values()
	{
		return map.values();
	}

	public boolean equals( Object o )
	{
		return map.equals(o);
	}

	public int hashCode()
	{
		return map.hashCode();
	}

	public String toString()
	{
		return map.toString();
	}

	private void writeObject( ObjectOutputStream s ) throws IOException
	{
		s.defaultWriteObject();
	}
}
