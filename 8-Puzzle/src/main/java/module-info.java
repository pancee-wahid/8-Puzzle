module com.puzzle.puzzle {
    requires javafx.controls;
    requires javafx.fxml;

//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens com.puzzle.puzzle to javafx.fxml;
    exports com.puzzle.puzzle;
}