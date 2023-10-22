package svc.dynamic.form.project.Service;

import static org.slf4j.event.Level.DEBUG;
import static org.slf4j.event.Level.ERROR;
import static org.slf4j.event.Level.INFO;
import static org.slf4j.event.Level.TRACE;
import static org.slf4j.event.Level.WARN;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import svc.dynamic.form.project.Component.MimeTypesComponent;
import svc.dynamic.form.project.Component.ResponseHashMapComponent;
import svc.dynamic.form.project.Component.ResponseIterableComponent;
import svc.dynamic.form.project.Component.ResponseListComponent;
import svc.dynamic.form.project.Component.ResponseObjectComponent;
import svc.dynamic.form.project.Configuration.AppProperties;
import svc.dynamic.form.project.Configuration.FileStorageProperties;
import svc.dynamic.form.project.Exception.FileStorageException;

/**
 *
 * @author ibent95
 */
@Service
public class CommonService {

	@Autowired
    private ResponseObjectComponent responseObject;
    @Autowired
    private ResponseListComponent responseList;
    @Autowired
    private ResponseHashMapComponent responseHashMap;
    @Autowired
    private ResponseIterableComponent responseIterable;
    @Autowired
    private MimeTypesComponent mimeTypeComponent;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	AppProperties appProperties;
	@Autowired
	FileStorageProperties fileStorageProperties;

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);

	private String appRoot;
	private Path fileStoragePath;
	private final Pattern NONLATIN = Pattern.compile("[^\\w-]");
  	private final Pattern WHITESPACE = Pattern.compile("[\\s]");
	private Map<String, Integer> paginator;

	HashMap<String, Object> hashMapResults;
	List<Object> results;
	Object result;

	public CommonService() {
		// this.appRoot = servletContext.getRealPath("/");
		this.fileStoragePath = Paths.get("public/files").toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStoragePath);
		} catch (IOException e) {
			throw new FileStorageException("Error on creating file storage.", e);
		}

		this.paginator = new HashMap();
		this.paginator.put("limit", 0);
		this.paginator.put("offset", 0);
		this.paginator.put("page_index", 0);

		this.hashMapResults = new HashMap<>();
		this.results = null;
		this.result = null;
	}

	public Path setGetFileStoragePath(String parameter) throws IOException {

		switch (parameter) {
			case "users_directory":
				this.fileStoragePath = Paths.get(this.fileStorageProperties.getSecretUsersDirectory()).toAbsolutePath().normalize();
				break;

			case "publications_directory":
				this.fileStoragePath = Paths.get(this.fileStorageProperties.getSecretPublicationsDirectory()).toAbsolutePath().normalize();
				break;

			case "research_directory":
				this.fileStoragePath = Paths.get(this.fileStorageProperties.getSecretResearchDirectory()).toAbsolutePath().normalize();
				break;
				
			default:
				this.fileStoragePath = Paths.get(this.fileStorageProperties.getPublicFilesDirectory()).toAbsolutePath().normalize();
				break;
		}

		Files.createDirectories(this.fileStoragePath);

		return this.fileStoragePath;
	}

	public void setLogger(Level type, String message) {

		switch (type) {

			case TRACE:
				this.LOGGER.trace(message);
				break;

			case DEBUG:
				this.LOGGER.debug(message);
				break;

			case ERROR:
				this.LOGGER.error(message);
				break;

			case WARN:
				this.LOGGER.warn(message);
				break;

			default:
				this.LOGGER.info(message);
				break;

			}

	}

	public void setLogger(Level type, String message, Throwable throwable) {

		switch (type) {

			case TRACE:
				this.LOGGER.trace(message, throwable);
				break;

			case DEBUG:
				this.LOGGER.debug(message, throwable);
				break;

			case ERROR:
				this.LOGGER.error(message, throwable);
				break;

			case WARN:
				this.LOGGER.warn(message, throwable);
				break;

			default:
				this.LOGGER.info(message, throwable);
				break;

			}

	}

	public String slug(String input) {
		String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
		String normalized 	= Normalizer.normalize(nowhitespace, Form.NFD);
		String slug 		= NONLATIN.matcher(normalized).replaceAll("");

		return slug.toLowerCase(Locale.ENGLISH);
	}

	public Map setGetPaginator(@RequestParam Map<String, String> request) {

		Integer limit = Integer.parseInt(request.get("limit"));
		Integer offset = Integer.parseInt(request.get("offset"));
		Integer pageIndex = (request.containsKey("page_index"))
			? Integer.parseInt(request.get("page_index"))
			: null;

		if (pageIndex != null) {
			offset = limit * pageIndex; // Can also (pageNumber - 1)
		}

		this.paginator.put("limit", limit);
		this.paginator.put("offset", offset);
		this.paginator.put("page_index", pageIndex);

		return this.paginator;
	}

	public Long createUUIDShort() {
		return new Random().nextLong(Long.MAX_VALUE);
	}

	public String createUUID() {
		return UUID.randomUUID().toString();
	}

    public Collection convertIteratorToCollection(Iterator iterator) {
        return (Collection) StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
        .collect(Collectors.toList());
    }

	public HashMap<String, Object> uploadFile(MultipartFile file, String parameter, String secondaryUrlPart) throws FileStorageException, IOException {
		String originalName	= StringUtils.cleanPath(file.getOriginalFilename()); // Already with extension
		String[] originalNameArray	= StringUtils.cleanPath(file.getOriginalFilename()).split("\\."); // Already with extension
        String slugName		= this.slug(originalNameArray[0]);
		String extension	= "." + this.mimeTypeComponent.getExtension(file.getContentType());
		String name			= slugName + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("YMdHHmmssSSS")).toString() + extension;

		Path targetPath 	= this.setGetFileStoragePath(parameter).resolve(name);
		String path 		= targetPath.toString();

		Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

		this.hashMapResults = new HashMap<>();
		this.hashMapResults.put("original_name", originalName);
		this.hashMapResults.put("slug", slugName);
		this.hashMapResults.put("name", name);
		this.hashMapResults.put("path", path);
		this.hashMapResults.put("url", this.appProperties.getBaseUrl() + "/" + secondaryUrlPart + "/" + name);
		this.hashMapResults.put("extension", extension);

		return this.hashMapResults;
	}

}
