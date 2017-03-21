import java.util.*;

class QuestionTwo{
   public static void main(String[] args) {
      String srcOne = "the house";
      String tarOne = "la maison";
      String srcTwo = "house";
      String tarTwo = "maison";
      /**Makes Working With Values Easier**/
      ArrayList<String> srcOneList = new ArrayList<>(Arrays.asList(srcOne.split(" ")));
      ArrayList<String> tarOneList = new ArrayList<>(Arrays.asList(tarOne.split(" ")));
      ArrayList<String> srcTwoList = new ArrayList<>(Arrays.asList(srcTwo.split(" ")));
      ArrayList<String> tarTwoList = new ArrayList<>(Arrays.asList(tarTwo.split(" ")));
      /**Get counts of the occurrences of word pairs**/
      double t = 0;
      for(String a : srcOneList){
         for(String b : tarOneList){
            t = getCount(a, srcOneList) / (double)tarOneList.size();
            System.out.println("t(" + a + "|" + b + ") = " + t);
         }
      }
   }

   static double getCount(String s, ArrayList<String> a){
      double count = 0;
      for(String q : a)
         if(s.equals(q))
            count+=1;
      return count;
   }
}
