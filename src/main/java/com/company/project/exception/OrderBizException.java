package com.company.project.exception;

import com.company.project.model.BaseCommonError;

/**
 * 订单业务异常（继承 BaseBizException）
 *
 * @author DanielQSL
 */
public class OrderBizException extends BaseBizException {

    public OrderBizException(Integer errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public OrderBizException(String errorMsg) {
        super(errorMsg);
    }

    public OrderBizException(Integer errorCode, String errorMsg, Object... arguments) {
        super(errorCode, errorMsg, arguments);
    }

    public OrderBizException(BaseCommonError baseCommonError) {
        super(baseCommonError);
    }

    public OrderBizException(BaseCommonError baseCommonError, String errorMsg) {
        super(baseCommonError, errorMsg);
    }

    public OrderBizException(BaseCommonError baseCommonError, Object... arguments) {
        super(baseCommonError, arguments);
    }

}
