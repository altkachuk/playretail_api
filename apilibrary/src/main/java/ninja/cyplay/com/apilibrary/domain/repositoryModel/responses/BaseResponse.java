package ninja.cyplay.com.apilibrary.domain.repositoryModel.responses;

import ninja.cyplay.com.apilibrary.utils.StringUtils;

/**
 * Created by damien on 28/04/15.
 */
public class BaseResponse {

    protected String status;
    protected String detail;

    public BaseResponse() {

    }

    public BaseResponse(String status, String detail) {
        this.status = status;
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        String[] s = {status, detail};
        return StringUtils.join(s, " ");
    }
}
