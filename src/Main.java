import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) {
		boolean playing = true;
		Scanner s = new Scanner(System.in);


		//	ゲーム開始
		while(playing) {
			System.out.println("ブラックジャック  スタート！");
			System.out.println("カードを配ります");



			//	カードの点数
			int player_count = 0;
			int com_count = 0;



			//	配られたカードを格納
			List<String> com_cards = new ArrayList<String>();
			List<String> player_cards = new ArrayList<String>();



			//	カードを配る
			Card com_card1 = new Card();
			com_cards.add(com_card1.drowCard());
			com_count += com_card1.cardPoint();


			Card player_card = new Card();
			for(int i = 0; i < 2; i++){
				player_cards.add(player_card.drowCard());
				player_count += player_card.cardPoint();
			}

			System.out.println("ディーラー : " + com_cards);
			System.out.println("あなた : " + player_cards);
			if(Card.bj(player_cards)) {
				System.out.println("あなたの勝ちです。");
				continue;
			}




			//	「引く」か「勝負」かを選択
			while(true) {
				System.out.println();
				System.out.println("勝負しますか？");
				System.out.println("[1]もう一枚引く・[2]勝負");

				int player = s.nextInt();


				// 「引く」処理
				if (player == 1) {
					Card player_card3 = new Card();
					player_cards.add(player_card3.drowCard());
					System.out.println("ディーラー : " + com_cards);
					System.out.println("あなた : " + player_cards);
					player_count += player_card3.cardPoint();

					//	バースト判定
					if (player_count > 21) {
						System.out.println("バーストしました。あなたの負けです。");
						playing = false;
						break;
					}
					continue;
				} else {	//	「勝負」処理
					Card com_card2 = new Card();
					com_cards.add(com_card2.drowCard());
					com_count += com_card2.cardPoint();

					System.out.println("ディーラー : " + com_cards + " : " + com_count);
					System.out.println("あなた : " + player_cards + " : " + player_count);


					//	結果を判定
					if(Card.bj(com_cards)) {
						System.out.println("ディーラーの勝ちです。");
						break;
					}else {
						Vs.deal(player_count,com_count);
						break;
					}
				}
			}

			System.out.println();	// 見た目ようの空行


			//	終了判定
			System.out.print("[何かのキー]もう１勝負しますか？・[9]終了しますか？");
			int player = s.nextInt();
			if (player == 9) {
				playing = false;
			} else {
				playing = true;
				continue;
			}
		}
	}
}




/*
 * 	カード関連の処理
 */
class Card {
	String[] cards = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	int[] points = {1,2,3,4,5,6,7,8,9,10,10,10,10};
	int x = (int)(Math.random()*12);


	//	カードを配る
	public String drowCard() {
		return cards[x];
	}


	//	カードの点数
	public int cardPoint() {
		return points[x];
	}


	//	ブラックジャックの判定
	static boolean bj(List<String> list) {
		if (list.contains("A")) {
			if (list.contains("J") || list.contains("Q") ||
					list.contains("K")) {
				System.out.println("ブラックジャック！");
				return true;
			}
		}
		return false;
	}
}




/*
 *	勝負の結果を表示
 */
class Vs {
	static void deal(int x, int y) {
		TreeSet<Integer> counts = new TreeSet<Integer>();
		counts.add(x);
		counts.add(y);
		int z = counts.floor(21);
		if (x == z) {
			System.out.println("あなたの勝ちです。");
		}else if(x == y){
			System.out.println("同点でドローでした。");
		}else {
			System.out.println("ディーラーの勝ちです。");
		}
		System.out.println();
	}
}
