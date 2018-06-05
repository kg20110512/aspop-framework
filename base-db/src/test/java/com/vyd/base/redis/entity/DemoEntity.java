/**
 * 
 */
package com.vyd.base.redis.entity;

/**
 * <p>Description: </p>
 * @author Dirk
 * @date 2017年5月26日 下午3:45:37 
 */
public class DemoEntity {
	private String name;
	private String address;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DemoEntity : [name="+this.getName()+",address="+address+"]";
	}
}