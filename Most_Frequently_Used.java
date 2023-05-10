import java.util.*;

class Most_Frequently_Used{
  public static ArrayList<Page> mfu(ArrayList<Page> pageArrayList){
    Page page = new Page();
    int max = pageArrayList.get(0).count;
    for(Page p : pageArrayList){
      if(p.count > max){
        page = p;
        max = p.count;
      }
    }
    page.pid = -1;
    page.page_no = -1;
    pageArrayList.set(page.index,page);
    return pageArrayList;
  }
}