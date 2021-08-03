package com.company.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审批状态码（枚举状态机）
 * 例如：在一个请假单的审批过程中肯定有这几种状态<发起审批，组长审批，经理审批，人事备案>
 * 在枚举类中定义抽象方法，并且在每个状态中进行具体的实现。
 * 如此在有大量的状态转移的场景中（固定的审批场景，支付场景），
 * 当前状态调用nextStatus()方法获取下一个状态。这种写法可以使得代码更加简洁干净，更加便于维护。
 *
 * @author qianshuailong
 * @date 2021/4/5
 */
@Getter
@AllArgsConstructor
public enum ApprovalStatusEnum {
    // 审批状态
    START(1, "开始审批") {
        @Override
        ApprovalStatusEnum getNextStatus() {
            return FIRST_LEADER;
        }
    },
    FIRST_LEADER(2, "第一个领导审批") {
        @Override
        ApprovalStatusEnum getNextStatus() {
            return SECOND_LEADER;
        }
    },
    SECOND_LEADER(3, "第二个领导审批") {
        @Override
        ApprovalStatusEnum getNextStatus() {
            return BACKUPS;
        }
    },
    BACKUPS(4, "备案") {
        @Override
        ApprovalStatusEnum getNextStatus() {
            return null;
        }
    };

    private final Integer code;
    private final String msg;

    /**
     * 获取下一个状态
     *
     * @return 状态
     */
    abstract ApprovalStatusEnum getNextStatus();

}
