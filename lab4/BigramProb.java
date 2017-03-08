import java.util.*;
import java.io.*;

/*
Count pair words are together / count of first word followed by any word
*/
class BigramProb{
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
         /**ArrayList of All Bigram Pairs**/
         ArrayList<ArrayList<String>> biGrams = calculateNgrams(input, 2);
			/**String to be Tested**/
         String testString = "<s> a cat sat on the mat </s>";
         /**Split Array of the Input String**/
         String [] splitString = testString.split(" ");
         float totalScore = 1;
         int countPair = 0;
         int countSingle = 0;
         for(int i = 0; i < splitString.length-1; i++){
            for(ArrayList<String> a : biGrams){
               //Increment if first word is the current word we are searching for
               if(a.get(0).equals(splitString[i])){
                  countSingle++;
                  //Increment if following word is the following word from our test string
                  if(a.get(1).equals(splitString[i+1]))
                     countPair++;
               }
         	}
            float currentScore = (float)countPair / (float) countSingle;
            totalScore = totalScore * currentScore;
            System.out.println("Probability of '" + splitString[i+1] + "' given '" + splitString[i] + "' equals : " + currentScore);
            countSingle = 0;
            countPair = 0;
         }
         System.out.println("Total Score : " + totalScore);
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
}
