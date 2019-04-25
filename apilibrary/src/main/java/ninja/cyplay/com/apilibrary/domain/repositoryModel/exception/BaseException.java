package ninja.cyplay.com.apilibrary.domain.repositoryModel.exception;

import ninja.cyplay.com.apilibrary.domain.repositoryModel.responses.BaseResponse;

/**
 * Created by damien on 28/04/15.
 */
public class BaseException extends RuntimeException {

    private BaseResponse response;
    private ExceptionType type;

    public BaseResponse getResponse() {
        return response;
    }

    public void setResponse(BaseResponse response) {
        this.response = response;
        this.type = ExceptionType.BUSINESS;
    }

    public void setAuthenticationResponse(BaseResponse response){
        this.response = response;
        this.type = ExceptionType.AUTHENTICATION;
    }

    public void setStackTrace(StackTraceElement[] trace){
        super.setStackTrace(trace);
        this.type = ExceptionType.TECHNICAL;
    }

    public void setType(ExceptionType type){
         this.type = type;
    }
    public ExceptionType getType(){
        return this.type;
    }

}
