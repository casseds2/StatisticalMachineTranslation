import java.util.ArrayList;
import java.io.*;

class Optional2{
   public static void main(String[] args) {
      /************Variable Declarations*******************/
      String test = "<s> a cat sat on the mat </s>";
      String line = "";
      String input = "";
      int countOne = 0;
      int countTwo = 0;
      float currentScore = 0;
      float totalScore = 1;
      /*****************Read the File in*******************/
      try{
         BufferedReader br = new BufferedReader(new FileReader("input.txt"));
         while((line = br.readLine()) != null){
            line = line + " ";
            input = input + line;
         }
         br.close();
      }
      catch(IOException e){
         System.out.println("I/O Error");
      }
      /**************Main Logic******************/
      int uniqueCount = uniqueCountScore(input);
      System.out.println("Unique Count " + uniqueCount);
      for(int i = 1; i < 5; i++){
         ArrayList<ArrayList<String>> testGram = nGramPart(test, i);
         ArrayList<ArrayList<String>> inputGram = nGramPart(input, i);
         for(ArrayList<String> a : testGram){
            for(ArrayList<String> b : inputGram){
               if(a.equals(b)){
                  countOne++;
               }
               if(a.subList(0, i-1).equals(b.subList(0, i-1))){
                  countTwo++;
               }
            }
            currentScore = ((float)countOne +1 )/ ((float) countTwo + (float)uniqueCount);
            totalScore = totalScore * currentScore;
            System.out.print("Score for ");
            printList(a);
            System.out.println(" is " + currentScore);
            countOne = 0;
            countTwo = 0;
         }
         System.out.println("Total Score for gram(" + i + ") is " + totalScore);
         totalScore = 1;
      }
   }

   /**************Print a List**************/
   static void printList(ArrayList<String> a){
      System.out.print("{");
      for(String s : a){
         System.out.print(s + " ");
      }
      System.out.print("}");
   }

   /*************Unique Words Contained in a String*************/
   static int uniqueCountScore(String s){
      ArrayList<String> used = new ArrayList<>();
      String [] array = s.split(" ");
      for(int i = 0; i < array.length; i++){
         if(!used.contains(array[i]))
            used.add(array[i]);
      }
      return used.size();
   }
   /**************N-Gram Partitioner******************/
   static ArrayList<ArrayList<String>> nGramPart(String s, int n){
      ArrayList<ArrayList<String>> allSets = new ArrayList<>();
      ArrayList<String> oneSet = new ArrayList<>();
      String [] array = s.split(" ");
      for(int i = 0; i < array.length-(n-1); i++){
         for(int j = i; j < i + n; j++){
            oneSet.add(array[j]);
         }
         allSets.add(oneSet);
         oneSet = new ArrayList<>();
      }
      return allSets;
   }
}
