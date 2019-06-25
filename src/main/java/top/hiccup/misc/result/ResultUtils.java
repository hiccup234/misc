package top.hiccup.misc.result;

/**
 * 通用Controller返回工具类
 *
 * @author wenhy
 * @date 2018/6/18
 */
public class ResultUtils {

    public static <T> Result<T> success() {
        // 仅标识接口成功，不返回业务内容content
        return new Result<>(Result.Status.SUCCESS.code());
    }

    public static <T> Result<T> success(T content) {
        return new Result.SuccessResult<>(content);
    }

    public static <T> Result<T> error(String errorCode, String errorMsg) {
        return new Result.ErrorResult<>(errorCode, errorMsg);
    }
}