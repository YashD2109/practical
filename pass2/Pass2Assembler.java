import java.io.*;
import java.util.*;

class Pass2Assembler {
    public static void main(String args[]) {
        BufferedReader br, brST, brLT;
        String input;
        String t;

        try {
            br = new BufferedReader(new FileReader("IM.txt"));
            brST = new BufferedReader(new FileReader("ST.txt"));
            brLT = new BufferedReader(new FileReader("LT.txt"));

            // Load the symbol and literal tables into maps for quick access
            Map<Integer, String> symbolTable = new HashMap<>();
            Map<Integer, String> literalTable = new HashMap<>();
            loadTable(brST, symbolTable);
            loadTable(brLT, literalTable);

            // Prepare the output file for machine code
            File output = new File("Output.txt");
            PrintWriter writer = new PrintWriter(output);

            // Process each line in the intermediate file
            while ((input = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(input, " ");
                StringBuilder lineOutput = new StringBuilder();

                while (st.hasMoreTokens()) {
                    t = st.nextToken();

                    // Check if the token is an AD directive
                    if (t.equals("AD")) {
                        // Skip the entire line for AD directives
                        st.nextToken(); // Skip the AD code
                        break;
                    } else if (t.equals("IS") || t.equals("DL")) {
                        // Write the opcode for IS or DL
                        lineOutput.append(t).append(" ").append(st.nextToken()).append(" ");
                    } else if (t.matches("\\d+")) {
                        // If it's a number, it's either an operand or address
                        lineOutput.append(t).append(" ");
                    } else if (t.startsWith("S")) {
                        // Process symbol reference
                        int symbolIndex = Integer.parseInt(t.substring(1));
                        String address = symbolTable.get(symbolIndex);
                        if (address != null) {
                            lineOutput.append(address).append(" ");
                        } else {
                            lineOutput.append("?? "); // In case of missing symbol
                        }
                    } else if (t.startsWith("L")) {
                        // Process literal reference
                        int literalIndex = Integer.parseInt(t.substring(1));
                        String address = literalTable.get(literalIndex);
                        if (address != null) {
                            lineOutput.append(address).append(" ");
                        } else {
                            lineOutput.append("?? "); // In case of missing literal
                        }
                    } else {
                        // Default case for other tokens (e.g., registers)
                        lineOutput.append(t).append(" ");
                    }
                }

                if (lineOutput.length() > 0) {
                    writer.println(lineOutput.toString().trim());
                }
            }

            // Close all resources
            writer.close();
            br.close();
            brST.close();
            brLT.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper function to load the symbol or literal table into a map
    private static void loadTable(BufferedReader br, Map<Integer, String> table) throws IOException {
        String line;
        int index = 0; // Use a separate counter as the key for each symbol or literal
        
        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, "\t");
    
            // Check if there are at least two tokens in the line (symbol name and address)
            if (st.countTokens() < 2) {
                System.out.println("Skipping malformed line: " + line);
                continue;
            }
            
            // Skip the symbol/literal name (first token)
            st.nextToken();
            
            // Parse the address (second token)
            String address = st.nextToken();
            
            // Add the entry to the table
            table.put(index++, address);
        }
    }
    
    
}

