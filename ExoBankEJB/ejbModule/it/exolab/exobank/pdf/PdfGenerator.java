package it.exolab.exobank.pdf;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import it.exolab.exobank.model.Transactions;
import it.exolab.exobank.model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfGenerator {

    public byte[] generatePdf(List<Transactions> transazioni , User user) throws DocumentException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Document document = new Document();
        
        PdfWriter.getInstance(document, byteArrayOutputStream);

        document.open();
        
        String introduction = "Transaction list of the user: " + user.getFirstName() + " " + user.getLastName();
        document.add(new Paragraph(introduction));

        document.add(new Paragraph("Transactions list:"));

        for (Transactions t : transazioni) {
            String targetBankAccountInfo = (t.getTargetBankAccountID() != null)
                    ? String.valueOf(t.getTargetBankAccountID().getBankAccountID())
                    : "N/A";
            
            
          
            String transactionInfo =String.format("ID: %s ,BankAccountID: %d, Status: %s, Type: %s, Date: %s,  Target: %s, Amount: %s € " , 
                    t.getTransactionID(),
                    t.getBankAccountID().getBankAccountID(),
                    t.getTransactionStatusID().getTransactionStatusName(),
                    t.getTransactionTypeID().getTransactionTypeName(),
                    t.getTransactionDate(),
                    targetBankAccountInfo,
                    (t.getAmount() % 1 == 0) ? String.format("%.0f", t.getAmount()) : String.format("%.2f", t.getAmount()));
            document.add(new Paragraph(transactionInfo));
        }
           

        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
