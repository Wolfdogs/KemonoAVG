/**
 * Copyright (C) 2012 MK124
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

package com.github.zoharwolf.kemono.util.resource.zip;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.lang3.StringUtils;

import com.github.zoharwolf.kemono.util.collection.FilteredMap;
import com.github.zoharwolf.kemono.util.resource.AbstractDirResource;
import com.github.zoharwolf.kemono.util.resource.DirResource;
import com.github.zoharwolf.kemono.util.resource.Resource;

/**
 * 读取源为Zip文件包的DirResource的实现。
 * 
 * @author MK124
 */

public class ZipDirResource extends AbstractDirResource implements DirResource
{
	private DirResource parent;
	
	private ZipFile zipFile;
	private ZipArchiveEntry entry;
	
	private Map<String, Resource> childs;
	
	
	public ZipDirResource( File file ) throws IOException
	{
		this.zipFile = new ZipFile( file );
		
		initialize();

		Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
		while( entries.hasMoreElements() )
		{
			ZipArchiveEntry zipArchiveEntry = entries.nextElement();
			String entryPath = zipArchiveEntry.getName();
			
			createResource( entryPath, zipArchiveEntry );
		}
	}
	
	private ZipDirResource( ZipDirResource parent, ZipFile zipFile )
	{
		this.parent = parent;
		this.zipFile = zipFile;
		
		initialize();
	}
	
	private void initialize()
	{
		childs = FilteredMap.lowercasedKeyMap( new HashMap<String, Resource>() );
	}
	
	private void setEntry( ZipArchiveEntry entry )
	{
		this.entry = entry;
	}
	
	private void createResource( String path, ZipArchiveEntry entry )
	{
		String[] childs = StringUtils.split( path, "/\\" );
		
		ZipDirResource res = this;
		for( int i=0; i < childs.length-1; i++ )
		{
			String name = childs[i];
			
			ZipDirResource child = (ZipDirResource) res.getDir(name);
			if( child == null ) child = new ZipDirResource(res, zipFile);
			
			res.addResource( name, child );
			res = child;
		}
		
		if( entry.isDirectory() )
		{
			ZipDirResource child = new ZipDirResource(res, zipFile);
			child.setEntry( entry );
			res.addResource( childs[childs.length-1], child );
		}
		else
		{
			ZipFileResource child = new ZipFileResource(res, zipFile, entry);
			res.addResource( childs[childs.length-1], child );
		}
	}
	
	private void addResource( String name, Resource resource )
	{
		childs.put( name.toLowerCase(), resource );
	}
	
	private Resource getChild( String path )
	{
		return childs.get( path.toLowerCase() );
	}

	@Override
	public String getPath()
	{
		return entry.getName();
	}

	@Override
	public DirResource getParent()
	{
		return parent;
	}
	
	@Override
	public Resource get( String path )
	{
		String[] childs = StringUtils.split( path, "/\\", 2 );
		if( childs.length == 0 ) return this;
		
		Resource res = getChild( childs[0] );
		
		if( childs.length == 1 ) return res;
		if( childs.length == 2 && res instanceof DirResource )
		{
			DirResource dirRes = (DirResource) res;
			return dirRes.get( childs[1] );
		}
		
		return null;
	}

	@Override
	public Collection<Resource> list()
	{
		return childs.values();
	}
}
