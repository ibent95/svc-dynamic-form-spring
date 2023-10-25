package svc.dynamic.form.project.Controller.V1;

import static org.slf4j.event.Level.ERROR;
import static org.slf4j.event.Level.INFO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import svc.dynamic.form.project.Component.ResponseHashMapComponent;
import svc.dynamic.form.project.Component.ResponseIterableComponent;
import svc.dynamic.form.project.Component.ResponseListComponent;
import svc.dynamic.form.project.Component.ResponseObjectComponent;
import svc.dynamic.form.project.Entity.Publication;
import svc.dynamic.form.project.Entity.PublicationFormVersion;
import svc.dynamic.form.project.Entity.PublicationType;
import svc.dynamic.form.project.Repository.PublicationRepository;
import svc.dynamic.form.project.Repository.PublicationFormRepository;
import svc.dynamic.form.project.Repository.PublicationTypeRepository;
import svc.dynamic.form.project.Service.CommonService;
import svc.dynamic.form.project.Service.DynamicFormService;
import svc.dynamic.form.project.Service.PublicationService;

@RestController
@RequestMapping(value = "public/api/v1")
public class PublicationQueryController {

    @Autowired
    private ResponseObjectComponent responseObject;
    @Autowired
    private ResponseListComponent responseList;
    @Autowired
    private ResponseHashMapComponent responseHashMap;
    @Autowired
    private ResponseIterableComponent responseIterable;

    @Autowired
    private PublicationRepository publicationRepo;
    @Autowired
    private PublicationFormRepository publicationFormRepo;
    @Autowired
    private PublicationTypeRepository publicationTypeRepo;

    @Autowired
    private CommonService commonSvc;
    @Autowired
    private DynamicFormService dynamicFormSvc;
    @Autowired
    private PublicationService publicationSvc;

    public PublicationQueryController() { }

    @RequestMapping(value = "publication", method = RequestMethod.GET)
    public ResponseEntity<ResponseHashMapComponent> main() {

		this.responseHashMap.status = 200;
		this.responseHashMap.info = "success";
		this.responseHashMap.message = "Welcome to Dynamic Forms service in Spring Boot 3.";
		this.responseHashMap.data = new HashMap<String, Object>();
		this.responseHashMap.data.put("message", "This is Publication API service.");
		this.responseHashMap.data.put("date", new Date());

		return ResponseEntity.status(this.responseHashMap.status).body(this.responseHashMap);
    }

    @RequestMapping(value = "publications", method = RequestMethod.GET)
    public ResponseEntity<ResponseListComponent> index(@RequestParam Map request) {

        try {
            Boolean flagActive                  = true;
            Map paginator                       = this.commonSvc.setGetPaginator(request);

            Long publicationsTotalCount         = this.publicationRepo.countByFlagActive(flagActive);
            List<Publication> publicationsData  = this.publicationRepo.findAllWithFlagActiveLimitOffset(
                flagActive,
                (int) paginator.get("limit"),
                (int) paginator.get("offset")
            );

            this.responseList.count     = publicationsTotalCount;
            this.responseList.data      = publicationsData;
            this.responseList.status    = 200;
            this.responseList.info      = "success";
            this.responseList.message   = "Success on get publications data!";
        } catch (Exception e) {
            this.responseList.status    = 400;
            this.responseList.info      = "error";
            this.responseList.message   = "Error on get publications data!";
        }

        return ResponseEntity.status(this.responseList.status).body(this.responseList);
    }

    @RequestMapping(value = "publications/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObjectComponent> getByUuid(@PathVariable String uuid) {

        try {
            Publication publicationData = this.publicationRepo.findByUuid(uuid);

            this.responseObject.data = publicationData;
            this.responseObject.status = 200;
            this.responseObject.info = "success";
            this.responseObject.message = "Success on get publication data by UUID: " + uuid + ".";
        } catch (Exception e) {
            this.responseObject.status = 400;
            this.responseObject.info = "error";
            this.responseObject.message = "Error on get publications data by UUID: " + uuid + ".";
        }

        return ResponseEntity.status(this.responseObject.status).body(this.responseObject);
    }

