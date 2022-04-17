package org.Group7_FinalProject.Utilities;

/*
 * The Highscore class includes all fields and methods related to a Highscore
 */
public class Highscore {
	
	//Fields for a Highscore
	private Integer value;
	private String accountName;
	
	//Default no-arg constructor
	public Highscore(){
		this(0, "");
	}
	
	//Constructor that requires two arguments
	public Highscore(Integer scoreNum, String accountName) {
		this.value = scoreNum;
		this.accountName = accountName;
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
}
