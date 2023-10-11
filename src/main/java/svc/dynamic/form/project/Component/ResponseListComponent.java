package svc.dynamic.form.project.Component;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 *
 * @author ibent95
 */
@Component
public class ResponseListComponent {
	public String info;
	public Integer status;
	public String message;
	public List data;
	public Long count;
}
