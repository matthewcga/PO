package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private final VBox ui = new VBox();
    private final Button newGame = new Button();
    private final Button scoreBoard = new Button();
    private final Button endGameScoreBoard = new Button();
    private final Button menu = new Button();
    private final Button endGameMenu = new Button();
    private final Button history = new Button();
    private final Button saveScore = new Button();
    private final ScoreBoardVM scoreBoardVM = new ScoreBoardVM();
    private final MoveHistoryVM moveHistoryVM = new MoveHistoryVM();

    public void start(Stage primaryStage) {
        ui.setAlignment(Pos.CENTER);
        ui.setPadding(new Insets(10, 10, 10, 10));
        ui.setSpacing(10);
        SetButtonsActions();
        MakeMenu();
        Scene scene = new Scene(ui, 500, 700);
        primaryStage.setScene(scene); primaryStage.show();
    }

    private void SetButtonsActions() {
        newGame.setText("new game");
        newGame.setOnAction(e -> {
            NewPlayerVM newPlayerVM = new NewPlayerVM(this);
            ui.getChildren().clear();
            ui.getChildren().addAll(menu, newPlayerVM.GetNewPlayerVM());
        });

        scoreBoard.setText("score board");
        scoreBoard.setOnAction(e -> {
            ui.getChildren().clear();
            ui.getChildren().addAll(menu, scoreBoardVM.GetScoreBoardVM());
        });

        endGameScoreBoard.setText("score board");
        endGameScoreBoard.setOnAction(e -> {
            ui.getChildren().clear();
            ui.getChildren().addAll(endGameMenu, saveScore, scoreBoardVM.GetScoreBoardVM());
            saveScore.setDisable(!NewPlayer.getCanSave());
        });

        menu.setText("return");
        menu.setOnAction(e -> MakeMenu());

        endGameMenu.setText("return");
        endGameMenu.setOnAction(e -> MakeEndGameMenu());

        history.setText("history");
        history.setOnAction(e -> {
            ui.getChildren().clear();
            ui.getChildren().addAll(endGameMenu, moveHistoryVM.GetMoveHistoryVM());
        });

        saveScore.setText("save score");
        saveScore.setOnAction(e -> {
            try { ScoreBoard.AddNewPlayer();}
            catch (IOException ex) { System.out.println(ex.getMessage()); ex.printStackTrace(); System.exit(1); }
            scoreBoardVM.refreshScoreBoard();
            saveScore.setDisable(!NewPlayer.getCanSave());
        });
    }

    private void MakeMenu() {
        ui.getChildren().clear();
        ui.getChildren().addAll(new Text("GAME TITLE"), newGame, scoreBoard);
    }

    private void MakeEndGameMenu() {
        String score = NewPlayer.getScore() + "/" + CardsLibrary.GetCardsAmount();
        ui.getChildren().clear();
        ui.getChildren().addAll(new Text((NewPlayer.getScore() == CardsLibrary.GetCardsAmount())? "YOU WON " + score: "GAME OVER " + score), menu, endGameScoreBoard, history);
    }

    public void GameStart(String nick) {
        ui.getChildren().clear();
        ui.getChildren().addAll(new GameVM(new NewPlayer(nick), this).GetGameVM());
    }

    public void GameEnd() {
        moveHistoryVM.refreshMoveHistory();
        MakeEndGameMenu();
    }
}