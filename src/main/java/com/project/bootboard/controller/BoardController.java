package com.project.bootboard.controller;

import com.project.bootboard.dto.BoardDto;
import com.project.bootboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor // 사실 생성자로 받아야 한다 대신 해준다
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    // 주소는 대시케이스로 하자
    @GetMapping("/list") // 파라미터 받는법
    public String boardList(@RequestParam(required = false, value = "current") String current,
                            @ModelAttribute("check") String check,
                            Model model) {
        Map<String, Object> map = boardService.getBoardList(current);
        // request.setAttribute();
        map.forEach((key, value) -> model.addAttribute(key, value));
        // 파일명
        return "/board/board-list";
    }

    // 입력폼
    @GetMapping("/insert-form")
    public String insertForm() {
        return "/board/board-insert-form";
    }

    @PostMapping("/insert")// 통으로 받기
    public String boardInsert(BoardDto boardDto) {
        log.info("%s", boardDto);
        boardService.boardInsert(boardDto);

        //리스트로 뿌려줘야 하니까
        return "redirect:/board/list?check=insert";
    }

    // 게시판 상세
    @GetMapping("/detail")
    public String boardDetail(@RequestParam("boardNo") int boardNo, Model model) {
        log.info(boardNo);
        BoardDto boardDto = boardService.boardDetail(boardNo);
        model.addAttribute("board", boardDto);
        return "/board/board-detail";
    }

    @PostMapping("/update")
    public String boardUpdate(BoardDto boardDto, Model model) {
        log.info(boardDto);
        boardService.boardUpdate(boardDto);

        // 성공하면 알림 띄워주고 디테일로 감 (파일이 아니라 주소로)
        model.addAttribute("msg", "수정 성공!");
        model.addAttribute("url", "/board/detail?boardNo=" + boardDto.getBoardNo());
        return "/etc/alert";
    }


    @GetMapping("/delete")
    public String boardDelete(@RequestParam("boardNo") int boardNo, Model model) {
        boardService.boardDelete(boardNo);
        model.addAttribute("msg", "삭제 성공!");
        model.addAttribute("url", "/board/list");
        return "/etc/alert";
    }


}
