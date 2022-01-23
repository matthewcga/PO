package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.style.StyledButton;
import agh.ics.oop.style.StyledText;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

import static agh.ics.oop.ImageLibrary.gameLogo;
import static agh.ics.oop.World.CatchException;

public class App extends Application {
    private final VBox ui = new VBox();
    private final StyledButton newGame = new StyledButton();
    private final StyledButton scoreBoard = new StyledButton();
    private final StyledButton endGameScoreBoard = new StyledButton();
    private final StyledButton menu = new StyledButton(50);
    private final StyledButton endGameMenu = new StyledButton(50);
    private final StyledButton history = new StyledButton();
    private final StyledButton saveScore = new StyledButton();
    private final ScoreBoardVM scoreBoardVM = new ScoreBoardVM();
    private final MoveHistoryVM moveHistoryVM = new MoveHistoryVM();

    public void start(Stage primaryStage) {
        ui.setAlignment(Pos.CENTER);
        ui.setPadding(new Insets(10, 10, 10, 10));
        ui.setSpacing(10);
        SetButtonsActions();
        MakeMenu();

        Scene scene = new Scene(ui, 700, 700);
        scene.getStylesheets().add(new File("src/main/resources/style.css").toURI().toASCIIString());
        primaryStage.setScene(scene); primaryStage.show();
    }

    private void SetButtonsActions() {
        newGame.setText("Nowa gra");
        newGame.setOnAction(e -> {
            NewPlayerVM newPlayerVM = new NewPlayerVM(this);
            ui.getChildren().clear();
            ui.getChildren().addAll(menu, newPlayerVM.GetNewPlayerVM());
        });

        scoreBoard.setText("Wyniki");
        scoreBoard.setOnAction(e -> {
            ui.getChildren().clear();
            ui.getChildren().addAll(menu, scoreBoardVM.GetScrollableVM());
        });

        endGameScoreBoard.setText("Wyniki");
        endGameScoreBoard.setOnAction(e -> {
            ui.getChildren().clear();
            ui.getChildren().addAll(endGameMenu, saveScore, scoreBoardVM.GetScrollableVM());
            saveScore.setDisable(!NewPlayer.getCanSave());
        });

        menu.setText("<");
        menu.setOnAction(e -> MakeMenu());

        endGameMenu.setText("<");
        endGameMenu.setOnAction(e -> MakeEndGameMenu());

        history.setText("Historia");
        history.setOnAction(e -> {
            ui.getChildren().clear();
            ui.getChildren().addAll(endGameMenu, moveHistoryVM.GetScrollableVM());
        });

        saveScore.setText("Zapisz");
        saveScore.setOnAction(e -> {
            try { ScoreBoard.AddNewPlayer();}
            catch (IOException ex) { CatchException(ex); }
            scoreBoardVM.refreshScrollable();
            saveScore.setDisable(!NewPlayer.getCanSave());
        });
    }

    private void MakeMenu() {
        ui.getChildren().clear();
        ui.getChildren().addAll(gameLogo, newGame, scoreBoard);
    }

    private void MakeEndGameMenu() {
        String score = NewPlayer.getScore() + "/" + CardsLibrary.GetCardsAmount();
        ui.getChildren().clear();
        ui.getChildren().addAll(new StyledText((NewPlayer.getScore() == CardsLibrary.GetCardsAmount())? "Wygrana! Wynik:" + score: "Przegrana! Wynik:" + score), menu, endGameScoreBoard, history);
    }

    public void GameStart(String nick) {
        ui.getChildren().clear();
        ui.getChildren().addAll(new GameVM(new NewPlayer(nick), this).GetGameVM());
    }

    public void GameEnd() {
        moveHistoryVM.refreshScrollable();
        MakeEndGameMenu();
    }
}