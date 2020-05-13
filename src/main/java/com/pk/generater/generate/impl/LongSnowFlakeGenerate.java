package com.pk.generater.generate.impl;

import com.pk.generater.algorithm.SnowFlakeIdWorker;
import com.pk.generater.annotation.IdType;
import com.pk.generater.generate.AbstractPkGenerate;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

/**
 * Long类型主键雪花赋值
 */
public class LongSnowFlakeGenerate extends AbstractPkGenerate {

    @Autowired
    private SnowFlakeIdWorker snowFlakeIdWorker;

    @Override
    public void process(Field field, Object paramObj) throws Exception {
        if (field == null) {
            defaultGeneratorKey(paramObj);
            return;
        }
        if (!field.getType().isAssignableFrom(Long.class)) {
            throw new IllegalArgumentException("主键策略LNID==》对应主键属性类型必须为Long");
        }
        if (super.checkField(field, paramObj)) {
            field.set(paramObj, snowFlakeIdWorker.nextId());
        }
    }

    @Override
    public IdType getType() {
        return IdType.LNID;
    }

    /**
     * 默认id主键赋值
     *
     * @return
     */
    @Override
    protected void defaultGeneratorKey(Object paramObj) {
        MetaObject metaObject = SystemMetaObject.forObject(paramObj);
        if (metaObject.getValue(DEFAULT_ID) == null) {
            if (!LONG_TYPE.equals(metaObject.getSetterType(DEFAULT_ID).getSimpleName())) {
                throw new IllegalArgumentException("主键策略LNID==》对应主键属性类型必须为Long");
            }
            metaObject.setValue(DEFAULT_ID, snowFlakeIdWorker.nextId());
        }
    }
}
