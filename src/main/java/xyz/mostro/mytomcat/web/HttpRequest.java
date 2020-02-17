package xyz.mostro.mytomcat.web;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: MOSTRO
 */
public class HttpRequest {
    private String method;

    private String url;

    private String version;

    private final Map<String, String> headerParameters = new HashMap<>(64);

    public HttpRequest() {

    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getHeaderParameters() {
        return headerParameters;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", version='" + version + '\'' +
                ", headerParameters=" + headerParameters.toString() +
                '}';
    }
}
