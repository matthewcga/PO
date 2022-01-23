package agh.ics.oop.gui;

import agh.ics.oop.Card;
import agh.ics.oop.CardsLibrary;
import agh.ics.oop.NewPlayer;
import agh.ics.oop.Stats;
import agh.ics.oop.style.StyledButton;
import agh.ics.oop.style.StyledText;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

import static agh.ics.oop.ImageLibrary.*;
import static agh.ics.oop.World.CatchException;

public class GameVM {
    private final VBox gameVM = new VBox();
    private final StyledButton accept = new StyledButton();
    private final StyledButton reject = new StyledButton();
    private final CardVM cardVM = new CardVM();

    private final ProgressBar armyBar = new ProgressBar();
    private final ProgressBar goldBar = new ProgressBar();
    private final ProgressBar foodBar = new ProgressBar();
    private final ProgressBar techBar = new ProgressBar();

    private final StyledText score = new StyledText(null);

    private final Queue<Integer> cardsQueue;
    private final NewPlayer player;
    private final App parentVM;

    private Card card;

    public GameVM(NewPlayer newPlayer, App parent) {
        parentVM = parent;
        player = newPlayer;
        cardsQueue = new LinkedList<>(MakeCardsList());

        armyBar.setId("bar");
        goldBar.setId("bar");
        foodBar.setId("bar");
        techBar.setId("bar");

        HBox stats = new HBox();
        stats.setAlignment(Pos.CENTER);
        stats.setPadding(new Insets(10, 10, 10, 10));
        stats.setSpacing(10);
        stats.getChildren().addAll(armyIcon, armyBar, goldIcon, goldBar, foodIcon, foodBar, techIcon, techBar, score);

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
        try {
            card = CardsLibrary.GetCard(cardsQueue.poll());
            cardVM.updateCardVM(card);
            accept.setText(card.getAccept());
            reject.setText(card.getReject());
        }
        catch (Exception ex) { CatchException(ex); }
    }

    public VBox GetGameVM() { return gameVM; }

    private List<Integer> MakeCardsList() {
        LinkedList<Integer> cards = new LinkedList<>(IntStream.range(0, CardsLibrary.GetCardsAmount()).boxed().toList());
        Collections.shuffle(cards);
        return cards;
    }

    private void UpdateStats() {
        Stats stats = player.getStats();

        armyBar.setProgress(stats.getArmy() / 100.0);
        goldBar.setProgress(stats.getGold() / 100.0);
        foodBar.setProgress(stats.getFood() / 100.0);
        techBar.setProgress(stats.getTech() / 100.0);

        score.setText("Wynik: " + NewPlayer.getScore());
    }
}
