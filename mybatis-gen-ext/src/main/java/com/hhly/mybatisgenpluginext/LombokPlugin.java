package com.hhly.mybatisgenpluginext;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * 给 javabean 生成 Lombok 的 @Data 注解
 * 同时不生成 get/set 方法
 */
public class LombokPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
        //interfaze.addAnnotation("@Repository");
        return true;
    }

    /**
     * 不生成 insert 方法,包含全部字段的
     */
    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成 insert 的 xml, 包含全部字段的
     */
    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumns != null) {
            if (primaryKeyColumns.size() == 1) {
                IntrospectedColumn pk = primaryKeyColumns.get(0);
                element.addAttribute(new Attribute("useGeneratedKeys", "true"));
                element.addAttribute(new Attribute("keyColumn", pk.getActualColumnName()));
                element.addAttribute(new Attribute("keyProperty", pk.getJavaProperty()));
            } else {
                System.err.printf("注意---表:%s的主键是多个列的复合主键,请修改 insert 语句的 xml 映射,注意主键生成\n",
                        introspectedTable.getTableConfiguration().getTableName());
            }
        }
        return true;
    }

    /**
     * 不生成 update 方法, 包含全部字段的
     */
    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成 update 的 xml, 包含全部字段的
     */
    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成 updateByPrimary 方法, 包含全部字段的
     */
    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成 updateByPrimary 的 xml, 包含全部字段的
     */
    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // topLevelClass.addImportedType("lombok.Data");
        // topLevelClass.addAnnotation("@Data");

        // topLevelClass.addImportedType("lombok.ToString");
        // topLevelClass.addAnnotation("@ToString");

        topLevelClass.addImportedType("lombok.Setter");
        topLevelClass.addAnnotation("@Setter");

        topLevelClass.addImportedType("lombok.Getter");
        topLevelClass.addAnnotation("@Getter");

        topLevelClass.addImportedType("lombok.NoArgsConstructor");
        topLevelClass.addAnnotation("@NoArgsConstructor");

        topLevelClass.addImportedType("lombok.experimental.Accessors");
        topLevelClass.addAnnotation("@Accessors(chain = true)");
        return true;
    }

    /**
     * 不生成 get 方法
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * 不生成 set 方法
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * 加入分页的方法. 以及按 hander 处理结果集的方法
     */
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType pageType = new FullyQualifiedJavaType("com.github.miemiedev.mybatis.paginator.domain.PageBounds");
        interfaze.addImportedType(pageType);

        // 加入分页方法
        Method pageBoundsMethod = new Method(method);
        pageBoundsMethod.addParameter(new Parameter(pageType, "page"));
        interfaze.addMethod(pageBoundsMethod);


        FullyQualifiedJavaType typeHandlerType = new FullyQualifiedJavaType("org.apache.ibatis.session.ResultHandler");
        interfaze.addImportedType(typeHandlerType);

        // 加入 hander 处理结果集方法
        Method handlerMethod = new Method(method);
        handlerMethod.addParameter(new Parameter(typeHandlerType, "handler"));
        handlerMethod.setReturnType(new FullyQualifiedJavaType("void")); //注意要返回void
        interfaze.addMethod(handlerMethod);

        // 加入分页及用 hander 处理结果集方法
        Method handlerMethod2 = new Method(method);
        handlerMethod2.addParameter(new Parameter(pageType, "page"));
        handlerMethod2.addParameter(new Parameter(typeHandlerType, "handler"));
        handlerMethod2.setReturnType(new FullyQualifiedJavaType("void")); //注意要返回void
        interfaze.addMethod(handlerMethod2);

        return true;
    }

    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        return clientSelectByExampleWithBLOBsMethodGenerated(method, interfaze, introspectedTable);
    }
}
