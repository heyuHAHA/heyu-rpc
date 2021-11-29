package rpc;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;


@Data
public class DefaultRequest implements Request{
    private String interfaceName;
    private String methodName;
    private Object[] arguments;
    private Map<String,String> attachments;
    private int retires = 0;
    private long requestId;
    private String paramtersDesc;

    public void setAttachment(String name, String value) {
        if (this.attachments == null) {
            this.attachments = new HashMap<>();
        }
        attachments.put(name,value);
    }

}
