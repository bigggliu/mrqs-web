package com.mediot.ygb.mrqs.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量定义类: 用于定义系统中用到的常量值或者码值
 *
 * @author
 * @version 1.0
 *  * @since JDK 1.6
 */
public class CustomConst {

    /**
     * 机构类型
     *
     * @author
     * @version 1.0
     *      * @since JDK 1.6
     */
    public static final class OrgType {

        /**
         * 医院
         */
        public static final int HOSPITAL = 1;

        /**
         * 企业（供应商/生产商）
         */
        public static final int SUPPLIER = 2;

        /**
         * 运营机构
         */
        public static final int CARRIER = 3;

        /**
         * 其他
         */
        public static final int OTHER = 9;

    }

    /**
     * 是否标识
     */
    public static final class Bool {

        /**
         * 是
         */
        public static final int YES = 1;
        /**
         * 否
         */
        public static final int NO = 0;

    }

    /**
     * 用户级别
     *
     * @author
     * @version 1.0
     *      * @since JDK 1.6
     */
    public static final class UserLevel {

        /**
         * 超级管理员
         */
        public static final String SUPER_ADMIN = "10";

        /**
         * 系统管理员
         */
        public static final String SYS_ADMIN = "20";

        /**
         * 系统操作员
         */
        public static final String ORG_USER = "30";

    }

    /**
     * 用户成功登录后存储在session里的信息变量名称
     */
    public final static class LoginUser {

        /**
         * 用户对象
         */
        public final static String SESSION_USER_INFO = "sessionUserInfo";

    }

    /**
     * 用户默认密码
     */
    public static final String DEFAULT_PASSWORD = "888888";

    /**
     * 默认验证码
     */
    public static final String DEFAULT_IMAGECODE = "6666";

    /**
     * 启用、停用状态
     *
     * @author
     * @version 1.0
     *      * @since JDK 1.6
     */
    public static final class Fstate {

        /**
         * 启用
         */
        public static final int USABLE = 1;
        /**
         * 停用
         */
        public static final int DISABLE = 0;

    }

    /**
     * 启用、停用状态
     */
    public static final Map<String, String> FstateMap = new HashMap<String, String>() {{
        put("1", "启用");
        put("0", "停用");
    }};

    /**
     * 机构申请类型： 00新机构注册   01机构更新   02新增机构    03代理更新
     */
    public static final class OrgApplyType {

        /**
         * 00新机构注册
         */
        public static final String ORG_REGISTER = "00";
        /**
         * 01机构更新
         */
        public static final String ORG_UPDATE = "01";
        /**
         * 02新增机构
         */
        public static final String NEW_ORG = "02";
        /**
         * 03代理更新
         */
        public static final String AGENT_UPDATE = "03";

    }

    /**
     * 机构申请状态： 00草稿 01待审核  02审核通过  03审核驳回  04待复审  05复审通过  06复审不通过
     */
    public static final class OrgApplyFstate {

        /**
         * 00草稿
         */
        public static final String DRAFT = "00";
        /**
         * 01待审核
         */
        public static final String NOT_AUDIT = "01";
        /**
         * 02审核通过
         */
        public static final String AUDIT_PASS = "02";
        /**
         * 03审核驳回
         */
        public static final String AUDIT_NO_PASS = "03";
        /**
         * 04待复审
         */
        public static final String NOT_REVIEW = "04";
        /**
         * 05复审通过
         */
        public static final String REVIEW_PASS = "05";
        /**
         * 06复审不通过
         */
        public static final String REVIEW_NO_PASS = "06";

    }

    /**
     * 机构来源
     * <p>Description: </p>
     *
     * @author
     * @version 1.0
     *      * @since JDK 1.8
     */
    public static final class OrgSource {

        /**
         * 机构来源（1纯院内供应商）
         */
        public static final Integer PURE_HOSPITAL = 1;

    }

    /**
     * 资质类型 00企业资质 01个人资质
     */
    public static final class CertType {

        /**
         * 00企业资质
         */
        public static final String ENTERPRISE_QUALIFICATION = "00";
        /**
         * 01个人资质
         */
        public static final String PERSONAL_QUALIFICATION = "01";

    }


    /**
     * 产品证件类型
     */
    public static final class ProductCertType {

        /**
         * 医疗器械备案凭证
         */
        public static final int INSTRUMENT_ARCHIVE = 1;

        /**
         * 医疗器械注册证
         */
        public static final int INSTRUMENT_REGISTER = 2;

        /**
         * 无证
         */
        public static final int NONE = 3;

    }

    /**
     * 平台类型 1   医供宝 2 采购平台 3 医院精细化管理平台
     */
    public static final class PlatformType {

        /**
         * 1   医供宝
         */
        public static final Integer ygb = 1;
        /**
         * 2 采购平台
         */
        public static final Integer CGPT = 2;
        /**
         * 3 医院精细化管理平台
         */
        public static final Integer HSCM = 3;

    }

    /**
     * 采购方式
     *
     * @author
     *
     */
    public static final class PurchaseType {

        /**
         * 采购
         */
        public static final int CG = 1;
        /**
         * 集中采购
         */
        public static final int JZCG = 2;
        /**
         * 区域平台
         */
        public static final int QYPT = 3;

    }

    /**
     * 机构更新状态： 00草稿  01待完成  02审核完成
     */
    public static final class OrgChangeApplyFstate {

        /**
         * 00草稿
         */
        public static final String DRAFT = "00";
        /**
         * 01待完成
         */
        public static final String NOT_AUDIT = "01";
        /**
         * 02审核完成
         */
        public static final String FINISH = "02";

    }

    /**
     * 资质类型： 00企业资质 01个人资质
     */
    public static final class OrgCertType {

        /**
         * 00企业资质
         */
        public static final String ENTERPRISE = "00";
        /**
         * 01个人资质
         */
        public static final String PERSONAL = "01";

    }

    /**
     * 变更类型： 01变更前、02变更后
     */
    public static final class OrgChangeType {

        /**
         * 01变更前
         */
        public static final String OLD = "01";
        /**
         * 02变更后
         */
        public static final String NEW = "02";

    }

    /**
     * 字典类型 00标准字典 01公有字典 02私有字典
     */
    public static final class OrgStaticType {

        /**
         * 00标准字典
         */
        public static final String STANDARD_STATIC = "00";
        /**
         * 01公有字典
         */
        public static final String PUBLIC_STATIC = "01";
        /**
         * 02私有字典
         */
        public static final String PRIVATE_STATIC = "02";

    }

    /**
     * 用户操作方式
     */
    public static final class UserEditType {

        /**
         * 医供宝平台(系统管理--管理员管理)
         */
        public static final int ygb_SYSTEM_ADMIN_EDIT = 1;
        /**
         * 医供宝平台(运营管理--系统管理--用户管理)
         */
        public static final int ygb_OPERATE_NORMAL_EDIT = 2;
        /**
         * 医供宝平台(运营管理--管理员管理)
         */
        public static final int ygb_OPERATE_ADMIN_EDIT = 3;

        /**
         * 采购平台(系统管理--用户管理)
         */
        public static final int CG_SYSTEM_ADMIN_EDIT = 4;

        /**
         * 采购平台(子系统--用户管理)
         */
        public static final int CG_SYSTEM_NORMAL_EDIT = 5;

        /**
         * 采购平台(供应商系统--供应商不存在)
         */
        public static final int CG_SUPPLY_ADMIN_EDIT = 6;

        /**
         * 采购平台(供应商系统--供应商存在)
         */
        public static final int CG_SUPPLY_ADMIN_EXIST_EDIT = 7;
    }

    /**
     * 系统类别
     */
    public static final class SystemType {

        /**
         * 10:机构管理员系统
         */
        public static final String ORG_ADMIN_SYSTEM = "10";
        /**
         * 20:供应商管理员系统
         */
        public static final String SUPPLY_ADMIN_SYSTEM = "20";

        /**
         * 30:业务系统
         */
        public static final String ORG_NORMAL_SYSTEM = "30";

        /**
         * 40:业务系统
         */
        public static final String SUPPLIER_NORMAL_SYSTEM = "40";

        /**
         * 50:工作站
         */
        public static final String HOSPITAL_WORK_SYSTEM = "50";

        /**
         * 60:精细化业务系统
         */
        public static final String HOSPITAL_BUSINESS_SYSTEM = "60";

        /**
         * 精细化系统管理系统(管理单元id)
         */
        public static final String HOSPITAL_PLATFORM_SYSTEM_DEFUALT_ID = "999999";

    }

    /**
     * 环境配置方式
     */
    public static final class ProfilesType {

        /**
         * 本地环境
         */
        public static final String LOCAL_ENV = "local";
        /**
         * 开发环境
         */
        public static final String DEV_ENV = "dev";
        /**
         * 测试环境
         */
        public static final String TEST_ENV = "test";
        /**
         * 生产环境
         */
        public static final String PROD_ENV = "prod";

    }

    /**
     * 操作方式
     */
    public static final class OperateType {

        /**
         * 新增
         */
        public static final String INSERT = "insert";
        /**
         * 编辑
         */
        public static final String UPDATE = "update";

    }

    /**
     * 医院等级
     */
    public static final class hospitalLevel {

        /**
         * 医院
         */
        public static final String HOSPITAL_THREE = "1";

    }

    /**
     * 医院类型
     */
    public static final class hospitalType {

        /**
         * 综合医院
         */
        public static final String HOSPITAL_SYNTHETICAL = "1";

    }

    /**
     * 证件类型
     */
    public static final class drugCertType {

        /**
         * 1 批准文号
         */
        public static final String APPROVAL_CERTIFICATE = "1";
        /**
         * 2 进口药品注册证certificate
         */
        public static final String IMPORT_CERTIFICATE = "2";

    }

    /**
     * 发票类型
     *
     * @author
     * @createDate 2018年9月14日 下午2:40:55
     */
    public static final class InvoiceType {

        public static final String INVOICE_BLUE = "00";//蓝票
        public static final String INVOICE_BLUE_NAME = "蓝票";

        public static final String INVOICE_RED = "01";//红票
        public static final String INVOICE_RED_NAME = "红票";

    }

    /**
     * 所属平台
     */
    public static final class platforModel {
        /**
         * 药品
         */
        public static final Integer Drug = 0;
        /**
         * 耗材
         */
        public static final Integer CONSUMABLE = 1;

    }

    /**
     * 发票状态
     *
     * @author
     * @version 1.0
     *
     * @since JDK 1.6
     */
    public static final class InvoiceFstate {
        /**
         * -2:发票待验收
         */
        public static final String AWAIT_CHECK = "-2";
        /**
         * -9:发票验收不通过
         */
        public static final String CHECK_NO_PASS = "-9";
        /**
         * 0:发票验收通过（验收通过为待审核）
         */
        public static final String CHECK_PASSED = "0";
        /**
         * 0:发票待审核（财务审核）
         */
        public static final String AWAIT_APPROVAL = "0";
        /**
         * 1:发票审核通过
         */
        public static final String APPROVAL_PASSED = "1";
        /**
         * 9:发票审核不通过
         */
        public static final String APPROVAL_NO_PASS = "9";
        /**
         * 草稿
         */
        public static final String DRAFT = "00";
        /**
         * 已结算
         */
        public static final String SETTLED = "90";
    }

    /**
     * 打款标记
     */
    public static final class PayTag {

        /**
         * 已标记
         */
        public static final Integer MoneyReceipt = 1;

        /**
         * 未标记
         */
        public static final Integer uncollected = 0;
    }

    /**
     * 剂型 1 丸剂  2 散剂 3 煎膏剂(膏滋) 4 丹剂 5 片剂 6 颗粒剂(冲剂)
     * 7 锭剂
     * 8 胶剂
     * 9 硬胶囊剂
     * 10 软胶囊剂
     * 11 糖浆剂
     * 12 合剂
     * 13 酒剂
     * 14 酊剂
     * 15 露剂
     * 16 注射剂
     * 17 气雾剂、喷雾剂
     * 18 膏药
     * 19 膜剂
     * 20 栓剂
     * 21 滴丸
     * 22 其他
     */
    public static final class dosage {

        /**
         * 1 丸剂
         */
        public static final String pill = "1";
        /**
         * 2 散剂
         */
        public static final String powder = "2";

    }

    /**
     * 国产/进口
     */
    public static final class isImport {

        /**
         * 0 国产
         */
        public static final String domestic = "0";
        /**
         * 1 进口
         */
        public static final String imp = "1";

    }

    /**
     * 招标产品操作类型 （00产品变更记录、01调价记录、02证件变更记录、03单位变更记录、04供货记录）
     */
    public static final class tenderProductOperationType {

        /**
         * 00产品变更记录
         */
        public static final String PRODUCT_CHANGE = "00";
        /**
         * 01调价记录
         */
        public static final String PRICE_CHANGE = "01";
        /**
         * 02证件变更记录
         */
        public static final String CERT_CHANGE = "02";
        /**
         * 03单位变更记录
         */
        public static final String UNIT_CHANGE = "03";
        /**
         * 04供货记录
         */
        public static final String FORG_CHANGE = "04";
        /**
         * 11零售价调价记录
         */
        public static final String RETAIL_PRICE_CHANGE = "11";

    }

    /**
     * 招标产品供货记录 变更类型 40新增、41启用、42停用
     */
    public static final class tenderProductForgChangeType {

        /**
         * 40新增
         */
        public static final String INSERT = "40";
        /**
         * 41启用
         */
        public static final String ENABLED = "41";
        /**
         * 42停用
         */
        public static final String OUTAGE = "42";

    }

    /**
     * 药品模块字典
     */
    public static final class MedicinalModule {

        /**
         * 入库
         */
        public static final String intstore_module = "RK";

        /**
         * 盘点
         */
        public static final String checkbill_module = "PD";

        /**
         * 处方
         */
        public static final String drugpricing_module = "CK";

        /**
         * 收费
         */
        public static final String sfkp_module = "SF";

        /**
         * 损益
         */
        public static final String syjl_module = "SY";

        /**
         * 日结
         */
        public static final String dailycharge_module = "RJ";
        /**
         * 机构
         */
        public static final String organization_module = "JG";
        /**
         * 病人就诊卡号
         */
        public static final String sick_person = "SP";
        /*
         * 临时处方
         * */
        public static final String linshi_drugpricing_module = "LS";
    }

    /**
     * 药品入库
     */
    public static final class MedicinalInStore {

        /**
         * 采购入库
         */
        public static final String purchase_instore = "10";

        /**
         * 初始化入库
         */
        public static final String init_instore = "20";

        /**
         * 盘盈入库
         */
        public static final String py_instore = "30";

    }

    /**
     * 药品出库
     */
    public static final class MedicinalOutStore {

        /**
         * 未发药
         */
        public static final Integer wfy_outstore = 10;

        /**
         * 盘亏出库
         */
        public static final Integer pk_outstore = 40;

        /**
         * 处方出库
         */
        public static final Integer cf_outstore = 50;

        /**
         * 退药退库
         */
        public static final Integer ty_outstore = 60;

    }

    /**
     * 盘点一级类别
     */
    public static final class MedicinalCheckBillType {

        /**
         * 明盘
         */
        public static final int mp_check_bill = 1;

        /**
         * 暗盘
         */
        public static final int ap_check_bill = 2;

        /**
         * 盘点全部提交
         */
        public static final int tj_all_check_bill = 1;

        /**
         * 盘点部分提交
         */
        public static final int tj_sub_check_bill = 2;

    }

    /**
     * 盘点二级类别
     * 盘点子类型  (1 全盘:锁库存 2 动盘  3 动销盘::锁库存)
     */
    public static final class MedicinalSubCheckBillType {

