package com.github.benjamineckstein.showcase.common;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import java.util.UUID;

@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class AbstractUuidEntity implements Persistable<UUID> {

  @Id
  @Column(name = "id", length = 16, unique = true, nullable = false)
  @Builder.Default
  @EqualsAndHashCode.Include
  @NonNull
  private UUID id = UUID.randomUUID();

  @Transient @Builder.Default
  // @Setter(AccessLevel.NONE)
  private boolean persisted = false;

  @Version @Column private Integer version;

  @PostPersist
  @PostLoad
  private void setPersisted() {
    persisted = true;
  }

  @Override
  public boolean isNew() {
    return !persisted;
  }
}
