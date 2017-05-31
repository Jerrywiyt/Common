package com.lujunyu.id;

public interface IdHelper {
	/**
	 * 获取下一个id
	 * @return
	 */
	long nextId();
	/**
	 * 结合Source，获取下一个id，
	 * @param source
	 * @return
	 */
	long nextId(Object source);
}
