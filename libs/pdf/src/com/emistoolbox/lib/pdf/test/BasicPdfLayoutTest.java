package com.emistoolbox.lib.pdf.test;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.emistoolbox.lib.pdf.layout.PDFLayout;
import com.emistoolbox.lib.pdf.layout.PDFLayoutAlignmentPlacement;
import com.emistoolbox.lib.pdf.layout.PDFLayoutBorderStyle;
import com.emistoolbox.lib.pdf.layout.PDFLayoutComponent;
import com.emistoolbox.lib.pdf.layout.PDFLayoutContent;
import com.emistoolbox.lib.pdf.layout.PDFLayoutCoordinatePlacement;
import com.emistoolbox.lib.pdf.layout.PDFLayoutFont;
import com.emistoolbox.lib.pdf.layout.PDFLayoutFontStyle;
import com.emistoolbox.lib.pdf.layout.PDFLayoutFrame;
import com.emistoolbox.lib.pdf.layout.PDFLayoutHorizontalAlignment;
import com.emistoolbox.lib.pdf.layout.PDFLayoutLineStyle;
import com.emistoolbox.lib.pdf.layout.PDFLayoutObjectFit;
import com.emistoolbox.lib.pdf.layout.PDFLayoutPDFContent;
import com.emistoolbox.lib.pdf.layout.PDFLayoutSides;
import com.emistoolbox.lib.pdf.layout.PDFLayoutTextContent;
import com.emistoolbox.lib.pdf.layout.PDFLayoutVerticalAlignment;

import es.jbauer.lib.io.impl.IOFileInput;
import info.joriki.graphics.Rectangle;

public class BasicPdfLayoutTest 
{
	private String testDir; 
	
	public BasicPdfLayoutTest(String testDir)
	{ this.testDir = testDir; } 
	
	public List<PDFLayout> getLayout()
	{
		PDFLayout layout = new PDFLayout();
		
		Rectangle page = new Rectangle(0, 0, 842, 595);
		PDFLayoutFrame outerFrame = new PDFLayoutFrame(); 
		outerFrame.setRectangle(page);

		List<PDFLayoutComponent> components = new ArrayList<PDFLayoutComponent>(); 
		
		PDFLayoutFont titleFont = new PDFLayoutFont(PDFLayoutFont.FONT_HELVETICA, 24, PDFLayoutFontStyle.BOLD); 
		PDFLayoutFont subtitleFont = new PDFLayoutFont(PDFLayoutFont.FONT_HELVETICA, 20, PDFLayoutFontStyle.BOLD);
//		PDFLayoutFont textFont = new PDFLayoutFont(PDFLayoutFont.FONT_HELVETICA, 16, PDFLayoutFontStyle.BOLD);
		PDFLayoutFont textFont = new PDFLayoutFont(PDFLayoutFont.FONT_HELVETICA, 16, PDFLayoutFontStyle.PLAIN);

		double textHeight = 50; 
		
		// Three by three sizes
		double gap = 18.0; 
		double cellWidth = (page.width() - gap * 2) / 3;
		double cellHeight = (page.height() - gap * 2) / 3;

		// Charts along bottom 
		components.add(getPositionedPdf("chart_bottom1.pdf", 0, 2 * (cellHeight + gap), cellWidth * 2 + gap, cellHeight));
		components.add(getPositionedPdf("chart_bottom2.pdf", 2 * (cellWidth + gap), 2 * (cellHeight + gap), cellWidth, cellHeight));
		
		// Big 2x2 chart
		components.add(getPositionedPdf("chart_main.pdf", cellWidth + gap, 0, cellWidth * 2 + gap, cellHeight * 2 + gap)); 

		// Title
		PDFLayoutTextContent title = new PDFLayoutTextContent("District Grade 2 Literacy Progress Report", titleFont);
		PDFLayoutTextContent subtitle = new PDFLayoutTextContent("District Chipata (Eastern)", subtitleFont);

		components.add(new PDFLayoutComponent(title, null, PDFLayoutHorizontalAlignment.CENTER, PDFLayoutVerticalAlignment.TOP));
		components.add(new PDFLayoutComponent(subtitle, null, PDFLayoutHorizontalAlignment.CENTER, PDFLayoutVerticalAlignment.BELOW));

		// Table on left. 
		PDFLayoutFrame textFrame = new PDFLayoutFrame(); 
//		PDFLayoutContent textContent = new PDFLayoutTextContent("This is some bigger text that will explain the charts. The chart layout is based on 4 charts - one featured chart and then three smaller charts. \n\nThis layout illustrates how we can arbitrarily place content on the page to create more interesting report pages. \n\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", textFont); 
		// some smaller text for a first test
		PDFLayoutContent textContent = new PDFLayoutTextContent("The quick brown fox jumps over the lazy dog.", textFont);
		PDFLayoutComponent textComponent = new PDFLayoutComponent (textContent,null,10,54);
		textComponent.setBorderStyle (new PDFLayoutBorderStyle (2.));		
		textFrame.setComponents(Collections.singletonList(textComponent));
		textFrame.setRectangle (new Rectangle (0, 0, cellWidth, cellHeight * 2 + gap));
		components.add(new PDFLayoutComponent(textFrame,null,PDFLayoutHorizontalAlignment.LEFT,PDFLayoutVerticalAlignment.TOP));

		outerFrame.setComponents (components);
		
		layout.setOuterFrame(outerFrame); 

		return Collections.singletonList(layout); 
	}
	
	private PDFLayoutComponent getPositionedPdf(String filename, double x, double y, double width, double height)
	{
		return new PDFLayoutPDFContent (new IOFileInput(new File(testDir, filename))).wrap (width,height).position (x,y);
	}
}
