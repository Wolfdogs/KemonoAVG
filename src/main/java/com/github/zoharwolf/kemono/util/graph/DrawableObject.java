package com.github.zoharwolf.kemono.util.graph;

import org.lwjgl.opengl.GL11;


public abstract class DrawableObject implements Renderable, Positionable, Centerable, Scaleable, Rotatable
{
	private float x, y, z;
	private float centerX, centerY, centerZ;
	private float scaleX, scaleY, scaleZ;
	private float rotateX, rotateY, rotateZ;

	
	protected abstract void onDraw();
	
	
	public DrawableObject()
	{
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		centerX = 0.0f;
		centerY = 0.0f;
		centerZ = 0.0f;
		scaleX = 1.0f;
		scaleY = 1.0f;
		scaleZ = 1.0f;
		rotateX = 0.0f;
		rotateY = 0.0f;
		rotateZ = 0.0f;
	}
	
	protected void tran()
	{
		GL11.glMatrixMode( GL11.GL_MODELVIEW );
		
		float x = this.x, y = this.y, z = this.z;
		float cx = centerX, cy = centerY, cz = centerZ;
		float sx = scaleX, sy = scaleY, sz = scaleZ;
		float rx = rotateX, ry = rotateY, rz = rotateZ;
		
		GL11.glTranslatef( cx, cy, cz );
		GL11.glRotatef( rx, 1.0f, 0.0f, 0.0f );
		GL11.glRotatef( ry, 0.0f, 1.0f, 0.0f );
		GL11.glRotatef( rz, 0.0f, 0.0f, 1.0f );
		GL11.glScalef( sx, sy, sz );
		GL11.glTranslatef( -cx, -cy, -cz );
		
		GL11.glMatrixMode( GL11.GL_PROJECTION );
		GL11.glTranslatef( x, y, z );
	}

	@Override
	public void render()
	{
		tran();
		onDraw();
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
	public float getCenterX()
	{
		return centerX;
	}
	
	@Override
	public void setCenterX(float centerX)
	{
		this.centerX = centerX;
	}
	
	@Override
	public float getCenterY()
	{
		return centerY;
	}
	
	@Override
	public void setCenterY(float centerY)
	{
		this.centerY = centerY;
	}
	
	@Override
	public float getCenterZ()
	{
		return centerZ;
	}
	
	@Override
	public void setCenterZ(float centerZ)
	{
		this.centerZ = centerZ;
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
	
	public float getScaleZ()
	{
		return scaleZ;
	}
	
	public void setScaleZ(float scaleZ)
	{
		this.scaleZ = scaleZ;
	}

	@Override
	public float getRotationX()
	{
		return rotateX;
	}

	@Override
	public void setRotationX( float rotateX )
	{
		this.rotateX = rotateX;
	}

	@Override
	public float getRotationY()
	{
		return rotateY;
	}

	@Override
	public void setRotationY(float rotateY)
	{
		this.rotateY = rotateY;
	}

	@Override
	public float getRotationZ()
	{
		return rotateZ;
	}

	@Override
	public void setRotationZ(float rotateZ)
	{
		this.rotateZ = rotateZ;
	}
}
