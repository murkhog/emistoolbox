package com.emistoolbox.lib.pdf;

import info.joriki.graphics.Rectangle;
import info.joriki.graphics.Transformation;
import info.joriki.io.SeekableByteArray;
import info.joriki.io.Util;
import info.joriki.pdf.ConstructiblePDFDocument;
import info.joriki.pdf.PDFArray;
import info.joriki.pdf.PDFDictionary;
import info.joriki.pdf.PDFFile;
import info.joriki.pdf.PDFFont;
import info.joriki.pdf.PDFStream;
import info.joriki.pdf.PDFWriter;
import info.joriki.pdf.TextState;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import com.emistoolbox.lib.pdf.layout.PDFLayout;
import com.emistoolbox.lib.pdf.layout.PDFLayoutAlignmentPlacement;
import com.emistoolbox.lib.pdf.layout.PDFLayoutElement;
import com.emistoolbox.lib.pdf.layout.PDFLayoutCoordinatePlacement;
import com.emistoolbox.lib.pdf.layout.PDFLayoutFont;
import com.emistoolbox.lib.pdf.layout.PDFLayoutFrameElement;
import com.emistoolbox.lib.pdf.layout.PDFLayoutHighchartElement;
import com.emistoolbox.lib.pdf.layout.PDFLayoutImageElement;
import com.emistoolbox.lib.pdf.layout.PDFLayoutObjectFit;
import com.emistoolbox.lib.pdf.layout.PDFLayoutPDFElement;
import com.emistoolbox.lib.pdf.layout.PDFLayoutFileElement;
import com.emistoolbox.lib.pdf.layout.PDFLayoutPlacement;
import com.emistoolbox.lib.pdf.layout.PDFLayoutSides;
import com.emistoolbox.lib.pdf.layout.PDFLayoutTextElement;
import com.emistoolbox.lib.pdf.layout.PDFLayoutVisitor;

import es.jbauer.lib.io.IOOutput;

public class PDFLayoutRenderer implements PDFLayoutVisitor<Void> {
	final static Color debugBoxColor = Color.LIGHT_GRAY;
	final static int ndigits = 6;

	private ResourceRenamer resourceRenamer;
	private PrintStream ps;
	private boolean debugging;

	public void setDebugging (boolean debugging) {
		this.debugging = debugging;
	}

	public void render (List<PDFLayout> layouts,IOOutput output) throws IOException {
		ConstructiblePDFDocument document = new ConstructiblePDFDocument ();
		
		// TODO: test document with more than one page
		for (PDFLayout layout : layouts)
			render (layout,document);
		
		PDFWriter writer = new PDFWriter (output.getOutputStream ());
		writer.write (document);
		writer.close ();
	}

