//전체선택 체크박스 체크,해제에 따른 게시글 체크박스 전체선택/해제 동기화
const selectAllEle = document.getElementById('selectAll');
const selectEle = document.querySelectorAll("input[name='bbsIds']");

selectAllEle.addEventListener('click', e => {
    const Checked = selectAllEle.checked;
    selectEle.forEach(ele => {
        ele.checked = Checked;
    });
});
//초기화면 버튼 이벤트

const btnHome = document.getElementById('btnHome');
btnHome.addEventListener('click', e => {
    console.log('초기화면 버튼 클릭됨');
    location.href = 'http://localhost:9070/';
}, false);

//게시글작성버튼 이벤트
const btnAdd = document.getElementById('btnAdd');
btnAdd.addEventListener('click', e => {
    location.href = '/bbs/add';
}, false);

//게시글삭제 버튼 이벤트
const btnDelsEle = document.getElementById('btnDels');
btnDelsEle.addEventListener('click', e => {
    //체크박스에 체크가 된 모든 요소를 NodeList형태로 반환
    const checkedBoxes = document.querySelectorAll("input[name='bbsIds']:checked");

    //선택된 체크박스가 없는경우 체크 - 체크박스에 체크를 안하고 삭제버튼을 눌렀을시 경고창을 띄우고 리턴
    if (checkedBoxes.length === 0) {
        alert('삭제할 게시글이 선택되지 않았습니다.');
        return;
    }
    //삭제시 확인유무 체크 - 취소할시 리턴
    if (!confirm('삭제하시겠습니까')) return;
    document.getElementById('frm').submit();
});