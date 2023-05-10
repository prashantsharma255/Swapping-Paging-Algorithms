import java.util.*;

class Main {

 public static class AlgoOutput {
  public double hitmissRatio;
  public int swappedInProcesses;
 }

 public static int NUMBER_OF_PROCESSES = 150;
 public static int SIMULATION_DURATION = 60;
 public static int MAX_PROCESSES_DURATION = 5;
 public static int PAGE_LIST_SIZE = 100;
 public static int hit_miss_ratio_per_run;
 private static int page_count_options[] = {5, 11, 17, 31};
 private static String[] algoTypes = new String[] {"Random", "FIFO", "LRU", "LFU", "MFU"};
 private static Random rand = new Random();

 public static void main(String[] args) {
  AlgoOutput[][] algoOutputs = new AlgoOutput[5][5];
  for (int iteration = 0; iteration < 5; iteration++) {
   Process processes[] = generateProcesses();
   for (int algoType = 0; algoType < 5; algoType++) {
    Process[] copy = createCopy(processes);
    System.out.println("************ Starting execution of " + algoTypes[algoType] +  " algorithm for " + iteration + " loop ************");
    algoOutputs[algoType][iteration] = runAlgorithm(algoType, copy);
   }
  }

  for (int i = 0; i < 5; i++) {
   System.out.println("\nAlgo Type: " + algoTypes[i]);
   double hitRatioSum = 0;
   int swappedInProcessesSum = 0;
   for (int j = 0; j < 5; j++)
   {
    hitRatioSum += algoOutputs[i][j].hitmissRatio;
    swappedInProcessesSum += algoOutputs[i][j].swappedInProcesses;
   }

   System.out.println("Hit ratio average: " + hitRatioSum / 5);
   System.out.println("Average swapped in processes: " + swappedInProcessesSum / 5 + "\n\n");
  }
 }

 static AlgoOutput runAlgorithm(int algoType, Process[] processes) {
  int simulation_clock;
  int swapped_in_processes = 0;
  int hit = 0, miss = 0;
  hit_miss_ratio_per_run = 0;

  Page current_page = new Page();
   Page obj = new Page(); //obj represents RAM
   ArrayList<Page> page_list = obj.init_page_list();

   int index_of_process_q = 0; // index to the start of process queue

   //for loop to execute the simulation for 60 seconds
   for (simulation_clock = 0; simulation_clock < SIMULATION_DURATION; simulation_clock++) {

    //At the beginning of every second, check for new incoming processes
    int numberOfStartedProcesses = startProcesses(index_of_process_q, processes, simulation_clock, obj, page_list);
    swapped_in_processes += numberOfStartedProcesses;
    index_of_process_q += numberOfStartedProcesses;

    // for every 100ms all processes in memory will request a new page
    for (int k = 1; k < 10; k++) {
     for (int j = 0; j < index_of_process_q; j++) {
      if (processes[j].service_duration <= 0) continue;

      //obj.get_next_page_no = principle of locality algorithm TODO:remove comment!
      processes[j].curr_page = obj.get_next_page_no(processes[j].curr_page, processes[j].process_size_in_pages); // get next page no

      if (obj.page_exists_in_memory(page_list, processes[j].pid, processes[j].curr_page)) { //check if page exists in memory
       current_page = obj.get_page_from_pid(page_list, processes[j].pid, processes[j].curr_page);
       hit++;
       current_page.count++;
       current_page.last_used = simulation_clock;
       page_list = obj.updatePageList(current_page, page_list);

       System.out.println("Time " + (simulation_clock + (0.1 * k)) + ": Page " + processes[j].curr_page + " for process " + processes[j].pid + " already exists in memory at location " + current_page.index);
      }
      else {
       boolean freePageAvail = true;
       //Page was not in memory, so use below page replacement algorithms to get the page in memory.
       Page free_page = obj.get_free_page(page_list);

       // There are no free pages in memory, evict the existing page based on below algorithms to fetch a new page
       if (free_page == null) {
        freePageAvail = false;
        miss++;
        switch (algoType) {
         case 0:
          page_list = Random_Pick.randomPick(page_list);
          break;
         case 1:
          page_list = First_Come_First_Serve.fcfs(page_list);
          break;
         case 2:
          page_list = Least_Recently_Used.lru(page_list);
          break;
         case 3:
          page_list = Least_Frequently_Used.lfu(page_list); //TODO: changes in output are needed
          break;
         case 4:
          page_list = Most_Frequently_Used.mfu(page_list);
          break;
        }

        free_page = obj.get_free_page(page_list);
       }

       free_page.pid = processes[j].pid;
       free_page.page_no = processes[j].curr_page;
       free_page.brought_in_time = simulation_clock + (0.1 * k);
       free_page.last_used = simulation_clock + (0.1 * k);
       free_page.count = 0;
       page_list = obj.updatePageList(free_page, page_list);

       System.out.println("Time " + (simulation_clock + (0.1 * k)) + ": Page " + processes[j].curr_page + " for process " + processes[j].pid + " brought into memory page number " + free_page.index + ". Eviction needed: " + !freePageAvail);
      }
     }
    }

    for (int a = 0; a < index_of_process_q; a++) {
     if (processes[a].service_duration > 0) {
      processes[a].service_duration--;
      if (processes[a].service_duration == 0) {
       //process has finished execution . free pages in memory;
       obj.printMemoryMap(page_list);
       obj.free_memory(page_list, processes[a].pid);
       System.out.println("Finished the process "+ processes[a].pid + " Time: " + simulation_clock +  " Size: "+ processes[a].process_size_in_pages  + " duration: "+ processes[a].service_duration);
      }
     }
    }
   }

   if(miss == 0){
    miss = 1;
   }

  AlgoOutput algoOutput = new AlgoOutput();
  algoOutput.hitmissRatio = hit * 1.0 / miss;
  algoOutput.swappedInProcesses = swapped_in_processes;
  return algoOutput;
 }

