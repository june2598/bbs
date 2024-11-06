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

