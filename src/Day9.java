import lib.InputUtil;

import java.io.IOException;

public class Day9 {
    public static void main(String[] args) throws IOException {
        String input = InputUtil.readAsString("input9.txt");
        countGroups(input);
    }

    private static void countGroups(String s) {
        int level = 0;
        int count = 0;
        boolean garbage = false;
        int garbageCount = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '!') {
                i++;
            } else if (c == '<' && !garbage) {
                garbage = true;
            } else if (c == '>') {
                garbage = false;
            } else if (c == '{' && !garbage) {
                level++;
            } else if (c == '}' && !garbage) {
                count += level;
                level--;
            } else if (garbage) {
                garbageCount++;
            }
        }
        System.out.println(count);
        System.out.println(garbageCount);
    }
}
