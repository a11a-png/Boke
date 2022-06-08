package com.wjg.boke.boke.comment.templates;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

public abstract class TemplateDirective implements TemplateDirectiveModel {
    protected static String ARTICLES = "articles";
    protected static String COLLECTION = "collection";
    protected static String ARTICLESS = "articless";
    protected static String MESSAGE = "message";
    protected static String HOTARTICLE = "Hotarticle";

    @Override
    public void execute(Environment env, Map parameters,
                        TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        try {
            execute(new DirectiveHandler(env, parameters, loopVars, body));
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new TemplateException(e, env);
        }
    }

    abstract public String getName();
    abstract public void execute(DirectiveHandler handler) throws Exception;
}
