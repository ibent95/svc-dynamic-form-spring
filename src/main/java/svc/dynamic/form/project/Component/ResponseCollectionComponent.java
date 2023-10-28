package svc.dynamic.form.project.Component;

import java.util.Collection;

import org.springframework.stereotype.Component;

/**
 *
 * @author ibent95
 */
@Component
public class ResponseCollectionComponent {
	public String info;
	public Integer status;
	public String message;
	public Collection data;
	public Long count;
}
