import java.util.*;

public class priorityNP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of processes:");
        int n = sc.nextInt();
        
        int Pid[] = new int[n];
        int AT[] = new int[n];
        int BT[] = new int[n];
        int CT[] = new int[n];
        int TAT[] = new int[n];
        int WAT[] = new int[n];
        int prio[] = new int[n];
        float avgtat = 0;
        float avgwat = 0;

        for (int i = 0; i < n; i++) {
            System.out.println("Enter the process id:");
            Pid[i] = sc.nextInt();
            System.out.println("Enter the Arrival Time:");
            AT[i] = sc.nextInt();
            System.out.println("Enter the Burst Time:");
            BT[i] = sc.nextInt();
            System.out.println("Enter the Priority:");
            prio[i] = sc.nextInt();
        }

        int F[] = new int[n]; // Finished flag
        Arrays.fill(F, 0); // Initialize all to 0

        int st = 0; // Current time
        int total = 0; // Count of completed processes

        while (true) {
            int min = Integer.MAX_VALUE;
            int c = -1; // Initialize c to -1 (no process selected)

            if (total == n) {
                break;
            }

            for (int i = 0; i < n; i++) {
                // Check if the process is available to execute
                if (AT[i] <= st && F[i] == 0 && prio[i] < min) {
                    c = i;
                    min = prio[i];
                }
            }

            if (c == -1) { // If no process is found
                st++;
            } else {
                CT[c] = st + BT[c]; // Completion time
                F[c] = 1; // Mark process as finished
                st = CT[c]; // Update current time
                total++;
            }
        }

        // Calculate TAT and WAT
        for (int i = 0; i < n; i++) {
            TAT[i] = CT[i] - AT[i]; // Turnaround time
            WAT[i] = TAT[i] - BT[i]; // Waiting time
            avgtat += TAT[i]; // Sum TAT for average
            avgwat += WAT[i]; // Sum WAT for average
        }

        // Output process details
        System.out.println("PID \t AT \t BT \t PRIO \t CT \t TAT \t WAT");
        for (int i = 0; i < n; i++) {
            System.out.println(Pid[i] + "\t" + AT[i] + "\t" + BT[i] + "\t" + prio[i] + "\t" + CT[i] + "\t" + TAT[i] + "\t" + WAT[i]);
        }

        // Calculate and print averages
        avgtat /= n; // Average turnaround time
        avgwat /= n; // Average waiting time
        System.out.printf("Average Turnaround Time: %.2f\n", avgtat);
        System.out.printf("Average Waiting Time: %.2f\n", avgwat);
    }
}
