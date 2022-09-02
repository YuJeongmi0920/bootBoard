package com.project.bootboard.mapper;

import com.project.bootboard.dto.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<Board> getBoardList(@Param("beginRow") int beginRow,
                             @Param("rowPerPage") int rowPerPage);

    int getBoardTotal();


    int insertBoard(@Param("board") Board board);

    Board boardDetail(@Param("boardNo") int boardNo);

    int showUp(@Param("boardNo") int boardNo);



    int boardDelete(@Param("boardNo") int boardNo);
}
