package javaricci.com.br;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.util.ArrayList;

class GeraCPF {
    private ArrayList<Integer> listaAleatoria = new ArrayList<Integer>();
    private ArrayList<Integer> listaNumMultiplicados = null;

    public int geraNumAleatorio() {
        return (int)(Math.random() * 10);
    }

    public ArrayList<Integer> geraCPFParcial() {
        listaAleatoria.clear(); // Limpa a lista antes de gerar um novo CPF
        for (int i = 0; i < 9; i++) listaAleatoria.add(geraNumAleatorio());
        return listaAleatoria;
    }

    public ArrayList<Integer> geraDigito() {
        listaNumMultiplicados = new ArrayList<Integer>();
        int primeiroDigito;
        int totalSomatoria = 0;
        int restoDivisao;
        int peso = 10;

        for (int item : listaAleatoria) listaNumMultiplicados.add(item * peso--);
        for (int item : listaNumMultiplicados) totalSomatoria += item;

        restoDivisao = (totalSomatoria % 11);
        primeiroDigito = restoDivisao < 2 ? 0 : 11 - restoDivisao;
        listaAleatoria.add(primeiroDigito);
        
        // Repetir para o segundo dígito
        totalSomatoria = 0;
        peso = 11; // Peso para o segundo dígito
        for (int item : listaAleatoria) listaNumMultiplicados.add(item * peso--);
        for (int item : listaNumMultiplicados) totalSomatoria += item;

        restoDivisao = (totalSomatoria % 11);
        int segundoDigito = restoDivisao < 2 ? 0 : 11 - restoDivisao;
        listaAleatoria.add(segundoDigito);

        return listaAleatoria;
    }

    public String geraCPFFinal() throws ParseException {
        geraCPFParcial();
        geraDigito();
        
        StringBuilder texto = new StringBuilder();
        for (int item : listaAleatoria) texto.append(item);
        
        MaskFormatter mf = new MaskFormatter("###.###.###-##");
        mf.setValueContainsLiteralCharacters(false);
        
        return mf.valueToString(texto.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GeraCPF().criarTela());
    }

    private void criarTela() {
        JFrame frame = new JFrame("Gerador de CPF");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        
        JButton gerarButton = new JButton("Gerar CPF");
        
        gerarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String cpfGerado = geraCPFFinal();
                    textArea.setText(cpfGerado);
                } catch (ParseException ex) {
                    textArea.setText("Erro ao gerar CPF: " + ex.getMessage());
                }
            }
        });

        panel.add(gerarButton, BorderLayout.NORTH);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        
        frame.setVisible(true);
    }
}
