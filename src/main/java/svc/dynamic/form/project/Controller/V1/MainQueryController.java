package svc.dynamic.form.project.Controller.V1;

import static org.slf4j.event.Level.ERROR;
import static org.slf4j.event.Level.INFO;
import static org.slf4j.event.Level.TRACE;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import svc.dynamic.form.project.Component.ResponseHashMapComponent;
import svc.dynamic.form.project.Component.ResponseObjectComponent;
import svc.dynamic.form.project.Entity.TemporaryFileUpload;
import svc.dynamic.form.project.Service.CommonService;

/**
 *
 * @author ibent95
 */
@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping(value = "public/api/v1")
public class MainQueryController {

	@Autowired
	private ResponseHashMapComponent responseHashMap;
	@Autowired
	private ResponseObjectComponent responseObject;

    @Autowired
    private CommonService commonSvc;

	String contentType;

	public MainQueryController(
		// ResponseHashMapComponent responseHashMap
	) {
		// this.responseHashMap = responseHashMap;

        // this.responseHashMap.info = "error";
        // this.responseHashMap.status = 400;
        // this.responseHashMap.message = "No prosses is ececute!";
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<ResponseHashMapComponent> requestMethodName(HttpServletRequest request) {

		this.responseHashMap.status = 200;
		this.responseHashMap.info = "success";
		this.responseHashMap.message = "This is Dynamic Forms Main service.";
		this.responseHashMap.data = new HashMap<String, Object>();
		this.responseHashMap.data.put("message", "Welcome to Dynamic Forms service in Spring Boot 3.");
		this.responseHashMap.data.put("date", new Date());

		return ResponseEntity.status(this.responseHashMap.status).body(this.responseHashMap);
	}

	@RequestMapping(value = "files/{serviceName}/{fileNameExtension}", method = RequestMethod.GET)
	public ResponseEntity<Resource> ngetUploadedFile(
        @PathVariable String serviceName,
        @PathVariable String fileNameExtension,
		HttpServletRequest request
    ) {
		try {
			Resource resource = this.commonSvc.getFile(serviceName + "_directory", fileNameExtension);
			this.contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

			// Fallback to the default content type if type could not be determined
			if (this.contentType == null) {
				this.contentType = "application/octet-stream";
			}

			this.responseObject.status 	= 200;
			this.responseObject.info 	= "success";
			this.responseObject.message = "Success on get uploaded file.";
			this.responseObject.data 	= resource;
			this.commonSvc.setLogger(INFO, this.responseObject.message);
        } catch (IOException ex) {
            this.responseObject.status 	= 400;
			this.responseObject.info 	= "error";
			this.responseObject.message = "Error on get uploaded file.";
			this.commonSvc.setLogger(INFO, this.responseObject.message);
        }

		return ResponseEntity.status(this.responseObject.status)
			.contentType(MediaType.parseMediaType(this.contentType))
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + ((Resource) this.responseObject.data).getFilename() + "\"")	
			.body((Resource) this.responseObject.data);
	}

	@RequestMapping(value = "files/upload/{serviceName}", method = RequestMethod.POST)
	public ResponseEntity<ResponseHashMapComponent> uploadFile(
        @PathVariable String serviceName,
		@RequestParam("meta_data_files") List<MultipartFile> requestFilesParam,
		HttpServletRequest request
	) {
		try {

			List<MultipartFile> files = (requestFilesParam != null) ? requestFilesParam : null;
            List<HashMap<String, Object>> uploadedFiles = new ArrayList<>();
            String[] uploadedFileNames = {};

            TemporaryFileUpload tempFileUpload = new TemporaryFileUpload();
            tempFileUpload.setId(this.commonSvc.createUUIDShort());
            tempFileUpload.setUuid(this.commonSvc.createUUID());

            if (files.size() > 0) {
				int fileIndex = 0;
                for (MultipartFile file: files) {
                    uploadedFiles.add(fileIndex, (file != null)
						? this.commonSvc.uploadFile(
							file,
							serviceName + "_directory",
							"api/v1/files/" + serviceName
						)
						: null
					);

                    uploadedFileNames[fileIndex] = (file != null)
                        ? uploadedFiles.get(fileIndex)
							.get("original_name")
							.toString()
                        : null;

					fileIndex++;
                }

                if (!uploadedFiles.isEmpty()) {
                    tempFileUpload.setUploadedDatetime(LocalDateTime.now());
                    tempFileUpload.setValue(String.join(",", uploadedFileNames));
                    tempFileUpload.setOtherValue(uploadedFiles.get(0));

					this.responseObject.data 	= tempFileUpload.getUuid();

                    // entityManager->persist(tempFileUpload);
                }
            }

			this.responseObject.status 	= 200;
			this.responseObject.info 	= "success";
			this.responseObject.message = "Success on get uploaded file.";
			this.commonSvc.setLogger(INFO, this.responseObject.message);
        } catch (IOException ex) {
            this.responseObject.status 	= 400;
			this.responseObject.info 	= "error";
			this.responseObject.message = "Error on get uploaded file.";
			this.commonSvc.setLogger(INFO, this.responseObject.message);
        }

		return ResponseEntity.status(this.responseHashMap.status).body(this.responseHashMap);
	}

}
