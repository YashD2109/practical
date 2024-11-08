
 import java.util.Scanner;
 public class optimal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of frames: ");
        int frames = scanner.nextInt();
        System.out.print("Enter the number of pages in the stream: ");
        int numPages = scanner.nextInt();
        int[] incomingStream = new int[numPages];
        System.out.println("Enter the pages:");
        for (int i = 0; i < numPages; i++) {
            incomingStream[i] = scanner.nextInt();
        }
        int pageFaults = 0;
        int[] temp = new int[frames];
        for (int i = 0; i < frames; i++) {
            temp[i] = -1;
        }
        System.out.println("Incoming \t Frame 1 \t Frame 2 \t Frame 3");
        for (int m = 0; m < numPages; m++) {
            int currentPage = incomingStream[m];
            boolean pageFault = true;
            int replaceIndex = -1;
            for (int i = 0; i < frames; i++) {
                if (temp[i] == currentPage) {
                    pageFault = false;
                    break;
                }
            }
            if (pageFault) {
                boolean inserted = false;
                for (int i = 0; i < frames; i++) {
                    if (temp[i] == -1) {
                        temp[i] = currentPage;
                        inserted = true;
                        pageFaults++;
                        break;
                    }
                }
                if (!inserted) {
                    int farthest = -1;
                    for (int i = 0; i < frames; i++) {
                        int j;
                        for (j = m + 1; j < numPages; j++) {
                            if (temp[i] == incomingStream[j]) {
                                if (j > farthest) {
                                    farthest = j;
                                    replaceIndex = i;
                                }
                                break;
                            }
                        }
                        
                        if (j == numPages) {
                            replaceIndex = i;
                            break;
                        }
                    }
                    temp[replaceIndex] = currentPage;
                    pageFaults++;
                }
            }
            System.out.print(currentPage + "\t\t\t");
            for (int i = 0; i < frames; i++) {
                if (temp[i] != -1) {
                    System.out.print(temp[i] + "\t\t\t");
                } else {
                    System.out.print(" - \t\t\t");
                }
            }
            System.out.println();
        }
        System.out.println("\nTotal Page Faults:\t" + pageFaults);
        scanner.close();
    }
 }