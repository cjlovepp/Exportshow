package com.cj.poi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class comeControler {
	@RequestMapping("index")
	public String index(){
		return "index";
	}

}
