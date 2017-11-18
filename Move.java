import java.util.*;

public class Move {

  public static boolean move(Player currentPlayer, String[] destination) {
    // check to see if player already moved
    if (currentPlayer.getMoved() == false) {
      String currentPosition = currentPlayer.getPlayerPosition();
      ParseFile parse = new ParseFile();
      HashMap<String,Room> rooms = parse.rooms;
      Room playerRoom = rooms.get(currentPosition);
      ArrayList<String> neighbors = playerRoom.getNeighbors();
      if (destination.length == 2) {
        if (neighbors.contains(destination[1])){
          currentPlayer.setPlayerPosition(destination[1]);
          Room newRoom = rooms.get(destination[1]);
          Card card = newRoom.getCard();
          card.setFlipped(true);
          System.out.printf("\nPlayer %s is now in %s. \n", currentPlayer.getPlayer(), destination[1]);
          currentPlayer.setMoved(true);
          return true;
        } else {
          System.out.println("The destination you wish to move is not a neighbor");
        }
      } else {
        String newDestination = "";
        for (int l = 1; l < destination.length; l++) {
          newDestination = newDestination + " " + destination[l];
        }
        newDestination = newDestination.trim();
        if (neighbors.contains(newDestination)){
          currentPlayer.setPlayerPosition(newDestination);
          Room newRoom = rooms.get(newDestination);
          Card card = newRoom.getCard();
          card.setFlipped(true);
          System.out.printf("\nPlayer %s is now in %s.\n", currentPlayer.getPlayer(), newDestination);
          return true;
        } else {
          System.out.println("The destination you wish to move is not a neighbor");
        }
      }
    } else {
      System.out.printf("player %s has already moved.\n", currentPlayer.getPlayer());
    }
    return false;
  }

   public static void enterScene() {

   }

   public static void enterCastingOffice() {

   }

   public static boolean askIfMoving() {
        boolean getOutOfLoop = false;
        String answer = "";
        System.out.println("If you want to move, type in y. Otherwise, type in n\n");
        while(!getOutOfLoop){
            Scanner input = new Scanner(System.in);
            answer = input.next();
            if(!answer.toLowerCase().equals("y") || !answer.toLowerCase().equals("n")){
                System.out.println("Type in y or n. I ain't got no time to mess with you");
            } else {
                getOutOfLoop = true;
            }
        }

        if(answer.toLowerCase().equals("y")){
            return true;
        } else if(answer.toLowerCase().equals("n")){
            return false;
        }
        return false;

   }

    public static String adjacentSets(HashMap<String,ArrayList> setNeighbors, String currentSet) {
        boolean getOutOfLoop = false;
        String answer = "";
        String destination = "";
        Set<String> keys = setNeighbors.keySet();
        while(!getOutOfLoop) {
            System.out.println("Which set would you move to? Please type in a single digit such as 1\n");
            for(String key: keys) {
                if(currentSet.equals(key)) {
                    //return values
                    for(int i = 0; i < setNeighbors.get(key).size(); i++) {
                        System.out.println(i+1 + ") " + setNeighbors.get(key).get(i));
                    }
                    Scanner input = new Scanner(System.in);
                    answer = input.next();
                    if(!answer.equals("1") || !answer.equals("2") || !answer.equals("3") || !answer.equals("4")){
                        System.out.println("You may not put anything but a single digit");
                    } else {
                        //Integer.parseInt(answer)-1

                        // incompatible java.string for destination and java.object, added .toString for compilation
                        destination = setNeighbors.get(key).get(Integer.parseInt(answer)-1).toString();
                        getOutOfLoop = true;
                    }
                }
            }
        }
        return destination;
    }

}