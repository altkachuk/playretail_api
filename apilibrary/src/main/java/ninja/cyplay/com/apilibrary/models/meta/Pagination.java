package ninja.cyplay.com.apilibrary.models.meta;

/**
 * Created by romainlebouc on 17/08/16.
 */
public class Pagination {

    public final static int DEFAULT_PAGINATION_SIZE = 30;
    private final static Pagination INITIAL_PAGINATION = new Pagination(0, DEFAULT_PAGINATION_SIZE);

    private final int offset;
    private final int limit;

    public Pagination(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isExtraData(){
        return offset >0;
    }

    public static Pagination getInitialPagingation(){
        return INITIAL_PAGINATION;
    }
}
