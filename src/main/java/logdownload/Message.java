package logdownload;

import jdk.internal.org.objectweb.asm.ClassWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Message{
    public String MemberId;
    public String UserId;
    public String ActionType;

    public Message() {
    }
}
