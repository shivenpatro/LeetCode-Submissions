// bro so basically what we are doing here is:
// we are given a string 's' that contains some bracket pairs like "(name)" or "(age)"!!
// inside the brackets there is a 'key' and we are given a 2D list 'knowledge' with [key, value] pairs!!
// we need to replace each "(key)" with its corresponding value from knowledge!!
// and if that key doesn't exist in knowledge, replace it with "?"!!
// normal characters outside brackets just remain untouched!!
//
// STEP-BY-STEP GAME PLAN (APPROACH 1):
// 1. First populate a HashMap<String, String> from 'knowledge' so we can do O(1) instant key lookups!!
// 2. Use a StringBuilder 'result' to build our final output string without wasting memory!!
// 3. Run a while loop through string 's' with pointer 'i':
//    - If s.charAt(i) is '(', jackpot! We entered a bracket:
//        * move i++ past '(' so we start reading the key itself
//        * run an inner while loop collecting characters into a 'temp' string until we hit ')'!!
//        * now 'temp' contains our exact key (e.g. "name")!!
//        * check map: if key is present, append its value to result, otherwise append "?"!!
//    - Else (it's a regular character outside brackets):
//        * just append it straight to result!!
//    - Increment i++ and keep moving forward!!
// 4. Return result.toString()!!
class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        Map<String, String> map = new HashMap<>();
        for(List<String> pair : knowledge){
            map.put(pair.get(0), pair.get(1));//knowldedeg list of list has the set of strings, which has to be replaced in string s na.. so for O(1) retrieval please add in proper map manner, from the list of lists.. traverse by tkaing out each list.. and first of each list is key and 2nd is value... traverse nad keep adding and map is populated
        }
        StringBuilder result = new StringBuilder();
        int n = s.length();//length of string
        for(int i = 0;i<n;i++){
            char c = s.charAt(i);
            if(c == '('){
                i++;//we found a open bracked, so we need to make a temporary string temp,we did extra i++ here because the string will obv start from one char after this bracket na, whicih iwll have the string inside the brackets so that we can check from the map if this is there and what will be the value from the map which has to be replaced.. if not there then "?"
                StringBuilder temp = new StringBuilder();//this is the temporary stringbuilder
                while(i<n && s.charAt(i) != ')'){
                    //so we make sure that i<n, so we are in limit of string s.. and we dont encounter a (  with no closing bracket.. which will be invalid...and we keep going till finding a closing bracket
                    temp.append(s.charAt(i));
                    i++;//keep going untill u find close bracket
                }
                String key = temp.toString();//convert  to string to check in map and find its value if there
                if(map.containsKey(key)) result.append(map.get(key));
                else 
                result.append("?");//key was not add.. so add question mark
            }else{
                result.append(c);//if its not open bracket.. keep simply adding
            }
        }
        return result.toString();//conversion of stringbuilder to string
    }
}