package com.lujunyu.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * 对象的序列化和反序列化操作
 * @author lujunyu
 *
 */
public final class SerializeUtil {
	private SerializeUtil(){}
	/**
	 * 序列化操作
	 * @param object
	 * @return
	 */
	public static <T extends Serializable> byte[] serialize(T t) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(t);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 反序列化操作
	 * @param bytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T unserialize(byte[] bytes,Class<T> t) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (T) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
