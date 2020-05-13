package com.pk.generater.generate;

import com.pk.generater.annotation.IdType;

import java.lang.reflect.Field;

/**
 * 策略模式：id赋值操作
 */
public interface PkGenerate {

	/**
	 * 主键赋值操作
	 */
	void process(Field field, Object paramObj) throws Exception;

	/**
	 * 返回主键策略类型
	 *
	 * @return
	 */
	IdType getType();

}
