package com.smartmug.kafka.configuration;

public enum CompressionType {
    NONE("none"),
    GZIP("gzip"),
    SNAPPY("snappy"),
    LZ4("lz4"),
    ZSTD("zstd");

    private String value;

    CompressionType(final String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
