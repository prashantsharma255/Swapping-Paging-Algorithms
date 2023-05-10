import java.util.*;

class Page{
  public int pid;
  public int index;
  public int page_no;
  public double brought_in_time;
  public double last_used;
  public int count;

  public Page(int index){
    pid = -1;
    page_no = -1;
    this.index = index;
  }

  public Page(){
    pid = -1;
    page_no = -1;
    
  }

  public ArrayList<Page> init_page_list(){
    ArrayList<Page> temp = new ArrayList<>();
    for(int i=0;i<Main.PAGE_LIST_SIZE;i++){
        temp.add(new Page(i));
    }
    return temp;
  }

  public void display_page_list(ArrayList<Page> temp){
    int counter = 0;
    for(int i=0;i<temp.size();i++){
      Page t = temp.get(i);
      if(t.count == 0){
        System.out.print("Page "+counter+" .|");
        counter++;
      }else{
        System.out.println();
        if(t.pid > 0){
          System.out.print("pid: "+t.pid+"\t"+"count: "+t.count+"\t"+String.format("last used: %.1f| ",t.last_used));
        }
        else{
          System.out.print("\t"+"count: "+t.count+"\t"+String.format("last used: %.1f| ",t.last_used));
        }
        if(i%10==0){
          System.out.println(" ");
        }

      }

    }
  }

  public boolean test_free_pages(ArrayList<Page> temp,int count){
    for(Page t : temp){
      if(t.pid == -1)
      count--;
      if(count == 0)
      return true;
    }
    return false;
  }

  public boolean page_exists_in_memory(ArrayList<Page> pl,int pid , int page_no){
    for(Page t : pl){
      if(t.pid == pid && t.page_no == page_no){
        return true;
      }
    }
    return false;
  }

  public Page get_free_page(ArrayList<Page> pl){
    for(int i=0;i<pl.size() ;i++){
      if(pl.get(i).pid == -1){
        return pl.get(i);
      }
    }
    return null;
  }

  public void free_memory(ArrayList<Page> pl, int pid){
    for(Page p : pl){
      if(p.pid == pid){
        p.pid = -1;
        p.page_no = -1;
      }
    }
  }
  //principle of locality algorithm
  public int get_next_page_no(int curr_page_no,int max_page_size){
    Random rand = new Random();
    int x = rand.nextInt(10);
    if(x < 7){
      x = curr_page_no+(rand.nextInt(3))-1;
    }
    else{
      x = rand.nextInt(max_page_size);
      while(Math.abs(x - curr_page_no) <= 1)
        x = rand.nextInt(max_page_size);
    }
    if(x < 0)
      x = 0;
    if(x >= 100)
      x = max_page_size-1;
    return x;
  }

  public Page get_page_from_pid(ArrayList<Page> pl , int pid, int page_no){
    for(Page p : pl){
      if(p.pid == pid && p.page_no == page_no){
        return p;
      }
    }
    return null;
  }

  public ArrayList<Page> updatePageList(Page p,ArrayList<Page> pl){
    pl.set(p.index,p);
    return pl;
  }

  public void printMemoryMap(ArrayList<Page> pl) {
    System.out.println("Memory map is");
    for (Page p: pl) {
      if (p.pid == -1) {
        System.out.print(".|");
      }
      else
      {
        System.out.print(p.pid + "|");
      }
    }
    System.out.println("\n");
  }
}