        /**
         * 全盘
         */
        public static final int qp_check_bill = 1;

        /**
         * 动盘
         */
        public static final int dp_check_bill = 2;

        /**
         * 动销盘
         */
        public static final int dxp_check_bill = 3;

        /**
         * 盘点正常单据
         */
        public static final int zc_check_bill = 1;

        /**
         * 盘点异常单据
         */
        public static final int yc_check_bill = 2;

    }

    /**
     * 盘点二级类别
     * 盘点状态(1--未盘点 2--已盘点)
     */
    public static final class MedicinalSubCheckBillDetailStatus {

        /**
         * 未盘点
         */
        public static final int not_check_bill = 1;

        /**
         * 已盘点
         */
        public static final int yet_check_bill = 2;

    }

    /**
     * 药品费用状态
     */
    public static final class MedicinalPayStatus {

        /**
         * 10:已收费
         */

        public static final Integer ysf_pay = 10;

        /**
         * 20:已退费
         */
        public static final Integer ytf_pay = 20;

        /**
         * 0:非药品
         */
        public static final String not_drug = "0";

        /**
         * 1:药品
         */
        public static final String yes_drug = "1";

        /**
         * 1:现金
         */
        public static final Integer cash_pay = 1;

        /**
         * 2:第三方支付
         */
        public static final Integer three_pay = 2;

        /**
         * 0:非药品
         */
        public static final String fyp_category = "0";

        /**
         * 1:药品
         */
        public static final String yp_category = "1";

        /**
         * 1:是否可以退药
         */
        public static final Integer cf_return_flag = 1;

    }

    /**
     * 盘点状态类别
     * 1 草稿 2 盘点中 3 损益处理 4 已完成
     */
    public static final class MedicinalCheckBillStatus {

        /**
         * 草稿
         */
        public static final int cg_status = 1;

        /**
         * 盘点中
         */
        public static final int pdz_status = 2;

        /**
         * 损益处理
         */
        public static final int sycl_status = 3;

        /**
         * 已完成
         */
        public static final int finish_status = 4;

    }

    /**
     * 是否组套 1是0否
     */
    public static final class TenderProductIsSuit {

        /**
         * 1套装
         */
        public static final int SUIT = 1;

        /**
         * 0单品
         */
        public static final int NOT_SUIT = 0;

    }

    /**
     * 订单分发标识
     *
     * @author
     *      */
    public static final class DispatcherFlag {

        /**
         * 0未知
         */
        public static final int UNKNOWN = 0;

        /**
         * 1正常订单
         */
        public static final int NORMAL_ORDER = 1;

        /**
         * 2父订单
         */
        public static final int PARENT_ORDER = 2;

        /**
         * 3子订单
         */
        public static final int CHILD_ORDER = 3;

    }

    /**
     * 招标产品 是否组套
     */
    public static final Map<String, String> TenderProductIsSuitMap = new HashMap<String, String>() {{
        put("0", "单品");
        put("1", "套装");
    }};

    /**
     * 变更状态 00待变更 01变更失败 02变更成功
     */
    public static final class PurchasePriceExecuteFstate {

        /**
         * 00待变更
         */
        public static final String PRICING_FOR = "00";

        /**
         * 01变更失败
         */
        public static final String PRICING_FAILURE = "01";

        /**
         * 02变更成功
         */
        public static final String PRICING_SUCCESS = "02";

    }

    /**
     * 招标类型
     *
     * @author
     *      */
    public static final class TenderType {

        /**
         * 初始化
         */
        public static final int INIT = 1;

        /**
         * 新材料申请采购
         */
        public static final int NEW_PRODUCT_APPLY = 2;

        /**
         * 临时议价采购
         */
        public static final int DISCUSS_PRICE = 3;

    }

    /**
     * 发布状态
     *
     * @author
     *      */
    public static final class TenderFstate {

        /**
         * 待发布
         */
        public static final int WAIT_PUBLISH = 0;
        /**
         * 发布中
         */
        public static final int PUBLISH_ING = 5;
        /**
         * 已发布
         */
        public static final int PUBLISH_FINISH = 6;

    }

    /**
     * 审核类型
     *
     * @author
     *      */
    public static final class AuditType {

        /**
         * 机构审核
         */
        public static final String ORG_AUDIT = "orgAudit";

        /**
         * 待办审核
         */
        public static final String TODO_AUDIT = "todoAudit";

        /**
         * 我的审核
         */
        public static final String MINE_AUDIT = "mineAudit";

    }

    /**
     * 审核业务：申请类型：00新机构注册  01机构更新  02新增机构  03代理更新  04银行账户更新  05新增代理  06授权更新  07新增授权  08厂商更新  09厂商新增
     * * 10招标产品审核  20证件更换  21更换证件  22调价申请  31新增产品 32资质更新 33授权更新
     *
     * @author
     *      */
    public static final class AuditApplyType {
        /**
         * 00新机构注册
         */
        public static final int NEW_ORG_REGISTER = 00;

        /**
         * 01机构更新
         */
        public static final int ORG_UPDATE = 01;

        /**
         * 02新增机构
         */
        public static final int INSERT_ORG = 02;

        /**
         * 03代理更新
         */
        public static final int AGENT_ORG_UPDATE = 03;

        /**
         * 04银行账户更新
         */
        public static final int BANK_ORG_UPDATE = 04;

        /**
         * 05新增代理
         */
        public static final int AGENT_ORG_INSERT = 05;

        /**
         * 06授权更新
         */
        public static final int AUTHORIZATION_UPDATE = 06;

        /**
         * 07新增授权
         */
        public static final int AUTHORIZATION_INSERT = 07;

        /**
         * 08厂商更新
         */
        public static final int PRODUCE_UPDATE = 8;

        /**
         * 09厂商新增
         */
        public static final int PRODUCE_INSERT = 9;

        /**
         * 10招标产品审核
         */
        public static final int TENDER = 10;

        /**
         * 20证件更换
         */
        public static final int CERT_CHANGE = 20;
        /**
         * 21证件更新
         */
        public static final int CERT_UPDATE = 21;
        /**
         * 22调价申请
         */
        public static final int PRICE_CHANGE = 22;
        //--------------药品--------------------
        /**
         * 31新增产品
         */
        public static final int INSERT_PRODUCT = 31;
        /**
         * 32资质更新
         */
        public static final int PRODUCT_CHANGE = 32;
        /**
         * 33授权更新
         */
        public static final int AUTHORIZATION_CHANGE = 33;
        //--------------耗材--------------------
        /**
         * 41新增产品
         */
        public static final int INSTRUMENT_INSERT_PRODUCT = 41;
        /**
         * 42资质更新
         */
        public static final int INSTRUMENT_PRODUCT_CHANGE = 42;
        /**
         * 43授权更新
         */
        public static final int INSTRUMENT_AUTHORIZATION_CHANGE = 43;
        /**
         * 44过期换证
         */
        public static final int INSTRUMENT_PAST_CHANGE = 44;
        /**
         * 45证件变更
         */
        public static final int INSTRUMENT_CERT_CHANGE = 45;
        /**
         * 46产品调价
         */
        public static final int INSTRUMENT_PRICE_CHANGE = 46;
        /**
         * 47产品停供
         */
        public static final int INSTRUMENT_STOP_CHANGE = 47;


    }

    /**
     * 审核状态
     *
     * @author
     *      */
    public static final class AuditState {

        /**
         * 草稿
         */
        public static final int DRAFT = 0;
        /**
         * 待审核
         */
        public static final int WAIT_AUDIT = 1;
        /**
         * 审核驳回
         */
        public static final int REJECT = 2;
        /**
         * 审核通过
         */
        public static final int AUDIT_PASS = 3;
        /**
         * 待复审
         */
        public static final int WAIT_REPERT_AUDIT = 4;
        /**
         * 复审不通过
         */
        public static final int REPERT_AUDIT_REJEST = 5;
        /**
         * 复审通过
         */
        public static final int REPERT_AUDIT_PASS = 6;
        /**
         * 审核中
         */
        public static final int IN_THE_AUDIT = 11;

    }

    /**
     * 审核状态map
     *
     * @author
     *      */
    public final static Map<Integer, String> AuditStateMap = new HashMap<Integer, String>() {{
        put(1, "医院待审核");
        put(2, "医院不通过");
        put(3, "医院通过");
        put(4, "平台待审核");
        put(5, "平台不通过");
        put(6, "平台通过");
    }};

    /**
     * 耗材产品变更类型 1:删除 ,2:更改,3:添加
     */
    public static final class InstrumentProductChangeType {
        /**
         * 1:删除
         */
        public static final String DELETE = "1";
        /**
         * 2:更改
         */
        public static final String UPDATE = "2";
        /**
         * 3:添加
         */
        public static final String INSERT = "3";
    }

    /**
     * 提醒类型 00正常、01即将到期(有效期-当日小于30天)、02已过期
     */
    public static final class LastTimeType {
        /**
         * 00正常
         */
        public static final String NORMAL = "00";
        /**
         * 01即将到期(有效期-当日小于30天)
         */
        public static final String ABOUT_TO_EXPIRE = "01";
        /**
         * 02已过期
         */
        public static final String HAVE_EXPIRED = "02";
    }

    /**
     * 是否导入成功 00否 01是
     */
    public static final class ImpInfoFstate {
        /**
         * 00否
         */
        public static final String NO = "00";
        /**
         * 01是
         */
        public static final String YES = "01";
    }

    /**
     * 是否包含植入物标志（00：否，01：是）
     */
    public static final class IsImplantFlag {
        /**
         * 00否
         */
        public static final String NO = "00";
        /**
         * 01是
         */
        public static final String YES = "01";
    }

    /**
     * 审核状态 (00、草稿 01、待审核，02、审核通过，03、审核不通过)
     */
    public static final class AuditFstate {
        /**
         * 00、草稿
         */
        public static final String DRAFT = "00";
        /**
         * 01、待审核
         */
        public static final String AWAIT_AUDIT = "01";
        /**
         * 02、审核通过
         */
        public static final String PASSED = "02";
        /**
         * 03、审核不通过
         */
        public static final String NO_PASS = "03";
    }

    /**
     * 调价类型(01、按固定金额调价  02、按比例调价)
     */
    public static final class PriceAdjustmentType {
        /**
         * 01、按固定金额调价
         */
        public static final String FIXATION_PRICE = "01";

        /**
         * 02、按比例调价
         */
        public static final String RATIO_PRICE = "02";
    }

    /**
     * 审核拓展类型 （priceAdjustmentFile 调价附件） （priceAdjustmentNo 调价编号） （priceAdjustmentType 调价类型） （priceAdjustmentValue 调价值）(isAddedChange 补充变更记录)
     */
    public static final class AuditDetailExtendType {
        /**
         * priceAdjustmentFile 调价附件
         */
        public static final String priceAdjustmentFile = "priceAdjustmentFile";

        /**
         * priceAdjustmentNo 调价编号
         */
        public static final String priceAdjustmentNo = "priceAdjustmentNo";

        /**
         * priceAdjustmentType 调价类型
         */
        public static final String priceAdjustmentType = "priceAdjustmentType";

        /**
         * priceAdjustmentValue 调价值
         */
        public static final String priceAdjustmentValue = "priceAdjustmentValue";

        /**
         * isAddedChange 补充变更记录
         */
        public static final String isAddedChange = "isAddedChange";
    }

    /**
     * 医疗器械 产品类型
     *
     * @author
     * @version 1.0
     *      * @since JDK 1.8
     */
    public static final class InstrumentProductType {

        //医疗器械
        public static final Integer MEDICAL_INSTRUMENT = 1;

        //无证
        public static final Integer NONE_CERT = 2;

    }

    /**
     * 医疗器械 证件类别
     *
     * @author
     * @version 1.0
     *      * @since JDK 1.8
     */
    public static final class InstrumentCertType {

        //备案凭证
        public static final int RECORD_CERT = 1;

        //注册证
        public static final int REGISTER_CERT = 2;

        //无证
        public static final int NONE_CERT = 3;

    }

    /**
     * 采购平台-耗材一期--【设置管理】【库房地址管理】是否默认地址
     * <p>Description: </p>
     *
     * @author
     * @version 1.0
     *      * @since JDK 1.8
     */
    public static final class StorageAddressIsDefault {
        /**
         * 是否默认- 默认
         */
        public final static int IS_DEFAULT = 1;

        /**
         * 是否默认- 非默认
         */
        public final static int IS_NO_DEFAULT = 0;
    }

    public static final class DeptType {
        /**
         * 科室可用状态
         */
        public static final int USE_DEPT = 1;
        /**
         * 科室停用状态
         */
        public static final int NOT_USE_DEPT = 0;

        /**
         * 耗材系统已关联科室
         */
        public static final int CHECK_DEPT = 1;
        /**
         * 耗材系统未关联科室
         */
        public static final int UN_CHECK_DEPT = 0;
    }

    /**
     * 系统类型
     *
     * @author
     */
    public static final class EncodeRuleType {

        /**
         * 编码规则类型
         */
        public static final String ENCODE_RULE_TYPE = "BMGZ";
    }

    /**
     * 规则名称
     *
     * @author
     */
    public static final class EncodeRuleChildType {

        /**
         * 【系统类型】
         */
        /**
         * 平台编码
         */
        public static final String PLATFORM_CODE = "platformCode";

        /**
         * 机构编码
         */
        public static final String ORG_CODE = "orgCode";

        /**
         * 管理单元编码
         */
        public static final String MANAGE_CODE = "manageCode";

        /**
         * 【产品类型】
         */
        /**
         * 产品编码
         */
        public static final String PRODCUT_CODE = "productCode";

        /**
         * 【产品类型】
         */
        /**
         * 设备产品编码
         */
        public static final String EQUIPMENT_CODE = "equipmentcode";

        /**
         * 【单据类型】
         */
        /**
         * 二维码
         */
        public static final String QR_BILL_CODE = "qrBillCode";

        /**
         * 包装码
         */
        public static final String BZ_BILL_CODE = "bzBillCode";

        /**
         * 拣货单
         */
        public static final String PICK_BILL_CODE = "pickingBillCode";

        /**
         * 入库单
         */
        public static final String INSTORE_BILL_CODE = "inStoreBillCode";

        /**
         * 出库单
         */
        public static final String OUTSTORE_BILL_CODE = "outStoreBillCode";

        /**
         * 库存盘点
         */
        public static final String STOCK_TAKING = "stockTaking";

        /**
         * 使用清单
         */
        public static final String USE_LISTING = "useListing";

        /**
         * 申请单--普耗
         */
        public static final String PA_APPLY_BILL_CODE = "paApplyBillCode";

        /**
         * 申请单--手术
         */
        public static final String SA_APPLY_BILL_CODE = "saApplyBillCode";

        /**
         * 申请单--高值
         */
        public static final String GA_APPLY_BILL_CODE = "gaApplyBillCode";

        /**
         * 申请单--退货
         */
        public static final String BACK_APPLY_BILL_CODE = "backApplyBillCode";

        /**
         * 送货单--手术
         */
        public static final String SD_SEND_BILL_CODE = "sdSendBillCode";

        /**
         * 送货单--普耗
         */
        public static final String PD_SEND_BILL_CODE = "pdSendBillCode";

        /**
         * 送货单--高值
         */
        public static final String GD_SEND_BILL_CODE = "gdSendBillCode";

        /**
         * 送货单--结算
         */
        public static final String JD_SEND_BILL_CODE = "jdSendBillCode";

        /**
         * 送货单--赠送
         */
        public static final String ZD_SEND_BILL_CODE = "zdSendBillCode";

