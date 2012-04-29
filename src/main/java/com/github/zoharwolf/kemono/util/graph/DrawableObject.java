package com.github.zoharwolf.kemono.util.graph;

import org.lwjgl.opengl.GL11;

public abstract class DrawableObject implements Drawable, Positionable, Scaleable, Rotatable
{
	private float x, y, z;
	private float scaleX, scaleY;
	private float rotateX, rotateY, rotateZ;

	
	protected abstract void onDraw( GL11 gl );
	
	
	public DrawableObject()
	{
		
	}

	@Override
	public void draw( GL11 gl )
	{
		
	}

	@Override
	public float getX()
	{
		return x;
	}

	@Override
	public void setX( float x )
	{
		this.x = x;
	}

	@Override
	public float getY()
	{
		return y;
	}

	@Override
	public void setY( float y )
	{
		this.y = y;
	}

	@Override
	public float getZ()
	{
		return z;
	}

	@Override
	public void setZ( float z )
	{
		this.z = z;
	}

	@Override
	public float getScaleX()
	{
		return scaleX;
	}

	@Override
	public void setScaleX( float scaleX )
	{
		this.scaleX = scaleX;
	}

	@Override
	public float getScaleY()
	{
		return scaleY;
	}

	@Override
	public void setScaleY( float scaleY )
	{
		this.scaleY = scaleY;
	}

	@Override
	public float getRotateX()
	{
		return rotateX;
	}

	@Override
	public void setRotateX( float rotateX )
	{
		this.rotateX = rotateX;
	}

	@Override
	public float getRotateY()
	{
		return rotateY;
	}

	@Override
	public void setRotateY(float rotateY)
	{
		this.rotateY = rotateY;
	}

	@Override
	public float getRotateZ()
	{
		return rotateZ;
	}

	@Override
	public void setRotateZ(float rotateZ)
	{
		this.rotateZ = rotateZ;
	}
}
