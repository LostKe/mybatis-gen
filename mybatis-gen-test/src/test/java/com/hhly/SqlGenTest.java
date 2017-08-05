package com.hhly;

import com.google.common.base.CaseFormat;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

/**
 * 生成所有的 table 字符.
 * <p/>
 * Created by tony on 15-4-23.
 */
@ContextConfiguration("/testSpringContext.xml")
public class SqlGenTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String SQL = "SHOW TABLES";
    private static final String FORMAT = "\n<table tableName=\"%s\" domainObjectName=\"%s\"  escapeWildcards=\"true\"\n" +
            "\t\t    enableCountByExample=\"true\" enableUpdateByExample=\"true\" enableDeleteByExample=\"true\"\n" +
            "\t\t    enableSelectByExample=\"true\" selectByExampleQueryId=\"true\"\n" +
            "\t\t    delimitIdentifiers=\"true\" delimitAllColumns=\"true\"/>";

    @Test
    public void testMybatisCreate() throws Exception {
        List<String> tables = jdbcTemplate.queryForList(SQL, String.class);
        for (String table : tables) {
            String entityName = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.UPPER_CAMEL)
                    .convert(table.toLowerCase().startsWith("t_") ? table.substring(2) : table);

            System.out.println(String.format(FORMAT, table, entityName));
        }
    }
}
