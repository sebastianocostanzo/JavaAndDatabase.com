package com.jesper.music.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.force.sdk.connector.ForceServiceConnector;
import com.jesper.music.model.Album;
import com.jesper.music.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.*;
import java.util.*;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AlbumDetailController {

	private static final Logger logger = LoggerFactory.getLogger(AlbumDetailController.class);
	@Autowired
	private ChatterRESTHelper chatterREST;
	
	@Inject
	MusicService musicService;
	
	@Inject
	ForceServiceConnector forceService;
	
	@ModelAttribute("album")
	public Album init(@PathVariable String id) {
		return (Album) musicService.findEntity(Album.class,id);
	}
	
	@RequestMapping(value="/album/{id}", method=RequestMethod.GET)
	public ModelAndView view(ModelAndView mv, @ModelAttribute Album album) {
		ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
		String feeds = "";
		
		try {
			JSONObject the_json = new JSONObject(chatterREST.getFeed(album.getId()));
			JSONArray the_json_array = the_json.getJSONArray("items");
			int size = the_json_array.length();
			    for (int i = 0; i < size; i++) {
			        JSONObject another_json_object = the_json_array.getJSONObject(i);
					logger.debug("in loop another json object: " + another_json_object);
			            arrays.add(another_json_object);
			    }
			JSONObject[] jsons = new JSONObject[arrays.size()];
			arrays.toArray(jsons);
			for (JSONObject jo:arrays){
				feeds += "<br/>" + jo.getString("body"); 
			}
		} catch (JSONException je){
			je.printStackTrace();
		}
		
		mv.addObject("feeds",feeds);
		mv.addObject("album", album);
		mv.setViewName("album_detail");
		return mv;
	}

	@RequestMapping(value="/album/{id}", method=RequestMethod.POST)
	public ModelAndView update(ModelAndView mv, @ModelAttribute Album album) {
		//return "redirect:/album/"+musicService.saveAlbum(album).getId();
		//album.setComment("");
		musicService.saveAlbum(album);
		chatterREST.createPost(album.getId(),album.getComment());
		
		mv.addObject("list", musicService.getList("Album",null));
		mv.setViewName("album_list");
		return mv;
	}
	
	

}

