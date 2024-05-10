package shaodado;

 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.List;
 import java.util.Scanner;


 public class game21 {
     public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
         System.out.println("歡迎來到21點遊戲！");
         System.out.println("請問你的名字是？");
         String playerName = scanner.nextLine();
         System.out.println("歡迎，" + playerName + "！讓我們開始遊戲吧。");

         Deck deck = new Deck();
         deck.shuffle();

         Player player = new Player(playerName);
         Dealer dealer = new Dealer();

         player.addCard(deck.dealCard());
         dealer.addCard(deck.dealCard());
         player.addCard(deck.dealCard());
         dealer.addCard(deck.dealCard());

         System.out.println("玩家 " + playerName + " 的手牌為：" + player.getHand());
         System.out.println("莊家的首張手牌為：" + dealer.getHand().get(0));

         // 玩家回合
         playerTurn(scanner, deck, player);

         // 莊家回合
         dealerTurn(deck, dealer);

         // 判斷輸贏
         determineWinner(player, dealer);

         scanner.close();
     }

     private static void playerTurn(Scanner scanner, Deck deck, Player player) {
         while (true) {
             System.out.println("請問你要(1)抽牌還是(2)停止抽牌？");
             int choice = scanner.nextInt();
             if (choice == 1) {
                 player.addCard(deck.dealCard());
                 System.out.println("你的手牌為：" + player.getHand());
                 if (player.getHandValue() > 21) {
                     System.out.println("你爆了！");
                     break;
                 }
             } else if (choice == 2) {
                 break;
             }
         }
     }

     private static void dealerTurn(Deck deck, Dealer dealer) {
         System.out.println("莊家的手牌為：" + dealer.getHand());
         while (dealer.getHandValue() < 17) {
             dealer.addCard(deck.dealCard());
             System.out.println("莊家抽牌...");
             System.out.println("莊家的手牌為：" + dealer.getHand());
         }
     }

     private static void determineWinner(Player player, Dealer dealer) {
         int playerScore = player.getHandValue();
         int dealerScore = dealer.getHandValue();
         System.out.println("玩家 " + player.getName() + " 的手牌總和為：" + playerScore);
         System.out.println("莊家的手牌總和為：" + dealerScore);
         if (playerScore > 21) {
             System.out.println("莊家獲勝！");
         } else if (dealerScore > 21 || playerScore > dealerScore) {
             System.out.println("玩家 " + player.getName() + " 獲勝！");
         } else if (playerScore < dealerScore) {
             System.out.println("莊家獲勝！");
         } else {
             System.out.println("平局！");
         }
     }
 }

 class Card {
     private final String suit;
     private final String rank;

     public Card(String suit, String rank) {
         this.suit = suit;
         this.rank = rank;
     }

     public String toString() {
         return suit+" "+rank ;
     }

     public int getValue() {
         if (rank.equals("A")) {
             return 11;
         } else if (rank.equals("K") || rank.equals("Q") || rank.equals("J")) {
             return 10;
         } else {
             return Integer.parseInt(rank);
         }
     }
 }

 class Deck {
     private final List<Card> cards;

     public Deck() {
         cards = new ArrayList<>();
         String[] suits = {"紅心", "方塊", "梅花", "黑桃"};
         String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

         for (String suit : suits) {
             for (String rank : ranks) {
                 cards.add(new Card(suit, rank));
             }
         }
     }

     public void shuffle() {
         Collections.shuffle(cards);
     }

     public Card dealCard() {
         return cards.remove(0);
     }
 }

 class Player {
     private final String name;
     private final List<Card> hand;

     public Player(String name) {
         this.name = name;
         hand = new ArrayList<>();
     }

     public void addCard(Card card) {
         hand.add(card);
     }

     public List<Card> getHand() {
         return hand;
     }

     public String getName() {
         return name;
     }

     public int getHandValue() {
         int value = 0;
         int numAces = 0;
         for (Card card : hand) {
             value += card.getValue();
             if (card.getValue() == 11) {
                 numAces++;
             }
         }
         while (value > 21 && numAces > 0) {
             value -= 10;
             numAces--;
         }
         return value;
     }
 }

 class Dealer extends Player {
     public Dealer() {
         super("Dealer");
     }
 }