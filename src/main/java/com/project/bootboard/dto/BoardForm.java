package com.project.bootboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardForm {
    public Board board;
    private List<MultipartFile> multiList;
}
