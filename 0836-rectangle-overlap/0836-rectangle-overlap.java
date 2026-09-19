class Solution {
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        // Two rectangles overlap if and only if they overlap on both X and Y axes
        return rec1[0] < rec2[2] && // rec1 starts before rec2 ends (X)
               rec1[2] > rec2[0] && // rec1 ends after rec2 starts (X)
               rec1[1] < rec2[3] && // rec1 starts before rec2 ends (Y)
               rec1[3] > rec2[1];   // rec1 ends after rec2 starts (Y)
    }
}