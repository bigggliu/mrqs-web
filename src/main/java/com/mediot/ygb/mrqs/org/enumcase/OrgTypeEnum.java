package com.mediot.ygb.mrqs.org.enumcase;

public enum OrgTypeEnum {

    ZHYY("综合医院","1b1884c2db4311e9913e00163e0a4fcb"),
    ZYYY("中医医院","1b189ccbdb4311e9913e00163e0a4fcb"),
    ZXYJHYY("中⻄医结合医院","1b18a375db4311e9913e00163e0a4fcb"),
    ZKYY("专科医院","1b18a9e5db4311e9913e00163e0a4fcb"),
    KFYY("康复医院","1b18aba0db4311e9913e00163e0a4fcb"),
    HYBJY("妇幼保健院","1b18ad59db4311e9913e00163e0a4fcb"),
    ZXWSY("中⼼卫⽣院","1b18aef5db4311e9913e00163e0a4fcb"),
    XZWSY("乡(镇)卫⽣院","1b18b084db4311e9913e00163e0a4fcb"),
    JDWSY("街道卫⽣院","1b18b212db4311e9913e00163e0a4fcb"),
    LYY("疗养院","1b18b3c3db4311e9913e00163e0a4fcb"),
    ZHMZB("综合⻔诊部","1b18b739db4311e9913e00163e0a4fcb"),
    ZKMZB("专科⻔诊部","1b18b915db4311e9913e00163e0a4fcb"),
    ZYMZB("中医⻔诊部","1b18bc58db4311e9913e00163e0a4fcb"),
    ZXYJHMZB("中⻄医结合⻔诊部","1b18be28db4311e9913e00163e0a4fcb"),
    MZYZS("⺠族医⻔诊部","1b18c005db4311e9913e00163e0a4fcb"),
    CLINIC("诊所","1b18c1b3db4311e9913e00163e0a4fcb"),
    ZY_CLINIC("中医诊所","1b18c445db4311e9913e00163e0a4fcb"),
    MZY_CLINIC("⺠族医诊所","1b18c66edb4311e9913e00163e0a4fcb"),
    WSS("卫⽣所","1b18c812db4311e9913e00163e0a4fcb"),
    YWS("医务室","1b18c9d9db4311e9913e00163e0a4fcb"),
    WSBJY("卫⽣保健所","1b18cbb1db4311e9913e00163e0a4fcb"),
    WSZ("卫⽣站","1b18cd6fdb4311e9913e00163e0a4fcb"),
    CZSS("村卫⽣室（所）","1b18cf2bdb4311e9913e00163e0a4fcb"),
    JJZX("急救中⼼","1b18d0f0db4311e9913e00163e0a4fcb"),
    JJZ("急救站","1b18d2badb4311e9913e00163e0a4fcb"),
    LCJYZX("临床检验中⼼","1b18d44edb4311e9913e00163e0a4fcb"),
    ZKJBFZY("专科疾病防治院","1b18d5f0db4311e9913e00163e0a4fcb"),
    ZKJBFZS("专科疾病防治所","1b18d7a5db4311e9913e00163e0a4fcb"),
    ZKJBFZZ("专科疾病防治站","1b18d953db4311e9913e00163e0a4fcb"),
    HLY("护理院","1b18db12db4311e9913e00163e0a4fcb"),
    HLZ("护理站","1b18dce8db4311e9913e00163e0a4fcb"),
    QTZLJG("其他诊疗机构","1b18deacdb4311e9913e00163e0a4fcb"),
    MBYY("⺠办医院","1b18a7f1db4311e9913e00163e0a4fcb")
    ;

    private String orgTypeLabel;

    private String value;

    OrgTypeEnum(String orgTypeLabel,String value){
        this.orgTypeLabel=orgTypeLabel;
        this.value=value;
    }

    public static String getOrgTypeLabel(String value) {
        for (OrgTypeEnum a : OrgTypeEnum.values()) {
            if (a.getValue().equals(value)) {
                return a.orgTypeLabel;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

}
