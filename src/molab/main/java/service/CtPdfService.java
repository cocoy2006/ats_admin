package molab.main.java.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import molab.main.java.component.CtReportAnalysisComponent;
import molab.main.java.component.CtReportSummaryComponent;
import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.CtDispatcherDao;
import molab.main.java.dao.CtRunnerDao;
import molab.main.java.dao.DeviceDao;
import molab.main.java.entity.Application;
import molab.main.java.entity.CtDispatcher;
import molab.main.java.entity.CtRunner;
import molab.main.java.entity.Device;
import molab.main.java.util.ChartUtil;
import molab.main.java.util.Molab;
import molab.main.java.util.Path;
import molab.main.java.util.PdfUtil;
import molab.main.java.util.Status;

import org.apache.commons.lang.StringUtils;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class CtPdfService {
	
	private static final Logger log = Logger.getLogger(CtPdfService.class.getName());

	@Autowired
	private CtDispatcherDao dao;
	
	@Autowired
	private DeviceDao dd;
	
	@Autowired
	private CtRunnerDao rd;
	
	@Autowired
	private ApplicationDao ad;
	
	@Autowired
	private CtRunnerService rs;
	
	@Autowired
	private LogcatService ls;
	
	public ByteArrayOutputStream loadPdf(CtDispatcher dispatcher, Application app) {
		List<CtRunner> runnerList = rs.findAll(dispatcher.getId());
		try {
			// Create a Document
			Document doc = new Document(PageSize.A3);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// Get a PdfWriter instance.
			PdfWriter.getInstance(doc, baos);
			// Open the Document.
			doc.open();
			
			// main part
			fillSummary(doc, dispatcher, app, runnerList);
//			doc.add(Chunk.NEXTPAGE);
			doc.newPage();
			fillDetail(doc, runnerList);
			fillPerformance(doc, dispatcher, runnerList);
//			doc.add(Chunk.NEXTPAGE);
			doc.newPage();
//			fillIntroduce(doc);
			// -- main part
			doc.close();
			clearTemp();
			return baos;
		} catch(Exception e) {
			log.severe(e.getMessage());
		}
		return null;
	}
	
	private void fillSummary(Document doc, CtDispatcher dispatcher, Application app, 
			List<CtRunner> runnerList) throws DocumentException, MalformedURLException, IOException {
		PdfUtil.addTitle(doc, "1. 测试概况");
		fillSummaryApp(doc, dispatcher, app);
		doc.add(Chunk.NEWLINE);
		fillSummaryResult(doc, runnerList);
		doc.add(Chunk.NEWLINE);
		fillSummaryFailureDevice(doc, dispatcher);
		doc.add(Chunk.NEWLINE);
		fillSummaryErrorLog(doc, dispatcher);
		doc.add(Chunk.NEWLINE);
	}

	private void fillSummaryApp(Document doc, CtDispatcher dispatcher, Application app) 
			throws DocumentException, MalformedURLException, IOException {
		PdfUtil.addSubTitle(doc, " 应用信息");
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100);
		// icon
		String iconPath = Path.apk(app.getMd5().concat(".png"));
		File icon = new File(iconPath);
		if(!icon.exists()) {
			iconPath = Path.apk("default.png");
		}
		Image image = Image.getInstance(iconPath);
		image.scaleAbsolute(20, 20);
        PdfPCell cell = new PdfPCell();
        cell.setRowspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setImage(image);
		table.addCell(cell);
		// text
		PdfUtil.addCell(table, "应用名", true);
		String label = app.getLabel();
		if(StringUtils.isBlank(label)) {
			label = app.getName();
		}
		PdfUtil.addCell(table, label, false);
		PdfUtil.addCell(table, "版本", true);
		PdfUtil.addCell(table, app.getVersion(), false);
		PdfUtil.addCell(table, "包名", true);
		PdfUtil.addCell(table, app.getPackagename(), false);
		PdfUtil.addCell(table, "文件大小", true);
		PdfUtil.addCell(table, Molab.parseSize(app.getSize()), false);
		PdfUtil.addCell(table, "MD5", true);
		PdfUtil.addCell(table, app.getMd5(), false);
		PdfUtil.addCell(table, "提测时间", true);
		PdfUtil.addCell(table, Molab.parseTime(dispatcher.getOprTime()), false);
        doc.add(table);
	}
	
	private void fillSummaryResult(Document doc, List<CtRunner> runnerList) 
			throws DocumentException, MalformedURLException, IOException {
		PdfUtil.addSubTitle(doc, " 结果概况");
		int width = 280, height = 280;
		DefaultPieDataset dataSet = new DefaultPieDataset();
		CtReportSummaryComponent rsc = rs.findSummary(runnerList);
		float successRate = Molab.rescale(rsc.getPassCount() * 100f / rsc.getTotal());
		float failureRate = Molab.rescale((rsc.getTotal() - rsc.getPassCount()) * 100f / rsc.getTotal());
		dataSet.setValue("通过" + successRate + "%", Float.valueOf(successRate));
		dataSet.setValue("失败" + failureRate + "%", Float.valueOf(failureRate));
		
		File file = new File(Path.temp(String.valueOf(System.currentTimeMillis()).concat(".png")));
		ChartUtil.pieChart("测试结果", file, width, height, dataSet);
		Image image = Image.getInstance(file.getAbsolutePath());
		
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		PdfPCell cell = new PdfPCell();
        cell.setRowspan(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setImage(image);
        table.addCell(cell);

        PdfUtil.addCell(table, "测试结果", true);
        PdfUtil.addCell(table, "终端数", true);
        PdfUtil.addCell(table, "测试通过", false);
        PdfUtil.addCell(table, String.valueOf(rsc.getPassCount()), false);
        PdfUtil.addCell(table, "安装未通过", false);
        PdfUtil.addCell(table, String.valueOf(rsc.getInstallFailureCount()), false);
        PdfUtil.addCell(table, "启动未通过", false);
        PdfUtil.addCell(table, String.valueOf(rsc.getLoadFailureCount()), false);
        PdfUtil.addCell(table, "卸载未通过", false);
        PdfUtil.addCell(table, String.valueOf(rsc.getUninstallFailureCount()), false);
        
        doc.add(table);
	}
	
	private void fillSummaryFailureDevice(Document doc, CtDispatcher dispatcher) 
			throws DocumentException, MalformedURLException, IOException {
//		PdfUtil.addSubTitle(doc, " 失败终端分析");
		// TODO
	}

	private void fillSummaryErrorLog(Document doc, CtDispatcher dispatcher) 
			throws DocumentException, MalformedURLException, IOException {
		PdfUtil.addSubTitle(doc, " 错误日志汇总");
		// text paragraph
		int crashCount = ls.count(dispatcher.getId(), Status.LogcatType.CRASH.getInt());
		int anrCount = ls.count(dispatcher.getId(), Status.LogcatType.ANR.getInt());
		int exceptionCount = ls.count(dispatcher.getId(), Status.LogcatType.EXCEPTION.getInt());
		String text = String.format("错误汇总：Crash %d 次；ANR %d 次；Exception %d 次。 ", 
				crashCount, anrCount, exceptionCount);
		PdfUtil.addParagraph(doc, text);
		// bar chart
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		dataSet.setValue(crashCount, "crash", "程序崩溃次数");
		dataSet.setValue(anrCount, "anr", "程序未响应次数");
		dataSet.setValue(exceptionCount, "exception", "其他异常次数");
		PdfUtil.addBarChart(doc, dataSet, "错误类别汇总");
	}
	
	private void fillDetail(Document doc, List<CtRunner> runnerList) throws DocumentException {
		PdfUtil.addTitle(doc, "2. 终端详情");
		List<CtRunner> successRunnerList = new ArrayList<CtRunner>(), 
				failureRunnerList = new ArrayList<CtRunner>();
		for(CtRunner runner : runnerList) {
			if(runner.getInstallTime() > 0 && runner.getLoadTime() > 0 && runner.getUninstallTime() > 0) {
				successRunnerList.add(runner);
			} else {
				failureRunnerList.add(runner);
			}
		}
		if(failureRunnerList.size() > 0) {
//			fillDetailFailureReason(doc, failureRunnerList);
//			doc.add(Chunk.NEWLINE);
			fillDetailFailureDeviceList(doc, failureRunnerList);
			doc.add(Chunk.NEWLINE);
		}
		if(successRunnerList.size() > 0) {
			fillDetailSuccessDeviceList(doc, successRunnerList);
			doc.add(Chunk.NEWLINE);
		}
	}
	
	private void fillDetailFailureReason(Document doc, List<CtRunner> failureRunnerList) throws DocumentException {
		PdfUtil.addSubTitle(doc, " 失败终端及原因");
		String text = String.format("共 %d 款终端出现运行失败错误，如下：", failureRunnerList.size());
		PdfUtil.addParagraph(doc, text);
		String reasonTemplate = "%s %s		%s";
		for(CtRunner runner : failureRunnerList) {
			Device device = dd.findById(runner.getDeviceId());
			String reason = null;
			if(runner.getInstallTime() == 0) {
				reason = String.format(reasonTemplate, device.getManufacturer(), device.getModel(), "安装失败");
				continue;
			}
			if(runner.getLoadTime() == 0) {
				reason = String.format(reasonTemplate, device.getManufacturer(), device.getModel(), "启动失败");
				continue;
			}
			if(runner.getUninstallTime() == 0) {
				reason = String.format(reasonTemplate, device.getManufacturer(), device.getModel(), "卸载失败");
				continue;
			}
			PdfUtil.addParagraph(doc, reason);
		}
	}
	
	private void fillDetailFailureDeviceList(Document doc, List<CtRunner> failureRunnerList) throws DocumentException {
		PdfUtil.addSubTitle(doc, " 失败终端列表");
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);
		PdfUtil.addCell(table, "品牌", true);
		PdfUtil.addCell(table, "设备型号", true);
		PdfUtil.addCell(table, "操作系统", true);
		PdfUtil.addCell(table, "分辨率", true);
		PdfUtil.addCell(table, "网络", true);
		PdfUtil.addCell(table, "失败原因", true);
		for(CtRunner runner : failureRunnerList) {
			Device device = dd.findById(runner.getDeviceId());
			PdfUtil.addCell(table, device.getManufacturer(), false);
			PdfUtil.addCell(table, device.getModel(), false);
			PdfUtil.addCell(table, device.getOs(), false);
			PdfUtil.addCell(table, device.getWidth().toString() + "*" + device.getHeight().toString(), false);
			PdfUtil.addCell(table, "WIFI", false);
			String reason = null;
			if(runner.getInstallTime() == 0) {
				reason = "安装失败";
			} else if(runner.getLoadTime() == 0) {
				reason = "启动失败";
			} else if(runner.getUninstallTime() == 0) {
				reason = "卸载失败";
			} else {
				reason = "UNKNOWN";
			}
			PdfUtil.addCell(table, reason, false);
		}
		doc.add(table);
	}
	
	private void fillDetailSuccessDeviceList(Document doc, List<CtRunner> successRunnerList) throws DocumentException {
		PdfUtil.addSubTitle(doc, " 通过终端列表");
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		PdfUtil.addCell(table, "品牌", true);
		PdfUtil.addCell(table, "设备型号", true);
		PdfUtil.addCell(table, "操作系统", true);
		PdfUtil.addCell(table, "分辨率", true);
		PdfUtil.addCell(table, "网络", true);
		for(CtRunner runner : successRunnerList) {
			Device device = dd.findById(runner.getDeviceId());
			PdfUtil.addCell(table, device.getManufacturer(), false);
			PdfUtil.addCell(table, device.getModel(), false);
			PdfUtil.addCell(table, device.getOs(), false);
			PdfUtil.addCell(table, device.getWidth().toString() + "*" + device.getHeight().toString(), false);
			PdfUtil.addCell(table, "WIFI", false);
		}
		doc.add(table);
	}
	
	private void fillPerformance(Document doc, CtDispatcher dispatcher, List<CtRunner> runnerList) throws DocumentException, IOException {
		PdfUtil.addTitle(doc, "3. 性能分析");
		CtReportAnalysisComponent rac = rs.findAnalysis(runnerList);
		fillPerformanceSummary(doc, rac);
		doc.add(Chunk.NEWLINE);
		fillPerformanceAnalysis(doc, dispatcher);
		doc.add(Chunk.NEWLINE);
		fillPerformanceWorst(doc, dispatcher);
		doc.add(Chunk.NEWLINE);
	}
	
	private void fillPerformanceSummary(Document doc, CtReportAnalysisComponent rac) throws DocumentException {
		PdfUtil.addSubTitle(doc, " 性能概况");
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);
		PdfUtil.addCell(table, "安装时间:" + rac.getAvgInstallTime() / 1000 + "s", false);
		PdfUtil.addCell(table, "启动时间:" + rac.getAvgLoadTime() / 1000 + "s", false);
		PdfUtil.addCell(table, "CPU占用:" + Molab.setAccuracy(rac.getAvgCpu(), 0) + "%", false);
		PdfUtil.addCell(table, "内存占用:" + rac.getAvgMemory() / 1024 + "MB", false);
		PdfUtil.addCell(table, "上行流量:" + rac.getAvgUptraffic() / 1024 + "KB", false);
		PdfUtil.addCell(table, "下行流量:" + rac.getAvgDowntraffic() / 1024 + "KB", false);
		Device device = rac.getMinInstallTimeDevice();
		PdfUtil.addCellBest(table, device.getManufacturer() + " " + device.getModel() + " " + device.getOs());
		device = rac.getMinLoadTimeDevice();
		PdfUtil.addCellBest(table, device.getManufacturer() + " " + device.getModel() + " " + device.getOs());
		device = rac.getMinCpuDevice();
		PdfUtil.addCellBest(table, device.getManufacturer() + " " + device.getModel() + " " + device.getOs());
		device = rac.getMinMemoryDevice();
		PdfUtil.addCellBest(table, device.getManufacturer() + " " + device.getModel() + " " + device.getOs());
		device = rac.getMinUptrafficDevice();
		PdfUtil.addCellBest(table, device.getManufacturer() + " " + device.getModel() + " " + device.getOs());
		device = rac.getMinDowntrafficDevice();
		PdfUtil.addCellBest(table, device.getManufacturer() + " " + device.getModel() + " " + device.getOs());
		device = rac.getMaxInstallTimeDevice();
		PdfUtil.addCellWorst(table, device.getManufacturer() + " " + device.getModel() + " " + device.getOs());
		device = rac.getMaxLoadTimeDevice();
		PdfUtil.addCellWorst(table, device.getManufacturer() + " " + device.getModel() + " " + device.getOs());
		device = rac.getMaxCpuDevice();
		PdfUtil.addCellWorst(table, device.getManufacturer() + " " + device.getModel() + " " + device.getOs());
		device = rac.getMaxMemoryDevice();
		PdfUtil.addCellWorst(table, device.getManufacturer() + " " + device.getModel() + " " + device.getOs());
		device = rac.getMaxUptrafficDevice();
		PdfUtil.addCellWorst(table, device.getManufacturer() + " " + device.getModel() + " " + device.getOs());
		device = rac.getMaxDowntrafficDevice();
		PdfUtil.addCellWorst(table, device.getManufacturer() + " " + device.getModel() + " " + device.getOs());
		doc.add(table);
	}
	
	private void fillPerformanceAnalysis(Document doc, CtDispatcher dispatcher) throws DocumentException, IOException {
		PdfUtil.addSubTitle(doc, " 性能分析");
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		DefaultPieDataset dataSet = new DefaultPieDataset();
		// install time
		Object[][] timeValue = {{0l, 1200l}, {1201l, 2000l}, {2001l, 3000l}, {3001l, Long.MAX_VALUE}};
		int[] result = fillPerformanceAnalysisCount(dispatcher, "installTime", timeValue);
		int total = result[0];
		float rate = 0f;
		if(total != 0) {
			rate = Molab.rescale(result[1] * 100f / total);
			dataSet.setValue("0~1.2:" + rate + "%", rate);
			rate = Molab.rescale(result[2] * 100f / total);
			dataSet.setValue("1.2~2.0:" + rate + "%", rate);
			rate = Molab.rescale(result[3] * 100f / total);
			dataSet.setValue("2.0~3.0:" + rate + "%", rate);
			rate = Molab.rescale(result[4] * 100f / total);
			dataSet.setValue("3.0以上:" + rate + "%", rate);
			PdfUtil.addPieChart(table, dataSet, "安装时间(s)", 280, 280);
		}
		// load time
		dataSet = new DefaultPieDataset();
		result = fillPerformanceAnalysisCount(dispatcher, "loadTime", timeValue);
		total = result[0]; rate = 0;
		if(total != 0) {
			rate = Molab.rescale(result[1] * 100f / total);
			dataSet.setValue("0~1.2:" + rate + "%", rate);
			rate = Molab.rescale(result[2] * 100f / total);
			dataSet.setValue("1.2~2.0:" + rate + "%", rate);
			rate = Molab.rescale(result[3] * 100f / total);
			dataSet.setValue("2.0~3.0:" + rate + "%", rate);
			rate = Molab.rescale(result[4] * 100f / total);
			dataSet.setValue("3.0以上:" + rate + "%", rate);
			PdfUtil.addPieChart(table, dataSet, "启动时间(s)", 280, 280);
		}
		// average cpu
		Object[][] percentageValue = {{0f, 5.0f}, {5.1f, 10.0f}, {10.1f, 20.0f}, {20.1f, Float.MAX_VALUE}};
		dataSet = new DefaultPieDataset();
		result = fillPerformanceAnalysisCount(dispatcher, "averageCpu", percentageValue);
		total = result[0]; rate = 0;
		if(total != 0) {
			rate = Molab.rescale(result[1] * 100f / total);
			dataSet.setValue("0~5.0:" + rate + "%", rate);
			rate = Molab.rescale(result[2] * 100f / total);
			dataSet.setValue("5.0~10.0:" + rate + "%", rate);
			rate = Molab.rescale(result[3] * 100f / total);
			dataSet.setValue("10.0~20.0:" + rate + "%", rate);
			rate = Molab.rescale(result[4] * 100f / total);
			dataSet.setValue("20.0以上:" + rate + "%", rate);
			PdfUtil.addPieChart(table, dataSet, "CPU占用率(%)", 280, 280);
		}
		// average memory
		Object[][] memoryValue = {{0l, 60l}, {61l, 100l}, {101l, 200l}, {201l, Long.MAX_VALUE}};
		dataSet = new DefaultPieDataset();
		result = fillPerformanceAnalysisCount(dispatcher, "averageMemory", memoryValue);
		total = result[0]; rate = 0;
		if(total != 0) {
			rate = Molab.rescale(result[1] * 100f / total);
			dataSet.setValue("0~60.0:" + rate + "%", rate);
			rate = Molab.rescale(result[2] * 100f / total);
			dataSet.setValue("60.0~100.0:" + rate + "%", rate);
			rate = Molab.rescale(result[3] * 100f / total);
			dataSet.setValue("100.0~200.0:" + rate + "%", rate);
			rate = Molab.rescale(result[4] * 100f / total);
			dataSet.setValue("200.0以上:" + rate + "%", rate);
			PdfUtil.addPieChart(table, dataSet, "内存利用率(MB)", 280, 280);
		}
		// downtraffic
		Object[][] trafficValue = {{0l, 512l}, {513l, 1024l}, {1025l, 2048l}, {2049l, Long.MAX_VALUE}};
		dataSet = new DefaultPieDataset();
		result = fillPerformanceAnalysisCount(dispatcher, "downtraffic", trafficValue);
		total = result[0]; rate = 0;
		if(total != 0) {
			rate = Molab.rescale(result[1] * 100f / total);
			dataSet.setValue("0~512.0:" + rate + "%", rate);
			rate = Molab.rescale(result[2] * 100f / total);
			dataSet.setValue("512.0~1024.0:" + rate + "%", rate);
			rate = Molab.rescale(result[3] * 100f / total);
			dataSet.setValue("1024.0~2048.0:" + rate + "%", rate);
			rate = Molab.rescale(result[4] * 100f / total);
			dataSet.setValue("2048以上:" + rate + "%", rate);
			PdfUtil.addPieChart(table, dataSet, "下行流量耗用(KB)", 280, 280);
		}
		// uptraffic
		dataSet = new DefaultPieDataset();
		result = fillPerformanceAnalysisCount(dispatcher, "uptraffic", trafficValue);
		total = result[0]; rate = 0;
		if(total != 0) {
			rate = Molab.rescale(result[1] * 100f / total);
			dataSet.setValue("0~512.0:" + rate + "%", rate);
			rate = Molab.rescale(result[2] * 100f / total);
			dataSet.setValue("512.0~1024.0:" + rate + "%", rate);
			rate = Molab.rescale(result[3] * 100f / total);
			dataSet.setValue("1024.0~2048.0:" + rate + "%", rate);
			rate = Molab.rescale(result[4] * 100f / total);
			dataSet.setValue("2048以上:" + rate + "%", rate);
			PdfUtil.addPieChart(table, dataSet, "上行流量耗用(KB)", 280, 280);
		}
		// end
		doc.add(table);
	}
	
	/**
	 * @return int[] {total, c1, c2, c3, c4, ...}*/
	private int[] fillPerformanceAnalysisCount(CtDispatcher dispatcher, String propertyName, Object[][] value) {
		int length = value.length, total = 0;
		int[] result = new int[length + 1];
		for(int i = 0; i < length; i++) {
			Object[] scale = value[i];
			int count = countSql(dispatcher.getId(), propertyName, scale[0], scale[1]);
			result[i + 1] = count;
			total += count;
		}
		result[0] = total;
		return result;
	}
	
	private int countSql(final int dispatcherId, final String propertyName, final Object minValue, final Object maxValue) {
//		final String sql = "select count(*) from CtRunner where dispatcherId=? and " + propertyName + " between ? and ?";
//		Object object = dao.getHibernateTemplate().execute(new HibernateCallback() {
//
//			@Override
//			public Object doInHibernate(Session session) throws HibernateException, SQLException {
//				return session.createQuery(sql)
//						.setParameter(0, dispatcherId)
//						.setParameter(1, minValue)
//						.setParameter(2, maxValue).uniqueResult();
//			}
//			
//		});
//		if(object != null) {
//			return (int) (long) object;
//		}
		String hql = "from CtRunner where dispatcherId=? and " + propertyName + " between ? and ?";
		List<CtRunner> list = rd.find(hql, dispatcherId, minValue, maxValue);
		if(list != null) {
			return list.size();
		}
		return 0;
	}
	
	private void fillPerformanceWorst(Document doc, CtDispatcher dispatcher) throws DocumentException, MalformedURLException, IOException {
		PdfUtil.addSubTitle(doc, " 性能最差机型");
		// install time
		List<CtRunner> runnerList = rs.findWorstFive(dispatcher.getId(), "installTime");
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		for(int i = 0; i < 5; i++) {
			CtRunner runner = runnerList.get(i);
			Device device = dd.findById(runner.getDeviceId());
			String key = device.getManufacturer() + " " + device.getModel() + " " + device.getOs();
			dataSet.setValue(runner.getInstallTime() / 1000, key, device.getModel());
		}
		PdfUtil.addBarChart(doc, dataSet, "安装耗时最差5款(s)");
		// load time
		runnerList = rs.findWorstFive(dispatcher.getId(), "loadTime");
		dataSet = new DefaultCategoryDataset();
		for(int i = 0; i < 5; i++) {
			CtRunner runner = runnerList.get(i);
			Device device = dd.findById(runner.getDeviceId());
			String key = device.getManufacturer() + " " + device.getModel() + " " + device.getOs();
			dataSet.setValue(runner.getLoadTime() / 1000, key, device.getModel());
		}
		PdfUtil.addBarChart(doc, dataSet, "启动耗时最差5款(s)");
		// average cpu
		runnerList = rs.findWorstFive(dispatcher.getId(), "averageCpu");
		dataSet = new DefaultCategoryDataset();
		for(int i = 0; i < 5; i++) {
			CtRunner runner = runnerList.get(i);
			Device device = dd.findById(runner.getDeviceId());
			String key = device.getManufacturer() + " " + device.getModel() + " " + device.getOs();
			dataSet.setValue(runner.getAverageCpu(), key, device.getModel());
		}
		PdfUtil.addBarChart(doc, dataSet, "CPU利用率最高5款(%)");
		// average memory
		runnerList = rs.findWorstFive(dispatcher.getId(), "averageMemory");
		dataSet = new DefaultCategoryDataset();
		for(int i = 0; i < 5; i++) {
			CtRunner runner = runnerList.get(i);
			Device device = dd.findById(runner.getDeviceId());
			String key = device.getManufacturer() + " " + device.getModel() + " " + device.getOs();
			dataSet.setValue(runner.getAverageMemory() / 1024, key, device.getModel());
		}
		PdfUtil.addBarChart(doc, dataSet, "内存使用最高5款(MB)");
		// downtraffic
		runnerList = rs.findWorstFive(dispatcher.getId(), "downtraffic");
		dataSet = new DefaultCategoryDataset();
		for(int i = 0; i < 5; i++) {
			CtRunner runner = runnerList.get(i);
			Device device = dd.findById(runner.getDeviceId());
			String key = device.getManufacturer() + " " + device.getModel() + " " + device.getOs();
			dataSet.setValue(runner.getDowntraffic() / 1024, key, device.getModel());
		}
		PdfUtil.addBarChart(doc, dataSet, "下行流量耗用最多5款(KB)");
		// uptraffic
		runnerList = rs.findWorstFive(dispatcher.getId(), "uptraffic");
		dataSet = new DefaultCategoryDataset();
		for(int i = 0; i < 5; i++) {
			CtRunner runner = runnerList.get(i);
			Device device = dd.findById(runner.getDeviceId());
			String key = device.getManufacturer() + " " + device.getModel() + " " + device.getOs();
			dataSet.setValue(runner.getUptraffic() / 1024, key, device.getModel());
		}
		PdfUtil.addBarChart(doc, dataSet, "上行流量耗用最多5款(KB)");
	}
	
	private void fillIntroduce(Document doc) throws DocumentException {
		PdfUtil.addTitle(doc, "4. CFCA移动质量中心");
		// TODO
	}
	
	private void clearTemp() {
		// TODO
	}
	
}
