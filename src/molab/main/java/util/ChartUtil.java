package molab.main.java.util;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ChartUtil {

	private static final String FONT_CN = "微软雅黑";
	
	public static void pieChart(String title, 
			File file, int width, int height,
			DefaultPieDataset dataSet) throws IOException {
		//创建主题样式  
        StandardChartTheme theme = new StandardChartTheme("CN");  
        //设置标题字体  
        theme.setExtraLargeFont(new Font(FONT_CN, Font.PLAIN, 10));  
        //设置轴向字体  
        theme.setLargeFont(new Font(FONT_CN, Font.CENTER_BASELINE, 10));  
        //设置图例字体  
        theme.setRegularFont(new Font(FONT_CN, Font.CENTER_BASELINE, 10));
        //应用主题样式  
        ChartFactory.setChartTheme(theme);
        // create chart
        JFreeChart chart = ChartFactory.createPieChart(title, dataSet, true, true, false);
        // chart background invisible
        chart.setBorderVisible(false);  
        chart.setBackgroundPaint(null);  
        chart.setBackgroundImageAlpha(0.0f);
        // plot color
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDrawingSupplier(new ChartDrawingSupplier());
        // save as png
        ChartRenderingInfo info = new ChartRenderingInfo( 
              new StandardEntityCollection()); 
        ChartUtilities.saveChartAsPNG(file, chart, width, height, info);
	}
	
	public static void barChart(String title, String xTitle, String yTitle, 
			File file, int width, int height,
			DefaultCategoryDataset dataSet) throws IOException {
		//创建主题样式  
        StandardChartTheme theme = new StandardChartTheme("CN");  
        //设置标题字体  
        theme.setExtraLargeFont(new Font(FONT_CN, Font.PLAIN, 10));  
        //设置轴向字体  
        theme.setLargeFont(new Font(FONT_CN, Font.CENTER_BASELINE, 10));  
        //设置图例字体  
        theme.setRegularFont(new Font(FONT_CN, Font.CENTER_BASELINE, 10));  
        //应用主题样式  
        ChartFactory.setChartTheme(theme);
        // create chart
        JFreeChart chart = ChartFactory.createBarChart(title, xTitle, yTitle,
				dataSet, PlotOrientation.VERTICAL, true, true, false);
        // chart background invisible
        chart.setBorderVisible(false);  
        chart.setBackgroundPaint(null);  
        chart.setBackgroundImageAlpha(0.0f);
        // plot background invisible
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDrawingSupplier(new ChartDrawingSupplier());
        // save as png
        ChartRenderingInfo info = new ChartRenderingInfo( 
                new StandardEntityCollection()); 
        ChartUtilities.saveChartAsPNG(file, chart, width, height, info);
	}
	
}
