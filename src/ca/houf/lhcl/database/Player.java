package ca.houf.lhcl.database;

public class Player
{
    private String name;
    private int goal;
    private int assist;

    public Player(final String n, final int g, final int a)
    {
        name = n;
        goal = g;
        assist = a;
    }

    public String getName()
    {
        return name;
    }
    public int getGoal()
    {
        return goal;
    }
    public int getAssist()
    {
        return assist;
    }
    public void setName(final String n)
    {
        this.name = n;
    }
    public void setGoal(final int g)
    {
        this.goal = g;
    }
    public void setAssist(final int a)
    {
        this.assist = a;
    }
}
