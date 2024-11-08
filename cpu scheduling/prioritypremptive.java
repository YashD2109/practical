import java.util.*;
public class prioritypremptive{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of processes");
        int n = sc.nextInt();
        int Pid[] = new int[n];
        int AT[] = new int[n];
        int BT[] = new int[n];
        int CT[] = new int[n];
        int TAT[] = new int[n];
        int WAT[] = new int[n];
        int btt[] = new int[n];
        int prio[] = new int[n];
        float avgtat = 0;
        float avgwat = 0;

        for(int i = 0; i < n; i++){
            System.out.println("Enter the process id");
            Pid[i] = sc.nextInt();
            System.out.println("Enter the Arrival Time");
            AT[i] = sc.nextInt();
            System.out.println("Enter the Burst time");
            BT[i] = sc.nextInt();
            btt[i] = BT[i];
            System.out.println("Enter priority time");
            prio[i] = sc.nextInt();
        }

        int F[] = new int[n];
        for(int i = 0; i < n; i++){
            F[i] = 0; 
        }

        int st = 0; 
        int total = 0;

        while(true){
            int min = 99;
            int c = n;
            if(total == n){
                break;
            }

            for(int i = 0; i < n; i++){
                if(AT[i] <= st && F[i] == 0 && prio[i] < min){
                    min = prio[i];
                    c = i;
                }
            }

            if(c == n){
                st = st + 1;
            }
            else{
                BT[c]--;
                st++;
                if(BT[c] == 0){
                    CT[c] = st;
                    total++;
                    F[c] = 1;
                }
            }
        }

        for(int i = 0; i < n; i++){ 
            TAT[i] = CT[i] - AT[i]; 
            WAT[i] = TAT[i] - btt[i]; 
            avgtat = avgtat + TAT[i]; 
            avgwat = avgwat + WAT[i];
        }

        System.out.println("Pid\tAT\tBT\tPrio\tCT\tTAT\tWAT");
        for(int i = 0; i < n; i++){ 
            System.out.println(Pid[i] + "\t" + AT[i] + "\t" + btt[i] + "\t" + prio[i] + "\t" + CT[i] + "\t" + TAT[i] + "\t" + WAT[i]);
        }

        System.out.println("Average TAT: " + avgtat/n + " and Average WAT: " + avgwat/n);
    }
}
