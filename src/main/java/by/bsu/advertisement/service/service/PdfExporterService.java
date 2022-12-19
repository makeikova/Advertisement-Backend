package by.bsu.advertisement.service.service;

import com.lowagie.text.pdf.PdfPTable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface PdfExporterService {
    void writeTableHeader(PdfPTable table);
    void writeTableData(PdfPTable table);
    void export(HttpServletResponse response) throws IOException;

    static void defaultExport(PdfExporterService service, HttpServletResponse response, String startFilename) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=%s_%s.pdf", startFilename, currentDateTime);
        response.setHeader(headerKey, headerValue);
        service.export(response);
    }
}
