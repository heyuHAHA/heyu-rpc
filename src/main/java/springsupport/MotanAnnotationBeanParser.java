package springsupport;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Locale;

public class MotanAnnotationBeanParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition bd = new RootBeanDefinition();
        bd.setBeanClass(AnnotationBean.class);
        bd.setLazyInit(false);

        String id = element.getAttribute("id");
        //默认每个都有Id
        if (id == null || id.length() == 0) {
            String  generatedName = element.getAttribute("name");
            if(generatedName == null || generatedName.length() == 0) {
                generatedName = element.getAttribute("class");
            }
            if (generatedName == null || generatedName.length() == 0) {
                generatedName = AnnotationBean.class.getName();
            }
            id = generatedName;
            int counter = 2;
            while (parserContext.getRegistry().containsBeanDefinition(id)) {
                id = generatedName + (counter++);
            }
        }
        bd.getPropertyValues().addPropertyValue("id",id);

        for (Method method : AnnotationBean.class.getClass().getMethods()) {
            String name = method.getName();
            if (name.length() <= 3
                    || !name.startsWith("set")
                    || !Modifier.isPublic(method.getModifiers())
                    || method.getParameterTypes().length != 1) {
                continue;
            }
            String property = name.substring(3,4).toLowerCase(Locale.ROOT) + name.substring(4);
            if ("id".equals(property)) {
                bd.getPropertyValues().addPropertyValue("id",id);
                continue;
            }

        }
        return bd;
    }
}
