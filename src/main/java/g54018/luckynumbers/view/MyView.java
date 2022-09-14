/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.luckynumbers.view;

import g54018.luckynumbers.model.Model;
import g54018.luckynumbers.model.Position;
import g54018.luckynumbers.model.Tile;
import java.util.List;
import java.util.Scanner;

/**
 * Create the view of the game Can ask a Position to user, the number of player in the game Display a welcome message, the current game of the player and error message
 *
 * @author basile <54018@etu.he2b.be>
 */
public class MyView implements View {

    private final String AUTHOR = "54018 (Basile Routier)";
    private final String VERSION = "1.3";
    private final String JEU = "Lucky Numbers";
    private Model model;

    /**
     * Create a view for the all current game
     *
     * @param model Model : model to get the current game
     */
    public MyView(Model model) {
        this.model = model;
    }

    /**
     * Check wich chain is largest and return the size of the largest chain
     *
     * @param title String[] : array of all word of a String array
     * @return the size of the largest String
     */
    private int maximumLgthOfMessage(String[] title) {
        int maxLgtTile = 0;
        for (var i = 0; i < title.length; i++) {
            if (title[i].length() > maxLgtTile) {
                maxLgtTile = title[i].length();
            }
        }
        return maxLgtTile;
    }

    /**
     * Display all the message put in the parameter of String array 
     * Format all the String to put in the ordre of size and have a non-straight display 
     * All the code is reusable and dynamic 
     * It check if the current word length size is different 
     * than the largest size of word to put in the center (not the same line) of the largest word
     *
     * @param title String[] : array of all word of a String array
     * @param largestLgtWord int: the largest size of a chain in a array of String
     * @param currentIndex
     */
    private void displayCenteredMessage(String[] title, int lineWidth, int currentIndex) {
        int i = currentIndex;
        if (title[i].length() != lineWidth) {
            int currentLgtWord = lineWidth - title[i].length();
            for (var j = 0; j < currentLgtWord; j++) {
                if (j == currentLgtWord / 2) {
                    System.out.print("  " + title[i] + "  ");
                }
                System.out.print(" ");
            }
        } else {
            System.out.print("  " + title[i] + "  ");
        }
    }

    /**
     * Display the hyphen to make a square in the border 
     * Get the largest length size chain to make a nice display
     *
     * @param largestLgtWord int: the largest size of a chain in a array of String
     */
    private void displayHyphen(int largestLgtWord) {
        System.out.print("\n|");
        for (var j = 0; j < largestLgtWord; j++) {
            System.out.print("-");
        }
        System.out.print("|");
    }

    /**
     * Display the Message if the the current index (i) is between 1 and the String array length, 
     * If it is true it will substract the current index by 1 to start at the beginning of the String array 
     * It is the content of the square 
     * If not it will only display the hyphen, it is the upper and lower bordre
     *
     * @param title String[] : array of all word of a String array
     * @param largestLgtWord int: the largest size of a chain in a array of String
     * @param i : current index of the loop
     */
    private void displayMessageHyphen(String[] title, int largestLgtWord, int i) {
            displayCenteredMessage(title, largestLgtWord, i);
    }

    /**
     * Display all the formatted message 
     * It display only straight bar at the beginning and end of the String array
     *
     * @param title String[] : array of all word of a String array
     */
    private void welcomeMessage(String... title) {
        int largestLgtWord = maximumLgthOfMessage(title);
        displayHyphen(largestLgtWord + 4);
        for (var i = 0; i < title.length; i++) {
            System.out.print("\n|");
            displayMessageHyphen(title, largestLgtWord, i);
            System.out.print("|");
        }
        displayHyphen(largestLgtWord + 4);
        
    }

    /**
     * Display the word with an offset
     *
     * @param sentence String: sentence for the format the output of him
     */
    private void makeItem(String sentence) {
        System.out.println("\t * " + sentence);
    }

    /**
     * Display all the word in the String array with an offset
     *
     * @param summary String: sentence for a summary
     */
    private void smallSummary(String... summary) {
        System.out.println("\n\nRésumé du jeu : ");
        for (var i = 0; i < summary.length; i++) {
            makeItem(summary[i]);
        }
    }

