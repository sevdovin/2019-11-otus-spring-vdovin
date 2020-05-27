package ru.otus.svdovin.employmenthistory.controller;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import ru.otus.svdovin.employmenthistory.dto.CompanyDto;
import ru.otus.svdovin.employmenthistory.dto.EmployeeDto;
import ru.otus.svdovin.employmenthistory.dto.RecordDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class EmployeeBookView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        Font catFont = FontFactory.getFont("font/times-rus.ttf", "CP1251", BaseFont.EMBEDDED, 18);
        Font subFont = FontFactory.getFont("font/times-rus.ttf", "CP1251", BaseFont.EMBEDDED, 16);
        Font headFont = FontFactory.getFont("font/times-rus.ttf", "CP1251", BaseFont.EMBEDDED, 14);
        Font itemFont = FontFactory.getFont("font/times-rus.ttf", "CP1251", BaseFont.EMBEDDED, 10);

        CompanyDto company = (CompanyDto) model.get("company");
        EmployeeDto employee = (EmployeeDto) model.get("employee");
        List<RecordDto> records = (List<RecordDto>) model.get("records");

        Paragraph preface = new Paragraph();
        preface.setAlignment(Element.ALIGN_CENTER);

        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Трудовая книжка", catFont));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Предприятие: " + company.getCompanyName(), subFont));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Сотрудник: " + employee.getLastName() + " " + employee.getFirstName() + " " + employee.getMiddleName(), subFont));
        addEmptyLine(preface, 1);
        document.add(preface);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new int[] {2, 2, 5, 4, 3, 2});

        PdfPCell hcell;
        hcell = new PdfPCell(new Phrase("Дата", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Тип записи", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Трудовая функция", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Код выполняемой функции", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Причина увольнения", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Отмена", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        for (RecordDto record : records) {
            PdfPCell cell;

            cell = new PdfPCell(new Phrase(record.getDate().toString(), itemFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(record.getTypeName(), itemFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(record.getPosition(), itemFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(record.getCode(), itemFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(record.getReason(), itemFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(record.getCancel(), itemFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        document.add(table);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
