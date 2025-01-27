package com.emistoolbox.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LayoutSides<T> implements Serializable
{
	private T left; 
	private T top; 
	private T right; 
	private T bottom;
	
	public LayoutSides()
	{}
	
	public LayoutSides(T val)
	{
		left = val; 
		right = val; 
		top = val; 
		bottom = val;  
	}
	
	public LayoutSides(T[] values)
	{ setValues(values); }
	
	public T getLeft() 
	{ return left; }

	public void setLeft(T left) 
	{ this.left = left; }
	
	public T getTop() 
	{ return top; }
	
	public void setTop(T top) 
	{ this.top = top; }
	
	public T getRight() 
	{ return right; }
	
	public void setRight(T right) 
	{ this.right = right; }
	
	public T getBottom() 
	{ return bottom; }
	
	public void setBottom(T bottom) 
	{ this.bottom = bottom; } 
	
	public void setValues(T[] values)
	{
		left = values[0]; 
		top = values[1]; 
		right = values[2]; 
		bottom = values[3]; 
	}
	
	public T[] getValues(T[] values)
	{
		values[0] = left; 
		values[1] = top; 
		values[2] = right; 
		values[3] = bottom; 
		
		return values; 
	}
	
	public List<T> getCSSList () {
		List<T> list = new ArrayList<T> ();
		list.add (top);
		list.add (right);
		list.add (bottom);
		list.add (left);
		return list;
	}

	public LayoutSides<T> clone()
	{
		LayoutSides<T> result = new LayoutSides<T>();
		result.left = left; 
		result.right = right; 
		result.top = top; 
		result.bottom = bottom; 
		
		return result; 
	}
}
