package com.lkh.http.webserver;

import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpParserTest {

    /**
     * @Since 190720(토)
     * @Description Input과 Output을 지정
     * @TODO 190721(일) 하드코딩 제거
     * @TODO 190725(목) 패턴 뽑아내기
     * @TODO 190728(일) Extract method
     */

    enum HTTP_METHOD {
        GET,
        POST,
        DELETE,
        PUT,
        TRACE,
        HEAD
    }

    // 요구사항 1. HTTP 요청 파싱 하기 TDD
    // 테스트 순서
    // 1. Input 데이터 준비
    // 2. 실행
    // 3. 예측 비교
    @Test
    public void RequestLine_get_요청_파싱() {
        String header = "GET /users HTTP/1.1";     // 메소드, URL, Version
        String[] requestLine = HttpParser.getRequestLine(header);
        assertThat(requestLine).isEqualTo(new String[] {"GET", "/users", "HTTP/1.1"});
    }

    @Test
    public void RequestLine_post_요청_파싱() {
        String header = "POST /users HTTP/1.1";
        String[] requestLine = HttpParser.getRequestLine(header);
        assertThat(requestLine).isEqualTo(new String[] {"POST", "/users", "HTTP/1.1"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void requestline_when_no_data_throw_exception() {
        String request = null;
        HttpParser.getRequestLine(request);
    }

    // 요구사항 2. HTTP 요청의 Query String과 Body로 전달되는 데이터를 파싱한다.
    @Test
    public void Request_queryString_요청_파싱() {
        String queryString = "GET /users?userId=javajigi&pwd=1234 HTTP/1.1";    // parameter 가 추가되었을 경우

        // Map 에 배열 넣기
        Map< String, String> result = HttpParser.getRequestParameters(queryString);

        assertThat(result.get("userId")).isEqualTo("javajigi");
        assertThat(result.get("pwd")).isEqualTo("1234");
    }

    @Test
    public void Request_body_요청_파싱() {

        String requestBody = postData();

        // HTTP Body 에서 key, value 뽑아내기
        Map<String, String> result = HttpParser.getRequestParameters(requestBody);

        assertThat(result.get("userId")).isEqualTo("javajigi");
        assertThat(result.get("password")).isEqualTo("password");
        assertThat(result.get("name")).isEqualTo("kyunghee");
    }

    @Test
    public void Request_body_요청_파싱2() {

        String requestBody = postData2();

        Map<String, String> result = HttpParser.getRequestParameters(requestBody);

        assertThat(result.get("userId")).isEqualTo("javajigi");
        assertThat(result.get("password")).isEqualTo("password");
        assertThat(result.get("name")).isEqualTo("kyunghee");
    }

    private String postData() {
        StringBuilder sb = new StringBuilder();
        sb.append("POST /users HTTP/1.1 " + "\n");
        sb.append("Host: localhost:8080 " + "\n");
        sb.append("Connection: Keep-alive " + "\n");
        sb.append("Content-Length: 46 " + "\n");
        sb.append("Content-Type: application/x-www-form-urlencoded " + "\n");
        sb.append("Accept: */* " + "\n");
        sb.append("\n");
        sb.append("userId=javajigi&password=password&name=kyunghee");
        return sb.toString();
    }

    private String postData2() {
        StringBuilder sb = new StringBuilder();
        sb.append("POST /users HTTP/1.1 " + "\n");
        sb.append("Host: localhost:8080 " + "\n");
        sb.append("Connection: Keep-alive " + "\n");
        sb.append("Content-Length: 46 " + "\n");
        sb.append("Content-Type: application/x-www-form-urlencoded " + "\n");
        sb.append("Accept: */* " + "\n");
        sb.append("Cookies: hi" + "\n");        // body 내용이 추가 되었을 경우
        sb.append("\n");                        // 개행 문자 이후 body 의 parameter 를 확인
        sb.append("userId=javajigi&password=password&name=kyunghee");
        return sb.toString();
    }
}
