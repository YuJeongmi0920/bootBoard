package com.project.bootboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardFile {
    private int boardFileNo;
    private int boardNo;
    private String fileName;
    private String originName;
    private String fileType;
    private long fileSize;
}
