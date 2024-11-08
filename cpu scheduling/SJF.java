import java.util.*;
public class SJF{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of process");
        int n = sc.nextInt();
        int Pid[] = new int[n];
        int AT[] = new int[n];
        int BT[] = new int[n];
        int CT[] = new int[n];
        int TAT[] = new int[n];
        int WAT[] = new int[n];
        float avgtat = 0;
        float avgwat = 0;
        for(int i = 0;i<n;i++){
            System.out.println("Enter the process id");
            Pid[i] = sc.nextInt();
            System.out.println("Enter the Arrival time");
            AT[i] = sc.nextInt();
            System.out.println("Enter the Burst Time");
            BT[i] = sc.nextInt();
        }
        int F[] = new int[n];
        for(int i=0;i<n;i++){
            F[i] = 0;
        }
        int st = 0;
        int total = 0;
        while (true){
            int min = 99;
            int c = n;
            if (total == n){
                break;
            }
            for (int i=0;i<n;i++){
                if(AT[i] <= st && F[i] ==0 && BT[i]< min){
                    c = i;
                    min = BT[i];
                }
            }
            if(c==n){
                st = st+1;

            }
            else{
                CT[c] = st+ BT[c];
                F[c] = 1;
                st = CT[c];
                total++;

            }
        }
        for(int i =0;i<n;i++){
            TAT[i] = CT[i] - AT[i];
            WAT[i] = TAT[i] - BT[i];
            avgtat = avgtat + TAT[i];
            avgwat = avgwat + WAT[i];
        }
        System.out.println("PID \t AT \t BT\t CT\t TAT \t WAT");
        for(int i=0;i<n;i++){
            System.out.println(Pid[i]+"\t"+AT[i]+"\t"+BT[i]+"\t"+CT[i]+"\t"+TAT[i]+"\t"+WAT[i]+"\t");
        }
        System.out.println("Average turn around time and waiting time are "+avgtat+avgwat);
    }
}