package com.github.zoharwolf.kemono.util.resource;


/**
 * 工厂类，制造IResourceManager的实例。
 * @author zohar
 *
 */
public abstract class ResourceManager implements IResourceManager 
{
    /**
     * 制造IResourceManager的实例
     * @param resName 指定的资源名，参照MultiResourceManager.java中的init()方法的注释
     * @return 返回一个IResourceManager实例，其中包含了所有由resName生成的IResourceManager实例。
     */
    public static dir getResource(String resName)
    {
        return new MultiResourceManager(resName);
    }
    
}
