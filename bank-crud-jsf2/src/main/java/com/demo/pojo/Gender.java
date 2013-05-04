/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.pojo;

public enum Gender
{
    MALE(0, "MALE"),
    FEMALE(1, "FEMALE");

    public static Gender fromId(int id) throws IllegalArgumentException
    {
        for (Gender e : values())
        {
            if (e.id == id)
            {
                return e;
            }
        }
        throw new IllegalArgumentException(String.format("Gender with id %s doesn't exists", id));
    }

    public static Gender fromLabel(String label) throws IllegalArgumentException
    {
        for (Gender e : values())
        {
            if (e.label.equalsIgnoreCase(label))
            {
                return e;
            }
        }
        throw new IllegalArgumentException(String.format("Gender with label %s doesn't exists", label));
    }
    private final int id;
    private final String label;

    private Gender(int id, String label)
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