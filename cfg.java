
import java.util.*;
import java.io.*;
/*
 * Author: Bryce Manley 
 * Class: CS 357
 * Final Project - Random Sentence Generator from CFG -
 * 
 * 
 * Description: This is a random sentence generator - given a txt file in the necessary format(see readme for proper formatting). It stores all given words and given non-terminal
 *  strings (NTS) in a 2d array. Each line is represented by its own 2d array and is a single entry in the Hash Table. The 2D array is then accessed in thew hash table by
 * using the corresponding NTS as the key. Once the data structure is built the program will recursively choose a random word while following the given rules of the CFG.
 * 
 * 
 */

//There is only one class in this program the CFG class which implements everything
public class cfg {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter location of file");
  


        String file = sc.next();

        cfg CFG = new cfg(file);
              //check if it is not a txt file
        if (!file.endsWith(".txt")) {
            //exit program
            System.out.println("Not a txt file");
            System.exit(0);
        }

   

        System.out.println("");

        sc.close();

    }

    // add words to hashmap with string NTS as key and array words as value
    public HashMap<String, String[][]> addNTS(String[][] words, Map<String, String[][]> NTSmap, String NTS) {

        NTSmap.put(NTS, (words));

        return (HashMap<String, String[][]>) NTSmap;

    }

    // This is the constructor it will parse the input file into the data structure
    public cfg(String filePath) {

        String NTS;
        File file = new File(filePath);
        String StartKey ;
        try {
            BufferedReader brt = new BufferedReader(new FileReader(file));

            String st;
            String[] words = new String[100];
            HashMap<String, String[][]> NTSmap = new HashMap<String, String[][]>();

            st = brt.readLine();
            if (st == null) {
                System.out.println("File is empty please enter a valid file path");
                System.exit(0);
            }

            // not the best code might want to touch up
            if (st.isEmpty()) {
                while ((st = brt.readLine()) != null) {

                    // if line is empty continue
                    if (st.isEmpty()) {
                        continue;
                    } else {
                        break;
                    }

                }
            }

            // if line does not contain "->" give error and exit
            if (!st.contains("->")) {
                System.out.println("Error: First non empty line does not contain \"->\"please fix");
                System.exit(0);
            }

                

                StartKey = st.substring(0, st.indexOf("->"));
            
            // close lt
            brt.close();

            // close and reopen so we can read first line again
            // so that we don't skip a line

            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((st = br.readLine()) != null) {

                // if line is empty continue
                if (st.isEmpty()) {
                    continue;
                }

                // if line does not contain "->" continue and give warning and line number
                if (!st.contains("->")) {
                    System.out.println("Warning: Line does not contain \"->\" please check line:" + st);
                    continue;
                }

                // set words before "->" to NTS then delete from st
                NTS = st.substring(0, st.indexOf("->"));

                // replace NTS+ -> with ""
                st = st.replace(NTS + "->", "").trim();

                ArrayList<String[]> tempAL = new ArrayList<>();
                String[] sections = st.split("}");

                for (String section : sections) {
                    section = section.replace('{', ' ').trim();
                    words = section.split(",");
                    // trim all the words in array

                    for (int i = 0; i < words.length; ++i) {
                        words[i] = words[i].trim();
                    }
                    tempAL.add(words);
                }

                String[][] result = new String[tempAL.size()][0];
                for (int i = 0; i < result.length; ++i) {
                    result[i] = tempAL.get(i);
                }

                // add words to hashmap with NTS as key and words as value
                addNTS(result, NTSmap, NTS);

            }
            RandArray(StartKey, NTSmap);
            br.close();

        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    // given array pick random word from array or if hashmap key is selected
    // recursively call function again with new array and print results
    public void RandArray(String startKey, HashMap<String, String[][]> NTSmap) {

        String[][] result = new String[100][100];

        result = NTSmap.get(startKey);
        for (int i = 0; i < result.length; ++i) {
            // generate random number between 0 and length of array
            int rnd = new Random().nextInt(result[i].length);
            // if random number is a key in hashmap
            if (NTSmap.containsKey(result[i][rnd])) {
                // create new Randarray object and run it - continue after thread is done
                // recursion lets goooooooooooooo
                RandArray(result[i][rnd], NTSmap);
            } else {
                // print random word
                System.out.print(result[i][rnd] + " ");
            }
        }

    }
}
