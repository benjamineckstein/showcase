package com.github.benjamineckstein.showcase.util;

import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import lombok.ToString;

@ToString
public class Testcase1 {

  public final Expertise expertise;

  public Testcase1(Expertise expertise) {
    this.expertise = expertise;
  }
}