    /**
     * Display information for the user like : game name, author, version, a small summary, ... 
     * Welcome message with dynamic sentence and a small summary who can be change
     */
    @Override
    public void displayWelcome() {
        welcomeMessage("Bonjour",
                "Bienvenue sur le jeu du " + JEU, "",
                "La version actuelle est la " + VERSION,
                "Le jeu a été créer par " + AUTHOR, "",
                "En vous souhaitant un bon jeu!", "",
                "Le bref résumé du jeu est disponible ci-dessous");

        smallSummary("Placer une tuile de façon à ce qu'elle soit croissante au reste de sa COLONNE et de sa LIGNE",
                "Il est possible d'avoir plusieurs fois le même chiffre dans notre jeu, il devra être placer de façon de respecter la règle ci-dessus",
                "Le premier à finir de remplir son tableau avec les bons chiffres remporte le point",
                "Le nombre de joueur est compris entre 2 et 4");
    }

    /**
     * Get the current player and format it to the display of a user
     * It format the display to put the title in the middle of the board game
     */
    private void displayCurrentPlayer() {
        int currentPlayerToDisplay = model.getCurrentPlayerNumber() + 1;
        String playerMessage = "Joueur " + currentPlayerToDisplay;
        var lgtBoardDivided = model.getBoardSize()/2;
        int lengthOfTheViewGameResponsive = model.getBoardSize()*4;
        for(var i=0;i<lgtBoardDivided;i++){
            System.out.print("  ");
        }
        System.out.println(playerMessage);
        for (var i = 0; i < lengthOfTheViewGameResponsive; i++) {
            System.out.print("-");
        }
        System.out.println("");
    }

    /**
     * Display all the number of Tile in the board of player. 
     * Format the display to be dynamic and reusable. 
     * Display a simple view like : | 1 2 3 4 |
     *
     * @param lgtBoard int : the current board length size of the player
     */
    private void displayUpperBordNumber(int lgtBoard) {
        System.out.print(" | ");
        for (var i = 0; i < lgtBoard-1; i++) {
            System.out.print(" " + (i + 1) + " ");
        }
        System.out.print(" " + (lgtBoard) + " |");
        System.out.println("");
    }

    /**
     * Display the hyphen for the simple view of the board. 
     * Can be use for upper and lower bord Look like : -+ - - - - +
     *
     * @param lgtBoard int : the current board length size of the player
     */
    private void displayBordHyphen(int lgtBoard) {
        System.out.print("-+ " + " -");
        for (var i = 1; i < lgtBoard-1; i++) {
            System.out.printf("%3s", "-");
        }
        System.out.printf("%3s", "-");
        System.out.print(" +");
        System.out.println("");
    }

