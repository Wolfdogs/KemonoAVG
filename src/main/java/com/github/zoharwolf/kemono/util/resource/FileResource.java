package com.github.zoharwolf.kemono.util.resource;

import java.io.InputStream;

public interface FileResource extends Resource
{
	int getSize();
	InputStream getInputStream();
}
