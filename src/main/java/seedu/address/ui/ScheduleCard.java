package seedu.address.ui;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person} in the viewed version.
 */
public class ScheduleCard extends UiPart<Region> {

    private static final String FXML = "ScheduleCard.fxml";
    private static final String SCHEDULE_HEADER = "%1$s's Full Schedule";
    private static final String UPCOMING_SCHEDULE_HEADER = "%1$s's Upcoming Schedule";
    private static final String NO_SCHEDULE_MESSAGE = "%1$s doesn't have any Schedule recorded yet.";
    private static final String DAILY_SCHEDULE_HEADER = "%1$s, %2$s";

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox cardRows;
    @FXML
    private Label name;
    @FXML
    private Label scheduleHeader;
    @FXML
    private HBox scheduleBox;
    @FXML
    private Label upcomingScheduleHeader;
    @FXML
    private HBox upcomingScheduleBox;
    @FXML
    private Label headerDay0;
    @FXML
    private HBox scheduleDay0;
    @FXML
    private Label headerDay1;
    @FXML
    private HBox scheduleDay1;
    @FXML
    private Label headerDay2;
    @FXML
    private HBox scheduleDay2;
    @FXML
    private Label headerDay3;
    @FXML
    private HBox scheduleDay3;
    @FXML
    private Label headerDay4;
    @FXML
    private HBox scheduleDay4;
    @FXML
    private Label headerDay5;
    @FXML
    private HBox scheduleDay5;
    @FXML
    private Label headerDay6;
    @FXML
    private HBox scheduleDay6;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ScheduleCard(Person person) {
        super(FXML);

        this.person = person;
        name.setText(person.getName().value);
        scheduleHeader.setText(String.format(SCHEDULE_HEADER, person.getName().value));
        upcomingScheduleHeader.setText(String.format(UPCOMING_SCHEDULE_HEADER, person.getName().value));

        headerDay0.setText(String.format(DAILY_SCHEDULE_HEADER, getDayOfWeekAfter(0), getDateAfter(0)));
        headerDay1.setText(String.format(DAILY_SCHEDULE_HEADER, getDayOfWeekAfter(1), getDateAfter(1)));
        headerDay2.setText(String.format(DAILY_SCHEDULE_HEADER, getDayOfWeekAfter(2), getDateAfter(2)));
        headerDay3.setText(String.format(DAILY_SCHEDULE_HEADER, getDayOfWeekAfter(3), getDateAfter(3)));
        headerDay4.setText(String.format(DAILY_SCHEDULE_HEADER, getDayOfWeekAfter(4), getDateAfter(4)));
        headerDay5.setText(String.format(DAILY_SCHEDULE_HEADER, getDayOfWeekAfter(5), getDateAfter(5)));
        headerDay6.setText(String.format(DAILY_SCHEDULE_HEADER, getDayOfWeekAfter(6), getDateAfter(6)));

        if ((!person.getSchedule().isEmpty())) {
            Label scheduleLabel = new Label(person.getSchedule().toString());
            scheduleLabel.setWrapText(true);
            scheduleBox.getChildren().add(scheduleLabel);
        } else {
            Label scheduleLabel = new Label(String.format(NO_SCHEDULE_MESSAGE,
                    person.getName().value));
            scheduleLabel.setWrapText(true);
            scheduleBox.getChildren().add(scheduleLabel);
        }

        List<HBox> dailySchedule = Arrays.asList(
            scheduleDay0, scheduleDay1, scheduleDay2, scheduleDay3, scheduleDay4, scheduleDay5, scheduleDay6);

        for (int i = 0; i < 7; i++) {
            if (!person.getUpcomingSchedule(i).isEmpty()) {
                Label upcomingScheduleLabel = new Label(person.getUpcomingSchedule(i).getDailyScheduleFormat());
                upcomingScheduleLabel.setWrapText(true);
                dailySchedule.get(i).getChildren().add(upcomingScheduleLabel);
            } else {
                Label upcomingScheduleLabel = new Label("-");
                upcomingScheduleLabel.setWrapText(true);
                dailySchedule.get(i).getChildren().add(upcomingScheduleLabel);
            }
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Gets the string representation of DayOfWeek {@code daysForward} days after today.
     */
    public String getDayOfWeekAfter(int daysForward) {
        return LocalDate.now().plusDays(daysForward).getDayOfWeek().toString();
    }

    /**
     * Gets the string representation of the date of {@code daysForward} days after today.
     */
    public String getDateAfter(int daysForward) {
        return LocalDate.now().plusDays(daysForward).toString();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCard)) {
            return false;
        }

        // state check
        ScheduleCard card = (ScheduleCard) other;
        return person.equals(card.person);
    }
}
