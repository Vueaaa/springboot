package org.example.aispringboot.DTO.command;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TodoUpdateCommandDTO {

    @Size(max = 200, message = "待办内容不能超过200个字符")
    private String title;

    private Boolean completed;
}
