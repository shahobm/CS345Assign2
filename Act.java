import java.util.*;

public class Act{
    int sceneRank = 0;
    int sceneNumber = 0;
    int playerRank = 0;
    int playerNumber = 0;
    int fame = 0;
    int money = 0;
    boolean actOnorOff = false;

    public void createAct(int sceneRank, int sceneNumber, int playerRank, int playerNumber, int fame, int money, boolean actOnorOff) {
        this.sceneRank = sceneRank;
        this.sceneNumber = sceneNumber;
        this.playerRank = playerRank;
        this.playerNumber = playerNumber;
        this.fame = fame;
        this.money = money;
        this.actOnorOff = actOnorOff;
    }

    public void playerAct(Player currentPlayer) {
        /*
            player is not in a role or casting office: case 1
                -> player is in  a scene room, then ask if they want to take a role
            or currently in a role: case 2
            or in the casting office: case 3
        */
        String playerRole = currentPlayer.getRole();
        if (playerRole.equals("")) {
          System.out.println("Player not on a role");
        } else {
          ParseFile pf = new ParseFile();
          ArrayList<Card> cards = pf.cards;
          HashMap<String,Room> rooms = pf.rooms;
          Card card = findCard(cards, playerRole);
          int movieBudget = card.getBudget();
          if (!(card.getCardName().equals("fail"))) {
            if (currentPlayer.getRoleValue().equals("on")){
              Dice dice = new Dice();
              int diceVal = dice.actRollDice();
              System.out.printf("You rolled %d", diceVal);
              actOnCard(currentPlayer, rooms, cards, currentPlayer.getPlayerPosition(), diceVal, movieBudget);
            } else {
              Dice dice = new Dice();
              int diceVal = dice.actRollDice();
              System.out.printf("You rolled %d", diceVal);
              actOffCard(currentPlayer, rooms, cards, currentPlayer.getPlayerPosition(), diceVal, movieBudget);
            }
          }
        }
        /* Current player is in casting office */
        if(currentPlayer.getPlayerPosition().equals("CastingOffice")) {
            //ask first if player wants to upgrade
            CastingOffice castingOffice = new CastingOffice();
            boolean askUpgrade = castingOffice.askIfUpgrade();
            if(askUpgrade) {
                String fameOrDollar = castingOffice.howToUpgrade();
                if(fameOrDollar.equals("f")) {
                    castingOffice.upgradeRankWithFame();
                } else if(fameOrDollar.equals("d")) {
                    castingOffice.upgradeRankWithMoney();
                }
                //user wants to move => find a adjacent room
            }


        /* Current player is in scene room */
        }
    }

    public Card findCard(ArrayList<Card> cards, String cardName){
      for (int i = 0; i < cards.size(); i++) {
        Card currCard = cards.get(i);
        if (currCard.getCardName().equals(cardName)){
          return currCard;
        }
      }
      Card cardFailure = new Card();
      cardFailure.setCardName("fail");
      return cardFailure;
    }

    public void takeUpRole(Player currentPlayer, String[] destination){
      String currentPosition = currentPlayer.getPlayerPosition();
      ParseFile pf = new ParseFile();
      ArrayList<Card> cards = pf.cards;
      HashMap<String,Room> rooms = pf.rooms;
      if (currentPlayer.getRole() == "") {
        // one word
        if (destination.length == 2) {
          // check room
          Room currRoom = rooms.get(currentPosition);
          Card card = currRoom.getCard();
          ArrayList<partExtra> parts = currRoom.getParts();
          ArrayList<part> cardParts = card.getCardParts();
          if (checkRole(currentPlayer, parts, cardParts, destination[1])){
            return;
          }
          // multiple words
        } else {
          String newDestination = "";
          for (int l = 1; l < destination.length; l++) {
            newDestination = newDestination + " " + destination[l];
          }
          newDestination = newDestination.trim();
          Room currRoom = rooms.get(currentPosition);
          Card card = currRoom.getCard();
          ArrayList<partExtra> parts = currRoom.getParts();
          ArrayList<part> cardParts = card.getCardParts();
          if (checkRole(currentPlayer, parts, cardParts, newDestination)){
            return;
          }
        }
      } else {
        System.out.println("Player already in a role.\n");
      }
    }

    public boolean checkRole(Player currentPlayer, ArrayList<partExtra> parts, ArrayList<part> cardParts, String partName){
      boolean check = false;
      for (int i = 0; i < parts.size(); i++) {
        partExtra currPart = parts.get(i);
        String name = currPart.getPartName();
        boolean taken = currPart.getTaken();
        if (taken != true) {
          if (name.equals(partName)) {
            currentPlayer.setRole(partName);
            currentPlayer.setRoleLevel(currPart.getLevel());
            currentPlayer.setRoleValue("off");
            currPart.setTaken(true);
            System.out.printf("\nPlayer %s is now acting in %s. \n", currentPlayer.getPlayer(), partName);
            return true;
          }
        } else {
          System.out.println("This role is already taken. \n");
          check = true;
        }
      }
      if (check == false) {
        for (int j = 0; j < parts.size(); j++) {
          partExtra currPart = parts.get(j);
          String currName = currPart.getPartName();
          boolean taken = currPart.getTaken();
          if (taken != true) {
            if (currName.equals(partName)) {
              currentPlayer.setRole(partName);
              currentPlayer.setRoleLevel(currPart.getLevel());
              currentPlayer.setRoleValue("on");
              currPart.setTaken(true);
              System.out.printf("\nPlayer %s is now acting in %s. \n", currentPlayer.getPlayer(), partName);
              return true;
            }
          } else {
            System.out.println("This role is already taken. \n");
          }
        }
      }
      if (check == false) {
        System.out.println("The role you want to act in is not on the card or board.\n");
      }
      System.out.println("The roles you can take is: ");
      printPartList(cardParts);
      printExtraPartList(parts);

      return false;
    }

