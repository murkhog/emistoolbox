package com.emistoolbox.common.model.analysis.impl;

import com.emistoolbox.common.excelMerge.ExcelReportConfig;
import com.emistoolbox.common.model.analysis.EmisIndicator;
import com.emistoolbox.common.model.analysis.EmisReportConfig;
import com.emistoolbox.common.model.meta.EmisMetaEnum;
import com.emistoolbox.common.model.meta.EmisMetaGroupEnum;
import com.emistoolbox.common.renderer.pdfreport.PdfReportConfig;
import com.emistoolbox.common.util.NamedUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportConfig implements EmisReportConfig, Serializable
{
    private static final long serialVersionUID = 1L;
    private List<EmisMetaGroupEnum> groupEnums = new ArrayList<EmisMetaGroupEnum>(); 
    private List<EmisIndicator> indicators = new ArrayList<EmisIndicator>();
    private List<PdfReportConfig> reports = new ArrayList<PdfReportConfig>();
    private List<ExcelReportConfig> excelReports = new ArrayList<ExcelReportConfig>(); 
    private int defaultDateIndex = 0;

    public List<EmisIndicator> getIndicators()
    { return this.indicators; }

    public int getDefaultDateIndex()
    { return this.defaultDateIndex; }

    public void setDefaultDateIndex(int dateIndex)
    { this.defaultDateIndex = dateIndex; }

    public void setIndicators(List<EmisIndicator> indicator)
    { this.indicators = indicator; }

    public List<PdfReportConfig> getReports()
    { return this.reports; }

    public void setReports(List<PdfReportConfig> reports)
    { this.reports = reports; }

    public List<ExcelReportConfig> getExcelReports()
    { return excelReports; } 

    public void setExcelReports(List<ExcelReportConfig> reports)
    { excelReports = reports; } 
    
    public List<EmisMetaGroupEnum> getGroupEnums()
    { return groupEnums; }

    public List<EmisMetaGroupEnum> getGroupEnums(EmisMetaEnum baseEnum)
    {
    	List<EmisMetaGroupEnum> result = new ArrayList<EmisMetaGroupEnum>(); 
    	for (EmisMetaGroupEnum groupEnum : groupEnums)
    	{
    		if (NamedUtil.sameName(groupEnum.getBaseEnum(), baseEnum))
    			result.add(groupEnum); 
    	}
    	
    	return result; 
    }

    public void setGroupEnums(List<EmisMetaGroupEnum> groupEnums)
    { this.groupEnums = groupEnums; }
}
