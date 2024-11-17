import { ajax } from '/js/common.js';
    async function findAllComment() {
      const bbsId = document.getElementById('bbsId').value; // 게시글 번호 가져오기
      const url = `http://localhost:9070/api/replybbs/${bbsId}`; // 댓글 API URL
      try {
        const comments = await ajax.get(url); // 댓글 목록 가져오기
        displayComments(comments); // 댓글 목록을 화면에 표시
      } catch (error) {
        console.error(error.message);
      }
    }

    function displayComments(response) {
      const replyWrap = document.getElementById('replyWrap'); // 댓글을 표시할 요소
      replyWrap.innerHTML = ''; // 기존 댓글 초기화

      const comments = response.body; // response.body에서 댓글 목록 가져오기

      if (comments && comments.length > 0) {
        comments.forEach(comment => {
          const commentElement = createLiReadMode(comment); // 댓글 요소 생성
          replyWrap.appendChild(commentElement); // 댓글 요소를 컨테이너에 추가
        });
      } else {
        replyWrap.innerHTML = '<p>댓글이 없습니다.</p>'; // 댓글이 없을 경우 메시지 표시
      }
    }

    // 댓글 추가 기능
    document.getElementById('btnAdd').addEventListener('click', async () => {

      const textarea = document.getElementById('comment'); // textarea
      const errorMessageDiv = document.getElementById('error-message'); // 에러 메시지 div
      const errorBlankArea = document.getElementById('errorBlankArea');

      // 유효성 체크 (textarea가 비었는지)
      if (textarea.value.trim() === '') { // 댓글란이 공백인 경우
        errorMessageDiv.textContent = '댓글을 입력해주세요.'; // 에러 메시지 설정
        errorBlankArea.style.display = 'block';
        errorMessageDiv.style.display = 'block'; // 에러 메시지 표시
        return;
      } else {
        errorMessageDiv.style.display = 'none'; // 에러 메시지 숨기기
      }

      const bbsId = document.getElementById('bbsId').value;
      const writer = document.querySelector('input[name="writer"]').value; // 작성자
      const content = document.getElementById('comment').value; // 댓글 내용

      const url = `http://localhost:9070/api/replybbs`; // 댓글 등록 API URL
      const data = { bbsId, writer, comments: content };

      try {
        await ajax.post(url, data); // AJAX POST 요청
        document.getElementById('comment').value = ''; // 댓글 입력란 초기화
        findAllComment(); // 댓글 목록 새로 고침
      } catch (error) {
        console.error(error.message);
      }
    });

    // 페이지가 로드될 때 댓글 목록을 불러오기
    window.onload = function () {
      findAllComment();
    };

    // 댓글 수정 및 삭제 이벤트
    const replyWrap = document.getElementById('replyWrap');
    replyWrap.addEventListener('click', async (e) => {
      if (e.target.tagName !== 'BUTTON') return;

      const targetLi = e.target.closest('li');
      const replyId = targetLi.getAttribute('data-reply-id'); // 댓글 ID 가져오기

      if (e.target.classList.contains('modifyBtn')) {

        // 수정 버튼 클릭
        const writer = targetLi.querySelector('strong').textContent; // 댓글 작성자
        const textOfReadMode = targetLi.querySelector('.comments').textContent; // 수정 전 댓글 내용 저장
        const $li = createLiModifyMode(textOfReadMode, replyId, writer); // 수정 폼 생성
        targetLi.parentNode.replaceChild($li, targetLi);


      } else if (e.target.classList.contains('delBtn')) {
        // 삭제 버튼 클릭
        if (!confirm('삭제하시겠습니까?')) return;

        try {
          await ajax.delete(`http://localhost:9070/api/replybbs/${replyId}`); // ajax함수: delete 요청
          findAllComment(); // 댓글 목록 새로 고침
        } catch (error) {
          console.error(error.message);
        }


      } else if (e.target.classList.contains('saveBtn')) {
        // 저장 버튼 클릭
        const modifiedContent = targetLi.querySelector('textarea').value;
        try {
          await ajax.patch(`http://localhost:9070/api/replybbs/${replyId}`, { comments: modifiedContent }); // ajax 함수 : patch 요청
          findAllComment(); // 댓글 목록 새로 고침
        } catch (error) {
          console.error(error.message);
        }


      } else if (e.target.classList.contains('cancelBtn')) {
        // 취소 버튼 클릭
        const initialContent = targetLi.querySelector('textarea').getAttribute('data-initial');
        const writer = targetLi.getAttribute('data-writer'); // 작성자 정보 복원

        const $li = createLiReadMode({ comments: initialContent, writer, reply_id: replyId }); // 수정된 댓글 내용과 작성자 설정
        targetLi.parentNode.replaceChild($li, targetLi);
      }
    });

    // 댓글 읽기 모드 생성 함수
    function createLiReadMode(comment) {
      const $li = document.createElement('li');
      $li.setAttribute('data-reply-id', comment.reply_id); // 댓글 ID 설정
      $li.setAttribute('data-writer', comment.writer); // 작성자 정보 저장

      // 로그인 된 유저 별칭을 회원정보 영역에서 가져옴
      const userNicknameEle = document.getElementById('userNickname');

      const userNickname = userNicknameEle ? userNicknameEle.textContent : ''; // 정말 혹시나 userNickname이 비어있을경우를 대비

      //userNickName과 comment.writer(댓글작성자)가 같다면 isWriter : true. 이를 이용해 수정/삭제버튼 유무를 결정
      const isWriter = userNickname === comment.writer;

      console.log('로그인 유저 별칭:', userNickname);
      console.log('작성자 확인:', isWriter);

      const createdAt = new Date(comment.cdate); // cdate를 사용하여 날짜 객체 생성
      const formattedDate = `${createdAt.getFullYear()}년 ${createdAt.getMonth() + 1}월 ${createdAt.getDate()}일 ${createdAt.getHours()}:${createdAt.getMinutes().toString().padStart(2, '0')}`;
             
      //수정,삭제버튼은 isWriter가 true일때만 보여야함
      $li.innerHTML = `
        <div class="comment-header"><span class="writer"><strong>${comment.writer}</strong></span><span class="date">${formattedDate}</span></div>
        <div class="comment-body"><span class="comments">${comment.comments}</span>
        <span class="innerBtn">${isWriter ? `<button class="btn modifyBtn">수정</button><button class="btn delBtn">삭제</button>` : ''}</span></div>`;
      
        return $li;
    }

    // 댓글 수정 모드 생성 함수
    function createLiModifyMode(text, replyId, writer) {
      const $li = document.createElement('li');
      $li.setAttribute('data-reply-id', replyId); // 댓글 ID 설정
      $li.setAttribute('data-writer', writer); // 작성자 정보 저장

      $li.innerHTML = `<textarea cols="30" rows="3" class="modifyTextarea" data-initial="${text}">${text}</textarea>
        <span class="innerBtn">
            <button class="btn saveBtn">저장</button>
            <button class="btn cancelBtn">취소</button>
        </span>`;
      return $li;
    }











