package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalSchedule.getTypicalSchedule;

import org.junit.jupiter.api.Test;

import seedu.address.model.schedule.Schedule;

public class JsonAdaptedScheduleTest {

    private static final Schedule VALID_SCHEDULE = getTypicalSchedule();

    @Test
    public void toModelType_validScheduleDetails_returnsSchedule() throws Exception {
        JsonAdaptedSchedule schedule = new JsonAdaptedSchedule(VALID_SCHEDULE);
        assertEquals(VALID_SCHEDULE, schedule.toModelType());
    }
}
