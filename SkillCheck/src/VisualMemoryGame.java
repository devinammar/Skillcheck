import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class VisualMemoryGame extends JFrame {
    private JPanel gridPanel;
    private JLabel levelLabel, instructionLabel;
    private int gridSize = 2;
    private int correctClicks = 0;
    private int totalClicksNeeded;
    private int currentLevel = 1;
    private ArrayList<Point> highlightedCells;
    private boolean isGameActive = false;
    private boolean isClickable = false;

    public VisualMemoryGame() {
        setTitle("Visual Memory Game");
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        levelLabel = new JLabel("Level: 1", SwingConstants.CENTER);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(levelLabel, BorderLayout.NORTH);

        gridPanel = new JPanel();
        add(gridPanel, BorderLayout.CENTER);

        instructionLabel = new JLabel("Klik 'Mulai' untuk memulai permainan", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(instructionLabel, BorderLayout.SOUTH);

        JButton startButton = new JButton("Mulai");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        add(startButton, BorderLayout.WEST);
        startButton.addActionListener(e -> startGame());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame() {
        gridSize = 2;
        currentLevel = 1;
        correctClicks = 0;
        isGameActive = true;
        instructionLabel.setText("Perhatikan grid, lalu klik kotak yang di-highlight.");
        levelLabel.setText("Level: " + currentLevel);
        setupGrid();
        highlightCells();
    }

    private void setupGrid() {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(gridSize, gridSize));
        totalClicksNeeded = gridSize;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                JButton cellButton = new JButton();
                cellButton.setBackground(Color.LIGHT_GRAY);
                cellButton.setFocusPainted(false);
                Point cellPosition = new Point(row, col);
                cellButton.putClientProperty("position", cellPosition);
                cellButton.addActionListener(e -> handleCellClick(cellPosition, cellButton));
                gridPanel.add(cellButton);
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void highlightCells() {
        isClickable = false;
        highlightedCells = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < gridSize; i++) {
            int row = random.nextInt(gridSize);
            int col = random.nextInt(gridSize);
            Point cellPosition = new Point(row, col);

            while (highlightedCells.contains(cellPosition)) {
                row = random.nextInt(gridSize);
                col = random.nextInt(gridSize);
                cellPosition = new Point(row, col);
            }
            highlightedCells.add(cellPosition);
        }

        for (Component comp : gridPanel.getComponents()) {
            JButton cellButton = (JButton) comp;
            Point cellPosition = (Point) cellButton.getClientProperty("position");
            if (highlightedCells.contains(cellPosition)) {
                cellButton.setBackground(Color.BLUE);
            }
        }

        Timer timer = new Timer(2000, e -> {
            for (Component comp : gridPanel.getComponents()) {
                JButton cellButton = (JButton) comp;
                cellButton.setBackground(Color.LIGHT_GRAY);
            }
            isClickable = true;
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void handleCellClick(Point cellPosition, JButton cellButton) {
        if (!isGameActive || !isClickable) return;
        if (highlightedCells.contains(cellPosition)) {
            cellButton.setBackground(Color.GREEN);
            correctClicks++;
            highlightedCells.remove(cellPosition);

            if (correctClicks == totalClicksNeeded) {
                nextLevel();
            }
        } else {
            endGame();
        }
    }

    private void nextLevel() {
        if (isGameActive) {
            currentLevel++;
            correctClicks = 0;
            if (currentLevel % 2 == 0) {
                gridSize++;
            }
            instructionLabel.setText("Selamat! Naik ke level " + currentLevel + ".");
            levelLabel.setText("Level: " + currentLevel);
            setupGrid();
            highlightCells();
        }
    }

    private void endGame() {
        isGameActive = false;
        isClickable = false;
        instructionLabel.setText("Game Over! Level tertinggi: " + currentLevel + ".");
        JOptionPane.showMessageDialog(this,
                "Game Over!\nLevel tertinggi: " + currentLevel +
                        "\nCoba lagi untuk skor lebih tinggi.");
    }
}
