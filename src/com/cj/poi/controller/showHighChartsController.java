package com.cj.poi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cj.base.utils.ExcelUtils;
import com.cj.base.utils.SvgPngConverter;
import com.cj.base.utils.UUIDUtils;

@Controller
@RequestMapping("front")
public class showHighChartsController {

	@RequestMapping("toHighChatrs")
	public String toHighChatrs(){
		return "highcharts";
	}
	
	@ResponseBody
	@RequestMapping("getData")
	public List<List<Object>> getData(int num){
		List<List<Object>> datas = new ArrayList<List<Object>>();
		for (int i = 0; i < num; i++) {
			List<Object> temp = new ArrayList<Object>();
			temp.add("测试数据" + i);
			temp.add(i);
			datas.add(temp);
		}
		
		return datas;
	}
	
	@ResponseBody
	@RequestMapping("saveSvg")
	public Boolean saveSvg (HttpServletRequest request , String svg){
		try {			
			HttpSession session = request.getSession();
			session.setAttribute("svgHighchatts", svg);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@ResponseBody
	@RequestMapping("exportHighCharts")
	public String exportHighCharts(HttpServletResponse response,HttpServletRequest request, int num) throws IOException{
		response.setContentType("application/x-msdownload");
		//response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=exportDatas.xlsx");
		InputStream image = null;
		String uuid = UUIDUtils.getUUID() + ".jpg" ;
		String tempPath = request.getServletContext().getRealPath("/temp") ;
		try {
			HttpSession session = request.getSession();
			String svgHighchatts = session.getAttribute("svgHighchatts").toString();
			SvgPngConverter.convertToJpeg(svgHighchatts, tempPath + File.separator + uuid) ;
			image = new FileInputStream(new File(tempPath + File.separator + uuid));
			List<List<Object>> datas = new ArrayList<List<Object>>();
			for (int i = 0; i < num; i++) {
				List<Object> temp = new ArrayList<Object>();
				temp.add("测试数据" + i);
				temp.add(i);
				datas.add(temp);
			}
			ExcelUtils.exportResourcesExcel(request, image, datas).write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(image != null){
				image.close();
			}
			FileUtils.forceDelete(new File(tempPath + File.separator+ uuid)) ;
		}
		return null;
		
	}
	
	
	
}
