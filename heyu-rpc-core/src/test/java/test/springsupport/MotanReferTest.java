package test.springsupport;


import test.SpringConfigurationBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springsupport.AnnotationBean;
import springsupport.RefererConfigBean;

public class MotanReferTest {

    private  ApplicationContext applicationContext;


    @Before
    public void setUp() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfigurationBean.class);
        this.applicationContext = applicationContext;
        Assert.assertNotNull(this.applicationContext);
    }

    @Test
    public void testReferConfigBean() {
        AnnotationBean annotationBean = applicationContext.getBean(AnnotationBean.class);
        Assert.assertNotNull(annotationBean);
        RefererConfigBean refererConfigBean = annotationBean.getFromMap("/com.heyu.rpc.test.Service:");
        Assert.assertNotNull(refererConfigBean);
        Assert.assertNotNull(refererConfigBean.getProtocolConfig());
        Assert.assertNotNull(refererConfigBean.getRegistryConfig());
        Assert.assertNotNull(refererConfigBean.getBasicRefererInterfaceConfig());
    }
}
