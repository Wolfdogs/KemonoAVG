
package com.github.zoharwolf.kemono.util.resource;

/**
 * 工厂类，制造IResourceManager的实例。
 * 
 * @author zohar
 * 
 */

public abstract class ResourceManager
{
	public static DirResource getResource( String resName )
	{
		return new MultiDirResource( resName );
	}
}
