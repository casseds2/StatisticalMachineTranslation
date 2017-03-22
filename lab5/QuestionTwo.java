import java.util.*;

class QuestionTwo{
   public static void main(String[] args) {
      String srcOne = "the house";
      String tarOne = "la maison";
      String srcTwo = "house";
      String tarTwo = "maison";
      int iterNumber = 2;

      /**Initialize t(e|f) uniformly**/
      ArrayList<String> align = allAlignments(srcOne, tarOne);

   }

   /**Print a HashMap of type <String, Double>**/
   static void printHashMap(HashMap<String, Double> hash){
      for(Map.Entry<String, Double> entry : hash.entrySet()){
         ArrayList<String> array = new ArrayList<>(Arrays.asList(entry.getKey().split(" ")));
         System.out.println(entry.getKey() + " : " + entry.getValue());
      }
   }

   /**Add 2 ArrayLists of Strings to a HashMap and Return it**/
   static HashMap<String, Double> initTvals(ArrayList<String> a, ArrayList<String> b){
      HashMap<String, Double> tVals =  new HashMap<>();
      addToHashMap(a, tVals);
      addToHashMap(b, tVals);
      return tVals;
   }

   static ArrayList<String> allAlignments(String srcOne, String tarOne){
      String [] srcOneArray = srcOne.split(" ");
      String [] tarOneArray = tarOne.split(" ");
      ArrayList<String> src = new ArrayList<>();
      src.addAll(Arrays.asList(srcOneArray));
      ArrayList<String> tar = new ArrayList<>();
      tar.addAll(Arrays.asList(tarOneArray));
      ArrayList<String> used = new ArrayList<>();
      for(String s : src){
         for(String t : tar){
            String align = s + " " + t;
            if(!used.contains(align))
               used.add(align);
         }
      }
      return used;
   }

   /**Add a String ArrayList to a HashMap, catering for duplicates**/
   static void addToHashMap(ArrayList<String> list, HashMap<String, Double> tVals){
      for(String s : list){
         if(tVals.containsKey(s))
            tVals.put(s, tVals.get(s) + 1.0 / (double)list.size());
         else
            tVals.put(s, 1.0 / (double) list.size());
      }
   }
}
