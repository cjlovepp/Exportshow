package com.cj.base.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * ClassName:ResourcesExcelUtils
 * Function: 定制化课程Excel导出数据
 *
 * @author   cj**
 *
 */
public class ExcelUtils {

	/*
	 * 获取XSSFWorkbook对象
	 */
	private static XSSFWorkbook getXSSFWorkbook(InputStream in) throws IOException {
		
		return new XSSFWorkbook(in) ;
	}
	
	/*
	 * 字体基本样式_
	 */
	private static XSSFCellStyle getContentStyle(XSSFWorkbook wb) {

		XSSFCellStyle contentStyle = wb.createCellStyle() ;
		contentStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		contentStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
       
		// 生成另一个字体
		XSSFFont font = wb.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		contentStyle.setFont(font);
		
		return contentStyle ;
	}
	
	/**
	 * exportResourcesExcel:(导出课程统计的数据)
	 * @param in
	 * @author Administrator
	 * @throws IOException 
	 */
	public static XSSFWorkbook exportResourcesExcel(HttpServletRequest request, InputStream image, List<List<Object>> datas) throws IOException {
		String path = request.getServletContext().getRealPath("/");
		File file = new File(path,"WEB-INF/template/resExportTemplate.xlsx");
		InputStream in = new FileInputStream(file);
		XSSFWorkbook wb = getXSSFWorkbook(in) ;
		
		// 插入图片
		insertPOIImage(wb, image);
		
		// 插入表格数据
		insertPOIDatas(wb, datas) ;
		
		return wb;
	}
	
	/*
	 * 表格数据插入
	 */
	private static void insertPOIDatas(XSSFWorkbook wb, List<List<Object>> datas) {

		XSSFSheet sheet = wb.getSheet("resources") ;
		
		// 数据插入
		if(datas != null){
			for(int i=0; i<datas.size();i++){
				XSSFRow dataRow = sheet.getRow(i + 5);
				//区县
				dataRow.createCell(0);
				XSSFCell areaCell = dataRow.getCell(0);
				String value = datas.get(i).get(0).toString() + ":" +  datas.get(i).get(1).toString();
				areaCell.setCellValue(new XSSFRichTextString(value));
				areaCell.setCellStyle(getContentStyle(wb));
				
			}
			
		}
		
	}

	/*
	 * 插入图片
	 */
	private static void insertPOIImage(XSSFWorkbook wb, InputStream image) throws IOException {
		XSSFSheet sheet = wb.getSheet("resources") ;
		// 声明一个画图的顶级管理器
        XSSFDrawing patriarch = sheet.createDrawingPatriarch();
        // 获取插入图片的位置
       /* XSSFRow row = sheet.getRow(1) ;
        row.createCell(0);
        XSSFCell firstCell = row.getCell(0) ;*/
        
        ByteArrayOutputStream firstByteArrayOut = new ByteArrayOutputStream(); 
	   	BufferedImage firstBufferImg = ImageIO.read(image); 
	   	
	   	ImageIO.write(firstBufferImg,  "jpg", firstByteArrayOut); 
	   	
	   	XSSFClientAnchor firstAnchor = new XSSFClientAnchor(0,0,512,255,(short) 0,1,(short)3,2); 
	   	// 插入图片
	   	patriarch.createPicture(firstAnchor  , wb.addPicture(firstByteArrayOut.toByteArray() ,HSSFWorkbook.PICTURE_TYPE_JPEG)); 
	}
}
