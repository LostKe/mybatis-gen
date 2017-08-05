package com.hhly.mybatis.codegen;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.ArrayList;
import java.util.List;

public class IntrospectedTableMyBatis3Impl extends org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl {
    @Override
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();

        if (xmlMapperGenerator != null) {
            Document document = xmlMapperGenerator.getDocument();
            GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                    getMyBatis3XmlMapperFileName(), getMyBatis3XmlMapperPackage(),
                    context.getSqlMapGeneratorConfiguration().getTargetProject(),
                    true, context.getXmlFormatter());
            if (context.getPlugins().sqlMapGenerated(gxf, this)) {
                answer.add(gxf);
            }

            // 多生成一个目录. 添加 _cust.xml 的空配置. 只在某个表第一次生成时用到
            Document myDocument = new Document(document.getPublicId(), document.getSystemId());
            XmlElement myElement = new XmlElement(document.getRootElement());
            myElement.getElements().clear();
            myDocument.setRootElement(myElement);

            String xmlFileName = getMyBatis3XmlMapperFileName();
            int pos = xmlFileName.lastIndexOf('.');

            String newXmlFileName = xmlFileName.substring(0, pos) + "_cust" + xmlFileName.substring(pos);
            GeneratedXmlFile mygxf = new GeneratedXmlFile(myDocument,
                    newXmlFileName, "custom",
                    context.getSqlMapGeneratorConfiguration().getTargetProject(),
                    true, context.getXmlFormatter());
            if (context.getPlugins().sqlMapGenerated(mygxf, this)) {
                answer.add(mygxf);
            }
        }
        return answer;
    }
}
