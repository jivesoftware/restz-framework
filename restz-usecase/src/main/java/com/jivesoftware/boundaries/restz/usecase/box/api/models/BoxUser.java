package com.jivesoftware.boundaries.restz.usecase.box.api.models;

/**
 * Created with IntelliJ IDEA.
 * User: bmoshe
 * Date: 1/16/13
 * Time: 7:45 PM
 */
public class BoxUser
extends BoxObject
{
    final String type = "user";

    private String name;
    private String login;

    public String getType()
    {
        return type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }
}
