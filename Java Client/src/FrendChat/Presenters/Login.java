package FrendChat.Presenters;

import FrendChat.Models.FrendServer;
import com.sun.javaws.exceptions.ExitException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Login {
    final int maxUsernameLength = 25;

    FrendServer frendServer = FrendServer.getInstance();

    @FXML
    private TextField txtLoginUsername;
    @FXML
    private PasswordField pswLoginPassword;
    @FXML
    private TextField txtRegisterUsername;
    @FXML
    private PasswordField pswRegisterPassword;
    @FXML
    private PasswordField pswConfirmPassword;
    @FXML
    private ColorPicker clrRegisterColor;

    public void btnLogin(ActionEvent actionEvent) {
        if (txtLoginUsername.getText().contains(" ")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Frend Chat");
            alert.setHeaderText("Username May Not Contain a Space");
            alert.setContentText("Your username may not contain a space. Please choose something different.");
            alert.showAndWait();

            return;
        }

        if (txtLoginUsername.getText().length() > maxUsernameLength) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Frend Chat");
            alert.setHeaderText("Username May Not Contain Be Greater Than " + maxUsernameLength + " Characters" );
            alert.setContentText("Your username may not be greater than " + maxUsernameLength + " characters. Please choose something different.");
            alert.showAndWait();

            return;
        }

        if (txtLoginUsername.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Frend Chat");
            alert.setHeaderText("Username Field Is Empty");
            alert.setContentText("You have not typed in a username.");
            alert.showAndWait();

            return;
        }

        if (pswLoginPassword.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Frend Chat");
            alert.setHeaderText("Password Field Is Empty");
            alert.setContentText("You have not typed in a password.");
            alert.showAndWait();

            return;
        }

        frendServer.login(txtLoginUsername.getText(), pswLoginPassword.getText(), this);
    }

    public void btnRegister(ActionEvent actionEvent) {
        if (txtRegisterUsername.getText().contains(" ")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Frend Chat");
            alert.setHeaderText("Username May Not Contain a Space");
            alert.setContentText("Your username may not contain a space. Please choose something different.");
            alert.showAndWait();

            return;
        }

        if (txtRegisterUsername.getText().length() > maxUsernameLength) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Frend Chat");
            alert.setHeaderText("Username May Not Contain Be Greater Than " + maxUsernameLength + " Characters" );
            alert.setContentText("Your username may not be greater than " + maxUsernameLength + " characters. Please choose something different.");
            alert.showAndWait();

            return;
        }

        if (txtRegisterUsername.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Frend Chat");
            alert.setHeaderText("Username Field Is Empty");
            alert.setContentText("You have not typed in a username.");
            alert.showAndWait();

            return;
        }

        if (pswRegisterPassword.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Frend Chat");
            alert.setHeaderText("Password Field Is Empty");
            alert.setContentText("You have not typed in a password.");
            alert.showAndWait();

            return;
        }

        if (!pswRegisterPassword.getText().equals(pswConfirmPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Frend Chat");
            alert.setHeaderText("Passwords Do Not Match");
            alert.setContentText("Your passwords do not match. Please try again.");
            alert.showAndWait();

            return;
        }

        byte red = (byte) (clrRegisterColor.getValue().getRed() * 255);
        byte green = (byte) (clrRegisterColor.getValue().getGreen() * 255);
        byte blue = (byte) (clrRegisterColor.getValue().getBlue() * 255);
        String colorHex = String.format("#%02X%02X%02X", red, green, blue);

        System.out.println(colorHex);

        frendServer.register(txtRegisterUsername.getText(), pswRegisterPassword.getText(), colorHex, this);

    }

    public void mdlCredentialsAccepted() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Frend Chat");
            alert.setHeaderText("Credentials Accepted");
            alert.setContentText("This is as far as Frend Chat is implemented though.");
            alert.showAndWait();
        });
    }

    public void mdlCredentialsRejected() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Frend Chat");
            alert.setHeaderText("Invalid Username or Password");
            alert.setContentText("Could not log in with the provided username and password. Please try again.");
            alert.showAndWait();
        });
    }

    public void mdlConnectionError() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Frend Chat");
            alert.setHeaderText("Connection Error");
            alert.setContentText("A communication error has occurred with the server.");
            alert.showAndWait();

            frendServer.closeConnection();

            Stage stage = FrendChat.Main.getPrimaryStage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/FrendChat/Views/Connect.fxml"));
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                System.exit(ExitException.LAUNCH_ERROR);
            }
        });
    }
}
