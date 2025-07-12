package com.example.fth.fth_demo.validation;

import org.junit.jupiter.api.Test;

class ValidationGroupsTest {
    @Test
    void testGroupsExist() {
        Class<?> create = ValidationGroups.Create.class;
        Class<?> update = ValidationGroups.Update.class;
        assert create != null;
        assert update != null;
    }
}
