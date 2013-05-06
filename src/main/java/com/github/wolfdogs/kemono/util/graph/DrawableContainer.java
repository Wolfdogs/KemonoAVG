package com.github.wolfdogs.kemono.util.graph;

import java.util.LinkedList;
import java.util.List;

/**
 * 可绘画对象的容器，用于管理可绘画对象的渲染循序以及事件处理。
 */
public class DrawableContainer extends DrawableObject
{
	private List<Renderable> drawables;
	
	
	public DrawableContainer()
	{
		drawables = new LinkedList<>();
	}
	
	public void add(Renderable drawable)
	{
		drawables.add(drawable);
	}
	
	@Override
	public void onRender()
	{
		for (Renderable drawable : drawables)
		{
			drawable.render();
		}
	}
}