	private void render (PDFLayout layout,ConstructiblePDFDocument document) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		ps = new PrintStream (baos);
		resourceRenamer = new ResourceRenamer ("R");
		PDFLayoutFrameElement outerFrame = layout.getOuterFrame ();
		flip (getBoundingBox (outerFrame));
		outerFrame.accept (this);
		ps.close ();
		PDFDictionary page = new PDFDictionary ("Page");
		page.put ("MediaBox",new PDFArray (getBoundingBox (outerFrame)));
		page.putIndirect ("Contents",new PDFStream (baos.toByteArray ()));
		PDFDictionary resources = resourceRenamer.getResources ();
		PDFDictionary fonts = resources.getOrCreateDictionary ("Font");
		for (Entry<PDFLayoutFont,String> entry : fontLabels.entrySet ())
			fonts.putIndirect (entry.getValue (),getFontDictionary (entry.getKey ()));
		page.putIndirect ("Resources",resources);
		document.addPage (page);
	}

	final static Set<String> standardFontNames = new HashSet<String> ();
	static {
		standardFontNames.add (PDFLayoutFont.FONT_TIMES);
		standardFontNames.add (PDFLayoutFont.FONT_HELVETICA);
		standardFontNames.add (PDFLayoutFont.FONT_COURIER);
	}

	private PDFDictionary getFontDictionary (PDFLayoutFont layoutFont) {
		if (!standardFontNames.contains (layoutFont.getFontName ()))
			throw new Error ("only standard fonts implemented");

		PDFDictionary dictionary = new PDFDictionary ("Font");
		dictionary.put ("Subtype","Type1");

		String baseFont = layoutFont.getFontName ();
		boolean isBold = layoutFont.getFontStyle ().isBold ();
		boolean isItalic = layoutFont.getFontStyle ().isItalic ();
		boolean isTimes = baseFont.equals ("Times");
		if (isBold || isItalic) {
			baseFont += '-';
			if (isBold)
				baseFont += "Bold";
			if (isItalic)
				baseFont += isTimes ? "Italic" : "Oblique";
		}
		else if (isTimes)
			baseFont += "-Roman";

		dictionary.put ("BaseFont",baseFont);
		// TODO: using standard encoding for now -- do proper encoding

		return dictionary;
	}

	private PDFFont getPDFFont (PDFLayoutFont layoutFont) {
		return PDFFont.getInstance (getFontDictionary (layoutFont),null);
	}

	private void drawRectangle (Rectangle r) {
		outputRectangle ((Rectangle) r.transformedBy (transformStack.peek ()));
		ps.print ("s\n");
	}

	private void drawRectangle (Rectangle r,Color color) {
		pushGraphicsState ();
		setStrokingColor (color);
		drawRectangle (r);
		popGraphicsState ();
	}

	private void setStrokingColor (Color color) {
		coordinateCommand ("SC",color.getRGBColorComponents (null));
	}

	private void outputRectangle (Rectangle r) {
		coordinateCommand ("re",r.xmin,r.ymin,r.width (),r.height ());
	}

	private void flip (Rectangle r) {
		transform (new Transformation (1,0,0,-1,0,r.ymin + r.ymax));
	}

	private void transform (Rectangle from,Rectangle to) {
		transform (Transformation.matchBoxes (from,to));
	}

	private Stack<Transformation> transformStack = new Stack<Transformation> ();
	{
		transformStack.push (new Transformation ());
	}

	private void pushTransform () {
		transformStack.push (transformStack.peek ());
	}

	private void popTransform () {
		transformStack.pop ();
	}

	private void transform (Transformation transform) {
		transformStack.push (new Transformation (transform,transformStack.pop ()));
	}

	private void outputTransform (Transformation transform) {
		coordinateCommand ("cm",transform.matrix);
	}

	private void coordinateCommand (String command,float ... m) {
		double [] d = new double [m.length];
		for (int i = 0;i < m.length;i++)
			d [i] = m [i];
		coordinateCommand (command,d);
	}

	private void coordinateCommand (String command,double ... m) {
		for (double v : m) {
			print (v);
			ps.print (' ');
		}
		ps.print (command);
		ps.print ('\n');
	}

	private void pushGraphicsState () {
		ps.print ("q\n");
	}

	private void popGraphicsState () {
		ps.print ("Q\n");
	}

	private void print (double x) {
		if (x < 0) {
			ps.print ('-');
			print (-x);
		}
		else {
			long l = (long) x;
			ps.print (l);
			StringBuilder builder = new StringBuilder ();
			builder.append ('.');
			for (int i = 0;i < ndigits;i++) {
				x -= l;
				x *= 10;
				l = (long) x;
				builder.append (l);
				if (l != 0) {
					ps.print (builder);
					builder.setLength (0);
				}
			}
		}
	}

	public Void visit(PDFLayout page) throws IOException {
		return null;
	}

	public Void visit (PDFLayoutFrameElement frame) throws IOException {
		PDFLayoutSides<Boolean> alignedWithEdge = new PDFLayoutSides<Boolean> (true);
		Rectangle frameBox = getBoundingBox (frame);
		Rectangle objectFitBox = new Rectangle (frameBox);
		Rectangle previousElementBox = new Rectangle (frameBox.xmax,frameBox.ymax,frameBox.xmin,frameBox.ymin);
		for (PDFLayoutElement element : frame.getElements ()) {
			PDFLayoutPlacement placement = element.getPlacement ();
			PDFLayoutObjectFit objectFit = element.getObjectFit ();
			Rectangle elementBox = getBoundingBox (element);
			double width = 0;
			double height = 0;
			switch (objectFit) {
			case CONTAIN:
				double scale = Math.min (objectFitBox.width () / elementBox.width (),objectFitBox.height () / elementBox.height ());
				width = scale * elementBox.width ();
				height = scale * elementBox.height ();
				break;
			case NONE:
				break;
				default:
					throw new Error ("object fit " + objectFit + " not implemented");
			}
			Rectangle newElementBox = objectFit == PDFLayoutObjectFit.NONE ? new Rectangle (elementBox) : new Rectangle (0,0,width,height);
			double x;
			double y;
			if (placement instanceof PDFLayoutCoordinatePlacement) {
				PDFLayoutCoordinatePlacement coordinatePlacement = (PDFLayoutCoordinatePlacement) placement;
				x = coordinatePlacement.getX ();
				y = coordinatePlacement.getY ();
				alignedWithEdge = new PDFLayoutSides<Boolean> (false);
			}
			else if (placement instanceof PDFLayoutAlignmentPlacement) {
				PDFLayoutAlignmentPlacement alignmentPlacement = (PDFLayoutAlignmentPlacement) placement;
				switch (alignmentPlacement.getHorizontalAlignment ()) {
				case BEFORE:
					x = previousElementBox.xmin - newElementBox.width ();
					alignedWithEdge.setLeft (false);
					break;
				case AFTER:
					x = previousElementBox.xmax;
					alignedWithEdge.setRight (false);
					break;
				case LEFT:
					x = frameBox.xmin;
					alignedWithEdge.setLeft (true);
					alignedWithEdge.setRight (false);
					break;
				case CENTER:
					x = objectFitBox.xmin + (objectFitBox.width () - newElementBox.width ()) / 2;
					alignedWithEdge.setLeft (false);
					alignedWithEdge.setRight (false);
					break;
				case RIGHT:
					x = frameBox.xmax - newElementBox.width ();
					alignedWithEdge.setLeft (false);
					alignedWithEdge.setRight (true);
					break;
				default:
					throw new Error ("horizontal placement " + alignmentPlacement.getHorizontalAlignment () + " not implemented");
				}

				switch (alignmentPlacement.getVerticalAlignment ()) {
				case ABOVE:
					y = previousElementBox.ymin - newElementBox.height ();
					alignedWithEdge.setTop (false);
					break;
				case BELOW:
					y = previousElementBox.ymax;
					alignedWithEdge.setBottom (false);
					break;
				case TOP:
					y = frameBox.ymin;
					alignedWithEdge.setTop (true);
					alignedWithEdge.setBottom (false);
					break;
				case CENTER:
					y = objectFitBox.ymin + (objectFitBox.height () - newElementBox.height ()) / 2;
					alignedWithEdge.setTop (false);
					alignedWithEdge.setBottom (false);
					break;
				case BOTTOM:
					y = frameBox.ymax - newElementBox.height ();
					alignedWithEdge.setTop (false);
					alignedWithEdge.setBottom (true);
				default:
					throw new Error ("vertical placement " + alignmentPlacement.getVerticalAlignment () + " not implemented");
				}
			}
			else
				throw new Error ("placement " + placement.getClass () + " not implemented");

			newElementBox.shiftBy (newElementBox.xmin - x,newElementBox.ymin - y);

			if (element.getDisplacement ().getVertical ()) {
				if (alignedWithEdge.getTop ())
					objectFitBox.ymin = newElementBox.ymax;
				if (alignedWithEdge.getBottom ())
					objectFitBox.ymax = newElementBox.ymin;
			}

			if (element.getDisplacement ().getHorizontal ()) {
				if (alignedWithEdge.getLeft ())
					objectFitBox.xmin = newElementBox.xmax;
				if (alignedWithEdge.getRight ())
					objectFitBox.xmax = newElementBox.xmin;
			}

			pushTransform ();
//			can either draw new box before transform or old box after transform
//			if (debugging)
//				drawRectangle (newElementBox,debugBoxColor);
			if (!newElementBox.equals (elementBox))
				transform (elementBox,newElementBox);
			if (debugging)
				drawRectangle (elementBox,debugBoxColor);
			boolean isLeaf = !(element instanceof PDFLayoutFrameElement);
			if (isLeaf) {
				flip (elementBox);
				pushGraphicsState ();
				outputTransform (transformStack.peek ());
			}
			element.accept (this);
			if (isLeaf)
				popGraphicsState ();
			popTransform ();
			previousElementBox = newElementBox;
		}
		return null;
	}

	public Void visit (PDFLayoutHighchartElement element) throws IOException {
		throw new Error ("PDF layout highchart element rendering not implemented");
	}

	public Void visit (PDFLayoutImageElement element) throws IOException {
		throw new Error ("PDF layout image element rendering not implemented");
	}

	public Void visit (PDFLayoutPDFElement pdfElement) throws IOException {
		ps.write (resourceRenamer.rename (getPage (pdfElement)));
		return null;
	}

	public Void visit (PDFLayoutTextElement textElement) {
		PDFLayoutFont layoutFont = textElement.getFont ();
		double fontSize = layoutFont.getFontSize ();
		ps.print ("BT /" + getFontLabel (layoutFont) + " " + fontSize + " Tf (" + textElement.getText () + ") Tj ET\n");
		return null;
	}

	private int fontIndex;
	private Map<PDFLayoutFont,String> fontLabels = new HashMap<PDFLayoutFont,String> ();

	private String getFontLabel (PDFLayoutFont layoutFont) {
		String fontLabel = fontLabels.get (layoutFont);
		if (fontLabel == null) {
			fontLabel = "F" + ++fontIndex;
			fontLabels.put (layoutFont,fontLabel);
		}
		return fontLabel;
	}

	private Rectangle getBoundingBox (PDFLayoutElement element) throws IOException {
		return element.accept (new PDFLayoutVisitor<Rectangle> () {
			public Rectangle visit(PDFLayout page) throws IOException {
				return null;
			}
			public Rectangle visit (PDFLayoutFrameElement frame) throws IOException {
				return new Rectangle (0,0,frame.getWidth (),frame.getHeight ());
			}

			public Rectangle visit (PDFLayoutHighchartElement element) throws IOException {
				throw new Error ("PDF layout highchart element bounding box not implemented");
			}
			public Rectangle visit (PDFLayoutImageElement element) throws IOException {
				throw new Error ("PDF layout image element bounding box not implemented");
			}
			public Rectangle visit (PDFLayoutPDFElement element) throws IOException {
				return getPage (element).getMediaBox ().toRectangle ();
			}

			public Rectangle visit (PDFLayoutTextElement textElement) {
				PDFLayoutFont layoutFont = textElement.getFont ();
				PDFFont pdfFont = getPDFFont (layoutFont);
				TextState textState = new TextState ();
				double fontSize = layoutFont.getFontSize ();
				textState.setTextFont (pdfFont,fontSize);
				double advance = textState.getAdvance (textElement.getText ().getBytes ()); // TODO: proper encoding
				double descent = fontSize * pdfFont.getDescent ();
				double ascent = fontSize * pdfFont.getAscent ();
				return new Rectangle (0,descent,advance,ascent);
			}
		});
	}

	private Map<PDFLayoutPDFElement,PDFDictionary> pageMap = new HashMap<PDFLayoutPDFElement,PDFDictionary> ();

	private PDFDictionary getPage (PDFLayoutPDFElement pdfElement) throws IOException {
		PDFDictionary page = pageMap.get (pdfElement);
		if (page == null) {
			page = new PDFFile (new SeekableByteArray (Util.toByteArray (pdfElement.getInput ().getInputStream ()))).getDocument ().getPage (1);
			pageMap.put (pdfElement,page);
		}
		return page;
	}
}
