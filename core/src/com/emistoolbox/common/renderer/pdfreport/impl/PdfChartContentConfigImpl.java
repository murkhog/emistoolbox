package com.emistoolbox.common.renderer.pdfreport.impl;

import com.emistoolbox.common.results.TableMetaResult;
import java.io.Serializable;

public class PdfChartContentConfigImpl extends PdfMetaResultContentConfigImpl<TableMetaResult> implements Serializable
{
    private int chartType;

    public int getChartType()
    {
        return this.chartType;
    }

    public void setChartType(int chartType)
    {
        this.chartType = chartType;
    }
}
