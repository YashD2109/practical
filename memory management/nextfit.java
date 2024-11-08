import java.util.Arrays;
import java.util.Scanner;

public class nextfit {
    static void NextFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
        int allocation[] = new int[n]; // Array to store block allocation
        Arrays.fill(allocation, -1); // Initialize allocation array with -1 (indicating not allocated)
        
        int j = 0; // Pointer to track block index
        
        // Allocate blocks to processes
        for (int i = 0; i < n; i++) {
            int count = 0;
            // Try to find the block for the current process
            while (count < m) {
                if (blockSize[j] >= processSize[i]) { // If block size is sufficient
                    allocation[i] = j; // Assign block to the process
                    blockSize[j] -= processSize[i]; // Update block size after allocation
                    remblockSize[i] = blockSize[j]; // Store remaining block size
                    break; // Break once allocated
                }
                j = (j + 1) % m; // Move to the next block, wrapping around with modulo
                count++;
            }
        }

        // Output the allocation results
        System.out.println("\nProcess No.\tProcess Size\tBlock No.\tRemaining Block Size");
        for (int i = 0; i < n; i++) {
            System.out.print((i + 1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1) {
                System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]);
            } else {
                System.out.print("Not Allocated" + "\t\t-");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Get the number of blocks from the user
        System.out.print("Enter the number of blocks: ");
        int m = in.nextInt();

        int blockSize[] = new int[m]; // Array to store block sizes
        int remblockSize[] = new int[m]; // Array to store remaining block sizes

        // Input block sizes
        for (int i = 0; i < m; i++) {
            System.out.print("Enter size of block " + (i + 1) + ": ");
            blockSize[i] = in.nextInt();
        }

        // Get the number of processes from the user
        System.out.print("Enter the number of processes: ");
        int n = in.nextInt();

        int processSize[] = new int[n]; // Array to store process sizes

        // Input process sizes
        for (int i = 0; i < n; i++) {
            System.out.print("Enter size of process " + (i + 1) + ": ");
            processSize[i] = in.nextInt();
        }

        // Call the NextFit function
        NextFit(blockSize, m, processSize, n, remblockSize);

        in.close();
    }
}
