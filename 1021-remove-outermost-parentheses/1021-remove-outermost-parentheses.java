//super duper easy question once you understand the description actually, its just bunch of bs writte seems like rando maths... but it just means... remove the outermost brackets of a primitive parenthese string... now what it means na ki... simply like we always do  +1 for seeing open bracket and -1 for seeing close bracket na...so so na see... when the count is 0... we dont add... and when count is not 0..we add it...LMAO THATS IT THATHS ENOUGH TO SOLVE THIS QUESTION LOL....

/*class Solution {
    public String removeOuterParentheses(String s) {
        int n = s.length();
        int count = 0;//the main thing... which keeps the record of balance pairs na.. like in the way of doing +1 -1... its keeping track of open adn close brackets in just 1 variable
        String result ="";
        for(int i = 0;i<n;i++){
            char c = s.charAt(i);
            if(c== '('){
                if(count != 0) {
                    result+=c;//concatenating only if the count is not 0.... that means what??? like see... if the string starts with open, we dont want that to be appended... like in ex 1... so so na.. for open brackets... we first check and then do count++, but for close we will frist do count-- and then check if its equal to 0.. why? because think about it bro... u start smthing.... and u wont include it... so obv u first check and then increase for every open bracket... but for closing... u wouldhv come to that na? like there has to be open behind it na(ITS ONLY VALID STRINGS BRO), so os... u have to do -- and then only check na?
                    //since we saw an open bracket so incersae the count na
                }
                count++;
            }
            else{
                //close bracket case brother
                count--;//first ding -- becasue we came here so we need to reduce the count,and for open we cant do same and incerase first because just dry run any string.. ex1 only.... the 1st and 6th have to be ignored na?... now if u do count++ and then check.... 1st will be also included right? which we dont want.. then count++(1st POS DONE) because it was open even tough not added, comes to 2nd.. added cuz it was 1 and made to 2(2nd POS DONE)... leaveing second and comes to 3rd count 2 -> 1..first -- then check(3rd POS DONE) ... leaving third and comes to 4th count is checked.. it was 1 so added and made 2(4th POS DONE), comes to 5th.. and first --(2->1) and then added(5ht POS DONE) ... and entering 6th before check..IT BECOMES 0!!!.. so 6th also not added(6th POS DONE)
                //for like ex 3... u can see decomposition will be '()' and '()' only.. so ya... nothing gets added
                if(count!=0){
                    result+=c;
                }  
            }
        } 
        return result;       
    }
}*/

//stringbuilder for faster  
class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                if (count != 0) {
                    result.append(c);
                }
                count++;
            } else {
                count--;
                if (count != 0) {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }
}
