package logdownload;


import org.springframework.web.bind.annotation.GetMapping;
import spark.Response;
import spark.route.Routes;

import java.util.HashMap;

import static spark.Spark.get;

public class LogDownloadRoutes extends Routes {
    private LogDownloadService logDownloadService;
    private static final String LOG_DOWNLOAD_WIZARD_URL = "/api/helm/wizard/log_download";

    protected LogDownloadRoutes(LogDownloadService logDownloadService) {
        this.logDownloadService = logDownloadService;
    }

    @GetMapping(produces = "application/json")
    public LogDownloadWrapper butlerProcess(Request req){
        return logDownloadService.butlerProcess(new LogDownloadRequest(req, "DIR"));
    }

    @GetMapping(value = "/saveloghut" , produces = "application/json")
    public LogDownloadWrapper butlerProcess1(Request req){
        return logDownloadService.butlerProcess(new LogDownloadRequest(req, "GET"));
    }
//
//    @GetMapping("/loghutdownload")
//    public String downloadLoghut(spark.Request req, Response res){
//       return logDownloadService.downloadLogHut(req,res);
//    }
//
//    public void registerRoutes() {
//
//        get(LOG_DOWNLOAD_WIZARD_URL, (req, res) -> {
//            res.type("application/json");
//            return logDownloadService.butlerProcess(new LogDownloadRequest(req, "DIR"));
//        }, new JsonTransformer());
//
//        get(LOG_DOWNLOAD_WIZARD_URL + "/saveloghut", (req, res) -> {
//            res.type("application/json");
//            return logDownloadService.butlerProcess(new LogDownloadRequest(req, "GET"));
//        }, new JsonTransformer());
//
//        get(LOG_DOWNLOAD_WIZARD_URL + "/loghutdownload", (req, res) -> {
//            logDownloadService.downloadLogHut(req, res);
//            return new HashMap<>();
//        }, Object::toString);
//
//    }

}
