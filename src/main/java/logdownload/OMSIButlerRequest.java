package logdownload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OMSIButlerRequest extends Message {

    public String ActionType;
    public String FileName;
    public String MemberId;
    public String UserId;
    public int ResendFromStart;

}
