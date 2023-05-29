module com.example.programmingassignmentgui {
  requires javafx.controls;
  requires javafx.fxml;


  opens com.example.programmingassignmentgui to javafx.fxml;
  exports com.example.programmingassignmentgui;
}