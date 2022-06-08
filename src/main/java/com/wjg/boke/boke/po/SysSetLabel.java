package com.wjg.boke.boke.po;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_set_label
 * @author 
 */
@Data
public class SysSetLabel implements Serializable {
    /**
     * 文章ID
     */
    private Integer wzId;

    /**
     * 标签ID
     */
    private Integer bqId;

    private static final long serialVersionUID = 1L;
}