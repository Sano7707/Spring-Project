import java.util.Stack;

public class SpringArray {

    public static Spring equivalentSpring(String springExpr) {
        Stack<Spring> stack = new Stack<>();
        char[] chars = springExpr.toCharArray();

        for (char c : chars) {
            if (c == '{') {
                stack.push(new Spring());
            } else if (c == '}') {
                if (stack.size() == 1) {
                    return stack.pop();
                }

                Spring current = stack.pop();
                Spring prev = stack.pop();
                stack.push(prev.inSeries(current));
            } else if (c == '[') {
                stack.push(new Spring());
            } else if (c == ']') {
                if (stack.size() == 1) {
                    return stack.pop();
                }

                Spring current = stack.pop();
                Spring prev = stack.pop();
                stack.push(prev.inParallel(current));
            }
        }

        return stack.pop();
    }

    public static Spring equivalentSpring(String springExpr, Spring[] springs) {
        Stack<Spring> stack = new Stack<>();
        char[] chars = springExpr.toCharArray();

        for (char c : chars) {
            if (c == '{') {
                stack.push(springs[0]);
            } else if (c == '}') {
                if (stack.size() == 1) {
                    return stack.pop();
                }

                Spring current = stack.pop();
                Spring prev = stack.pop();
                stack.push(prev.inSeries(current));
            } else if (c == '[') {
                stack.push(springs[0]);
            } else if (c == ']') {
                if (stack.size() == 1) {
                    return stack.pop();
                }

                Spring current = stack.pop();
                Spring prev = stack.pop();
                stack.push(prev.inParallel(current));
            } else {
                int index = Character.getNumericValue(c) - 1;
                stack.push(springs[index]);
            }
        }

        return stack.pop();
    }
}