        /**
         * 送货单--退货
         */
        public static final String TD_SEND_BILL_CODE = "tdSendBillCode";

        /**
         * 订单--普耗
         */
        public static final String PO_ORDER_BILL_CODE = "poOrderBillCode";

        /**
         * 订单--结算
         */
        public static final String JO_ORDER_BILL_CODE = "joOrderBillCode";

        /**
         * 订单--手术
         */
        public static final String SO_ORDER_BILL_CODE = "soOrderBillCode";

        /**
         * 订单--高值
         */
        public static final String GO_ORDER_BILL_CODE = "goOrderBillCode";

        /**
         * 订单--退货单
         */
        public static final String TO_ORDER_BILL_CODE = "toOrderBillCode";

        /**
         * 汇总单--结算
         */
        public static final String JG_GATHER_BILL_CODE = "jgGatherBillCode";

        /**
         * 汇总单--高值
         */
        public static final String GG_GATHER_BILL_CODE = "ggGatherBillCode";

        /**
         * 汇总单--普耗
         */
        public static final String PG_GATHER_BILL_CODE = "pgGatherBillCode";

        /**
         * 计划单--普耗
         */
        public static final String PP_PLAN_BILL_CODE = "ppPlanBillCode";

        /**
         * 计划单--高值
         */
        public static final String GP_PLAN_BILL_CODE = "gpPlanBillCode";

        /**
         * 计划单--手术
         */
        public static final String SP_PLAN_BILL_CODE = "spPlanBillCode";

        /**
         * 计划单--结算
         */
        public static final String JP_PLAN_BILL_CODE = "jpPlanBillCode";

        /**
         * 登记--手术
         */
        public static final String OC_REGISTRATION = "ocRegistration";

        /**
         * 登记--高值
         */
        public static final String GC_REGISTRATION = "gcRegistration";

        /**
         * 机构申请
         */
        public static final String ORG_APPLY_BILL_CODE = "orgApplyBillCode";

        /**
         * 设备维修单
         */
        public static final String EQ_MAINTENANCE_CODE = "eqMaintenanceCode ";

        /**
         * 设备培训记录
         */
        public static final String EQ_TRAIN_DOCUMENT_CODE = "eqTrainDocumentCode ";

        /**
         * 设备应急演练
         */
        public static final String EQ_EMERGENCY_DRILL_CODE = "eqEmergencyDrillCode ";

        /**
         * 设备采购计划单
         */
        public static final String EQ_PURCHASE_PLAN_CODE = "eqPurchasePlanCode ";

        /**
         * 设备转科单
         */
        public static final String EQ_TRANSFER_CODE = "eqTransferCode ";

        /**
         * 设备巡检单
         */
        public static final String EQ_CHECK_CODE = "eqCheckCode ";

        /**
         * 设备不良事件
         */
        public static final String EQ_ADVEVENT_CODE = "eqAdvEventCode ";

        /**
         * 设备计量单
         */
        public static final String EQ_MEASURE_CODE = "eqMeasureCode ";

        /**
         * 设备配送单
         */
        public static final String EQ_DELIVERY_CODE = "eqDeliveryCode ";

        /**
         * 设备入库单
         */
        public static final String EQ_IMPORT_CODE = "eqImportCode ";

        /**
         * 设备出库单
         */
        public static final String EQ_OUTPORT_CODE = "eqOutportCode ";

        /**
         * 设备报废单
         */
        public static final String EQ_SCRAP_CODE = "eqScrapCode ";

        /**
         * 设备保养计划单
         */
        public static final String EQ_MAINTAIN_PLAN_CODE = "eqMaintainPlanCode ";

        /**
         * 设备保养单
         */
        public static final String EQ_MAINTAIN_CODE = "eqMaintainCode ";

        /**
         * 验收单
         */
        public static final String ACCEPT_CHECK = "acceptCheck ";

        /**
         * 退货单
         */
        public static final String BACK_FORG = "backForg ";

        /**
         * 退库单
         */
        public static final String BACK_STORAGE = "backStorage ";

        /**
         * 配送单
         */
        public static final String SEND_BILL_CODE = "sendBillCode";

        /**
         * 货位移动单
         */
        public static final String GOODS_LOCAL_ADJUST_CODE = "goodsLocalAdjustCode";

        /**
         * 打包单--条形码
         */
        public static final String PACK_BILL_CODE = "packBillCode";

        /**
         * 上架单
         */
        public static final String PUTAWAY_CODE = "putawayCode";

        /**
         * 运送单
         */
        public static final String TRANSPORT_CODE = "transportCode";

        /**
         * 三方仓下架单
         */
        public static final String storehouse_picking_code = "storehousePickingCode";

        /**
         * 三方仓拒收单
         */
        public static final String storehouse_rejection_code = "storehouseRejectionCode";

        /**
         * 三方仓拒收单
         */
        public static final String storehouse_back_code = "storehouseBackCode";
        /**
         * 暂存签收单号
         */
        public static final String DELIVERY_RECEIPT = "deliveryReceiptCode";

        /**
         * 物流回执单
         */
        public static final String LOGISTICS_RECEIPT = "logisticsReceiptCode";

        /**
         * 打包单号
         */
        public static final String PACK_CODE = "packCode";

        /**
         * 欠品单号
         */
        public static final String DELIVERY_MARGIN = "deliveryMarginCode";


        /**
         * 损益单号
         */
        public static final String PROFIT_LOSS = "profitLossCode";

        /**
         * 配货单号
         */
        public static final String DISTRIBUTION_OUTPUT = "distributionOutput";


        /**
         * saas部门编码
         */
        public static final String SAAS_DEPART_CODE = "saasDepartCode";

    }

    /**
     * 产品编码规则是否继承
     *
     * @author
     */
    public static final class EncodeRuleStatus {

        /**
         * 维护
         */
        public static final int YES_STATUS = 1;

        /**
         * 非维护
         */
        public static final int NO_STATUS = 0;
    }

    /**
     * 产品编码规则变量类型
     *
     * @author
     */
    public static final class EncodeRuleConfig {

        /**
         * 常量
         */
        public static final String VAR_TYPE = "01";

        /**
         * 国产／进口
         */
        public static final String DOM_CIM_TYPE = "02";

        /**
         * 物资分类
         */
        public static final String GOOD_TYPE = "03";

        /**
         * 日期分类
         */
        public static final String DATE_TYPE = "04";

        /**
         * 流水号分类
         */
        public static final String FLOW_TYPE = "05";

        /**
         * 流水号分类-产品证件流水号
         */
        public static final String FLOW_PRODUCT_CART_TYPE = "501";

        /**
         * 流水号分类-产品流水号
         */
        public static final String FLOW_PRODUCT_TYPE = "502";

        /**
         * 系统编码分类
         */
        public static final String SYSTEM_RULE_TYPE = "06";

        /**
         * 系统编码分类-机构编码
         */
        public static final String SYSTEM_RULE_ORG_TYPE = "601";

        /**
         * 系统编码分类-管理单元编码
         */
        public static final String SYSTEM_RULE_UNIT_TYPE = "602";

        /**
         * 系统编码分类-子系统编码
         */
        public static final String SYSTEM_RULE_SYSTEM_TYPE = "603";

        /**
         * 随机码分类
         */
        public static final String RANDDOM_TYPE = "07";

        /**
         * 自定义
         */
        public static final String CUSTOM_TYPE = "custom";

    }

    /**
     * 编码规则日期变量类型
     *
     * @author
     */
    public enum EncodeRuleDateType {

        /**
         * yyyy
         */
        YYYY("yyyy", "yyyy"),

        /**
         * yyyyMM
         */
        YYYYMM("yyyymm", "yyyyMM"),

        /**
         * yyyyMM
         */
        YYYYMMDD("yyyymmdd", "yyyyMMdd"),

        /**
         * yymmdd
         */
        YYMMDD("yymmdd", "yyMMdd"),

        /**
         * yyyyMMddHHmmss
         */
        YYYYMMDDHHMMSS("yyyyMMddHHmmss", "yyyyMMddHHmmss");

        private String frontDate;

        private String systemDate;

        public String getFrontDate() {
            return frontDate;
        }

        public String getSystemDate() {
            return systemDate;
        }

        private EncodeRuleDateType(String frontDate, String systemDate) {
            this.frontDate = frontDate;
            this.systemDate = systemDate;
        }

    }

    /**
     * 送货单状态
     *
     *
     */
    public static final class DeliveryFstate {
        /**
         * 10:待确认
         */
        // public static final int AWAIT_CONFIRM = 10;
        /**
         * 40:已确认、待发货
         */
        public static final Integer AWAIT_SEND = 40;
        /**
         * 50:已发货、待验收
         */
        public static final Integer AWAIT_CHECK = 50;
        /**
         * 60:验收通过
         */
        public static final Integer CHECK_PASSED = 60;
        /**
         * 80:完结
         */
        public static final Integer FINISH = 80;
        /**
         * 90:验收不通过
         */
        public static final Integer CHECK_NO_PASS = 90;
        /**
         * 70:验收通过（二次验收）
         */
        public static final Integer DEPT_CHECK_PASSED = 70;
        /**
         * 99:二次验收不通过
         */
        public static final Integer REJECTION_PASS = 99;

        /**
         * 部分验收
         */
        public static final Integer PORTTION_PASS = 91;

        /**
         * 拒收
         */
        public static final Integer REJECT = 61;
    }

    /**
     * 状态变更类型标识
     *
     *
     */
    public static final class ChangeFstateType {
        /**
         * 订单
         */
        public static final String ORDER = "订单";
        /**
         * 送货单
         */
        public static final String DELIVERY = "送货单";
        /**
         * 送货单打印
         */
        public static final String DELIVERY_PRINT = "送货单打印";
        /**
         * 发票
         */
        public static final String INVOICE = "发票";
        /**
         * 计划单
         */
        public static final String PLAN = "计划单";
        /**
         * 退货单
         */
        public static final String BACK = "退货单";
    }

    /**
     * 送货单类型 (迭代版本用的送货类型）
     *
     *
     */
    public static final class DeliveryType {
        /**
         * 普耗
         */
        public static final String DELIVERY = "DELIVERY";
        /**
         * 高值
         */
        public static final String HIGH_DELIVERY = "HIGH_DELIVERY";
        /**
         * 结算
         */
        public static final String SETTLE_DELIVERY = "SETTLE_DELIVERY";
        /**
         * 手术
         */
        public static final String OPER_DELIVERY = "OPER_DELIVERY";
        /**
         * 赠送
         */
        public static final String GIVE_DELIVERY = "GIVE_DELIVERY";
        /**
         * 退货
         */
        public static final String BACK_DELIVERY = "BACK_DELIVERY";
        /**
         * 跟台备货送货单
         */
        public static final String STOCK_DELIVERY = "STOCK_DELIVERY";
        /**
         * 普通
         */
        public static final String ORDINARY_DELIVERY = "ORDINARY_DELIVERY";
        /**
         * 特需药
         */
        public static final String SPECIAL_MEDICINE_DELIVERY = "SPECIAL_MEDICINE_DELIVERY";
        /**
         * 管制药
         */
        public static final String CONTROLS_MEDICINE_DELIVERY = "CONTROLS_MEDICINE_DELIVERY";
        /**
         * 手工签收
         */
        public static final String HAND_DELIVERY = "HAND_DELIVERY";
    }

    /**
     * 文件导出Excel表对应字段名(送货单)
     */
    public final static Map<String, String> DeliveryExcelMap = new HashMap<String, String>() {{
        put("fstateName", "状态");
        put("deliveryTypeName", "送货单类型");
        put("printtimes", "打印次数");
        put("sendNo", "送货单号");
        put("orderNo", "订单号");
        put("totalPrice", "送货单总金额");
        put("fOrgName", "供应方");
        put("rOrgName", "医疗机构");
        put("pStorageName", "库房");
        put("sendUserName", "制单人");
        put("tfaddress", "收货地址");
        put("sendDate", "制单时间");
        put("checkUserName", "验收人");
        put("checktime", "验收时间");

    }};

    /**
     * 导出一级库房库存查询对应字段
     */
    public final static Map<String, String> storageInventoryExcelMap = new HashMap<String, String>() {{
        put("geName", "通用名称");
        put("materialName", "产品名称");
        put("styleName", "财务分类");
        put("typeName", "物资分类");
        put("spec", "规格");
        put("fmodel", "型号");
        put("suitName", "组件名称");
        put("inmode", "库存类型");
        put("purchaseUnit", "单位");
        put("amount", "库存数量");
        put("purchasePrice", "价格");
        put("money", "金额");
        put("fOrgName", "供应商");
        put("registerNo", "注册证号");
        put("produceName", "生产商");
    }};

    /*
     * 导出二级库存字段
     */
    public final static Map<String, String> levelStorageInventoryExcelMap = new HashMap<String, String>() {{
        put("geName", "通用名称");
        put("materialName", "产品名称");
        put("spec", "规格");
        put("fmodel", "型号");
        put("inmode", "库存类型");
        put("useUnit", "领用单位");
        put("amount", "库存数量");
        put("uLimit", "库存上限");
        put("lLimit", "库存下限");
        put("usePrice", "价格");
        put("money", "金额");
        put("styleName", "财务分类");
        put("typeName", "物资分类");
        put("registerNo", "注册证号");
        put("fOrgName", "供应商");
        put("produceName", "生产商");
        put("shfsName", "送货方式");
        put("qrFlagName", "一物一码");
    }};

    /**
     * 计划单类别
     *
     * @author
     * @version 1.0
     *
     * @since JDK 1.6
     */
    public static final class PlanType {
        /**
         * 普耗
         */
        public static final String PLAN = "PLAN";
        /**
         * 高值
         */
        public static final String HIGH_PLAN = "HIGH_PLAN";
        /**
         * 结算
         */
        public static final String SETTLE_PLAN = "SETTLE_PLAN";
        /**
         * 手术
         */
        public static final String OPER_PLAN = "OPER_PLAN";
    }

    /**
     * 计划单状态
     */
    public static final class PlanFstate {
        /**
         * 00-草稿
         */
        public static final String DRAFT = "00";
        /**
         * 20-待确认
         */
        public static final String AWAIT_AGGREE = "20";
        /**
         * 30-已确认
         */
        public static final String ALREADY_AGGREE = "30";
        /**
         * 34-采购驳回
         */
        public static final String REJECT = "34";
        /**
         * 36-已汇总
         */
        public static final String SUMMATION = "36";
        /**
         * 40-采购中
         */
        public static final String PURCHASE = "40";
        /**
         * 42-已撤销
         */
        public static final String CANCELLED = "42";
        /**
         * 47-已作废
         */
        public static final String OBSOLETE = "47";
        /**
         * 77-异常结束
         */
        public static final String STOP = "77";
        /**
         * 60-完结
         */
        public static final String FINISH = "60";
    }

    /**
     * 计划进度
     */
    public static final class PlanProgress {
        /**
         * 计划已确认
         */
        public static final String PLAN_CONFIRMED = "30";
        /**
         * 计划已汇总
         */
        public static final String PLAN_SUMMARIZED = "36";
        /**
         * 计划采购中
         */
        public static final String PLAN_PROCUREMENT = "40";
        /**
         * 计划完结
         */
        public static final String PLAN_FINISH = "60";
        /**
         * 订单已确认(订单备货中)
         */
        public static final String ORDER_CONFIRMED = "40";
        /**
         * 订单关闭
         */
        public static final String ORDER_CLOSE = "70";
        /**
         * 订单完成
         */
        public static final String ORDER_FINISH = "80";

        /**
         * 全部发货(订单下的送货单全部待验收)
         */
        public static final String ALL_SHIPPED = "46";

