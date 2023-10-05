package svc.dynamic.form.project.Controller.V1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author ibent95
 */
@RestController
public class MainController {

	private ArrayList response;

	public MainController() {
		// code
	}

	@RequestMapping(value="api/v1", method=RequestMethod.GET)
	public String requestMethodName(@RequestParam String param) {
		return "Try";
	}
	
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
	public void handleError() {
		// code
	}

}
