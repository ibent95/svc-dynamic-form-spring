package svc.dynamic.form.project.Component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 *
 * @author ibent95
 */
@Component
public class ResponseHashMapComponent {
	public String info;
	public Integer status;
	public String message;
	public Map<String, Object> data;

	public ResponseHashMapComponent() {
		this.info = null;
		this.status = null;
		this.message = null;
		this.data = null;
	}

	public ResponseHashMapComponent(String info, Integer status, String message) {
		this.info = info;
		this.status = status;
		this.message = message;
	}

	public ResponseHashMapComponent(String info, Integer status, String message, HashMap data) {
		this.info = info;
		this.status = status;
		this.message = message;
		this.data = data;
	}

}
