package com.ruoyi.media.exception;

/**
 * 媒体模块操作异常
 * 
 * @author ruoyi
 */
public class MediaOperationException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    public MediaOperationException(String message)
    {
        this.message = message;
    }

    public MediaOperationException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }
} 