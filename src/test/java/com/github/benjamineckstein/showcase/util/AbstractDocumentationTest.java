package com.github.benjamineckstein.showcase.util;

import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@MySpringBootTest
public abstract class AbstractDocumentationTest {

  @Autowired protected TestcaseGenerator testcaseGenerator;
  @Autowired private WebApplicationContext webApplicationContext;
  protected MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  protected String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
