package com.project.bootboard.mapper;


import com.project.bootboard.dto.BoardFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardFileMapper {
    int insertBoardFile(@Param("boardFile") BoardFile boardFile);

    int fileDelete(@Param("fileName") String fileName);
}
