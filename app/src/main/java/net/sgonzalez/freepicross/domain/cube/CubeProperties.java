package net.sgonzalez.freepicross.domain.cube;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class CubeProperties {
	
	//**************************************************//
    //**********     GLOBAL VARIABLES     **************//
	
	private boolean solid;
	
	private int xHint;
	private int yHint;
	private int zHint;
	
	private boolean shown;
	private boolean destroyed;

	private boolean marked;
	private boolean errored;
	
	private int id;
	
	//**************************************************//
    //**********     CONSTRUCTORS     ******************//
	
	public CubeProperties(boolean solid, int xHint, int yHint, int zHint) {
		
		this.solid = solid;
		
		this.xHint = xHint;
		this.yHint = yHint;
		this.zHint = zHint;
		
		this.shown = true;
		this.destroyed = false;
		
		this.marked = false;
		this.errored = false;
		
		this.id = -1;
	}
	
	//**************************************************//
    //**********     SETTERS AND GETTERS     ***********//
	
	public void    setShown(boolean shown)			{this.shown = shown;}
	public void    setDestroyed(boolean destroyed)	{this.destroyed = destroyed;}
	public void    setMarked(boolean marked) 		{this.marked = marked;}
	public void    setErrored(boolean errored) 		{this.errored = errored;}
	public void	   setID(int id)					{this.id = id;}
	public boolean getShown() 						{return this.shown;}
	public boolean getDestroyed() 					{return this.destroyed;}
	public boolean getMarked() 						{return this.marked;}
	public boolean getErrored() 					{return this.errored;}
	public boolean getSolid() 						{return this.solid;}
	public int     getXHint() 						{return this.xHint;}	
	public int     getYHint() 						{return this.yHint;}
	public int     getZHint() 						{return this.zHint;}
	public int	   getID()							{return this.id;}
}
