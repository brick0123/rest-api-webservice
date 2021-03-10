package com.woodcock.events;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EventTest {

  @Test
  void builder() {
    Event event = Event.builder()
        .build();

    assertThat(event).isNotNull();
  }

  @Test
  void javaBean() {
    // given
    String name = "event";
    String description = "test";

    // when
    Event event = new Event();
    event.setName(name);
    event.setDescription(description);

    // then
    assertThat(event.getName()).isEqualTo(name);
    assertThat(event.getDescription()).isEqualTo(description);
  }
}