        /**
         * 已发货(送货单待验收)
         */
        public static final String SHIPPED = "50";
        /**
         * 验收通过(送货单验收通过)
         */
        public static final String ACCEPTANCE = "60";
        /**
         * 完结(送货单完结)
         */
        public static final String FINISH = "80";

        /**
         * 采购确认
         */
        public static final String PURCHASE_CONFIRMATION = "1";

        /**
         * 订单确认
         */
        public static final String ORDER_CONFIRMATION = "2";
        /**
         * 订单发货
         */
        public static final String ORDER_SHIPMENT = "3";
        /**
         * 订单部分验收
         */
        public static final String ORDER_PARTIAL_ACCEPTANCE = "4";
        /**
         * 订单全部验收
         */
        public static final String ORDERS_FULL_ACCEPTANCE = "5";
    }

    /**
     * 银行更新状态
     *
     * @author
     */
    public static final class OrgBankStatus {

        /**
         * 带审核
         */
        public static final Integer HANDLE_STATUS = 1;

        /**
         * 审核完成
         */
        public static final Integer OVER_STATUS = 2;

        /**
         * 驳回
         */
        public static final Integer REJECT_STATUS = 3;

    }

    /**
     * 银行更新类型
     *
     * @author
     */
    public static final class OrgBankType {

        /**
         * 医院
         */
        public static final Integer HOSPITAL_TYPE = 1;

        /**
         * 运营
         */
        public static final Integer OPERATE_TYPE = 2;

    }

    /**
     * 药品订单状态
     */
    public static final class MedicalOrderFstate {

        /**
         * 需方取消
         */
        public static final int TENDER_CANCLE = 29;

        /**
         * 待受理
         */
        public static final int MEDICAL_DISPOSING = 30;

        /**
         * 备货中
         */
        public static final int MEDICAL_DELIVERY = 40;

        /**
         * 发货完成
         */
        public static final int DELIVERY_COMPLATE = 46;

        /**
         * 交易完成
         */
        public static final int DEAL_COMPLATE = 80;

        /**
         * 供方取消
         */
        public static final int SUPPLY_CANCLE = 90;


    }


    /**
     * 订单状态
     *
     * @author
     *      */
    public static final class OrderFstate {
        /**
         * 20-已提交（需方：待发送）
         */
        public static final int AWAIT_SEND = 20;
        /**
         * 29-已取消（需方：取消订单）
         */
        public static final int CANCEL = 29;
        /**
         * 30-待确认（需方：发送订单）
         */
        public static final int AWAIT_CONFIRM = 30;
        /**
         * 40 备货中（供方：确认订单）
         */
        public static final int SETTLE_GOODS = 40;
        /**
         * 46 全部发货（供方：备货完成并全部发货）
         */
        public static final int SEND_OUT_GOODS_FINISH = 46;
        /**
         * 70 已关闭（供方：关闭订单）
         */
        public static final int CLOSED = 70;
        /**
         * 80 订单完成
         */
        public static final int FINISH = 80;
        /**
         * 90 已拒绝（供方：拒绝订单）
         */
        public static final int REJECT = 90;
    }

    /**
     * 订单状态
     */
    public static final Map<Integer, String> OrderFstateMap = new HashMap<Integer, String>() {{
        put(30, "待确认");
        put(40, "备货中");
        put(46, "全部发货");
        put(70, "已关闭");
        put(80, "订单完成");
        put(90, "已拒绝");
    }};

    /**
     * 计划单数据来源
     */
    public static final class PlanFsource {
        /**
         * 01:科室申请
         */
        public static final String DEPTAPPLY = "01";
        /**
         * 02:库房申请
         */
        public static final String STORAGEAPPLY = "02";
    }

    /**
     * 汇总单状态
     */
    public static final class GatherFstate {
        /**
         * 10-新建
         */
        public static final String NEW_BUILT = "10";
        /**
         * 42-已撤销 shiyong 2018/11/13
         */
        public static final String CANCELLED = "42";
        /**
         * 41-已驳回 shiyong 2018/11/13
         */
        public static final String REJECT = "41";
        /**
         * 60-完结
         */
        public static final String FINISH = "60";
        /**
         * 62、审核通过
         */
        public static final String PASSED = "62";
        /**
         * 64-不通过
         */
        public static final String NO_PASS = "64";
    }

    /**
     * 汇总单类别
     *
     * @author
     * @version 1.0
     *
     * @since JDK 1.6
     */
    public static final class GatherType {
        /**
         * 普耗
         */
        public static final String GATHER = "GATHER";
        /**
         * 高值
         */
        public static final String HIGH_GATHER = "HIGH_GATHER";
        /**
         * 结算
         */
        public static final String SETTLE_GATHER = "SETTLE_GATHER";
        /**
         * 报备
         */
        public static final String APPLY_RB_GATHER = "APPLY_RB_GATHER";
    }

    /**
     * 订单类别
     *
     * @author
     * @version 1.0
     *
     * @since JDK 1.6
     */
    public static final class OrderType {
        /**
         * 普耗
         */
        public static final String ORDER = "ORDER";
        /**
         * 备货
         */
        public static final String HIGH_ORDER = "HIGH_ORDER";
        /**
         * 结算
         */
        public static final String SETTLE_ORDER = "SETTLE_ORDER";
        /**
         * 手术
         */
        public static final String OPER_ORDER = "OPER_ORDER";
        /**
         * 退货订单
         */
        public static final String BACK_ORDER = "BACK_ORDER";
        /**
         * 普通
         */
        public static final String ORDINARY_ORDER = "ORDINARY_ORDER";
        /**
         * 特需药订单
         */
        public static final String SPECIAL_MEDICINE_ORDER = "SPECIAL_MEDICINE_ORDER";
        /**
         * 管制药订单
         */
        public static final String CONTROLS_MEDICINE_ORDER = "CONTROLS_MEDICINE_ORDER";
    }

    /**
     * 订单类别
     */
    public static final Map<String, String> OrderTypeMap = new HashMap<String, String>() {{
        put("ORDER", "普耗订单");
        put("HIGH_ORDER", "备货订单");
        put("SETTLE_ORDER", "结算订单");
        put("OPER_ORDER", "手术订单");
        put("BACK_ORDER", "退货订单");
    }};

    /**
     * 订单来源
     *
     * @author
     * @version 1.0
     *
     * @since JDK 1.6
     */
    public static final class OrderFsource {
        /**
         * 库房计划
         */
        public static final Integer PLAN = 1;
        /**
         * 自建订单
         */
        public static final Integer SELF_ORDER = 2;
    }

    /**
     * 申请单为采购还是派送
     */
    public static final class SendModeType {
        /**
         * 00-采购(0库存)
         */
        public static final Integer PURCHASE = 0;
        /**
         * 01-派送(供应商备货)
         */
        public static final Integer PLCK = 1;
        /**
         * 02-调拨
         */
        public static final Integer ALLOT = 2;
        /**
         * 03-库房备货
         */
        public static final Integer KFBH = 3;
    }

    /**
     * 库房产品“备货方式”
     *
     * @author
     *      */
    public static final class StockType {
        /**
         * 库管备货
         */
        public static final int STORAGE_STOCK = 1;
        /**
         * 供应商备货
         */
        public static final int SUPPLIER_STOCK = 3;
    }

    /**
     * 申请产品“送货方式”
     * 送货方式：0直配(采购) 1配货(拣货) 2调拨(送科室库)  3货位移动
     */
    public static final class SendMode {
        /**
         * 采购（送科室）
         */
        public static final int PURCHASE = 0;
        /**
         * 拣货（送库房，科室再找库管要）
         */
        public static final int PLCK = 1;
        /**
         * 调拨（送科室库）
         */
        public static final int ALLOT = 2;
        /**
         * 货位移动
         */
        public static final int FREIGHT_MOVE = 3;
    }

    /**
     * 是否供应商备货 0否 1是
     */
    public static final class isStockup {
        /**
         * 是供应商备货
         */
        public static final int YES = 1;
        /**
         * 不是供应商备货
         */
        public static final int NO = 0;
    }

    /**
     * 文件导出普耗/高值计划产品表对应字段名
     */
    public final static Map<String, String> planCjDetailListExcelMap = new HashMap<String, String>() {{
        put("isSuitName", "类型");
        put("productName", "产品名称");
        put("geName", "通用名称");
        put("suitName", "组件名称");
        put("spec", "规格");
        put("fmodel", "型号");
        put("purchaseUnit", "采购单位");
        put("purchasePrice", "采购价格");
        put("exAmount", "需求数量");
        put("amount", "采购数量");
        put("price", "金额");
        put("tfBrandName", "品牌");
        put("fOrgName", "供应商");
        put("registNo", "证件号");
        put("flot", "生产批号");
        put("prodDate", "生产日期");
        put("usefulDate", "有效期至");
    }};

    /**
     * 文件导出手术计划品牌表对应字段名
     */
    public final static Map<String, String> planBjDetailListExcelMap = new HashMap<String, String>() {{
        put("tfBrandName", "品牌");
        put("fOrgName", "供应商");
        put("lxr", "联系人");
        put("lxdh", "联系电话");
    }};

    /**
     * 文件导出Excel普耗/高值计划表对应字段名
     */
    public final static Map<String, String> planCjListExcelMap = new HashMap<String, String>() {{
        put("fstateName", "状态");
        put("planNo", "计划单号");
        put("fsourceName", "计划单来源");
        put("deptName", "申请科室");
        put("storageName", "申请库房");
        put("tfAddress", "收货地址");
        put("totalPrice", "金额");
        put("planUsername", "申请人");
        put("planTime", "申请时间");
        put("applyNo", "申请单号");
    }};

    /**
     * 文件导出手术计划表对应字段名
     */
    public final static Map<String, String> planBjListExcelMap = new HashMap<String, String>() {{
        put("fstateName", "状态");
        put("planNo", "计划单号");
        put("zyh", "住院号");
        put("operName", "手术名称");
        put("name", "患者姓名");
        put("deptName", "申请科室");
        put("tfAddress", "收货地址");
        put("storageName", "备货库房");
        put("planUsername", "申请人");
        put("planTime", "申请时间");
        put("applyNo", "申请单号");
    }};

    /**
     * 退货单状态
     */
    public static final class BackFstate {
        /**
         * 0-草稿
         */
        public static final Integer DRAFT = 0;
        /**
         * 10-待确认
         */
        public static final Integer AWAIT_AGGREE = 10;
        /**
         * 11-已撤销
         */
        public static final Integer CANCELLED = 11;
        /**
         * 20-已确认
         */
        public static final Integer ALREADY_AGGREE = 20;
        /**
         * 50-采购驳回
         */
        public static final Integer REJECT = 50;
        /**
         * 47-异常结束
         */
        public static final Integer STOP = 47;
        /**
         * 70-已删除（已作废）
         */
        public static final Integer DELETE = 70;
        /**
         * 30-退货中
         */
        public static final Integer PURCHASE = 30;
        /**
         * 80-完结
         */
        public static final Integer FINISH = 80;
        /**
         * 21-待接收
         */
        public static final Integer AWAIT_RECEIVE = 21;
        /**
         * 28-已接收
         */
        public static final Integer RECEIVE = 28;
    }

    /**
     * 启用、停用状态
     */
    public static final class SourceFstate {

        /**
         * 启用
         */
        public static final String USABLE = "01";
        /**
         * 停用
         */
        public static final String DISABLE = "00";

    }

    /**
     * 外来器械审核状态 00草稿， 01待审核，02审核不通，03生效
     */
    public static final class ToolDirFstate {
        /**
         * 00草稿
         */
        public static final String DRAFT = "00";
        /**
         * 01待审核
         */
        public static final String WAIT_AUDIT = "01";
        /**
         * 02审核不通
         */
        public static final String AUDIT_NO_PASS = "02";
        /**
         * 03生效
         */
        public static final String AUDIT_PASS = "03";
    }

    /**
     * 申请单状态
     */
    public static final class ApplyFstate {
        /**
         * 0-草稿
         */
        public static final int DRAFT = 0;
        /**
         * 10-已提交
         * TODO 10已提交 与 20待确认 是一个意思，开发找个时间去掉10状态
         */
        public static final int SUBMITED = 10;
        /**
         * 20-待确认（已提交，待库房确认）
         */
        public static final int AWAIT_CONFORM = 20;
        /**
         * 15-待出库（已确认、待拣货出库）
         */
        public static final int WAIT_OUPORT = 15;
        /**
         * 35-已汇总，仅用于报备申请单中
         */
        public static final int GATHERED = 35;
        /**
         * 34-库管驳回
         */
        public static final int STORAGE_REJECT = 34;
        /***
         * 37 采购驳回
         */
        public static final int PURCHASE_REJECT = 37;
        /**
         * 40-采购中
         */
        public static final int PURCHASING = 40;
        /**
         * 42-已撤销
         */
        public static final int CANCELLED = 42;
        /**
         * 47-已作废
         */
        public static final int OBSOLETE = 47;
        /**
         * 60-完结
         */
        public static final int FINISH = 60;
        /**
         * 79-异常结束
         */
        public static final int ABNORMAL_END = 79;
        /**
         * 92-删除
         */
        public static final int DELETE = 92;
        /**
         * 30-已确认
         */
        public static final int CONFORM = 30;
    }

    /**
     * 申请单类别
     */
    public static final class ApplyType {
        /**
         * 普耗
         */
        public static final String APPLY = "APPLY";
        /**
         * 高值
         */
        public static final String HIGH_APPLY = "HIGH_APPLY";
        /**
         * 手术
         */
        public static final String OPER_APPLY = "OPER_APPLY";
        /**
         * 调拨申请
         */
        public static final String ALLOT_APPLY = "ALLOT_APPLY";
    }

    /**
     * 工作人员字典分类
     */
    public static final class personnel {
        //上传电子签名
        public static final String ELECTRONIC_SIGNATURE = "electronicSignature";
        //身份
        public static final String PERSONNEL_CARD = "personnelCard";
        //状态
        public static final String FSTATE = "fstate";
        //配置部门类型
        public static final String DEPT_TYPE = "deptType";
    }

    /**
     * 精细化科室类型
     *
     * @author
     * @version 1.0
     *      * @since JDK 1.8
     */
    public static final class DepartInfoType {
        //科室
        public static final String DEPART_TYPE_DEPARTMENT = "1";
        //科室库
        public static final String DEPART_TYPE_LIBRARY = "2";
        //二级库
        public static final String DEPART_TYPE_TWOLIBRARY = "3";
    }

    /**
     * 库房类型
     *
     * @author
     */
    public static final class StorageType {
        /**
         * 采购库房
         */
        public static final int STORAGE_TYPE_CAIGOU = 1;
        /**
         * 二级库房
         */
        public static final int STORAGE_TYPE_TWODEPT = 2;
        /**
         * 科室库
         */
        public static final int STORAGE_TYPE_LIB = 3;
    }

    /**
     * 领用单位类型
     *
     * @author
     *      */
    public static final class UseUnitType {
        /**
         * 采购单位
         */
        public static final int PURCHASE_UNIT = 1;
        /**
         * 最小单位
         */
        public static final int LEAST_UNIT = 2;
        /**
         * 领用单位
         */
        public static final int USE_UNIT = 3;
        /**
         * 自定义单位
         */
        public static final int CUSTOM_UNIT = 9;
    }

    /**
     * 产品来源
     *
     * @author
     *      */
    public static final class ProductSourceType {

        /**
         * 医供宝-采购平台
         */
        public static final int PURCHASE_PLATFORM = 1;

        /**
         * 本地自建
         */
        public static final int LOCAL = 2;

    }

