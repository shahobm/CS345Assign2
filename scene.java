import java.util.*;

public class scene {

    public static String cardName = "";
    public static int sceneNumber = 0;
    public static String description = "";
    public static int shotsFilmed = 0;
    public static Role[] roles;
    //public static Card currentCard;
    public static int scenesAvailable = 10;
    public static String name;

    public scene(){
        name = null;
    }

    public void createScene(String cardName, int sceneNumber, String description) {
        this.cardName = cardName;
        this.sceneNumber = sceneNumber;
        this.description = description;
    }

    public Role[] getRoomRoles(String nameOfCard) {

        return roles;
        //pass in a card
    }


    public static int getBudget(String nameOfCard) {
        int budget = 0;
        ParseFile.parseCards();
        HashMap<String,Integer> cardBudget = ParseFile.cardBudget;
        for(String key : cardBudget.keySet()){
            if(nameOfCard.equals(key)){
                budget = cardBudget.get(key);
            }
        }
        System.out.println("key: " + key + " name of card: " + nameOfCard + " budget: " + budget);
        return budget;
    }

    public int shotsFilmed() {

        return this.shotsFilmed;
    }

    public int decrementShotCount() {
        this.shotsFilmed--;
        return this.shotsFilmed;
    }



    public static void displayInfo() {
        //System.out.println(currentCard.sceneName + "is currently shooting " + currentCard.getName());
    }


    public static boolean rolesAvailable(Role[] roles){
        return false;
    }


    public static void shootScene(int rank) {

        // show information about the scene
        displayInfo();

        //Role[] cardRoles = currentCard.getScenes();
        //System.out.println("The budget for this scene is:" + scene.getBudget() + " million dollars, and has " + shotsFilmed + " shots left.");
        //System.out.println("These are roles on the card:");
        // for(Role role : cardRoles) {
        //     if(!(role.getName().equals("0"))) {
        //         roleAvailable = true;
        //     }
        // }
        // if(!roleAvailable){
        //     System.out.println("There are no roles available");
        // }
        //
        // System.out.println("These are extra roles you can take:");
        // roleAvailable = false;
        // for(Role role: roomRoles) {
        //     if((role.getName().equals("0"))) {
        //         roleAvailable = true;
        //     }
        // }
        // if(!roleAvailable){
        //     System.out.println("There are no extra roles you can take!");
        // }
    }


    private static void endScene(int[] diceOutcomes, boolean scenesAvailable) {
        // Role[] cardRoles = getCardRoles();
        //
        // for(Role role : cardRoles) {
        //     if(role.getTaken()){
        //         role.removePlayer();
        //     }
        // }
        //
        // for(Role role : roomRoles) {
        //     if(role.getTaken()){
        //         role.removePlayer();
        //     }
        // }
        // distributeEarnings(diceOutcomes);
        // checklastScene();
    }

    public static void distributeEarnings(int[] diceOutcomes) {
        // if (currentCard.isARoleTaken()) {
        //     System.out.println(" Distributing Earnings for roles");
        //     Role[] cardRoles = getCardRoles();
        //     for (int i = 0; i < diceOutcomes.length; i++) {
        //         if (cardRoles[i % cardRoles.length].getTaken()) {
        //             cardRoles[i % cardRoles.length].giveBonus(diceOutcomes[i]);
        //         }
        //     }
        // }
    }

    // determine if scene just shot was the last scene
    public static void checklastScene(){
        if(scenesAvailable == 0){
            //EndGame.determineWinner();
        }else{
            scenesAvailable--;
        }
    }
}