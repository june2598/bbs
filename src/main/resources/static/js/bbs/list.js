//전체선택 체크박스 체크,해제에 따른 게시글 체크박스 전체선택/해제 동기화
const selectAllEle = document.getElementById('selectAll');
const selectEle = document.querySelectorAll("input[name='bbsIds']");
const activeBtnEle = document.querySelector('.active');


//active 페이징은 클릭이벤트를 막음
activeBtnEle.addEventListener('click', e =>{
    e.preventDefault();
    return false;
},false);


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
    console.log(checkedBoxes);

    //선택된 체크박스가 없는경우 체크 - 체크박스에 체크를 안하고 삭제버튼을 눌렀을시 경고창을 띄우고 리턴
    if (checkedBoxes.length === 0) {
        alert('삭제할 게시글이 선택되지 않았습니다.');
        return;
    }
    const userNicknameEle = document.getElementById('userNickname');         // 현재 로그인된 사용자의 별칭을 가져옴
    const userNickname = userNicknameEle ? userNicknameEle.textContent : ''; // 정말 혹시나 userNickname이 비어있을경우를 대비

    // 관리자 계정인지 확인
    const isAdmin = document.getElementById('adminInfo') !== null;

    // 체크된 게시글의 작성자 (별칭)를 확인
    const invalidPosts = Array.from(checkedBoxes).filter(e => {         //  NodeList 타입의 checkedBoxes를 실제 배열로 변환. filter : 주어진 조건을 만족하는 요소들로만 이루어진 새로운 배열 생성
        const checkedRow = e.closest('tr'); // 체크박스가 포함된 행 찾기
        const writerNickname = checkedRow.querySelector('.writer').textContent; // 작성자 별칭 가져오기


        // 관리자 계정인 경우 별칭 비교를 스킵. (다른 사람의 글도 다 삭제 가능하게)
        if (isAdmin) {
            return false; // invalidPosts에 포함되지 않음
        }
        
        return writerNickname !== userNickname; // 서로 다를 경우 true를 반환하여 해당 체크박스를 invalidPosts 배열에 포함시키고, 같으면 false를 반환하여 제외시킴
    });

    // 관리자가 아니면서, 다른 사용자의 게시글이 체크된 경우 경고 
    if (!isAdmin && invalidPosts.length > 0) {
        alert('본인이 작성한 게시글만 삭제할 수 있습니다.');
        return;
    }


    //삭제시 확인유무 체크 - 취소할시 리턴
    if (!confirm('삭제하시겠습니까')) return;
    document.getElementById('frm').submit();
});

