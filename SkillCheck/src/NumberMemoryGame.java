import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class NumberMemoryGame extends JFrame {
    private JLabel instructionLabel, numberLabel;
    private JTextField inputField;
    private JButton submitButton, startButton;
    private int level = 1;
    private String currentSequence;
    private Random random;
    private Timer displayTimer;

    public NumberMemoryGame() {
        setTitle("Number Memory Game");
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        instructionLabel = new JLabel("Tekan 'Mulai' untuk memulai permainan", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(instructionLabel, BorderLayout.NORTH);

        numberLabel = new JLabel("", SwingConstants.CENTER);
        numberLabel.setFont(new Font("Arial", Font.BOLD, 32));
        add(numberLabel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(10);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton = new JButton("Kirim");
        submitButton.setEnabled(false);

        inputPanel.add(inputField);
        inputPanel.add(submitButton);
        add(inputPanel, BorderLayout.SOUTH);

        startButton = new JButton("Mulai");
        add(startButton, BorderLayout.WEST);

        random = new Random();

        // Action untuk tombol mulai
        startButton.addActionListener(e -> startGame());

        // Action untuk tombol submit
        submitButton.addActionListener(e -> checkAnswer());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame() {
        level = 1;
        instructionLabel.setText("Perhatikan angka, kemudian masukkan jawabannya!");
        startButton.setEnabled(false);
        inputField.setEnabled(false);
        submitButton.setEnabled(false);
        generateSequence();
    }

    private void generateSequence() {
        StringBuilder sequenceBuilder = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sequenceBuilder.append(random.nextInt(10)); // Angka acak 0-9
        }
        currentSequence = sequenceBuilder.toString();
        displaySequence();
    }

    private void displaySequence() {
        numberLabel.setText(currentSequence);
        inputField.setEnabled(false);
        submitButton.setEnabled(false);

        // Tampilkan angka selama 2 detik
        displayTimer = new Timer(2000, e -> {
            numberLabel.setText("");
            inputField.setEnabled(true);
            submitButton.setEnabled(true);
            inputField.requestFocus();
            displayTimer.stop();
        });
        displayTimer.setRepeats(false);
        displayTimer.start();
    }

    private void checkAnswer() {
        String userAnswer = inputField.getText().trim();
        if (userAnswer.equals(currentSequence)) {
            level++;
            inputField.setText("");
            instructionLabel.setText("Benar! Lanjut ke level " + level + ".");
            generateSequence();
        } else {
            endGame();
        }
    }

    private void endGame() {
        inputField.setEnabled(false);
        submitButton.setEnabled(false);
        startButton.setEnabled(true);
        JOptionPane.showMessageDialog(this,
                "Game Over!\nLevel tertinggi: " + level +
                        "\nCoba lagi untuk skor lebih tinggi!");
        instructionLabel.setText("Tekan 'Mulai' untuk bermain lagi.");
    }
}
