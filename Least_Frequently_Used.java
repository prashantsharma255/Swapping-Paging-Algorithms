import java.util.*;

class Least_Frequently_Used{
  public static ArrayList<Page> lfu(ArrayList<Page> pageArrayList){
    Page page = new Page();
    int min_count = pageArrayList.get(0).count;
    for(Page p : pageArrayList){
      if(p.count < min_count){
        page = p;
        min_count = p.count;
      }
    }
    page.pid = -1;
    page.page_no = -1;
    pageArrayList.set(page.index,page);
    return pageArrayList;
  }
}