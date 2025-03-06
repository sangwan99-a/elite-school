package com.elite.school.util;

import java.net.http.HttpHeaders;

@FunctionalInterface
public interface AuthCustomizer {

    void customize(HttpHeaders headers);
}
