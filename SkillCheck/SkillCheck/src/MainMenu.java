import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {

        setTitle("Human SkillCheck");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(75, 155, 185));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(75, 155, 185));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("SkillCheck", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Test and improve your cognitive skills", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(title);
        topPanel.add(subtitle);
        topPanel.add(Box.createVerticalStrut(20));

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        centerPanel.setBackground(new Color(75, 155, 185));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("Segoe UI", Font.BOLD, 14);

        JButton typingSpeedButton = new JButton("Typing Speed");
        JButton reactionButton = new JButton("Reaction Time");
        JButton numberButton = new JButton("Number Memory");
        JButton visualButton = new JButton("Visual Memory");

        JButton[] buttons = {typingSpeedButton, reactionButton, numberButton, visualButton};

        for (JButton b : buttons) {
            b.setFont(font);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setBorderPainted(false);
        }

        typingSpeedButton.setBackground(new Color(230, 90, 90));
        reactionButton.setBackground(new Color(110, 80, 170));
        numberButton.setBackground(new Color(40, 170, 100));
        visualButton.setBackground(new Color(210, 140, 20));

        typingSpeedButton.addActionListener(e -> new TypingSpeedGame());
        reactionButton.addActionListener(e -> new ReactionTimeGame());
        numberButton.addActionListener(e -> new NumberMemoryGame());
        visualButton.addActionListener(e -> new VisualMemoryGame());

        centerPanel.add(typingSpeedButton);
        centerPanel.add(reactionButton);
        centerPanel.add(numberButton);
        centerPanel.add(visualButton);

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}