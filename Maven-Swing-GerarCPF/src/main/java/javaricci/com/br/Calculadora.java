package javaricci.com.br;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class Calculadora extends JFrame {
	private static final long serialVersionUID = 1L;
	private final JTextField txtDisplay;

    public Calculadora() {
        txtDisplay = new JTextField();
        initComponents();
    }

    private void initComponents() {
        txtDisplay.setFont(new Font("Consolas", Font.BOLD, 24));
        txtDisplay.setHorizontalAlignment(JTextField.RIGHT);

        // Adiciona listener para ENTER
        txtDisplay.addActionListener(e -> processInput());

        // Botões de números e operações
        JButton[] numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            final int num = i;
            numberButtons[i] = createButton(String.valueOf(num), e -> appendToDisplay(String.valueOf(num)));
        }

        JButton buttonPlus = createButton("+", e -> appendToDisplay("+"));
        JButton buttonMinus = createButton("-", e -> appendToDisplay("-"));
        JButton buttonMultiply = createButton("*", e -> appendToDisplay("*"));
        JButton buttonDivide = createButton("/", e -> appendToDisplay("/"));
        JButton buttonEqual = createButton("=", e -> processInput());
        JButton buttonClear = createButton("C", e -> clearDisplay());

        // Layout
        setLayout(new GridLayout(5, 4));
        setTitle("Calculadora");
        add(txtDisplay);

        // Adiciona os botões na ordem
        for (int i = 1; i <= 9; i++) {
            add(numberButtons[i]);
        }
        add(numberButtons[0]);
        add(buttonPlus);
        add(buttonMinus);
        add(buttonMultiply);
        add(buttonDivide);
        add(buttonEqual);
        add(buttonClear);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Consolas", Font.BOLD, 24));
        button.addActionListener(action);
        return button;
    }

    private void appendToDisplay(String text) {
        txtDisplay.setText(txtDisplay.getText() + text);
    }

    private void processInput() {
        String input = txtDisplay.getText();
        try {
            double result = evaluateExpression(input);
            txtDisplay.setText(String.valueOf(result));
        } catch (Exception e) {
            txtDisplay.setText("Error");
        }
    }

    private void clearDisplay() {
        txtDisplay.setText("");
    }

    /**
     * Avalia uma expressão matemática simples usando pilhas.
     */
    private double evaluateExpression(String expression) throws Exception {
        // Remover espaços desnecessários
        expression = expression.replaceAll("\\s+", "");

        // Pilhas para números e operadores
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);

            // Caso seja um número, processa até formar o número completo
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                numbers.push(Double.parseDouble(sb.toString()));
                continue;
            }

            // Caso seja um operador
            if ("+-*/".indexOf(c) >= 0) {
                while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(c);
            }
            i++;
        }

        // Processa os operadores restantes
        while (!operators.isEmpty()) {
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    /**
     * Verifica a precedência dos operadores.
     */
    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    /**
     * Aplica a operação matemática.
     */
    private double applyOperation(char op, double b, double a) throws Exception {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Cannot divide by zero");
                return a / b;
            default:
                throw new UnsupportedOperationException("Invalid operator");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculadora().setVisible(true));
    }
}
