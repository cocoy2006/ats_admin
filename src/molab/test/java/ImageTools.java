package molab.test.java;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageTools {
	/**
	 * 读取图片文件
	 * @param file
	 * @return
	 */
	public static BufferedImage readImage(File file){
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	/**
	 * 获得信息指纹
	 * @param pixels 灰度值矩阵
	 * @param avg 平均值
	 * @return 返回01串的十进制表示
	 */
	public static int getFingerPrint(double[][] pixels, double avg) {
		
		int width = pixels[0].length;
		int height = pixels.length;
		byte[] bytes = new byte[height*width];
		int count = 0;
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(pixels[i][j] >= avg){
					bytes[i*height + j] = 1;
				}else {
					bytes[i*height + j] = 0;
				}
			}
		}
	    int fingerprint = 0;
	    for(int i = 0; i < bytes.length; i++){
	    	fingerprint += (bytes[bytes.length-i-1] << i);
	    }
		return fingerprint;
	}
	/**
	 * 缩小图片
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage reduceSize(BufferedImage image, int width,
			int height) {
		
		BufferedImage new_image = null;
		double width_times = (double) width / image.getWidth();
		double height_times = (double) height / image.getHeight();
		if (image.getType() == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = image.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(width,height);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			new_image = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			new_image = new BufferedImage(width, height, image.getType());
		Graphics2D g = new_image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(image, AffineTransform.getScaleInstance(width_times, height_times));
		g.dispose();
		return new_image;
	}
	/**
	 * 得到平均值
	 * @param pixels 灰度值矩阵
	 * @return
	 */
	public static double getAverage(double[][] pixels) {
		int width = pixels[0].length;
		int height = pixels.length;
		int count = 0;
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				count += pixels[i][j];
			}
		}
		return count / (width*height);
	}

	/**
	 * 得到灰度值
	 * @param image
	 * @return
	 */
	public static double[][] getGrayValue(BufferedImage image) {
		int width = image.getWidth();  
	    int height = image.getHeight();  
		double[][] pixels = new double[height][width];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
	        	pixels[i][j] = computeGrayValue(image.getRGB(i, j)); 
	        }  
	    }  
		return pixels;
	}
	/**
	 * 计算灰度值
	 * @param pixels
	 * @return
	 */
	public static double computeGrayValue(int pixel) {
		int red = (pixel >> 16) & 0xFF;
		int green = (pixel >> 8) & 0xFF;
		int blue = (pixel) & 255;
		return  0.3 * red + 0.59 * green + 0.11 * blue;
	}
}
