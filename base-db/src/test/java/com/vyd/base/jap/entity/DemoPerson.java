/**
 * 
 */
package com.vyd.base.jap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * <p>Description: </p>
 * @author Dirk
 * @date 2017年5月19日 下午1:39:13 
 */
@Entity
@Table(name = "demo_person")
public class DemoPerson {
	
	/**
	 * ID
	 */
	@Id
	@GenericGenerator(name="idGenerator", strategy = "uuid")
	@GeneratedValue(generator="idGenerator")
	@Column(name="id",unique=true,nullable=false,length=40)
	private String id;
	
	/**
	 * 姓名
	 */
	@Column(name="name")
	private String name;
	
	/**
	 * 地址
	 */
	@Column(name="address")
	private String address;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
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
		return "DemoPerson : [id=" + this.getId() + ",name="+this.getName()+",address="+address+"]";
	}
}