    @RequestMapping(value = "publications/form-meta-data/{publicationTypeCode}", method = RequestMethod.GET)
    public ResponseEntity<ResponseHashMapComponent> getFormMetaDataByPublicationTypeCode(@PathVariable String publicationTypeCode)  {

        try {
            PublicationType publicationType                     = this.publicationTypeRepo.findByPublicationTypeCode(publicationTypeCode);
            
            // FormVersion
            Collection<PublicationFormVersion> formVersions     = publicationType.getPublicationFormVersionCollection();
            PublicationFormVersion formVersion                  = this.publicationSvc.getActiveFormVersionData(formVersions);
            CopyOnWriteArrayList<HashMap<String, Object>> formsRawCollection    = this.publicationSvc.getFormMetaDataByPublicationFormCollection(formVersion.getPublicationFormCollection());

            CopyOnWriteArrayList<HashMap<String, Object>> formsCollection	    = new CopyOnWriteArrayList<HashMap<String, Object>>(
                formsRawCollection.stream().map((HashMap<String, Object> field) -> {
                    String[] fieldOptions;
                    field.put("options", new ArrayList<>());

                    switch (field.get("field_type").toString()) {
                        case "select":
                            fieldOptions = field.get("field_options").toString().split("-");

                            // Get options of select from database (Master data or terms of taxonomy)
                            field.replace("options", (fieldOptions[0].equals("master"))
                                ? this.publicationFormRepo.findAllMasterData(
                                    fieldOptions[1],
                                    null,
                                    null
                                )
                                : null
                            );
                            break;

                        case "autoselect":
                        case "autocomplete":
                            fieldOptions = field.get("field_options").toString().split("-");
                            
                            // Get options of autoselect from database (Master data or terms of taxonomy)
                            // NEED RECONFIGURE LATER IN CustomPublicationFormRepositoryImpl!!!
                            field.replace("options", (fieldOptions[0].equals("master"))
                                ? this.publicationFormRepo.findAllTaxonomyTerms(
                                    fieldOptions[1],
                                    null,
                                    null
                                )
                                : null
                            );
                            break;
                        
                        default: break;
                    }

                    return field;
                }).toList()
            );

            Collection<HashMap<String, Object>> forms           = this.dynamicFormSvc.setFields(formsCollection, formVersion.getGridSystem());

            HashMap<String, Object> formMetaData                = this.publicationSvc.setGetFormMetaData(formVersion, forms);

            this.responseHashMap.data    = formMetaData;
            this.responseHashMap.status  = 200;
            this.responseHashMap.info    = "success";
            this.responseHashMap.message = "Success on get form metadata data by Publication Type Code: " + publicationTypeCode + ".";
            this.commonSvc.setLogger(INFO, this.responseHashMap.message);
        } catch (Exception e) {
            this.responseHashMap.status  = 400;
            this.responseHashMap.info    = "error";
            this.responseHashMap.message = "Error on get form metadata data by Publication Type Code: " + publicationTypeCode + ".";
            this.commonSvc.setLogger(ERROR, this.responseHashMap.message, e);
        }

        return ResponseEntity.status(this.responseHashMap.status).body(this.responseHashMap);
    }

    @RequestMapping(value = "publications/{publicationUuid}/form-meta-data", method = RequestMethod.GET)
    public ResponseEntity<ResponseHashMapComponent> getFormMetaDataByPublicationUuid(@PathVariable String publicationUuid) {

        try {
            Publication publication                             = this.publicationRepo.findByUuid(publicationUuid);
            PublicationFormVersion formVersion                  = publication.getPublicationFormVersion();
            CopyOnWriteArrayList<HashMap<String, Object>> formsRawCollection = this.publicationSvc.getFormMetaDataByPublicationMetaCollection(publication.getPublicationMeta());

            CopyOnWriteArrayList<HashMap<String, Object>> formsCollection	    = new CopyOnWriteArrayList<HashMap<String, Object>>(
                formsRawCollection.stream().map((HashMap<String, Object> field) -> {
                    String[] fieldOptions;
                    field.put("options", new ArrayList<>());

                    switch (field.get("field_type").toString()) {
                        case "select":
                            fieldOptions = field.get("field_options").toString().split("-");

                            // Get options of select from database (Master data or terms of taxonomy)
                            field.replace("options", (fieldOptions[0].equals("master"))
                                ? this.publicationFormRepo.findAllMasterData(
                                    fieldOptions[1],
                                    null,
                                    null
                                )
                                : null
                            );
                            break;

                        case "autoselect":
                        case "autocomplete":
                            fieldOptions = field.get("field_options").toString().split("-");
                            
                            // Get options of autoselect from database (Master data or terms of taxonomy)
                            // NEED RECONFIGURE LATER IN CustomPublicationFormRepositoryImpl!!!
                            field.replace("options", (fieldOptions[0].equals("master"))
                                ? this.publicationFormRepo.findAllTaxonomyTerms(
                                    fieldOptions[1],
                                    null,
                                    null
                                )
                                : null
                            );
                            break;
                        
                        default: break;
                    }

                    return field;
                }).toList()
            );

            Collection<HashMap<String, Object>> forms           = this.dynamicFormSvc.setFields(formsCollection, formVersion.getGridSystem());

            HashMap<String, Object> formMetaData                = this.publicationSvc.setGetFormMetaData(formVersion, forms);

            this.responseHashMap.data    = formMetaData;
            this.responseHashMap.status  = 200;
            this.responseHashMap.info    = "success";
            this.responseHashMap.message = "Success on get form metadata data by Publication UUID: " + publicationUuid + ".";
            this.commonSvc.setLogger(INFO, this.responseHashMap.message);
        } catch (Exception e) {
            this.responseHashMap.status  = 400;
            this.responseHashMap.info    = "error";
            this.responseHashMap.message = "Error on get form metadata data by Publication UUID: " + publicationUuid + ".";
            this.commonSvc.setLogger(ERROR, this.responseHashMap.message, e);
        }

        return ResponseEntity.status(this.responseHashMap.status).body(this.responseHashMap);
    }

}
