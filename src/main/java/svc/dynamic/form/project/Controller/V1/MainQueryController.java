package svc.dynamic.form.project.Controller.V1;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import svc.dynamic.form.project.Component.ResponseHashMapComponent;

/**
 *
 * @author ibent95
 */
@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping(value = "public/api/v1")
public class MainQueryController {

	@Autowired
	private ResponseHashMapComponent responseHashMap;

	public MainQueryController(
		// ResponseHashMapComponent responseHashMap
	) {
		// this.responseHashMap = responseHashMap;

        // this.responseHashMap.info = "error";
        // this.responseHashMap.status = 400;
        // this.responseHashMap.message = "No prosses is ececute!";
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<ResponseHashMapComponent> requestMethodName(HttpServletRequest request) {

		this.responseHashMap.status = 200;
		this.responseHashMap.info = "success";
		this.responseHashMap.message = "This is Dynamic Forms Main service.";
		this.responseHashMap.data = new HashMap<String, Object>();
		this.responseHashMap.data.put("message", "Welcome to Dynamic Forms service in Spring Boot 3.");
		this.responseHashMap.data.put("date", new Date());

		return ResponseEntity.status(this.responseHashMap.status).body(this.responseHashMap);
	}

}
