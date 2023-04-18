import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SpringArray {

    //Do we assume that input is validated?
    public static Spring equivalentSpring(String springExpr) {
        Stack<Spring> stack = new Stack<>();
        Spring currentSpring = new Spring();

        for (char c : springExpr.toCharArray()) {
            if (c == '[') {
                stack.push(currentSpring);
                currentSpring = new Spring();
            } else if (c == ']') {
                Spring poppedSpring = stack.pop();
                currentSpring = poppedSpring.inParallel(currentSpring);
            } else if (c == '{') {
                stack.push(currentSpring);
                currentSpring = new Spring();
            } else if (c == '}') {
                Spring poppedSpring = stack.pop();
                currentSpring = poppedSpring.inSeries(currentSpring);
            }
        }

        return currentSpring;
    }

    private static Spring parallelEquivalentSpring(Spring[] springs) {
        double totalK = 0.0;
        for (Spring spring : springs) {
            totalK += 1.0 / spring.getK();
        }
        return new Spring(1.0 / totalK);
    }

    private static Spring seriesEquivalentSpring(Spring[] springs) {
        double totalK = 0.0;
        for (Spring spring : springs) {
            totalK += spring.getK();
        }
        return new Spring(totalK);
    }


    public static Spring equivalentSpring(String springExpr, Spring[] springs) throws IllegalArgumentException {

        if (springExpr == null || springs == null || springs.length == 0) {
            throw new IllegalArgumentException("Invalid input: springExpr or springs array is null or empty");
        }

        Stack<Spring> stack = new Stack<>();

        for (int i = 0; i < springExpr.length(); i++) {
            char c = springExpr.charAt(i);

            if (c == '{') {
                stack.push(null);
            } else if (c == '[') {
                stack.push(new Spring());
            } else if (c == '}') {
                List<Spring> parallelSprings = new ArrayList<>();
                while (stack.peek() != null) {
                    parallelSprings.add(stack.pop());
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("Invalid input: unmatched '}'");
                    }
                }
                stack.pop();
                Spring equivalentSpring = parallelEquivalentSpring(parallelSprings.toArray(new Spring[0]));
                stack.push(equivalentSpring);
            } else if (c == ']') {
                Spring[] seriesSprings = new Spring[stack.size()];
                int j = stack.size() - 1;
                while (!stack.isEmpty() && stack.peek() != null) {
                    seriesSprings[j--] = stack.pop();
                }
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Invalid input: unmatched ']'");
                }
                stack.pop();
                Spring equivalentSpring = seriesEquivalentSpring(seriesSprings);
                stack.push(equivalentSpring);
            } else {
                throw new IllegalArgumentException("Invalid input: unrecognized character '" + c + "'");
            }
        }

        if (stack.size() != 1 || stack.peek() == null) {
            throw new IllegalArgumentException("Invalid input: mismatched braces or brackets");
        }
        return stack.pop();
    }

}
