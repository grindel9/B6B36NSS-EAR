package cz.cvut.ear.sem.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Authority {

    ADMIN("ADMIN"), TEACHER("TEACHER"), STUDENT("STUDENT");

    private final String name;
}
