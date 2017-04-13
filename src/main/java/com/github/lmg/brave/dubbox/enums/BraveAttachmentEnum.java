package com.github.lmg.brave.dubbox.enums;

/**
 * Created by liaomengge on 17/4/13.
 */
public enum BraveAttachmentEnum {

    Sampled, ParentId, TraceId, SpanId;

    public String getName() {
        return this.name().toLowerCase();
    }
}
