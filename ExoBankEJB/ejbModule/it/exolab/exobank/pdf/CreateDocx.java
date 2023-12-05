//package it.exolab.exobank.pdf;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
//import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
//import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
//import org.apache.poi.xwpf.usermodel.XWPFTable;
//import org.apache.poi.xwpf.usermodel.XWPFTableCell;
//import org.apache.poi.xwpf.usermodel.XWPFTableRow;
//import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
//
//import it.exolab.exobank.model.BankAccount;
//import it.exolab.exobank.model.Transactions;
//import it.exolab.exobank.model.User;
//import it.exolab.exobank.utils.Costanti;
//import it.exolab.exobank.utils.FormatDate;
//import java.io.ByteArrayOutputStream;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//
//public class CreateDocx {
//
//	public byte[] createDocxFile(List<Transactions> transazioni) {
//
//		try {
//
//			try (XWPFDocument document = new XWPFDocument();
//					ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//
//
//				createParagraph(document, true, true, 24, "Roman", ParagraphAlignment.LEFT, UnderlinePatterns.SINGLE,
//						Costanti.EXOBANK);
//
//				createParagraphWithInfo(document, ParagraphAlignment.RIGHT, transazioniList);
//
//				createParagraph(document, true, false, 18, "Modern", ParagraphAlignment.LEFT, UnderlinePatterns.NONE,
//						Costanti.RIEPILOGO_TRANSAZIONE);
//
//				XWPFTable table = document.createTable(transazioni.getData().size() + 1, 5);
//
//				table.setWidth("100%");
//
//				XWPFTableRow titoloHeader = table.getRow(0);
//
//				String[] headers = Costanti.HEADER_CELL_DOC;
//
//				fillHeaderCell(titoloHeader, headers);
//
//				fillCell(transazioniList, table);
//
//				createFooterWithNumbPage(document, Costanti.FOOTER);
//
//				document.write(outputStream);
//				return outputStream.toByteArray();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	private XWPFParagraph createParagraph(XWPFDocument document, boolean bold, boolean italic, int size, String family,
//			ParagraphAlignment align, UnderlinePatterns underLine, String text) {
//
//		XWPFParagraph paragraph = document.createParagraph();
//		paragraph.setAlignment(align);
//		XWPFRun run = paragraph.createRun();
//		run.setBold(bold);
//		run.setFontSize(size);
//		run.setFontFamily(family);
//		run.setText(text);
//		run.setItalic(italic);
//		run.setUnderline(underLine);
//		run.addBreak();
//		return paragraph;
//	}
//
//	private void createParagraphWithInfo(XWPFDocument document, ParagraphAlignment align,
//			List<Transactions> transazioniList) {
//		XWPFParagraph infoParagraph = document.createParagraph();
//		infoParagraph.setAlignment(align);
//		XWPFRun infoRun = infoParagraph.createRun();
//		infoRun.setFontSize(13);
//		User utenteModel = transazioniList.get(0).getContoCorrente().getUtente();
//		BankAccount contoModel = transazioniList.get(0).getContoCorrente();
//		infoRun.setBold(true);
//		infoRun.setText(Costanti.DETTAGLI_TITOLARE);
//		infoRun.addCarriageReturn();
//		XWPFRun infoRun1 = infoParagraph.createRun();
//		infoRun1.setFontSize(12);
//		infoRun1.setBold(false);
//		infoRun1.setText("Nome: " + utenteModel.getNome());
//		infoRun1.addCarriageReturn();
//		infoRun1.setText("Cognome: " + utenteModel.getCognome());
//		infoRun1.addCarriageReturn();
//		infoRun1.setText("Numero conto: " + contoModel.getNumeroConto());
//		infoRun1.addCarriageReturn();
//		infoRun1.setText("Saldo: " + String.format("%.2f", contoModel.getSaldo()) + "€");
//		infoRun1.addCarriageReturn();
//		infoRun1.setText("Data: " + LocalDate.now().toString());
//		infoRun1.addBreak();
//		infoRun1.addBreak();
//	}
//
//	private void createFooterWithNumbPage(XWPFDocument document, String text) {
//
//		CTP ctp = CTP.Factory.newInstance();
//
//		CTText txt = ctp.addNewR().addNewT();
//		txt.setStringValue(text);
//		txt.setSpace(SpaceAttribute.Space.PRESERVE);
//
//		CTP ctp1 = CTP.Factory.newInstance();
//
//		CTText txt1 = ctp1.addNewR().addNewT();
//		txt1.setStringValue("Pag. ");
//		txt1.setSpace(SpaceAttribute.Space.PRESERVE);
//
//		CTR run = ctp1.addNewR();
//		run.addNewFldChar().setFldCharType(STFldCharType.BEGIN);
//		run.addNewInstrText().setStringValue(" PAGE ");
//		run.addNewFldChar().setFldCharType(STFldCharType.END);
//
//		txt1 = ctp1.addNewR().addNewT();
//		txt1.setStringValue(" of ");
//		txt1.setSpace(SpaceAttribute.Space.PRESERVE);
//
//		run = ctp1.addNewR();
//		run.addNewFldChar().setFldCharType(STFldCharType.BEGIN);
//		run.addNewInstrText().setStringValue(" NUMPAGES ");
//		run.addNewFldChar().setFldCharType(STFldCharType.END);
//
//		XWPFParagraph parFooter = new XWPFParagraph(ctp, document);
//		parFooter.setSpacingBefore(200);
//		parFooter.setAlignment(ParagraphAlignment.CENTER);
//
//		XWPFParagraph parNumbPage = new XWPFParagraph(ctp1, document);
//		parNumbPage.setAlignment(ParagraphAlignment.RIGHT);
//
//		XWPFHeaderFooterPolicy policy = document.getHeaderFooterPolicy();
//		if (policy == null) {
//			policy = document.createHeaderFooterPolicy();
//		}
//		policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, new XWPFParagraph[] { parFooter, parNumbPage });
//	}	
//	
//	
//	private XWPFTableRow createTableRow(XWPFTable table, int index) {		
//		XWPFTableRow row = table.getRow(index);	
//		row.setHeight(10);
//		return row;		
//	}
//	
//	
//	private XWPFTableCell createTableCell(XWPFTableRow tableRow, int index, String color) {		
//		XWPFTableCell cell = tableRow.getCell(index);
//		cell.setColor(color);
//		return cell;		
//	}
//	
//	
//	private XWPFRun createTableRun(XWPFTableCell cell, String text, int size, ParagraphAlignment align) {	
//		XWPFParagraph par = cell.addParagraph();
//		par.setAlignment(align);
//		XWPFRun run = par.createRun();	
//		run.setText(text);		
//		run.setFontSize(size);	
//		return run;		
//	}
//
//	
//	private void fillHeaderCell(XWPFTableRow titoloHeader, String[] headers) {
//		for (int i = 0; i < headers.length; i++) {			
//			XWPFTableCell cell = createTableCell(titoloHeader, i, "8C8989");	
//			createTableRun(cell, headers[i], 9, ParagraphAlignment.CENTER);
//		}
//	}
//
//	private void fillCell(List<Transactions> transazioniList, XWPFTable table) {
//		for (int i = 0 ; i < transazioniList.size(); i++) {
//			String color = (i % 2 == 0) ? "F7F5F5" : "D1D1D1";
//			XWPFTableRow row = createTableRow(table, i + 1);			
//			XWPFTableCell cell0 = createTableCell(row, 0, color);					
//			XWPFRun run = createTableRun(cell0, FormatDate.formattaData(transazioniList.get(i).getDataTransazione()), 8, ParagraphAlignment.CENTER);
//			XWPFTableCell cell1 = createTableCell(row, 1, color);
//			run = createTableRun(cell1, String.format("%.2f€", (transazioniList.get(i).getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_DEPOSITO) ? transazioniList.get(i).getImporto() : -transazioniList.get(i).getImporto()), 8, ParagraphAlignment.CENTER);
//			run.setColor((transazioniList.get(i).getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_DEPOSITO) ? "2F9C08" : "FF0000");
//			XWPFTableCell cell2 = createTableCell(row, 2, color);
//			run = createTableRun(cell2, transazioniList.get(i).getStatoTransazione().getStatoTransazione(), 8, ParagraphAlignment.CENTER);
//			XWPFTableCell cell3 = createTableCell(row, 3, color);
//			run = createTableRun(cell3, transazioniList.get(i).getTipoTransazione().getTipoTransazione(), 8, ParagraphAlignment.CENTER);
//			XWPFTableCell cell4 = createTableCell(row, 4, color);
//			run = createTableRun(cell4, (transazioniList.get(i).getContoCorrenteBeneficiario() != null) ? transazioniList.get(i).getContoCorrenteBeneficiario().getNumeroConto() : "---", 8, ParagraphAlignment.CENTER);
//
//		}
//
//	}
//}
//
