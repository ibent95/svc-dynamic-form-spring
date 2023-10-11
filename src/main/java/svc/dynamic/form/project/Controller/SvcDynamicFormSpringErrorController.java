package svc.dynamic.form.project.Controller;

import java.util.Date;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import svc.dynamic.form.project.Component.ResponseErrorComponent;

/**
 *
 * @author ibent95
 */
@RestController
public class SvcDynamicFormSpringErrorController implements ErrorController {

	private static final String ERROR_PATH = "/error";

	@RequestMapping(ERROR_PATH)
	public ResponseEntity<Object> handleNotFoundResponse(NotFoundException exception) {
		ResponseErrorComponent responseError = new ResponseErrorComponent(404, new Date(), exception.getMessage(), "The route is not found!");
		return ResponseEntity.status(404).body(responseError);
	}

    public String getErrorPath() {
        return ERROR_PATH;
    }

}
