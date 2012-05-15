/**
 * Copyright (C) 2012 ZOHAR
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

package com.github.zoharwolf.kemono.util.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MultiDirResource extends AbstractDirResource
{
	private List<DirResource> dirResList;

	
	public MultiDirResource( List<DirResource> dirResList )
	{
	    this.dirResList = dirResList;
	}

	@Override
	public DirResource getParent()
	{
	    List<DirResource> parentDirList = new ArrayList<DirResource>();
	    for( DirResource oneDir: dirResList )
	    {
	        DirResource oneParent = oneDir.getParent();
	        if ( oneParent!= null && !parentDirList.contains( oneParent ) )
	        {
	            parentDirList.add(oneParent);
	        }
	    }
	    return new MultiDirResource(parentDirList);
	}

	@Override
	public Resource get( String path )
	{
	    List<FileResource> childFileList = new ArrayList<FileResource>();
	    List<DirResource> childDirList = new ArrayList<DirResource>();
        for( DirResource oneDir: dirResList )
        {
            Resource oneChild = oneDir.get( path );
            if ( oneChild instanceof FileResource && oneChild != null && !childFileList.contains(oneChild) )
            {
                childFileList.add((FileResource)oneChild);
            }
            if ( oneChild instanceof DirResource && oneChild != null && !childDirList.contains(oneChild) )
            {
                childDirList.add((DirResource)oneChild);
            }
        }
        if ( !childDirList.isEmpty() )
            return new MultiDirResource(childDirList);
        else if ( !childFileList.isEmpty() )
            return new MultiFileResource(childFileList);
        else
            return null;
	}

	@Override
	public Collection<Resource> list()
	{
		List<Resource> resList = new ArrayList<>();
		for( DirResource dr : dirResList )
		{
		    if ( !dr.list().isEmpty())
		    {
		        resList.addAll( dr.list() );
		    }
		}
		return resList;
	}

	@Override
	public String getPath()
	{
	    String path = "";
	    for ( DirResource dr : dirResList )
	    {
	        if ( dr.getPath() != null)
	        {
	            path = dr.getPath();
	            break;
	        }
	    }
		return path;
	}

	@Override
	public String toString()
	{
	    StringBuffer sb = new StringBuffer();
	    sb.append("Dir List:\n");
	    for( DirResource dr : dirResList )
	    {
	        if (dr != null)
	        {
	            sb.append(dr);
	            sb.append('\n');
	        }
	    }
		return sb.toString();
	}
}
