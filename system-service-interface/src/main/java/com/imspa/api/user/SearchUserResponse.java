package com.imspa.api.user;

import lombok.Data;

import java.util.List;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-12 17:21
 */
@Data
public class SearchUserResponse {
    private List<UserVO> users;
    private Integer totalRecord;
    private Integer totalPage;
}
