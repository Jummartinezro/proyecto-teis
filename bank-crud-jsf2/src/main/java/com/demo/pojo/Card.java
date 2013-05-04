/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.pojo;

public enum Card
{
    MASTERCARD(0, "MASTERCARD"),
    VISA(1, "VISA"),
    AMEX(2, "AMEX"),
    DISCOVER(3, "DISCOVER"),
    ENROUTE(4, "ENROUTE"),
    JCB(5, "JCB");

    public static Card fromId(int id) throws IllegalArgumentException
    {
        for (Card e : values())
        {
            if (e.id == id)
            {
                return e;
            }
        }
        throw new IllegalArgumentException(String.format("Card with id %s doesn't exists", id));
    }

    public static Card fromLabel(String label) throws IllegalArgumentException
    {
        for (Card e : values())
        {
            if (e.label.equalsIgnoreCase(label))
            {
                return e;
            }
        }
        throw new IllegalArgumentException(String.format("Card with label %s doesn't exists", label));
    }
    private final int id;
    private final String label;

    private Card(int id, String label)
    {
        this.id = id;
        this.label = label;
    }

    public int getId()
    {
        return this.id;
    }

    public String getLabel()
    {
        return this.label;
    }

    @Override
    public String toString()
    {
        return this.id + ":" + this.label;
    }
}