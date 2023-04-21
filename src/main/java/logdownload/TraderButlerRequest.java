package logdownload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TraderButlerRequest extends Message {

    public String ActionType;
    public String FileName;
    public String MemberId;
    public String UserId;

    public TraderButlerRequest(String MemberId, String UserId, String ActionType) {
        super(MemberId, UserId, ActionType);
    }

}
