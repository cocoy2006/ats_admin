package molab.test.java;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import molab.main.java.util.ChartUtil;
import molab.main.java.util.PdfUtil;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.general.DefaultPieDataset;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class ItextTester {
	
	private static String icon = "C:\\Development\\cfca\\document\\icon.png";

	public static void main(String[] args) throws Exception {
//		String pdfPath = "C:\\Development\\cfca\\document\\Report@CFCA_created.pdf";
//		createPdf(pdfPath);
		
		int width = 300, height = 300;
		DefaultPieDataset dataSet = new DefaultPieDataset();
		dataSet.setValue("中国", 19.64);
		dataSet.setValue("印度", 17.3);
		dataSet.setValue("United States", 4.54);
		dataSet.setValue("Indonesia", 3.4);
		dataSet.setValue("Brazil", 2.83);
		dataSet.setValue("Pakistan", 2.48);
		dataSet.setValue("Bangladesh", 2.38);
		File file = new File("C:\\Development\\cfca\\document\\tmp.png");
		
//		JFreeChart chart = ChartUtil.pieChart("Population", dataSet);
//		ChartRenderingInfo info = new ChartRenderingInfo( 
//                new StandardEntityCollection()); 
//        ChartUtilities.saveChartAsPNG(filename, chart, width, height, info);
//		ChartUtilities.saveChartAsPNG(filename, chart, width, height);
		ChartUtil.pieChart("人口", file, width, height, dataSet);
		
		System.out.println("Done.");
		System.exit(0);
	}
	
	public static void fillPdf(String pdfPath) throws IOException, DocumentException {
		PdfReader reader = new PdfReader(pdfPath);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        /* 将要生成的目标PDF文件名称 */   
        PdfStamper ps = new PdfStamper(reader, bos);  
        PdfContentByte under = ps.getUnderContent(1);
        
        // 添加 中文信息	
//  		BaseFont bf = BaseFont.createFont(Path.font("SIMSUN.TTC").concat(",0"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//  		Font font = new Font(bf, 12, Font.NORMAL);
        
//        float sinus = (float)Math.sin(Math.PI / 60);
//        float cosinus = (float)Math.cos(Math.PI / 60);
//        under.beginText();
//        under.setFontAndSize(bf, 12);
//        under.setTextMatrix(cosinus, sinus, -sinus, cosinus, 300, 1000);
//        under.showText("Hello");
//        under.endText();
        
//        Image image = Image.getInstance(icon);
//        image.scalePercent(0.5f);
//        image.scaleToFit(80, 80);
//        image.setAbsolutePosition(50, 1000);
//        under.addImage(image);
        
        
 		
 		/* 必须要调用这个，否则文档不会生成的 */    
        ps.setFormFlattening(true);  
        ps.close();  
        OutputStream fos = new FileOutputStream("C:\\Development\\cfca\\document\\Report@CFCA_new.pdf");  
        fos.write(bos.toByteArray());  
        fos.flush();  
        fos.close();  
        bos.close();
	}
	
	public static void createPdf(String pdfPath) throws Exception {
		// 第一步： Create a Document
		Document doc = new Document(PageSize.A4);
		// 第二 步： Get a PdfWriter instance.
		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(pdfPath));
		// 第三步：Open the Document.
		doc.open();
 
		// 第四步：添加内容
		// 添加 中文信息
//		BaseFont bf = BaseFont.createFont(Path.font("SIMSUN.TTC").concat(",0"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//		Font font = new Font(bf, 12, Font.NORMAL);
		
		// 添加 Paragraph
//		document.add(new Paragraph("Hello iText."));
//		document.add(Chunk.NEWLINE);
//		document.add(new Paragraph("这是中文：欢迎来到iText世界。", font));
		PdfUtil.addTitle(doc, "Hello iText.");
		PdfUtil.addSubTitle(doc, "这是中文：欢迎来到iText世界。");
		
		PdfPTable table = new PdfPTable(8);
        for(int aw = 0; aw < 16; aw++){
            table.addCell("hi");
        }
        doc.add(table);
        
//		Image image = Image.getInstance(icon);
//		image.setAbsolutePosition(50, 1000);
//		document.add(image);
 
		// Close the Document.
		doc.close();
	}
	
}
