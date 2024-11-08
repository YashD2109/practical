import java.util.Scanner;

public class worstfit {
    static void worstFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
        int allocation[] = new int[n]; // Array to store block allocation

        // Initialize allocation array
        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1;
        }

        // Allocate blocks to processes using worst fit
        for (int i = 0; i < n; i++) {
            int worstIdx = -1;
            // Find the block with the largest size that can accommodate the process
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (worstIdx == -1 || blockSize[j] > blockSize[worstIdx]) {
                        worstIdx = j;
                    }
                }
            }
            
            // If a suitable block is found, allocate it to the process
            if (worstIdx != -1) {
                allocation[i] = worstIdx;
                blockSize[worstIdx] -= processSize[i]; // Update block size
                remblockSize[i] = blockSize[worstIdx]; // Store remaining block size
            }
        }

        // Output the result
        System.out.println("\nProcess No.\tProcess Size\tBlock No.\tRemaining Block Size");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1) {
                System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]);
            } else {
                System.out.print("Not Allocated" + "\t\t" + remblockSize[i]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Get the number of blocks from the user
        System.out.print("Enter the number of blocks: ");
        int m = in.nextInt();

        int blockSize[] = new int[m];  // Array to store block sizes
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

        // Call the worstFit function
        worstFit(blockSize, m, processSize, n, remblockSize);

        in.close();
    }
}
