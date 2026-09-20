class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();//final solution in list of lists
        List<Integer> temp = new ArrayList<>();//the temp list, which will be storing that particular combination
        Set<Integer> st = new HashSet<>();//this is the hashset, which makes sure that we dont resuse a number, like making combinations like 1,1,1 or 1,2,2.. etc... so we make a hashset, and check that if that number is already tehre in the hashset, then dont use it, continue, and we only hashset because the contains funciton in hashset  takes O(1), and not O(n)
        solve(temp,nums,st,result);
        return result;
    }
    public void solve(List<Integer> temp, int[] nums, Set<Integer> st, List<List<Integer>> result){
        if(temp.size() == nums.length){//now, if the temp size is equal to qn array, that means that particular combination has been formed, so add that list to list of lists
            result.add(new ArrayList<>(temp)); // make a fresh copy here, because temp is just a reference and since we backtrack and remove elements later, temp will end up empty [] at the end and result will have empty lists
            return;
        }
        for(int i = 0; i<nums.length; i++){
            if(st.contains(nums[i])) continue;//if that is already present, please move the i and try the next number, because trying this is invalid since its already there in the set which means its already there in the temp list as well

            //following the khandani template for backtracking
            //1. do
            temp.add(nums[i]);
            st.add(nums[i]);
            //2. explore
            solve(temp,nums,st,result);//calling the function again, doinf the explore part with i starting from start.. and finding the possible permutations
            //3. undo
            temp.remove(temp.size()-1);//for arraylist, we remove the latest added element, for hashset we can directly pass the value to be removed from the set
            st.remove(nums[i]);//we are removing, since we are doing backtrack, so since we are done exploring, whtever we explored, we have to remove it as well
        }
    }
}

