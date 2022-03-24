package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Schedule;

/**
 * An UI component that displays information of a {@code Person} in the viewed version.
 */
public class PersonViewCard extends UiPart<Region> {

    private static final String FXML = "PersonViewCard.fxml";

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
    private Label phone;
    @FXML
    private HBox scheduleBox;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonViewCard(Person person) {
        super(FXML);
        ImageView scheduleIcon = new ImageView("/images/schedule_icon.png");
        scheduleIcon.setFitHeight(16);
        scheduleIcon.setFitWidth(16);

        this.person = person;
        name.setText(String.format("%1$s's Schedule", person.getName().value));
        phone.setText(person.getPhone().value);

        if (!Schedule.isEmptySchedule(person.getSchedule())) {
            scheduleBox.getChildren().add(scheduleIcon);
            Label scheduleLabel = new Label(person.getSchedule().toString());
            scheduleBox.getChildren().add(scheduleLabel);
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonViewCard)) {
            return false;
        }

        // state check
        PersonViewCard card = (PersonViewCard) other;
        return person.equals(card.person);
    }
}
