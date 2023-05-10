# Memory Managanemt - Swapping & Paging Algorithms

The purpose of this project is to explore the various memory management algorithms for swapping and paging. We have generated simulated processes  using random number generator. We need not make any process management system calls.

### Workload Generation
We assume main memory has 100 MB with page size of 1 MB. Processes have randomly and evenly distributed sizes of 5, 11, 17, and 31 MB. Processes have randomly and evenly distributed service durations of 1, 2, 3, 4, or 5 seconds. We also assume that free pages are structured as a linked list, and when a process needs a free page we pick the page from the head of the free linked list.
Next, we generate around 150 new random processes into a Job Queue linked list sorted based on arrival time. A job at the head of the Job Queue is taken out and assigned memory to run if there is at least 4 free pages that can be assigned to that job (i.e., initially only 25 jobs will run. When a job is run, we need initially one page (page-0) and then the process will make a memory reference to another page in that process and we need to page-in the referenced page from disk if the page is not already in memory. Once a jobs are in memory, the processes run independently and simultaneously for their durations.   

### Paging
Assume as an example we are running a process that consists of 11 pages numbered 0 through 10. When we start executing, this process there is only free 4 pages Physical memory frames. There are always 11 page frames available on disk. When the process starts, none of its pages are in memory.

The process makes random references to its pages. Due to locality of reference, after referencing a page i, there is a 70% probability that the next reference will be to page i, i-1, or i+1. i wraps around from 10 to 0. In other words, there is a 70% probability that for a given i, Δi will be -1, 0, or +1. Otherwise, |Δi| > 1.

### Simulation Execution
We run each page replacement algorithm 5 times simulating 1 minute each time to get an average of the number of processes successfully swapped into memory (but not necessarily completed) during the minute.
For each replacement algorithm, we print the average number of processes that were successfully Swapped-in. Memory map for the 100 pages is defined as where the characters are the process names (one character per MB) and the dots are holes (one per MB).

Each time a process is swapped in (start running) or completes (exits and therefore is removed from memory), we print a record. For each replacement algorithm, print the average number of processes (over the 5 runs) that were successfully swapped-in (started execution).    
       
Please refer **readme.txt** on how to execute the code.
