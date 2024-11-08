import java.util.Scanner;

public class bestfit {
    static void bestFit(int blockSize[], int m, int processSize[], int n) {
        int allocation[] = new int[n]; // Array to store block allocation
        for (int i = 0; i < allocation.length; i++) allocation[i] = -1;

        for (int i = 0; i < n; i++) {
            int bestIdx = -1;
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (bestIdx == -1 || blockSize[bestIdx] > blockSize[j])
                        bestIdx = j;
                }
            }

            if (bestIdx != -1) {
                allocation[i] = bestIdx;
                blockSize[bestIdx] -= processSize[i];
            }
        }

        System.out.println("\nProcess No.\tProcess Size\tBlock no.");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
            System.out.println(allocation[i] != -1 ? allocation[i] + 1 : "Not Allocated");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Get number of memory blocks
        System.out.print("Enter the number of memory blocks: ");
        int m = sc.nextInt();

        // Get block sizes from user
        int blockSize[] = new int[m];
        System.out.println("Enter the size of each block:");
        for (int i = 0; i < m; i++) {
            System.out.print("Block " + (i + 1) + ": ");
            blockSize[i] = sc.nextInt();
        }

        // Get number of processes
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        // Get process sizes from user
        int processSize[] = new int[n];
        System.out.println("Enter the size of each process:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + ": ");
            processSize[i] = sc.nextInt();
        }

        // Call the bestFit method
        bestFit(blockSize, m, processSize, n);
        
        sc.close();
    }
}