    /**
     * 备货方式
     *
     * @author
     *      */
    public static final class Bhfs {

        /**
         * 库房备货
         */
        public static final int KGBH = 1;

        /**
         * 供应商备货
         */
        public static final int GSYBH = 3;

    }

    /**
     * 库房产品标识（普耗产品、高值产品）
     *
     * @author
     *      */
    public static final class StorageProductFlag {

        /**
         * 普耗产品
         */
        public static final int PH = 1;

        /**
         * 高值产品
         */
        public static final int GZ = 2;

    }

    /**
     * 采购进度
     *
     * @author
     */
    public static final class PurchaseRate {
        /**
         * 10-库房确认
         */
        public static final Integer StorageConfirm = 10;
        /**
         * 20-采购确认
         */
        public static final Integer PurchaseConfirm = 20;
        /**
         * 30-订单确认
         */
        public static final Integer OrderConfirm = 30;
        /**
         * 40-部分到货
         */
        public static final Integer PartArrival = 40;
        /**
         * 90-到货
         */
        public static final Integer finish = 90;
    }

    /**
     * 拣货单状态
     */
    public static final class ApplyPickFstate {
        /**
         * 10-新建
         */
        public static final Integer NEW_BUILT = 10;
        /**
         * 79-异常结束 shiyong 2018/11/13
         */
        public static final Integer ABNORMAL_END = 79;
        /**
         * 80-完结
         */
        public static final Integer FINISH = 80;
        /**
         * 81-手动完结
         */
        public static final Integer MANUAL_FINISH = 81;
    }

    /**
     * 拣货进度
     *
     * @author
     */
    public static final class PickRate {
        /**
         * 10-库房确认
         */
        public static final Integer StorageConfirm = 10;
        /**
         * 90-完结
         */
        public static final Integer finish = 90;
    }

    /**
     * 中心库房“出库方式”<br/>
     * tb_outport表的outmode字段
     */
    public static final class OutMode {
        /**
         * 手工出库
         */
        public static final String MANUAL = "11";
        /**
         * 申领出库
         */
        public static final String DISTRIBUTION = "12";
        /**
         * 退货出库
         */
        public static final String BACK_FORG = "13";
        /**
         * 跟台高值出库
         */
        public static final String HIGH_OUT = "14";
        /**
         * 常备高值出库
         */
        public static final String OPER_OUT = "15";
        /**
         * 调拨出库
         */
        public static final String ALLOT = "07";
    }

    /**
     * 科室库房“出库方式
     * 11手工出库、12申领出库、13普耗退库、14跟台高值退库、15常备高值退库 16登记消耗
     */
    public static final class SecOutMode {
        /**
         * 手工出库
         */
        public static final String MANUAL = "11";
        /**
         * 申领出库
         */
        public static final String DISTRIBUTION = "12";
        /**
         * 普耗退库
         */
        public static final String BACK_STORAGE = "13";
        /**
         * 跟台高值退库
         */
        public static final String OPER_BACK_STORAGE = "14";
        /**
         * 常备高值退库
         */
        public static final String HIGH_BACK_STORAGE = "15";
        /**
         * 登记消耗
         */
        public static final String REGISTER_CONSUMER = "16";
    }

    /**
     * “入库类型”
     * TODO 字典是否按2张表区分开
     * tb_import表 INTYPE字段
     * tb_dimport表 INTYPE字段
     */
    public static final class InType {
        /**
         * 申购入库
         */
        public static final String APPLY_PURCHASE = "01";
        /**
         * 补货
         */
        public static final String REPLENISHMENT = "02";
        /**
         * 结算入库
         */
        public static final String BALANCE = "03";
        /**
         * 盘盈入库
         */
        public static final String INVENTORY_PROFIT = "04";
        /**
         * 初始化入库 (期初入库)
         */
        public static final String INITIALIZATION = "05";
        /**
         * 退货
         */
        public static final String RETURN_GOODS = "06";
        /**
         * 调拨入库
         */
        public static final String ALLOT = "07";
        /**
         * 备货入库
         */
        public static final String STOCKUP = "08";
        /**
         * 赠送入库
         */
        public static final String GIVE = "09";
        /**
         * 骨科产品入库（手术跟台业务）
         */
        public static final String GK = "10";
    }


    /**
     * 入库类型 （迭代版本用的类型）
     */
    public static final class importType {
        /**
         * 1普耗采购、
         */
        public static final String COMMON_PURCHASE = "pusrchaseImport";
        /**
         * 2常备高值
         */
        public static final String COMMON_HIGH = "commonHigh";
        /**
         * 3跟台高值
         */
        public static final String FOLLOW_HIGH = "followHigh";
        /**
         * 手工入库
         */
        public static final String HAND_IN = "handImport";

        /**
         * 期初入库
         */
        public static final String START_IMPORT = "startImport";
        /**
         * 5赠送入库
         */
        public static final String GIVE_IN = "giveImport";
        /**
         * 6手工赠送入库
         */
        public static final String HAND_AND_GIVE = "handGiveImport";
        /**
         * 7退库入库
         */
        public static final String EXIT_IN = "exitImport";
        /**
         * 8拒收入库
         */
        public static final String REJECT_IN = "rejectImport";
        /**
         * 9常备高值退库
         */
        public static final String COMMON_HIGH_EXIT = "commonHighExit";
        /**
         * 10跟台高值退库
         */
        public static final String FOLLOW_HIGH_EXIT = "followHighExit";

        /**
         * 盘盈入库
         */
        public static final String PROFIT_IN = "profitImport";

        /**
         * 申购入库
         */
        public static final String APPLY_PURCHASE = "applyPurchase";
        /**
         * 补货入库
         */
        public static final String REPLENISHMENT = "replenishment";
    }

    /**
     * 二级库入库类型 （迭代版本用的类型）
     */
    public static final class secondiImportType {
        /**
         * 1普耗采购、申领
         */
        public static final String COMMON_PURCHASE = "secondPurchaseImport";
        /**
         * 2常备高值
         */
        public static final String COMMON_HIGH = "secondCommonImport";
        /**
         * 3跟台高值
         */
        public static final String FOLLOW_HIGH = "secondFollowHighImport";
        /**
         * 期初入库
         */
        public static final String START_IMPORT = "secondStartImport ";
        /**
         * 5赠送入库
         */
        public static final String GIVE_IN = "giveImport";
        /**
         * 科室退库入库
         */
        public static final String administrativeExit = "administrativeExit ";
    }

    /**
     * “入库方式”
     * TODO 字典是否按2张表区分开
     * tb_import表 INMODE字段
     * tb_dimport表 INMODE字段
     */
    public static final class InMode {
        /**
         * 采购入库
         */
        public static final String PURCHASING_WAREHOUSING = "01";
        /**
         * 盘盈入库
         */
        public static final String INVENTORY_PROFIT = "04";
        /**
         * 初始化入库
         */
        public static final String INITIALIZATION = "05";
        /**
         * 退货
         */
        public static final String RETURN_GOODS = "06";
        /**
         * 调拨入库
         */
        public static final String ALLOT = "07";
        /**
         * 备货入库
         */
        public static final String STOCKUP = "08";
        /**
         * 赠送入库
         */
        public static final String GIVE = "09";
        /**
         * 骨科产品入库（手术跟台业务）
         */
        public static final String GK = "10";

        /**
         * 暂存入库
         */
        public static final Integer STAGED = 11;

        /**
         * 拒收
         */
        public static final Integer REJECT = 12;
    }

    /**
     * 入库类型
     */
    public static final Map<Integer, String> inStoreTypes = new HashMap<Integer, String>() {{
        put(11, "暂存入库");
        put(12, "拒收入库");
    }};

    /**
     * 盘点单状态  0盘点中、1待审核、2审核通过、3审核不通过
     */
    public static final class PdFstate {
        /**
         * 0盘点中
         */
        public static final Integer AWAIT_CHECK = 0;
        /**
         * 1待审核
         */
        public static final Integer AWAIT_AUDIT = 1;
        /**
         * 2审核通过
         */
        public static final Integer AUDIT_AGREE = 2;
        /**
         * 3审核不通过
         */
        public static final Integer AUDIT_REJECT = 3;
    }

    /**
     * 导出一级库房入库明细对应字段 财务模块
     */
    public final static Map<String, String> importDetailExcelMap = new HashMap<String, String>() {{
        put("InNo", "入库单");
        put("inmodeName", "入库分类");
        put("typeName", "物资分类");
        put("styleName", "财务分类");
        put("materialName", "产品名称");
        put("spec", "规格");
        put("fmodel", "型号");
        put("purchaseUnit", "采购单位");
        put("rksl", "数量");
        put("pRetailPrice", "价格");
        put("money", "金额");
        put("produceName", "生产商");
        put("fOrgName", "供应商");
        put("inTemp", "入库温度");
        put("flot", "生产批号");
    }};

    /**
     * 是否赠送
     */
    public static final class GiveFlag {
        /**
         * 01、是
         */
        public static final String YES = "01";
        /**
         * 00、否
         */
        public static final String NO = "00";
    }

    /**
     * 导出二级库房入库明细对应字段
     */
    public final static Map<String, String> dImportDetailExcelMap = new HashMap<String, String>() {{
        put("InNo", "入库单");
        put("inmodeName", "入库分类");
        put("isSuitName", "类型");
        put("qrcode", "二维码");
        put("materialName", "产品名称");
        put("geName", "通用名称");
        put("geFqun", "通用简码");
        put("suitName", "组件名称");
        put("spec", "规格");
        put("fmodel", "型号");
        put("useUnit", "领用单位");
        put("rksl", "入库数量");
        put("uRetailPrice", "单价");
        put("money", "金额");
        put("flot", "生产批号");
        put("prodDate", "生产日期");
        put("usefulDate", "有效期");
        put("productionPlace", "生产产地");
        put("registerNo", "证件号");
        put("fbarcode", "主条码");
        put("fbarcodeSec", "批次条码");
        put("brand", "品牌");
        put("produceName", "生产商");
        put("fOrgName", "供应商");
        put("createUsername", "操作员");
        put("inTime", "入库时间");
        put("sendNo", "配送单号");
        put("orderNo", "订单号");
    }};

    /**
     * 导出二级库房出库明细对应字段
     */
    public final static Map<String, String> dOutportDetailExcelMap = new HashMap<String, String>() {{
        put("outNo", "出库单号");
        put("inNo", "入库单号");
        put("outMode", "出库分类");
        put("storageName", "库房");
        put("deptName", "领用科室");
        put("operationRoom", "手术间");
        put("doctorName", "医生");
        put("lyr", "领用人");
        put("deptAddress", "科室地址");
        put("createUserName", "操作员");
        put("outDate", "出库时间");
        put("qrcode", "二维码");
        put("materialName", "产品名称");
        put("geName", "通用名称");
        put("fmodel", "型号");
        put("spec", "规格");
        put("useUnit", "单位");
        put("uRetailPrice", "价格");
        put("number", "出库数量");
        put("money", "金额");
        put("flot", "生产批号");
        put("prodDate", "生产日期");
        put("usefulDate", "有效期");
        put("tfBrand", "品牌");
        put("fOrgName", "供应商");
    }};

    /**
     * 库房级别
     *
     * @author
     *      */
    public static final class StorageLevel {

        /**
         * 采购库房
         */
        public static final int LARGE_STORAGE = 1;

        /**
         * 二级库房
         */
        public static final int SECEND_STORAGE = 2;

        /**
         * 科室库房
         */
        public static final int DEPT_STORAGE = 3;

    }

    /**
     * 保存/提交 动作标识
     *
     * @author
     *      */
    public static final class SaveSubmitFlag {

        /**
         * 保存
         */
        public static final int SAVE = 1;

        /**
         * 提交
         */
        public static final int SUBMIT = 2;

    }

    /**
     * 二维码状态
     */
    public static final class QrcodeFstate {
        /**
         * 新建
         */
        public static final Integer created = 0;
        /**
         * 已入库
         */
        public static final Integer imported = 20;
        /**
         * 退货
         */
        public static final Integer sendback_to_supplier = 30;
        /**
         * 已出库
         */
        public static final Integer outported = 40;
        /**
         * 出库退库
         */
        public static final Integer sendback_to_storage = 50;
        /**
         * 已登记
         */
        public static final Integer registed = 60;
        /**
         * 已计费
         */
        public static final Integer charged = 70;
        /**
         * 计费失败
         */
        public static final Integer chargedFail = 71;
        /**
         * 已退费
         */
        public static final Integer refunded = 80;
        /**
         * 退费失败
         */
        public static final Integer refundedFail = 81;

        /**
         * 31.中心库验收(二级库待验收)
         */
        public static final  Integer checkStorage =31;
        /**
         *  32.中心库拒收
         */
        public static  final Integer rejectStorage =32;

        /**
         *  33二级库验收
         */ public static final  Integer checkSecondStorage =33;
        /**
         *  34 二级库拒收
         */
        public static  final Integer rejectSecondStorage =34;



    }

    //手术申请患者信息是否是接口录入
    public static final class IsInterface {
        //手动录入
        public static final String isInterface = "1";
        //接口录入
        public static final String noInterface = "2";
    }

    /**
     * 高值退库状态
     *
     * @author
     */
    public static final class GZBackStatus {

        /**
         * 带退库
         */
        public static final String WAIT_BACK = "01";

        /**
         * 已退库
         */
        public static final String OVER_BACK = "02";

        /**
         * 已退费
         */
        public static final String COST_BACK = "03";
    }

    /**
     * 開始日期
     * 結束日期
     * 格式化
     */
    public static final class FDateFormat {

        /**
         * 開始日期
         */
        public static final String startDateFormat = "00:00:00";
        /**
         * 結束日期
         */
        public static final String endDateFormat = "23:59:59";

    }

    /**
     * 预算周期类型
     */
    public static final class BudgetPeriodType {
        /**
         * 01年
         */
        public static final Integer YEAR = 01;

        /**
         * 02月
         */
        public static final Integer MONTH = 02;
    }

    /**
     * 预算责任主体类型
     */
    public static final class BudgetSubject {
        /**
         * 01科室
         */
        public static final String DEPT = "01";

        /**
         * 02库房
         */
        public static final String STORAGE = "02";
    }

    /**
     * 付款计划状态
     */
    public static final class PayPlanStatus {

        /**
         * 草稿
         */
        public static final String DRAFT = "1";

        /**
         * 带审核
         */
        public static final String CHECK = "2";

        /**
         * 审核完成
         */
        public static final String OVER = "3";

        /**
         * 驳回
         */
        public static final String REJECT = "4";

    }

    /**
     * 付款计划提交标示
     *
     * @author
     */
    public static final class PayPlanTag {

        public static final String SAVE_TAG = "1";

        public static final String CONFIRM_TAG = "2";
    }

    /**
     * HisServiceTag
     * 标示
     *
     * @author
     */
    public static final class HisServiceTag {

        /**
         * 开发环境
         */
        public static String HIS_DEVELOP = "develop";

        /**
         * 武汉长江航运医院
         */
        public static String HIS_CHYY = "chyy";

    }

    /**
     * 发布未发布
     */
    public static final class ReleaseFstate {
        /**
         * 1、未发布
         */
        public static Integer NOT_RELEASE = 1;

        /**
         * 2、已发布
         */
        public static Integer PUBLISHED = 2;
    }

    /**
     * 档案来源 1初始化 2购入
     */
    public static final class SourceFiles {
        /**
         * 1、初始化
         */
        public static Integer INSERT = 1;

        /**
         * 2、购入
         */
        public static Integer BUY = 2;
    }

