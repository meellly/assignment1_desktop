import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Assignment 1 - Tic Tac Toe (desktop implementation)
 * Team Members: Jason Katto, Melissa Schilbe, Hinal Wadia
**/


public class Main extends Applet {

    Panel topPanel = new Panel(), bottomPanel = new Panel(), boardPanel = new Panel();
    Label infoLabel = new Label();
    Label turnLabel = new Label();
    Button startButton = new Button();
    TTTButton[] tttButton = new TTTButton[9];
    Player[] players = new Player[2];
    Player currentPlayer = null;
    DataCell[] cells = new DataCell[9];
    List<Integer> player0List = new ArrayList<Integer>();
    List<Integer> player1List = new ArrayList<Integer>();
    boolean can_reset = false;
    int filled_cells = 0;

    void swapPlayer() {
        currentPlayer = currentPlayer == players[0] ? players[1] : players[0];
    }

    void claimCell(Player player, int i) {
        if (player == players[0]) {
            player0List.add(i);
        } else {
            player1List.add(i);
        }
    }

    boolean checkForWin(Player player) {
        if (player == players[0]) {
            if (player0List.contains(0) && player0List.contains(1) && player0List.contains(2)) {
                return true;
            } else if (player0List.contains(3) && player0List.contains(4) && player0List.contains(5)) {
                return true;

            } else if (player0List.contains(6) && player0List.contains(7) && player0List.contains(8)) {
                return true;

            } else if (player0List.contains(0) && player0List.contains(3) && player0List.contains(6)) {
                return true;

            } else if (player0List.contains(1) && player0List.contains(4) && player0List.contains(7)) {
                return true;

            } else if (player0List.contains(2) && player0List.contains(5) && player0List.contains(8)) {
                return true;

            } else if (player0List.contains(0) && player0List.contains(4) && player0List.contains(8)) {
                return true;

            } else if (player0List.contains(2) && player0List.contains(4) && player0List.contains(6)) {
                return true;

            } else {
                return false;
            }
        } else {

            if (player1List.contains(0) && player1List.contains(1) && player1List.contains(2)) {
                return true;
            } else if (player1List.contains(3) && player1List.contains(4) && player1List.contains(5)) {
                return true;

            } else if (player1List.contains(6) && player1List.contains(7) && player1List.contains(8)) {
                return true;

            } else if (player1List.contains(0) && player1List.contains(3) && player1List.contains(6)) {
                return true;

            } else if (player1List.contains(1) && player1List.contains(4) && player1List.contains(7)) {
                return true;

            } else if (player1List.contains(2) && player1List.contains(5) && player1List.contains(8)) {
                return true;

            } else if (player1List.contains(0) && player1List.contains(4) && player1List.contains(8)) {
                return true;

            } else if (player1List.contains(2) && player1List.contains(4) && player1List.contains(6)) {
                return true;

            } else {
                return false;
            }
        }
    }

    void enableButtons(boolean enable) {
        for (int i = 0; i < 9; i++) {
            tttButton[i].setEnabled(enable);
        }
    }

    void resetGame() {
        can_reset = false;
        filled_cells = 0;
        //clear game cells
        for (int i = 0; i < 9; i++) {
            cells[i] = new DataCell("");
            cells[i].addObserver(tttButton[i]);
            tttButton[i].setLabel("");
            tttButton[i].setEnabled(true);
        }
        //clear player's claimed cells
        player0List.clear();
        player1List.clear();
        //reset current player
        currentPlayer = players[0] = new Player("Player 1", "X");
        players[1] = new Player("Player 2", "O");
    }

    @Override
    public void init() {
        currentPlayer = players[0] = new Player("Player 1", "X");
        players[1] = new Player("Player 2", "O");

        super.init();
        resize(250, 430);
        add(topPanel);
        add(boardPanel);
        add(bottomPanel);

        // Top - game info
        topPanel.setPreferredSize(new Dimension(250, 90));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(infoLabel);
        infoLabel.setText("When ready select 'Start Game'    ");
        topPanel.add(turnLabel);
        turnLabel.setText("                                              ");

        // Center - game board
        boardPanel.setPreferredSize(new Dimension(250, 250));
        boardPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        for (int i = 0; i < 9; i++) {
            tttButton[i] = new TTTButton(i);
            cells[i] = new DataCell("");
            cells[i].addObserver(tttButton[i]);
            tttButton[i].setPreferredSize(new Dimension(70, 70));
            boardPanel.add(tttButton[i]);
            tttButton[i].setEnabled(false);
            tttButton[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    filled_cells = filled_cells + 1;
                    startButton.setLabel("Reset");
                    can_reset = true;
                    TTTButton source = (TTTButton) e.getSource(); //currentButton
                    int index = source.getIndex();
                    cells[index].setValue(currentPlayer.getSymbol());
                    claimCell(currentPlayer, index);
                    if (checkForWin(currentPlayer)) {
                        infoLabel.setText(currentPlayer.getPlayerName() + " wins!");
                        turnLabel.setText("");
                        enableButtons(false);

                    } else {
                        if (filled_cells > 8) {
                            infoLabel.setText("Cat's Game!");
                            turnLabel.setText("");
                            filled_cells = 0;

                        } else {
                            swapPlayer();
                            infoLabel.setText("Good luck!");
                            turnLabel.setText("It's " + currentPlayer.getPlayerName() + "'s turn - " + currentPlayer.getSymbol());
                            source.setEnabled(false);
                        }
                    }
                    // fix for button "auto-focus"
                    tttButton[index].requestFocusInWindow();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }

        // Bottom - game control
        bottomPanel.setPreferredSize(new Dimension(250, 90));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        startButton.setLabel("Start Game");
        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (can_reset) {
                    resetGame();
                    infoLabel.setText("Game has reset");
                    turnLabel.setText(currentPlayer.getPlayerName() + "'s turn - " + currentPlayer.getSymbol());
                } else {
                    enableButtons(true);
                    infoLabel.setText("Good luck!");
                    turnLabel.setText("It's " + currentPlayer.getPlayerName() + "'s turn - " + currentPlayer.getSymbol());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        bottomPanel.add(startButton);
    }
}
