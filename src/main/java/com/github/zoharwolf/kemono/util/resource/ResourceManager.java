package com.github.zoharwolf.kemono.util.resource;

import java.io.InputStream;

public abstract class ResourceManager implements IResourceManager
{
    @Override
    public abstract InputStream getResourceAsStream(String path);
}
