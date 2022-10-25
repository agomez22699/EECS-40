public class Calculator  {
    String displayString = "0";
    String Operand1, Operand2;
    String Operator = "0"; // to check whether to put s in operand1 or operand2
    int operatorSwitch; // to switch where to store the "S"
    String oldS; // old s char
    int calState = 0; // different calculator states

    public void acceptInput(String s) {
        if (calState == 0) { //means we are at the beginning of calculator state
            if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
                oldS = s;
                displayString = s;
                Operand1 = s;
                calState = 1;
            } else if (s.charAt(0) == '.') {
                oldS = ".";
                displayString += '.';
                Operand1 = displayString; // transferring 0. to the operand1
                calState = 1;
            } else if (s.charAt(0) == 'C') {
                oldS = "";
                Operator = "0";
                Operand1 = "";
                Operand2 = "";
                displayString = "0";
            } else if ((s.charAt(0) == '+') || (s.charAt(0) == '-') || (s.charAt(0) == '*') || (s.charAt(0) == '/')) {
                Operator = s; //this means the operation wanting to be done will be sent to the Operator variable
                calState = 4; // where we know that operator 1 is 0
            }
        } //end of beginning state of calculator
        else if (calState == 1) { // at this state, we either add s to operand1, operand2, or operator
            if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
                if (Operator.equals("0")) {
                    oldS = s;
                    displayString += s;
                    Operand1 += s;
                } else if (operatorSwitch == 1) {
                    displayString = "";
                    oldS = s;
                    displayString = s;
                    Operand2 = s;
                    operatorSwitch = 0;
                } else {
                    oldS = s;
                    displayString += s;
                    Operand2 += s;
                }

            } else if (s.charAt(0) == '.') { // the case where calculator ignores multiple . inputs
                if (!oldS.equals(s)) {
                    if (Operator.equals("0")) {
                        oldS = s;
                        displayString += s;
                        Operand1 += s;
                    } else {
                        oldS = s;
                        displayString += s;
                        Operand2 += s;
                    }
                }
            } else if ((s.charAt(0) == '+') || (s.charAt(0) == '-') || (s.charAt(0) == '*') || (s.charAt(0) == '/')) {
                if (oldS.charAt(0) >= '0' && oldS.charAt(0) <= '9') {
                    oldS = s;
                    Operator = s;
                    operatorSwitch = 1;
                } else {
                    displayString = "Error";
                    calState = 2; // where its in a constant error state
                }
            } else if (s.charAt(0) == 'C') {
                calState = 0;
                oldS = "";
                Operator = "0";
                Operand1 = "";
                Operand2 = "";
                displayString = "0";
            } else if (s.charAt(0) == '=') {
                if (Operator.equals("/") && (Operand2.equals("0"))) {
                    displayString = "NaN";
                    calState = 3;
                } else {
                    double tempResult = switch (Operator) {
                        case "+" -> (Double.parseDouble(Operand1) + Double.parseDouble(Operand2));
                        case "*" -> (Double.parseDouble(Operand1) * Double.parseDouble(Operand2));
                        case "-" -> (Double.parseDouble(Operand1) - Double.parseDouble(Operand2));
                        default -> (Double.parseDouble(Operand1) / Double.parseDouble(Operand2));
                    };
                    Operand1 = Double.toString(tempResult);
                    displayString = Operand1;
                    Operand2 = "";
                    operatorSwitch = 1;
                    oldS = displayString;
                    calState = 1;
                }
            }

        } else if (calState == 2) { //constant error state until C is entered
            if (s.charAt(0) == 'C') {
                calState = 0;
                oldS = "";
                Operator = "0";
                Operand1 = "";
                Operand2 = "";
                displayString = "0";
            } else {
                displayString = "Error";
            }
        } else if (calState == 3) {// constant NaN state until C is entered
            if (s.charAt(0) == 'C') {
                calState = 0;
                oldS = "";
                Operator = "0";
                Operand1 = "";
                Operand2 = "";
                displayString = "0";
            } else {
                displayString = "NaN";
            }
        } else if (calState == 4) {
            if ((s.charAt(0) >= '0' && s.charAt(0) <= '9') && (operatorSwitch == 0)) {
                switch (Operator) {
                    case "-" -> {
                        displayString = "-" + s;
                        oldS = displayString;
                        Operand1 = displayString;
                        operatorSwitch = 3;
                    }
                    case "+" -> {
                        displayString = s;
                        oldS = displayString;
                        Operand1 = displayString;
                        operatorSwitch = 3;
                    }
                    case "*", "/" -> {
                        displayString = "0";
                        oldS = displayString;
                        Operand1 = displayString;
                        operatorSwitch = 3;
                    }
                }
            } else if ((s.charAt(0) >= '0' && s.charAt(0) <= '9') && (operatorSwitch == 3)) {
                oldS = s;
                displayString += s;
                Operand1 = displayString;
            } else if ((s.charAt(0) >= '0' && s.charAt(0) <= '9') && (operatorSwitch == 1)) {
                displayString = "";
                oldS = s;
                displayString = s;
                Operand2 = s;
                operatorSwitch = 2;
            } else if ((s.charAt(0) >= '0' && s.charAt(0) <= '9') && (operatorSwitch == 2)) {
                oldS = s;
                displayString += s;
                Operand2 += s;
            } else if (s.charAt(0) == '.') { // the case where calculator ignores multiple . inputs
                if (!oldS.equals(s)) {
                    oldS = s;
                    displayString += s;
                    Operand2 += s;
                }
            } else if ((s.charAt(0) == '+') || (s.charAt(0) == '-') || (s.charAt(0) == '*') || (s.charAt(0) == '/')) {
                if (oldS.charAt(0) >= '0' && oldS.charAt(0) <= '9') {
                    oldS = s;
                    Operator = s;
                    operatorSwitch = 1;
                } else {
                    displayString = "Error";
                    calState = 2; // where its in a constant error state
                }
            } else if (s.charAt(0) == 'C') {
                calState = 0;
                oldS = "";
                Operator = "0";
                Operand1 = "";
                Operand2 = "";
                displayString = "0";
            } else if (s.charAt(0) == '=') {
                 if (Operator.equals("/") && (Operand2.equals("0"))) {
                    displayString = "NaN";
                    calState = 3;
                } else if (Operand2 == null) {
                    Operand2 = "0";
                    double tempResult = switch (Operator) {
                        case "+" -> (Double.parseDouble(Operand1) + Double.parseDouble(Operand2));
                        case "*" -> (Double.parseDouble(Operand1) * Double.parseDouble(Operand2));
                        case "-" -> (Double.parseDouble(Operand1) - Double.parseDouble(Operand2));
                        default -> (Double.parseDouble(Operand1) / Double.parseDouble(Operand2));
                    };
                    Operand1 = Double.toString(tempResult);
                    displayString = Operand1;
                    Operand2 = "";
                    operatorSwitch = 1;
                    calState = 1;
                }
                 else{
                     double tempResult = switch (Operator) {
                         case "+" -> (Double.parseDouble(Operand1) + Double.parseDouble(Operand2));
                         case "*" -> (Double.parseDouble(Operand1) * Double.parseDouble(Operand2));
                         case "-" -> (Double.parseDouble(Operand1) - Double.parseDouble(Operand2));
                         default -> (Double.parseDouble(Operand1) / Double.parseDouble(Operand2));
                     };
                     Operand1 = Double.toString(tempResult);
                     displayString = Operand1;
                     Operand2 = "";
                     operatorSwitch = 1;
                     calState = 1;
                 }
            }
        }
    }
    public String getDisplayString(){
        return displayString;
    }
}
