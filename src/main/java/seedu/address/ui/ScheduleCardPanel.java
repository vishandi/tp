package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the viewed list of persons.
 */
public class ScheduleCardPanel extends UiPart<Region> {
    private static final String FXML = "ScheduleCardPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ScheduleCardPanel.class);

    @FXML
    private ListView<Person> viewedPersonListView;

    /**
     * Creates a {@code ScheduleCardPanel} with the given {@code ObservableList}.
     */
    public ScheduleCardPanel(ObservableList<Person> personList) {
        super(FXML);
        viewedPersonListView.setItems(personList);
        viewedPersonListView.setCellFactory(listView -> new ViewedPersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ViewedPersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ScheduleCard(person).getRoot());
            }
        }
    }

}
