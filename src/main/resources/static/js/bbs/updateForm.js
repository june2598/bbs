const btnCancelEle = document.getElementById('btnCancel');
const bbsIdEle = document.getElementById('bbsId');

// //저장
// btnUpdateEle.addEventListener('click',e=>{
// const frmEle = document.getElementById('frm');
// frmEle.submit();
// },false);

//취소
btnCancelEle.addEventListener('click', e => {
    //history.go(-1); 브라우저에서 뒤로가기 버튼
    location.href = `/bbs/${bbsIdEle.value}/`;
}, false);
