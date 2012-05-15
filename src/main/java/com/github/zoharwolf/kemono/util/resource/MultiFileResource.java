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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MultiFileResource implements FileResource
{
	private List<FileResource> fileResList;
	
	
	public MultiFileResource( List<FileResource> fileResList )
	{
		this.fileResList = fileResList;
	}
	
	@Override
	public String getPath()
	{
		String path = "";
		for ( FileResource f : fileResList )
		{
			if ( f.getPath() != null)
			{
				path = f.getPath();
				break;
			}
		}
		return path;
	}

	@Override
	public DirResource getParent()
	{
		List<DirResource> parentDirList = new ArrayList<DirResource>();
		for( FileResource f: fileResList )
		{
			DirResource oneParent = f.getParent();
			if ( oneParent!= null && !parentDirList.contains( oneParent ) )
			{
				parentDirList.add(oneParent);
			}
		}
		return new MultiDirResource(parentDirList);
	}

	@Override
	public int getSize()
	{
		int size = 0;
		for ( FileResource f : fileResList )
		{
			if ( f.getSize() > 0)
			{
				size = f.getSize();
				break;
			}
		}
		return size;
	}

	@Override
	public InputStream getInputStream()
	{
		InputStream is = null;
		for ( FileResource f : fileResList )
		{
			if ( f.getInputStream() != null )
			{
				is = f.getInputStream();
				break;
			}
		}
		return is;
	}

}
