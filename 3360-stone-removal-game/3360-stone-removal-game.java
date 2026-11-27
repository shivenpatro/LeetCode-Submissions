class Solution {
    //so basically alice starts by taking 10 stones, then bob takes 9, then alice takes 8 and it keeps going down by 1 each turn..
    //whoever doesnt have enough stones left to make their move loses the game..
    //notice one simple thing: alice always plays on odd turns (1, 3, 5..) and bob always plays on even turns (2, 4, 6..)...
    //so we just simulate the game and count how many total turns got completed.. if total turns is odd, that means alice made the last valid move and bob got stuck -> alice wins!! if even, bob made the last move so alice got stuck -> alice loses
    public boolean canAliceWin(int n) {
        int take = 10; //alice always starts by removing 10 stones
        int steps = 0; //counts how many valid moves actually happened
        while (n >= take) { //keep going as long as enough stones are left in the pile
            n -= take; //take stones out of the pile
            take--; //next player has to take 1 less stone
            steps++; //one turn done
        }
        return steps % 2 == 1; //odd steps means alice played last so she wins.. even means bob played last so false
    }
}