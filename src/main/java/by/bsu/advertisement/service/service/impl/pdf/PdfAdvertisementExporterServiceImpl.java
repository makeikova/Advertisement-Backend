package by.bsu.advertisement.service.service.impl.pdf;

import by.bsu.advertisement.service.model.Advertisement;
import by.bsu.advertisement.service.model.Person;
import by.bsu.advertisement.service.repository.AdvertisementRepository;
import by.bsu.advertisement.service.service.AdvertisementService;
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

@Service("advertisementExporter")
@RequiredArgsConstructor
public class PdfAdvertisementExporterServiceImpl implements PdfExporterService {

    private final AdvertisementService advertisementService;

    @Override
    public void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.RED);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Advertisement ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Title", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Description", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("IsAppear", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Image URL", font));
        table.addCell(cell);
    }

    @Override
    public void writeTableData(PdfPTable table) {
        for (Advertisement advertisement : advertisementService.getAllWithoutAppear()) {
            table.addCell(String.valueOf(advertisement.getId()));
            table.addCell(advertisement.getTitle());
            table.addCell(advertisement.getDescription());
            table.addCell(String.valueOf(advertisement.getIsAppear()));
            table.addCell(advertisement.getImageUrl());
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

        Paragraph p = new Paragraph("List of Advertisement", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 2.0f, 3.0f, 2.0f, 4.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }
}
