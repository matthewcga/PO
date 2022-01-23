package agh.ics.oop.style;

import javafx.scene.control.Button;

public class StyledButton extends Button {
    public StyledButton() { this.setMinWidth(200); }
    public StyledButton(int size) {
        this.setMinWidth(size);
    }
}
