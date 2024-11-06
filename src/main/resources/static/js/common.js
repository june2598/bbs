function getBytesSize(str) {
  const encoder = new TextEncoder();
  const byteArray = encoder.encode(str);
  return byteArray.length;
}

export { getBytesSize };

//목록
const btnListAllEle = document.getElementById('btnListAll');
btnListAllEle.addEventListener('click', e => {
    console.log('목록버튼 클릭됨');
    location.href = '/bbs';
}, false);

