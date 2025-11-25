module ph.edu.dlsu.lbycpei.caferecommmendationsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens ph.edu.dlsu.lbycpei.caferecommmendationsystem to javafx.fxml;
    exports ph.edu.dlsu.lbycpei.caferecommmendationsystem;
}