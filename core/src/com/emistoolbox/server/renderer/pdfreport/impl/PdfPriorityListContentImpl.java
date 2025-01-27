package com.emistoolbox.server.renderer.pdfreport.impl;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import com.emistoolbox.common.ChartFont;
import com.emistoolbox.common.model.priolist.PriorityListItem;
import com.emistoolbox.common.renderer.pdfreport.EmisTableStyle;
import com.emistoolbox.common.renderer.pdfreport.PdfPriorityListContentConfig;
import com.emistoolbox.common.results.PriorityMetaResult;
import com.emistoolbox.server.renderer.pdfreport.FontIdentifier;
import com.emistoolbox.server.renderer.pdfreport.PdfContentVisitor;
import com.emistoolbox.server.renderer.pdfreport.PdfPriorityListContent;

public class PdfPriorityListContentImpl extends PdfContentBase<PdfPriorityListContentConfig> implements PdfPriorityListContent, Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private List<PriorityListItem> result; 

	private String[] headers = null; 
	private int valueCount = 0; 
	private int fieldCount = 0; 

	private String[] formats = null; 
	
	@Override
	public List<PriorityListItem> getResults() 
	{ return result; }

	@Override
	public void setResults(List<PriorityListItem> result) 
	{
		this.result = result; 
		
		PriorityMetaResult meta = getConfig().getMetaResult(); 
		fieldCount = meta.getAdditionalFields().length; 
		valueCount = meta.getMetaResultValues().size(); 
		
		headers = new String[fieldCount + valueCount + 1];
		headers[0] = ""; 
		for (int i = 0; i < fieldCount; i++) 
			headers[1 + i] = meta.getAdditionalFields()[i];  

		formats = new String[valueCount]; 
		for (int i = 0; i < valueCount; i++) 
		{
			headers[1 + fieldCount + i] = meta.getMetaResultValue(i).getName(true); 
			headers[1 + fieldCount + i] = meta.getMetaResultValue(i).getName(true); 
			
			formats[i] = meta.getMetaResultValue(i).getFormat();  
		}
	}

	@Override
	public <T> T accept(PdfContentVisitor<T> visitor) 
	{ return visitor.visit(this); }

	@Override
	public void setFont(FontIdentifier paramFontIdentifier, ChartFont paramChartFont) 
	{}

	@Override
	public ChartFont getFont(FontIdentifier paramFontIdentifier) 
	{ return null; }

	@Override
	public int getColumns() 
	{ 
		return 1 + fieldCount + valueCount;   
	}

	@Override
	public int getRows() 
	{ return result.size() + 1; } 

	@Override
	public String getText(int row, int col) 
	{
		if (row == 0)
			return headers[col]; 
		
		PriorityListItem item = result.get(row - 1); 
		if (col == 0)
			return item.getId() + ": " + item.getName(); 
		
		if (col <= fieldCount)
			return item.getEntityValues()[col - 1]; 
		
		return getFormattedValue(formats[col - fieldCount - 1], item.getValues()[col - fieldCount - 1]);
	}
	
	private String getFormattedValue(String format, double value)
	{
		NumberFormat fmt= new DecimalFormat(format); 
		return fmt.format(value); 
	}

	@Override
	public EmisTableStyle getTableStyle() 
	{ return getConfig().getTableStyle(); } 

	@Override
	public void setTableStyle(EmisTableStyle style) 
	{}
}
