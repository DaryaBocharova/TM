package ru.bocharova.se.api.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.bocharova.se.entity.Domain;

@Service
public interface IDomainService {

    void load(@Nullable final Domain domain);

    void export(@Nullable final Domain domain);

}
