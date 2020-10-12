package com.github.benjamineckstein.showcase.util;

import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class Testhelper {

    public static <T> T getBody(ResponseEntity<T> response) {
      assertThat(response.getBody()).isNotNull();
      return response.getBody();
    }
}
