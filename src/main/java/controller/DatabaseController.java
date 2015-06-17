package controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import service.DatabaseService;


@Controller
@RequestMapping("/db")
public class DatabaseController {

	@Autowired
	private DatabaseService dbService;
    private static final Logger log = Logger.getLogger(String.valueOf(DatabaseController.class));
	

	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public void startDB() throws Exception{
		log.info("starte");
		dbService.startDB();
	}
	
	@RequestMapping(value = "/stop", method = RequestMethod.GET)
	public void stopDB() throws Exception{
		log.info("starte");
		dbService.stopDB();
	}
	
}