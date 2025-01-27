package com.emistoolbox.server.renderer.pdfreport.impl;

import com.emistoolbox.server.renderer.pdfreport.PdfContentVisitor;
import com.emistoolbox.server.renderer.pdfreport.PdfImageContent;

import es.jbauer.lib.io.IOInput;

public class PdfImageContentImpl extends PdfContentBase implements PdfImageContent
{
    private IOInput file;

    public PdfImageContentImpl(IOInput file)
    { this.file = file; } 

    public IOInput getFile()
    { return file; }

	@Override
	public <T> T accept(PdfContentVisitor<T> visitor) 
	{ return visitor.visit(this); }
}
