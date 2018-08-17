package com.lxc.autobeandemo;

import com.example.bean.ObjectCopyUtil;

/**
 * @author lxc
 */
public abstract class BassData<T> implements BaseClass<T>{
	
	public T getInitData() {
		try {
			return (T) ObjectCopyUtil.copy(initData());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
