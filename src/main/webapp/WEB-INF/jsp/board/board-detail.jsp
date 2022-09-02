<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <!-- jQuery library -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <form action="/board/insert" method="post" enctype="multipart/form-data" id="board-form">
        <input type="hidden" name="board.boardNo" value="${board.boardNo}">
        <div class="mb-3">
            <h2 class="form-label">이미지</h2>
            <div class="d-flex">
                <c:forEach items="${board.boardFileList}" var="file">
                    <div class="imgBox">
                        <img style="width: 150px; height: 150px" src="${file}"
                             onerror="this.src = '/resource/img/images.png'">
                        <button fileName="${file}" type="button" class="btn-sm btn-primary imgDelBtn">삭제</button>
                    </div>
                </c:forEach>
            </div>
            <hr/>
        </div>
        <div class="mb-3">
            <h2 class="form-label">제목</h2>
            <input class="form-control" type="text" id="boardTitle" name="board.boardTitle" value="${board.boardTitle}">
            <hr/>
        </div>
        <div class="mb-3">
            <h2 class="form-label">내용</h2>
            <input class="form-control" type="text" id="boardContent" name="board.boardContent"
                   value="${board.boardContent}">
            <hr/>
        </div>
        <button class="btn btn-primary" type="button" id="addFile">파일추가</button> <!--  input type="file" -->
        <button class="btn btn-primary" type="button" id="removeFile">파일삭제</button>
        <div id="fileSection">
        </div>
        <div class="my-2">
            <button id="addButton" type="button" class="btn btn-primary">수정</button>
            <a href="/board/delete?boardNo=${board.boardNo}" class="btn btn-primary">삭제</a>
            <a class="btn btn-primary" href="/board/list">리스트로</a>
        </div>
    </form>
</div>
<script>

    document.querySelector("#removeFile").addEventListener('click', function (ev) {
        document.querySelector("#fileSection").innerHTML = "";
    })

    document.querySelector("#addFile").addEventListener('click', function (ev) {
        let div = document.createElement("div");
        let input = document.createElement("input");
        input.className = "mt-2 form-control multiList";
        input.name = "multiList";
        input.type = "file"
        div.appendChild(input);
        document.querySelector("#fileSection").appendChild(div);
    })

    // 유효성 검사
    document.querySelector("#addButton").addEventListener('click', function (ev) {
        let isFileEmpty = false;
        document.querySelectorAll(".multiList").forEach(ev => {
            if (ev.value == '') {
                isFileEmpty = true;
            }
        })
        let boardTitle = document.querySelector("#boardTitle");
        let boardContent = document.querySelector("#boardContent");
        if (boardTitle.value.trim() == '') {
            alert('제목을 입력해주세요')
            return;
        }
        if (boardContent.value.trim() == '') {
            alert('내용을 입력해주세요')
            return;
        }
        if (isFileEmpty) {
            alert("파일을 등록해주세요")
            return;
        }
        document.querySelector("#board-form").submit();
    })

    // 삭제 버튼
    document.querySelectorAll(".imgBox").forEach((e) => {
        e.addEventListener('click', function (ev) {
            if (ev.target.className === 'btn-sm btn-primary imgDelBtn') {
                let fileName = ev.target.getAttribute("fileName");
                let url = "/board/img-delete";
                fetch(url, {
                    headers: {
                        "Content-Type": "application/json"
                    },
                    method: "POST",
                    body: JSON.stringify({
                        fileName: fileName
                    })
                }).then(resp => resp.text())
                    .then(data => {
                        console.log(data)
                        if (data == 'ok') {
                            alert('삭제성공')
                            ev.target.parentElement.remove();
                        }
                    })
            }
        });
    })
</script>
</body>

</html>