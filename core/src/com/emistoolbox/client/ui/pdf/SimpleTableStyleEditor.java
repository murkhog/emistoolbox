package com.emistoolbox.client.ui.pdf;

import com.emistoolbox.client.EmisEditor;
import com.emistoolbox.common.renderer.pdfreport.impl.SimpleTableStyle;
import com.emistoolbox.common.renderer.pdfreport.layout.BorderStyle;
import com.emistoolbox.common.renderer.pdfreport.layout.impl.CSSCreator;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;

public class SimpleTableStyleEditor extends HTML implements EmisEditor<SimpleTableStyle>, HasValueChangeHandlers<SimpleTableStyle>
{
	private SimpleTableStyle style; 

	private PopupEditor uiPopup = new PopupEditor(); 

	private ChartColorEditor uiTableBorderColor = new ChartColorEditor(); 
	private IntPicker uiTableBorderWidth = new IntPicker(BorderEditor.BORDER_WIDTHS, "", "pt"); 
	
	private ChartColorEditor uiHeaderBgColor = new ChartColorEditor(); 
	private ChartFontEditor uiHeaderFont = new ChartFontEditor(); 
	private ChartColorEditor uiHeaderBorderColor = new ChartColorEditor(); 
	private IntPicker uiHeaderBorderWidth = new IntPicker(BorderEditor.BORDER_WIDTHS, "", "pt"); 
	
	private ChartColorEditor uiDataBgColor = new ChartColorEditor(); 
	private ChartFontEditor uiDataFont = new ChartFontEditor(); 
	private ChartColorEditor uiDataBorderColor = new ChartColorEditor(); 
	private IntPicker uiDataBorderWidth = new IntPicker(BorderEditor.BORDER_WIDTHS, "", "pt");
	
	private IntPicker uiPadding = new IntPicker(new int[] { 0, 1, 2, 4, 5, 7, 10, 15, 20 }, "", "pt"); 
	
	public SimpleTableStyleEditor()
	{
		uiPopup.add("Padding", uiPadding);
		
		uiPopup.add(new HTML("<b>Table Border</b>"));
		uiPopup.add("Color", uiTableBorderColor); 
		uiPopup.add("Width", uiTableBorderWidth); 
		
		uiPopup.add(new HTML("<b>Headers</b>"));
		uiPopup.add("Font", uiHeaderFont); 
		uiPopup.add("Background", uiHeaderBgColor); 
		uiPopup.add("Border color", uiHeaderBorderColor); 
		uiPopup.add("Border width", uiHeaderBorderWidth); 

		uiPopup.add(new HTML("<b>Data</b>")); 
		uiPopup.add("Font", uiDataFont); 
		uiPopup.add("Background", uiDataBgColor); 
		uiPopup.add("Border color", uiDataBorderColor); 
		uiPopup.add("Border width", uiDataBorderWidth); 
		
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (style != null)
					uiPopup.show(SimpleTableStyleEditor.this); 
			}
		}); 
		
		uiPopup.addValueChangeHandler(new ValueChangeHandler<PopupEditor>() {
			@Override
			public void onValueChange(ValueChangeEvent<PopupEditor> event) 
			{
				ValueChangeEvent.fire(SimpleTableStyleEditor.this, get());
				updateUi(); 
			}
		}); 
		
		set(null); 
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<SimpleTableStyle> handler) 
	{ return addHandler(handler, ValueChangeEvent.getType()); }

	@Override
	public void commit() 
	{
		if (style == null)
			return; 
		
		style.setHeaderBackground(uiHeaderBgColor.get());
		style.setHeaderFont(uiHeaderFont.get());
		BorderStyle border = style.getHeaderBorder(); 
		if (border == null)
			border = new BorderStyle();
		border.setWidth(uiHeaderBorderWidth.get());
		border.setColor(uiHeaderBorderColor.get());
		style.setHeaderBorder(border);
		
		style.setDataBackground(uiDataBgColor.get());
		style.setDataFont(uiDataFont.get());
		border = style.getDataBorder(); 
		if (border == null)
			border = new BorderStyle();
		border.setWidth(uiDataBorderWidth.get());
		border.setColor(uiDataBorderColor.get());
		style.setDataBorder(border);

		border = style.getTableBorder(); 
		if (border == null)
			border = new BorderStyle();
		border.setWidth(uiTableBorderWidth.get());
		border.setColor(uiTableBorderColor.get());
		style.setTableBorder(border);
		
		style.setPadding(uiPadding.get());
		style.init(); 
	}

	@Override
	public SimpleTableStyle get() 
	{
		commit(); 
		return style;
	}

	@Override
	public void set(SimpleTableStyle style) 
	{
		this.style = style; 
	
		if (style == null)
			setVisible(false); 
		else
		{
			setVisible(true); 
			
			uiHeaderBgColor.set(style.getHeaderBackground()); 
			uiHeaderFont.set(style.getHeaderFont());
			
			uiDataBgColor.set(style.getDataBackground()); 
			uiDataFont.set(style.getDataFont());

			setBorder(style.getHeaderBorder(), uiHeaderBorderColor, uiHeaderBorderWidth); 
			setBorder(style.getDataBorder(), uiDataBorderColor, uiDataBorderWidth); 
			setBorder(style.getTableBorder(), uiTableBorderColor, uiTableBorderWidth); 

			uiPadding.set((int) style.getPadding());
			
			updateUi(); 
		}
	}

	private void setBorder(BorderStyle border, ChartColorEditor uiColor, IntPicker uiWidth)
	{
		if (border == null || border.getColour() == null)
			uiColor.set(null); 
		else
			uiColor.set(border.getColour());
		
		if (border == null)
			uiWidth.set(0);
		else
			uiWidth.set(border.getWidth());
	}
	
	public void updateUi()
	{ setHTML(SimpleTableStyleEditor.getTablePreview(style)); }
	
	public static String getTablePreview(SimpleTableStyle style)
	{
		String[][] data = new String[][] { 
			{ "", "Header", "Header" }, 
			{ "Header", "100", "300" },
			{ "Header", "100", "300" }
		}; 
		
		return getTablePreview(data, style); 
	}
	
	public static String getTablePreview(String[][] data, SimpleTableStyle style)
	{
		String tableCss = CSSCreator.getCssAsString(style.getTableBorder());
		String headerCss = CSSCreator.getCssAsString(style.getHeaderFont(), style.getHeaderBackground(), style.getHeaderBorder()); 
		String dataCss = CSSCreator.getCssAsString(style.getDataFont(), style.getDataBackground(), style.getDataBorder());
		
		StringBuffer result = new StringBuffer(); 
		result.append("<table style='border-collapse: collapse; ").append(tableCss).append("'>"); 

		for (int row = 0; row < data.length; row++)
		{
			result.append("<tr>");
			for (int col = 0; col < data[0].length; col++)
			{
				String cellCss = dataCss; 
				if (row == 0 || col == 0)
					cellCss = headerCss; 
				
				result.append("<td style='").append(cellCss).append("'>").append(data[row][col]).append("</td>"); 
			}
			result.append("</tr>"); 
		}
				
		result.append("</table>"); 
		
		return result.toString(); 
	}
}
