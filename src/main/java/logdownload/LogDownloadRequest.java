package logdownload;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.WebUtils;


@Getter
@Setter
public class  LogDownloadRequest {
    private String actionType;
    private String memberId;
    private String userId;
    private String fileName;
    private Boolean isOMSI = false;
    private String requestKey;
    private String topic;

    public LogDownloadRequest(Request req, String actionType) {
        this.actionType = actionType;
        this.memberId = req.getMemberid();
        this.userId = req.getUserid();
        this.fileName = req.getFilename();
        if (StringUtils.isBlank(this.userId) || "OMSI".equalsIgnoreCase(this.userId)) {
            this.userId = "";
            this.isOMSI = true;
        }
        this.requestKey = generateKeyForResponseMap(memberId, userId, actionType);
        if (this.isOMSI) {
            this.topic = String.join("_", "RemoteComponent_Butler", memberId).replaceAll("\\s", "");
        } else {
            this.topic = String.join("_", "RemoteComponent_Traders", memberId, userId).replaceAll("\\s", "");
        }
    }

    public static String generateKeyForResponseMap(String memberId, String userId, String actionType) {
        String key = String.join("", memberId, userId, actionType);
        key = key.replaceAll("\\s", "");
        return key;
    }

    public void validateRequestActionType() throws Exception {
        if (!"DIR".equalsIgnoreCase(this.actionType) && !"GET".equalsIgnoreCase(this.actionType))
            throw new Exception("Invalid action type.");
    }
}
