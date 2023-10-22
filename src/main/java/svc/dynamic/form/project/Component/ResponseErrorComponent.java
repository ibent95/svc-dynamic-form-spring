package svc.dynamic.form.project.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class ResponseErrorComponent {

  private int statusCode;
  private Date timestamp;
  private String message;
  private String description;
  private StackTraceElement[] stackTrace;
  private List<ResponseErrorValidationComponent> errors;

	public ResponseErrorComponent() {
  }

  public ResponseErrorComponent(int statusCode, String message) {
		this.statusCode = statusCode;
		this.timestamp = new Date();
		this.message = message;
	}

  public ResponseErrorComponent(int statusCode, Date timestamp, String message, String description) {
    this.statusCode = statusCode;
    this.timestamp = timestamp;
    this.message = message;
    this.description = description;
  }

  public ResponseErrorComponent(int statusCode, Date timestamp, String message, String description, StackTraceElement[] stackTrace) {
    this.statusCode = statusCode;
    this.timestamp = timestamp;
    this.message = message;
    this.description = description;
    this.stackTrace = stackTrace;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public StackTraceElement[] getStackTrace() {
    return stackTrace;
  }

  public void setStackTrace(StackTraceElement[] stackTrace) {
    this.stackTrace = stackTrace;
  }

  public List<ResponseErrorValidationComponent> getErrors() {
    return errors;
  }

  public void setErrors(List<ResponseErrorValidationComponent> errors) {
    this.errors = errors;
  }

  public void addValidationError(String field, String message){
    if (Objects.isNull(errors)) {
        errors = new ArrayList<>();
    }

    errors.add(new ResponseErrorValidationComponent(field, message));
  }
  
}
