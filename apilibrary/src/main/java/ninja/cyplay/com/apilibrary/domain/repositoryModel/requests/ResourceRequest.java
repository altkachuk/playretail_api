package ninja.cyplay.com.apilibrary.domain.repositoryModel.requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import ninja.cyplay.com.apilibrary.utils.StringUtils;

/**
 * Created by romainlebouc on 14/08/16.
 */
public class ResourceRequest<Resource> {

    private final static AtomicInteger requestIdGenerator = new AtomicInteger();

    protected final static String FIELDS_KEY = "fields";
    protected final static String PAGINATE_KEY = "paginate";
    protected final static String PAGINATE_OFFSET_KEY = "offset";
    protected final static String PAGINATE_LIMIT_KEY = "limit";
    protected final static String SORT_BY_KEY = "sort_by";
    protected final static String SEARCH_KEY = "q";

    private final int requestId;

    private String id;
    private String secondLevelId;
    private String thirdLevelId;

    protected final Map<String, String> params = new HashMap<>();
    private Resource body;
    protected final Map<String, List<String>> multiParams = new HashMap<>();

    public ResourceRequest() {
        this.requestId = requestIdGenerator.incrementAndGet();
    }

    public ResourceRequest id(String id) {
        this.id = id;
        return this;
    }

    public ResourceRequest secondLevelId(String secondLevelId) {
        this.secondLevelId = secondLevelId;
        return this;
    }

    public ResourceRequest thirdLevelId(String thirdLevelId) {
        this.thirdLevelId = thirdLevelId;
        return this;
    }

    public ResourceRequest filter(Map<String, String> filters) {
        if (filters != null) {
            params.putAll(filters);
        }
        return this;
    }

    public ResourceRequest filter(String key, String value) {
        params.put(key, value);
        return this;
    }

    public ResourceRequest filter(String key, List<String> values) {
        if (key != null && values != null && !values.isEmpty()) {
            List<String> multiParamsValues = multiParams.get(key);
            if (multiParamsValues == null) {
                multiParamsValues = new ArrayList<>();
                multiParams.put(key, multiParamsValues);
            }
            multiParamsValues.addAll(values);
        }
        return this;
    }

    public ResourceRequest project(List<String> fields) {
        if (fields != null && !fields.isEmpty()) {
            params.put(FIELDS_KEY, StringUtils.join(fields.toArray(), ","));
        }
        return this;
    }

    public ResourceRequest paginate(boolean paginate) {
        params.put(PAGINATE_KEY, String.valueOf(paginate));
        return this;
    }

    public ResourceRequest paginate(int offset, int limit) {
        if (offset >= 0 && limit >= 0) {
            params.put(PAGINATE_OFFSET_KEY, String.valueOf(offset));
            params.put(PAGINATE_LIMIT_KEY, String.valueOf(limit));
        }
        return this;
    }

    public ResourceRequest sort(String sort) {
        if (sort != null && sort.length() > 0) {
            params.put(SORT_BY_KEY, sort);
        }
        return this;
    }

    public ResourceRequest sort(List<String> sortBys) {
        if (sortBys != null && !sortBys.isEmpty()) {
            params.put(SORT_BY_KEY, StringUtils.join(sortBys.toArray(), ","));
        }
        return this;
    }

    public ResourceRequest search(String query) {
        if (query != null && query.length() > 0) {
            params.put(SEARCH_KEY, query);
        }
        return this;
    }

    public ResourceRequest body(Resource body) {
        this.body = body;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getSecondLevelId() {
        return secondLevelId;
    }

    public String getThirdLevelId() {
        return thirdLevelId;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Resource getBody() {
        return body;
    }

    public Map<String, List<String>> getMultiParams() {
        return multiParams;
    }

    public int getRequestId() {
        return requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourceRequest<?> that = (ResourceRequest<?>) o;

        return requestId == that.requestId;

    }

    @Override
    public int hashCode() {
        return requestId;
    }
}
