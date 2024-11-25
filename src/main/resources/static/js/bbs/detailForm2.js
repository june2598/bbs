import { ajax,  PaginationUI} from '/js/common.js';

const $list = document.createElement('div');
$list.setAttribute('id','list')
document.body.appendChild($list);

const divEle = document.createElement('div');
divEle.setAttribute('id','reply_pagenation');
document.body.appendChild(divEle);

(async ()=>{
  const url = '/api/products/totalCnt';
  try {
    const result = await ajax.get(url);
    
    const totalRecords = result.totalCnt; // 전체 레코드수
    const recordsPerPage = 10;            // 페이지당 레코드수
    const pagesPerPage = 10;              // 한페이지당 페이지수

    const handlePageChange = (reqPage)=>{
      return getProducts(reqPage,recordsPerPage);
    };

    // Pagination UI 초기화
    var pagination = new PaginationUI('reply_pagenation', handlePageChange);
    
    pagination.setTotalRecords(totalRecords);
    pagination.setRecordsPerPage(recordsPerPage);
    pagination.setPagesPerPage(pagesPerPage);

    // 첫페이지 가져오기
    pagination.handleFirstClick();   

  }catch(err){
    console.error(err);
  }
})();
