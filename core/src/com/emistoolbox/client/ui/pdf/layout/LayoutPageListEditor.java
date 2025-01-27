package com.emistoolbox.client.ui.pdf.layout;

import java.util.ArrayList;
import java.util.List;

import com.emistoolbox.client.EmisEditor;
import com.emistoolbox.client.admin.ui.EmisUtils;
import com.emistoolbox.client.admin.ui.ListBoxWithUserObjects;
import com.emistoolbox.common.renderer.pdfreport.PdfContentConfig;
import com.emistoolbox.common.renderer.pdfreport.PdfText;
import com.emistoolbox.common.renderer.pdfreport.layout.LayoutFrameConfig;
import com.emistoolbox.common.renderer.pdfreport.layout.LayoutPageConfig;
import com.emistoolbox.common.renderer.pdfreport.layout.impl.LayoutFrameConfigImpl;
import com.emistoolbox.common.renderer.pdfreport.layout.impl.LayoutPageConfigImpl;
import com.emistoolbox.common.util.Rectangle;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LayoutPageListEditor extends VerticalPanel implements EmisEditor<List<LayoutPageConfig>>
{
	private LayoutPageEditor uiPageEditor;
	private ListBoxWithUserObjects<LayoutPageConfig> uiPages = new ListBoxWithUserObjects<LayoutPageConfig>(); 
	private PushButton btnAddPage = new PushButton("Add Page"); 
	private PushButton btnMoveUp = new PushButton("Up"); 
	private PushButton btnMoveDown = new PushButton("Down"); 
	private PushButton btnDelPage = new PushButton("Del Page");
	
	public LayoutPageListEditor(LayoutPageEditor editor)
	{
		this.uiPageEditor = editor; 
		setWidth("100%"); 

		/*
		uiContentList.addAddToPageHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				LayoutPageConfig page = uiPages.getUserObject(); 
				if (page == null)
					return; 
				
				PdfContentConfig content = LayoutPageListEditor.this.uiContentList.removeContent();
				if (content == null)
					return; 
				
				LayoutFrameConfig frame = new LayoutFrameConfigImpl(); 
				frame.setContentConfig(content); 
				frame.setPosition(getDefaultPosition(page.getFrames()));
				
				page.addFrame(frame);
			}
		}); 
		 */ 
		
		uiPages.setVisibleItemCount(5);
		uiPages.setWidth("100%");

		EmisUtils.init(btnAddPage, 60); 
		EmisUtils.init(btnMoveDown, 60); 
		EmisUtils.init(btnMoveUp, 60); 
		EmisUtils.init(btnDelPage, 60); 
		
		HorizontalPanel hp = new HorizontalPanel(); 
		hp.setSpacing(3);
		hp.add(btnAddPage);
		hp.add(btnMoveUp);
		hp.add(btnMoveDown);
		hp.add(btnDelPage);
		
		add(new HTML("<div class='section'>Pages</div>")); 
		add(uiPages);
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		add(hp); 

		btnAddPage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				commit();
				
				LayoutPageConfig config = new LayoutPageConfigImpl();
				uiPages.add(getTitle(config, uiPages.getItemCount()), config);
				uiPages.setSelectedIndex(uiPages.getItemCount());

				updateUi(); 
			}
		});
		
		btnDelPage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int index = uiPages.getSelectedIndex(); 
				if (index == -1)
					return;
				
				uiPages.removeItem(index);
				if (index >= uiPages.getItemCount())
					index--; 
				
				if (index == -1)
					return; 
				
				uiPages.setSelectedIndex(index);
				uiPageEditor.set(uiPages.getUserObject());

				updateUi(); 
			}
		});

		btnMoveUp.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				uiPages.moveUp();
				updateTitles(); 
				updateUi(); 
			}
		});

		btnMoveDown.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				uiPages.moveDown();
				updateTitles(); 
				updateUi(); 
			}
		});
		
		uiPages.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				updateUi(); 
			}
			
		}); 
	}
	
	@Override
	public void commit() 
	{ uiPageEditor.commit(); }

	@Override
	public List<LayoutPageConfig> get() 
	{
		commit(); 
		
		List<LayoutPageConfig> result = new ArrayList<LayoutPageConfig>(); 
		for (int i = 0; i < uiPages.getItemCount(); i++) 
			result.add(uiPages.getUserObject(i));
		
		return result; 
	}

	@Override
	public void set(List<LayoutPageConfig> pageConfigs) 
	{
		uiPages.clear(); 
		int i = 0;
		for (LayoutPageConfig pageConfig : pageConfigs)
		{
			uiPages.add(getTitle(pageConfig, i), pageConfig);  
			i++; 
		}
		
		if (uiPages.getItemCount() > 0)
			uiPages.setSelectedIndex(0);
		
		updateUi(); 
	}
	
	private String getTitle(LayoutPageConfig config, int page)
	{
		String result = "Page " + (page + 1);
		String title = config.getText(PdfText.TEXT_TITLE);
		if (title != null)
			result += ": " + title; 
		
		return result; 
	}
	
	private void updateTitles()
	{
		for (int i = 0; i < uiPages.getItemCount(); i++)
			uiPages.setItemText(i, getTitle(uiPages.getUserObject(i), i));
	}
	
	private void updateUi()
	{
		int index = uiPages.getSelectedIndex(); 
		int count = uiPages.getItemCount(); 
		
		btnDelPage.setEnabled(index != -1);
		btnMoveUp.setEnabled(index > 0); 
		btnMoveDown.setEnabled(index != -1 && index < count - 1); 
		
		if (index != -1)
			uiPageEditor.set(uiPages.getUserObject());
	}
}
