package xyz.mostro.mytomcat.web;

import lombok.Data;

import java.util.Date;

/**
 * @Author: MOSTRO
 */
@Data
public class HttpResponse {

    private int status = 200;

    private String contentEncoding = "gzip";

    private String contentType = "text/html;charset=UTF-8";

    private Date date = new Date();

    private String server = "openresty";

    private String vary = "Accept-Encoding";

    @Override
    public String toString() {
        return "status: " + status +
                "\ncontentEncoding: " + contentEncoding +
                "\ncontentType: " + contentType +
                "\n date: " + date +
                "\nserver: " + server +
                "\nvary: " + vary+"\n";
    }
}
