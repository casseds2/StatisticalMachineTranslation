import java.io.*;
import java.util.*;

/*
Count pair words are together / count of first word followed by any word
*/

class NgramHashProb{
   public static void main(String[] args) {
      /**Read the Test File in**/
      String input = "";
      try{
         BufferedReader in = new BufferedReader(new FileReader("input.txt"));
         input = "";
         String next = "";
         while((next = in.readLine()) != null){
            next = next + " ";
            input = input + next;
         }
         in.close();
      }
      catch(Exception e){
         System.out.println("I/O Error");
      }
      /**Test String**/
      String testString = "<s> a cat sat on the mat </s>";
      /**Split Array of the Input String**/
      String [] splitString = testString.split(" ");
      /**Get Unique words in Corpus**/
      int uniqueCount = calculateUniques(input.split(" "));
      /**
         For N-gram 1, Calculate Probability of sentence
         For N-gram 2, if first 1 is the same, countFirst++, if last (second) element is the same then countAll++
         For N-gram 3, if first 2 are the same, countFirst++; if last element is the same then countAll++
         For N-gram 4, if first 3 are the same, countFirst++, if last element is the same then countAll++
      **/
   }

   /**Calculate Frequencies of Ngrams**/
   static HashMap<ArrayList<String>, Integer> getNgramFreqs(ArrayList<ArrayList<String>> nGramArray){
      HashMap<ArrayList<String>, Integer> h = new HashMap<>();
      for(ArrayList<String> a : nGramArray){
         if(h.containsKey(a)){
            h.put(a, h.get(a) + 1);
         }
         else{
            h.put(a, 1);
         }
      }
      return h;
   }

   /**Return Unique words of a List**/
   static int calculateUniques(String [] list){
      ArrayList<String> usedWords = new ArrayList<>();
      for(int i = 0; i < list.length; i++){
         if(!usedWords.contains(list[i]))
            usedWords.add(list[i]);
      }
      return usedWords.size();
   }

   /**Split the Sentence into Desired NGrams, ie. 2**/
   static ArrayList<ArrayList<String>> calculateNgrams(String s, int n){
    ArrayList<ArrayList<String>> allSets = new ArrayList<>();
    ArrayList<String> nGramSets = new ArrayList<>();
    String [] splitString = s.split(" ");
    for(int i = 0; i < splitString.length - (n-1); i++){
      for(int j = i; j < i + n; j++){
          nGramSets.add(splitString[j]);
      }
      allSets.add(nGramSets);
      nGramSets = new ArrayList<>();
    }
    return allSets;
  }

  static void printNgramFreqs(HashMap<ArrayList<String>, Integer> h){
     for(Map.Entry<ArrayList<String>, Integer> entry : h.entrySet()) {
        //System.out.println(entry.getKey()+" : "+entry.getValue());
        ArrayList<String> temp = entry.getKey();
        int i = entry.getValue();
        printNgramList(temp);
        System.out.print(" = " + i);
        System.out.println();
     }
 }

  static void printNgramList(ArrayList<String> a){
     System.out.print("{");
     for(String s : a){
        System.out.print(s + " ");
     }
     System.out.print("}");
  }
}
