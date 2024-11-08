import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Pass1 {
    public static void main(String[] args) throws NullPointerException,FileNotFoundException {
        String[] REG = {"ax", "bx", "cx", "dx"};
        String[] IS = {"stop", "add", "sub", "mult", "mover", "movem", "comp", "be", "div", "read"};
        String[] DL = {"ds", "dc"};

        int total_symb = 0, total_ltr = 0, optab_cnt = 0, pooltab_cnt = 0, loc = 0, temp, pos;
        boolean start = false, end = false, fill_addr = false, ltorg = false;

        Obj[] literal_table = new Obj[10];
        Obj[] symb_table = new Obj[10];
        Obj[] optab = new Obj[60];
        Pooltable[] pooltab = new Pooltable[5];

        String line;

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) {

            while ((line = br.readLine()) != null && !end) {
                String[] tokens = line.split(" ", 4);
                if (loc != 0 && !ltorg) {
                    bw.write("\n" + loc);
                    loc++;
                }

                ltorg = fill_addr = false;

                for (int k = 0; k < tokens.length; k++) {
                    pos = -1;
                    if (start) {
                        loc = Integer.parseInt(tokens[k]);
                        start = false;
                    }
                    switch (tokens[k]) {
                        case "start":
                            start = true;
                            pos = 1;
                            bw.write("\t (AD, " + pos + ")");
                            break;
                        case "end":
                            end = true;
                            pos = 2;
                            bw.write("\t(AD, " + pos + ")\n");
                            for (temp = 0; temp < total_ltr; temp++) {
                                if (literal_table[temp].addr == 0) {
                                    literal_table[temp].addr = loc - 1;
                                    bw.write("\t(DL, 2) \t (C, " + literal_table[temp].name + ")\n" + loc++);
                                }
                            }
                            pooltab[pooltab_cnt++] = new Pooltable(0, total_ltr);
                            break;
                        case "origin":
                            pos = 3;
                            bw.write("\t(AD, " + pos + ")");
                            pos = search(tokens[++k], symb_table, total_symb);
                            k++;
                            bw.write("\t(C, " + symb_table[pos].addr + ")");
                            loc = symb_table[pos].addr;
                            break;
                        case "ltorg":
                            ltorg = true;
                            pos = 5;
                            bw.write("\t(AD, " + pos + ")\n");
                            for (temp = 0; temp < total_ltr; temp++) {
                                if (literal_table[temp].addr == 0) {
                                    literal_table[temp].addr = loc - 1;
                                    bw.write("\t(DL, 2) \t (C, " + literal_table[temp].name + ")\n" + loc++);
                                }
                            }
                            pooltab[pooltab_cnt++] = new Pooltable(pooltab[pooltab_cnt - 1].first + pooltab[pooltab_cnt - 1].total_literals, total_ltr - pooltab[pooltab_cnt - 1].first - 1);
                            break;
                        case "equ":
                            pos = 4;
                            bw.write("\t(AD, " + pos + ")");
                            String prev_token = tokens[k - 1];
                            int posi = search(prev_token, symb_table, total_symb);
                            pos = search(tokens[++k], symb_table, total_symb);
                            symb_table[posi].addr = symb_table[pos].addr;
                            bw.write("\t(S," + (pos + 1) + ")");
                            break;
                        default:
                            if (pos == -1) {
                                pos = search(tokens[k], IS);
                                if (pos != -1) {
                                    bw.write("\t(IS, " + pos + ")");
                                    optab[optab_cnt++] = new Obj(tokens[k], pos);
                                } else if ((pos = search(tokens[k], DL)) != -1) {
                                    bw.write("\t(DL, " + (pos + 1) + ")");
                                    optab[optab_cnt++] = new Obj(tokens[k], pos);
                                    fill_addr = true;
                                } else if (tokens[k].matches("[a-zA-Z]+:")) {
                                    symb_table[total_symb++] = new Obj(tokens[k].substring(0, tokens[k].length() - 1), loc - 1);
                                    bw.write("\t(S, " + total_symb + ")");
                                } else {
                                    pos = search(tokens[k], REG);
                                    if (pos != -1) {
                                        bw.write("\t(RG, " + (pos + 1) + ")");
                                    } else if (tokens[k].matches("='\\d+'")) {
                                        String s = tokens[k].substring(2, tokens[k].length() - 1);
                                        literal_table[total_ltr++] = new Obj(s, 0);
                                        bw.write("\t(L, " + total_ltr + ")");
                                    } else if (tokens[k].matches("\\d+")) {
                                        bw.write("\t(C, " + tokens[k] + ")");
                                    } else {
                                        pos = search(tokens[k], symb_table, total_symb);
                                        if (fill_addr && pos != -1) {
                                            symb_table[pos].addr = loc - 1;
                                            fill_addr = false;
                                        } else if (pos == -1) {
                                            symb_table[total_symb++] = new Obj(tokens[k], 0);
                                            bw.write("\t(S," + total_symb + ")");
                                        } else {
                                            bw.write("\t(S," + pos + ")");
                                        }
                                    }
                                }
                            }
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file");
            e.printStackTrace();
        }
    }

    public static int search(String token, String[] list) {
        for (int i = 0; i < list.length; i++) {
            if (token.equalsIgnoreCase(list[i]))
                return i;
        }
        return -1;
    }

    public static int search(String token, Obj[] list, int cnt) {
        for (int i = 0; i < cnt; i++) {
            if (token.equalsIgnoreCase(list[i].name))
                return i;
        }
        return -1;
    }
}
