package com.imspa.api.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-12 17:21
 */
@Data
public class SearchUserRequest {
    @NotEmpty
    private String name;
    @NotNull
    private Integer pageIndex = 0;
    @NotNull
    private Integer pageSize = 15;
}
