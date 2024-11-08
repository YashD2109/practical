import java.util.Scanner;

public class bankers {
    private int allocation[][], need[][], max[][], available[][], np, nr;
    Scanner sc = new Scanner(System.in);

    // Method to input data
    public void Input() {
        System.out.println("Enter number of processes:");
        np = sc.nextInt();
        System.out.println("Enter number of resources:");
        nr = sc.nextInt();
        
        need = new int[np][nr];  // Need matrix
        max = new int[np][nr];   // Max matrix
        allocation = new int[np][nr];  // Allocation matrix
        available = new int[1][nr];    // Available matrix
        
        // Input Max Matrix
        System.out.println("Enter the Max matrix:");
        for (int i = 0; i < np; i++) {
            for (int j = 0; j < nr; j++) {
                max[i][j] = sc.nextInt();
            }
        }
        
        // Input Allocation Matrix
        System.out.println("Enter the Allocation matrix:");
        for (int i = 0; i < np; i++) {
            for (int j = 0; j < nr; j++) {
                allocation[i][j] = sc.nextInt();
            }
        }
        
        // Input Available Matrix
        System.out.println("Enter the Available matrix:");
        for (int i = 0; i < nr; i++) {
            available[0][i] = sc.nextInt();
        }
    }

    // Method to calculate the Need matrix
    void calculateNeed() {
        System.out.println("Need Matrix:");
        for (int i = 0; i < np; i++) {
            for (int j = 0; j < nr; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
                System.out.print(need[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Method to check if the allocation is possible for a process
    private boolean canAllocate(int process) {
        for (int i = 0; i < nr; i++) {
            if (available[0][i] < need[process][i]) {
                return false; // Allocation not possible if available resources are less than the needed
            }
        }
        return true; // Allocation possible
    }

    // Method to check if the system is in a safe state
    public void isSafe() {
        int count = 0;
        Input();        // Get the input matrices
        calculateNeed(); // Calculate the need matrix
        boolean[] done = new boolean[np]; // To track if process is allocated
        
        // Loop until all processes are allocated
        while (count < np) {
            boolean allocated = false;
            for (int i = 0; i < np; i++) {
                if (!done[i] && canAllocate(i)) {
                    for (int j = 0; j < nr; j++) {
                        available[0][j] = available[0][j] - need[i][j] + allocation[i][j];
                    }
                    System.out.println("Process " + (i + 1) + " is allocated.");
                    allocated = done[i] = true;
                    count++;
                }
            }
            if (!allocated) { // If no process was allocated, system is unsafe
                break;
            }
        }

        if (count == np) { // If all processes are allocated safely
            System.out.println("\nSafely Allocated.\n");
        } else { // If system is unsafe
            System.out.println("Not allocated safely.");
        }
    }

    // Main method to execute the program
    public static void main(String[] args) {
        bankers b = new bankers();
        b.isSafe(); // Call the safety check method
    }
}
