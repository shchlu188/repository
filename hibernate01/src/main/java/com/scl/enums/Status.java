package com.scl.enums;

/**
 * Created with IntelliJ IDEA.
 * User: chenglu
 * Date: 2019/11/21
 * Description:
 */
public enum Status {
    CONTINUE(100),
    PROCESSING(101),
    CHECKPOINT(102);
    private int code;

    Status(int code) {
        this.code = code;
    }
}