 static Process[] generateProcesses() {
  Process[] processes = new Process[NUMBER_OF_PROCESSES];

  //For loop to generate 150 processes
  for (int j = 0; j < NUMBER_OF_PROCESSES; j++) {
   rand.setSeed(j * 10000000); // all processes begin with page 0
   processes[j] = new Process(j, page_count_options[rand.nextInt(4)], rand.nextInt(60), 1 + rand.nextInt(MAX_PROCESSES_DURATION), 0);
   // public Process(int pid, int page_count,int arrival_time,int duration,int curr_page)TODO: remove comment !
  }

  //Sorting the generated processes based on arrival time
  Arrays.sort(processes, new Comparator < Process > () {
   @Override
   public int compare(Process p1, Process p2) {
    return p1.arrival_time - p2.arrival_time;
   }
  });

  return processes;
 }

 static Process[] createCopy(Process[] input) {
  Process[] processes = new Process[NUMBER_OF_PROCESSES];
  for (int j = 0; j < NUMBER_OF_PROCESSES; j++) {
   processes[j] = new Process(input[j].pid, input[j].process_size_in_pages, input[j].arrival_time, input[j].service_duration, input[j].curr_page);
  }

  return processes;
 }

 static int startProcesses(int index_of_process_q, Process[] processes, int simulation_clock, Page obj, ArrayList<Page> page_list) {
  int swapped_in_processes = 0;
  System.out.println();
  while (index_of_process_q < NUMBER_OF_PROCESSES && processes[index_of_process_q].arrival_time <= simulation_clock) {
   if (obj.test_free_pages(page_list, 4)) { // check for at least 4 free pages
    Page p = obj.get_free_page(page_list); // if yes, bring process in memory
    p.pid = processes[index_of_process_q].pid;
    p.page_no = processes[index_of_process_q].curr_page;
    p.brought_in_time = 1.0 * simulation_clock;
    p.count = 1;
    p.last_used = simulation_clock;
    page_list = obj.updatePageList(p, page_list);
    System.out.println("Starting the process "+ p.pid + " Time: " + simulation_clock +  " Size: "+ processes[index_of_process_q].process_size_in_pages  + " duration: "+ processes[index_of_process_q].service_duration);
    obj.printMemoryMap(page_list);
    swapped_in_processes++;
    index_of_process_q++;
   } else
    break; // not enough memory
  }

  System.out.println();
  return swapped_in_processes;
 }
}