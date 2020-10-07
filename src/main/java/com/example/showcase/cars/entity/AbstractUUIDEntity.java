package com.example.showcase.cars.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class AbstractUUIDEntity implements Persistable<UUID> {

    @Id
    @Column(name="id", length = 16, unique = true, nullable = false)
    @Builder.Default
    @EqualsAndHashCode.Include
    private UUID id = UUID.randomUUID();

    @Transient
    @Builder.Default
    private boolean persisted = false;

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
