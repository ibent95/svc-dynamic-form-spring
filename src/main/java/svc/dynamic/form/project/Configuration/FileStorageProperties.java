package svc.dynamic.form.project.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    
    private String publicFilesDirectory;
    private String secretResourcesDirectory;
    private String resourcesDirectory;
    private String secretUsersDirectory;
    private String usersDirectory;
    private String secretPublicationsDirectory;
    private String publicationsDirectory;
    private String secretResearchDirectory;
    private String researchDirectory;

    public String getPublicFilesDirectory() {
        return publicFilesDirectory;
    }

    public void setPublicFilesDirectory(String publicFilesDirectory) {
        this.publicFilesDirectory = publicFilesDirectory;
    }

    public String getSecretResourcesDirectory() {
        return secretResourcesDirectory;
    }

    public void setSecretResourcesDirectory(String secretResourcesDirectory) {
        this.secretResourcesDirectory = secretResourcesDirectory;
    }

    public String getResourcesDirectory() {
        return resourcesDirectory;
    }

    public void setResourcesDirectory(String resourcesDirectory) {
        this.resourcesDirectory = resourcesDirectory;
    }

    public String getSecretUsersDirectory() {
        return secretUsersDirectory;
    }

    public void setSecretUsersDirectory(String secretUsersDirectory) {
        this.secretUsersDirectory = secretUsersDirectory;
    }

    public String getUsersDirectory() {
        return usersDirectory;
    }

    public void setUsersDirectory(String usersDirectory) {
        this.usersDirectory = usersDirectory;
    }

    public String getSecretPublicationsDirectory() {
        return secretPublicationsDirectory;
    }

    public void setSecretPublicationsDirectory(String secretPublicationsDirectory) {
        this.secretPublicationsDirectory = secretPublicationsDirectory;
    }

    public String getPublicationsDirectory() {
        return publicationsDirectory;
    }

    public void setPublicationsDirectory(String publicationsDirectory) {
        this.publicationsDirectory = publicationsDirectory;
    }

    public String getSecretResearchDirectory() {
        return secretResearchDirectory;
    }

    public void setSecretResearchDirectory(String secretResearchDirectory) {
        this.secretResearchDirectory = secretResearchDirectory;
    }

    public String getResearchDirectory() {
        return researchDirectory;
    }

    public void setResearchDirectory(String researchDirectory) {
        this.researchDirectory = researchDirectory;
    }

}
