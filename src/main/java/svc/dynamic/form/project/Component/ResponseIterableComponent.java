package svc.dynamic.form.project.Component;

import java.util.Map;

import org.springframework.stereotype.Component;

/**
 *
 * @author ibent95
 */
@Component
public class ResponseIterableComponent {
	public String info;
	public Integer status;
	public String message;
	public Iterable data;
}
