package com.github.zoharwolf.kemono.util.resource.filter;

import com.github.zoharwolf.kemono.util.resource.FileResource;

public interface FileResourceFilter
{
	boolean isAccept( FileResource resource );
}