    /**
     * 设备产品来源 1本地 2  医供宝
     */
    public static final class AssetsBaseSource {
        /**
         * 1、本地
         */
        public static Integer LOCAL = 1;

        /**
         * 2、  医供宝
         */
        public static Integer ygb = 2;
    }

    /**
     * 是、否状态
     */
    public static final Map<String, Integer> EquipmentIsNot = new HashMap<String, Integer>() {{
        put("是", 1);
        put("否", 2);
    }};

    /**
     * 购入方式 1招标采购 2议价采购
     */
    public static final Map<String, Integer> EquipmentBuyType = new HashMap<String, Integer>() {{
        put("招标采购", 1);
        put("议价采购", 2);
    }};

    /**
     * 1有，2无状态
     */
    public static final Map<String, Integer> EquipmentWithout = new HashMap<String, Integer>() {{
        put("有", 1);
        put("无", 2);
    }};

    /**
     * 折旧方法
     * 1平均年限法
     * 2工作量法
     * 3双倍余额递减法
     * 4年数总和法
     */
    public static final class DepreciationType {
        /**
         * 1、平均年限法
         */
        public static Integer AVG_YEAR = 1;

        /**
         * 2、工作量法
         */
        public static Integer WORKLOAD = 2;

        /**
         * 3、双倍余额递减法
         */
        public static Integer DOUBLE_REMAINDER = 3;

        /**
         * 4、年数总和法
         */
        public static Integer YEAR_SUM = 4;
    }

    /**
     * 资产原值类型 自筹资金1  财政拨款2  科研经费3  教学资金4  接收捐赠5  其他6
     */
    public final static class AssetsPayType {
        /**
         * 1、自筹资金原值
         */
        public static final Integer selfraisedfunds = 1;
        /**
         * 2、财政拨款原值
         */
        public static final Integer fiscalAppropriation = 2;
        /**
         * 3、科研经费原值
         */
        public static final Integer scientificResearchFunds = 3;
        /**
         * 4、教学资金原值
         */
        public static final Integer teachingFunds = 4;
        /**
         * 5、科研经费原值
         */
        public static final Integer receiveDonations = 5;
        /**
         * 6、其他原值
         */
        public static final Integer otherOriginalValue = 6;

    }

    /**
     * 设备产品来源 1本地 2  医供宝
     */
    public static final class AssetsRecordFstate {
        /**
         * 1、本地
         */
        public static Integer LOCAL = 1;

        /**
         * 2、  医供宝
         */
        public static Integer ygb = 2;
    }

    /**
     * 状态 1正常 2报废
     */
    public static final class AssetsRecordNormalFstate {
        /**
         * 1、正常
         */
        public static Integer NORMAL = 1;

        /**
         * 2、报废
         */
        public static Integer SCRAP = 2;
    }

    /**
     * 使用状态 1运行， 2借出 ，3维修 ， 4保养， 5报废中， 6已报废
     */
    public static final class AssetsRecordUseFstate {
        /**
         * 1、运行
         */
        public static Integer NORMAL = 1;
        /**
         * 2、借出
         */
        public static Integer LEND = 2;
        /**
         * 3、维修
         */
        public static Integer MAINTENANCE = 3;
        /**
         * 4、保养
         */
        public static Integer MAINTAIN = 4;
        /**
         * 5、报废中
         */
        public static Integer WAIT_SCARP = 5;
        /**
         * 6、已报废
         */
        public static Integer SCARP = 6;
    }

    /**
     * 同步我的产品-详情-保存 类型-
     */
    public static final class updateProductType {
        /**
         * productInfo产品信息
         */
        public static String PRODUCT_INFO = "productInfo";
        /**
         * supplierInfo供应商信息
         */
        public static String SUPPLIER_INFO = "supplierInfo";
        /**
         * gwInfo产品信息
         */
        public static String GW_INFO = "gwInfo";

    }

    /**
     * 维修状态 1草稿 2待指派 3待接修 4维修中 5已驳回 6已撤销 7待验收 8验收不通过 9完成 10作废
     */
    public static final class RepairOrderFstate {
        /**
         * 1草稿
         */
        public static Integer DRAFT = 1;
        /**
         * 2待指派
         */
        public static Integer ASSIGNED = 2;
        /**
         * 3待接修
         */
        public static Integer WAITING = 3;
        /**
         * 4维修中
         */
        public static Integer MAINTENANCE = 4;
        /**
         * 5已驳回
         */
        public static Integer REFUSED = 5;
        /**
         * 6已撤销
         */
        public static Integer WITHDRAWN = 6;
        /**
         * 7待验收
         */
        public static Integer ACCEPTANCE = 7;
        /**
         * 8验收不通过
         */
        public static Integer ACCEPTANCE_REJECTION = 8;
        /**
         * 9完成
         */
        public static Integer FINISH = 9;
        /**
         * 10作废
         */
        public static Integer INVALID = 10;
    }

    /**
     * 维修单验收状态
     */
    public static final class RepairOrderAcceFstate {
        /**
         * 1验收通过
         */
        public static Integer CHECK_PASSED = 1;
        /**
         * 2验收不通过
         */
        public static Integer CHECK_NO_PASSED = 2;
    }

    /**
     * 转科状态 1草稿 2待转科 3已转科 4已撤销 5已作废 6驳回
     */
    public static final class TransferFstate {
        /**
         * 1草稿
         */
        public static Integer DRAFT = 1;
        /**
         * 2待转科
         */
        public static Integer NO_CHANGE = 2;
        /**
         * 3已转科
         */
        public static Integer CHANGE = 3;
        /**
         * 4已撤销
         */
        public static Integer WITHDRAWN = 4;
        /**
         * 5已作废
         */
        public static Integer INVALID = 5;
        /**
         * 6驳回
         */
        public static Integer REFUSED = 6;
    }

    /**
     * 转科状态 1草稿 2待确认 3已确认 4驳回 5已撤销 6已作废
     */
    public static final class PurchasePlanDetailFstate {
        /**
         * 1草稿
         */
        public static Integer DRAFT = 1;
        /**
         * 2待确认
         */
        public static Integer AWAIT_AGGREE = 2;
        /**
         * 3已确认
         */
        public static Integer ALREADY_AGGREE = 3;
        /**
         * 4驳回
         */
        public static Integer REFUSED = 4;
        /**
         * 5已撤销
         */
        public static Integer WITHDRAWN = 5;
        /**
         * 6已作废
         */
        public static Integer INVALID = 6;
    }

    /**
     * 产品扩展属性名称
     */
    public static final class ProductAttributeName {

        /**
         * 条形码
         */
        public static final String FBARCODE = "2";

    }

    /**
     * 设备配送单 状态  1草稿  2待发货  3待验收 4验收通过  5验收不通过 6交易完成
     */
    public static final class AssetsDeliveryFstate {
        /**
         * 1草稿
         */
        public static Integer DRAFT = 1;
        /**
         * 2待发货
         */
        public static Integer AWAIT_SEND = 2;
        /**
         * 3待验收
         */
        public static Integer AWAIT_CHECK = 3;
        /**
         * 4验收通过
         */
        public static Integer CHECK_PASSED = 4;
        /**
         * 5验收不通过
         */
        public static Integer CHECK_NO_PASS = 5;
        /**
         * 6交易完成
         */
        public static Integer FINISH = 6;
    }

    /**
     * 设备入库类型  1采购入库  4盘盈入库  5初始化入库  6退货入库
     */
    public static final class AssetsImportIntype {
        /**
         * 采购入库
         */
        public static final Integer PURCHASING_WAREHOUSING = 1;
        /**
         * 盘盈入库
         */
        public static final Integer INVENTORY_PROFIT = 4;
        /**
         * 初始化入库
         */
        public static final Integer INITIALIZATION = 5;
        /**
         * 退货
         */
        public static final Integer RETURN_GOODS = 6;
    }

    /**
     * 设备配送类型 1入库  2越库
     */
    public static final class AssetsSendType {
        /**
         * 1入库
         */
        public static final Integer IN = 1;
        /**
         * 2越库
         */
        public static final Integer OUT = 2;
    }

    /**
     * 设备出库方式  2科室领用出库  5退库出库
     */
    public static final class AssetsImportOuttype {
        /**
         * 2科室领用出库
         */
        public static final Integer CONSUM = 2;
        /**
         * 5退库出库
         */
        public static final Integer BACK = 5;
    }

    /**
     * 培训档案类型  00培训记录  01应急演练
     */
    public static final class TrainingDocumentType {
        /**
         * 00培训记录
         */
        public static final Integer TRAIN_DOCUMENT = 00;
        /**
         * 01应急演练
         */
        public static final Integer EMERGENCY_DRILL = 01;
    }

    /**
     * 保养计划 状态 1草稿 2待执行 3已执行 4已关闭
     */
    public static final class MaintainPlanFstate {
        /**
         * 1草稿
         */
        public static Integer DRAFT = 1;
        /**
         * 2待执行
         */
        public static Integer AWAIT_PERFORM = 2;
        /**
         * 3已执行
         */
        public static Integer PERFORMED = 3;
        /**
         * 4已关闭
         */
        public static Integer CLOSED = 4;
    }

    /**
     * 状态 1草稿、2待报废、3已撤销、4已作废、5报废完成、6已驳回
     */
    public final static class ScarpFstate {
        /**
         * 1草稿
         */
        public static final Integer DRAFT = 1;
        /**
         * 2待报废
         */
        public static final Integer WAIT_SCARP = 2;
        /**
         * 3已撤销
         */
        public static final Integer CANCELLED = 3;
        /**
         * 4已作废
         */
        public static final Integer CLOSE = 4;
        /**
         * 5报废完成
         */
        public static final Integer SCARP = 5;
        /**
         * 6已驳回
         */
        public static final Integer REJECT = 6;
    }

    public final static class supplyTender {
        /**
         * 待审核
         */
        public static final Integer AUDIT_ING = 1;
        /**
         * 初审
         */
        public static final Integer AUDIT_START = 2;
        /**
         * 终审
         */
        public static final Integer AUDIT_END = 3;
        /**
         * 审核通过
         */
        public static final Integer AUDIT_ED = 5;
        /**
         * 审核不通过
         */
        public static final Integer AUDIT_FAIL = 4;

    }

    /**
     * 是否默认供应商
     */
    public final static class supplyDto {
        /**
         * 是
         */
        public static final Integer IS_DEFAULT = 1;
        /**
         * 否
         */
        public static final Integer NOT_DEFAULT = 0;

    }

    /**
     * 索引库系统地址
     */
    public static final String INDEX_PATH = "./java/luence"; //相对路径

    /**
     * 匹配对码
     */
    public final static class flag {
        /**
         * 否
         */
        public static final Integer IS_DEFAULT = 0;
        /**
         * 是
         */
        public static final Integer NOT_DEFAULT = 1;
    }

    /**
     * 是否需要运营审核
     */
    public final static class OperationStatus {
        /**
         * 否
         */
        public static final Integer IS_DEFAULT = 0;
        /**
         * 是
         */
        public static final Integer NOT_DEFAULT = 1;
    }

    /**
     * 企业信息和证件审核状态
     */
    public final static class CompanyCertStatus {
        /**
         * 待提交
         */
        public static final Integer AUDIT_WAIT = 0;
        /**
         * 待平台审核
         */
        public static final Integer AUDIT_SYSTEM = 1;
        /**
         * 平台不通过
         */
        public static final Integer AUDIT_SYSTEM_FAIL = 2;
        /**
         * 待医院审核
         */
        public static final Integer AUDIT_HOSPITAL_START = 3;
        /**
         * 医院不通过
         */
        public static final Integer AUDIT_HOSPITAL_FAIL = 4;
        /**
         * 医院通过
         */
        public static final Integer AUDIT_HOSPITAL_PASS = 5;
        /**
         * 平台通过
         */
        public static final Integer AUDIT_SYSTEM_PASS = 6;
    }

    /**
     * 企业证件类型
     */
    public enum CompanyCertType {

        yet_match(1, "已对码"),

        no_match(0, "未对码"),

        reg_no(0, "正常"),
        def_reg_no(1, "缺失"),
        past_reg_no(2, "过期");

        private Integer type;

        private String typeName;

        public Integer getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        CompanyCertType(Integer type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }
    }

    //厂商证件
    //0缺失 ，1 正常，2 过期
    public enum CertTypeStatus {
        reg_no(0, "缺失"),
        def_reg_no(1, "正常"),
        past_reg_no(2, "过期");

        private final Integer value;
        private final String description;

        public Integer getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

        CertTypeStatus(Integer value, String description) {
            this.value = value;
            this.description = description;
        }
    }

    /**
     * 供应商授权书状态
     */
    public final static class AuthorizationStatus {

        /**
         * 是时间段
         */
        public static final Integer IS_TIME_QUANTUM = 1;

        /**
         * 不是时间段,长期
         */
        public static final Integer NOT_TIME_QUANTUM = 2;

        /**
         * 授权书正常状态
         */
        public static final String NORMAL_STATUS_NAME = "正常";
        public static final Integer NORMAL_STATUS = 0;

        /**
         * 授权书过期状态
         */
        public static final String PAST_STATUS_NAME = "过期";
        public static final Integer PAST_STATUS = 1;

        /**
         * 关联状态,默认显示为1
         */
        public static final Integer RELATION_STATUS = 1;

        /**
         * 删除标识,正常
         */
        public static final Integer NO_DEL_FLAG = 0;

        /**
         * 删除标识,已删除
         */
        public static final Integer YET_DEL_FLAG = 1;

        /**
         * 返回成功标识
         */
        public static final Integer SUCCESS_FLAG = 1;
    }

    /**
     * 供应商授权类型
     */
    public enum AuthorizationType {

        manufacturer_type(0, "生产商"),

        agent_type(1, "代理商"),

        supply_type(2, "供应商");

        private Integer type;

        private String typeName;

        public Integer getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        AuthorizationType(Integer type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }

    }

    /**
     * 记录企业证件的，变化内容
     */
    public enum CompanyCertChange {

        cert_c0("c0", "更新营业执照"),

        cert_c1("c1", "更新组织机构代码证"),

        cert_c2("c2", "更新税务登记证"),

        cert_c3("c3", "更新医疗器械经营许可证"),
        cert_c4("c4", "更新医疗器械生产许可证"),
        cert_c5("c5", "更新第二类医疗器械经营备案凭证"),
        cert_c6("c6", "更新第一类医疗器械生产备案凭证"),
        cert_c7("c7", "更新第一类医疗器械生产企业登记表"),
        cert_c8("c8", "更新商业登记条例"),
        cert_c9("c9", "更新外国（地区）企业常驻代表机构登记证"),
        cert_c10("c10", "更新药品经营许可证"),
        cert_c11("c11", "更新药品生产许可证"),
        cert_c12("c12", "更新消毒产品生产企业卫生许可证"),
        cert_c13("c13", "更新食品卫生许可证"),
        cert_c14("c14", "更新食品流通许可证"),
        cert_c15("c15", "更新冷链运输证明");


        private String type;

        private String typeName;

        public String getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        CompanyCertChange(String type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }

    }

    /**
     * 记录供应商信息，变化内容
     */
    public enum SupplyInfoChange {

        orgName("orgName", "更新企业名称"),

        contactName("contactName", "更新企业法人"),

        contactMobile("contactMobile", "更新联系电话"),

        province("province", "更新地址"),
        city("city", "更新地址"),
        district("district", "更新地址"),
        tfAddress("tfAddress", "更新详细地址"),
        depositBankAddr("depositBankAddr", "更新开户银行"),
        depositBankAccount("depositBankAccount", "更新银行账号");


        private String type;

        private String typeName;

