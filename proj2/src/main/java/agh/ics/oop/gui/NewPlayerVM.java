package agh.ics.oop.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class NewPlayerVM {
    private final VBox newPlayerVM = new VBox();
    private final TextField nickValue = new TextField();
    private final Button start = new Button();
    private final Text info = new Text();

    public NewPlayerVM(App parent) {

        Pattern pattern = Pattern.compile("\\w{0,20}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> pattern.matcher(change.getControlNewText()).matches() ? change : null);

        nickValue.setPromptText("nickname (3-20)");
        nickValue.setOnKeyTyped(e -> {
            if (nickValue.getText().length() < 3) { start.setDisable(true); info.setText("nickname is too short"); }
            else { start.setDisable(false); info.setText(null); }
        });
        nickValue.setTextFormatter(formatter);

        start.setDisable(true);
        start.setText("start");
        start.setOnAction(e -> parent.GameStart(nickValue.getText()));

        HBox form = new HBox();
        form.setAlignment(Pos.CENTER);
        form.setSpacing(10);
        form.getChildren().addAll(nickValue, start);

        newPlayerVM.setAlignment(Pos.CENTER);
        newPlayerVM.getChildren().addAll(form, info);
    }

    public VBox GetNewPlayerVM() { return newPlayerVM; }
}
