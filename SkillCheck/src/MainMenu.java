import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Human SkillCheck");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        JLabel title = new JLabel("Welcome to SkillCheck!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title);

        JButton typingSpeedButton = new JButton("Typing Speed Game");
        JButton reactionGameButton = new JButton("Reaction Time Game");
        JButton numberMemoryButton = new JButton("Number Memory Game");
        JButton visualMemoryButton = new JButton("Visual Memory Game");

        typingSpeedButton.addActionListener(e -> new TypingSpeedGame());
        reactionGameButton.addActionListener(e -> new ReactionTimeGame());
        numberMemoryButton.addActionListener(e -> new NumberMemoryGame());
        visualMemoryButton.addActionListener(e -> new VisualMemoryGame());

        add(typingSpeedButton);
        add(reactionGameButton);
        add(numberMemoryButton);
        add(visualMemoryButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
