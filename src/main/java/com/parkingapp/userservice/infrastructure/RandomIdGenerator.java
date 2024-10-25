package com.parkingapp.userservice.infrastructure;

import com.parkingapp.userservice.domain.user.common.IdGenerator;

import java.util.UUID;

public class RandomIdGenerator implements IdGenerator {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }

}
