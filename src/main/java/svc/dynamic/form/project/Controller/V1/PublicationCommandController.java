package svc.dynamic.form.project.Controller.V1;

import static org.slf4j.event.Level.ERROR;
import static org.slf4j.event.Level.INFO;
import static org.slf4j.event.Level.TRACE;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import svc.dynamic.form.project.Component.ResponseHashMapComponent;
import svc.dynamic.form.project.Component.ResponseIterableComponent;
import svc.dynamic.form.project.Component.ResponseListComponent;
import svc.dynamic.form.project.Component.ResponseObjectComponent;
import svc.dynamic.form.project.Entity.Publication;
import svc.dynamic.form.project.Entity.PublicationFormVersion;
import svc.dynamic.form.project.Entity.PublicationType;
import svc.dynamic.form.project.Repository.PublicationFormVersionRepository;
import svc.dynamic.form.project.Repository.PublicationRepository;
import svc.dynamic.form.project.Repository.PublicationStatusRepository;
import svc.dynamic.form.project.Repository.PublicationTypeRepository;
import svc.dynamic.form.project.Service.CommonService;
import svc.dynamic.form.project.Service.PublicationService;

@RestController
@RequestMapping(value = "public/api/v1")
public class PublicationCommandController {
    @Autowired
    private ResponseObjectComponent responseObject;
    @Autowired
    private ResponseListComponent responseList;
    @Autowired
    private ResponseHashMapComponent responseHashMap;
    @Autowired
    private ResponseIterableComponent responseIterable;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private CommonService commonSvc;
    @Autowired
    private PublicationService publicationSvc;

    @Autowired
    private PublicationRepository publicationRepo;
    @Autowired
    private PublicationStatusRepository publicationStatusRepo;
    @Autowired
    private PublicationTypeRepository publicationTypeRepo;
    @Autowired
    private PublicationFormVersionRepository publicationFormVersionRepo;

    private static final Logger logger = LoggerFactory.getLogger(PublicationCommandController.class);

    public PublicationCommandController() {}

    @RequestMapping(value = "publications", method = RequestMethod.POST)
    public ResponseEntity<ResponseObjectComponent> save(
        @RequestParam Map<String, Object> requestParam,
        @RequestParam("meta_data_files") List<MultipartFile> requestFilesParam,
        HttpServletRequest request
    ) {

        try {

            PublicationType publicationType = this.publicationTypeRepo.findByPublicationTypeCode(requestParam.get("publication_type_code").toString());

            PublicationFormVersion publicationFormVersion = this.publicationFormVersionRepo.findByIdPublicationTypeAndFlagActive(publicationType.getId(), true);

            Publication publicationData = this.publicationSvc.setDataByDynamicForm(requestParam, requestFilesParam, request, publicationFormVersion, null);

            this.publicationRepo.save(publicationData);

            this.responseObject.data = publicationData;
            this.responseObject.status = 200;
            this.responseObject.info = "success";
            this.responseObject.message = "Success on create publication data.";
            this.commonSvc.setLogger(INFO, this.responseObject.message);
        } catch (Exception e) {
            this.responseObject.status = 400;
            this.responseObject.info = "error";
            this.responseObject.message = "Error on create publication data.";
            this.commonSvc.setLogger(ERROR, this.responseObject.message);
        }

        return ResponseEntity.status(this.responseObject.status).body(this.responseObject);
    }

    @RequestMapping(value = "publications/{uuid}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseObjectComponent> save(
        @RequestParam Map<String, Object> requestParam,
        @RequestParam("meta_data_files") List<MultipartFile> requestFilesParam,
        @PathVariable String uuid,
        HttpServletRequest request
    ) {

        try {
            Publication publication = (uuid != null)
                ? this.publicationRepo.findByUuid(uuid)
                : null;

            PublicationType publicationType = this.publicationTypeRepo.findByPublicationTypeCode(requestParam.get("publication_type_code").toString());

            PublicationFormVersion publicationFormVersion = this.publicationFormVersionRepo.findByIdPublicationTypeAndFlagActive(publicationType.getId(), true);

            Publication publicationData = this.publicationSvc.setDataByDynamicForm(requestParam, requestFilesParam, request, publicationFormVersion, publication);

            this.publicationRepo.save(publicationData);

            this.responseObject.data = publicationData;
            this.responseObject.status = 200;
            this.responseObject.info = "success";
            this.responseObject.message = "Success on edit publication data by UUID: " + uuid + ".";
            this.commonSvc.setLogger(INFO, this.responseObject.message);
        } catch (Exception e) {
            this.responseObject.status = 400;
            this.responseObject.info = "error";
            this.responseObject.message = "Error on edit publications data by UUID: " + uuid + ". Message: " + e.getLocalizedMessage();
            this.commonSvc.setLogger(ERROR, this.responseObject.message);
        }

        return ResponseEntity.status(this.responseObject.status).body(this.responseObject);
    }
    
}
