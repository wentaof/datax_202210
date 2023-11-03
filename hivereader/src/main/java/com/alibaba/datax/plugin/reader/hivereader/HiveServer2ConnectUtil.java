package com.alibaba.datax.plugin.reader.hivereader;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveServer2ConnectUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HiveServer2ConnectUtil.class);

    /**
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) {
//        "; use default; create table tmp_datax_hivereader_20220808_1659953092709 ROW FORMAT DELIMITED FIELDS TERMINATED BY '\\u0001' STORED AS TEXTFILE  as select id,username,password from default.t_user;"
        execHiveSql("hadoop", "hadoop"
                ,"; use fengwentao; create table tmp_datax_hivewriter_20221227_1672131490477(id STRING,customer_id STRING,name STRING,gender STRING,ristel STRING,creat_time TIMESTAMP,sales_id STRING,is_customer STRING,identity STRING,external_user_id STRING,wechat_nickname STRING,sales_stage STRING,sales_stage_name STRING,qw_sales_id STRING,sales_name STRING,qw_remark STRING,area_id STRING,area_value STRING,first_connect_time STRING,synctime STRING)  ROW FORMAT DELIMITED FIELDS TERMINATED BY '\\u0001' stored as TEXTFILE"
                ,"jdbc:hive2://192.168.192.26:10001/default");
    }

    /**
     * hive执行多个sql
     *
     * @param username
     * @param password
     * @param hiveSql
     * @param hiveJdbcUrl
     * @return
     */
    public static boolean execHiveSql(String username, String password, String hiveSql, String hiveJdbcUrl) {
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            LOG.info("hiveJdbcUrl:{}", hiveJdbcUrl);
            LOG.info("username:{}", username);
            LOG.info("password:{}", password);
            Connection conn = DriverManager.getConnection(hiveJdbcUrl, username, password);
            Statement stmt = conn.createStatement();

            String[] hiveSqls = hiveSql.split(";");
            for (int i = 0; i < hiveSqls.length; i++) {
                if (StringUtils.isNotEmpty(hiveSqls[i])) {
                    stmt.execute(hiveSqls[i]);
                }
            }
            return true;
        } catch (SQLException sqlException) {
            LOG.error(sqlException.getMessage(), sqlException);
            return false;
        }
    }
}