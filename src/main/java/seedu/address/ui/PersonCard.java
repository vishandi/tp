package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person} in the list version.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox cardRows;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private HBox telegramBox;
    @FXML
    private HBox githubBox;
    @FXML
    private HBox addressBox;
    @FXML
    private HBox emailBox;
    @FXML
    private FlowPane tags;



    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        ImageView addressIcon = new ImageView("/images/address_icon.png");
        ImageView emailIcon = new ImageView("/images/email_icon.png");
        ImageView telegramIcon = new ImageView("/images/telegram_icon.png");
        ImageView githubIcon = new ImageView("/images/github_icon.png");
        addressIcon.setFitHeight(16);
        addressIcon.setFitWidth(16);
        emailIcon.setFitHeight(16);
        emailIcon.setFitWidth(16);
        telegramIcon.setFitHeight(16);
        telegramIcon.setFitWidth(16);
        githubIcon.setFitHeight(16);
        githubIcon.setFitWidth(16);

        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().value);
        phone.setText(person.getPhone().value);
        if (!person.getTelegram().isEmpty()) {
            telegramBox.getChildren().add(telegramIcon);
            Label telegramLabel = new Label(person.getTelegram().value);
            telegramBox.getChildren().add(telegramLabel);
        }
        if (!person.getGithub().isEmpty()) {
            githubBox.getChildren().add(githubIcon);
            Label githubLabel = new Label(person.getGithub().value);
            githubBox.getChildren().add(githubLabel);
        }
        if (!person.getEmail().isEmpty()) {
            emailBox.getChildren().add(emailIcon);
            Label emailLabel = new Label(person.getEmail().value);
            emailBox.getChildren().add(emailLabel);
        }
        if (!person.getAddress().isEmpty()) {
            addressBox.getChildren().add(addressIcon);
            Label addressLabel = new Label(person.getAddress().value);
            addressBox.getChildren().add(addressLabel);
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @FXML
    private void emailtoContact() {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            URI mailto = null;
            try {
                mailto = new URI(String.format("mailto:%s", person.getEmail()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            try {
                desktop.mail(mailto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void openGithub() {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            URI githubUri = null;
            try {
                githubUri = new URI("https://github.com/" + person.getGithub());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            try {
                desktop.browse(githubUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void changeEmailColorWhenHovered() {
        emailBox.setStyle("-fx-background-color: #c66c6c;");
    }

    @FXML
    private void changeEmailColorWhenLeft() {
        emailBox.setStyle("-fx-background-color: transparent;");
    }

    @FXML
    private void changeGithubColorWhenHovered() {
        githubBox.setStyle("-fx-background-color: #c66c6c;");
    }

    @FXML
    private void changeGithubColorWhenLeft() {
        githubBox.setStyle("-fx-background-color: transparent;");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
