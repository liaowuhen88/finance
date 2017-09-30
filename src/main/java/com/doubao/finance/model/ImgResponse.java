package com.doubao.finance.model;

public class ImgResponse
{
    private String fileType;
    private String userType;
    private String id;
    private String date;
    private String childFileType;
    private String src;
    private String path;

    public String getSrc()
    {
        return this.src;
    }

    public void setSrc(String src)
    {
        this.src = src;
    }

    public String getPath()
    {
        return this.path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getChildFileType()
    {
        return this.childFileType;
    }

    public void setChildFileType(String childFileType)
    {
        this.childFileType = childFileType;
    }

    public String getDate()
    {
        return this.date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getUserType()
    {
        return this.userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }

    public String getFileType()
    {
        return this.fileType;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}

