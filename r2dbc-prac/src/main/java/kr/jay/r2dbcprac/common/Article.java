package kr.jay.r2dbcprac.common;

import lombok.Data;

@Data
public class Article {
    private final String id;
    private final String title;
    private final String content;
}