package com.emistoolbox.client.ui.pdf;

import com.emistoolbox.client.EmisEditor;
import com.emistoolbox.client.util.ui.UIUtils;
import com.emistoolbox.common.renderer.pdfreport.layout.BorderStyle;
import com.emistoolbox.common.util.LayoutSides;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;

public class BorderEditor extends FlexTable implements EmisEditor<LayoutSides<BorderStyle>> 
{
	private static String[] sizes = new String[] { "0", "1", "2", "3", "4", "5" }; 
	private static String[] labels = new String[] { "Left", "Top", "Right", "Bottom" }; 

	private LayoutSides<BorderStyle> borders; 
	
	private ListBox uiMode = new ListBox(); 
	private ListBox[] uiSizes = new ListBox[4]; 
	private ChartColorEditor[] uiColors = new ChartColorEditor[4];
	
	private int rowFirstEditor = 0; 

	public BorderEditor()
	{
		uiMode.addItem("Same sides");
		uiMode.addItem("Different sides");
		uiMode.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				updateUi(); 
			}
		}); 
		
		int row = 0; 
//		if (title != null)
//		{
//			setText(row, 0, title); 
//			getCellFormatter().setStyleName(row, 0, "section");
//			row++; 
//		}
		
		getFlexCellFormatter().setColSpan(row, 0, 3);
		setWidget(row++, 0, uiMode); 
		
		rowFirstEditor = row; 
		for (int i = 0; i < 4; i++) 
		{
			setText(row, 0, labels[i]); 
			
			uiColors[i] = new ChartColorEditor(); 
			setWidget(row, 1, uiColors[i]); 

			uiSizes[i] = new ListBox(); 
			setWidget(row, 2, uiSizes[i]); 
			for (String size : sizes)
				uiSizes[i].addItem(size); 
			
			row++; 
		}
		
		
	}

	@Override
	public void commit() 
	{
		if (borders == null)
			return;
		
		BorderStyle[] styles = borders.getValues(new BorderStyle[4]); 
		boolean sameSides = uiMode.getSelectedIndex() != 1; 
		for (int i = 0; i < 4; i++)
		{
			int uiIndex = sameSides ? 0 : i; 
			
			styles[i].setColor(uiColors[uiIndex].get());
			try { styles[i].setWidth(new Integer(UIUtils.getListBoxValue(uiSizes[uiIndex]))); }
			catch (Throwable err)
			{  styles[i].setWidth(0); }
		}
	}

	@Override
	public LayoutSides<BorderStyle> get() 
	{
		commit(); 
		return borders; 
	}

	@Override
	public void set(LayoutSides<BorderStyle> borders) 
	{
		this.borders = borders; 
		if (borders == null)
			setVisible(false); 
		else
		{
			setVisible(true); 

			boolean sameSides = true; 
			BorderStyle[] values = borders.getValues(new BorderStyle[4]); 
			for (int i = 0; i < 4; i++)
			{
				if (values[i] != null)
				{
					UIUtils.setListBoxValue(uiSizes[i], "" + values[i].getWidth()); 
					uiColors[i].set(values[i].getColour());
					sameSides &= same(values[0], values[i]); 
				}
			}
			
			uiMode.setSelectedIndex(sameSides ? 0 : 1);
			updateUi(); 
		}
	}
	
	private void updateUi()
	{		
		boolean sameSides = uiMode.getSelectedIndex() != 1; 
		for (int row = rowFirstEditor + 1; row < getRowCount(); row++)
			getRowFormatter().setVisible(row, !sameSides); 

		setText(rowFirstEditor, 0, sameSides ? "All" : labels[0]);
	}
	
	private boolean same(BorderStyle bs1, BorderStyle bs2)
	{
		if (bs1 == null && bs2 == null)
			return true; 
		
		if (bs1.getWidth() != bs2.getWidth())
			return false; 
		
		if (bs1.getColour() == null)
			return bs2.getColour() == null; 
		else
			return bs1.getColour().equals(bs2.getColour()); 
	}
}