import java.io.*;
import java.util.*;

public class Pass1Assembler {
    static int locationCounter = 0;
    static ArrayList<Integer> symbolAddress = new ArrayList<>();
    static ArrayList<Integer> literalAddress = new ArrayList<>();
    
    public static void main(String args[]) {
        BufferedReader br;
        String input = null;

        // Define sets for each type of instruction or register
        String IS[] = {"ADD", "SUB", "MUL", "MOVR", "MOVER"};
        String UserReg[] = {"AREG", "BREG", "CREG", "DREG"};
        String AD[] = {"START", "END", "ORIGIN", "LTORG"};
        String DL[] = {"DC", "DS"};

        int symbolCount = 0, literalCount = 0;
        int currentAddress = 0;
        
        ArrayList<String> symbols = new ArrayList<>();
        ArrayList<String> literals = new ArrayList<>();
        
        try {
            br = new BufferedReader(new FileReader("initial.txt"));
            File intermediateFile = new File("IM.txt");
            File symbolFile = new File("ST.txt");
            File literalFile = new File("LT.txt");
            PrintWriter intermediateWriter = new PrintWriter(intermediateFile);
            PrintWriter symbolWriter = new PrintWriter(symbolFile);
            PrintWriter literalWriter = new PrintWriter(literalFile);
            
            while ((input = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(input, " ");
                
                while (st.hasMoreTokens()) {
                    String token = st.nextToken();

                    if (token.matches("\\d+") && token.length() > 2) {
                        locationCounter = Integer.parseInt(token);
                        currentAddress = locationCounter;
                        intermediateWriter.println(locationCounter);
                        locationCounter -= 1; // Adjust LC for the next line
                    } else {
                        if (Arrays.asList(AD).contains(token)) {
                            handleAD(token, intermediateWriter);
                        } else if (Arrays.asList(IS).contains(token)) {
                            handleIS(token, IS, intermediateWriter);
                        } else if (Arrays.asList(UserReg).contains(token)) {
                            handleRegister(token, UserReg, intermediateWriter);
                        } else if (Arrays.asList(DL).contains(token)) {
                            handleDL(token, DL, intermediateWriter);
                        } else {
                            handleSymbolsAndLiterals(token, st, intermediateWriter, symbols, literals, symbolCount, literalCount);
                            symbolCount++;
                            literalCount++;
                        }
                    }
                }
                locationCounter++;
            }
            
            writeTables(symbolWriter, literalWriter, symbols, symbolAddress, literals, literalAddress);
            closeFiles(intermediateWriter, symbolWriter, literalWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleAD(String token, PrintWriter intermediateWriter) {
        intermediateWriter.print("AD ");
        intermediateWriter.println(token);
    }

    private static void handleIS(String token, String[] IS, PrintWriter intermediateWriter) {
        int index = Arrays.asList(IS).indexOf(token) + 1;
        intermediateWriter.print("IS " + index + " ");
    }

    private static void handleRegister(String token, String[] UserReg, PrintWriter intermediateWriter) {
        int regCode = Arrays.asList(UserReg).indexOf(token) + 1;
        intermediateWriter.print(" " + regCode + " ");
    }

    private static void handleDL(String token, String[] DL, PrintWriter intermediateWriter) {
        int index = Arrays.asList(DL).indexOf(token) + 1;
        intermediateWriter.print("DL " + index + " ");
    }

    private static void handleSymbolsAndLiterals(String token, StringTokenizer st, PrintWriter intermediateWriter, ArrayList<String> symbols, ArrayList<String> literals, int symbolCount, int literalCount) {
        if (token.matches("^[a-zA-Z]+$")) {
            if (symbols.contains(token)) {
                int index = symbols.indexOf(token);
                intermediateWriter.print("S" + index + " ");
            } else {
                symbols.add(token);
                symbolAddress.add(locationCounter);
                intermediateWriter.print("S" + symbolCount + " ");
            }
        } else if (token.startsWith("=")) {
            literals.add(token);
            literalAddress.add(locationCounter);
            intermediateWriter.print("L" + literalCount + " ");
        }
        if (!st.hasMoreTokens()) {
            intermediateWriter.println();
        }
    }

    private static void writeTables(PrintWriter symbolWriter, PrintWriter literalWriter, ArrayList<String> symbols, ArrayList<Integer> symbolAddress, ArrayList<String> literals, ArrayList<Integer> literalAddress) {
        for (int i = 0; i < symbols.size(); i++) {
            symbolWriter.println(i + "\t" + symbols.get(i) + "\t" + symbolAddress.get(i));
        }

        for (int i = 0; i < literals.size(); i++) {
            literalWriter.println(i + "\t" + literals.get(i) + "\t" + literalAddress.get(i));
        }
    }

    private static void closeFiles(PrintWriter intermediateWriter, PrintWriter symbolWriter, PrintWriter literalWriter) throws IOException {
        intermediateWriter.close();
        symbolWriter.close();
        literalWriter.close();
    }
}
