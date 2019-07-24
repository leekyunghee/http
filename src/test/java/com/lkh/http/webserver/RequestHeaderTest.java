package com.lkh.http.webserver;

import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderTest {
    // @Date 190720(토)
    // @Date @TODO 190721(일) 하드코딩 하지 말기. 패턴 뽑아내기
    // 요구사항 1. HTTP 요청 파싱 하기 TDD
    // input과 output을 지정
    @Test
    public void RequestLine_get_요청_파싱() {
        String header = "GET /users HTTP/1.1";     // 메소드, URL, Version
        String[] requestLine = HttpParser.getRequestLine(header);
        assertThat(requestLine).isEqualTo(new String[] {"GET", "/users", "HTTP/1.1"});
    }

    @Test
    public void RequestLine_post_요청_파싱() {
        String header = "POST /users HTTP/1.1";
        String[] requestLine = HttpParser.postRequestLine(header);
        assertThat(requestLine).isEqualTo(new String[] {"POST", "/users", "HTTP/1.1"});
    }

    // 요구사항 2. HTTP 요청의 Query String과 Body로 전달되는 데이터를 파싱한다.
    @Test
    public void Request_queryString_요청_파싱() {
        String queryString = "GET /users?userId=javajigi HTTP/1.1";

        // key, value로 변환 해줄 것
        // map에 배열 넣기
        Map< String, String> result = HttpParser.getQueryString(queryString);

        assertThat(result.get("userId")).isEqualTo("javajigi");
    }

    @Test
    public void Request_body_요청_파싱() {

        StringBuilder sb = new StringBuilder();
        sb.append("POST /users HTTP/1.1 " + "\n");
        sb.append("Host: localhost:8080 " + "\n");
        sb.append("Connection: Keep-alive " + "\n");
        sb.append("Content-Length: 46 " + "\n");
        sb.append("Content-Type: application/x-www-form-urlencoded " + "\n");
        sb.append("Accept: */* " + "\n");
        sb.append("userId=javajigi&password=password&name=Jaesung");
        String requestBody = sb.toString();

        // body에서 key, value 뽑아내기
        Map<String, String> result = HttpParser.postRequestBody(requestBody);

        assertThat(result.get("userId")).isEqualTo("javajigi");
        assertThat(result.get("password")).isEqualTo("password");
        assertThat(result.get("name")).isEqualTo("Jaesung");
    }
}
