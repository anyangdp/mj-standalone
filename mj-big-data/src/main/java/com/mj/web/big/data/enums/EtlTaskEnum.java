package com.mj.web.big.data.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EtlTaskEnum {
    SINGLE_TASK("单源任务"),
    MULTI_TASK("多源任务");
    private final String name;
}
