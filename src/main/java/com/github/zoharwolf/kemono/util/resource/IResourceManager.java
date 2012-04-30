package com.github.zoharwolf.kemono.util.resource;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface IResourceManager
{
    InputStream getResourceAsStream( String name ) throws FileNotFoundException;
}