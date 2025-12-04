package ph.edu.dlsu.lbycpei.caferecommmendationsystem;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;
import java.util.ArrayList;
import java.util.List;


public class InventoryController {


    @FXML private TableView<InventoryItem> inventoryTable;
    @FXML private TableColumn<InventoryItem, String> colName;
    @FXML private TableColumn<InventoryItem, Integer> colQuantity;


    @FXML private TextField txtName;
    @FXML private TextField txtQuantity;


    private final ObservableList<InventoryItem> inventoryList = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colQuantity.setCellValueFactory(c -> c.getValue().quantityProperty().asObject());

        inventoryTable.setItems(inventoryList);

    }


    @FXML
    private void handleAdd() {
        String name = txtName.getText();
        int quantity;

        try {
            quantity = Integer.parseInt(txtQuantity.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid quantity");
            return;
        }

        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setHeaderText("Enter price for the new menu item:");
        Optional<String> result = dialog.showAndWait();

        if (result.isEmpty()) {
            showAlert("Cancelled.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(result.get());
        } catch (Exception e) {
            showAlert("Invalid price.");
            return;
        }

        List<String> existingNames = cafeSystem.getMenuItems()
                .stream()
                .map(MenuItem::getName)
                .filter(n -> !n.equals(name))
                .toList();

        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(existingNames);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Dialog<List<String>> recDialog = new Dialog<>();
        recDialog.setTitle("Select Recommended Items");

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        recDialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        recDialog.getDialogPane().setContent(listView);

        recDialog.setResultConverter(btn -> {
            if (btn == saveButton) {
                return new ArrayList<>(listView.getSelectionModel().getSelectedItems());
            }
            return null;
        });

        List<String> recommendations =
                recDialog.showAndWait().orElse(new ArrayList<>());

        cafeSystem.addNewItem(name, quantity, price, recommendations);

        inventoryList.setAll(cafeSystem.getInventory().getItems());
        inventoryTable.refresh();
        clearFields();
    }

    @FXML
    private void handleUpdate() {
        InventoryItem item = inventoryTable.getSelectionModel().getSelectedItem();
        if (item == null) {
            showAlert("Select an item to update.");
            return;
        }

        String oldName = item.getName();
        String newName = txtName.getText();

        int newQty;
        try {
            newQty = Integer.parseInt(txtQuantity.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid quantity");
            return;
        }

        cafeSystem.updateItemName(oldName, newName);
        cafeSystem.updateItemQuantity(newName, newQty);

        inventoryList.setAll(cafeSystem.getInventory().getItems());
        inventoryTable.refresh();
        clearFields();
    }

    @FXML
    private void handleEditRecommendations() {
        InventoryItem selected = inventoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Select an item to edit recommendations.");
            return;
        }

        String itemName = selected.getName();
        MenuItem menuItem = cafeSystem.getMenuItems()
                .stream()
                .filter(mi -> mi.getName().equals(itemName))
                .findFirst()
                .orElse(null);
        if (menuItem == null) {
            showAlert("This item has no menu entry.");
            return;
        }

        List<String> allNames = cafeSystem.getMenuItems()
                .stream()
                .map(MenuItem::getName)
                .filter(n -> !n.equals(itemName))
                .toList();

        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(allNames);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        for (String rec : menuItem.getRecommendedItems()) {
            listView.getSelectionModel().select(rec);
        }

        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Edit Recommendations for " + itemName);

        ButtonType saveBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(listView);

        dialog.setResultConverter(btn -> {
            if (btn == saveBtn) {
                return new ArrayList<>(listView.getSelectionModel().getSelectedItems());
            }
            return null;
        });

        List<String> newRecs = dialog.showAndWait().orElse(null);
        if (newRecs == null) return;

        cafeSystem.updateItemRecommendations(itemName, newRecs);

        showInfo("Recommendations updated!");
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private void handleDelete() {
        InventoryItem item = inventoryTable.getSelectionModel().getSelectedItem();
        if (item == null) {
            showAlert("Select an item to delete.");
            return;
        }

        cafeSystem.removeItemEverywhere(item.getName());

        inventoryList.setAll(cafeSystem.getInventory().getItems());
    }


    private void clearFields() {
        txtName.clear();
        txtQuantity.clear();
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBackToMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cafe-view.fxml"));
            Parent root = loader.load();

            MainController controller = loader.getController();
            controller.setCafeSystem(this.cafeSystem);
            controller.refreshMenuTable();

            Stage stage = (Stage) inventoryTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private CafeSystem cafeSystem;

    public void setCafeSystem(CafeSystem system) {
        this.cafeSystem = system;
        inventoryTable.setItems(
                FXCollections.observableArrayList(cafeSystem.getInventory().getItems())
        );
    }


}
