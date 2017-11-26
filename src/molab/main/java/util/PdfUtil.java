package molab.main.java.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang.StringUtils;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfUtil {
	
	private static BaseFont baseFont;
	private static Font font10;
	
	static {
		try {
			baseFont = BaseFont.createFont(Path.font("MSYH.TTC").concat(",0"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			font10 = new Font(baseFont, 10, Font.NORMAL);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void addTitle(Document doc, String title) throws DocumentException {
		Font font = new Font(baseFont, 12, Font.NORMAL);
		doc.add(new Paragraph(title, font));
		doc.add(Chunk.NEWLINE);
	}
	
	public static void addSubTitle(Document doc, String subTitle) throws DocumentException {
		Image blueImage = Image.getInstance(3, 16, 3, 8, new byte[] { (byte)0, (byte)255, (byte)0 });
		doc.add(new Chunk(blueImage, 0, 0));
		
		Phrase p = new Phrase(subTitle, new Font(baseFont, 11, Font.NORMAL));
		doc.add(p);
		doc.add(Chunk.NEWLINE);
	}
	
	public static void addParagraph(Document doc, String text) throws DocumentException {
		Paragraph p = new Paragraph(text, new Font(baseFont, 10, Font.NORMAL));
		doc.add(p);
	}
	
	public static void addCell(PdfPTable table, String text, boolean isHeader) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setFixedHeight(55);
		if(isHeader) {
			cell.setBackgroundColor(new BaseColor(240, 240, 240));
		}
		
		String content = text;
		if(StringUtils.isBlank(text)) {
			content = "";
		}
		
		Paragraph p = new Paragraph(content, font10);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		
		table.addCell(cell);
	}
	
	public static void addCellBest(PdfPTable table, String text) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setFixedHeight(55);
		cell.setBackgroundColor(new BaseColor(91, 184, 91));
		
		String content = text;
		if(StringUtils.isBlank(text)) {
			content = "";
		}
		
		Paragraph p = new Paragraph(content, font10);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		
		table.addCell(cell);
	}
	
	public static void addCellWorst(PdfPTable table, String text) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setFixedHeight(55);
		cell.setBackgroundColor(new BaseColor(216, 82, 78));
		
		String content = text;
		if(StringUtils.isBlank(text)) {
			content = "";
		}
		
		Paragraph p = new Paragraph(content, font10);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		
		table.addCell(cell);
	}
	
	public static void addPieChart(PdfPTable table, DefaultPieDataset dataSet, String title, int width, int height) throws IOException, BadElementException {
		File file = new File(Path.temp(String.valueOf(System.currentTimeMillis()).concat(".png")));
		ChartUtil.pieChart(title, file, width, height, dataSet);
		Image image = Image.getInstance(file.getAbsolutePath());
		PdfPCell cell = new PdfPCell();
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setImage(image);
		table.addCell(cell);
	}
	
	public static void addBarChart(Document doc, DefaultCategoryDataset dataSet, String title) throws MalformedURLException, IOException, DocumentException {
		File file = new File(Path.temp(String.valueOf(System.currentTimeMillis()).concat(".png")));
		ChartUtil.barChart(title, "", "", file, (int) (doc.getPageSize().getWidth() * 0.9), 300, dataSet);
		Image image = Image.getInstance(file.getAbsolutePath());
		doc.add(image);
	}
	
}
