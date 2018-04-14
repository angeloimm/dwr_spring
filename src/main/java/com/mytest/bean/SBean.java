package com.mytest.bean;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.springframework.util.StringUtils;

@DataTransferObject(type="bean",javascript="sbean")
public class SBean {
	private String beanName;
	@RemoteProperty
	public String getBeanName()
	{
		if( !StringUtils.hasText(beanName) )
		{
			return "default text";
		}
		return beanName;
	}

	public void setBeanName(String beanName)
	{
		this.beanName = beanName;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beanName == null) ? 0 : beanName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SBean other = (SBean) obj;
		if (beanName == null)
		{
			if (other.beanName != null)
				return false;
		}
		else if (!beanName.equals(other.beanName))
			return false;
		return true;
	}
}