    /***
     * Display all the information board of the current player and a straight bar to differentiate each row 
     * Check if the Tile is equal to null, if it is it will display 
     * If not it will display the value of the Tile
     * Look like : | 1 * 20 * * |
     *
     * @param lgtBoard int : the current board length size of the player
     */
    private void displayBoardUser(int lgtBoard) {
        Tile tile;
        for (int i = 0; i < lgtBoard; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < lgtBoard; j++) {
                tile = model.getTile(model.getCurrentPlayerNumber(), new Position(i, j));
                if (tile == null) {
                    System.out.printf("%3s", "*");
                } else {
                    System.out.printf("%3d", tile.getValue());
                }
            }
            System.out.println(" |");
        }
    }
    
    /**
     * Display the number of hidden Tile in the deck
     */
    private void displayNumberStillHiddenTile(){
        System.out.println("");
        System.out.println("Il reste " + model.faceDownTileCount() + " cartes en stock");
    }
    
    /**
     * Check every length of the Tile value in the visible deck
     * And return the total of the length
     * @return the length for each value in the deck
     */
    private int lengthListDeck(){
        int sizeWordDeck=0;
        var listDeck = model.getAllfaceUpTiles();
        for(var i=0;i<listDeck.size();i++){
            if(listDeck.get(i).getValue()>=10){
                sizeWordDeck += 2;
            }else{
                sizeWordDeck++;
            }
        }
        return sizeWordDeck;
    }
    
    /**
     * Display for the number of Tile in the deck an equals for each Tile
     */
    private void displayHyphenList(){
        int sizeWordDeck = lengthListDeck();
        int listDeck = model.getAllfaceUpTiles().size();
        System.out.print("\t");
        System.out.print("=");
        for(var i=0;i<sizeWordDeck;i++){
            System.out.print("=");
        }
        for(var i=0;i<listDeck;i++){
            System.out.print("=");
        }
        System.out.println("");
    }
    
    /**
     * Display for each Tile in the current deck, the value of which Tile
     * Like : =======
     *         1 2 3
     *        =======
     */
    private void displayAvailableTile(){
        var listDeck = model.getAllfaceUpTiles();
        System.out.println("");
        System.out.println("   Liste des tuiles visibles");
        displayHyphenList();
        System.out.print("\t");
        System.out.print(" ");
        for(var i : listDeck){
            System.out.print(i.getValue() + " ");
        }
        System.out.println("");
        displayHyphenList();
        System.out.println("");
    }
    
    /**
     * Check if their is no one avaible Tile in the deck
     * If it is null, it will display an message saying their is no one Tile
     * Else it will display all the avaibale Tile in the deck
     */
    private void displayDeck(){
        var listDeck = model.getAllfaceUpTiles();
        if(listDeck.size()==0){
            System.out.println("");
            System.out.println("\t   Il n'y a pas de tuile visible");
            System.out.println("");
            
        }else{
            displayAvailableTile();
        }
    }

    /**
     * Check the robustness of the entry such as the entry is an integer
     * If the choice is not 0 and if the choice is not 1 and the number of available Tile is 0
     * It will reask such as the choice is valid
     * And return this choice
     * 
     * @param sc Scanner: it will use to know which option does user want
     * @return the choice of the option for pick a visible tile or random
     */
    private int robustChoice(Scanner sc){
        int choiceTile=0;
        var listDeck = model.getAllfaceUpTiles();
        do{
            System.out.println("Vous avez plusieurs choix pour récupérer des cartes, entrez donc : \n\t * 0 pour les cartes non visibles \n\t * 1 pour récupérer une carte dans la pioche");
            choiceTile = verifRobustRead(sc, "L'option voulu n'est malheuresement pas encore disponible", "\tLe choix rentré doit être un nombre soit 0 ou soit 1 comme indiqué précedemment");
            if((choiceTile!=1 || listDeck.size()<1) && (choiceTile!=0)){
                displayError("Entrez un nombre qui soit correct donc qui soit 0 pour les cartes non visibles ou 1 pour les cartes visibles, il faut bien évidemment qu'il y est des tuiles dans la pioche visible");
            }
        }while((choiceTile!=1 || listDeck.size()<1) && (choiceTile !=0));
        return choiceTile;
    }
    
    /**
     * Check the robustness of the entry such as the entry is an integer
     * If the Tile is not inside the available Tile deck
     * It will reask such as the Tile is good
     * And change the pickedTile to the choice
     * 
     * @param sc Scanner: it will use to know which option does user want
     */
    private void choiceVisibleTile(Scanner sc){
        var listDeckVisible = model.getAllfaceUpTiles();
        int choiceTile = 0;
        System.out.println("");
        do{
            System.out.println("\tVeuillez entrez une tuile qui est dans la pioche. Il n'est malheuresement pas possible de sortir du jeu mais il est possible de prendre une tuile et de la redeposer pour passer au joueur suivant.");
            choiceTile = verifRobustRead(sc, "L'option voulu n'est malheuresement pas encore disponible", "\tLe choix rentré doit être un nombre soit 0 ou soit 1 comme indiqué précedemment");
            if(!listDeckVisible.contains(new Tile(choiceTile))){
                displayError("La tuile que vous avez entrez n'est pas dans la pioche. Veuillez réessayer avec une valide.");
            }
        }while(!listDeckVisible.contains(new Tile(choiceTile)));
        model.pickFaceUpTile(new Tile(choiceTile));
    }
    
    /**
     * Check if the choice is the random Tile with the 0 and pick a face down Tile
     * Else it ask which Tile in the deck is  gonna be take
     * 
     */
    private void pickHiddenTileOrNot(){
        Scanner sc = new Scanner(System.in);
        int choiceTile = robustChoice(sc);
        if(choiceTile==0){
            model.pickFaceDownTile();
        }else{
            choiceVisibleTile(sc);
        }
    }
    
    /**
     * Check the robustness of the entry such as the entry is an integer
     * If the choice is different than 0 and 1
     * It will reask the choice such as is the good one
     * Return the valid choice
     * 
     * @return the choice of the user if we want to put or drop the tile
     */
    private int putTileOrDropVerif(){
        Scanner sc = new Scanner(System.in);
        int choiceDropOrPut;
        do{
            System.out.println("\tVeuillez entrez une option soit : \n\t* 0 pour poser la tuile choisie dans votre plateau\n\t* 1 pour rédeposer la tuile dans la table mais votre tour sera passé.");
            choiceDropOrPut = verifRobustRead(sc, "L'option choisie n'est pas encore disponible, il ne faut pas rentrer de texte pour les choix saisissez soit : 0 pour poser la tuile ou 1 pour la redéposer dans la table");
            if(choiceDropOrPut!=0 && choiceDropOrPut!=1){
                displayError("Veuillez bien vérifier à entrer une option correct. Vous pouvez revérifier les options ci-dessous :");
            }
        }while(choiceDropOrPut!=0 && choiceDropOrPut!=1);
        return choiceDropOrPut;
    }
    
    /**
     * If the choice is 0 it will put the Tile into a given position selected by the user
     * Else it will drop the Tile into the available deck
     * 
     */
    @Override
    public void putTileOrDrop(){
        int choiceDropOrPut = putTileOrDropVerif();
        if(choiceDropOrPut==0){
            model.putTile(askPosition());
        }else{
            model.dropTile();
        }
    }

    /**
     * Display the current picked Tile Check if the current state is valid to get a Tile
     */
    private void chosenTile() {
        System.out.println("Tuile choisie : " + model.getPickedTile().getValue());
    }

    /**
     * Display all the interface of the game it design the
     * <ul>
     * <li>The current player who play</li>
     * <li>His own Board with all the tiles that put inside it</li>
     * <li>His board with a simple view, dynamic and reusable of the number of the size of the board</li>
     * <li>The number of still hidden Tile in the game</li>
     * <li>Display the visible deck</li>
     * <li>Ask to the player if we want to take a random Tile in the hidden deck or the visible deck</li>
     * <li>The select tile</li>
     * </ul>
     */
    @Override
    public void displayGame() {
        int lgtBoard = model.getBoardSize();
        System.out.println("\n");
        
        displayCurrentPlayer();
        displayUpperBordNumber(lgtBoard);
        displayBordHyphen(lgtBoard);
        displayBoardUser(lgtBoard);
        displayBordHyphen(lgtBoard);
        displayNumberStillHiddenTile();
        displayDeck();
        pickHiddenTileOrNot();
        chosenTile();
    }
    
    /**
     * Display the winner of the game
     * If the at the position 1 in the list the number is -1 it display the only winner who finish the game
     * Else it display all the winner which have the minimum of null Tile in their game
     */
    @Override
    public void displayWinner() {
        List<Integer> playerWinner = model.getWinners();
        if(playerWinner.size()==2 && playerWinner.get(1)==-1){
            System.out.println("\nLe grand gagnant du jeu est le : joueur "+ (playerWinner.get(0) +1) +". \n\tBien joué à lui !!!");
        }else{
            System.out.println("\nLe jeu s'est malheuresement fini car toutes les tuiles cachées ont été révélé. Voici le(s) gagnants(s) ce qui ont le moins de case vide :");
            for(var i: playerWinner){
                System.out.println("\t* Le joueur " + (i+1));
            }
        }
        
    }
    
    /**
     * Verify if the number of the user is an Integer and not a chain, decimal or other stuff
     * If the user do not enter the right values, it display an error and re ask the number
     * Such as is a good value
     * 
     * @param sc Scanner : get the value enter by user and gonna be compare
     * @param mutliScan Boolean : allow to know if Scanner need two verificiation or only one
     * @param messageError Array String: contain all the error message
     * @return true when a number is valid so it is an Integer
     */
    private int verifRobustRead(Scanner sc, String... messageError) {
        while (!sc.hasNextInt()) {
            for(String message : messageError){
                    displayError(message);
            }
            sc.next();
        }
        return sc.nextInt();
    }

    /**
     * Check with a robust reading method if the number is a Integer
     * If it is , it get the Integer and check if the player number is between 2 and 4 
     * And return true if the number is valid
     * 
     * @return the number of player in the game who is valid
     */
    private int verifPlayerCount() {
        Scanner sc = new Scanner(System.in);
        int playerCount;
        boolean isNotBetween;
        do {
            System.out.print("Avec combien de joueur voulez-vous jouez ? Veuillez entrez un nombre entre 2 et 4 : \n");
            playerCount = verifRobustRead(sc, "\nVeuillez rentrez un chiffre entier. Veuillez entrez un nombre entre 2 et 4 :");
            isNotBetween = (playerCount < 2 || playerCount > 4);
            if(isNotBetween){
                displayError("Le nombre de joueur n'est pas entre 2 et 4. Il sera compliqué de jouer tout seul ou de jouer avec trop de personnes non ?");
            }
        } while(isNotBetween);
        return playerCount;
    }

    /**
     * Ask the number of all the player who can play in the game, this number is including between 2 and 4
     * Verify if the number of player count is valid so different of null, chain or decimal.
     * And check if the player enter the good number of player. If this number is not between 2 and 4. It will reask the number
     * Such as the number of player is valid
     * 
     * @return the number of player in the game who will play
     */
    @Override
    public int askPlayerCount() {
        System.out.println("\n\nComme vu précédemment dans les règles, le jeu se joue entre 2 et 4 joueurs maximum.");
        return verifPlayerCount();
    }
    
    /**
     * Verify if the number is a valid Integer with none chain or decimal
     * Loop to check if the number position is inside the board with a brief explanation of what the expected format is
     * If the value is correct and inside the board, it will return a position
     * 
     * @param explanation String : short sentence to describe the format for a valid position
     * @param exemple String : short exemple to show the different accepted format
     * @return a valid position who are inside the board and Integer
     */
    private Position verifIsInsideBoard(String explanation, String exemple) {
        String errorRow = "\n La position de la ligne n'est pas correct, veuillez bien regarder les instructions pour choisir une position";
        String errorCol = "\n La position de la colonne n'est pas correct, veuillez bien regarder les instructions pour choisir une position";
        Scanner sc = new Scanner(System.in);
        int row=-1, col=-1;
        String rowStr, colStr;
        Position pos = null;
        var isInside = false;
        do{
            System.out.println(explanation);
            System.out.println(exemple);
            row=-1;
            col=-1;
            try{
                rowStr = sc.next();
                row = Integer.parseInt(rowStr);
            }catch(NumberFormatException e){
                displayError(errorRow);
                sc.nextLine();
                continue;
            }
            try{
               colStr = sc.next();
               col = Integer.parseInt(colStr);
            }catch(NumberFormatException e){
                displayError(errorCol);
                sc.nextLine();
                continue;
            }
            pos = new Position(--row, --col);
            isInside = model.isInside(pos);
            if(!isInside){
                displayError("PLOUF, la position que vous avez rentrez n'est pas entre 1 et " + model.getBoardSize() +". Attention que votre tuile ne tombe pas la prochaine fois.");
            }
        } while(!isInside);
        return pos;
        
    }
    
    /**
     * Verify is the enter position is inside the board
     * Loop to check if the number position is can be put inside the board of the player
     * If the value can be put, it will return a Position
     * If the position can not be put, it will display an error message and loop
     * 
     * @param smallRule String : short sentence that explains at what point in the game the player is
     * @param explanation String : short sentence to describe the format for a valid position
     * @param exemple String : short exemple to show the different accepted format
     * @return a Position that can be put inside the board
     */
    private Position verifPosition(String smallRule, String explanation, String exemple) {
        Scanner sc = new Scanner(System.in);
        Position pos;
        boolean canBePut;
        System.out.println(smallRule);
        do {
            pos = verifIsInsideBoard(explanation, exemple);
            canBePut = model.canTileBePut(pos);
            if(!canBePut){
                displayError("La position que vous avez choisie n'est malheuresement pas possible à poser. Elle n'est soit pas croissante envers les lignes ou les colonnes.");
            }
        } while (!canBePut);
        return pos;
    }
    
    /**
     * Ask the Position for a select Tile
     * Will choose the number of the row and the column 
     * to put it's select tile inside his board
     * If the position is not inside the board it will display an error and re ask to the player a position
     * It will check if the selected Tile can be put inside the board with the player position.
     * Robust Reading method
     * Such as the position is good
     * 
     * @return a valid position
     */
    @Override
    public Position askPosition() {
        String smallRule = "Vous êtes maintenant à l'étape de choisir une position qui comprend une ligne et une colonne.";
        String explanation = "\n Veuillez choisir une position qui est entre 1 et "+model.getBoardSize()+" et bien-sûr possible à placer";
        String exemple = "\tVoici un exemple de format accepté, le L et le C ne sont juste la pour vous indiquez la ligne et la colonne MAIS ne doivent pas être mis. \n\t * Exemple :   L:1 C:3  ou  1 puis 2. Ce qui signifie que la tuile choisi sera insérer à la ligne 1 et colonne 3";
        return verifPosition(smallRule, explanation, exemple);
    }

    /**
     * Display the error with a specific format The format is underline the error message
     *
     * @param message String: message error that give in parameter to be display
     */
    @Override
    public void displayError(String message) {
        System.out.println("\n" + message);
        for (var i = 0; i < message.length(); i++) {
            System.out.print("-");
        }
        System.out.print("\n");
    }

}
