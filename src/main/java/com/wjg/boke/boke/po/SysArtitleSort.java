package com.wjg.boke.boke.po;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_artitle_sort
 * @author 
 */
@Data
public class SysArtitleSort implements Serializable {
    private Integer articleId;

    private Integer sortId;

    private static final long serialVersionUID = 1L;
}