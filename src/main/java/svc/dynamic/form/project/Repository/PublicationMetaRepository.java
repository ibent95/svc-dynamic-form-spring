package svc.dynamic.form.project.Repository;

import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;
import svc.dynamic.form.project.Entity.PublicationMeta;

/**
 *
 * @author ibent95
 */
public interface PublicationMetaRepository extends CrudRepository<PublicationMeta, Id> {
	
}