    public void printPartList(ArrayList<part> cardParts){
      System.out.println("Parts you can choose on the card: ");
      for (int i = 0; i < cardParts.size(); i++) {
        part part = cardParts.get(i);
        System.out.println(part.getName());
      }
    }

    public void printExtraPartList(ArrayList<partExtra> cardParts){
      System.out.println("Parts you can choose off the card: ");
      for (int i = 0; i < cardParts.size(); i++) {
        partExtra part = cardParts.get(i);
        System.out.println(part.getPartName());
      }
    }

    public void actOnCard(Player currentPlayer, HashMap<String,Room> rooms, ArrayList<Card> cards, String room, int dieValue, int budgetMovie) {
        if(actOnorOff){
            if(dieValue >= budgetMovie){
                System.out.println("You succeeded in acting off card! You will get 2 fame");
                int currFame = currentPlayer.getFame();
                currFame += 2;
                currentPlayer.setFame(currFame);
                Room currRoom = rooms.get(room);
                int take = currRoom.getNumofTakes();
                take--;
                currRoom.setNumofTakes(take);
                if (take == 0){
                  endScene(currRoom, cards, budgetMovie);
                }
            } else {
                System.out.println("You failed in acting on card! You earn nothing");
            }
        }
    }

    public void actOffCard(Player currentPlayer, HashMap<String,Room> rooms, ArrayList<Card> cards, String room, int dieValue, int budgetMovie){
        if(!actOnorOff){
            if(dieValue >= budgetMovie){
                System.out.println("You succeeded in acting off card! You will get 1 money and 1 fame");
                Room currRoom = rooms.get(room);
                int take = currRoom.getNumofTakes();
                take--;
                currRoom.setNumofTakes(take);

                int currFame = currentPlayer.getFame();
                currFame += 2;
                currentPlayer.setFame(currFame);

                int currMoney = currentPlayer.getMoney();
                currMoney++;
                currentPlayer.setMoney(currMoney);
            } else {
                System.out.println("You failed in acting off card! You still earn 1 money");
                int currMoney = currentPlayer.getMoney();
                currMoney++;
                currentPlayer.setMoney(currMoney);
            }
        }
    }


    public void endScene(Room room, ArrayList<Card> cards, int budgetMovie){
      ArrayList<Player> onTheCardPlayers = new ArrayList<Player>();
      Deadwood d = new Deadwood();
      int scenesLeft = d.scenesLeft;
      scenesLeft--;
      d.setScenesLeft(scenesLeft);
      Card card = room.getCard();
      String cardName = card.getCardName();
      ArrayList<part> parts = card.getCardParts();
      GameSetup GS = new GameSetup();
      ArrayList<Player> players = GS.players;
      for (int i=0; i< players.size() ;i++) {
        Player currPlayer = players.get(i);
        if (parts.contains(currPlayer.getRole())){
          onTheCardPlayers.add(currPlayer);
        }
      }
      if (onTheCardPlayers.size() > 0){
        if (onTheCardPlayers.size() == 1) {
          Dice die = new Dice();
          ArrayList<Integer> sortedDice = die.rollDice(budgetMovie);
        } else {
          onTheCardPlayers = sortPlayers(onTheCardPlayers, parts);
          Dice die = new Dice();
          ArrayList<Integer> sortedDice = die.rollDice(budgetMovie);
          distributeEarnings(onTheCardPlayers, sortedDice);
        }
      } else {
        return;
      }
    }

    public static void distributeEarnings(ArrayList<Player> onTheCardPlayers, ArrayList<Integer> diceValue) {

      boolean playersPaid = false;
      while(playersPaid = false){
        int dicePosition = 0;
        for(int i = 0; i < onTheCardPlayers.size(); i++){
          if(diceValue.get(dicePosition) != diceValue.size()){
            int playerMoney = onTheCardPlayers.get(i).getMoney() + diceValue.get(dicePosition);
            onTheCardPlayers.get(i).setMoney(playerMoney);
            dicePosition ++;
          }else{
            dicePosition = 0;
            i--;
          }
        }
        playersPaid = true;
      }
    }

    public ArrayList<Player> sortPlayers(ArrayList<Player> players, ArrayList<part> parts){
      for (int i = 0; i < players.size(); i++) {
        if (i + 1 < players.size()) {
          Player currPlayer = players.get(i);
          Player nextPlayer = players.get(i+1);
          if (currPlayer.getRoleLevel() < nextPlayer.getRoleLevel()) {
            players.set(i, nextPlayer);
            players.set(i+1, currPlayer);
          }
        }
      }
      return players;
    }
}