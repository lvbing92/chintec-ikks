package com.chintec.ikks.common.util;

import com.chintec.ikks.common.entity.response.AuthorityMenuResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/22 14:29
 */
public class MenuTree {

    public static List<AuthorityMenuResponse> getMenuTrees(List<AuthorityMenuResponse> menus) {
        List<AuthorityMenuResponse> collect = menus.stream().filter(m -> m.getParentId() == 0).map((m) -> {
            m.setChildList(getChildrenList(m, menus));
            return m;
        }).collect(Collectors.toList());
        return collect;
    }

    public static List<AuthorityMenuResponse> getChildrenList(AuthorityMenuResponse root, List<AuthorityMenuResponse> menus) {

        List<AuthorityMenuResponse> children = menus.stream().filter(m -> {
            return Objects.     equals(m.getParentId(), root.getMenuId());
        }).map((m) -> {
            m.setChildList(getChildrenList(m, menus));
            return m;
        }).collect(Collectors.toList());
        return children;
    }
}
