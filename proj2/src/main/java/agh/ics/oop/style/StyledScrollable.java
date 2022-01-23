package agh.ics.oop.style;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public abstract class StyledScrollable {
    protected final ScrollPane scrollPane = new ScrollPane();
    protected final VBox scrollContent = new VBox();

    public StyledScrollable() {
        scrollContent.setAlignment(Pos.CENTER);
        scrollContent.setPadding(new Insets(10, 10, 10, 10));
        scrollPane.setContent(scrollContent);
        scrollPane.setMaxHeight(750);
        refreshScrollable();
    }

    public ScrollPane GetScrollableVM() { return scrollPane; }
    public abstract void refreshScrollable();
}
