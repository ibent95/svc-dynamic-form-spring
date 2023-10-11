package svc.dynamic.form.project.Component;

import org.springframework.stereotype.Component;

/**
 *
 * @author ibent95
 */
@Component
public class ResponseErrorValidationComponent {

	private String field;
	private String message;

	public ResponseErrorValidationComponent() {
	}

	public ResponseErrorValidationComponent(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
