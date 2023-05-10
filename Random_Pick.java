import java.util.*;

//
class Random_Pick{
  public static ArrayList<Page> randomPick(ArrayList<Page> pageArrayList){
    int count =0;
    Random rand = new Random();
    int random = rand.nextInt(Main.PAGE_LIST_SIZE);
    Page page = new Page();
    for(Page p : pageArrayList){
      if(p.pid > 0 && count < random){
        page = p;
      }
      count++;
    }
    page.pid = -1;
    page.page_no = -1;
    pageArrayList.set(page.index,page);
    return pageArrayList;
  }
}
