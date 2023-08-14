package kr.jay.r2dbcprac.common.repository;

import lombok.Data;

@Data
public class ImageEntity {
    private final String id;
    private final String name;
    private final String url;
}