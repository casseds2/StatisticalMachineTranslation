import java.io.*;
import java.util.*;

class Optional3{
   public static void main(String[] args) {
      String test = "<s> a cat sat on the car </s>";
      String line = "";
      String input = "";
      /**READ THE FILE IN**/
      try{
         BufferedReader br = new BufferedReader(new FileReader("input.txt"));
         while((line = br.readLine()) != null){
            input = input + line + " ";
         }
         br.close();
      }
      catch(IOException e){
         System.out.print("I/O Error");
         System.exit(0);
      }
      /**MAIN LOGIC**/
      int uniqueCount = calcUnique(input);
      int countOne = 0;
      int countTwo = 0;
      float totalScore = 1;
      float currentScore = 0;
      for(int i = 1; i < 5; i++){
         ArrayList<ArrayList<String>> testGrams = gramPart(test, i);
         ArrayList<ArrayList<String>> inputGrams = gramPart(input, i);
         for(ArrayList<String> a : testGrams){
            for(ArrayList<String> b : inputGrams){
               if(a.equals(b))
                  countOne++;
               if(a.subList(0, i-1).equals(b.subList(0, i-1)))
                  countTwo++;
            }
            currentScore = ((float)countOne + 1)/ ((float)countTwo + (float)uniqueCount);
            totalScore = totalScore * currentScore;
            countOne = 0;
            countTwo = 0;
         }
         System.out.println("Score for nGram(" + i + ") is " + totalScore);
         totalScore = 1;
      }
   }
   /**CALCULATE UNIQUE WORDS**/
   static int calcUnique(String s){
      String [] array = s.split(" ");
      ArrayList<String> used = new ArrayList<>();
      for(int i = 0; i < array.length; i++){
         if(!used.contains(array[i]))
            used.add(array[i]);
      }
      return used.size();
   }
   /**PARTITION THE STRING**/
   static ArrayList<ArrayList<String>> gramPart(String s, int n){
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
