package svc.dynamic.form.project.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String baseUrl;
    private String projectDirectory;
    private String projectPublicDirectory;
    private String secretAssetsDirectory;
    private String assetsDirectory;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getProjectDirectory() {
        return projectDirectory;
    }

    public void setProjectDirectory(String projectDirectory) {
        this.projectDirectory = projectDirectory;
    }

    public String getProjectPublicDirectory() {
        return projectPublicDirectory;
    }

    public void setProjectPublicDirectory(String projectPublicDirectory) {
        this.projectPublicDirectory = projectPublicDirectory;
    }

    public String getSecretAssetsDirectory() {
        return secretAssetsDirectory;
    }

    public void setSecretAssetsDirectory(String secretAssetsDirectory) {
        this.secretAssetsDirectory = secretAssetsDirectory;
    }

    public String getAssetsDirectory() {
        return assetsDirectory;
    }

    public void setAssetsDirectory(String assetsDirectory) {
        this.assetsDirectory = assetsDirectory;
    }

}
