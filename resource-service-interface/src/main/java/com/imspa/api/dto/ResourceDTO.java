package com.imspa.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-09 18:29
 */
@Data
public class ResourceDTO implements Serializable {
    private String id;
    private String name;
    private LocalDateTime time;
}
