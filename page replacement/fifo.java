

import java.util.Scanner;
public class fifo {
    public static void main(String args[]) {
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
        int pages = incomingStream.length;
        int[] temp = new int[frames];
        for (int i = 0; i < frames; i++) {
            temp[i] = -1;
        }
        System.out.println("Incoming \t Frame 1 \t Frame 2 \t Frame 3");
        for (int m = 0; m < pages; m++) {
            int s = 0;
            for (int n = 0; n < frames; n++) {
                if (incomingStream[m] == temp[n]) {
                    s++;
                    pageFaults--;
                }
            }
            pageFaults++;
            if ((pageFaults <= frames) && (s == 0)) {
                temp[m % frames] = incomingStream[m];
            } else if (s == 0) {
                temp[(pageFaults - 1) % frames] = incomingStream[m];
            }
            System.out.println();
            System.out.print(incomingStream[m] + "\t\t\t");
            for (int n = 0; n < frames; n++) {
                if (temp[n] != -1)
                    System.out.print(temp[n] + "\t\t\t");
                else
                    System.out.print(" - \t\t\t");
            }
        }
        System.out.println("\nTotal Page Faults:\t" + pageFaults);
        scanner.close();
    }
 }