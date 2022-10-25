package eecs40;
import java.util.*;

public class ExprCalculator implements CalculatorInterface {
    private String expression = "", temp = "";

    static int precedence(String i){
        return switch (i) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^", "mod","sqrt", "fac","sin","cos", "tan", "log", "ln" -> 3;
            //case "sin","cos", "tan", "log", "ln" -> 3;
            default -> -1;
        };
    }

    static ArrayList<String> postFix(String input_str){
        String unaryOps = "+-*/^m", binaryOps = "sctlf()", current = "";
        Stack<String> stack = new Stack<>();
        Queue<String> numQueue = new LinkedList<>();
        ArrayList<String> result = new ArrayList<>();

        for(int i = 0; i < input_str.length(); ++i){
             current = Character.toString(input_str.charAt(i));

            if(current.equals("(")){
                stack.push(current);
            }else if (current.equals(")")){
                System.out.println(stack);
                String y = stack.peek();
                while(!(stack.isEmpty()) && !y.equals("(")){
                    result.add(stack.pop());
                    if(!stack.isEmpty()){
                        stack.pop();
                    }
                }
            }else if(Character.isDigit(input_str.charAt(i))){
                if(i+1 < input_str.length()){
                    if((unaryOps.contains(Character.toString(input_str.charAt(i+1))) || (binaryOps.contains(Character.toString(input_str.charAt(i+1)))))){
                        StringBuilder temp = new StringBuilder();
                        while(!numQueue.isEmpty()){
                            temp.append(numQueue.peek());
                            numQueue.remove();
                        }
                        temp.append(current);
                        stack.push(temp.toString());
                        if (!stack.isEmpty()){
                            result.add(stack.pop());
                        }
                    }else{
                        numQueue.add(current);
                    }
                }else{
                    if((unaryOps.contains(Character.toString(input_str.charAt(i-1))) || (binaryOps.contains(Character.toString(input_str.charAt(i-1)))))){
                        stack.push(current);
                        result.add(stack.pop());

                    }else{
                        StringBuilder temp = new StringBuilder();
                        while(!numQueue.isEmpty()){
                            temp.append(numQueue.peek());
                            numQueue.remove();
                        }
                        temp.append(current);
                        stack.push(temp.toString());
                        result.add(stack.pop());
                    }
                }

            }else if(unaryOps.contains(current)){
                while(!stack.isEmpty() && (precedence(current) <= precedence(stack.peek()))){
                    result.add(stack.pop());
                }
                stack.push(current);
            }else if(current.equals("s")){
                if(input_str.charAt(i+1) == 'i'){
                    while(!stack.isEmpty() && (precedence("sin") <= precedence(stack.peek()))){
                        result.add(stack.pop());
                    }
                    stack.push("sin");
                }else if(input_str.charAt(i+1) == 'q'){
                    while(!stack.isEmpty() && (precedence("sqrt") <= precedence(stack.peek()))){
                        result.add(stack.pop());
                    }
                    stack.push("sqrt");
                }
            }else if(current.equals("c")){
                Character x = input_str.charAt(i+1);
                if(x.equals('o')) {
                    while (!stack.isEmpty() && (precedence("cos") <= precedence(stack.peek()))) {
                        result.add(stack.pop());
                    }
                    stack.push("cos");
                }
            }else if(current.equals("t")){
                while(!stack.isEmpty() && (precedence("tan") <= precedence(stack.peek()))){
                    result.add(stack.pop());
                }
                stack.push("tan");
            }else if(current.equals("f")){
                while(!stack.isEmpty() && (precedence("fac") <= precedence(stack.peek()))){
                    result.add(stack.pop());
                }
                stack.push("fac");
            }else if(current.equals("l")){
                Character x = input_str.charAt(i+1);
                if(x.equals('o')){
                    while(!stack.isEmpty() && (precedence("log") <= precedence(stack.peek()))){
                        result.add(stack.pop());
                    }
                    stack.push("log");
                }else if(input_str.charAt(i+1) == 'n'){
                    while(!stack.isEmpty() && (precedence("ln") <= precedence(stack.peek()))){
                        result.add(stack.pop());
                    }
                    stack.push("ln");
                }
            }
        }
        System.out.println(stack);


        while(!stack.isEmpty()){
            if(stack.peek().equals("(")){
                return new ArrayList<>();
            }
            result.add(stack.pop());
        }

        return result;
    }


    private void eval() {
        String unaryOps = "+-*/^m", binaryOps = "sincostanloglnfacsqrt", current;
        boolean decimal = false, error = false, front_par = false, back_par = false, error_p = false, error_z = false;
        for(int i = 0; i < expression.length(); i++){
            current = Character.toString(expression.charAt(i));
            if(current.equals(".")){                           //if there is more than one decimal point
                if(decimal){
                    error = true;
                    break;
                }else{
                    decimal = true;
                }
            }else if(current.equals("/")){//Divide by 0
                Character temp = expression.charAt(i+1);
                if(temp.equals('0')){
                    error_z = true;
                    break;
                }
            }else if(Character.isDigit(current.charAt(0))){             //if binary ops show up after a number
                if(i+1<expression.length()) {
                    if (binaryOps.contains(Character.toString(expression.charAt(i + 1)))) {
                        error = true;
                        break;
                    }
                }
            } else if(unaryOps.contains(current)){
                if(unaryOps.contains(Character.toString(expression.charAt(i+1)))){ //if two operators show up after one another
                    error = true;
                    break;
                }
            } else if(Objects.equals(current, ")")){                    //parentheses error
                if(!back_par){
                    back_par = true;
                }
            }else if(current.equals("(")){                     //parentheses error
                if(!front_par){
                    front_par = true;
                }
            }
        }

        if(front_par && !back_par){
            temp = expression;
            expression = "Error: Parentheses";
            error_p = true;
        }else if(!front_par && back_par){
            temp = expression;
            expression = "Error: Parentheses";
            error_p = true;
        }

        if(error){
            temp = expression;
            expression = "Error";
        }else if(error_p){
            temp = expression;
            expression = "Error: Parentheses";
        }else if (error_z){
            temp = expression;
            expression = "NaN";
        }else if(!error && !error_p && !error_z){
            ArrayList<String> post = postFix(expression);
            Stack<Double> numStack = new Stack<>();
            System.out.println(post);
            for (String current_e : post) {
                if (Character.isDigit(current_e.charAt(0))) {
                    numStack.push(Double.parseDouble(current_e));
                } else {
                    if (unaryOps.contains(current_e)) {
                        double val1 = numStack.pop();
                        double val2 = numStack.pop();

                        switch (current_e) {
                            case "+" -> numStack.push(val1 + val2);
                            case "-" -> numStack.push(val1 - val2);
                            case "*" -> numStack.push(val1 * val2);
                            case "/" -> numStack.push(val2 / val1);
                            case "^" -> numStack.push( Math.pow(val1, val2));
                            case "mod" -> numStack.push(val1 % val2);
                        }
                    } else{
                        double val = numStack.pop();

                        switch (current_e) {
                            case "sin" -> numStack.push( Math.sin(val));
                            case "cos" -> numStack.push( Math.cos(val));
                            case "tan" -> numStack.push( Math.tan(val));
                            case "log" -> numStack.push( Math.log10(val));
                            case "ln" -> numStack.push(Math.log(val));
                            case "fac" -> {
                                double temp_num = 1;
                                for(int j = 1; j <= val; ++j){
                                    temp_num *= j;
                                }
                                numStack.push(temp_num);
                            }
                        }
                    }
                }
                System.out.println(numStack);
                System.out.println(current_e);
            }

            expression = Double.toString(numStack.pop());
        }

    }
    @Override
    public void acceptInput(String s) {
        switch (s) {
            case "=" -> eval();
            case "Backspace" -> {
                if (temp.equals("")) {
                    expression = expression.substring(0, expression.length() - 1);
                } else {
                    expression = temp.substring(0, temp.length() - 1);
                    temp = expression;
                }
            }
            case "C" -> expression = ""; // clear!
            default -> expression = expression + s;// accumulate input String
        }
    }
    @Override
    public String getDisplayString() {
        return expression;
    }
}