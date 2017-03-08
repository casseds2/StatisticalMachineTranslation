import java.util.*;
import java.io.*;

/*
Count pair words are together / count of first word followed by any word
*/

class BigramSmoothing{
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
         String testString = "<s> a cat sat on the car </s>";
         /**Split Array of the Input String**/
         String [] splitString = testString.split(" ");
         /**Get Unique words in Corpus**/
         int uniqueCount = calculateUniques(input.split(" "));
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
            float currentScore = ((float)countPair + 1) / ((float) countSingle + (float) uniqueCount);
            totalScore = totalScore * currentScore;
            System.out.println("Probability of '" + splitString[i+1] + "' given '" + splitString[i] + "' equals : " + currentScore);
            countSingle = 0;
            countPair = 0;
         }
         System.out.println("Total Score : " + totalScore);
      }
      /**Make a HashMap of all Unique Words and their Frequencies**/
      public static HashMap<String, Integer> freqCounter(String input){
   		HashMap<String, Integer> wordMap = new HashMap<>();
   		String [] splitString = input.split(" ");
   		for(int i = 0; i < splitString.length; i++){
   			if(wordMap.containsKey(splitString[i])){
   				wordMap.put(splitString[i], wordMap.get(splitString[i]) + 1);
   			}
   			else{
   				wordMap.put(splitString[i], 1);
   			}
   		}
   		return wordMap;
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
}
