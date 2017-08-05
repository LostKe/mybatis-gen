package com.hhly.mybatis.codegen.xmlmapper;

import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;

public class YjsJavaMapperGenerator extends JavaMapperGenerator {

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return new YjsXMLMapperGenerator();
    }

}
