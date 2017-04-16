package com.github.lmg.brave.enums;

/**
 * Created by liaomengge on 17/4/16.
 */
public enum ProtocolEnum {

    REST, DUBBO, THRIFT, THRIFT2;

    public String getName() {
        return this.name().toLowerCase();
    }
}
