package com.osi.socialmedia.model;

public class GridBlock {
	public String gridImageName;
	public String gridText;
	/**
	 * @return the gridImageName
	 */
	public String getGridImageName() {
		return gridImageName;
	}
	public GridBlock(String gridImageName, String gridText) {
		this.gridImageName = gridImageName;
		this.gridText = gridText;
	}
	/**
	 * @param gridImageName the gridImageName to set
	 */
	public void setGridImageName(String gridImageName) {
		this.gridImageName = gridImageName;
	}
	/**
	 * @return the gridText
	 */
	public String getGridText() {
		return gridText;
	}
	/**
	 * @param gridText the gridText to set
	 */
	public void setGridText(String gridText) {
		this.gridText = gridText;
	}




}
