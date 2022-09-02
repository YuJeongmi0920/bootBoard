package com.project.bootboard.controller;

import com.project.bootboard.dto.Board;
import com.project.bootboard.dto.BoardFile;
import com.project.bootboard.dto.BoardForm;
import com.project.bootboard.service.BoardService;
import lombok.RequiredArgsConstructor;
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
        return "/board/insert-form";
    }



    @PostMapping("/insert")
    public String addBoard(BoardForm boardForm, Model model) {
        System.out.println(boardForm);
        boardService.addBoard(boardForm);
        int boardNo = boardForm.getBoard().getBoardNo();
        if (boardNo != 0) {
            model.addAttribute("msg", "입력성공");
            model.addAttribute("url", "/board/detail?boardNo=" + boardNo);
            return "/etc/alert";
        }
        return "redirect:/board/list";
    }

    // 게시판 상세
    @GetMapping("/detail")
    public String boardDetail(@RequestParam("boardNo") int boardNo, Model model) {
        log.info(boardNo);
        Board board = boardService.boardDetail(boardNo);
        model.addAttribute("board", board);
        return "/board/board-detail";
    }



    @GetMapping("/delete")
    public String boardDelete(@RequestParam("boardNo") int boardNo, Model model) {
        boardService.boardDelete(boardNo);
        model.addAttribute("msg", "삭제 성공!");
        model.addAttribute("url", "/board/list");
        return "/etc/alert";
    }


    // 주소는 대시케이스로 하자
    @ResponseBody
    @PostMapping("/img-delete") // 파라미터 받는법
    public String boardList(@RequestBody BoardFile boardFile) {
        System.out.println(boardFile);
        boardService.fileDelete(boardFile.getFileName());
        return "ok";
    }



}
