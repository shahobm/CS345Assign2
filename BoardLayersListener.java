/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/


import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.*;
import java.awt.Rectangle;


public class BoardLayersListener extends JFrame {

  // Private Attributes

  // JLabels
  static JLabel boardlabel;
  static JLabel cardlabel;
  static JLabel cardlabelJail;
  static JLabel playerlabel;
  static JLabel mLabel;
  static JLabel[] playerInfo = {new JLabel("Player 1"), new JLabel("Player 2"), new JLabel("Player 3"), new JLabel("Player 4"), new JLabel("Player 5"), new JLabel("Player 6"), new JLabel("Player 7"), new JLabel("Player 8")};


  //JButtons
  static JButton bAct;
  static JButton bRehearse;
  static JButton bMove;
  static JButton bTakeRole;
  static JButton bRankUp;
  static JButton bEndTurn;

  // JLayered Pane
  static JLayeredPane bPane;
  static JPanel panelStatus;
  static JPanel info;

  static Dimension boardSize = new Dimension(1170, 882);
  static Dimension paneSize = new Dimension(1300, 550);

  // Constructor

  public BoardLayersListener() {

       // Set the title of the JFrame
       super("Deadwood");
       // Set the exit option for the JFrame
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       //asks for number of players
       String[] options = new String[] {"2", "3", "4", "5", "6", "7", "8"};
       int option =  JOptionPane.showOptionDialog(null, "Choose a number of players", "Message",
       JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
       null, options, options[0]);

       if (option != JOptionPane.CLOSED_OPTION) {
           System.out.println(options[option]);
       } else {
           System.out.println("No option selected");
       }

       // Create the JLayeredPane to hold the display, cards, dice and buttons
       bPane = getLayeredPane();

       // Create the deadwood board
        boardlabel = new JLabel();
        ImageIcon icon =  new ImageIcon("images/board.jpg");
        boardlabel.setIcon(icon);
        boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());

        info = new JPanel();

       // Add the board to the lowest layer
       bPane.add(boardlabel, new Integer(0));

       // Set the size of the GUI
       setSize(icon.getIconWidth()+200,icon.getIconHeight());

     // Create the board for status of player
       panelStatus = new JPanel();
       panelStatus.setLayout(null);
       panelStatus.setBounds(icon.getIconWidth()+10,390,450,500);
       panelStatus.setBackground(Color.WHITE);

