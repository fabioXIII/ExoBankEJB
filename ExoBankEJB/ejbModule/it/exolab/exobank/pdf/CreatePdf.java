package it.exolab.exobank.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;


import it.exolab.exobank.model.UserTransactions;
import it.exolab.exobank.utils.Costanti;
import it.exolab.exobank.utils.FormatDate;

public class CreatePdf extends PdfPageEventHelper {

    public byte[] createPdfTable(List<UserTransactions> userTransactions) {
        
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(30f, 30f, 30f, 60f);
            document.open();

            createParagraph(document, Costanti.EXOBANK, Element.ALIGN_LEFT,
                    new Font(Font.ITALIC, 22, Font.BOLD | Font.ITALIC | Font.UNDERLINE));

            createParagraphWithInfo(document, Element.ALIGN_RIGHT, userTransactions,
                    new Font(Font.TIMES_ROMAN, 13, Font.BOLD), new Font(Font.TIMES_ROMAN, 12, Font.NORMAL));

            createParagraph(document, Costanti.RIEPILOGO_TRANSAZIONE, Element.ALIGN_CENTER,
                    new Font(Font.TIMES_ROMAN, 18, Font.BOLD));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            float[] columnWidths = { 80f, 80f, 80f, 80f, 80f };
            table.setWidths(columnWidths);

            String[] headerCells = Costanti.HEADER_CELL_DOC;
            fillHeaderCell(new Font(Font.HELVETICA, 10), headerCells, table);
            fillCell(userTransactions, table, new Font(Font.HELVETICA, 8));

            createFooterWithNumbPage(writer, document);

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    private Paragraph createParagraph(Document document, String text, int alignment, Font font)
            throws DocumentException {
        Paragraph customText = new Paragraph(text, font);
        customText.setAlignment(alignment);
        document.add(customText);
        document.add(new Paragraph(" "));
        return customText;
    }

    private void createParagraphWithInfo(Document document, int alignment, List<UserTransactions> userTransactions,
            Font titleFont, Font textFont) throws DocumentException {
    	System.out.println(userTransactions.size());
    	
        if (!userTransactions.isEmpty()) {
            UserTransactions transaction = userTransactions.get(0);
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(50);

            PdfPCell keyCell = new PdfPCell(new Phrase("Key", textFont));
            PdfPCell valueCell = new PdfPCell(new Phrase("Value", textFont));

            infoTable.addCell(keyCell);
            infoTable.addCell(valueCell);

            // Assuming you have some keys and their corresponding values
            infoTable.addCell(new PdfPCell(new Phrase("First Name", textFont)));
            infoTable.addCell(new PdfPCell(new Phrase(transaction.getFirstName(), textFont)));

            infoTable.addCell(new PdfPCell(new Phrase("Last Name", textFont)));
            infoTable.addCell(new PdfPCell(new Phrase(transaction.getLastName(), textFont)));

            infoTable.addCell(new PdfPCell(new Phrase("Bank Account ID", textFont)));
            infoTable.addCell(new PdfPCell(new Phrase(String.valueOf(transaction.getBankAccountId()), textFont)));

            infoTable.addCell(new PdfPCell(new Phrase("Balance", textFont)));
            infoTable.addCell(new PdfPCell(new Phrase(String.format("%.2f€", transaction.getBalance()), textFont)));

//            infoTable.addCell(new PdfPCell(new Phrase("Transaction Date", textFont)));
//            infoTable.addCell(new PdfPCell(new Phrase(FormatDate.formattaData(transaction.getTransactionDate()), textFont)));
//
//            infoTable.addCell(new PdfPCell(new Phrase("Transaction Type", textFont)));
//            infoTable.addCell(new PdfPCell(new Phrase(transaction.getTransactionTypeName(), textFont)));
//
//            infoTable.addCell(new PdfPCell(new Phrase("Transaction Status", textFont)));
//            infoTable.addCell(new PdfPCell(new Phrase(transaction.getTransactionStatusName(), textFont)));
//
//            infoTable.addCell(new PdfPCell(new Phrase("Target Account Number", textFont)));
//            String targetAccountNumber = (transaction.getTargetBankAccountId() != null)
//                    ? transaction.getAccountNumber()
//                    : "---";
//            infoTable.addCell(new PdfPCell(new Phrase(targetAccountNumber, textFont)));

            infoTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            document.add(infoTable);
            document.add(new Paragraph(" "));
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        Font font = new Font(Font.HELVETICA, 6);
        Phrase footer = new Phrase(Costanti.FOOTER, font);
        footer.add(new Phrase(" - Pagina " + writer.getPageNumber(), font));
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer, document.left() + 270, document.bottom() - 20, 0);
    }

    private void createFooterWithNumbPage(PdfWriter writer, Document document) {
        CreatePdf pdf = new CreatePdf();
        pdf.onEndPage(writer, document);
        writer.setPageEvent(pdf);
    }

    private PdfPCell createCell(String text, Font fontCell, int height, Color backGroundColor) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, fontCell));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(backGroundColor);
        cell.setFixedHeight(height);
        return cell;
    }

    private void fillHeaderCell(Font cellFont, String[] headerCells, PdfPTable table) {
        for (String header : headerCells) {
            table.addCell(createCell(header, cellFont, 17, new java.awt.Color(125, 124, 124)));
        }
    }

    private void fillCell(List<UserTransactions> userTransactions, PdfPTable table, Font dataFont) {
        for (int i = 0; i < userTransactions.size(); i++) {
            UserTransactions transazione = userTransactions.get(i);
            Color backgroundColor = i % 2 == 0 ? new java.awt.Color(247, 247, 247) : new java.awt.Color(212, 210, 210);
            table.addCell(createCell(FormatDate.formattaData(transazione.getTransactionDate()), dataFont, 14, backgroundColor));
            
            double importo = (transazione.getTransactionTypeName().equalsIgnoreCase("DEPOSIT"))
                    ? transazione.getAmount()
                    : -transazione.getAmount();
            
            Color textColor = (importo > 0 || transazione.getTransactionTypeName().equalsIgnoreCase("DEPOSIT"))
                    ? new Color(0, 128, 0)  // Colore verde per DEPOSIT con importo negativo
                    : new Color(255, 0, 0);  // Colore rosso per gli altri
            
            String formattedImporto = String.format("%.2f€", Math.abs(importo));
            if (transazione.getTransactionTypeName().equalsIgnoreCase("DEPOSIT")) {
                formattedImporto = "+" + formattedImporto;  // Aggiunge "+" solo per DEPOSIT                
            }
            else {
            	formattedImporto = "-" + formattedImporto;
            }

            table.addCell(createCell(String.format(formattedImporto), new Font(Font.HELVETICA, 8, Font.NORMAL, textColor), 14, backgroundColor));
            table.addCell(createCell(transazione.getTransactionStatusName(), dataFont, 14, backgroundColor));
            table.addCell(createCell(transazione.getTransactionTypeName(), dataFont, 14, backgroundColor));

            String numeroContoBeneficiario = (transazione.getTargetBankAccountId() != null)
                    ? transazione.getAccountNumber()
                    : "---";
            table.addCell(createCell(numeroContoBeneficiario, dataFont, 14, backgroundColor));
        }
    }
}
