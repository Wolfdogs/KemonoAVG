package com.github.zoharwolf.kemono.util.resource;

import java.io.FileNotFoundException;
import java.io.InputStream;

public abstract class ResourceManager
{
    public static IResourceManager getResource(String resName)
    {
        return new MultiResourceManager(resName);
    }
}
