package logdownload;

import jdk.internal.org.objectweb.asm.ClassWriter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.attribute.AclEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class TraderButlerResponse extends Message {


    private TraderButlerRequest request;
    private ClassWriter file;
    private Boolean isLastPart;
    private String result;

    public TraderButlerResponse(String MemberId, String UserId, String ActionType) {
        super(MemberId, UserId, ActionType);
    }

    public TraderButlerRequest getRequest() {
        return request;
    }

    public void setRequest(TraderButlerRequest request) {
        this.request = request;
    }

    public ClassWriter getFile() {
        return file;
    }

    public Boolean getIsLastPart() {
        return isLastPart;
    }

    public String getResult() {
        return result;
    }

    public static List<LogDownload> getButlerFileEntriesList() {
        LogDownload download = new LogDownload("FILE", "ABCD/BCD", "03/23/2023  07:26 AM", "1024");
        List<LogDownload> logDownloadList = new ArrayList<>();
        logDownloadList.add(download);
        return logDownloadList;
    }

}
