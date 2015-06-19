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
@RequestMapping("icharts")
public class showIChartsController {

	@RequestMapping("toIChatrs")
	public String toIChatrs(){
		return "iCharts";
	}
	
	@ResponseBody
	@RequestMapping("exportICharts")
	public String exportICharts(HttpServletResponse response,HttpServletRequest request, String images) throws IOException{
		response.setContentType("application/x-msdownload");
		//response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=exportDatas.xlsx");
		System.out.println(images);
		InputStream image = null;
		String uuid = UUIDUtils.getUUID() + ".jpg" ;
		String tempPath = request.getServletContext().getRealPath("/temp") ;
		try {
			HttpSession session = request.getSession();
			String svgHighchatts = session.getAttribute("svgHighchatts").toString();
			SvgPngConverter.convertToJpeg(svgHighchatts, tempPath + File.separator + uuid) ;
			image = new FileInputStream(new File(tempPath + File.separator + uuid));
			List<List<Object>> datas = new ArrayList<List<Object>>();
			for (int i = 0; i < 9; i++) {
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
