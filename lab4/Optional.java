import java.io.*;
import java.util.*;

/**
Count pair words are together / count of first word followed by any word
**/

class Optional{
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
      /**Get Unique words in Corpus**/
      int uniqueCount = calculateUniques(input.split(" "));
      /**
         For N-gram 1, Calculate Probability of sentence
         For N-gram 2, if first 1 is the same, countFirst++, if last (second) element is the same then countAll++
         For N-gram 3, if first 2 are the same, countFirst++; if last element is the same then countAll++
         For N-gram 4, if first 3 are the same, countFirst++, if last element is the same then countAll++
      **/
      int countFirst = 0;
      int countAll = 0;
      float currentScore = 0;
      float totalScore = 1;
      for(int i = 1; i < 5; i++){
        ArrayList<ArrayList<String>> lineGrams = calculateNgrams(testString, i);
        ArrayList<ArrayList<String>> fileGrams = calculateNgrams(input, i);
        for(ArrayList <String> a : lineGrams){ //For each list of Ngrams if size 'i' in the test line
          for(ArrayList <String> b : fileGrams){ //For each list of Ngrams if size 'i' in the input line
            if(a.equals(b)) // If the nGrams are the same, increase countALl
              countAll++;
            if(a.subList(0, i-1).equals(b.subList(0, i-1))){ //If nGram, except the last element is the same, countFirst++
              countFirst++;
            }
          }
          currentScore = ((float)countAll + 1)/ ((float) countFirst + (float)uniqueCount);
          totalScore = totalScore * currentScore;
          System.out.print("Score for ");
          printNgramList(a);
          System.out.println(" is " + currentScore);
          countFirst = 0;
          countAll = 0;
        }
        System.out.println("Total Score for Ngram(" + i + ") is " + totalScore);
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
/**Print out a set of Ngrams stored in a list**/
  static void printNgramList(ArrayList<String> a){
     System.out.print("{");
     for(String s : a){
        System.out.print(s + " ");
     }
     System.out.print("}");
  }
}
