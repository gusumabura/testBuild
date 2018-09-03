package com.wmp.hw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wmp.hw.motel.HwVo;
import com.wmp.hw.motel.ResultVo;
import com.wmp.hw.service.MainService;

@Controller
public class MainController {
	
	@Autowired
	MainService mainService;
	
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("title", "HW");
		return "index";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api", produces="application/json", consumes="application/json")
	@ResponseBody
	public ResultVo resultApi(@RequestBody HwVo hwVo) {
		return mainService.getProc(hwVo);
	}
}
