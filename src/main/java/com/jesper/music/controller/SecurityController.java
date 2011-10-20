package com.jesper.music.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.force.sdk.oauth.context.*;
import com.force.sdk.oauth.context.store.*;

@Controller
public class SecurityController {

	@RequestMapping(value="/logoutSuccess", method=RequestMethod.GET)
	public String logout(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
		// Do any additional cleanup that might be prudent...
		//SecurityContext sc = ForceSecurityContextHolder.get();
		//SecurityContextServiceImpl scsi =  new SecurityContextServiceImpl();
		//scsi.setSecurityContextStorageService(new SecurityContextCookieStore());
		//scsi.clearSecurityContext(request,response);
		//SecurityContextUtil.clearCookieValues(response);
		//return "redirect:/logoutSuccess.jsp";
		return "logoutSuccess";
	}
}
