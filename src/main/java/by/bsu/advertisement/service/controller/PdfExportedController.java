package by.bsu.advertisement.service.controller;

import by.bsu.advertisement.service.service.PdfExporterService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/pdf")
public class PdfExportedController {

    private final PdfExporterService pdfExporterUserService;
    private final PdfExporterService pdfExporterAdvertisementService;
    private final PdfExporterService pdfExporterDeviceService;

    public PdfExportedController(@Qualifier("userExporter") PdfExporterService pdfExporterService,
                                 @Qualifier("advertisementExporter") PdfExporterService pdfExporterAdvertisementService,
                                 @Qualifier("deviceExporter") PdfExporterService pdfExporterDeviceService) {
        this.pdfExporterUserService = pdfExporterService;
        this.pdfExporterAdvertisementService = pdfExporterAdvertisementService;
        this.pdfExporterDeviceService = pdfExporterDeviceService;
    }

    @GetMapping("/users")
    public void exportUsersToPDF(HttpServletResponse response) throws DocumentException, IOException {
        PdfExporterService.defaultExport(pdfExporterUserService, response, "users");
    }

    @GetMapping("/advertisement")
    public void exportAdvertisementToPDF(HttpServletResponse response) throws IOException {
        PdfExporterService.defaultExport(pdfExporterAdvertisementService, response,"advertisements");
    }

    @GetMapping("/device")
    public void exportDeviceToPDF(HttpServletResponse response) throws IOException {
        PdfExporterService.defaultExport(pdfExporterDeviceService, response, "devices");
    }
}
