package com.imspa.web.auth;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-12 15:14
 */
public class PermissionUtil {
    private volatile static List<String> allPermissionUrlItem = null;

    public static List<String> getAllPermissionUrlItem() {
        if (allPermissionUrlItem == null) {
            synchronized (PermissionUtil.class) {
                if (allPermissionUrlItem == null) {
                    allPermissionUrlItem = Arrays.stream(Permissions.class.getEnumConstants()).map(Permissions::getUrl).collect(Collectors.toList());
                }
            }
        }
        return allPermissionUrlItem;
    }

}
