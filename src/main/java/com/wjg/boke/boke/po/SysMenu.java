package com.wjg.boke.boke.po;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_menu
 * @author 
 */
@Data
public class SysMenu implements Serializable {
    private Integer menuId;

    private String menuName;

    private static final long serialVersionUID = 1L;
}