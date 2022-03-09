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
import seedu.address.model.schedule.Schedule;

/**
 * An UI component that displays information of a {@code Person}.
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
    private HBox addressBox;
    @FXML
    private HBox emailBox;
    @FXML
    private HBox scheduleBox;
    @FXML
    private FlowPane tags;



    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        ImageView addressIcon = new ImageView("./images/address_icon.png");
        ImageView emailIcon = new ImageView("./images/email_icon.png");
        ImageView scheduleIcon = new ImageView("./images/schedule_icon.png");
        ImageView telegramIcon = new ImageView("./images/telegram_icon.png");
        addressIcon.setFitHeight(16);
        addressIcon.setFitWidth(16);
        emailIcon.setFitHeight(16);
        emailIcon.setFitWidth(16);
        scheduleIcon.setFitHeight(16);
        scheduleIcon.setFitWidth(16);
        telegramIcon.setFitHeight(16);
        telegramIcon.setFitWidth(16);

        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        if (!person.getTelegram().isEmpty()) {
            telegramBox.getChildren().add(telegramIcon);
            Label telegramLabel = new Label(person.getTelegram().value);
            telegramBox.getChildren().add(telegramLabel);
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
        if (!Schedule.isEmptySchedule(person.getSchedule())) {
            scheduleBox.getChildren().add(scheduleIcon);
            Label scheduleLabel = new Label(person.getSchedule().toString());
            scheduleBox.getChildren().add(scheduleLabel);
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
    private void emailChangeColorWhenHovered() {
        emailBox.setStyle("-fx-background-color: #ff0000; ");
    }

    @FXML
    private void emailChangeColorWhenLeft() {
        emailBox.setStyle("-fx-background-color: transparent; ");
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
