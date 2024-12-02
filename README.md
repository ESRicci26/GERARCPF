Aplicação Java Swing com dependência MAVEN para efetuar a geração de CPFs válidos e aleatórios.

Explicação do Código
--------------------
Método main: Inicia a aplicação Swing.
Método criarTela: Cria a interface gráfica com um botão e um campo de texto.

Interface Gráfica
-----------------
JFrame: A janela principal da aplicação.
JPanel: Um painel que organiza os componentes.
JButton: Um botão que, ao ser clicado, chama o método para gerar o CPF.
JTextArea: Um campo de texto onde o CPF gerado é exibido.

Funcionalidade
--------------
Quando o botão "Gerar CPF" é clicado, o aplicativo chama geraCPFFinal() e exibe o resultado no JTextArea.
Se ocorrer um erro, uma mensagem de erro é exibida.
Esse código fornece uma interface simples e funcional para gerar e visualizar CPFs válidos e aleatórios.
