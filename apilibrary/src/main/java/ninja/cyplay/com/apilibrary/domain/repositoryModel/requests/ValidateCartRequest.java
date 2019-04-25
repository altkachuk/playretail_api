package ninja.cyplay.com.apilibrary.domain.repositoryModel.requests;

/**
 * Created by damien on 27/01/16.
 */
public class ValidateCartRequest<Resource>  {

    private String cutomerId;
    private String comment;
    private Resource body;

    public ValidateCartRequest(String cutomerId, String comment) {
        this.cutomerId = cutomerId;
        this.comment = comment;
    }


    public String getCustomerId() {
        return cutomerId;
    }

    public void setCustomerId(String cutomerId) {
        this.cutomerId = cutomerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
