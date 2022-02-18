package com.company.project.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * JDBC工具类
 *
 * @author DanielQSL
 */
public class JdbcUtil {

    private static final String MYSQL_JDBC_URL_FORMAT = "jdbc:mysql://%s:%s";

    private JdbcUtil() {
    }

    /**
     * 通用查询
     * 返回单条记录
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     * @param params     参数
     * @return 查询结果
     */
    public static Object[] query(DataSource dataSource, String sql, Object... params) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        return queryRunner.query(sql, new ArrayHandler(), params);
    }

    /**
     * 通用集合查询
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     * @param params     参数
     * @return 查询结果
     */
    public static <T> T list(DataSource dataSource, String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        return queryRunner.query(sql, rsh, params);
    }

    /**
     * 通用插入
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     * @param params     参数
     * @return 插入成功行数
     */
    public static int save(DataSource dataSource, String sql, Object... params) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        return queryRunner.update(sql, params);
    }

    /**
     * 批量插入
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     * @param params     参数
     */
    public static void batchInsert(DataSource dataSource, String sql, List<Object[]> params) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        queryRunner.batch(sql, params.toArray(new Object[params.size()][]));
    }

    /**
     * 通用删除
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     * @param params     参数
     * @return 删除成功行数
     */
    public static int remove(DataSource dataSource, String sql, Object... params) throws SQLException {
        return save(dataSource, sql, params);
    }

    /**
     * 执行sql语句
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     */
    public static void execute(DataSource dataSource, String sql) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        queryRunner.execute(sql);
    }

    /**
     * 原生JDBC执行建表语句
     *
     * @param dataSource 数据源
     * @param sql        SQL语句
     */
    public static void jdbcExecute(DataSource dataSource, String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    /**
     * 原生JDBC执行MySQL的sql语句
     *
     * @param host     主机名
     * @param port     端口号
     * @param username 用户名
     * @param password 密码
     * @param sql      SQL语句
     */
    public static void jdbcExecute(String host, Integer port, String username, String password, String sql) throws SQLException {
        String connectionStr = String.format(MYSQL_JDBC_URL_FORMAT, host, port);
        try (Connection connection = DriverManager.getConnection(connectionStr, username, password);
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

}
