package com.project.bootboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Board {
    private int boardNo;
    private String boardTitle;
    private String boardContent;
    private String boardDate;
    private int boardRead;
    private List<String> boardFileList;
}
