import java.util.*;
import java.io.*;

/*
Count pair words are together / count of first word followed by any word
*/

class NGramProb{
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
         //ArrayList<ArrayList<String>> biGrams = calculateNgrams(input, 2);
         String testString = "<s> a cat sat on the mat </s>";
         /**Split Array of the Input String**/
         String [] splitString = testString.split(" ");
         /**Get Unique words in Corpus**/
         int uniqueCount = calculateUniques(input.split(" "));
         /**Final Score**/
         float totalScore = 1;
         /**Both Counters**/
         int countSingle = 0;
         int countPair = 0;
         /**For NGrams 1-4**/
         for(int i = 1; i < 5; i++){ //Ngram Counter
            ArrayList<ArrayList<String>> nGrams = calculateNgrams(input, i);
            for(int j = 0; j < splitString.length-i; j++){ //Index of Current Word in Test String
               boolean flag = true;
               for(ArrayList<String> a : nGrams){
                  /**Prints out the Two Lists to be Compared**/
                  System.out.print("Does ");
                  printNgramList(a);
                  System.out.print(" equal {");
                  for(int r = j; r < j+i; r++){
                     System.out.print(splitString[r] + " ");
                  }
                  System.out.print("}");
                  System.out.println();
                  /**Logic Handling Counters**/
                  if(a.get(0).equals(splitString[j])){
                     countSingle++;
                     System.out.println("Count Single :" + countSingle);
                     /**First Word was the Same so Now Compare the Rest**/
                     for(int k = 1; k < i; k++){
                        if(!a.get(k).equals(splitString[j+k]))
                           flag = false;
                     }
                     if(flag){ //Flag wasn't set to false so all words matched
                        countPair++;
                        System.out.println("Count Pair :" + countPair);
                     }
                  }
               }
               float currentScore = ((float) countPair + 1) / ((float) countSingle + (float) uniqueCount);
               totalScore = totalScore * currentScore;
            }
            System.out.println("Probability of sentence at nGram(" + i + ") equals : " + totalScore);
            countSingle = 0;
            countPair = 0;
            totalScore = 1;
         }
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

     static void printNgramList(ArrayList<String> a){
        System.out.print("{");
        for(String s : a){
           System.out.print(s + " ");
        }
        System.out.print("}");
     }
}