        public String getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        SupplyInfoChange(String type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }

    }

    public enum AgentChange {

        companyName("companyName", "更新企业名称"),

        phoneNumber("phoneNumber", "更新联系电话"),

        address("address", "更新地址"),

        province("province", "更新地址"),
        city("city", "更新地址"),
        district("district", "更新地址");


        private String type;

        private String typeName;

        public String getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        AgentChange(String type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }

    }

    /**
     * 审核类型(00产品新增  01产品修改  02过期换证  03证件更新)
     */
    public enum AduitType {
        product_type_insert(0, "产品新增"),
        product_type_update(1, "产品修改"),
        cert_past_update(2, "过期换证"),
        cert_update(3, "证件更新"),
        product_price(4, "产品调价"),
        product_stop(5, "产品停供"),
        authorization_update(6,"授权更新");
        private final Integer type;

        private final String typeName;

        public Integer getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        AduitType(Integer type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }
    }

    public enum AduitFrom {
        hospital_type(1, "医院"),
        platfrom_type(2, "平台");

        private Integer type;

        private String typeName;

        public Integer getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        AduitFrom(Integer type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }
    }

    /**
     * 审核状态(1医院待审核 2医院不通过 3医院通过,4平台待审核 5平台不通过 6平台通过 )
     */
    public enum AduitStatus {

        draft(0, "待提交"),
        hospital_auditing(1, "医院待审核"),
        hospital_fail(2, "医院不通过"),
        hospital_pass(3, "医院通过"),
        platform_auditing(4, "平台待审核"),
        platform_fail(5, "平台不通过"),
        platform_pass(6, "平台通过"),
        discard(7, "单据作废");

        private Integer type;

        private String typeName;

        public Integer getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        AduitStatus(Integer type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }
    }

    /**
     * 审核状态(0：待审核、1：审核通过、2：审核不通过）
     */
    public enum statusType {
        auditing(0, "待审核"),
        pass(1, "审核通过"),
        fail(2, "审核不通过"),
        port(3,"部分通过");

        private Integer type;

        private String typeName;

        public Integer getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        statusType(Integer type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }
    }


    /**
     * 用户注册审核信息
     */
    public final static class UserRegisterStatus {

        /**
         * 待审核
         */
        public static final Integer WAIT_AUDIT = 0;

        /**
         * 审核通过
         */
        public static final Integer AUDIT_PASS = 1;


        /**
         * 审核不通过
         */
        public static final Integer AUDIT_FAIL = 2;

    }

    /**
     * 流程配置开关
     */
    public final static class flowFstate {
        /***
         * 1开
         */
        public static final Integer OPEN = 1;
        /**
         * 0关
         */
        public static final Integer CLOSE = 0;
    }


    /**
     * 发送邮箱信息
     * 0:获取验证码
     */
    public static final Map<Integer, String> senderEmailMsg = new HashMap<Integer, String>() {{
        put(0, "您好，您的医供宝验证码是：VERIFCODE，请在10分钟以内输入有效。");
        put(1, "您好，您注册的医供宝账号已通过，希望您使用愉快~");
        put(2, "您好，您注册的医供宝账号未通过审核，审核意见：AUDITOPINION,请登录医供宝网页重新注册一次，谢谢配合。");
        put(3, "您好，您已变更密码，账号为：ADMIN，若不是本人操作，请联系运营人员。");
    }};

    public static final Map<Integer, String> AduitStatusMap = new HashMap<Integer, String>() {{
        put(0, "待提交");
        put(1, "医院待审核");
        put(2, "医院通过");
        put(3, "医院不通过");
        put(4, "平台待审核");
        put(5, "平台不通过");
        put(6, "平台通过");
    }};

    /**
     * 是、否冷藏状态
     */
    public static final Map<Integer, String> IsRefrigerate = new HashMap<Integer, String>() {{
        put(0, "否");
        put(1, "是");
    }};

    /**
     * 是、否冷藏状态
     */
    public static final Map<String, Integer> IsRefrigerateFallMap = new HashMap<String, Integer>() {{
        put("否", 0);
        put("是", 1);
    }};

    /**
     * 加密文件key值
     */
    public static final int ENCRYPTED_KEY = 321465;

    /**
     * 药品订单
     */
    public static final Map<String, String> MedicalOrderTypeMap = new HashMap<String, String>() {{
        put("普通订单", "ORDINARY_ORDER");
        put("特需药订单", "SPECIAL_MEDICINE_ORDER");
        put("管制药订单", "CONTROLS_MEDICINE_ORDER");
    }};

    /**
     * 药品配送单
     */
    public static final Map<String, String> MedicalDeliveryTypeMap = new HashMap<String, String>() {{
        put("普通配送单", "ORDINARY_DELIVERY");
        put("特需药配送单", "SPECIAL_MEDICINE_DELIVERY");
        put("管制药配送单", "CONTROLS_MEDICINE_DELIVERY");
    }};

    /**
     * 药品订单结算方式
     */
    public static final Map<String, String> MedicalOrderJsfsMap = new HashMap<String, String>() {{
        put("寄售", "00");
        put("采购", "01");
    }};


    public enum XyhisEnum {

        /**
         * 订单
         */
        PURCHASE_ORDER("purchaseOrder"),
        /**
         * 主数据
         */
        // TENDER_MATERIAL_MODIFY("tenderMaterialModify"),
        TENDER_MATERIAL_MODIFY("setEquipStock"),
        DELIVERY_INFO("deliveryInfo"),
        DELIVERY_ACCEPT("deliveryAccept"),
        RECEIVER("YGB"),
        SENDER("HIS"),
        ERROR_CODE("CE"),
        ERROR_DESC("失败"),
        SUCCESS_CODE("CA"),
        SUCCESS_DESC("成功");

        private String message;

        public String getMessage() {
            return message;
        }

        XyhisEnum(String message) {
            this.message = message;
        }
    }

    public static final String XyhisUrl = "http://192.168.2.103:8081/medicinal-960/webservice/hisWebService?wsdl";


    public final static class Constants {
        public final static String kCode_Success = "0";          //  成功
        public final static String kCode_Fail = "1";          //  失败
        public final static String kCode_SessionError = "1000";       //  登陆超时
    }

    public final static String truncate = "truncate";

    /**
     * 切片大小
     */
    public final static long TruncateSize = 1 * 1024 * 1024;

    /**
     * 招标产品同步 1新增未同步 2编辑未同步 9已同步
     */
    public final static class SynchFstate {
        public final static Integer INSERT_SYNCH = 1;
        public final static Integer UPDATE_SYNCH = 2;
        public final static Integer SYNCH_SUCCESS = 3;
    }


    /**
     * 数据来源 1采购平台 2本地
     */
    public final static class DateSources {
        public final static Integer YGB_PLATFORM = 1;
        public final static Integer LOCAL = 2;
    }

    //参数配置
    public final static String parameterConfig = "parameterConfig";

    /**
     * 拣货明细状态 1待拣货 2已拣货
     */
    public final static class PickingDetailFstate {
        public final static Integer NOT_PICKING = 1;
        public final static Integer PICKING = 2;
    }

    /**
     * 出库单状态 1待复核 2部分复核 3已完成 4复核驳回
     */
    public final static class OutportFstate {
        /**
         * 1待复核
         */
        public static final Integer NOT_CHECK = 1;
        /**
         * 2部分复核
         */
        public static final Integer PORTTION_CHECK = 2;
        /**
         * 3已完成
         */
        public static final Integer CHECK_PASS = 3;
        /**
         * 4复核驳回
         */
        public static final Integer CHECK_NO_PASS = 4;

        public static final Map<Integer, String> outportFstateMap = new HashMap<Integer, String>() {{
            put(1, "待复核");
            put(2, "部分复核");
            put(3, "已完成");
            put(4, "复核驳回");
        }};
    }

    /**
     * 拣货单类型 manualOutput手工出库 distributionOutput申领出库 back退货 giveBack还货 automaticGiveBack自动还货
     * backStorage普耗退库 secondGiveBack普耗还货 standingHighGiveBack常备高值还货 followHighGiveBack跟台高值还货
     */
    public final static class PickingType {
        public final static String manualOutput = "manualOutput";
        public final static String distributionOutput = "distributionOutput";
        public final static String back = "back";
        public final static String giveBack = "giveBack";
        public final static String automaticGiveBack = "automaticGiveBack";
        public final static String backStorage = "backStorage";
        public final static String secondGiveBack = "secondGiveBack";
        public final static String standingHighGiveBack = "standingHighGiveBack";
        public final static String followHighGiveBack = "followHighGiveBack";
    }

    /**
     * 状态 1待拣货 2部分拣货 3已完成
     */
    public final static class PickingFstate {
        public final static Integer NOT_PICKING = 1;
        public final static Integer PORTTION_PICKING = 2;
        public final static Integer PICKING = 3;
    }

    /**
     * 退货类型 1退货 2还货 3自动还货
     */
    public final static class BackType {
        public final static Integer BACK = 1;
        public final static Integer GIVE_BACK = 2;
        public final static Integer AUTOMATIC_GIVE_BACK = 3;
    }

    /**
     * 出库明细状态 1待复核 2已复核
     */
    public final static class OutportDetailFstate {
        public final static Integer NOT_CHECK = 1;
        public final static Integer CHECK = 2;
    }

    /**
     * 退货状态 1待下架 2待复核 3部分复核 4已完成 5已驳回
     */
    public final static class NewBackFstate {
        public final static Integer NOT_PICKING = 1;
        public final static Integer NOT_CHECK = 2;
        public final static Integer PART_CHECK = 3;
        public final static Integer CHECK = 4;
        public final static Integer CHECK_NO_PASS = 5;
    }

    /**
     * 验收单类型    5跟台高值退
     */
    public final static class CheckAcceptType {
        /**
         * 1申领
         */
        public final static String AAPLY = "secondPurchaseImport";
        /**
         * 常备高值入库
         */
        public final static String HIGH_IMPORT = "secondCommonImport";
        /**
         * 跟台高值入库
         */
        public final static String FOLLOW_IMPORT = "secondFollowHighImport";
        /**
         * 科室退库
         */
        public final static String DEPT_EXIT = "administrativeExit";
        /**
         * 期初入库
         */
        public final static String START_IMPORT = "secondStartImport";

        /**
         * j拒收入库
         */
        public final static String REJECT = "rejectImport";


    }

    /**
     * 货主类型
     */
    public static class MasterType {
        /**
         * 供应商
         */
        public static Integer APPLY = 1;
        /**
         * 供应商二级库
         */
        public static Integer SECOND_APPLY = 2;
        /**
         * 中心库
         */
        public static Integer CENTER = 3;
        /**
         * 二级库
         */
        public static Integer SECOND = 4;
        /**
         * 科室
         */
        public static Integer OFFICE = 5;

    }

    /**
     * 结算节点
     */
    public static class SetNode {
        /**
         * 一级库
         */
        public static final String ONE_STORAGE_SRTTLE = "oneStorageSettle";

        /**
         * 二级库
         */
        public static final String TWO_STORAGE_SETTLE = "twoStorageSettle";
    }


    /**
     * 上架状态
     */
    public static class ImportStatus {
        /**
         * 待上架
         */
        public static Integer PUTAWAY_ING = 1;
        /**
         * 部分上架
         */
        public static Integer PORTION_PUTAWAY = 2;

        /**
         * 上架完成
         */
        public static Integer PUTAWAY_ED = 3;


    }

    /**
     * 验收状态
     */
    public static class CheckAccpetFstate {
        /**
         * 待验收
         */
        public static Integer CHECK_ING = 1;
        /**
         * 部分验收
         */
        public static Integer CHECK_PORT = 2;
        /**
         * 已验收
         */
        public static Integer CHECK_ED = 3;
        /**
         * 已拒收
         */
        public static Integer CHECK_REJECT = 4;

    }

    /**
     * 参数配置（配置级别）全院级别、中心库级别、二级库级别、科室级别
     */
    public final static class ParameterConfig {
        public final static String hospitalLevel = "hospitalLevel";
        public final static String centralStorageLevel = "centralStorageLevel";
        public final static String twoStorageLevel = "twoStorageLevel";
        public final static String deptLevel = "deptLevel";
    }

    /**
     * 退库类型 1普耗退库 2还货 3常备高值还货 4跟台高值还货
     */
    public final static class BackStorageType {
        public final static Integer BACK = 1;
        public final static Integer GIVE_BACK = 2;
        public final static Integer HIGH_BACK = 3;
        public final static Integer OPER_BACK = 4;
    }

    /**
     * 退货状态 1待下架 2待复核 3部分复核 4已完成 5已驳回 6待退库
     */
    public final static class BackStorageFstate {
        public final static Integer NOT_PICKING = 1;
        public final static Integer NOT_CHECK = 2;
        public final static Integer PART_CHECK = 3;
        public final static Integer CHECK = 4;
        public final static Integer CHECK_NO_PASS = 5;
        public final static Integer NOT_BACK_STORAGE = 1;
    }

    /**
     * 验收目标类型： 1库房验收 2科室验收
     */
    public final static class CheckTargetType {
        public final static Integer STORAGE = 1;
        public final static Integer DEPT = 2;
    }

    /**
     * 1中心库验收 2科室库验收
     */
    public final static class CheckLevelType {
        public final static Integer ONE_STORAGE = 1;
        public final static Integer TWO_STORAGE = 2;
    }

    /**
     * 申请单单据来源 1科室申领、2库房补货
     */
    public final static class ApplySource {
        public final static Integer DEPT = 1;
        public final static Integer STORAGE = 2;
    }

    /**
     * 配货单状态 1待分配、2部分分配、3全部分配、4已完成
     */
    public static final class PickFstate {
        /**
         * 1待分配
         */
        public static final Integer NOT_PICK = 1;
        /**
         * 2部分分配
         */
        public static final Integer PART_PICK = 2;
        /**
         * 3全部分配
         */
        public static final Integer ALL_PICK = 3;
        /**
         * 4已完成
         */
        public static final Integer FINISH = 4;
    }

    /**
     * 单据来源
     */
    public final static class PlanSource {
        /**
         * 库房补货
         */
        public static final Integer STORAGE_MEND = 1;

        /**
         * 2库房直配
         */
        public static final Integer STORAGE_DIRECT = 2;

        /**
         * 3科室直配
         */
        public static final Integer DESK_DIRECT = 3;
    }

    /**
     * 货位移动类型 1手术申请 2手工移动 3手术归还
     */
    public final static class GoodsLocalAdjustType {
        /**
         * 1手术申请
         */
        public static final Integer OPER_APPLY = 1;

        /**
         * 2手工移动
         */
        public static final Integer MANUAL = 2;

        /**
         * 3手术归还
         */
        public static final Integer RETURN = 3;
    }

    /**
     * 货位移动状态 1待移动 2已完成
     */
    public final static class GoodsLocalAdjustFstate {
        /**
         * 1待移动
         */
        public static final Integer NO_ADJUST = 1;

        /**
         * 2已完成
         */
        public static final Integer FINISH = 2;
    }

    /**
     * 对象类型 tb_apply申请单 tb_goods_local_adjust货位移动单 tb_charge_info患者使用登记单
     */
    public final static class targetType {
        /**
         * tb_apply申请单
         */
        public static final String tb_apply = "tb_apply";

        /**
         * tb_goods_local_adjust货位移动单
         */
        public static final String tb_goods_local_adjust = "tb_goods_local_adjust";

        /**
         * tb_charge_info患者使用登记单
         */
        public static final String tb_charge_info = "tb_charge_info";
        /**
         * tb_plan计划单
         */
        public static final String tb_plan = "tb_plan";
        /**
         * tb_doutport二级库房出库单
         */
        public static final String tb_doutport = "tb_doutport";
    }

