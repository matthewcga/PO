package agh.ics.oop.gui;

import agh.ics.oop.style.StyledButton;
import agh.ics.oop.style.StyledText;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class NewPlayerVM {
    private final VBox newPlayerVM = new VBox();
    private final TextField nickValue = new TextField();
    private final StyledButton start = new StyledButton(50);
    private final StyledText info = new StyledText(null);

    public NewPlayerVM(App parent) {
        Pattern pattern = Pattern.compile("\\w{0,20}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> pattern.matcher(change.getControlNewText()).matches() ? change : null);

        nickValue.setPromptText("nick (3-20)");
        nickValue.setOnKeyTyped(e -> {
            if (nickValue.getText().length() < 3) { start.setDisable(true); info.setText("min. 3 znaki"); }
            else { start.setDisable(false); info.setText(null); }
        });
        nickValue.setTextFormatter(formatter);

        start.setDisable(true);
        start.setText("Start");
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
