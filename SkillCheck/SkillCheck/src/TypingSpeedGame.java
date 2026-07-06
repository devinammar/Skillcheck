import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TypingSpeedGame extends JFrame {
    private JTextField inputField;
    private JLabel timerLabel, wpmLabel;
    private JPanel wordsPanel;
    private boolean isProcessing = false;

    private String[] wordList = {
            "apple", "banana", "cat", "dog", "elephant", "fish", "grape", "hat",
            "ice", "juice", "kite", "lemon", "monkey", "nut", "orange", "pear",
            "queen", "rabbit", "snake", "tiger", "umbrella", "van", "water", "tree",
            "yarn", "zebra", "book", "car", "door", "egg", "fan", "gift", "home", "island"
    };

    private String currentSentence;
    private int timeRemaining = 30;
    private int wordsTyped = 0;
    private long startTime;
    private boolean isTyping = false;
    private Timer timer;

    public TypingSpeedGame() {

        setTitle("Typing Speed Test Game");
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        timerLabel = new JLabel("Time: " + timeRemaining + " s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setHorizontalAlignment(SwingConstants.LEFT);

        wpmLabel = new JLabel("WPM: 0");
        wpmLabel.setFont(new Font("Arial", Font.BOLD, 16));
        wpmLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(timerLabel, BorderLayout.WEST);
        labelPanel.add(wpmLabel, BorderLayout.EAST);

        wordsPanel = new JPanel();
        wordsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        inputField = new JTextField(30);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isProcessing) {
                    checkInput();
                }
            }
        });

        add(labelPanel, BorderLayout.NORTH);
        add(wordsPanel, BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);

        setVisible(true);

        timer = new Timer(1000, e -> updateTimer());

        generateSentence();
        timer.start();
    }

    private void generateSentence() {
        Random rand = new Random();
        StringBuilder sentenceBuilder = new StringBuilder();

        for (int i = 0; i < 50; i++) {
            String word = wordList[rand.nextInt(wordList.length)];
            sentenceBuilder.append(word).append(" ");
        }

        currentSentence = sentenceBuilder.toString().trim();
        updateWordsPanel();
    }

    private void updateWordsPanel() {
        wordsPanel.removeAll();

        String[] words = currentSentence.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            JLabel wordLabel = new JLabel(words[i] + " ");
            wordLabel.setFont(new Font("Arial", Font.PLAIN, 18));

            if (i == 0) {
                wordLabel.setForeground(Color.BLUE);
            }

            wordsPanel.add(wordLabel);
        }

        wordsPanel.revalidate();
        wordsPanel.repaint();
    }

    private void updateTimer() {
        if (timeRemaining > 0) {
            timeRemaining--;
            timerLabel.setText("Time: " + timeRemaining + " s");
            if (isTyping) {
                updateWPM();
            }
        } else {
            endGame();
        }
    }

    private void updateWPM() {
        double minutesElapsed = (System.currentTimeMillis() - startTime) / 60000.0;
        if (minutesElapsed > 0) {
            int wpm = (int) (wordsTyped / minutesElapsed);
            wpmLabel.setText("WPM: " + wpm);
        }
    }

    private void endGame() {
        timer.stop();
        inputField.setEnabled(false);
        double minutesElapsed = (System.currentTimeMillis() - startTime) / 60000.0;
        int finalWPM = (int) (wordsTyped / minutesElapsed);
        wpmLabel.setText("WPM: " + finalWPM);
        JOptionPane.showMessageDialog(this,
                "Time's up!\nWords typed: " + wordsTyped +
                        "\nFinal WPM: " + finalWPM);
    }

    private void checkInput() {
        isProcessing = true;

        try {
            if (!isTyping && !inputField.getText().isEmpty()) {
                isTyping = true;
                startTime = System.currentTimeMillis();
            }

            String typedText = inputField.getText().trim();
            String[] currentWords = currentSentence.split("\\s+");

            if (currentWords.length > 0) {
                String currentWord = currentWords[0];

                if (typedText.equals(currentWord)) {
                    wordsTyped++;
                    SwingUtilities.invokeLater(() -> {
                        currentSentence = String.join(" ",
                                java.util.Arrays.copyOfRange(currentWords, 1, currentWords.length));
                        inputField.setText("");
                        if (currentSentence.isEmpty()) {
                            generateSentence();
                        } else {
                            updateWordsPanel();
                        }
                        updateWPM();
                    });
                }

                if (currentWord.startsWith(typedText)) {
                    inputField.setForeground(Color.BLACK);
                } else {
                    inputField.setForeground(Color.RED);
                }
            }
        } finally {
            isProcessing = false;
        }
    }

}
