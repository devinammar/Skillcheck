import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ReactionTimeGame extends JFrame {
    private JButton reactionButton;
    private long startTime;

    public ReactionTimeGame() {
        setTitle("Reaction Time Game");
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel instructions = new JLabel("Pencet tombol saat berubah warna menjadi hijau!!",
                SwingConstants.CENTER);
        reactionButton = new JButton("Wait...");
        reactionButton.setBackground(Color.RED);
        reactionButton.setEnabled(false);
        reactionButton.addActionListener(e -> checkReaction());

        add(instructions, BorderLayout.NORTH);
        add(reactionButton, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
        startGame();
    }

    private void startGame() {
        Random random = new Random();
        int delay = 2000 + random.nextInt(3000);

        Timer timer = new Timer(delay, e -> {
            reactionButton.setText("Click Me!");
            reactionButton.setBackground(Color.GREEN);
            reactionButton.setEnabled(true);
            startTime = System.currentTimeMillis();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void checkReaction() {
        long reactionTime = System.currentTimeMillis() - startTime;
        JOptionPane.showMessageDialog(this, "Your reaction time: " + reactionTime + " ms");
        reactionButton.setEnabled(false);
    }
}