/*
========================================================================================
LINE-BY-LINE EXECUTION DRY RUN FOR nums = [1, 2, 3]
========================================================================================

Initial state:
  nums = [1, 2, 3], length = 3
  temp = [], st = {}, result = []

----------------------------------------------------------------------------------------
BRANCH 1: Start by picking nums[0] = 1
----------------------------------------------------------------------------------------
Call 1: solve(temp=[], st={})
  - i = 0 -> nums[0] = 1 not in st
    * DO:      temp = [1], st = {1}
    * EXPLORE: calls solve(temp=[1], st={1})

    Call 2: solve(temp=[1], st={1})
      - i = 0 -> nums[0] = 1 is already in st -> continue
      - i = 1 -> nums[1] = 2 not in st
        * DO:      temp = [1, 2], st = {1, 2}
        * EXPLORE: calls solve(temp=[1, 2], st={1, 2})

        Call 3: solve(temp=[1, 2], st={1, 2})
          - i = 0 -> nums[0] = 1 in st -> continue
          - i = 1 -> nums[1] = 2 in st -> continue
          - i = 2 -> nums[2] = 3 not in st
            * DO:      temp = [1, 2, 3], st = {1, 2, 3}
            * EXPLORE: calls solve(temp=[1, 2, 3], st={1, 2, 3})

            Call 4: solve(temp=[1, 2, 3], st={1, 2, 3})
              - temp.size() == 3 == nums.length -> BASE CASE REACHED!
              - result.add(new ArrayList<>(temp)) -> SAVED: [1, 2, 3]
              - return back to Call 3

            * UNDO: remove 3 -> temp = [1, 2], st = {1, 2}
          - Loop i ends for Call 3 -> returns back to Call 2

        * UNDO: remove 2 -> temp = [1], st = {1}

      - i = 2 -> nums[2] = 3 not in st
        * DO:      temp = [1, 3], st = {1, 3}
        * EXPLORE: calls solve(temp=[1, 3], st={1, 3})

        Call 5: solve(temp=[1, 3], st={1, 3})
          - i = 0 -> nums[0] = 1 in st -> continue
          - i = 1 -> nums[1] = 2 not in st
            * DO:      temp = [1, 3, 2], st = {1, 3, 2}
            * EXPLORE: calls solve(temp=[1, 3, 2], st={1, 3, 2})

            Call 6: solve(temp=[1, 3, 2], st={1, 3, 2})
              - temp.size() == 3 == nums.length -> BASE CASE REACHED!
              - result.add(new ArrayList<>(temp)) -> SAVED: [1, 3, 2]
              - return back to Call 5

            * UNDO: remove 2 -> temp = [1, 3], st = {1, 3}
          - i = 2 -> nums[2] = 3 in st -> continue
          - Loop i ends for Call 5 -> returns back to Call 2

        * UNDO: remove 3 -> temp = [1], st = {1}
      - Loop i ends for Call 2 -> returns back to Call 1

    * UNDO: remove 1 -> temp = [], st = {}

----------------------------------------------------------------------------------------
BRANCH 2: Start by picking nums[1] = 2
----------------------------------------------------------------------------------------
Call 1 resumed:
  - i = 1 -> nums[1] = 2 not in st
    * DO:      temp = [2], st = {2}
    * EXPLORE: calls solve(temp=[2], st={2})

    Call 7: solve(temp=[2], st={2})
      - i = 0 -> nums[0] = 1 not in st
        * DO:      temp = [2, 1], st = {2, 1}
        * EXPLORE: calls solve(temp=[2, 1], st={2, 1})

        Call 8: solve(temp=[2, 1], st={2, 1})
          - i = 0 -> in st -> continue
          - i = 1 -> in st -> continue
          - i = 2 -> nums[2] = 3 not in st
            * DO:      temp = [2, 1, 3], st = {2, 1, 3}
            * EXPLORE: calls solve(temp=[2, 1, 3], st={2, 1, 3})

            Call 9: solve(temp=[2, 1, 3], st={2, 1, 3})
              - BASE CASE REACHED!
              - result.add(new ArrayList<>(temp)) -> SAVED: [2, 1, 3]
              - return back to Call 8

            * UNDO: remove 3 -> temp = [2, 1], st = {2, 1}
          - Loop i ends -> return to Call 7

        * UNDO: remove 1 -> temp = [2], st = {2}

      - i = 1 -> nums[1] = 2 in st -> continue
      - i = 2 -> nums[2] = 3 not in st
        * DO:      temp = [2, 3], st = {2, 3}
        * EXPLORE: calls solve(temp=[2, 3], st={2, 3})

        Call 10: solve(temp=[2, 3], st={2, 3})
          - i = 0 -> nums[0] = 1 not in st
            * DO:      temp = [2, 3, 1], st = {2, 3, 1}
            * EXPLORE: calls solve(temp=[2, 3, 1], st={2, 3, 1})

            Call 11: solve(temp=[2, 3, 1], st={2, 3, 1})
              - BASE CASE REACHED!
              - result.add(new ArrayList<>(temp)) -> SAVED: [2, 3, 1]
              - return back to Call 10

            * UNDO: remove 1 -> temp = [2, 3], st = {2, 3}
          - i = 1 -> in st -> continue
          - i = 2 -> in st -> continue
          - Loop i ends -> return to Call 7

        * UNDO: remove 3 -> temp = [2], st = {2}
      - Loop i ends -> return to Call 1

    * UNDO: remove 2 -> temp = [], st = {}

----------------------------------------------------------------------------------------
BRANCH 3: Start by picking nums[2] = 3
----------------------------------------------------------------------------------------
Call 1 resumed:
  - i = 2 -> nums[2] = 3 not in st
    * DO:      temp = [3], st = {3}
    * EXPLORE: calls solve(temp=[3], st={3})

    Call 12: solve(temp=[3], st={3})
      - i = 0 -> nums[0] = 1 not in st
        * DO:      temp = [3, 1], st = {3, 1}
        * EXPLORE: calls solve(temp=[3, 1], st={3, 1})

        Call 13: solve(temp=[3, 1], st={3, 1})
          - i = 0 -> in st -> continue
          - i = 1 -> nums[1] = 2 not in st
            * DO:      temp = [3, 1, 2], st = {3, 1, 2}
            * EXPLORE: calls solve(temp=[3, 1, 2], st={3, 1, 2})

            Call 14: solve(temp=[3, 1, 2], st={3, 1, 2})
              - BASE CASE REACHED!
              - result.add(new ArrayList<>(temp)) -> SAVED: [3, 1, 2]
              - return back to Call 13

            * UNDO: remove 2 -> temp = [3, 1], st = {3, 1}
          - i = 2 -> in st -> continue
          - Loop i ends -> return to Call 12

        * UNDO: remove 1 -> temp = [3], st = {3}

      - i = 1 -> nums[1] = 2 not in st
        * DO:      temp = [3, 2], st = {3, 2}
        * EXPLORE: calls solve(temp=[3, 2], st={3, 2})

        Call 15: solve(temp=[3, 2], st={3, 2})
          - i = 0 -> nums[0] = 1 not in st
            * DO:      temp = [3, 2, 1], st = {3, 2, 1}
            * EXPLORE: calls solve(temp=[3, 2, 1], st={3, 2, 1})

            Call 16: solve(temp=[3, 2, 1], st={3, 2, 1})
              - BASE CASE REACHED!
              - result.add(new ArrayList<>(temp)) -> SAVED: [3, 2, 1]
              - return back to Call 15

            * UNDO: remove 1 -> temp = [3, 2], st = {3, 2}
          - i = 1 -> in st -> continue
          - i = 2 -> in st -> continue
          - Loop i ends -> return to Call 12

        * UNDO: remove 2 -> temp = [3], st = {3}
      - i = 2 -> in st -> continue
      - Loop i ends -> return to Call 1

    * UNDO: remove 3 -> temp = [], st = {}

All branches completed!
Final result returned:
[[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
========================================================================================
*/