package logdownload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Request {
    public String memberid;
    public String userid;
    public String filename;
    public String loghutUrl;
}


