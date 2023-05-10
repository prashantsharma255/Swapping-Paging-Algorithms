import java.util.*;

class First_Come_First_Serve{
  public static ArrayList<Page> fcfs(ArrayList<Page> pageArrayList){
    Page page = pageArrayList.get(0);
    for(Page p : pageArrayList){
      if(p.brought_in_time < page.brought_in_time){
        page = p;
      }
    }
    System.out.println("EVICTED: "+page.index+"  "+page.count+"  "+page.last_used);
    page.pid = -1;
    page.page_no = -1;
    pageArrayList.set(page.index,page);
    return pageArrayList;
  }
}