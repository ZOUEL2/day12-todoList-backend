package oocl.example.todolistbackend.req;

import lombok.Data;

@Data
public class TodoUpdateReq {
    private String text;
    private Boolean done;
}
