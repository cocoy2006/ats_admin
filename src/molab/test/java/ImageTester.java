package molab.test.java;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;

import molab.main.java.util.ImageUtil;

public class ImageTester {

	public static void main(String[] args) throws IOException {
		String path = "C:\\Development\\workspace_ats\\.metadata\\.me_tcat7\\webapps\\admin\\upload\\csSs\\";
//		String path1 = path + "52.1.png";
		String path2 = path + "52.1.png";
//		File file1 = new File(path1);
		File file2 = new File(path2);
//		BufferedImage image1 = ImageIO.read(file1);
		BufferedImage image2 = ImageIO.read(file2);
//		image1 = ImageUtil.zoom(image1, image1.getWidth() / 2, image2.getHeight() / 2);
//		image2 = ImageUtil.zoom(image2, image1.getWidth(), image1.getHeight());
//		System.out.println(ImageUtil.matches(image1, image2));
		
		Image image = image2.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		ImageIO.write((RenderedImage) image, "png", new File("C:\\Development\\a.png"));
		
//		ImageTester tester = new ImageTester();
//		int orgin_fingerprint = tester.getFingerPrint(image1);
//		int compared_fingerprint = tester.getFingerPrint(image2);
//		int different_num = tester.compareFingerPrint(orgin_fingerprint,compared_fingerprint);
//		System.out.println(different_num);
		
//		compareImage(path1, path2);
//		System.out.println(modelMatch(image1, image2));
		
		System.out.println("Done.");
		System.exit(0);
	}

	
	// 改变成二进制码  
    public static String[][] getPX(String args) {  
        int[] rgb = new int[3];  
  
        File file = new File(args);  
        BufferedImage bi = null;  
        try {  
            bi = ImageIO.read(file);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        int width = bi.getWidth();  
        int height = bi.getHeight();  
        int minx = bi.getMinX();  
        int miny = bi.getMinY();  
        String[][] list = new String[width][height];  
        for (int i = minx; i < width; i++) {  
            for (int j = miny; j < height; j++) {  
                int pixel = bi.getRGB(i, j);  
                rgb[0] = (pixel & 0xff0000) >> 16;  
                rgb[1] = (pixel & 0xff00) >> 8;  
                rgb[2] = (pixel & 0xff);  
                list[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];  
  
            }  
        }  
        return list;  
  
    }  
      
    public static void compareImage(String imgPath1, String imgPath2){  
        String[] images = {imgPath1, imgPath2};  
        if (images.length == 0) {  
            System.out.println("Usage >java BMPLoader ImageFile.bmp");  
            System.exit(0);  
        }  
  
        // 分析图片相似度 begin  
        String[][] list1 = getPX(images[0]);  
        String[][] list2 = getPX(images[1]);  
        int xiangsi = 0;  
        int busi = 0;  
        int i = 0, j = 0;  
        for (String[] strings : list1) {  
            if ((i + 1) == list1.length) {  
                continue;  
            }  
            for (int m=0; m<strings.length; m++) {  
                try {  
                    String[] value1 = list1[i][j].toString().split(",");  
                    String[] value2 = list2[i][j].toString().split(",");  
                    int k = 0;  
                    for (int n=0; n<value2.length; n++) {  
                        if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {  
                            xiangsi++;  
                        } else {  
                            busi++;  
                        }  
                    }  
                } catch (RuntimeException e) {  
                    continue;  
                }  
                j++;  
            }  
            i++;  
        }  
  
        list1 = getPX(images[1]);  
        list2 = getPX(images[0]);  
        i = 0;  
        j = 0;  
        for (String[] strings : list1) {  
            if ((i + 1) == list1.length) {  
                continue;  
            }  
            for (int m=0; m<strings.length; m++) {  
                try {  
                    String[] value1 = list1[i][j].toString().split(",");  
                    String[] value2 = list2[i][j].toString().split(",");  
                    int k = 0;  
                    for (int n=0; n<value2.length; n++) {  
                        if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {  
                            xiangsi++;  
                        } else {  
                            busi++;  
                        }  
                    }  
                } catch (RuntimeException e) {  
                    continue;  
                }  
                j++;  
            }  
            i++;  
        }  
        String baifen = "";  
        try {  
            baifen = ((Double.parseDouble(xiangsi + "") / Double.parseDouble((busi + xiangsi) + "")) + "");  
            baifen = baifen.substring(baifen.indexOf(".") + 1, baifen.indexOf(".") + 3);  
        } catch (Exception e) {  
            baifen = "0";  
        }  
        if (baifen.length() <= 0) {  
            baifen = "0";  
        }  
        if(busi == 0){  
            baifen="100";  
        }  
  
        System.out.println("相似像素数量：" + xiangsi + " 不相似像素数量：" + busi + " 相似率：" + Integer.parseInt(baifen) + "%");  
  
    }
	
	public static double modelMatch(BufferedImage sourceImage, BufferedImage candidateImage) {
		HistogramFilter hfilter = new HistogramFilter();
		float[] sourceData = hfilter.filter(sourceImage);
		float[] candidateData = hfilter.filter(candidateImage);
		double[] mixedData = new double[sourceData.length];
		for(int i=0; i<sourceData.length; i++ ) {
			mixedData[i] = Math.sqrt(sourceData[i] * candidateData[i]);
		}
		
		// The values of Bhattacharyya Coefficient ranges from 0 to 1,
		double similarity = 0;
		for(int i=0; i<mixedData.length; i++ ) {
			similarity += mixedData[i];
		}
		
		// The degree of similarity
		return similarity;
	}

	private int getFingerPrint(BufferedImage image) {
		final int WIDTH = 8;
		final int HEIGHT = 8;
		image = ImageTools.reduceSize(image, WIDTH, HEIGHT);
		double[][] pixels = ImageTools.getGrayValue(image);
		double avg = ImageTools.getAverage(pixels);
		int fingerprint = ImageTools.getFingerPrint(pixels, avg);
		return fingerprint;
	}

	private int compareFingerPrint(int orgin_fingerprint, int compared_fingerprint) {
		int count = 0;
		for(int i = 0; i < 64; i++){
			byte orgin = (byte) (orgin_fingerprint & (1 << i));
			byte compared = (byte) (compared_fingerprint & (1 << i));
			if(orgin != compared){
				count++;
			}
		}
		return count;
	}
	
}
