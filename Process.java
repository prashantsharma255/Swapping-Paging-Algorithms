//Process class: data structure for Processes
class Process{
  public int pid;
  public int process_size_in_pages;
  public int arrival_time;
  public int service_duration;
  public int curr_page;

  public Process(int pid, int page_count,int arrival_time,int duration,int curr_page){
    this.pid = pid;
    this.process_size_in_pages = page_count;
    this.arrival_time = arrival_time;
    this.service_duration = duration;
    this.curr_page = curr_page;
  }
}
