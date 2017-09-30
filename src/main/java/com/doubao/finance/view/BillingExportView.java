package com.doubao.finance.view;

import com.doubao.finance.util.ExcelUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class BillingExportView
        extends AbstractXlsxView
{
    private Class clazz;

    public BillingExportView(Class clazz)
    {
        this.clazz = clazz;
    }

    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        String fileName = (String)model.get("title");
        fileName = new String(fileName.getBytes(), "ISO-8859-1");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setHeader("Pragma", "No-cache");

        List dataList = (List)model.get("data");
        if (dataList.isEmpty()) {
            return;
        }
        ExcelUtils.export(workbook, dataList, this.clazz);
    }

    protected void renderWorkbook(Workbook workbook, HttpServletResponse response)
            throws IOException
    {
        super.renderWorkbook(workbook, response);
    }
}
