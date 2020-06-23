package com.mediot.ygb.mrqs.org.enumcase;

public enum OrgLevelEnum {

    TCA("三级甲等","d2c04fe0f0af11e9913e00163e0a4fcb"),
    TCS("三级特等","d2c057fbf0af11e9913e00163e0a4fcb"),
    TCB("三级乙等","d2c05d04f0af11e9913e00163e0a4fcb"),
    TCC("三级丙等","d2c05e39f0af11e9913e00163e0a4fcb"),
    SCA("二级甲等","d2c05f57f0af11e9913e00163e0a4fcb"),
    SCB("二级乙等","d2c06076f0af11e9913e00163e0a4fcb"),
    SCC("二级丙等","d2c0618cf0af11e9913e00163e0a4fcb"),
    FCA("一级甲等","d2c06288f0af11e9913e00163e0a4fcb"),
    FCB("一级乙等","d2c0637ef0af11e9913e00163e0a4fcb"),
    FCC("一级丙等","d2c06473f0af11e9913e00163e0a4fcb"),
            ;

    private String orgLevelLabel;

    private String value;

    OrgLevelEnum(String orgLevelLabel,String value){
        this.orgLevelLabel=orgLevelLabel;
        this.value=value;
    }

    public static String getOrgLevelLabel(String value) {
        for (OrgLevelEnum a : OrgLevelEnum.values()) {
            if (a.getValue().equals(value)) {
                return a.orgLevelLabel;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

}
