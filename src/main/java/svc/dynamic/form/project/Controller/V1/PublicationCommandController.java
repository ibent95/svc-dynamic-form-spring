package svc.dynamic.form.project.Controller.V1;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping(value = "publications")
    @PutMapping(value = "publications/{uuid}")
    public ResponseEntity<ResponseObjectComponent> save(
        @RequestParam Map<String, Object> requestParam,
        @RequestParam("meta_data_files") List<MultipartFile> requestFilesParam,
        HttpServletRequest request
    ) {
        String uuid = (requestParam.containsKey("uuid"))
            ? requestParam.get("uuid").toString()
            : null;

        try {

            Publication publication = (uuid != null)
                ? publicationRepo.findByUuid(uuid)
                : null;

            PublicationType publicationType = this.publicationTypeRepo.findByPublicationTypeCode(requestParam.get("publication_type_code").toString());

            PublicationFormVersion publicationFormVersion = this.publicationFormVersionRepo.findByIdPublicationTypeAndFlagActive(publicationType.getId(), true);

            Publication publicationData = this.publicationSvc.setDataByDynamicForm(requestParam, requestFilesParam, request, publicationFormVersion, publication);

            this.publicationRepo.save(publicationData);

            this.responseObject.data = publicationData;
            this.responseObject.status = 200;
            this.responseObject.info = "success";
            this.responseObject.message = (uuid == null) ? "Success on create publication data." : "Success on edit publication data by UUID: " + uuid + ".";
        } catch (Exception e) {
            this.responseObject.status = 400;
            this.responseObject.info = "error";
            this.responseObject.message = (uuid == null) ? "Error on create publication data." : "Error on edit publications data by UUID: " + uuid + ".";
        }

        return ResponseEntity.status(this.responseObject.status).body(this.responseObject);
    }
    
}
