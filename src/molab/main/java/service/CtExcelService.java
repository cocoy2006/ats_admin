package molab.main.java.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.logging.Logger;

import molab.main.java.component.CtReportSummaryComponent;
import molab.main.java.component.CtRunnerComponent;
import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.CtDispatcherDao;
import molab.main.java.dao.CtRunnerDao;
import molab.main.java.dao.DeviceDao;
import molab.main.java.entity.Application;
import molab.main.java.entity.CtDispatcher;
import molab.main.java.entity.CtRunner;
import molab.main.java.entity.Device;
import molab.main.java.util.Path;
import molab.main.java.util.ReportUtil;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CtExcelService {
	
	private static final Logger log = Logger.getLogger(CtExcelService.class.getName());

	@Autowired
	private CtDispatcherDao dao;
	
	@Autowired
	private DeviceDao dd;
	
	@Autowired
	private CtRunnerDao rd;
	
	@Autowired
	private ApplicationDao ad;
	
	private Application application;
	private List<CtRunner> runnerList;
	private List<Device> deviceList;
	
	public Workbook loadExcel(CtDispatcher dispatcher, Application application) {
		this.application = application;
		this.runnerList = rd.findByDispatcherId(dispatcher.getId());
		this.deviceList = dd.findAll(dispatcher.getId());
		try {
			String template = Path.xls();
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(template));
			Workbook wb = new HSSFWorkbook(fs);
			fillBasciInfoSheet(wb);
			fillDeviceInfoSheet(wb);
			fillAdaptInfoSheet(wb);
			return wb;
		} catch(Exception e) {
			log.severe(e.getMessage());
		}
		return null;
	}
		
	private void fillBasciInfoSheet(Workbook wb) {
		Sheet sheet = wb.getSheet("基本概况");
		sheet.setFitToPage(true);
		// 测试应用
		Row row = sheet.getRow(2);
		row.getCell(2).setCellValue(application.getName());
		// 测试通过率
		row.getCell(7).setCellValue(ReportUtil.loadPassrate(runnerList).concat("%"));
		// 测试终端数
		row = sheet.getRow(4);
		row.getCell(7).setCellValue(runnerList.size());
		// 安装失败
		CtReportSummaryComponent rsc = ReportUtil.loadSummary(runnerList);
		row = sheet.getRow(16);
		row.getCell(2).setCellValue(rsc.getInstallFailureCount());
		// 待优化
		row.getCell(7).setCellValue(0);
		// 启动失败
		row = sheet.getRow(18);
		row.getCell(2).setCellValue(rsc.getLoadFailureCount());
		// 通过
		row.getCell(7).setCellValue(rsc.getPassCount());
		// 卸载失败
		row = sheet.getRow(20);
		row.getCell(2).setCellValue(rsc.getUninstallFailureCount());
		// 不兼容合计
		row = sheet.getRow(22);
		row.getCell(2).setCellValue(rsc.getInstallFailureCount() 
				+ rsc.getLoadFailureCount() + rsc.getUninstallFailureCount());
		// 通过合计
		row.getCell(7).setCellValue(rsc.getPassCount());
	}
	
	private void fillDeviceInfoSheet(Workbook wb) {
		Sheet sheet = wb.getSheet("硬件信息");
		sheet.setFitToPage(true);
		int size = deviceList.size();
		if(size > 0) {
			for(int i = 0; i < deviceList.size(); i++) {
				Device device = deviceList.get(i);
				Row row = sheet.createRow(i + 2);
				row.createCell(0).setCellValue(String.valueOf(i + 1));
				row.createCell(1).setCellValue(device.getManufacturer() + "/" + device.getModel());
				row.createCell(2).setCellValue(device.getOs());
				row.createCell(3).setCellValue(String.valueOf(device.getWidth()) + "*" + String.valueOf(device.getHeight()));
			}
		}
		
	}
	
	private void fillAdaptInfoSheet(Workbook wb) {
		Sheet sheet = wb.getSheet("适配详情");
		sheet.setFitToPage(true);
		for(int i = 0; i < runnerList.size(); i++) {
			CtRunner runner = runnerList.get(i);
			Device device = deviceList.get(i);
			Row row = sheet.createRow(i + 2);
			row.createCell(0).setCellValue(String.valueOf(i + 1)); // 序号
			row.createCell(1).setCellValue(device.getManufacturer() + "/" + device.getModel());
			// 安装测试
			if(runner.getInstallTime() > 0) {
				row.createCell(2).setCellValue(" 安装成功 ");
			} else {
				row.createCell(2).setCellValue(" 安装失败 ");
			}
			// 启动测试
			if(runner.getLoadTime() > 0) {
				row.createCell(3).setCellValue(" 启动成功 "); 
			} else {
				row.createCell(3).setCellValue(" 启动失败 ");
			}
			// 卸载测试
			if(runner.getUninstallTime() > 0) {
				row.createCell(4).setCellValue(" 卸载成功 ");
			} else {
				row.createCell(4).setCellValue(" 卸载失败 ");
			}
			// 利用setRunner方法获取页面显示数据
			CtRunnerComponent rc = new CtRunnerComponent();
			rc.setRunner(runner);
			// 安装时间
			row.createCell(5).setCellValue(rc.getDisplayInstallTime());
			// 启动时间
			row.createCell(6).setCellValue(rc.getDisplayLoadTime());
			// 卸载时间
			row.createCell(7).setCellValue(rc.getDisplayUninstallTime());
			// CPU占用（均值）%
			row.createCell(8).setCellValue(rc.getDisplayAverageCpu());
			// 内存占用（均值）MB
			row.createCell(9).setCellValue(rc.getDisplayAverageMemory());
		}
	}
	
//	private ByteArrayOutputStream loadIcon() throws IOException {
//		String icon = application.getIcon();
//		byte[] bytes = new BASE64Decoder().decodeBuffer(icon);
//		ByteArrayOutputStream baso = new ByteArrayOutputStream();
//		baso.write(bytes);
//		return baso;
//	}
	
}
