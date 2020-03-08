package org.geeksword.service.bean;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum IdType {
    MAX_PEEK("max-peek"),
    MIN_GRANULARITY("min-granularity");

    private final String name;

    public long value() {
        int type = 0;
        switch (this) {
            case MIN_GRANULARITY:
                type = 1;
                break;
            default:
                break;
        }
        return type;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static IdType parse(String name) {
        if ("max-peek".equals(name)) {
            return MAX_PEEK;
        } else if ("min-granularity".equals(name)) {
            return MIN_GRANULARITY;
        }
        return null;
    }

    public static IdType parse(long type) {
        if (type == 1) {
            return MIN_GRANULARITY;
        } else if (type == 0) {
            return MAX_PEEK;
        }
        return null;
    }
}
