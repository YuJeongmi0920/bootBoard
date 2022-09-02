<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <!-- jQuery library -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
    <!-- Popper JS -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <form action="/board/insert" method="post" enctype="multipart/form-data" id="board-form">
        <div class="mb-3">
            <label for="boardTitle" class="form-label">제목</label>
            <input type="text" class="form-control" id="boardTitle" name="board.boardTitle">
        </div>
        <div class="mb-3">
            <label for="boardContent" class="form-label">내용</label>
            <textarea class="form-control" id="boardContent" name="board.boardContent" rows="5"></textarea>
        </div>
        <button class="btn btn-primary" type="button" id="addFile">파일추가</button> <!--  input type="file" -->
        <button class="btn btn-primary" type="button" id="removeFile">파일삭제</button>
        <div id="fileSection">
        </div>
        <button class="btn btn-primary my-2" type="button" id="addButton">등록</button>
    </form>
</div>
<form enctype="multipart/form-date" method="post" action="/insert">


</form>
<script>
    $(document).ready(function () {
        $('#removeFile').click(function () {
            $('#fileSection').empty();
        })
    })

    $('#addFile').click(function () {
        let html = '<div><input class="mt-2 form-control multiList" type="file" name="multiList"></div>';
        $('#fileSection').append(html);
    });
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

</script>
</body>
</html>
