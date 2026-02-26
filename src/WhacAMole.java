import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class WhacAMole{
     int boardWidth = 600;
     int boardHeight = 650;


     JFrame frame = new JFrame("Mario: Whac A Mole");
     JLabel textLabel = new JLabel();
     JPanel textPanel = new JPanel();
     JPanel boardPanel = new JPanel();


     JButton[] board = new JButton[9];
     ImageIcon moleIcon;
     ImageIcon bombIcon ;

     JButton currMoleTile;
     JButton currBombTile;

     Random random = new Random();

     Timer setMoleTimer;
     Timer setBombTimer;
     int score = 0;


    WhacAMole(){
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score : 0");
        textLabel.setOpaque(true);
        textLabel.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 60));

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout (3, 3));
        //boardPanel.setBackground(Color.black);
        frame.add(boardPanel);



        Image moleImage = new ImageIcon(getClass().getResource("./mole.png")).getImage();
        moleIcon = new ImageIcon(moleImage.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        Image bombImage = new ImageIcon(getClass().getResource("./bomb.png")).getImage();
        bombIcon = new ImageIcon(bombImage.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));


        score = 0;
        for(int i = 0; i < 9; i++){
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);

           // tile.setIcon(moleIcon);
            tile.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    JButton tile = (JButton) e.getSource();
                    if(tile == currMoleTile){
                        score += 10;
                        textLabel.setText("Score: " + Integer.toString(score));
                    }
                    else if(tile == currBombTile){
                        textLabel.setText("Game ☠️ Over: " + Integer.toString(score));
                        setMoleTimer.stop();
                        setBombTimer.stop();
                        for(int i = 0; i < 9; i++){
                            board[i].setEnabled(false);
                        }
                    }
                }
            });
        }

        setMoleTimer = new Timer(1000, new ActionListener(){
            public void actionPerformed(ActionEvent e){

               //remove mole from current tile
                if (currMoleTile != null){
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }

                //randomly select another tile
                int num = random.nextInt(9);
                JButton tile = board[num];

                //if tile is occupied by bomb, skip tile for this tern
                if (currBombTile == tile) return;

                //set tile to mole
                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        });

        setBombTimer = new Timer(1500, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (currBombTile != null){
                    currBombTile.setIcon(null);
                    currBombTile = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];

                if(currMoleTile == tile) return;

                currBombTile = tile;
                currBombTile.setIcon(bombIcon);
            }
        });

        setMoleTimer.start();
        setBombTimer.start();
        frame.setVisible(true);

    }
}








