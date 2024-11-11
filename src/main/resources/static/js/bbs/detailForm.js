const btnUpdateEle = document.getElementById('btnUpdate');
const btnDeleteEle = document.getElementById('btnDelete');
//수정
btnUpdateEle.addEventListener('click', e => {
    console.log('수정버튼 클릭됨');
    const bbsIdEle = document.getElementById('bbsId');
    location.href = `/bbs/${bbsIdEle.value}/edit`;
}, false);

//삭제
btnDeleteEle.addEventListener('click', e => {
    if (!confirm('삭제하시겠습니까')) return;

    const bbsIdEle = document.getElementById('bbsId');
    location.href = `/bbs/${bbsIdEle.value}/del`;
}, false);

let errdiv = null;
const $btnAdd = document.getElementById("btnAdd");
const $comment = document.getElementById("comment");
const $reply = document.getElementById("replyWrap");
$reply.addEventListener('click', e => {
    if (e.target.tagName != 'BUTTON') return;
    console.log(e.target);
    console.log(e.currentTarget);

    const targetLi = e.target.closest('li');
    const targetP = targetLi.closest('.replyInnerWrap');
    if (e.target.classList.contains('modifyBtn')) {
        console.log('수정버튼 클릭');
        const textOfReadMode = targetLi.querySelector('span').textContent; //수정전 댓글내용 저장
        console.log(textOfReadMode);
        const $li = createLiModifyMode(textOfReadMode); //수정전 댓글내용을 초기내용으로 하는 수정폼
        console.log(e.target.closest('li'));
        targetLi.parentNode.replaceChild($li, targetLi);
    } else if (e.target.classList.contains('delBtn')) {
        console.log('삭제버튼 클릭');
        if (!confirm('삭제하시겠습니까?')) return;
        targetP.parentNode.removeChild(targetP);
    } else if (e.target.classList.contains('saveBtn')) {
        console.log('저장버튼 클릭');
        const modifiedContent = targetLi.querySelector('textarea').value;
        const $li = createLiReadMode(modifiedContent);
        targetLi.parentNode.replaceChild($li, targetLi);
    } else if (e.target.classList.contains('cancelBtn')) {
        console.log('취소버튼 클릭');
        const initialContent = targetLi.querySelector('textarea').getAttribute('data-initial');
        const $li = createLiReadMode(initialContent);
        targetLi.parentNode.replaceChild($li, targetLi);
    }
}, false);

$btnAdd.addEventListener('click', e => {
    const btnAdd = document.getElementById('btnAdd'); // 등록 버튼
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
                   const $p = document.createElement('p');
                   $p.className = "replyInnerWrap";
                   $reply.appendChild($p);
                   const $span = document.createElement('span');
                   $span.className = "blankArea";
                   $p.appendChild($span);
                   const $li = createLiReadMode(comment.value);
                   $p.appendChild($li);
                   comment.value = '';
           }, false);
  



function createLiReadMode(text) {
    const $li = document.createElement('li');
    $li.innerHTML = `<span>${text}</span><span class="innerBtn"><button class="btn modifyBtn">수정</button><button class="btn delBtn">삭제</button></span>`;
    return $li;
}

function createLiModifyMode(text) {
    const $li = document.createElement('li');
    $li.innerHTML = `<textarea cols="30" rows="3" class="modifyTextarea" data-initial="${text}">${text}</textarea><span class="innerBtn"><button class="btn saveBtn">저장</button><button class="btn cancelBtn">취소</button></span>`;
    return $li;
}