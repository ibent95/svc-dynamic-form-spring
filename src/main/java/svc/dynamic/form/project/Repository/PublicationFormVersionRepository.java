package svc.dynamic.form.project.Repository;

import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;
import svc.dynamic.form.project.Entity.PublicationFormVersion;

/**
 *
 * @author ibent95
 */
public interface PublicationFormVersionRepository extends CrudRepository<PublicationFormVersion, Id> {
	
}
