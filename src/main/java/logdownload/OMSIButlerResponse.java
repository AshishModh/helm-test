package logdownload;

import jdk.internal.org.objectweb.asm.ClassWriter;
import lombok.Getter;

import java.nio.file.attribute.AclEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class OMSIButlerResponse extends Message {

    private OMSIButlerRequest request;
    private Boolean isLastPart;
    private ClassWriter file;
    private String result;

    public OMSIButlerResponse(String MemberId, String UserId, String ActionType) {
        super(MemberId, UserId, ActionType);
    }

    public void setRequest(OMSIButlerRequest request) {
        this.request = request;
    }

    public OMSIButlerRequest getRequest() {
        return request;
    }

    public Boolean getIsLastPart() {
        return isLastPart;
    }

    public ClassWriter getFile() {
        return file;
    }

    public String getResult() {
        return result;
    }


    public List<LogDownload> getButlerFileEntriesList() {
       LogDownload download = new LogDownload("FILE", "ABCD/XYZ", "03/23/2023  07:26 AM", "1234");
       List<LogDownload> logDownloadList = new ArrayList<>();
       logDownloadList.add(download);
       return logDownloadList;
    }

}
