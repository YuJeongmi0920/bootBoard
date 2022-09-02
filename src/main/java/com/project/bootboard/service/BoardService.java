package com.project.bootboard.service;

import com.project.bootboard.dto.Board;
import com.project.bootboard.dto.BoardFile;
import com.project.bootboard.dto.BoardForm;
import com.project.bootboard.dto.PageNationDto;
import com.project.bootboard.mapper.BoardFileMapper;
import com.project.bootboard.mapper.BoardMapper;
import com.project.bootboard.util.PageNationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardService {

    private final BoardMapper boardMapper;
    private final ResourceLoader resourceLoader;
    private final BoardFileMapper boardFileMapper;


    public Map<String, Object> getBoardList(String current) {
        // 보드 총갯수
        int total = boardMapper.getBoardTotal();
        // 만들어논 메서드
        PageNationDto pageNation = PageNationUtil.getPageNation(current, total, "/board/list", 10);
        // 보드리스트 가져오기
        List<Board> boardList = boardMapper.getBoardList(pageNation.getBeginRow(), pageNation.getRowPerPage());
        // 담을통
        Map<String, Object> map = new HashMap<>();
        map.put("pageNation", pageNation);
        map.put("boardList", boardList);
        log.info(boardList);
        return map;
    }

    @Transactional // 트랜잭션 편리하게 해줌 롤백처리
    public Board boardDetail(int boardNo) {
        // 조회수오르고
        boardMapper.showUp(boardNo);
        //보드상세 갖다주고
        return boardMapper.boardDetail(boardNo);
    }

//    public int boardUpdate(Board boardDto) {
//        return boardMapper.boardUpdate(boardDto);
//    }

    public int boardDelete(int boardNo) {
        return boardMapper.boardDelete(boardNo);
    }


    @Transactional
    public void addBoard(BoardForm boardForm) {
        // 일단 db에 인서트하기
        int row = boardMapper.insertBoard(boardForm.getBoard());
        // 인서트를 성공하고 파일업로드를 했다면
        if (boardForm.getMultiList() != null) {
            // 해당 파일리스트 for문
            for (MultipartFile mf : boardForm.getMultiList()) {
                // 파일 객체 만들어서 채우기
                BoardFile boardFile = new BoardFile();
                boardFile.setBoardNo(boardForm.getBoard().getBoardNo());
                boardFile.setOriginName(mf.getOriginalFilename());
                boardFile.setFileType(mf.getContentType());
                boardFile.setFileSize(mf.getSize());
                // UUID를 통해서 고유한 파일이름을 만들어낸다 이유는 ? 파일이름이 중복되면 안되니까
                // UUID란 랜덤한 키 중복될 확률 거의 없다고 보면 된다 ex) 550e8400-e29b-41d4-a716-446655440000-파일명.jpg
                // 확장자 구하는거 ex) .jpg .jpeg .ext .exe 등
                String ext = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
                String fileName = UUID.randomUUID().toString().replace("-", "") + ext;

                // 리소스는 현재 프로젝트 경로 가져와주기
                Resource resource = resourceLoader.getResource("/resources/upload");
                Path path = null;
                try {
                    path = Paths.get(resource.getURI());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                boardFile.setFileName("/resources/upload/" + fileName);

                // 파일 객체 전달해서 파일정보 insert
                boardFileMapper.insertBoardFile(boardFile);

                try {
                    mf.transferTo(new File(path + "/" + fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException();    // 트랜잭션 처리가 되도록 강제로 Runtime 예외(try 절을 강요하지 않는)발생
                }
            }
        }
    }

    public void fileDelete(String fileName) {
        int num = boardFileMapper.fileDelete(fileName);
        if (num > 0) {
            Resource resource = resourceLoader.getResource("");
            try {
                System.out.println("삭제했냐 ? ");
                URI uri = resource.getURI();
                String result = uri + fileName.substring(1);
                result = result.substring(6);
                System.out.println(result);
                Path path = Paths.get(result);
                Files.deleteIfExists(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
