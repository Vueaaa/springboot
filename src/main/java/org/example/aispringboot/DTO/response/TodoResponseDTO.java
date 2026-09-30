package org.example.aispringboot.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TodoResponseDTO {

    private Long id;
    private String title;
    private Boolean completed;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
