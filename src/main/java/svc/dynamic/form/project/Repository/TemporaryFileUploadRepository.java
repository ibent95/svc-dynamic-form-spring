package svc.dynamic.form.project.Repository;

import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;
import svc.dynamic.form.project.Entity.TemporaryFileUpload;

/**
 *
 * @author ibent95
 */
public interface TemporaryFileUploadRepository extends CrudRepository<TemporaryFileUpload, Id> {

    TemporaryFileUpload findByUuid(String uuid);
	
}
