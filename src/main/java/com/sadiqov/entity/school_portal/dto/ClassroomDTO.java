package com.sadiqov.entity.school_portal.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Data
public class ClassroomDTO {
    @NotBlank(message = "Name is required")
    String name;

    @NotNull(message = "Teacher ID is required")
    Long teacherId;
}