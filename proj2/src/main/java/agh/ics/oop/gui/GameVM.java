package agh.ics.oop.gui;

import agh.ics.oop.Card;
import agh.ics.oop.CardsLibrary;
import agh.ics.oop.NewPlayer;
import agh.ics.oop.Stats;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

public class GameVM {
    private final VBox gameVM = new VBox();
    private final Button accept = new Button();
    private final Button reject = new Button();
    private final CardVM cardVM = new CardVM();

    private final Text armyScore = new Text();
    private final Text goldScore = new Text();
    private final Text foodScore = new Text();
    private final Text techScore = new Text();
    private final Text score = new Text();

    private final Queue<Integer> cardsQueue;
    private final NewPlayer player;
    private final App parentVM;

    private Card card;

    public GameVM(NewPlayer newPlayer, App parent) {
        parentVM = parent;
        player = newPlayer;
        cardsQueue = new LinkedList<>(MakeCardsList());

        HBox stats = new HBox();
        stats.setAlignment(Pos.CENTER);
        stats.setPadding(new Insets(10, 10, 10, 10));
        stats.setSpacing(10);
        stats.getChildren().addAll(armyScore, goldScore, foodScore, techScore, score);

        accept.setOnAction(e -> MakeMove(true));
        reject.setOnAction(e ->  MakeMove(false));

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setSpacing(10);
        buttons.getChildren().addAll(accept, reject);


        gameVM.setAlignment(Pos.CENTER);
        gameVM.setPadding(new Insets(10, 10, 10, 10));
        gameVM.getChildren().addAll(stats, cardVM.GetCardVM(), buttons);

        GetNewCard();
        UpdateStats();
    }

    private void MakeMove(boolean move) {
        if (!player.MakeMove(card, move)) parentVM.GameEnd();
        else if (cardsQueue.isEmpty()) parentVM.GameEnd();
        else { GetNewCard(); UpdateStats(); }
    }

    private void GetNewCard() {
        card = CardsLibrary.GetCard(cardsQueue.poll());
        cardVM.updateCardVM(card);
        accept.setText(card.getAccept());
        reject.setText(card.getReject());
    }

    public VBox GetGameVM() { return gameVM; }

    private List<Integer> MakeCardsList() {
        LinkedList<Integer> cards = new LinkedList<>(IntStream.range(0, CardsLibrary.GetCardsAmount()).boxed().toList());
        Collections.shuffle(cards);
        return cards;
    }

    private void UpdateStats() {
        Stats stats = player.getStats();
        armyScore.setText("Army " + stats.getArmy() + "/100");
        goldScore.setText("Gold " + stats.getGold() + "/100");
        foodScore.setText("Food " + stats.getFood() + "/100");
        techScore.setText("Tech " + stats.getTech() + "/100");
        score.setText("Score " + player.getScore());
    }
}
