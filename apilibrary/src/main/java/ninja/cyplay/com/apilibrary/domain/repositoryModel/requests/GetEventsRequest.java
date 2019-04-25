package ninja.cyplay.com.apilibrary.domain.repositoryModel.requests;

import java.util.Date;

/**
 * Created by romainlebouc on 26/04/16.
 */
public class GetEventsRequest {

    private final String sellerId;
    private final Date after;
    private final Date before;
    private final String attendeeId;
    private final String attendeeType;

    public GetEventsRequest(String sellerId,
                            Date after,
                            Date before,
                            String attendeeId,
                            String attendeeType) {
        this.sellerId = sellerId;
        this.after = after;
        this.before = before;
        this.attendeeId = attendeeId;
        this.attendeeType = attendeeType;
    }

    public GetEventsRequest(String sellerId,
                            Date after,
                            Date before) {
        this(sellerId, after, before, null, null);
    }

    public GetEventsRequest(String attendeeId,
                            String attendeeType) {
        this(null, null, null, attendeeId, attendeeType);
    }

    public String getSellerId() {
        return sellerId;
    }

    public Date getAfter() {
        return after;
    }

    public Date getBefore() {
        return before;
    }

    public String getAttendeeId() {
        return attendeeId;
    }

    public String getAttendeeType() {
        return attendeeType;
    }
}
