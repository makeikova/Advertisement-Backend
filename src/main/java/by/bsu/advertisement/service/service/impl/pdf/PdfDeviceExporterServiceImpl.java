package by.bsu.advertisement.service.service.impl.pdf;

import by.bsu.advertisement.service.model.Device;
import by.bsu.advertisement.service.model.Person;
import by.bsu.advertisement.service.service.DeviceService;
import by.bsu.advertisement.service.service.PdfExporterService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

@Service("deviceExporter")
@RequiredArgsConstructor
public class PdfDeviceExporterServiceImpl implements PdfExporterService {

    private final DeviceService deviceService;

    @Override
    public void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Device ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Title", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Impression Per Hour", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Is Active", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Owner", font));
        table.addCell(cell);
    }

    @Override
    public void writeTableData(PdfPTable table) {
        for (Device device : deviceService.getAll()) {
            table.addCell(String.valueOf(device.getId()));
            table.addCell(device.getTitle());
            table.addCell(String.valueOf(device.getImpressionPerHour()));
            table.addCell(String.valueOf(device.getIsActive()));
            table.addCell(String.valueOf(device.getPerson().getUsername()));
        }
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Users", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }
}
