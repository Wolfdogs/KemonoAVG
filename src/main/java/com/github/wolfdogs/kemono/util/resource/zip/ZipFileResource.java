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

package com.github.wolfdogs.kemono.util.resource.zip;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipException;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import com.github.wolfdogs.kemono.util.resource.DirResource;
import com.github.wolfdogs.kemono.util.resource.FileResource;

/**
 * 读取源为Zip文件包的FileResource的实现。
 * 
 * @author MK124
 */
public class ZipFileResource implements FileResource
{
	private DirResource parent;
	private ZipFile zipFile;
	private ZipArchiveEntry entry;
	

	public ZipFileResource( DirResource parent, ZipFile zipFile, ZipArchiveEntry entry )
	{
		this.parent = parent;
		this.zipFile = zipFile;
		this.entry = entry;
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
	public int getSize()
	{
		return (int) entry.getSize();
	}

	@Override
	public InputStream getInputStream()
	{
		try
		{
			return zipFile.getInputStream(entry);
		}
		catch( ZipException e )
		{
			e.printStackTrace();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
