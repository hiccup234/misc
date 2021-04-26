package top.hiccup.misc.result;

import java.io.Serializable;

/**
 * MVC接口常用返回结果类
 *
 * @author wenhy
 * @date 2019/6/24
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -1L;

    private String status;

    public Result(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public enum Status {
        SUCCESS("OK"),
        ERROR("ERROR");

        private String code;

        Status(String code) {
            this.code = code;
        }

        public String code() {
            return this.code;
        }
    }

    public static class SuccessResult<T> extends Result<T> {
        private T content;

        public SuccessResult(T content) {
            super(Status.SUCCESS.code);
            this.content = content;
        }

        public T getContent() {
            return content;
        }

        public void setContent(T content) {
            this.content = content;
        }
    }

    public static class ErrorResult<T> extends Result<T> {
        private String errorCode;
        private String errorMsg;

        public ErrorResult(String errorCode, String errorMsg) {
            super(Status.ERROR.code);
            this.errorCode = errorCode;
            this.errorMsg = errorMsg;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }
}