      addButtons(icon);
      addCards();
      addPlayers();
      addTakes();
  }

  public void addTakes(){
      ParseFile parse = new ParseFile();
      HashMap<String,Room> rooms = parse.rooms;
      for (String key: rooms.keySet()){
        Room currRoom = rooms.get(key);
        if ((key != "trailer") && (key != "Casting Office")){
            ArrayList<take> takes = currRoom.getTakes();
            Rectangle roomArea = currRoom.getCardArea();
            System.out.println(takes.size());
            for (int i = 0; i < takes.size(); i++) {
                take t = takes.get(i);
                Rectangle takeArea = t.getArea();
                // Add a scene card to this room
                cardlabel = new JLabel();
                //System.out.println(card.getCardName());
                ImageIcon cardImage =  new ImageIcon("images/shot.png");
                cardlabel.setIcon(cardImage);
                // x+4 and y-4
                cardlabel.setBounds((int)takeArea.getX(),(int)takeArea.getY(), cardImage.getIconWidth(),cardImage.getIconHeight());
                cardlabel.setOpaque(true);
                // Add the card to the lower layer
                bPane.add(cardlabel, new Integer(2));
            }
        }
      }
  }


  public void addCards(){

      ParseFile parse = new ParseFile();
      HashMap<String,Room> rooms = parse.rooms;
      for (String key: rooms.keySet()){
        Room currRoom = rooms.get(key);
        if ((key != "trailer") && (key != "Casting Office")){
            Card currCard = currRoom.getCard();
            // System.out.printf("RoomName: %s \nCardName: %s\n",currRoom.getName(), currCard.getCardName());
            Rectangle roomArea = currRoom.getCardArea();
            placeCards(currCard, currRoom.getCardArea());
            placeCardBacks(currCard, currRoom.getCardArea());
        }
      }
  }

  public void placeCards(Card card, Rectangle cardArea){
      // Add a scene card to this room
      cardlabel = new JLabel();
      //System.out.println(card.getCardName());
      ImageIcon cardImage =  new ImageIcon("images/" + card.getCardImg());
      cardlabel.setIcon(cardImage);
      // x+4 and y-4
      cardlabel.setBounds((int)cardArea.getX(),(int)cardArea.getY(),cardImage.getIconWidth(),cardImage.getIconHeight());
      cardlabel.setOpaque(true);
      card.setCardLabel(cardlabel);
      // Add the card to the lower layer
      bPane.add(cardlabel, new Integer(2));

  }

  public void placeCardBacks(Card card, Rectangle cardArea){
      // Add a scene card to this room
      cardlabel = new JLabel();
      ImageIcon cardBack =  new ImageIcon("images/backOfCard.png");
      cardlabel.setIcon(cardBack);
      // x+4 and y-4
      cardlabel.setBounds((int)cardArea.getX()+4,(int)cardArea.getY()-4,cardBack.getIconWidth(),cardBack.getIconHeight());
      cardlabel.setOpaque(true);
      card.setBackCardLabel(cardlabel);
      // Add the card to the lower layer
      bPane.add(cardlabel, new Integer(3));

  }

  public void addButtons(ImageIcon icon) {
      // Create the Menu for action buttons
      mLabel = new JLabel("MENU");
      mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
      bPane.add(mLabel,new Integer(2));

      // Create Action buttons
      bAct = new JButton("ACT");
      bAct.setBackground(Color.white);
      bAct.setBounds(icon.getIconWidth()+10, 40,120, 40);
      bAct.addMouseListener(new boardMouseListener());

      bRehearse = new JButton("REHEARSE");
      bRehearse.setBackground(Color.white);
      bRehearse.setBounds(icon.getIconWidth()+10,100,120, 40);
      bRehearse.addMouseListener(new boardMouseListener());

      bMove = new JButton("MOVE");
      bMove.setBackground(Color.white);
      bMove.setBounds(icon.getIconWidth()+10,160,120, 40);
      bMove.addMouseListener(new boardMouseListener());

      bTakeRole = new JButton("TAKE ROLE");
      bTakeRole.setBackground(Color.white);
      bTakeRole.setBounds(icon.getIconWidth()+10,220,120, 40);
      bTakeRole.addMouseListener(new boardMouseListener());

      bRankUp = new JButton("RANK UP");
      bRankUp.setBackground(Color.white);
      bRankUp.setBounds(icon.getIconWidth()+10,280,120, 40);
      bRankUp.addMouseListener(new boardMouseListener());

      bEndTurn = new JButton("END TURN");
      bEndTurn.setBackground(Color.white);
      bEndTurn.setBounds(icon.getIconWidth()+10,340,120, 40);
      bEndTurn.addMouseListener(new boardMouseListener());


      // Place the action buttons in the top layer
      bPane.add(bAct, new Integer(2));
      bPane.add(bRehearse, new Integer(2));
      bPane.add(bMove, new Integer(2));
      bPane.add(bTakeRole, new Integer(2));
      bPane.add(bRankUp, new Integer(2));
      bPane.add(bEndTurn, new Integer(2));
  }

  public void addPlayers() {
      ParseFile pf = new ParseFile();
      ArrayList<Player> players = pf.players;
      int widthOffset = 0;
      int heightOffset = 0;
      for (int i = 0; i < players.size(); i++) {
        Player currPlayer = players.get(i);
        String img = getPlayerImage(currPlayer.getPlayer(), currPlayer.getRank());
        playerlabel = new JLabel();
        ImageIcon pIcon = new ImageIcon(img);
        playerlabel.setIcon(pIcon);
        if (i == 5) {
            heightOffset += pIcon.getIconHeight();
            widthOffset = 0;
        }
        playerlabel.setBounds(991+widthOffset,248+heightOffset,pIcon.getIconWidth(),pIcon.getIconHeight());
        currPlayer.setPlayerLabel(playerlabel);
        bPane.add(playerlabel,new Integer(3));
        widthOffset += pIcon.getIconWidth();
      }
  }

  public static void removePlayer(Player player) {
      JLabel playerlabel = player.getPlayerLabel();
      bPane.remove(playerlabel);
      bPane.revalidate();
      bPane.repaint();
  }

  public static void onCardMove(Player player, Rectangle cardArea, Rectangle roomArea) {
      JLabel playerlabel = player.getPlayerLabel();
      Icon pIcon = playerlabel.getIcon();
      playerlabel.setBounds((int)cardArea.getX()+(int)roomArea.getX(),(int)cardArea.getY()+(int)roomArea.getY(),
      pIcon.getIconWidth(),pIcon.getIconHeight());
      bPane.add(playerlabel,new Integer(4));
  }

  public static void movePlayer(Player player, Rectangle cardArea, Room room) {
      int players = room.getPlayersOnCard();
      JLabel playerlabel = player.getPlayerLabel();
      Icon pIcon = playerlabel.getIcon();
      playerlabel.setBounds((int)cardArea.getX()+(players * pIcon.getIconWidth()),(int)cardArea.getY(),pIcon.getIconWidth(),pIcon.getIconHeight());
      bPane.add(playerlabel,new Integer(4));
  }

  public static String getPlayerImage(String playerName, int playerRank){
      char color = Character.toLowerCase(playerName.charAt(0));
      String img = "images/" + color + playerRank + ".png";
      return img;
  }

  public static void flipCard(Room room) {
      Card card = room.getCard();
      JLabel cardlabel = card.getBackCardLabel();
      bPane.remove(cardlabel);
      bPane.revalidate();
      bPane.repaint();
  }


  public void buildLowerPanel(/*get num player */){
   int numPlayers = 6;
   info = new JPanel();

   info.setBounds(boardSize.width + 10, 400, 120, 40);
   // info. add days remaining
   for (int i = 0; i <= numPlayers; i++){
       info.add(playerInfo[i]);
   }
   bPane.add(info, JLayeredPane.DEFAULT_LAYER);

}

  // This class implements Mouse Events

    class boardMouseListener implements MouseListener {

        // Code for the different button clicks
        public void mouseClicked(MouseEvent e) {

            if (e.getSource()== bAct){
                System.out.println("Acting is Selected\n");
            }
            else if (e.getSource()== bRehearse){
                System.out.println("Rehearse is Selected\n");
            }
            else if (e.getSource()== bMove){
                System.out.println("Move is Selected\n");
            }
            else if (e.getSource()== bTakeRole){
                System.out.println("Taking a Role is Selected\n");
            }
            else if (e.getSource()== bRankUp){
                System.out.println("Rank Up is Selected\n");
            }
            else if (e.getSource()== bEndTurn){
                System.out.println("End\n");
                //endTurnButton();
            }
        }
        public void mousePressed(MouseEvent e) {
        }
        public void mouseReleased(MouseEvent e) {
        }
        public void mouseEntered(MouseEvent e) {
        }
        public void mouseExited(MouseEvent e) {
        }

    }

    public void endTurnButton(){
        System.out.println("Ending Turn\n");

        Deadwood.startTurn(Deadwood.players.get(Deadwood.a), "end");

    }
    public void actButton(){
        Deadwood.startTurn(Deadwood.players.get(Deadwood.a), "act");
    }

    public void rehearseButton(){
        Deadwood.startTurn(Deadwood.players.get(Deadwood.a), "rehearse");
    }

    // player moves by selecting possible adjacent rooms
    public void moveButton(){

        // show available movement positions
        String selectedMove = "";
        /*String[] options = new String[] {adjacentroom1, adjacentroom2};
        int option =  JOptionPane.showOptionDialog(null, "Select a method for rank increase", "Message",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
                */

        Deadwood.startTurn(Deadwood.players.get(Deadwood.a), "move" + selectedMove);
    }

    // user requests to increase rank by pressing rank button
    public void rankButton() {
        ParseFile parse = new ParseFile();
        HashMap<String, Room> rooms = parse.rooms;
        for (String key : rooms.keySet()) {
            Room currRoom = rooms.get(key);
            if ((!Objects.equals(key, "Casting Office"))) {
                System.out.println("must be in casting office to increase rank");
                // current room is currRoom
            } else {
                //playerUpgrade(currentPlayer,inputArray, level);
                // goes castingOffice.java, select increase rank with fame or money
            }
        }
    }

    // ask how the user wants to upgrade, return response to casting office
    public String askRankOrMoney(){
        String response = "";
        String[] options = new String[] {"Rank", "Money"};
        int option =  JOptionPane.showOptionDialog(null, "Select a method for rank increase", "Message",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        return response;
    }

    // allow user to take a role by typing their selected role in pop up
    public void takeRoleButton(){
        String role = "";
        Deadwood.startTurn(Deadwood.players.get(Deadwood.a), "move" + role);
    }

    // ask if the user wants to act on or off the card, return response to main program
    public String askActOnOrOff(){
        String response = "";
        String[] options = new String[] {"On", "Off"};
        int option =  JOptionPane.showOptionDialog(null, "Would you like to work on or off the card?", "Message",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        return response;
    }
    
    // display information to be shown to the user as a pop up menu
    public void displayGenericMessage(String message){
        
    }
}
