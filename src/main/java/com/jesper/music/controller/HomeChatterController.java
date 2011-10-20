package com.jesper.music.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jesper.music.model.Album;
import com.jesper.music.model.Artist;
import com.jesper.music.model.Genre;
import com.jesper.music.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Handles requests for the chatter page.
 */
@Controller
public class HomeChatterController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private RESTHelper rest;
	
	enum ManagedEntities {
		album (Album.class),
		artist (Artist.class),
		genre (Genre.class);
		
		private Class<? extends Object> clazz;
		
		ManagedEntities(Class<? extends Object> clazz) {
			this.clazz = clazz;
		}
		
		public String getClassName() { return clazz.getName(); }
	}
	
	@Inject
	MusicService musicService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/homeChatter", method=RequestMethod.GET)
	public String home() {
		return "homeChatter_detail";
	}

	@RequestMapping(value = "/c", method = RequestMethod.POST)
    public ModelAndView createResource(ModelAndView mv, @RequestParam String resource, @RequestParam String body) {
		mv.setViewName("dumpresponse");
		mv.addObject("json", rest.create(resource, body));
		return mv;
    }

	@RequestMapping(value = "/q", method = RequestMethod.POST)
    public ModelAndView performQuery(ModelAndView mv, @RequestParam String query) {
		mv.setViewName("dumpresponse");
		mv.addObject("json", rest.query(query));
		return mv;
    }
	
}

