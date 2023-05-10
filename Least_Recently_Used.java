import java.util.*;
class Least_Recently_Used{
  public static ArrayList<Page> lru(ArrayList<Page> pageArrayList){
    Page page = new Page();
    int lru = (int)pageArrayList.get(0).last_used;
    for(Page p : pageArrayList){
      if(p.last_used < lru){
        page = p;
        lru = (int)p.last_used;
      }
    }
    page.pid = -1;
    page.page_no = -1;
    pageArrayList.set(page.index,page);
    return pageArrayList;
  }
}