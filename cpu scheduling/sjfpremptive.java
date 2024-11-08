import java.util.*;

public class sjfpremptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of processes");
        int n = sc.nextInt();

        int Pid[] = new int[n];
        int AT[] = new int[n];   // Arrival Time
        int BT[] = new int[n];   // Burst Time
        int CT[] = new int[n];   // Completion Time
        int TAT[] = new int[n];  // Turnaround Time
        int WAT[] = new int[n];  // Waiting Time
        int originalBT[] = new int[n];  // Original Burst Time for reference

        float avgtat = 0;
        float avgwat = 0;

        // Input for each process
        for (int i = 0; i < n; i++) {
            System.out.println("Enter the process id");
            Pid[i] = sc.nextInt();
            System.out.println("Enter the Arrival Time");
            AT[i] = sc.nextInt();
            System.out.println("Enter the Burst Time");
            BT[i] = sc.nextInt();
            originalBT[i] = BT[i]; // Storing original Burst Time
        }

        int F[] = new int[n]; // Finished flag for each process
        Arrays.fill(F, 0);    // Initialize all as unfinished

        int st = 0;  // System time
        int total = 0; // Total completed processes

        // Main loop to simulate the SJF preemptive process scheduling
        while (true) {
            int min = Integer.MAX_VALUE;
            int c = n;

            if (total == n) {  // If all processes are completed
                break;
            }

            // Select process with the shortest burst time that has arrived
            for (int i = 0; i < n; i++) {
                if (AT[i] <= st && F[i] == 0 && BT[i] < min) {
                    min = BT[i];
                    c = i;
                }
            }

            if (c == n) {
                st = st + 1; // No process is available to execute, increment time
            } else {
                BT[c]--;    // Process is executing
                st++;       // Increment system time
                if (BT[c] == 0) {  // Process completed
                    CT[c] = st;   // Set completion time
                    total++;      // Increment total completed processes
                    F[c] = 1;     // Mark process as finished
                }
            }
        }

        // Calculating Turnaround Time (TAT) and Waiting Time (WAT)
        for (int i = 0; i < n; i++) {
            TAT[i] = CT[i] - AT[i];       // Turnaround Time = Completion Time - Arrival Time
            WAT[i] = TAT[i] - originalBT[i]; // Waiting Time = Turnaround Time - Burst Time
            avgtat += TAT[i];             // Sum up TAT for average
            avgwat += WAT[i];             // Sum up WAT for average
        }

        // Display the results
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWAT");
        for (int i = 0; i < n; i++) {
            System.out.println(Pid[i] + "\t" + AT[i] + "\t" + originalBT[i] + "\t" + CT[i] + "\t" + TAT[i] + "\t" + WAT[i]);
        }

        // Print average TAT and WAT
        System.out.println("Average Turnaround Time: " + avgtat / n);
        System.out.println("Average Waiting Time: " + avgwat / n);
    }
}