    public final static class CompanyCode {
        /**
         * 企业类型
         * (a1:国内代理商 a2:港澳台代理商 a3:国外国内分公司 a4:外国（地区）常驻代表处 p1:境内厂家 p2:境外厂家)
         */
        public static final String A1 = "a1";

        public static final String A2 = "a2";

        public static final String A3 = "a3";

        public static final String A4 = "a4";

        public static final String P1 = "p1";

        public static final String P2 = "p1";


    }

    /**
     * 资质类型： 00供应商证件 01厂家证件 02代理商证件 03业务员授权书
     */
    public static final class OrgInfoCertType {

        /**
         * 00供应商证件
         */
        public static final String SUPPLY = "00";
        /**
         * 01厂家证件
         */
        public static final String PRODUCT = "01";

        public static final String AGENT = "02";

        public static final String AUTHORIZATION = "03";


    }


    /**
     * 入库打包常量定义
     */
    public static final class ImportPacking {

        /**
         * 已完成打包
         */
        public static final Integer YET_PACKING_STATUS = 3;

        /**
         * 部分打包
         */
        public static final Integer PART_PACKING_STATUS = 2;

        /**
         * 待打包
         */
        public static final Integer STAY_PACKING_STATUS = 1;

        /**
         * 明细已打包状态
         */
        public static final Integer DETAIL_YET_STATUS = 2;

        /**
         * 明细未打包状态
         */
        public static final Integer DETAIL_NO_STATUS = 1;

        /**
         * 操作标识打包
         */
        public static final Integer PACKING_FLAG = 1;

        /**
         * 合并包裹
         */
        public static final Integer MERGE_PACKING_FLAG = 2;
        /**
         * 批量打包
         */
        public static final Integer BATCH_PACKING = 2;

        /**
         * 入库打包状态
         */
        public static final Map<Integer, String> importPackingStatus = new HashMap<Integer, String>() {{
            put(1, "待打包");
            put(2, "部分打包");
            put(3, "打包完成");
        }};

        /**
         * 入库打包类型
         */
        public static final Map<Integer, String> importPackingType = new HashMap<Integer, String>() {{
            put(1, "入库打包");
        }};

        /**
         * 返回成功标识
         */
        public static final Integer SUCCESS_FLAG = 1;

    }

    /**
     * 入库上架常量定义
     */
    public static final class ImportPutaway {

        /**
         * 上架完成
         */
        public static final Integer YET_PUTAWAY_STATUS = 3;

        /**
         * 部分上架
         */
        public static final Integer PART_PUTAWAY_STATUS = 2;

        /**
         * 待上架
         */
        public static final Integer STAY_PUTAWAY_STATUS = 1;

        /**
         * 明细待上架
         */
        public static final Integer DETAIL_STAY_PUTAWAY_STATUS = 3;

        /**
         * 明细已上架
         */
        public static final Integer DETAIL_YETPUTAWAY_STATUS = 4;

        /**
         * 拒收类型
         */
        public static final Integer REJECTION_TYPE = 35;

        public static final Map<String, Integer> PUTAWAY_TYPE = new HashMap<String, Integer>() {{
            put("ORDINARY_DELIVERY", 31);
            put("CONTROLS_MEDICINE_DELIVERY", 32);
            put("handImport", 33);
        }};

        public static final Map<Integer, String> QURERY_PUTAWAY_TYPE = new HashMap<Integer, String>() {{
            put(31, "ORDINARY_DELIVERY");
            put(32, "CONTROLS_MEDICINE_DELIVERY");
            put(33, "handImport");
            put(35, "rejectImport");
        }};

        /**
         * 上架类型
         */
        public static final Map<Integer, String> PUTAWAY_TYPE_NAME = new HashMap<Integer, String>() {{
            put(31, "普药");
            put(32, "毒麻");
            put(33, "手工普药");
            put(34, "手工毒麻");
            put(31, "普通药品");
            put(32, "毒麻药品");
            put(33, "手工普药");
            put(34, "手工毒麻");
            put(35, "拒收");
        }};

        public static final Map<Integer, String> PUTAWAY_STATUS = new HashMap<Integer, String>() {{
            put(1, "待上架");
            put(2, "部分上架");
            put(3, "上架完成");
        }};

        /**
         * 返回成功标识
         */
        public static final Integer SUCCESS_FLAG = 1;
    }


    /**
     * 配送单签收状态
     */
    public static final class DeliveryStatus {

        /**
         * 待签收
         */
        public static final Integer SIGN_ING = 1;
        /**
         * 部分签收
         */
        public static final Integer SING_PORT = 2;
        /**
         * 已签收
         */
        public static final Integer SING_ED = 3;
        /**
         * 拒收
         */
        public static final Integer SIGN_REJECT = 4;

    }

    public static final class ShipmentFstate {
        /**
         * 待发货
         */
        public static final Integer FSTATE_ING = 4;

        /**
         * 已发货
         */
        public static final Integer FSTATE_END = 5;
    }

    /**
     * 运送单状态
     */
    public static final class TransportFstate {
        /**
         * 待揽收
         */
        public static final Integer FSTATE_ING = 1;

        /**
         * 运输中
         */
        public static final Integer TRAN_ING = 2;

        /**
         * 已签收
         */
        public static final Integer FSTATE_END = 3;
    }

    /**
     * 拒收单明细状态 1待签收 2已签收
     */
    public static final class RejectionDetailFstate {
        /**
         * 待签收
         */
        public static final Integer SIGN_ING = 1;
        /**
         * 已签收
         */
        public static final Integer SING_ED = 2;

    }

    /**
     * 拒收单状态 状态 0草稿 1待签收、2部分签收、3已签收
     */
    public static final class RejectionFstate {
        /**
         * 草稿
         */
        public static final Integer DRAFT = 0;
        /**
         * 待签收
         */
        public static final Integer SIGN_ING = 1;
        /**
         * 部分签收
         */
        public static final Integer SING_PORT = 2;
        /**
         * 已签收
         */
        public static final Integer SING_ED = 3;

    }

    /**
     * 拒收单状态 状态 0草稿 1待签收、2部分签收、3已签收、4待供应商确认、5待供应商取货、6已完成
     */
    public static final class RejectionSourceFstate {
        /**
         * 草稿
         */
        public static final Integer DRAFT = 0;
        /**
         * 待签收
         */
        public static final Integer SIGN_ING = 1;
        /**
         * 部分签收
         */
        public static final Integer SING_PORT = 2;
        /**
         * 已签收
         */
        public static final Integer SING_ED = 3;
        /**
         * 待供应商确认
         */
        public static final Integer FORG_SEND = 4;
        /**
         * 待供应商取货
         */
        public static final Integer FORG_PICKUP = 5;
        /**
         * 已完成
         */
        public static final Integer FINISH = 6;

    }

    /**
     * 单据来源类型 1仓库收货  2医院拒收
     */
    public static final class DeliveryResource {
        /**
         * 仓库收货
         */
        public static final Integer STORAGE = 1;
        /**
         * 医院拒收
         */
        public static final Integer RORG = 2;

    }

    /**
     * 三方仓退货单状态 1待配货、2已配货
     */
    public static final class StorehouseBackFstate {
        /**
         * 待配货
         */
        public static final Integer SIGN_SEND = 1;
        /**
         * 已配货
         */
        public static final Integer SEND_ED = 2;

    }

    /**
     * 三方仓拣货单类型 1申领 2退货
     */
    public static final class StorehousePickingType {
        /**
         * 申领
         */
        public static final Integer DISTRIBUTION = 1;
        /**
         * 退货
         */
        public static final Integer BACK = 2;

    }

    /**
     * 配送方式
     */
    public static final class TransportType {
        /**
         * 1三方物流
         */
        public static final Integer THREE_TRAN = 1;
        /**
         * 2自营物流
         */
        public static final Integer SELF_TRAN = 2;
        /**
         * 3自提
         */
        public static final Integer SELF_GET = 3;

    }


    /**
     * 三方仓拣货明细状态 1待拣货 2已拣货
     */
    public final static class StorehousePickinggDetailFstate {
        public final static Integer NOT_PICKING = 1;
        public final static Integer PICKING = 2;
    }

    public static final class DistributeStatus {

        /**
         * 待配货
         */
        public static final Integer STAY_DISTRIBUTE = 1;
        /**
         * 已配货
         */
        public static final Integer YET_DISTRIBUTE = 2;

        /**
         * 生成拣货单标识
         */
        public static final Integer BUILD_PICKING = 2;

        /**
         * 返回成功标识
         */
        public static final Integer SUCCESS_FLAG = 1;

        /**
         * 返回错误标识
         */
        public static final Integer ERR_FLAG = 2;

        public static final Map<Integer, String> distributeStatusMaps = new HashMap<Integer, String>() {{
            put(1, "待配货");
            put(2, "已配货");
        }};
    }

    /**
     * 出库常量类
     */
    public static final class OutportConstant {

        /**
         * 待复核
         */
        public static final Integer STAY_AUDIT = 1;

        /**
         * 已完成
         */
        public static final Integer YET_AUDIT = 2;

        /**
         * 驳回
         */
        public static final Integer NO_AUDIT = 3;

        /**
         * 已发货
         */
        public static final Integer YET_DELIVERY = 5;


        /**
         * 申领出库类型
         */
        public static final Integer APPLY_TYPE = 12;

        /**
         * 退货出库类型
         */
        public static final Integer BACK_TYPE = 13;

        public static final Map<Integer, String> statusMaps = new HashMap<Integer, String>() {{
            put(1, "待复核");
            put(2, "已完成");
            put(3, "驳回");
            put(5, "已发货");
        }};

        public static final Map<Integer, String> typeMaps = new HashMap<Integer, String>() {{
            put(12, "申领出库");
            put(13, "退货出库");
        }};

        /**
         * 返回成功标识
         */
        public static final Integer SUCCESS_FLAG = 1;

    }

    /**
     * 药品盘点编码常量
     */
    public static final class DrugCheckBillConstant {

        //盘点状态--新建
        public static final Integer STATUS_CREATE = 1;

        //盘点状态--盘点中
        public static final Integer STATUS_IN_CHECK = 2;

        //盘点状态--待审核
        public static final Integer STATUS_WAIT_EXAMINE = 3;

        //盘点状态--驳回
        public static final Integer STATUS_REJECT = 4;

        //盘点状态--已完成
        public static final Integer STATUS_COMPLETE = 5;

        //盘点类型--明盘
        public static final Integer TYPE_LIGHT = 1;

        //盘点类型--暗盘
        public static final Integer TYPE_DARK = 2;

        //盘点子类型--全盘
        public static final Integer SUB_TYPE_ALL = 1;

        //盘点子类型--动销盘
        public static final Integer SUB_TYPE_MOVE_AND_SALE = 2;

        //盘点范围--自定义
        public static final Integer RANGE_SELF = 2;

    }

    /**
     * 欠品单状态1待处理  2已收货   3已关闭
     */
    public static final class MarginFstate {
        /**
         * 1待处理
         */
        public static final Integer DISPOSE_ING = 1;

        /**
         * 已收货
         */
        public static final Integer DISPOSE_ED = 2;

        /**
         * 已关闭
         */
        public static final Integer DISPOSE_CLOSE = 3;
    }

    /**
     * 状态 1待拣货 2部分拣货 3已拣货 4已完成
     */
    public final static class StorehousePickingFstate {
        public final static Integer NOT_PICKING = 1;
        public final static Integer PORTTION_PICKING = 2;
        public final static Integer PICKING = 3;
        public final static Integer FINISH = 4;
    }

    /**
     * 配送轨迹
     */
    public final static class StockValue {
        public final static String ORDER = "供应商接收订单";
        public final static String SPD_DELIVERY = "SPD仓库接收配送单";
        public final static String TRA_DELIVERY = "承运商接收配送单";
        public final static String TRA_DELIVERY_ED = "承运商正在配送单";
        public final static String DELIVERY = "已配送完成";

    }

    /**
     * 拒收单类型 1拒收 2手工拒收
     */
    public final static class StorehouseRejectionInfoType {
        public final static Integer rejection = 1;
        public final static Integer manual_rejection = 2;
    }

    /**
     * 三明平台发货明细状态与医供宝平台状态对应
     */
    public static final Map<Integer, Integer> SanMingDeliverDetailStatusMap = new HashMap<Integer, Integer>() {{
        put(1, 1);
        put(2, 4);
        put(3, 3);
    }};

    /**
     * 医院产品单位类型 1、最小发药单位 2、包装规格单位 3、整包装单位 4、采购单位
     */
    public static final Map<Integer, String> TenderProductUnitTypeMap = new HashMap<Integer, String>() {{
        put(1, "最小发药单位");
        put(2, "包装规格单位");
        put(3, "整包装单位");
        put(4, "采购单位");
    }};


    /**
     * saas系统出库方式
     */
    public final static class SaasOutMode {

        //手工出库
        public static final String HAND = "01";

        //发药出库
        public static final String DISPENSING = "02";

        //盘亏出库
        public static final String CHECK_LOSS = "03";

        //申领出库
        public static final String APPLY = "04";

        //退货出库
        public static final String BACK = "05";

        //报损出库
        public static final String REPORT_LOSS = "99";

        public static final Map<String, String> saasOutmodeMap = new HashMap<String, String>() {{
            put("01", "手工出库");
            put("02", "发药出库");
            put("03", "盘亏出库");
            put("04", "申领出库");
            put("05", "退货出库");
            put("99", "报损出库");
        }};
    }


    /**
     * saas系统部门类型
     */
    public final static class SaasDeptType {
        //系统管理
        public static final String DEPARTMENT = "1";

        //药库
        public static final String DEPARTMENT_STOREHOUSE = "2";

        //药房
        public static final String SECOND_STOREHOUSE = "3";

    }

    public final static class WorkFstate {
        public static final Integer REST = 1;
        public static final Integer WORK = 2;
    }

    /**
     * 1:supplyCertId  2:authorizationId 3:supplyProductId 供应商4 org_id 5证件org_cert_id 6:代理商f_org_agent_id  7业务员user_grant_id
     */
    public final static class AuditDetailIdType {
        public static final Integer supplyCertId = 1;
        public static final Integer authorizationId = 2;
        public static final Integer supplyProductId = 3;
        public static final Integer org_id = 4;
        public static final Integer org_cert_id = 5;
        public static final Integer f_org_agent_id = 6;
        public static final Integer user_grant_id = 7;
    }

    /**
     * 清点状态：1 待复核   2复核驳回  3已清点  4换货
     */
    public final  static class InventoryFstate{
        public static final Integer check_ing = 1;
        public static final Integer check_reject = 2;
        public static final Integer check_ed = 3;
        public static final Integer give_back = 4;
    }

    /**
     * 微信消息推送状态 0未推送 1推送成功 2推送失败
     */
    public final  static class WxMessageFstate{
        public static final Integer NO_PUSH = 0;
        public static final Integer PUSH_SUCCESS = 1;
        public static final Integer PUSH_FAILURE = 2;
    }

    /**
     * 微信消息模版类型
     */
    public final  static class WxMessageType{
        /**
         * 企业证件到期提醒
         */
        public static final String orgCertExpire = "orgCertExpire";
        /**
         * 企业证件到期提醒
         */
        public static final String fOrgGrantCertExpire = "fOrgGrantCertExpire";
        /**
         * 企业证件到期提醒
         */
        public static final String userGrantCertExpire = "userGrantCertExpire";
        /**
         * 企业证件到期提醒
         */
        public static final String supplyCertExpire = "supplyCertExpire";
    }

}


