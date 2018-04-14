package com.mytest.controller;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytest.bean.SBean;

@RemoteProxy(name = "SController")
//@Controller
//@RequestMapping("/dwr/*")
public class SController {

	private static final Logger logger = LoggerFactory.getLogger(SController.class);

	@Autowired
	SBean sbean;

	@RemoteMethod
	@RequestMapping("getBeanName")
	@ResponseBody
	public String getBeanName() {
		try {
			return sbean.getBeanName();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return "Error!";
		}

	}
}