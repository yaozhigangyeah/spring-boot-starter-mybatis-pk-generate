package com.pk.generater.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(PkProperties.PREFIX)
public class PkProperties {

    protected static final String PREFIX = "pk.auto.generate";
    private static final String PK_TYPE = "SNID";
    private static final String EN_ABLED = "false";
    private static final long WORKER_ID = 1L;
    private static final long DATACENTER_ID = 1L;

    /**
     * 主键策略（LNID、SNID、UUID）
     */
    private String pkType = PK_TYPE;

    /**
     * 是否开启主键策略插件
     */
    private String enabled = EN_ABLED;

    /**
     * 工作ID
     */
    private Long workerId = WORKER_ID;

    /**
     * 数据中心ID
     */
    private Long datacenterId = DATACENTER_ID;

    public String getPkType() {
        return pkType;
    }

    public void setPkType(String pkType) {
        this.pkType = pkType;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public Long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(Long datacenterId) {
        this.datacenterId = datacenterId;
    }

    @Override
    public String toString() {
        return "PkProperties{" +
                "pkType='" + pkType + '\'' +
                ", enabled='" + enabled + '\'' +
                ", workerId=" + workerId +
                ", datacenterId=" + datacenterId +
                '}';
    }
}